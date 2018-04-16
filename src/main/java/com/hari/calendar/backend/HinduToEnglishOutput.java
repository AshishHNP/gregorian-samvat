// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 4/15/2018 10:55:47 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   HinduToEnglishOutput.java

package com.hari.calendar.backend;

import calendrica.Gregorian;
import com.hari.calendar.overwrites.HinduLunar;
import com.hari.calendar.overwrites.OldHinduLunar;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class HinduToEnglishOutput
{

    public HinduToEnglishOutput(long year, String month, String paksh, int tithi)
    {
        Integer inputTithi = new Integer(tithi);
        if(paksh.equals("Vad"))
            inputTithi = Integer.valueOf(inputTithi.intValue() + 15);
        int hinduNumericalMonth = calculateMonth(month);
        if(hinduNumericalMonth < 8)
            year++;
        Gregorian convertedTarikh = new Gregorian((new HinduLunar(year, hinduNumericalMonth, false, inputTithi.intValue(), false)).toFixed());
        System.out.println(convertedTarikh.format());
    }

    private int calculateMonth(String month)
    {
        return Arrays.asList(OldHinduLunar.monthNames).indexOf(month) + 1;
    }

    public static void main(String args[])
    {
        new HinduToEnglishOutput(2072L, "Jeth", "Vad", 11);
    }

    public static final String samvatMonths[] = {
        "Kartak", "Magshar", "Posh", "Maha", "Faagan", "Chaitra", "Vaishakh", "Jeth", "Ashadha", "Shravan", 
        "Bhadarvaa", "Aaso"
    };

}
