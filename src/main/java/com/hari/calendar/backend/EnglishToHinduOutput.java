// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov Date: 4/15/2018 10:50:12 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html http://www.neshkov.com/dj.html - Check often for new
// version!
// Decompiler options: packimports(3)
// Source File Name: EnglishToHinduOutput.java

package com.hari.calendar.backend;

import java.util.Arrays;

import com.hari.calendar.overwrites.HinduLunar;

import calendrica.Gregorian;

public class EnglishToHinduOutput {

    private String converted;
    public static final String[] gregorianMonths = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December" };

    public static String getGregorianToHindu(EnglishToHinduOutput object) {
        return object.converted;
    }

    public EnglishToHinduOutput(String month, int day, long year) {
        HinduLunar convertedTithi = new HinduLunar((new Gregorian(year, calculateMonth(month), day)).toFixed());
        converted = convertedTithi.formattedTithi();
    }

    public String getConverted() {
        return converted;
    }

    private int calculateMonth(String month) {
        return Arrays.asList(gregorianMonths).indexOf(month) + 1;
    }

}
