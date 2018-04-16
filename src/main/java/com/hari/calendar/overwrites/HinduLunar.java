// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov Date: 4/15/2018 10:52:42 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html http://www.neshkov.com/dj.html - Check often for new
// version!
// Decompiler options: packimports(3)
// Source File Name: HinduLunar.java

package com.hari.calendar.overwrites;

import java.text.MessageFormat;

import calendrica.Date;
import calendrica.FixedVector;
import calendrica.Gregorian;
import calendrica.HinduSolar;
import calendrica.OldHinduSolar;

// Referenced classes of package com.hari.calendar.overwrites:
// OldHinduLunar

public class HinduLunar extends Date {

    public HinduLunar() {}

    public HinduLunar(long date) {
        super(date);
    }

    public HinduLunar(Date date) {
        super(date);
    }

    public HinduLunar(long year, int month, boolean leapMonth, int day, boolean leapDay) {
        this.year = year;
        this.month = month;
        this.leapMonth = leapMonth;
        this.day = day;
        this.leapDay = leapDay;
    }

    public static long toFixed(long year, int month, boolean leapMonth, int day, boolean leapDay) {
        return (new HinduLunar(year, month, leapMonth, day, leapDay)).toFixed();
    }

    public long toFixed() {
        double approx = (double)OldHinduSolar.EPOCH
                + 365.2587564814815D * ((double)(year + 3044L) + (double)(month - 1) / 12D);
        long s = (long)Math.floor(approx - (1.0D / deg(360D)) * 365.2587564814815D
                * (mod((HinduSolar.solarLongitude(approx) - (double)(month - 1) * deg(30D)) + deg(180D), deg(360D))
                        - 180D));
        int k = lunarDay((double)s + 0.25D);
        long x;
        if (3 < k && k < 27) {
            x = k;
        } else {
            HinduLunar mid = new HinduLunar(s - 15L);
            if (mid.month < month || mid.leapMonth && !leapMonth) x = mod(k + 15, 30) - 15;
            else x = mod(k - 15, 30) + 15;
        }
        long est = (s + (long)day) - x;
        long tau = (est - (long)mod((lunarDay((double)est + 0.25D) - day) + 15, 30)) + 15L;
        long date;
        for (date = tau - 1L; !onOrBefore(this, new HinduLunar(date)); date++)
            ;
        return date;
    }

    public void fromFixed(long date) {
        double critical = HinduSolar.sunrise(date);
        day = lunarDay(critical);
        leapDay = day == lunarDay(HinduSolar.sunrise(date - 1L));
        double lastNewMoon = newMoonBefore(critical);
        double nextNewMoon = newMoonBefore(Math.floor(lastNewMoon) + 35D);
        int solarMonth = HinduSolar.zodiac(lastNewMoon);
        leapMonth = solarMonth == HinduSolar.zodiac(nextNewMoon);
        month = adjustedMod(solarMonth + 1, 12);
        year = HinduSolar.calendarYear(nextNewMoon) - 3044L - (long)(leapMonth && month == 1 ? -1 : 0);
    }

    public void fromArray(int a[]) {
        year = a[0];
        month = a[1];
        leapMonth = a[2] != 0;
        day = a[3];
        leapDay = a[4] != 0;
    }

    public static double newMoonBefore(double tee) {
        double varepsilon = Math.pow(2D, -34D);
        double tau = tee - (1.0D / deg(360D)) * lunarPhase(tee) * 29.530587946071719D;
        double l = tau - 1.0D;
        double u = Math.min(tee, tau + 1.0D);
        double lo = l;
        double hi = u;
        double x;
        for (x = (hi + lo) / 2D; HinduSolar.zodiac(lo) != HinduSolar.zodiac(hi)
                && hi - lo >= varepsilon; x = (hi + lo) / 2D)
            if (lunarPhase(x) < deg(180D)) hi = x;
            else lo = x;

        return x;
    }

    public static boolean onOrBefore(HinduLunar d1, HinduLunar d2) {
        return d1.year < d2.year || d1.year == d2.year && (d1.month < d2.month
                || d1.month == d2.month && (d1.leapMonth && !d2.leapMonth || d1.leapMonth == d2.leapMonth
                        && (d1.day < d2.day || d1.day == d2.day && (!d1.leapDay || d2.leapDay))));
    }

