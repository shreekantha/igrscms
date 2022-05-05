package com.myriadquest.kreiscms.domain.enumeration;

/**
 * The Designation enumeration.
 */
public enum Designation {
    PRINCIPAL("Principal"),
    ASST_TEACHER_KANNADA("Asst Teacher Kannada"),
    ASST_TEACHER_ENGLISH("Asst Teacher English"),
    ASST_TEACHER_HINDI("Asst Teacher Hindi"),
    ASST_TEACHER_MATHS("Asst Teacher Maths"),
    ASST_TEACHER_SCIENCE("Asst Teacher Science"),
    ASST_TEACHER_SOCIAL_SCIENCE("Asst Teacher Social Science"),
    ASST_TEACHER_PE("Asst Teacher Physical Education"),
    ASST_TEACHER_COMPUTER("Asst Teacher Computer"),
    ASST_TEACHER_MUSIC("Asst Teacher Music"),
    ASST_TEACHER_DRAWING("Asst Teacher Drawing"),
    STAFF_NURSE("Staff Nurse"),
    WARDEN("Warden"),
    FDA("FDA"),
    SDA("SDA");

    private final String value;


    Designation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
