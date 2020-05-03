package com.mgyh.supermaket.repository;

import com.mgyh.supermaket.entity.Classification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MGYH
 * @date 2020/3/31
 */
@Repository
public interface ClassificationRepository extends CrudRepository<Classification,Integer> {
    List<Classification> findAll();
}
