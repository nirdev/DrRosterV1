package com.example.android.drroster.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Nir on 4/17/2016.
 */
@Table(name = "Duties")
public class DutyTypeDB extends Model {


    @Column(name = "Type")
    public String type;

    public DutyTypeDB() {
    }

    public DutyTypeDB(String name) {
        this.type = name;
    }
}
