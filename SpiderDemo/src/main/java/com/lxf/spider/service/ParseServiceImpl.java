/**
 * Copyright (C), 2015-2018, XXX有限公司
 */
package com.lxf.spider.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxf.spider.controller.SpiderController;
import com.lxf.spider.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import per.lxf.webmvc.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * <pre>
 * @author:      李晓福
 * @date:        2018/12/18 21:01
 * @description: 
 * @since:       1.0.0
 * @history:
 * 作者姓名       修改时间             版本号           描述
 * lxf           2018/12/18 21:01     1.0.0           创建
 * </pre>
 */

@Component
public class ParseServiceImpl implements ParseService {

    private Logger logger = LoggerFactory.getLogger(SpiderController.class);

    @Override
    public JSONObject doParse(String url) {
        logger.info("url={}", url);
        JSONObject data = new JSONObject(true);
        if (url.isEmpty()) {
            data.put("status", 404);
            data.put("msg", "请求参数为空");
        } else {
            JSONArray array = new JSONArray(0);
            if (url.matches("http://www\\.huya\\.com/g/2168")) {
                parseHuyaHtmlContent(array, url);
            } else if (url.matches("http://www\\.huya\\.com/cache\\.php")) {
                parseHuyaJsonContent(array, url, 1);
            } else if (url.matches("http://webh\\.huajiao\\.com/live/listcategory")) {
                parseHuajiaoJsonContent(array, url, 0);
            } else {
                data.put("status", 404);
                data.put("msg", "不能识别的请求参数");
                return data;
            }
            data.put("status", 200);
            data.put("msg", "OK");
            data.put("datas", array);
        }
        return data;
    }

    private void parseHuyaHtmlContent(JSONArray array, String url) {
        String content = HttpUtil.getString(url);
        Document document = Jsoup.parse(content);
        Elements images = document.select(".pic");
        Elements name = document.select(".nick");
        Elements watche = document.select(".js-num");
        for (int i = 0; i < images.size(); i++) {
            String nickname = name.get(i).text();
            String watches = watche.get(i).text();
            String imageName = UUID.randomUUID().toString() + ".jpg";
            Map<String, String> dataOriginal = images.get(i).dataset();
            String imageURL = dataOriginal.get("original");
            if (imageURL.startsWith("//")) {
                imageURL = "https:" + imageURL;
            }
            loadImages(imageName, imageURL);
            imageName = "downloadFile.do?fileName=" + imageName;
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("nickname", nickname);
            jsonObject.put("watches", watches);
            jsonObject.put("imageName", imageName);
            jsonObject.put("imageURL", imageURL);
            array.add(jsonObject);
        }
    }

    private void parseHuyaJsonContent(JSONArray array, String url, int page){
        url = url + "?m=LiveList&do=getLiveListByPage&gameId=2168&tagAll=0&page=" + page;
        String content = HttpUtil.getString(url);
        JSONObject json = JSONObject.parseObject(content);
        String status = json.getString("status");
        if ("200".equals(status)) {
            JSONObject data = json.getJSONObject("data");
            JSONArray datas = data.getJSONArray("datas");
            for (int i = 0; i < datas.size(); i++) {
                String nick = datas.getJSONObject(i).getString("nick");
                String imageURL = datas.getJSONObject(i).getString("screenshot");
                String watches = datas.getJSONObject(i).getString("totalCount");
                String imageName = UUID.randomUUID().toString() + ".jpg";
                loadImages(imageName, imageURL);
                imageName = "downloadFile.do?fileName=" + imageName;
                JSONObject jsonObject = new JSONObject(true);
                jsonObject.put("nickname", nick);
                jsonObject.put("watches", watches);
                jsonObject.put("imageName", imageName);
                jsonObject.put("imageURL", imageURL);
                array.add(jsonObject);
            }
            int totalPage = data.getIntValue("totalPage");
            page = data.getIntValue("page");
            if (page < totalPage) {
                page = page + 1;
                parseHuyaJsonContent(array, url, page);
            }
        }
    }

    private void parseHuajiaoJsonContent(JSONArray array, String url, int offset){
        url = url + "?_callback=jQuery110207887400644314504_1509778591679&cateid=" +
                "800&offset=" + offset + "&nums=100&fmt=json&_=" + System.currentTimeMillis();
        String content = HttpUtil.getString(url);
        JSONObject json = JSONObject.parseObject(content);
        String status = json.getString("errno");
        if ("0".equals(status)) {
            JSONObject data = json.getJSONObject("data");
            JSONArray feeds = data.getJSONArray("feeds");
            for (int i = 0; i < feeds.size(); i++) {
                JSONObject feed = feeds.getJSONObject(i).getJSONObject("feed");
                JSONObject author = feeds.getJSONObject(i).getJSONObject("author");
                String nickname = author.getString("nickname");
                String watches = feed.getString("watches");
                String imageName = UUID.randomUUID().toString() + ".jpg";
                String imageURL = feed.getString("image");
                loadImages(imageName, imageURL);
                imageName = "downloadFile.do?fileName=" + imageName;
                JSONObject jsonObject = new JSONObject(true);
                jsonObject.put("nickname", nickname);
                jsonObject.put("watches", watches);
                jsonObject.put("imageName", imageName);
                jsonObject.put("imageURL", imageURL);
                array.add(jsonObject);
            }
            int more = data.getIntValue("more");
            if (1 == more) {
                offset = offset + 100;
                parseHuajiaoJsonContent(array, url, offset);
            }
        }
    }

    private void loadImages(String imageName, String imageUrl) {
        HttpUtil.getBytes(imageUrl, imageName);
    }
}
