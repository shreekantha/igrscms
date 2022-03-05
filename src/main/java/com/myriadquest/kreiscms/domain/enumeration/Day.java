package com.myriadquest.kreiscms.domain.enumeration;

/**
 * The Day enumeration.
 */
public enum Day {
    MON("Mon"),
    TUE("Tue"),
    WED("Wed"),
    THU("Thu"),
    FRI("Fri"),
    SAT("Sat"),
    SUN("Sun");

    private final String value;


    Day(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
