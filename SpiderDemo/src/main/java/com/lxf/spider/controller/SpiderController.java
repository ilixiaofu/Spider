/**
 * Copyright (C), 2015-2018, XXX有限公司
 */
package com.lxf.spider.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxf.spider.service.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import per.lxf.webmvc.stereotype.Autowired;
import per.lxf.webmvc.stereotype.Controller;
import per.lxf.webmvc.stereotype.RequestMapping;
import per.lxf.webmvc.stereotype.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * @author:      李晓福
 * @date:        2018/12/18 20:42
 * @description: 
 * @since:       1.0.0
 * @history:
 * 作者姓名       修改时间             版本号           描述
 * lxf           2018/12/18 20:42     1.0.0           创建
 * </pre>
 */

@Controller
public class SpiderController {

    private Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @Autowired
    private ParseService parseService;

    @RequestMapping("spider.do")
    public String spider(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response) {
        logger.info("RemoteHost={}", request.getRemoteHost());
        logger.info("RemoteAddr={}", request.getRemoteAddr());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        JSONObject json = parseService.doParse(url);
        return json.toJSONString();
    }
}
