package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.Classification;
import com.mgyh.supermaket.repository.ClassificationRepository;
import com.mgyh.supermaket.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MGYH
 * @date 2020/3/31
 */
@Service
public class ClassificationService {
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private SearchRepository searchRepository;

    public JSONObject getList(JSONObject object,int pageNo,int pageSize){
        String sql = "select c from Classification c where 1=1 ";
        if(object.containsKey("name")){
            sql+=" and name like :name";
            object.put("name","%"+object.getString("name")+"%");
        }
        if(object.containsKey("treeString")){
            sql+="treeString like :treeString";
            object.put("treeString","%"+object.getString("treeString")+"%");
        }
        return searchRepository.commonSearch(sql,object,pageNo,pageSize);
    }


    public JSONArray getAll(){
        List<Classification> classificationList = classificationRepository.findAll();
        //parentId为0，为第一级
        return getChildren(0, classificationList);
    }
    public JSONArray getChildren(int parentId,List<Classification> classificationList){
        Map<String, Object> map;
        JSONObject object;
        JSONArray array = new JSONArray();
        for(Classification classification : classificationList){
            if(classification.getParentId() == parentId){
                map = new HashMap<>();
                map.put("label", classification.getName());
                map.put("value", classification.getId()+"");
                if(getChildren(classification.getId(), classificationList).size() > 0) {
                    map.put("children", getChildren(classification.getId(), classificationList));
                }
                object = new JSONObject(map);
                array.add(object);
            }
        }
        return array;
    }

    @Transactional
    public void save(Classification classification){
        classificationRepository.save(classification);
    }
}
