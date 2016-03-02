/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.anchora.garbage.entry.Users;
import me.anchora.garbage.entry.UsersCriteria;
import me.anchora.garbage.entry.base.CommonVo;

import org.apache.ibatis.session.RowBounds;

public interface UsersService {
    public List<Users> queryUsersByPage(UsersCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(UsersCriteria criteria);

    public List<Users> login(Users users);

    public void insert(Users users);

    public void update(Users users);

    public void delete(Users users);

    public void changePwd(Users users);

    public void updateLastLogin(Users usersReturn);

	public List<Map<String, Object>> usersChart(CommonVo commonVo, Locale locale);

    public List<Users> queryAllUsers(UsersCriteria criteria);
}
