//package com.example.demoswagger.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.JSON;
//import com.ictcars.common.utils.HttpSslUtil;
//import com.ictcars.system.domain.*;
//import com.ictcars.system.mapper.*;
//import com.ictcars.system.service.SendDataService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 用户 业务层处理
// *
// * @author ictcars
// */
//@Service
//public class SendServiceImpl implements SendDataService{
//
//    @Autowired
//    private BTrainsetDictMapper bTrainsetDictMapper;
//
//    @Override
//    public String sendBTrainsetDict() {
//        String token = "b51a68ce-a08d-3d1a-9868-06a038040790";
//        //获取数据库list数据
//        List<BTrainsetDict> bTrainsetDicts = bTrainsetDictMapper.selectBTrainsetDictList(new BTrainsetDict());
//        //获取数据条数 :total
//        int total = bTrainsetDicts.size();
//        //数据库数据转json   :date
//        String s = JSON.toJSONString(bTrainsetDicts);
//        //获取表数据行的字段数   :fieldNum
//        int length = BTrainsetDict.class.getDeclaredFields().length;
//
//        //外层封装的大map
//        Map<String, String> dataMap = new HashMap<>();
//        dataMap.put("total", String.valueOf(total));
//        dataMap.put("fieldNum", String.valueOf(length));
//        dataMap.put("data", s);
//
//
//        //大map装换成  jsonObject类型
//        String jsonStr = JSON.toJSONString(dataMap);
//        JSONObject jsonObject = JSON.parseObject(jsonStr);
//
//        //头信息
//        Map<String, String> header = new HashMap<>();
//        header.put("Authorization:Bearer",token);
//        //拼接参数
//        String param = "tableName=" + tableName + "&" + "jsonObject=" + jsonObject;
//        //发送请求，获得响应信息
//        String responseStr = HttpSslUtil.sendPost("https://10.1.158.68:8243/emis/ v1/ access", header, param, true);
//        //如果状态码200
//        if (responseStr != null){
//            if ("状态码" == "200"){
//                return "success";
//            }else {
//                return "fail";
//            }
//
//        }
//        //否则失败
//        return null;
//    }
//}
