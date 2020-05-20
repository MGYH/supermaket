package com.mgyh.supermaket.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.service.EntryRecordService;
import com.mgyh.supermaket.util.BeanUtil;
import com.mgyh.supermaket.util.ExcelUtil;
import com.mgyh.supermaket.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@RestController
@CrossOrigin
public class ReportController {
    @Autowired
    private EntryRecordService entryRecordService;
    @Autowired
    private ExcelUtil excelUtil;

    /**
     *
     * @param object {from: {},exportObject :{}} form 查询参数， exportObject 导出列
     * @param response
     * @param methodName 导出获取数据的方法
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    @PostMapping("/export/excel")
    @ResponseBody
    public void export(@RequestBody JSONObject object,HttpServletResponse response, String methodName) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 获取参数
        JSONObject from = object.getJSONObject("form");
        // 响应头设置
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;");
        // 获取methodName 对应的类的全路径
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("exportExcel",locale );
        // 通过反射机制获取对应类
        Class c = Class.forName(bundle.getString(methodName+"_bean"));
        JSONObject exportObject = new JSONObject(8,true);
        exportObject = JSONObject.parseObject(bundle.getString(methodName+"_exportObject"));
        System.out.println(exportObject);
        // 获取对应方法
        Method method = c.getDeclaredMethod(methodName,JSONObject.class);
        // 通过bean工程获取的bean,执行方法
        JSONObject result = (JSONObject) method.invoke(BeanUtil.getBean(c), from);
        excelUtil.webDownload(exportObject, result.getObject("list",List.class),response);
    }
}
