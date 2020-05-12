package com.mgyh.supermaket.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class ExcelUtil {

    public void noModleWrite(JSONObject head, List<HashMap<String,String>> data) {
        // 写法1
        String fileName =  "noModleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head(head)).sheet("模板").doWrite(dataList(head,data));
    }

    public void webDownload(JSONObject head, List<HashMap<String,String>> data, HttpServletResponse response) throws IOException {
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(response.getOutputStream()).head(head(head)).sheet("模板").doWrite(dataList(head,data));
    }

    private List<List<String>> head(JSONObject jsonObject) {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head = null;
        for (Map.Entry entry : jsonObject.entrySet()) {
            head = new ArrayList<String>();
            head.add(entry.getValue().toString());
            list.add(head);
        }
        return list;
    }

    private List<List<Object>> dataList(JSONObject head, List<HashMap<String,String>> data) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for(HashMap map : data){
            System.out.println(map);
            List<Object> h = new ArrayList<Object>();
            for(Map.Entry entry : head.entrySet()) {
                h.add(map.get(entry.getKey()));
            }
            list.add(h);
        }
        return list;
    }
}
