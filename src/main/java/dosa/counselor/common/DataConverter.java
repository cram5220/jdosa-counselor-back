package dosa.counselor.common;


import dosa.counselor.common.encryption.AES256;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DataConverter {

    public static Short booleanToShort(Boolean bool){
        if(bool){
            return 1;
        }else{
            return 0;
        }
    }

    public static LocalDateTime stringToDatetime(String str){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        if(!str.contains("-")){
            pattern = "yyyyMMddHHmmss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        return dateTime;
    }

    public static String datetimeToString(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetimeStr = dateTime.format(formatter);
        return datetimeStr;
    }


    public static LocalDate stringToDate(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(str.substring(0,10), formatter);
        return date;
    }


    public static String dateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = date.format(formatter);
        return dateStr;
    }

    public static String dateToString(Date date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = date.toLocalDate().format(formatter);
        return dateStr;
    }


    public static LocalTime stringToTime(String str){
        //str 이 time 형태일수도 datetime 형태일수도 있음
        str = str.replaceAll(".*?([0-9]{1,2}\\:[0-9]{2}\\:[0-9]{2}).*","$1");
        if(str.length()<8){
            str="0"+str;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(str, formatter);
        return time;
    }

    public static String timeToString(LocalTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStr = time.format(formatter);
        return timeStr;
    }

    public static Boolean stringToBoolean(String boolStr){
        Boolean bool = Boolean.FALSE;

        if(boolStr.equals("1")||boolStr.equals("Y")||boolStr.equals("true")){
            bool = Boolean.TRUE;
        }
        return bool;
    }


    public static Integer encryptedBirthdateToAgeGroup(String encBirthdate){
        String birthDateStr = AES256.decrypt(encBirthdate);
        LocalDate birthDate = DataConverter.stringToDate(birthDateStr);
        Integer age = LocalDate.now().getYear()-birthDate.getYear();
        age = (age/10)*10;
        return age;
    }

    public static Short encryptedGenderToShort(String encGender){
        String genderStr = AES256.decrypt(encGender);
        Short gender = Short.parseShort(genderStr);
        return gender;
    }
    public static String timeToStringWithoutSec(LocalTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String timeStr = time.format(formatter);
        return timeStr;
    }


    public static Short dateToWeekday(LocalDate date){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 7;
            default:
                return 0;
        }
    }


    public static Short dayToMssqlWeekday(Short day){

        Short[] list = {2,3,4,5,6,7,1};
        return list[day-1];

    }
}
