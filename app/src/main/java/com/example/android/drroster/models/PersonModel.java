package com.example.android.drroster.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Nir on 4/16/2016.
 */

@Table(name = "People")
public class PersonModel extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Number")
    public int number;

    public PersonModel() {
        //Used by ActiveAndroid
    }


}
