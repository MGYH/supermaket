package com.mgyh.supermaket.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @author MGYH
 * @date 2020/3/31
 */
@Entity
@Table(name = "t_categories")
public class Categories{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ColumnDefault("0")
    private int parentId;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
