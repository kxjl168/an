/**
 * @(#)MobileMsgController.java 2019-01-24
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.AppController;

import com.github.pagehelper.Page;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.PageUtil;
import com.kxjl.base.util.StringUtil;
import com.kxjl.tasktransferplat.pojo.Message;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.service.MessageService;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 消息
 *
 * @author zj
 * @version 1.0.1 2019年1月24日
 * @revision zj 2019年1月24日
 * @since 1.0.1
 */
@Controller
@RequestMapping("/interface/message")
@PropertySource("classpath:project.properties")
public class MobileMsgController extends AppBaseController {

    @Value("${login.wxappid}")
    private String appid;

    @Value("${login.wxsecret}")
    private String secret;

    @Autowired
    private MessageService msgService;

    private Logger logger = Logger.getLogger(MobileMsgController.class);

    /**
     * 消息列表
     *
     * @param request
     * @param response
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public AppResult list(HttpServletRequest request, HttpServletResponse response) {
        try {

            Userinfo user = TokenUtil.getCurrentUser();

            int pageSize = parseIntegerParam(request, "pageSize");
            int pageNum = parseIntegerParam(request, "pageNum");
            PageCondition pagec = new PageCondition(pageNum, pageSize);

            Message query = new Message();
            query.setReceiver(user.getId());

            Page page = PageUtil.getPage(pagec);
            List<Message> msgs = msgService.selectMessageList(query);

            HashMap mp = new HashMap<>();
            mp.put("data", msgs);
            mp.put("current", page.getPageNum());
            mp.put("size", page.getPageSize());
            mp.put("pages", page.getPages());
            mp.put("total", page.getTotal());
//			mp.put("pageinfo", new PageCondition(page.getPageNum(),page.getPageSize(),page.getPages(),page.getTotal()));

            return AppResultUtil.success(mp);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AppResultUtil.fail();
        }
    }

    /**
     * 设置消息为已读
     *
     * @param request
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    @ResponseBody
    @RequestMapping(value = "/setRead", method = RequestMethod.POST)
    public AppResult setRead(HttpServletRequest request) {
        String messagId = parseStringParam(request, "messageId");
        if (StringUtil.isNull(messagId)) {
            return AppResultUtil.fail();
        }
        try {
            Message message = new Message();
            message.setMessageId(Long.parseLong(messagId));
            message.setHasRead(1);
            msgService.updateMessage(message);
            return AppResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultUtil.fail();
        }

    }

    /**
     * 已读消息
     *
     * @param request
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    @ResponseBody
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public AppResult read(HttpServletRequest request) {

        Long messagId = parseLongParam(request, "messageId");

        if (messagId == 0) {
            return AppResultUtil.fail();
        }
        try {

            Message msg = msgService.selectMessageById(messagId);
            msg.setHasRead(1);
            msgService.updateMessage(msg);

            return AppResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultUtil.fail();
        }

    }

    /**
     * 未读消息数量
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public AppResult count(HttpServletRequest request) {

        Userinfo user = TokenUtil.getCurrentUser();

        try {
            int count = msgService.selectUnreadMessageByCount(user.getId());
            HashMap mp = new HashMap<>();
            mp.put("count", count);
            return AppResultUtil.success(mp);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultUtil.fail();
        }

    }
}
