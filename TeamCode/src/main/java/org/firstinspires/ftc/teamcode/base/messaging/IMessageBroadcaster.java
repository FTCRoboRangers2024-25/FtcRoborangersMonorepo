package org.firstinspires.ftc.teamcode.base.messaging;

import java.util.HashSet;

public interface IMessageBroadcaster {
    void broadcastMessage(String message);
    void addReceiverRange(HashSet<Object> receivers);
    void addReceiver(Object receiver);
}
