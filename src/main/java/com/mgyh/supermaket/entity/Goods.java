package com.mgyh.supermaket.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version
    private int version;

    private String code;

    private String name;

    @ColumnDefault("0")
    private String num;

    private String treeString;

    @Column(precision = 19, scale = 2)
    private BigDecimal purchasePrice;
    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    private int shelfLife;

    private String shelfLifeUnit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTreeString() {
        return treeString;
    }

    public void setTreeString(String treeString) {
        this.treeString = treeString;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getShelfLifeUnit() {
        return shelfLifeUnit;
    }

    public void setShelfLifeUnit(String shelfLifeUnit) {
        this.shelfLifeUnit = shelfLifeUnit;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
