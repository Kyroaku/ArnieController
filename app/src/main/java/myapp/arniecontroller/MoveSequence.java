package myapp.arniecontroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Dziedzic on 2017-11-21.
 */

public class MoveSequence {
    private List<Element> mSequence;
    private String mName;

    public MoveSequence(String name) {
        mSequence = new ArrayList<>();
        mName = name;
    }

    public void Add() {
        mSequence.add(new Element());
    }
    public List<Element> GetList() {
        return mSequence;
    }

    public String toString() {
        return mName;
    }

    public class Element {
        short mAngles[];

        public Element() {
            mAngles = new short[]{0, 0, 0};
        }

        public String toString() {
            return "[" + mAngles[0] + ", " + mAngles[1] + ", " + mAngles[2] + "]";
        }
    }
}
