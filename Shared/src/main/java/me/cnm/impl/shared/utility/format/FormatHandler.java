package me.cnm.impl.shared.utility.format;

import me.cnm.shared.utility.format.IFormatHandler;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("SpellCheckingInspection")
public class FormatHandler implements IFormatHandler {

    // Date and Time
    private static final String[] SHORT_NAME = new String[]{"y", "mo", "d", "h", "m", "s"};
    private static final long[] MULTIPLIERS = new long[]{31536000000L, 2628000000L, 86400000, 3600000, 60000, 1000};


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
        else if (value < GIGA) return this.formatUnits(value, MEBI, String.format("M%s", unit));
        else if (value < TERA) return this.formatUnits(value, GIGA, String.format("G%s", unit));
        else if (value < PETA) return this.formatUnits(value, TERA, String.format("T%s", unit));
        else if (value < EXA) return this.formatUnits(value, PETA, String.format("P%s", unit));
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
        String formatted = format;
        long fullTime = endTime - startTime;
        long rest = fullTime;

        for (int i = 0; i < MULTIPLIERS.length; i++) {
            long multiplier = MULTIPLIERS[i];

            long restUnit = rest / multiplier;
            formatted = formatted.replace("%" + SHORT_NAME[i], Long.toString(restUnit));

            rest -= multiplier * restUnit;

            long fullUnit = fullTime / multiplier;
            formatted = formatted.replace("%f" + SHORT_NAME[i], Long.toString(fullUnit));
        }

        return formatted;
    }

    @Override
    @NotNull
    public String formatElapsedSecs(long secs) {
        return this.formatTimeDiff(0, secs * 1000, "%fh, %h:%m:%s");
    }

    private String formatUnits(long value, long prefix, String unit) {
        if (value % prefix == 0) return String.format("%d %s", value / prefix, unit);
        return String.format("%.2f %s", (double) value / prefix, unit);
    }

}
