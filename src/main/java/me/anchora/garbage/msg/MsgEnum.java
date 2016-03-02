package me.anchora.garbage.msg;

public enum MsgEnum {
    SUCCESS("000", "Success."),
    LOGIN_10001("LOGIN_10001", "Username is required!"),
    LOGIN_10002("LOGIN_10002", "Password is required!"),
    LOGIN_10003("LOGIN_10003", "ValidationCode is required!"),
    LOGIN_10004("LOGIN_10004", "ValidationCode is error!"),
    LOGIN_10005("LOGIN_10005", "User dosn't exists or password is error!"),
    LOGIN_10006("LOGIN_10006", "Could not delete yourself!"),
    LOGIN_10007("LOGIN_10007", "The old password is wrong!"),
    LOGIN_10008("LOGIN_10008", "You are not administrator, could not login!"),
    USER_ALL("USER_ALL", "Number of users"),
    GARBAGE_ALL("GARBAGE_ALL", "Number of garbages"),
    QRCODE_00001("QRCODE_00001", "QrCode does not exist!"),
    QRCODE_00002("QRCODE_00002", "QrCode already exist!"),
    QRCODE_00003("QRCODE_00003", "QrCode start is larger then end."),
    QRCODE_00004("QRCODE_00004", "Wrong length between qrCode start and end."),
    QRCODE_00005("QRCODE_00005", "QrCode start is required!"),
    QRCODE_00006("QRCODE_00006", "QrCode could not be deleted!"),
    QRCODE_00007("QRCODE_00007", "QrCode could not be modified!"),
    
    GIFT_00001("GIFT_00001", "Gift grant num could not larger than gift num!"),
    GIFT_00002("GIFT_00002", "Gift has been granted, could not delete!"),

    GIFT_GRANT_00001("GIFT_GRANT_00001", "Remainning gift num is less then you granted!"),
    GIFT_GRANT_00002("GIFT_GRANT_00002", "User score is sufficient!"),
    
    CACHE_GARBAGE_CANS("GARBAGE_CANS", "GARBAGE_CANS"),
    CACHE_GARBAGE_TYPES("GARBAGE_TYPES", "GARBAGE_TYPES"),
    CACHE_GARBAGE_STATIONS("CACHE_GARBAGE_STATIONS", "CACHE_GARBAGE_STATIONS"),
    CACHE_GIFTS("CACHE_GIFTS", "CACHE_GIFTS"),
    CACHE_MENUS("CACHE_MENUS", "CACHE_MENUS"),
    CACHE_USERS("CACHE_USERS", "CACHE_USERS"),
    
    LOGIN("0002", "Please Login!"),
    SYS_ERROR("0001", "System Error!");

    private final String code;
    private final String description;

    private MsgEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
    
    public static MsgEnum getByCode(String code) {
        for (MsgEnum cacheCode : values()) {
            if (cacheCode.getCode().equals(code)) {
                return cacheCode;
            }
        }
        return null;
    }
}
