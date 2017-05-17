package com.home.wanyu.bean.waterEleRan;

import java.util.List;

/**
 * Created by liuhaidong on 2017/5/12.
 */

public class Items {
    private String itemNumber;

    private long paymentItemId;

    private long moneySum;

    private long money;

    private String time;

    private List<String> items;

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public long getPaymentItemId() {
        return paymentItemId;
    }

    public void setPaymentItemId(long paymentItemId) {
        this.paymentItemId = paymentItemId;
    }

    public long getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(long moneySum) {
        this.moneySum = moneySum;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
