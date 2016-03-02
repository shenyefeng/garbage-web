package me.anchora.garbage.entry.base;

import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.Users;

public class SuperGiftGrants extends PageEntry {
    private Users users;
    private Gifts gifts;
    private String userName;
    private String realName;
    private String giftName;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Gifts getGifts() {
        return gifts;
    }

    public void setGifts(Gifts gifts) {
        this.gifts = gifts;
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

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }
}
