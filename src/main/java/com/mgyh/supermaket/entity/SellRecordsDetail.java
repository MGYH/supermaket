package com.mgyh.supermaket.entity;



import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="t_sellRecordsDetail")
public class SellRecordsDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailId;

    @ManyToOne(targetEntity = SellRecords.class)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "recordId")
    private SellRecords sellRecords;

    private String goodsCode;

    private String entryCode;

    private int goodsNum;

    private String goodsName;

    @Column(precision = 19, scale = 2)
    private BigDecimal sellPrice;

    @Column(precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int id) {
        this.detailId = id;
    }

    public SellRecords getSellRecords() {
        return sellRecords;
    }

    public void setSellRecords(SellRecords sellRecords) {
        this.sellRecords = sellRecords;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
