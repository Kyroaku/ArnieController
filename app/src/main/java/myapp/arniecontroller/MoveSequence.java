package myapp.arniecontroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Dziedzic on 2017-11-21.
 */

public class MoveSequence {
    private static int mNumInstances = 0;

    private List<Element> mSequence;
    private Element mSelectedItem;
    private String mName;

    public MoveSequence(String name) {
        mSequence = new ArrayList<>();
        mName = name;
        mSelectedItem = null;
        mNumInstances++;
    }
    public MoveSequence() {
        this("Sequence " + mNumInstances);
    }

    public void Add() {
        mSequence.add(new Element());
    }
    public void Remove(){
        if(GetSelectedItem() != null)
            mSequence.remove(GetSelectedItem());
    }
    public void Add(short a1, short a2, short a3) {
        mSequence.add(new Element(a1, a2, a3));
    }
    public List<Element> GetList() {
        return mSequence;
    }
    public void Select(int id) {
        if(mSelectedItem != null)
            mSelectedItem.IsSelected(false);
        mSelectedItem = mSequence.get(id);
        mSelectedItem.IsSelected(true);
    }
    public Element GetSelectedItem() {
        return mSelectedItem;
    }

    public String toString() {
        return mName;
    }

    public static void ResetNumInstances() {
        mNumInstances = 0;
    }

    public class Element {
        short mAngles[];
        boolean mIsSelected;

        public Element() {
           this((short)0, (short)0, (short)0);
        }
        public Element(short a1, short a2, short a3) {
            mAngles = new short[]{ a1, a2, a3 };
        }

        public void SetAngle(int angleId, short angle) {
            mAngles[angleId] = angle;
        }
        public short Angle(int angleId) {
            return mAngles[angleId];
        }

        public boolean IsSelected() {
            return mIsSelected;
        }
        public void IsSelected(boolean sel) {
            mIsSelected = sel;
        }

        public String toString() {
            if(IsSelected())
                return "-> [" + mAngles[0] + ", " + mAngles[1] + ", " + mAngles[2] + "]";
            else
                return "[" + mAngles[0] + ", " + mAngles[1] + ", " + mAngles[2] + "]";
        }
    }
}
