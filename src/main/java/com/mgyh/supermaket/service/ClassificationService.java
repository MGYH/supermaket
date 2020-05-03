package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.Classification;
import com.mgyh.supermaket.entity.Goods;
import com.mgyh.supermaket.repository.ClassificationRepository;
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

    public List<Classification> getList(){
        return classificationRepository.findAll();
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
                map.put("value", classification.getId());
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
