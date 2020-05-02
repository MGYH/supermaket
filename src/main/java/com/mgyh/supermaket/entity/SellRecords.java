package com.mgyh.supermaket.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_sellRecords")
public class SellRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String totalMoney;

    private String change;

    private String paid;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateDate;

    private String operatorName;

    private String operatorId;

    private String payment;

    @OneToMany(targetEntity = SellRecordsDetail.class,mappedBy="sellRecords")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<SellRecordsDetail> detailList = new ArrayList<SellRecordsDetail>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
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

    public List<SellRecordsDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SellRecordsDetail> detailList) {
        this.detailList = detailList;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "SellRecords{" +
                "id=" + id +
                ", totalMoney='" + totalMoney + '\'' +
                ", operateDate=" + operateDate +
                ", operatorName='" + operatorName + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", detailList=" + detailList +
                '}';
    }
}
