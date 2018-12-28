/**
 * Copyright (C), 2015-2018, XXX有限公司
 */
package com.lxf.spider.listener;

import com.lxf.spider.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 * @author:      李晓福
 * @date:        2018/12/17 18:54
 * @description: 
 * @since:       1.0.0
 * @history:
 * 作者姓名       修改时间             版本号           描述
 * lxf           2018/12/17 18:54     1.0.0           创建
 * </pre>
 */

public class ContextListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        deleteFiles();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void deleteFiles() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String rootPath = HttpUtil.class.getResource("./../../../../../images/").getPath();
                File rooot = new File(rootPath);
                File[] files = rooot.listFiles();
                logger.info("delete files count={}", files.length);
                for (File file : files) {
                    file.delete();
                }
            }
        }, 1*1000, 45*60*1000);
    }
}
