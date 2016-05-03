package com.example.android.drroster.models;

import android.util.Pair;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Model Summary -
 * 4. Date of day - Date (maybe long)
 * 1. First call name - String
 * 2. Second call name - String
 * 3. Third call name - String
 * 5. ArrayList of Duty Task and name Pair<String,String> - F = DutyType, S = DutyDoer
 * 6. ArrayList of leave dates people ArrayList<String>
 * */
@Parcel
public class ItemMainView {

    Date day;
    String firstCallName;
    String SecondCallName;
    String thirdCallName;
    ArrayList<Pair<String,String>> dutyTypeDoerPairArray;
    ArrayList<String> leaveDatePeople;

    public ItemMainView() {
    }

    public ItemMainView(Date day, String firstCallName, String secondCallName, String thirdCallName, ArrayList<Pair<String, String>> dutyTypeDoerPairArray, ArrayList<String> leaveDatePeople) {
        this.day = day;
        this.firstCallName = firstCallName;
        SecondCallName = secondCallName;
        this.thirdCallName = thirdCallName;
        this.dutyTypeDoerPairArray = dutyTypeDoerPairArray;
        this.leaveDatePeople = leaveDatePeople;
    }

    public ItemMainView(Date day, String firstCallName, String secondCallName, ArrayList<Pair<String, String>> dutyTypeDoerPairArray, ArrayList<String> leaveDatePeople) {
        this.day = day;
        this.firstCallName = firstCallName;
        SecondCallName = secondCallName;
        this.dutyTypeDoerPairArray = dutyTypeDoerPairArray;
        this.leaveDatePeople = leaveDatePeople;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getFirstCallName() {
        return firstCallName;
    }

    public void setFirstCallName(String firstCallName) {
        this.firstCallName = firstCallName;
    }

    public String getSecondCallName() {
        return SecondCallName;
    }

    public void setSecondCallName(String secondCallName) {
        SecondCallName = secondCallName;
    }

    public String getThirdCallName() {
        return thirdCallName;
    }

    public void setThirdCallName(String thirdCallName) {
        this.thirdCallName = thirdCallName;
    }

    public ArrayList<Pair<String, String>> getDutyTypeDoerPairArray() {
        return dutyTypeDoerPairArray;
    }

    public void setDutyTypeDoerPairArray(ArrayList<Pair<String, String>> dutyTypeDoerPairArray) {
        this.dutyTypeDoerPairArray = dutyTypeDoerPairArray;
    }

//    public void setDutyDoer(String typeName,String doerName) {
//        for (Pair pair : this.dutyTypeDoerPairArray){
//           if (pair.first.equals(typeName)){
//               new
//               pair.second = doerName;
//           }
//
//        }
//        this.dutyTypeDoerPairArray = dutyTypeDoerPairArray;
//    }

    public ArrayList<String> getLeaveDatePeople() {
        return leaveDatePeople;
    }

    public void setLeaveDatePeople(ArrayList<String> leaveDatePeople) {
        this.leaveDatePeople = leaveDatePeople;
    }
}
