package com.mgyh.supermaket.entity;



import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="t_entryRecordsDetail")
public class EntryRecordsDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailId;

    @ManyToOne(targetEntity = EntryRecords.class)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "recordId")
    private EntryRecords entryRecords;

    private String goodsCode;

    private String goodsName;

    private String goodsNum;

    @Column(precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date manufactureDate;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int id) {
        this.detailId = id;
    }

    public EntryRecords getEntryRecords() {
        return entryRecords;
    }

    public void setEntryRecords(EntryRecords entryRecords) {
        this.entryRecords = entryRecords;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
}
