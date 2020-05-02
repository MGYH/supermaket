package com.mgyh.supermaket.entity;

import javax.persistence.*;

@Entity
@Table(name="t_goodsmodel")
public class GoodsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private int goodsId;

    private String color;

    private String num;

    private String model;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsid(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
