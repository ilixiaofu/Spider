/**
 * Copyright (C), 2015-2018, XXX有限公司
 */
package com.lxf.spider.util;

import com.lxf.spider.controller.SpiderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * <pre>
 * @author:      李晓福
 * @date:        2018/12/18 21:05
 * @description: 
 * @since:       1.0.0
 * @history:
 * 作者姓名       修改时间             版本号           描述
 * lxf           2018/12/18 21:05     1.0.0           创建
 * </pre>
 */

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(SpiderController.class);

    public static String getString(String url) {
        InputStreamReader streamReader = null;
        StringBuffer stringBuffer = null;
        try {
            URL url1 = new URL(url);
            URLConnection connection = url1.openConnection();
            streamReader = new InputStreamReader(
                    connection.getInputStream(), "utf-8");
            BufferedReader reader = new BufferedReader(streamReader);

            stringBuffer = new StringBuffer(0);
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                stringBuffer.append(temp + "\n");
            }
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        } finally {
            try {
                streamReader.close();
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
        return stringBuffer.toString();
    }

    public static void getBytes(String imageUrl, String imageName) {
        String desPath = HttpUtil.class.getResource("./../../../../../images/").getPath();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(new File(desPath, imageName));
            int size;
            do {
                byte[] bytes = new byte[1024];
                size = inputStream.read(bytes);
                if (size != -1) {
                    outputStream.write(bytes, 0, size);
                }
            } while (size != -1);
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException", e);
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
