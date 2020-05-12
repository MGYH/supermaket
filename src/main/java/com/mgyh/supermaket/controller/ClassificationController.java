package com.mgyh.supermaket.controller;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.Classification;
import com.mgyh.supermaket.service.ClassificationService;
import com.mgyh.supermaket.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author MGYH
 */
@RestController
@CrossOrigin
public class ClassificationController {
    @Autowired
    public ClassificationService classificationService;

    @PostMapping("/classification/findAll")
    @ResponseBody
    public Result findAll(@RequestBody JSONObject object,int pageNo,int pageSize){
        return new Result(classificationService.getList(object,pageNo,pageSize));
    }

    @PostMapping("/classification/save")
    @ResponseBody
    public Object save(@RequestBody JSONObject object){
        System.out.println(object.toJSONString());
        classificationService.save(object.getObject("classification", Classification.class));
        return new Result();
    }

    @GetMapping("/classification/getOption")
    public Result getOption(){
        return new Result(classificationService.getAll());
    }


}
