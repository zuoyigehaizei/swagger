package com.example.demoswagger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @PostMapping
    public void test01() {
        //获取access_token
        String access_token = "";
        //获取数据库表名 tableName,数据库数据并转化为jsonObject类型
        String tableName = "";
        //数据库数据:自己数据查
        List<Object> list = new ArrayList<>();
        String s = JSON.toJSONString(list);
        JSONObject jsonObject = JSON.parseObject(s);
        //头信息
        Map<String, String> map = new HashMap<>();
        map.put("Authorization:Bearer", access_token);
        //param  name1=value1&name2=value2
        String param = "tableName=" + tableName + "&" + "jsonObject=" + jsonObject;
        //调用第三方接口，获取返回值
//        HttpSslUtil.sendPost("请求url",map,param,true);
        //判断返回值状态吗，200正常获取到
    }
}
