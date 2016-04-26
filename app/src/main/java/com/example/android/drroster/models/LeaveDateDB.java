package com.example.android.drroster.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Nir on 4/26/2016.
 */
@Table(name = "LeaveDates")
public class LeaveDateDB extends Model {

    @Column(name = "Date")
    public Date date;

    @Column(name = "person")
    public PersonDB person;

    public LeaveDateDB() {
        //Used by ActiveAndroid
    }

    public LeaveDateDB(Date day, PersonDB person) {
        this.date = day;
        this.person = person;
    }
}
