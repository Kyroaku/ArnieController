package myapp.arniecontroller;

/**
 * Created by Marcin Dziedzic on 2017-10-28.
 */

public class FrameAngles extends Frame {
    byte[] mAngles;

    // Constructors.

    public FrameAngles() {
        this((byte)0, (byte)0, (byte)0);
    }
    public FrameAngles(byte angle1, byte angle2, byte angle3) {
        mCommand = 123;
        mAngles = new byte[] { angle1, angle2, angle3 };
    }

    // Setters.

    public void Angle1(byte angle) {
        mAngles[0] = angle;
    }
    public void Angle2(byte angle) {
        mAngles[1] = angle;
    }
    public void Angle3(byte angle) {
        mAngles[2] = angle;
    }
    public void Angles(byte angle1, byte angle2, byte angle3) {
        mAngles[0] = angle1; mAngles[1] = angle2; mAngles[2] = angle3;
    }

    // Getters.

    public byte Angle1() {
        return mAngles[0];
    }
    public byte Angle2() {
        return mAngles[1];
    }
    public byte Angle3() {
        return mAngles[2];
    }
    public byte[] Angles() {
        return mAngles;
    }
}
