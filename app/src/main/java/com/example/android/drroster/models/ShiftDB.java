package com.example.android.drroster.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Nir on 4/26/2016.
 */

@Table(name = "Shifts")
public class ShiftDB extends Model{

    @Column(name = "day")
    public Date date;

    @Column(name = "firstCallPerson")
    public PersonDB firstCall;

    @Column(name = "secondCallPerson")
    public PersonDB secondCall;

    @Column(name = "thirdCallPerson")
    public PersonDB thirdCall;


    public ShiftDB() {
        //Used by ActiveAndroid
    }

    public ShiftDB(Date day, PersonDB firstCall, PersonDB secondCall, PersonDB thirdCall) {
        this.date = day;
        this.firstCall = firstCall;
        this.secondCall = secondCall;
        this.thirdCall = thirdCall;

    }

}
