package com.dylan.example;

import org.dylan.example.Name;

/**
 * Created by liufengquan on 2017/9/30.
 */
public class Hello {
    public static void main(String[] args) {
        Name name = new Name();
        System.out.format("Hello %s!%n", name.getName());
    }
}
