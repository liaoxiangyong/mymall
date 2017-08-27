package edu.ccnt.mymall.common;

public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LONGIN(10,"NEED_LOGIN"),
    ILLEGLE_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private  final String desc;

    ResponseCode(int code,String desc){
        this.code =code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
