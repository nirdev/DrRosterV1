package com.example.android.drroster.models;

import android.util.Log;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/5/2016.
 */
@Parcel
public class ShiftFull {

    Long id;
    String name;
    Boolean isFirstCall = false;
    Boolean isSecondCall = false;
    Boolean isThirdCall = false;
    boolean isLeaveDate = false;
    List<Date> leaveDates = null;

    // empty constructor needed by the Parceler library
    public ShiftFull() {
    }

    public ShiftFull(Long id, String name, Boolean isFirstCall, Boolean isSecondCall, Boolean isThirdCall, Boolean isLeavDate, List<Date> leaveDates) {
        this.id = id;
        this.name = name;
        this.isFirstCall = isFirstCall;
        this.isSecondCall = isSecondCall;
        this.isThirdCall = isThirdCall;
        this.isLeaveDate = isLeavDate;
        this.leaveDates = leaveDates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsFirstCall() {
        return isFirstCall;
    }

    public void setIsFirstCall(Boolean isFirstCall) {
        this.isFirstCall = isFirstCall;
    }

    public Boolean getIsSecondCall() {
        return isSecondCall;
    }

    public void setIsSecondCall(Boolean isSecondCall) {
        this.isSecondCall = isSecondCall;
    }

    public Boolean getIsThirdCall() {
        return isThirdCall;
    }

    public void setIsThirdCall(Boolean isThirdCall) {
        this.isThirdCall = isThirdCall;
    }

    public boolean getIsLeavDate() {
        return isLeaveDate;
    }

    public void setIsLeavDate(boolean isLeavDate) {
        this.isLeaveDate = isLeavDate;
    }

    public List<Date> getLeaveDates() {
        return leaveDates;
    }

    public void setLeaveDates(List<Date> leaveDates) {
        this.leaveDates = leaveDates;
    }
    public void LogPerson(){
        Log.wtf(id+"", "name: " + name + " isFirst " + isFirstCall + " isSecond " + isSecondCall + " isthird: " + isThirdCall);
    }

}
