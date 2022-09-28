package me.cnm.shared.utility.format;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

/**
 * The {@code FormatHandler} is used to format values to human-readable values
 */
public interface IFormatHandler {

    /**
     * Format the given double to 2 decimal digits
     *
     * @param value The double to be formatted
     * @return The formatted double
     * @see #formatDouble(double, int)
     */
    double formatDouble(double value);

    /**
     * Format the given double to n decimal digits
     *
     * @param value    The double to be formatted
     * @param decimals The amount of decimal digits
     * @return The formatted double
     * @throws IllegalArgumentException If {@code decimals} is lower than 0
     */
    double formatDouble(double value, int decimals);

    /**
     * Format bytes int a string representation using IEC standard<br>
     * For disk space use {@link #formatBytesDecimal(long)}
     *
     * @param bytes The bytes to be formatted
     * @return The formatted bytes
     */
    @NotNull
    String formatBytes(long bytes);

    /**
     * Format bytes into a string representation using decimal SI units<br>
     * This is used for disk space, most other storage should use {@link #formatBytes(long)}
     *
     * @param bytes The bytes to be formatted
     * @return The formatted bytes
     */
    @NotNull
    String formatBytesDecimal(long bytes);

    /**
     * Format the value into a string representation using decimal SI units
     *
     * @param value The value to be formatted
     * @return The formatted value
     * @throws NullPointerException If {@code unit} is null
     */
    @NotNull
    String formatValue(long value, @NotNull String unit);

    /**
     * Format the given time with a {@link SimpleDateFormat}
     *
     * @param time   The time to format
     * @param format The formatted use by the {@link SimpleDateFormat}
     * @return The formatted time
     * @throws NullPointerException If {@code format} is null
     * @see SimpleDateFormat
     */
    @NotNull
    String formatTime(long time, @NotNull String format);

    /**
     * Formats the difference between the start and the end date
     *
     * @param startTime The start date from witch to calculate
     * @param endTime   The end date to calculate to
     * @param format    The format in witch to the time should be output<br>
     *                  The following placeholders are replaced:<br>
    *                   <table class="striped">
     *                      <caption>Chart shows existing placeholders</caption>
     *                      <thead>
     *                          <tr>
     *                              <th scope="col" style="text-align:left">Placeholder</th>
     *                              <th scope="col" style="text-align:left">Replacement</th>
     *                              <th scope="col" style="text-align:left">Example(for 1 year 2 months 5 hours and 7 minutes)</th>
     *                          </tr>
     *                      </thead>
     *                      <tbody>
     *                          <tr>
     *                              <th scope="row">%y</th>
     *                              <th>Year(s)</th>
     *                              <th>1</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%mo</th>
     *                              <th>Month(s)</th>
     *                              <th>2</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%fmo</th>
     *                              <th>Full month(s) (ignore the amount of years)</th>
     *                              <th>14</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%d</th>
     *                              <th>Day(s)</th>
     *                              <th>0</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%fd</th>
     *                              <th>Full day(s) (ignore the amount of years and months)</th>
     *                              <th>420</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%h</th>
     *                              <th>Hour(s)</th>
     *                              <th>5</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%fh</th>
     *                              <th>Full hour(s) (ignore the amount of years, months and days)</th>
     *                              <th>10080</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%m</th>
     *                              <th>Minute(s)</th>
     *                              <th>7</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%fm</th>
     *                              <th>Full minutes(s) (ignore the amount of years, months, days and hours)</th>
     *                              <th>604807</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%s</th>
     *                              <th>Second(s)</th>
     *                              <th>0</th>
     *                          </tr>
     *                          <tr>
     *                              <th scope="row">%fs</th>
     *                              <th>Full seconds(s) (ignore the amount of years, months, days, hours and seconds)</th>
     *                              <th>36288420</th>
     *                          </tr>
     *                      </tbody>
     *                  </table>
     * @return The formatted time difference
     * @throws NullPointerException If {@code format} is null
     */
    @NotNull
    String formatTimeDiff(long startTime, long endTime, @NotNull String format);

    /**
     * Formats an elapsed time in seconds as days, hh:mm:ss
     *
     * @param secs The elapsed seconds
     * @return The formatted time
     */
    @NotNull
    String formatElapsedSecs(long secs);

}
