package com.mgyh.supermaket.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_entryRecords")
public class EntryRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalMoney;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date operateDate;

    private String operatorName;

    private String operatorId;


    @OneToMany(targetEntity = EntryRecordsDetail.class,mappedBy="entryRecords")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<EntryRecordsDetail> detailList = new ArrayList<EntryRecordsDetail>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public List<EntryRecordsDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<EntryRecordsDetail> detailList) {
        this.detailList = detailList;
    }

}
