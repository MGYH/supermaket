package com.mgyh.supermaket.entity;



import org.hibernate.annotations.Cascade;

import javax.persistence.*;

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

    private String goodsNum;

    private String sellPrice;

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

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }
}
