package me.anchora.garbage.entry.base;

import me.anchora.garbage.entry.Roles;
import me.anchora.garbage.entry.Users;


public class SuperRoleUsers extends PageEntry {
    private Users users;
    private Roles roles;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
