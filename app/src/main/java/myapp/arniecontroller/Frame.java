package myapp.arniecontroller;

import java.io.Serializable;

/**
 * Created by Marcin Dziedzic on 2017-10-28.
 */

public class Frame implements Serializable {

    byte mCommand;

    // Constructors.

    public Frame() {
        this((byte)0);
    }
    public Frame(byte command) {
        mCommand = command;
    }

    // Setters.

    public void Command(byte command) {
        mCommand = command;
    }

    // Getters.

    public byte Command() {
        return mCommand;
    }
}