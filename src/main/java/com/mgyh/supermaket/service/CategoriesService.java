package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.Categories;
import com.mgyh.supermaket.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MGYH
 * @date 2020/3/31
 */
@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    public JSONArray getAll(){
        List<Categories> categoriesList = categoriesRepository.findAll();
        //parentId为0，为第一级
        return getChildren(0,categoriesList);
    }
    public JSONArray getChildren(int parentId,List<Categories> categoriesList){
        Map<String, Object> map;
        JSONObject object;
        JSONArray array = new JSONArray();
        for(Categories categories : categoriesList){
            if(categories.getParentId() == parentId){
                map = new HashMap<>();
                map.put("label",categories.getName());
                map.put("value",categories.getId());
                map.put("children",getChildren(categories.getId(),categoriesList));
                object = new JSONObject(map);
                array.add(object);
            }
        }
        return array;
    }
}
