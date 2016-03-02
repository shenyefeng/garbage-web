package me.anchora.garbage.controller;

import static me.anchora.garbage.Constants.DEFAULT_TIMEONE;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.Users;
import me.anchora.garbage.entry.UsersCriteria;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.UsersService;
import me.anchora.garbage.utils.ConfigUtil;
import me.anchora.garbage.utils.CriteriaUtil;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.IpUtils;
import me.anchora.garbage.utils.Md5;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;
import me.anchora.garbage.utils.SystemUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController extends BaseController {
    private static Logger logger = Logger.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/loginAjax.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void loginAjax(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Users users = (Users) EntryUtil.getObject(request, Users.class);
            if (users.getUserName() == null || "".equals(users.getUserName())) {
                logger.info(MsgEnum.LOGIN_10001.getDescription());
                SystemUtil.throwException(MsgEnum.LOGIN_10001.getCode());
            }
            if (users.getPassword() == null || "".equals(users.getPassword())) {
                logger.info(MsgEnum.LOGIN_10002.getDescription());
                SystemUtil.throwException(MsgEnum.LOGIN_10002.getCode());
            }
            if (users.getValidationCode() == null || "".equals(users.getValidationCode())) {
                logger.info(MsgEnum.LOGIN_10003.getDescription());
                SystemUtil.throwException(MsgEnum.LOGIN_10003.getCode());
            } else if (!users.getValidationCode().equalsIgnoreCase((String) request.getSession().getAttribute("validationCode"))) {
                // if(!"garbagemgr".equals(users.getValidationCode())) {
                logger.info(MsgEnum.LOGIN_10004.getDescription());
                SystemUtil.throwException(MsgEnum.LOGIN_10004.getCode());
                // }
            }

            users.setPassword(Md5.MD5(users.getPassword()));

            List<Users> usersList = usersService.login(users);

            if (usersList == null || usersList.size() == 0) {
                logger.info(MsgEnum.LOGIN_10005.getDescription());
                SystemUtil.throwException(MsgEnum.LOGIN_10005.getCode());
            } else {
                Users usersReturn = usersList.get(0);
                if(!"Y".equals(usersReturn.getIsAdmin())) {
                    logger.info(MsgEnum.LOGIN_10008.getDescription());
                    SystemUtil.throwException(MsgEnum.LOGIN_10008.getCode());
                }
                usersReturn.setLastLogin(usersReturn.getCurrentLogin());
                usersReturn.setCurrentLogin(new Date());
                usersReturn.setUpdatedAt(usersReturn.getCurrentLogin());
                usersReturn.setLastLoginIp(usersReturn.getCurrentLoginIp());
                usersReturn.setCurrentLoginIp(IpUtils.getRemoteIpAddr(request));
                usersService.updateLastLogin(usersReturn);

                logger.info(users.getUserName() + " Users Success!");
                
                if(usersReturn.getTimezone() == null || usersReturn.getTimezone().length() == 0) {
                    String timezone = ConfigUtil.getConfig(cacheService, "timezone");
                    if(timezone == null || timezone.length() == 0) {
                        logger.info("timezone has not bean configured! Default value " + DEFAULT_TIMEONE + " has been setted.");
                        timezone = DEFAULT_TIMEONE;
                    }
                    usersReturn.setTimezone(timezone);
                }
                request.getSession().setAttribute("errorInfo", null);
                request.getSession().setAttribute("users", usersReturn);
                request.getSession().setAttribute("userName", usersReturn.getUserName());
                request.getSession().setAttribute("userId", usersReturn.getId());
                request.getSession().setAttribute("timezone", usersReturn.getTimezone());
            }
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/logout.html", method = { RequestMethod.POST, RequestMethod.GET })
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        result = new PaasResult();
        String returnUrl = "login.jsp";
        try {
            logger.info(request.getSession().getAttribute("userName") + " Logout Success!");
            request.getSession().setAttribute("errorInfo", null);
            request.getSession().setAttribute("users", null);
            request.getSession().setAttribute("userName", null);
            request.getSession().setAttribute("userId", null);
            request.getSession().setAttribute("timezone", null);
            returnUrl = "login.jsp";
        } catch (Exception e) {
            super.exception(request, e);
        }

        return returnUrl;
    }

    @RequestMapping(value = "/admin/getAllUsersByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllUsersByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Users users = (Users) EntryUtil.getObject(request, Users.class);
            UsersCriteria criteria = (UsersCriteria)CriteriaUtil.createCriteria(users, UsersCriteria.class);
            
            PageTool.pageSetting(users, cacheService);
            List<Users> resultList = usersService.queryUsersByPage(criteria, users.getRowBounds());
            Integer count = usersService.queryCount(criteria);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, users.getPageSize()));
            result.addCurrentPage(users.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/getAllUsers.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Users users = (Users) EntryUtil.getObject(request, Users.class);
            UsersCriteria criteria = (UsersCriteria)CriteriaUtil.createCriteria(users, UsersCriteria.class);

            List<Users> resultList = usersService.queryAllUsers(criteria);

            result.addList(resultList, getTimezone(request));
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
    
    @RequestMapping(value = "/admin/createUsers.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createUsers(HttpServletRequest request, HttpServletResponse response) {
        result = new PaasResult();
        try {
            Users users = (Users) EntryUtil.getObject(request, Users.class);
            usersService.insert(users);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateUsers.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void update(HttpServletRequest request, HttpServletResponse response) {
        result = new PaasResult();
        try {
            Users users = (Users) EntryUtil.getObject(request, Users.class);
            usersService.update(users);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteUsers.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        result = new PaasResult();
        try {
            Users users = (Users) EntryUtil.getObject(request, Users.class);
            users.setUserName((String) request.getSession().getAttribute("userName"));
            usersService.delete(users);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/changePwd.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void changePwd(HttpServletRequest request, HttpServletResponse response) {
        result = new PaasResult();
        try {
            Users users = (Users) EntryUtil.getObject(request, Users.class);
            users.setUserName((String) request.getSession().getAttribute("userName"));
            usersService.changePwd(users);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
