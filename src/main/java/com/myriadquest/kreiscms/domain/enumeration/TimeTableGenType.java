package com.myriadquest.kreiscms.domain.enumeration;

/**
 * The TimeTableGenType enumeration.
 */
public enum TimeTableGenType {
    ALL_DAYS_EXCEPT_SAT("sameForAllDaysExceptSaturday"),
    ALL_DAYS("sameForAllDays");

    private final String value;


    TimeTableGenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
