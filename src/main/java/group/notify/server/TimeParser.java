package group.notify.server;
import java.text.ParseException;
import java.util.TimeZone;

import group.notify.telegram.dataObjects.Update;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeParser {
    public static DateTimeFormatter longDateTimePattern = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
    public static DateTimeFormatter longTimePattern = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static DateTimeFormatter shortTimePattern = DateTimeFormatter.ofPattern("HH:mm");
    public static TimeZone timeZone = TimeZone.getDefault();
    public static long maxTimerDuration = 1209600000; // 2 тижні
    public static long minTimerDuration = 30000; // 30 сек

    public static long parseFullTimeString(String timeString) throws ParseException{
        LocalDateTime parsedTime = LocalDateTime.parse(timeString.trim(), longTimePattern);
        return parsedTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static long parseShortTimeString(String timeString) throws ParseException{
        LocalTime parsedTime = LocalTime.parse(timeString.trim(), shortTimePattern);
        LocalDate currentDate = LocalDate.now();
        LocalDateTime resultTime = LocalDateTime.of(currentDate, parsedTime);
        return resultTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static byte validateTime(long time){
        long currentTime = System.currentTimeMillis();
        long upperLimit = currentTime + maxTimerDuration;
        long lowerLimit = currentTime + minTimerDuration;
        long timeDigit = time;
        byte out = 0;
        if (timeDigit > lowerLimit) {
            out += 1;
        }
        if (timeDigit < upperLimit) {
            out -= 1;
        }
        return out;
    }
    public static long parseTime(String timeString) throws Exception{
        LocalDateTime parsedDateTime;
        LocalTime parsedTime;
        try{
            parsedDateTime = LocalDateTime.parse(timeString, longDateTimePattern);
        }
        catch (Exception e){
            LocalDate currentDate = LocalDate.now();
            try{
                parsedTime = LocalTime.parse(timeString, longTimePattern);
                parsedDateTime = LocalDateTime.of(currentDate, parsedTime);
            }
            catch (Exception e2){
                try{
                    parsedTime = LocalTime.parse(timeString, shortTimePattern);
                    parsedDateTime = LocalDateTime.of(currentDate, parsedTime);
                }
                catch (Exception e3){
                    throw new Exception();
                }
            }
        }
        return parsedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
