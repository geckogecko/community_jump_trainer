package com.steinbacher.jumpstar.core;

/**
 * Created by georg on 25.03.18.
 */

public class Equipment {
    private static final String TAG = "equipment";

    private String mName;
    private Type mType;

    public enum Type {
        GYM,
        HOME,
        BOTH
    }

    public Equipment(String name, Type type) {
        mName = name;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public Type getType() {
        return mType;
    }
}
