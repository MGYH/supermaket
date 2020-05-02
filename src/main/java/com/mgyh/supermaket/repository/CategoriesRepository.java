package com.mgyh.supermaket.repository;

import com.mgyh.supermaket.entity.Categories;
import com.mgyh.supermaket.entity.Goods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MGYH
 * @date 2020/3/31
 */
@Repository
public interface CategoriesRepository extends CrudRepository<Categories,Integer> {
    List<Categories> findAll();
}
