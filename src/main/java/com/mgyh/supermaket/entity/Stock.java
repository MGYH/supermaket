package com.mgyh.supermaket.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author MGYH
 * @date 2020/3/22
 */
@Entity
@Table(name = "t_stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int goodsId;

    private int goodsmodelId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date stockDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfManufacture;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsmodelId() {
        return goodsmodelId;
    }

    public void setGoodsmodelId(int goodsmodelId) {
        this.goodsmodelId = goodsmodelId;
    }

    public Date getStockDate() {
        return stockDate;
    }

    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
