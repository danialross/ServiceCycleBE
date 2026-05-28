package com.danialross.ServiceCycle.modules.parts.enums;

public enum PartPosition {
    FRONT,
    FRONT_LEFT,
    FRONT_RIGHT,
    BACK,
    BACK_LEFT,
    BACK_RIGHT,
    LEFT,
    RIGHT;

    public static final String allValues = "FRONT,FRONT_LEFT,FRONT_RIGHT,BACK,BACK_LEFT,BACK_RIGHT,LEFT,RIGHT";
    public static final String regexPattern = "FRONT|FRONT_LEFT|FRONT_RIGHT|BACK|BACK_LEFT|BACK_RIGHT|LEFT|RIGHT";
}
