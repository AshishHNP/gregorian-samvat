package com.hari.calendar.model;

public class GregorianDate {

    GregorianMonth month;
    String day;
    String year;

    public GregorianDate() {}

    public GregorianMonth getMonth() {
        return month;
    }

    public void setMonth(GregorianMonth month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
