package me.anchora.garbage.entry.base;

import me.anchora.garbage.entry.GarbageCans;
import me.anchora.garbage.entry.GarbageStations;
import me.anchora.garbage.entry.GarbageTypes;
import me.anchora.garbage.entry.QrCodes;
import me.anchora.garbage.entry.Users;

public class SuperGarbages extends PageEntry {
    private Users users;
    private GarbageCans garbageCans;
    private GarbageStations garbageStations;
    private GarbageTypes garbageTypes;
    private QrCodes qrCodes;

    private String userName;
    private String realName;
    private String qrCode;
    private String typeName;
    private String stationName;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public GarbageCans getGarbageCans() {
        return garbageCans;
    }

    public void setGarbageCans(GarbageCans garbageCans) {
        this.garbageCans = garbageCans;
    }

    public GarbageStations getGarbageStations() {
        return garbageStations;
    }

    public void setGarbageStations(GarbageStations garbageStations) {
        this.garbageStations = garbageStations;
    }

    public GarbageTypes getGarbageTypes() {
        return garbageTypes;
    }

    public void setGarbageTypes(GarbageTypes garbageTypes) {
        this.garbageTypes = garbageTypes;
    }

    public QrCodes getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(QrCodes qrCodes) {
        this.qrCodes = qrCodes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
