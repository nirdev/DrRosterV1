package com.example.android.drroster.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Nir on 4/17/2016.
 */
@Table(name = "Duties")
public class AdditionalDutyModel extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Number")
    public int number;

    public AdditionalDutyModel () {
        //Used by ActiveAndroid
    }

}
