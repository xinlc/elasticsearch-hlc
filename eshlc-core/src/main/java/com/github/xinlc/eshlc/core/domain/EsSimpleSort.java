package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.OrderType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ES 简单排序
 *
 * @author Richard
 * @since 2021-04-01
 */
public class EsSimpleSort {

    private List<Order> orders;

    public EsSimpleSort() {
        orders = new ArrayList<>();
    }

    public EsSimpleSort(Order... ods) {
        orders = new ArrayList<>();
        orders.addAll(Arrays.asList(ods));
    }

    public EsSimpleSort sort(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        return this;
    }

    public EsSimpleSort and(EsSimpleSort sort) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.addAll(sort.orders);
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static class Order {
        private String fieldName;
        private OrderType orderType;

        public Order() {
        }

        public Order(String fieldName, OrderType orderType) {
            this.fieldName = fieldName;
            this.orderType = orderType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public OrderType getOrderType() {
            return orderType;
        }

        public void setOrderType(OrderType orderType) {
            this.orderType = orderType;
        }

        @Override
        public String toString() {
            return "Order{" +
                "fieldName='" + fieldName + '\'' +
                ", orderType=" + orderType +
                '}';
        }
    }
}
