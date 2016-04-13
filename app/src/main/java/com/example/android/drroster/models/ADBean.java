package com.example.android.drroster.models;

/**
 * Created by Nir on 4/13/2016.
 */
public class ADBean {

    String name;
    Boolean isChecked;

    public ADBean(String name) {
        this.name = name;
        this.isChecked = false;
    }

    public ADBean(String name, Boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
}
