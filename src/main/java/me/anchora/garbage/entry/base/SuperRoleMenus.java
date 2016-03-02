package me.anchora.garbage.entry.base;

import me.anchora.garbage.entry.Menus;
import me.anchora.garbage.entry.Roles;

public class SuperRoleMenus extends PageEntry {
    private Roles roles;
    private Menus menus;

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Menus getMenus() {
        return menus;
    }

    public void setMenus(Menus menus) {
        this.menus = menus;
    }

}
