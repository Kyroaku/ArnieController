package myapp.arniecontroller;

/**
 * Created by Marcin Dziedzic on 2017-10-28.
 */

public class FrameAngles extends Frame {
    short[] mAngles;

    // Constructors.

    public FrameAngles() {
        this(0, 0, 0);
    }
    public FrameAngles(int angle1, int angle2, int angle3) {
        mCommand = 123;
        mAngles = new short[] { (short)angle1, (short)angle2, (short)angle3 };
    }

    // Setters.

    public void Angle1(int angle) {
        mAngles[0] = (short)angle;
    }
    public void Angle2(int angle) {
        mAngles[1] = (short)angle;
    }
    public void Angle3(int angle) {
        mAngles[2] = (short)angle;
    }
    public void Angles(int angle1, int angle2, int angle3) {
        mAngles[0] = (short)angle1; mAngles[1] = (short)angle2; mAngles[2] = (short)angle3;
    }

    // Getters.

    public short Angle1() {
        return mAngles[0];
    }
    public short Angle2() {
        return mAngles[1];
    }
    public short Angle3() {
        return mAngles[2];
    }
    public short[] Angles() {
        return mAngles;
    }
}
