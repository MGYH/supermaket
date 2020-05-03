package com.mgyh.supermaket.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @author MGYH
 * @date 2020/3/31
 */
@Entity
@Table(name = "t_categories")
public class Classification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ColumnDefault("0")
    private int parentId;

    //层级树字符字符串
    private String treeString;

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

    public String getTreeString() {
        return treeString;
    }

    public void setTreeString(String treeString) {
        this.treeString = treeString;
    }
}
