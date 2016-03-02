package me.anchora.garbage.entry.base;

import me.anchora.garbage.entry.MenuDetails;
import me.anchora.garbage.entry.MenuLanguages;
import me.anchora.garbage.entry.Users;

public class SuperMenus extends PageEntry {
    private MenuDetails menuDetails;
    private Users users;
    private MenuLanguages menuLanguages;
    private String menuLanguageUser;
    private String parentMenuLanguageUser;

    public MenuDetails getMenuDetails() {
        return menuDetails;
    }

    public void setMenuDetails(MenuDetails menuDetails) {
        this.menuDetails = menuDetails;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public MenuLanguages getMenuLanguages() {
        return menuLanguages;
    }

    public void setMenuLanguages(MenuLanguages menuLanguages) {
        this.menuLanguages = menuLanguages;
    }

    public String getMenuLanguageUser() {
        return menuLanguageUser;
    }

    public void setMenuLanguageUser(String menuLanguageUser) {
        this.menuLanguageUser = menuLanguageUser;
    }

    public String getParentMenuLanguageUser() {
        return parentMenuLanguageUser;
    }

    public void setParentMenuLanguageUser(String parentMenuLanguageUser) {
        this.parentMenuLanguageUser = parentMenuLanguageUser;
    }

}