    public static double lunarDayAfter(double tee, double k) {
        double varepsilon = Math.pow(2D, -17D);
        double phase = (k - 1.0D) * 12D;
        double tau = tee + 0.0027777777777777779D * mod(phase - lunarPhase(tee), deg(360D)) * 29.530587946071719D;
        double l = Math.max(tee, tau - 2D);
        double u = tau + 2D;
        double lo = l;
        double hi = u;
        double x;
        for (x = (hi + lo) / 2D; hi - lo >= varepsilon; x = (hi + lo) / 2D)
            if (mod(lunarPhase(x) - phase, 360D) < deg(180D)) hi = x;
            else lo = x;

        return x;
    }

    public static double lunarLongitude(double tee) {
        return HinduSolar.truePosition(tee, 27.321674162683866D, 0.088888888888888892D, 27.554597974680476D,
                0.010416666666666666D);
    }

    public static double lunarPhase(double tee) {
        return mod(lunarLongitude(tee) - HinduSolar.solarLongitude(tee), 360D);
    }

    public static int lunarDay(double tee) {
        return (int)quotient(lunarPhase(tee), deg(12D)) + 1;
    }

    public static int lunarStation(long date) {
        double critical = HinduSolar.sunrise(date);
        return (int)quotient(lunarLongitude(critical), deg(800D) / 60D) + 1;
    }

    public static long newYear(long gYear) {
        long jan1 = Gregorian.toFixed(gYear, 1, 1);
        double mina = HinduSolar.solarLongitudeAfter(jan1, deg(330D));
        double newMoon = lunarDayAfter(mina, 1.0D);
        long hDay = (long)Math.floor(newMoon);
        double critical = HinduSolar.sunrise(hDay);
        return hDay + (long)(newMoon < critical || lunarDay(HinduSolar.sunrise(hDay + 1L)) == 2 ? 0 : 1);
    }

    public static int karana(int n) {
        if (n == 1) return 0;
        if (n > 57) return n - 50;
        else return adjustedMod(n - 1, 7);
    }

    public static int yoga(long date) {
        return (int)Math.floor(mod(((HinduSolar.solarLongitude(date) + lunarLongitude(date)) * 60D) / 800D, deg(27D)))
                + 1;
    }

    public static FixedVector sacredWednesdaysInGregorian(long gYear) {
        return sacredWednesdays(Gregorian.toFixed(gYear, 1, 1), Gregorian.toFixed(gYear, 12, 31));
    }

    public static FixedVector sacredWednesdays(long start, long end) {
        long wed = kDayOnOrAfter(start, 3);
        FixedVector result = new FixedVector();
        for (; wed <= end; wed += 7L) {
            HinduLunar hDate = new HinduLunar(wed);
            if (hDate.day == 8) result.addFixed(wed);
        }

        return result;
    }

    protected String toStringFields() {
        return (new StringBuilder("year=")).append(year).append(",month=").append(month).append(",leapMonth=")
                .append(leapMonth).append(",day=").append(day).append(",leapDay=").append(leapDay).toString();
    }

    public String format() {
        return MessageFormat.format("{0}, {1}{2} {3}{4} {5,number,#} V.E.",
                new Object[] { nameFromDayOfWeek(toFixed(), OldHinduLunar.dayOfWeekNames), new Integer(day),
                        leapDay ? " II" : "", nameFromMonth(month, OldHinduLunar.monthNames), leapMonth ? " II" : "",
                        new Long(year) });
    }

    public String formattedTithi() {
        String paksh = "\u0AB8\u0AC1\u0AA6";
        Integer tithi = new Integer(day);
        if (tithi.intValue() > 15) {
            paksh = "\u0AB5\u0AA6";
            if (tithi.intValue() != 30) tithi = Integer.valueOf(tithi.intValue() % 15);
        }
        return MessageFormat.format("{0}, {1}{2} {3} {4}{5} {6,number,#} V.S.",
                new Object[] { nameFromDayOfWeek(toFixed(), OldHinduLunar.gujaratiWeekNames),
                        nameFromMonth(month, OldHinduLunar.gujaratiMonthNames), leapMonth ? " II" : "", paksh, tithi,
                        leapDay ? " II" : "", month >= 8 ? new Long(year) : new Long(year - 1L) });
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HinduLunar)) return false;
        HinduLunar o = (HinduLunar)obj;
        return o.year == year && o.month == month && o.leapMonth == leapMonth && o.day == day && o.leapDay == leapDay;
    }

    public long year;
    public int month;
    public boolean leapMonth;
    public int day;
    public boolean leapDay;
    public static final int LUNAR_ERA = 3044;
    public static final double SYNODIC_MONTH = 29.530587946071719D;
    public static final double SIDEREAL_MONTH = 27.321674162683866D;
    public static final double ANOMALISTIC_MONTH = 27.554597974680476D;
}
