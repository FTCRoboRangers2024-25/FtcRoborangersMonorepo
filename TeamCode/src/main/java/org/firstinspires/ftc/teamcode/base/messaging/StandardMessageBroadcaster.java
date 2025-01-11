package org.firstinspires.ftc.teamcode.base.messaging;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import javax.inject.Inject;

public class StandardMessageBroadcaster implements IMessageBroadcaster {

    private class ReceiverMethod {
        public String message;
        public Object instance;
        public Method method;

        public ReceiverMethod(String message, Object instance, Method method) {
            this.message = message;
            this.instance = instance;
            this.method = method;
        }
    }

    private final Telemetry telemetry;
    private final HashSet<ReceiverMethod> messageReceiverMethods = new HashSet<>();

    @Inject
    public StandardMessageBroadcaster(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void broadcastMessage(String message) {
        if (message.isEmpty())
            telemetry.addLine("Broadcasted empty message");

        for (ReceiverMethod receiver : messageReceiverMethods) {
            if (receiver.message.equals(message) && receiver.instance != null) {
                try {
                    receiver.method.invoke(receiver.instance);
                } catch (IllegalAccessException iae) {
                    telemetry.addLine("This method can't be accessed by message broadcasting: " + iae.getMessage());
                } catch (InvocationTargetException ite) {
                    telemetry.addLine("Method invoked caused an exception: " + ite.getMessage());
                }
            }
        }
    }

    @Override
    public void addReceiverRange(HashSet<Object> receivers) {
        for (Object obj : receivers) {
            addReceiver(obj);
        }
    }

    @Override
    public void addReceiver(Object receiver) {
        Class<?> objectType = receiver.getClass();
        for (Method method : objectType.getMethods()) {
            if (method.isAnnotationPresent(ReceiveMessage.class)) {
                messageReceiverMethods.add(
                        new ReceiverMethod(
                                method.getAnnotation(ReceiveMessage.class).message(),
                                receiver,
                                method)
                );
            }
        }
    }
}
