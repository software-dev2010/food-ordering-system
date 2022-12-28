package com.food.ordering.system.order.service.dataaccess.order.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoApp {
    public static void main(String[] args) {
        List<Integer> l1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        System.out.println(l1);
        l1.add(5);
        System.out.println(l1);
    }
}
