// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov Date: 4/15/2018 10:56:11 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html http://www.neshkov.com/dj.html - Check often for new
// version!
// Decompiler options: packimports(3)
// Source File Name: OldHinduLunar.java

package com.hari.calendar.overwrites;

import java.text.MessageFormat;

import calendrica.Date;
import calendrica.OldHinduSolar;

public class OldHinduLunar extends Date {

    public OldHinduLunar() {}

    public OldHinduLunar(long date) {
        super(date);
    }

    public OldHinduLunar(Date date) {
        super(date);
    }

    public OldHinduLunar(long year, int month, boolean leapMonth, int day) {
        this.year = year;
        this.month = month;
        this.leapMonth = leapMonth;
        this.day = day;
    }

    public static long toFixed(long year, int month, boolean leapMonth, int day) {
        double mina = (double)(12L * year - 1L) * 30.43822337962963D;
        double lunarNewYear = 29.530581807581694D * (double)(quotient(mina, 29.530581807581694D) + 1L);
        return (long)Math.floor((double)OldHinduSolar.EPOCH + lunarNewYear
                + 29.530581807581694D * (double)(!leapMonth
                        && Math.ceil((lunarNewYear - mina) / 0.90764157204793605D) <= (double)month ? month : month - 1)
                + (double)(day - 1) * 0.9843527269193898D + 0.75D);
    }

    public long toFixed() {
        return toFixed(year, month, leapMonth, day);
    }

    public void fromFixed(long date) {
        double sun = (double)OldHinduSolar.dayCount(date) + 0.25D;
        double newMoon = sun - mod(sun, 29.530581807581694D);
        leapMonth = 0.90764157204793605D >= mod(newMoon, 30.43822337962963D) && mod(newMoon, 30.43822337962963D) > 0.0D;
        month = 1 + (int)mod(Math.ceil(newMoon / 30.43822337962963D), 12D);
        day = 1 + (int)mod(quotient(sun, 0.9843527269193898D), 30L);
        year = (long)Math.ceil((newMoon + 30.43822337962963D) / 365.25868055555554D) - 1L;
    }

    public void fromArray(int a[]) {
        year = a[0];
        month = a[1];
        leapMonth = a[2] != 0;
        day = a[3];
    }

    public static boolean isLeapYear(long lYear) {
        return mod((double)lYear * 365.25868055555554D - 30.43822337962963D,
                29.530581807581694D) >= 18.638882943006465D;
    }

    protected String toStringFields() {
        return (new StringBuilder("year=")).append(year).append(",month=").append(month).append(",leapMonth=")
                .append(leapMonth).append(",day=").append(day).toString();
    }

    public String format() {
        return MessageFormat.format("{0}, {1} {2}{3} {4,number,#} K.Y.",
                new Object[] { nameFromDayOfWeek(toFixed(), dayOfWeekNames), new Integer(day),
                        nameFromMonth(month, monthNames), leapMonth ? " II" : "", new Long(year) });
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof OldHinduLunar)) return false;
        OldHinduLunar o = (OldHinduLunar)obj;
        return o.year == year && o.month == month && o.leapMonth == leapMonth && o.day == day;
    }

    public long year;
    public int month;
    public boolean leapMonth;
    public int day;
    public static final double ARYA_LUNAR_MONTH = 29.530581807581694D;
    public static final double ARYA_LUNAR_DAY = 0.9843527269193898D;
    public static final String dayOfWeekNames[] = { "Ravivaar", "Somvaar", "Mangalvaar", "Budhvaar", "Guruvaar",
            "Shukrvaar", "Shanivaar" };
    public static final String gujaratiWeekNames[] = { "Ravivaar", "Somvaar", "Mangalvaar", "Budhvaar", "Guruvaar",
            "Shukrvaar", "Shanivaar" };

    // public static final String gujaratiWeekNames[] = { "રવિવાર", "સોમવાર", "મંગળવાર", "બુધવાર", "ગુરુવાર",
    // "શુક્રવાર",
    // "શનિવાર" };
    // public static final String gujaratiWeekNames[] = {
    // "\u0AB0\u0AB5\u0ABF\u0AB5\u0ABE\u0AB0", "\u0AB8\u0ACB\u0AAE\u0AB5\u0ABE\u0AB0",
    // "\u0AAE\u0A82\u0A97\u0AB3\u0AB5\u0ABE\u0AB0", "\u0AAC\u0AC1\u0AA7\u0AB5\u0ABE\u0AB0",
    // "\u0A97\u0AC1\u0AB0\u0AC1\u0AB5\u0ABE\u0AB0", "\u0AB6\u0AC1\u0A95\u0ACD\u0AB0\u0AB5\u0ABE\u0AB0",
    // "\u0AB6\u0AA8\u0ABF\u0AB5\u0ABE\u0AB0"
    // };
    public static final String monthNames[] = { "Chaitra", "Vaishakh", "Jeth", "Ashadha", "Shravan", "Bhadarvaa",
            "Aaso", "Kartak", "Magshar", "Posh", "Maha", "Faagan" };
    public static final String gujaratiMonthNames[] = { "Chaitra", "Vaishakh", "Jeth", "Ashadha", "Shravan",
            "Bhadarvaa", "Aaso", "Kartak", "Magshar", "Posh", "Maha", "Faagan" };

    // public static final String gujaratiMonthNames[] = { "ચૈત્ર", "વૈશાખ", "જેઠ", "અષાઢ", "શ્રાવણ", "ભાદરવો", "આસો",
    // "કારતક", "માગશર", "પોષ", "મહા", "ફાગણ" };
    // public static final String gujaratiMonthNames[] = { "\u0A9A\u0AC8\u0AA4\u0ACD\u0AB0",
    // "\u0AB5\u0AC8\u0AB6\u0ABE\u0A96", "\u0A9C\u0AC7\u0AA0", "\u0A85\u0AB7\u0ABE\u0AA2",
    // "\u0AB6\u0ACD\u0AB0\u0ABE\u0AB5\u0AA3", "\u0AAD\u0ABE\u0AA6\u0AB0\u0AB5\u0ACB", "\u0A86\u0AB8\u0ACB",
    // "\u0A95\u0ABE\u0AB0\u0AA4\u0A95", "\u0AAE\u0ABE\u0A97\u0AB6\u0AB0", "\u0AAA\u0ACB\u0AB7",
    // "\u0AAE\u0AB9\u0ABE", "\u0AAB\u0ABE\u0A97\u0AA3" };

}
