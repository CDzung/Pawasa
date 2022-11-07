package com.pawasa.ulti;

import com.pawasa.model.Order;
import com.pawasa.model.OrderStatus;

import java.util.HashSet;
import java.util.Set;

public class OrderSupport {
    public static Set<Order> getOrderByStatus(Set<Order> all, String status) {
        Set<Order> list_order = new HashSet<>();
        if (status == null || status.equals("All")) {
            list_order = all;
        } else if (status.equals("pending")) {
            for (Order i : all) {
                if (i.getOrderStatuses().size() == 1) {
                    for (OrderStatus o : i.getOrderStatuses()) {
                        if (o.getOrderStatus().equals("Chờ xác nhận")) {
                            list_order.add(i);
                        }
                    }
                }
            }
        } else if (status.equals("processing")) {
            for (Order i : all) {
                if (i.getOrderStatuses().size() == 2) {
                    for (OrderStatus o : i.getOrderStatuses()) {
                        if (o.getOrderStatus().equals("Đang giao")) {
                            list_order.add(i);
                        }
                    }
                }
            }
        } else if (status.equals("complete")) {
            for (Order i : all) {
                if (i.getOrderStatuses().size() == 3) {
                    for (OrderStatus o : i.getOrderStatuses()) {
                        if (o.getOrderStatus().equals("Đã giao hàng")) {
                            list_order.add(i);
                        }
                    }
                }
            }
        } else {
            for (Order i : all) {
                if (i.getOrderStatuses().size() == 3) {
                    for (OrderStatus o : i.getOrderStatuses()) {
                        if (o.getOrderStatus().equals("Đã hủy")) {
                            list_order.add(i);
                        }
                    }
                }
            }
        }
        return list_order;
    }
}
