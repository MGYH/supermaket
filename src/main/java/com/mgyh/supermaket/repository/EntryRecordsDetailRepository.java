package com.mgyh.supermaket.repository;

import com.mgyh.supermaket.entity.EntryRecordsDetail;
import com.mgyh.supermaket.entity.Goods;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EntryRecordsDetailRepository extends CrudRepository<EntryRecordsDetail,Integer> {
    /**
     * 更具入库条形码查询，入库的商品
     * @param entryCode
     * @return
     */
    List<EntryRecordsDetail> findByEntryCode(String entryCode);
}
