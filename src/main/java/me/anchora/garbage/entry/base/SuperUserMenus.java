package me.anchora.garbage.entry.base;

import me.anchora.garbage.entry.Menus;
import me.anchora.garbage.entry.Users;

public class SuperUserMenus extends PageEntry {
    private Menus menus;
    private Users users;

    public Menus getMenus() {
        return menus;
    }

    public void setMenus(Menus menus) {
        this.menus = menus;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
