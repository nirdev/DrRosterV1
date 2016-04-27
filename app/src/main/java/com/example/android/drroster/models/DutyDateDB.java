package com.example.android.drroster.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Nir on 4/27/2016.
 */
@Table(name = "DutyDate")
public class DutyDateDB extends Model{

    @Column(name = "Date")
    public Date date;

    @Column(name = "DutyDoer")
    public String dutyDoer;

    @Column(name = "DutyType")
    public DutyTypeDB dutyType;

    public DutyDateDB() {}

    public DutyDateDB(Date date, DutyTypeDB dutyType) {
        this.dutyType = dutyType;
        this.date = date;
    }

    public DutyDateDB(Date date, DutyTypeDB dutyType, String dutyDoer) {
        this.dutyType = dutyType;
        this.dutyDoer = dutyDoer;
        this.date = date;
    }
}
