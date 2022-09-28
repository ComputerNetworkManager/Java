package me.cnm.impl.shared.utility.format;

import me.cnm.shared.utility.format.IFormatHandler;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FormatHandler implements IFormatHandler {

    // Date and Time
    private static final String[] SHORT_NAME = new String[]{"y", "mo", "d", "h", "m", "s"};
    private static final int[] TYPES = new int[]{Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND};


    // Binary prefixes for IEC
    private static final long KIBI = 1L << 10;
    private static final long MEBI = 1L << 20;
    private static final long GIBI = 1L << 30;
    private static final long TEBI = 1L << 40;
    private static final long PEBI = 1L << 50;
    private static final long EXBI = 1L << 60;

    // Decimal prefixes for SI
    private static final long KILO = (long) Math.pow(10, 3);
    private static final long MEGA = (long) Math.pow(10, 6);
    private static final long GIGA = (long) Math.pow(10, 9);
    private static final long TERA = (long) Math.pow(10, 12);
    private static final long PETA = (long) Math.pow(10, 15);
    private static final long EXA = (long) Math.pow(10, 18);

    @Override
    public double formatDouble(double value) {
        return this.formatDouble(value, 2);
    }

    @Override
    public double formatDouble(double value, int decimals) {
        return new BigDecimal(value)
                .setScale(decimals, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    @NotNull
    public String formatBytes(long bytes) {
        if (bytes == 1) return String.format("%d byte", bytes);
        else if (bytes < KIBI) return String.format("%d bytes", bytes);
        else if (bytes < MEBI) return this.formatUnits(bytes, KIBI, "KiB");
        else if (bytes < GIBI) return this.formatUnits(bytes, MEBI, "MiB");
        else if (bytes < TEBI) return this.formatUnits(bytes, GIBI, "GiB");
        else if (bytes < PEBI) return this.formatUnits(bytes, TEBI, "TiB");
        else if (bytes < EXBI) return this.formatUnits(bytes, PEBI, "PiB");
        else return this.formatUnits(bytes, EXBI, "EiB");
    }

    @Override
    @NotNull
    public String formatBytesDecimal(long bytes) {
        if (bytes == 1) return String.format("%d byte", bytes);
        else if (bytes < KILO) return String.format("%d bytes", bytes);
        return this.formatValue(bytes, "B");
    }

    @Override
    @NotNull
    public String formatValue(long value, @NotNull String unit) {
        if (value < KILO) return String.format("%d %s", value, unit).trim();
        else if (value < MEGA) return this.formatUnits(value, KILO, String.format("K%s", unit));
        else if (value < GIGA) return this.formatUnits(value, MEBI,  String.format("M%s", unit));
        else if (value < TERA) return this.formatUnits(value, GIGA,  String.format("G%s", unit));
        else if (value < PETA) return this.formatUnits(value, TERA,  String.format("T%s", unit));
        else if (value < EXA) return this.formatUnits(value, PETA,  String.format("P%s", unit));
        else return this.formatUnits(value, EXA, String.format("E%s", unit));
    }

    @Override
    @NotNull
    public String formatTime(long time, @NotNull String format) {
        return new SimpleDateFormat(format)
                .format(new Date(time));
    }

    @Override
    @NotNull
    public String formatTimeDiff(long startTime, long endTime, @NotNull String format) {
        String formated = format;

        long[] differences = this.getDateDiff(startTime, endTime, true);
        for (int i = 0; i < TYPES.length; i++) {
            formated = formated.replace("%" + SHORT_NAME[i],
                    differences.length >= i ? String.valueOf(differences[i]) : "0");
        }

        long[] fullDifferences = this.getDateDiff(startTime, endTime, false);
        for (int i = 0; i < TYPES.length; i++) {
            formated = formated.replace("%f" + SHORT_NAME[i],
                    differences.length >= i ? String.valueOf(fullDifferences[i]) : "0");
        }

        return formated;
    }

    @Override
    @NotNull
    public String formatElapsedSecs(long secs) {
        return this.formatTimeDiff(0, secs, "%fh, %h:%m:%s");
    }

    private String formatUnits(long value, long prefix, String unit) {
        if (value % prefix == 0) return String.format("%d %s", value / prefix, unit);
        return String.format("%.2f %s", (double) value / prefix, unit);
    }

    private long[] getDateDiff(long startTime, long endTime, boolean modify) {
        Calendar startTimeCalender = this.getCalenderWithMillis(startTime);
        Calendar endTimeCalender = this.getCalenderWithMillis(endTime);

        boolean future = false;
        if (endTimeCalender.equals(startTimeCalender)) return new long[0];

        if (endTimeCalender.after(startTimeCalender)) future = true;

        // Temporary 50ms time buffer added to avoid display truncation due to code execution delays
        endTimeCalender.add(Calendar.MILLISECOND, future ? 50 : -50);

        long[] differences = new long[TYPES.length];
        for (int i = 0; i < TYPES.length; i++) {
            int diff = dateDiff(TYPES[i], startTimeCalender, endTimeCalender, future, modify);
            differences[i] = diff;
        }

        // Preserve correctness in the original date object by removing the extra buffer time
        endTimeCalender.add(Calendar.MILLISECOND, future ? -50 : 50);
        return differences;
    }

    private int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future, boolean modifyCalender) {
        int diff = 0;
        long savedDate = fromDate.getTimeInMillis();
        while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
            if (modifyCalender) savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
            diff++;
        }
        diff--;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

    private Calendar getCalenderWithMillis(long millis) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(millis);
        return calendar;
    }

}
