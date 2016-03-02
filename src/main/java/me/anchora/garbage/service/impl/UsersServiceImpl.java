/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import static me.anchora.garbage.Constants.CHART_DAYS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.UsersMapper;
import me.anchora.garbage.entry.Users;
import me.anchora.garbage.entry.UsersCriteria;
import me.anchora.garbage.entry.base.CommonVo;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.UsersService;
import me.anchora.garbage.utils.DateUtil;
import me.anchora.garbage.utils.ListUtil;
import me.anchora.garbage.utils.Md5;
import me.anchora.garbage.utils.SystemUtil;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "usersService")
public class UsersServiceImpl extends BaseService implements UsersService {
    private static Logger logger = Logger.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<Users> queryUsersByPage(UsersCriteria criteria, RowBounds rowBounds) {
        List<Users> result = usersMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(UsersCriteria criteria) {
        Integer result = usersMapper.countByExample(criteria);
        return result;
    }

    @Override
    public List<Users> login(Users users) {
        UsersCriteria criteria = new UsersCriteria();
        criteria.createCriteria().andUserNameEqualTo(users.getUserName()).andPasswordEqualTo(users.getPassword());
        
        List<Users> result = usersMapper.selectByExample(criteria);
        
        return result;
    }

    @Override
    public void insert(Users users) {
        users.setCreatedAt(new Date());
        users.setCreatedBy(users.getAdminId());
        if(users.getPassword() != null && users.getPassword().length() > 0){
            users.setPassword(Md5.MD5(users.getPassword()));
        }
        if(users.getIsAdmin() == null || "".equals(users.getIsAdmin()) || !"Y".equals(users.getIsAdmin())) {
            users.setIsAdmin("N");
        }
        usersMapper.insert(users);
        cacheService.doReloadUsers();
    }

    @Override
    public void update(Users users) {
        users.setUpdatedAt(new Date());
        users.setUpdatedBy(users.getAdminId());
        if(users.getPassword() != null && users.getPassword().length() > 0) {
            users.setPassword(Md5.MD5(users.getPassword()));
        }
        
        if(users.getIsAdmin() == null || "".equals(users.getIsAdmin()) || !"Y".equals(users.getIsAdmin())) {
            users.setIsAdmin("N");
        }
        
        usersMapper.updateByPrimaryKeySelective(users);
        cacheService.doReloadUsers();
    }

    @Override
    public void updateLastLogin(Users users) {
        usersMapper.updateByPrimaryKey(users);
        cacheService.doReloadUsers();
    }

    @Override
    public void delete(Users users) {
        Users usersTmp = usersMapper.selectByPrimaryKey(users.getId());
        if (usersTmp.getUserName().equals(users.getUserName())) {
            SystemUtil.throwException(MsgEnum.LOGIN_10006.getCode());
        }
        usersMapper.deleteByPrimaryKey(users.getId());
        cacheService.doReloadUsers();
    }

    @Override
    public void changePwd(Users users) {
        users.setOldPassword(Md5.MD5(users.getOldPassword()));
        UsersCriteria criteria = new UsersCriteria();
        criteria.createCriteria().andUserNameEqualTo(users.getUserName()).andPasswordEqualTo(users.getOldPassword());
        List<Users> usersList = usersMapper.selectByExample(criteria);
        if (usersList == null || usersList.size() == 0) {
            SystemUtil.throwException(MsgEnum.LOGIN_10007.getCode());
        }

        Users usersTmp = usersList.get(0);
        usersTmp.setPassword(Md5.MD5(users.getNewPassword()));
        usersTmp.setUpdatedAt(new Date());
        usersMapper.updateByPrimaryKey(usersTmp);
        cacheService.doReloadUsers();
    }

    @Override
    public List<Map<String, Object>> usersChart(CommonVo commonVo, Locale locale) {
        String days = commonVo.getDays();
        if (days == null || "".equals(days)) {
            logger.info("Parameter 'days' has not been setted. Default value(" + CHART_DAYS + ") has been setted.");
            days = CHART_DAYS;
        }

        List<Date> dayList = new ArrayList<Date>();
        Date date = DateUtil.endOfDay(new Date());
        dayList.add(date);
        for (int i = 1; i < Integer.parseInt(days); i++) {
            date = DateUtil.beforeHours(date, 24L);
            dayList.add(date);
        }

        @SuppressWarnings("unchecked")
        List<Date> severalDate = (List<Date>) ListUtil.getSeveralData(dayList, Long.valueOf(commonVo.getPointNum()));
        Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
        Map<String, Object> lastMap;
        List<Map<String, Object>> list;
        Integer count;
        String type;
        UsersCriteria criteria;

        for (Date dateTmp : severalDate) {
            criteria = new UsersCriteria();
            criteria.createCriteria().andCreatedAtLessThanOrEqualTo(dateTmp);
            type = super.getMsg(MsgEnum.USER_ALL.getCode(), locale);

            if (map.containsKey(type)) {
                list = map.get(type);
            } else {
                list = new ArrayList<Map<String, Object>>();
                map.put(type, list);
            }
            lastMap = new HashMap<String, Object>();
            count = usersMapper.countByExample(criteria);
            lastMap.put("time", dateTmp);
            lastMap.put("count", count);
            list.add(lastMap);
        }

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapTmp;
        for (String userFlag : map.keySet()) {
            mapTmp = new HashMap<String, Object>();
            mapTmp.put("type", userFlag);
            mapTmp.put("datas", map.get(userFlag));
            result.add(mapTmp);
        }
        return result;
    }

    @Override
    public List<Users> queryAllUsers(UsersCriteria criteria) {
        List<Users> result = usersMapper.selectByExample(criteria);
        return result;
    }
}
