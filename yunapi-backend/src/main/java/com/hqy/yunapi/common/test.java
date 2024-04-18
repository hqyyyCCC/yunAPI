package com.hqy.yunapi.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class test {
    public static void func1(String s){
        System.out.println(s.length());
    }
    public static void main(String[] args) {
        String [] s  = new String[]{"a","b","c","d"};
//        Arrays.stream()
        
        Stream.of(s).forEach(test :: func1);
    }
}
