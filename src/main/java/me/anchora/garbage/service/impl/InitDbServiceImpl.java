/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import me.anchora.garbage.dao.GarbageCansMapper;
import me.anchora.garbage.dao.GarbageStationsMapper;
import me.anchora.garbage.dao.GarbageTypesMapper;
import me.anchora.garbage.dao.GarbagesMapper;
import me.anchora.garbage.dao.GiftGrantsMapper;
import me.anchora.garbage.dao.GiftsMapper;
import me.anchora.garbage.dao.MenuActionsMapper;
import me.anchora.garbage.dao.MenuDetailsMapper;
import me.anchora.garbage.dao.MenuLanguagesMapper;
import me.anchora.garbage.dao.MenusMapper;
import me.anchora.garbage.dao.OperationLogsMapper;
import me.anchora.garbage.dao.QrCodesMapper;
import me.anchora.garbage.dao.RoleMenusMapper;
import me.anchora.garbage.dao.RoleUsersMapper;
import me.anchora.garbage.dao.RolesMapper;
import me.anchora.garbage.dao.ScoreDetailsMapper;
import me.anchora.garbage.dao.SystemConfigMapper;
import me.anchora.garbage.dao.UserMenusMapper;
import me.anchora.garbage.dao.UsersMapper;
import me.anchora.garbage.entry.UsersCriteria;
import me.anchora.garbage.service.InitDbService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "initDbService")
public class InitDbServiceImpl implements InitDbService {

    private static Logger logger = Logger.getLogger(InitDbServiceImpl.class);

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private MenuActionsMapper menuActionsMapper;

    @Autowired
    private MenuLanguagesMapper menuLanguagesMapper;

    @Autowired
    private MenuDetailsMapper menuDetailsMapper;

    @Autowired
    private GarbageCansMapper garbageCansMapper;

    @Autowired
    private GarbagesMapper garbagesMapper;

    @Autowired
    private GarbageStationsMapper garbageStationsMapper;

    @Autowired
    private GarbageTypesMapper garbageTypesMapper;

    @Autowired
    private GiftGrantsMapper giftGrantsMapper;

    @Autowired
    private GiftsMapper giftsMapper;

    @Autowired
    private QrCodesMapper qrCodesMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private OperationLogsMapper operationLogsMapper;

    @Autowired
    private ScoreDetailsMapper scoreDetailsMapper;
    
    @Autowired
    private RolesMapper rolesMapper;
    
    @Autowired
    private RoleUsersMapper roleUsersMapper;
    
    @Autowired
    private RoleMenusMapper roleMenusMapper;
    
    @Autowired
    private UserMenusMapper userMenusMapper;
	
    @Override
    public void initDb() {
        UsersCriteria usersCriteria = new UsersCriteria();
        usersCriteria.createCriteria();
        try {
            Integer count = usersMapper.countByExample(usersCriteria);
            if (count == null || count == 0) {
                initTables();
            }
        } catch (Exception e) {
            logger.info(e);
            initTables();
        }
    }

    private void initTables() {
        systemConfigMapper.createTable();
        systemConfigMapper.insertData();

        menusMapper.createTable();
        menusMapper.insertData();

        menuActionsMapper.createTable();
//        menuActionsMapper.insertData();

        menuLanguagesMapper.createTable();
        menuLanguagesMapper.insertData();

        menuDetailsMapper.createTable();
        menuDetailsMapper.insertData();

        garbageCansMapper.createTable();
        garbagesMapper.createTable();
        garbageStationsMapper.createTable();
        garbageTypesMapper.createTable();
        giftGrantsMapper.createTable();
        giftsMapper.createTable();
        qrCodesMapper.createTable();
        usersMapper.createTable();
        
        operationLogsMapper.createTable();
        scoreDetailsMapper.createTable();
        
//        garbageCansMapper.insertData();
//        garbagesMapper.insertData();
//        garbageStationsMapper.insertData();
//        garbageTypesMapper.insertData();
//        giftGrantsMapper.insertData();
//        giftsMapper.insertData();
//        qrCodesMapper.insertData();
        usersMapper.insertData();

        rolesMapper.createTable();
        rolesMapper.insertData();
        
        roleUsersMapper.createTable();
        roleUsersMapper.insertData();
        
        roleMenusMapper.createTable();
        roleMenusMapper.insertData();
        
        userMenusMapper.createTable();

    }
}
