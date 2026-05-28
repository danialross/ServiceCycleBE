package com.danialross.ServiceCycle.modules.parts.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PartType {
    TYRES,
    CHAIN,
    SPROCKET,
    ENGINE_OIL,
    BRAKE_FLUID,
    COOLANT,
    BRAKE_PADS,
    VALVE_CLEARANCE,
    AIR_FILTER,
    CLUTCH,
    BATTERY,
    SPARK_PLUG,
    TRANSMISSION_FLUID,
    OTHERS;

    public static final String allValues = "TYRES,CHAIN,SPROCKET,ENGINE_OIL,BRAKE_FLUID,COOLANT,BRAKE_PADS,VALVE_CLEARANCE,AIR_FILTER,CLUTCH,BATTERY,SPARK_PLUG,TRANSMISSION_FLUID,OTHERS";

    public static final String regexPattern = "TYRES|CHAIN|SPROCKET|ENGINE_OIL|BRAKE_FLUID|COOLANT|BRAKE_PADS|VALVE_CLEARANCE|AIR_FILTER|CLUTCH|BATTERY|SPARK_PLUG|TRANSMISSION_FLUID|OTHERS";

    public static final List<PartType> positionedParts = List.of(TYRES,BRAKE_PADS);

    public static final List<PartType> indexedParts = List.of(SPARK_PLUG);

}
