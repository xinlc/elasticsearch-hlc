package com.github.xinlc.eshlc.core.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ES 排序
 *
 * @author Richard
 * @since 2021-04-05
 */
public class EsSort {

    private List<IEsSort> orders;

    public EsSort() {
        orders = new ArrayList<>();
    }

    public EsSort(IEsSort... ods) {
        orders = new ArrayList<>();
        orders.addAll(Arrays.asList(ods));
    }

    public EsSort sort(IEsSort order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        return this;
    }

    public EsSort and(EsSort sort) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.addAll(sort.orders);
        return this;
    }

    public List<IEsSort> getOrders() {
        return orders;
    }

    public void setOrders(List<IEsSort> orders) {
        this.orders = orders;
    }

}
