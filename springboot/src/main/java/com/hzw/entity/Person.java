package com.hzw.entity;

import lombok.Data;

import java.util.List;
@Data
public class Person {
    private String name;  
    private int age;  
    private String imageUrl;  
    private List<Person> children;
}