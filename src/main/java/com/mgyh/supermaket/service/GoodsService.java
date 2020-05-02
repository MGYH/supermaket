package com.mgyh.supermaket.service;

import com.mgyh.supermaket.entity.Goods;
import com.mgyh.supermaket.repository.GoodsRepository;
import com.mgyh.supermaket.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author MGYH
 * @date 2020/3/22
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SearchRepository searchRepository;

    public List<Goods> getGoodsByCode(String code){
        return goodsRepository.findByCode(code);
    }

    @Transactional
    public List<Goods> findAll(){
        return searchRepository.commonSearch();
    }

    @Transactional
    public void save(Goods goods){
        goodsRepository.save(goods);
    }
}
