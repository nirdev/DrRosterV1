package com.example.android.drroster.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Nir on 4/17/2016.
 */
@Table(name = "Duties")
public class AdditionalDutyDB extends Model {

    @Column(name = "Date")
    public Date date;

    @Column(name = "Type")
    public String type;

    @Column(name = "Person")
    public String person;

    public AdditionalDutyDB() {
        //Used by ActiveAndroid
    }

    public AdditionalDutyDB(Date date, String type) {
        this.date = date;
        this.type = type;
    }

    public AdditionalDutyDB(Date date, String type, String person) {
        this.date = date;
        this.type = type;
        this.person = person;
    }
}
