/**
 * Copyright (C), 2015-2018, XXX有限公司
 */
package com.lxf.spider.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxf.spider.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import per.lxf.webmvc.stereotype.Controller;
import per.lxf.webmvc.stereotype.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <pre>
 * @author:      李晓福
 * @date:        2018/12/22 13:14
 * @description: 
 * @since:       1.0.0
 * @history:
 * 作者姓名       修改时间             版本号           描述
 * lxf           2018/12/22 13:14     1.0.0           创建
 * </pre>
 */

@Controller
public class FileDownloadController {

    private Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @RequestMapping(value = "downloadFile.do")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        String desPath = HttpUtil.class.getResource("./../../../../../images/").getPath();
        File file = new File(desPath + fileName);
        if (!file.exists()) {
            JSONObject result = new JSONObject(true);
            JSONObject data = new JSONObject(true);
            result.put("code", 404);
            data.put("msg", "文件不存在");
            result.put("data", data);
            try {
                response.getWriter().write(result.toJSONString());
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        } else {
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(file);
                outputStream = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int size;
                do {
                    size = inputStream.read(buffer);
                    if (size != -1) {
                        outputStream.write(buffer, 0, size);
                    }
                } while (size != -1);
            } catch (FileNotFoundException e) {
                logger.error("FileNotFoundException", e);
            } catch (IOException e) {
                logger.error("IOException", e);
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("IOException", e);
                }
            }
        }
    }
}