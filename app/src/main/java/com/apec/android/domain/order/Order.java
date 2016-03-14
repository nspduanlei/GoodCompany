package com.apec.android.domain.order;

import com.apec.android.domain.transport.Deliveryman;
import com.apec.android.domain.user.Address;

import java.util.List;

/**
 * Created by duanlei on 2016/3/10.
 */
public class Order {
    private int id;
    private String orderNo;
    private double orderAmount;
    private int orderType;
    private Address addreRes;
    private List<ProRecord> processLogs;
    private Deliveryman deliverRes;
    private List<OrderItem> orderItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public Address getAddreRes() {
        return addreRes;
    }

    public void setAddreRes(Address addreRes) {
        this.addreRes = addreRes;
    }

    public List<ProRecord> getProcessLogs() {
        return processLogs;
    }

    public void setProcessLogs(List<ProRecord> processLogs) {
        this.processLogs = processLogs;
    }

    public Deliveryman getDeliverRes() {
        return deliverRes;
    }

    public void setDeliverRes(Deliveryman deliverRes) {
        this.deliverRes = deliverRes;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
