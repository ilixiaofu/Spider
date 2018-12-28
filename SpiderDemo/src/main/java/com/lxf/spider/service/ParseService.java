/**
 * Copyright (C), 2015-2018, XXX有限公司
 */
package com.lxf.spider.service;

import com.alibaba.fastjson.JSONObject;

/**
 * <pre>
 * @author:      李晓福
 * @date:        2018/12/18 20:54
 * @description: 
 * @since:       1.0.0
 * @history:
 * 作者姓名       修改时间             版本号           描述
 * lxf           2018/12/18 20:54     1.0.0           创建
 * </pre>
 */

public interface ParseService {

    JSONObject doParse(String url);
}