package com.home.zubrin.zbodytemp.Model;

import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Person {

    private UUID mId;
    private String mName;
    private Integer mAge;
    private Card mCard;

    // Constructors

    public Person(String name, Integer age) {
        mId = UUID.randomUUID();
        mCard = new Card();

        mName = name;
        mAge = age;
    }

    // Public setters and getters

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Integer getAge() {
        return mAge;
    }

    public void setAge(Integer age) {
        mAge = age;
    }


}
