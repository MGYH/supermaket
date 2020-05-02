package com.mgyh.supermaket.repository;

import com.mgyh.supermaket.entity.Goods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * @author MGYH
 * @date 2020/3/22
 */

@Repository
public interface GoodsRepository extends CrudRepository<Goods,Integer> {
    List<Goods> findAll();

    List<Goods> findByCode(String code);
}
