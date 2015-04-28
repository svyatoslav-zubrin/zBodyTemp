package com.home.zubrin.zbodytemp.Model;

import java.util.ArrayList;

/**
 * Created by zubrin on 4/25/15.
 */
public class Persons {

    public static final Persons sharedInstance = new Persons();
    // TODO: correct singleton implementation (prevent creation of multiple instances from code)...

    private ArrayList<Person> mPersons;

    public ArrayList<Person> getPersons() {
        return mPersons;
    }

    public void addPerson(Person person) {
        mPersons.add(person);
    }

    public void removePerson(Person person) {
        mPersons.remove(person);
    }

    // DEBUG: custom constructor

    private Persons() {
        mPersons = new ArrayList<Person>();

        Person adam = new Person("Adam", 33);
        Person eve  = new Person("Eve", 22);

        mPersons.add(adam);
        mPersons.add(eve);
    }
}
