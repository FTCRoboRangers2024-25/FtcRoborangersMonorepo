package org.firstinspires.ftc.teamcode.base.utils;

public class Functions {
    public static double linearInterpolation(double a, double b, double t) {
        return a + (b - a) * t;
    }

    public static double clamp(double min, double max, double value) {
        if (value < min) return min;
        else if (value > max) return max;
        else return value;
    }

    public static int clamp(int min, int max, int value) {
        if (value < min) return min;
        else if (value > max) return max;
        else return value;
    }

    public static double clamp01(double value) {
        return clamp(0, 1, value);
    }

    public static double targetTolerance(double target, double tolerance, double value) {
        if (Math.abs(value - target) <= tolerance) {
            return target;
        }
        return value;
    }

    public static int targetTolerance(int target, int tolerance, int value) {
        if (Math.abs(value - target) <= tolerance) {
            return target;
        }
        return value;
    }
}
