package me.anchora.garbage.entry.base;

import me.anchora.garbage.entry.Garbages;
import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.Users;

public class SuperScoreDetails extends PageEntry {
    private Users users;
    private Gifts gifts;
    private Garbages garbages;

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

    public Garbages getGarbages() {
        return garbages;
    }

    public void setGarbages(Garbages garbages) {
        this.garbages = garbages;
    }

}
