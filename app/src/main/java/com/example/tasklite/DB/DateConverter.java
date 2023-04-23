package com.example.tasklite.DB;

import androidx.room.TypeConverter;

import com.example.tasklite.Entities.PriorityLevel;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimeStamp(Long date){
        return date == null ? null : new Date(date);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(PriorityLevel priorityLevel){
        return priorityLevel == null ? null: priorityLevel.name();
    }

    @TypeConverter
    public static String toPriority(String priorityLevel){
        return priorityLevel == null ? null: priorityLevel.valueOf(priorityLevel);
    }
}
