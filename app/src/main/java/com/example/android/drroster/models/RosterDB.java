package com.example.android.drroster.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Nir on 4/27/2016.
 */
@Table(name = "Rosters")
public class RosterDB extends Model{

    @Column(name = "Date")
    public Date date;

    public RosterDB() {}

    public RosterDB(Date roster) {
        this.date = roster;
    }
}
