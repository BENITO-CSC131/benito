package org.bot;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import org.json.JSONException;
import org.json.JSONObject;

public class Assignment {
    private final int courseID;
    private final int assID;
    private final String assName;
    private final String assDate;

    private final LocalDate assDateFormat;

    // Constructor that takes a JSONObject
    public Assignment(JSONObject assignment) {
        this.courseID = testIntKey("course_id",assignment);
        this.assID = testIntKey("id",assignment);
        this.assName = testStringKey("name",assignment);
        this.assDate = testStringKey("due_at",assignment);
        this.assDateFormat = (assDate.equals("Null")) ? LocalDate.from(LocalDate.now()) : ZonedDateTime.parse(assDate, DateTimeFormatter.ISO_DATE_TIME)
                .withZoneSameInstant(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    public int testIntKey(String key, JSONObject obj) {
        int tmp = 0;
        try{
            tmp = obj.getInt(key);
        }
        catch(JSONException e)
        {
            tmp = -1;
        }
        return tmp;
    }

    public String testStringKey(String key, JSONObject obj) {
        String tmp = "";
        try{
            tmp = obj.getString(key);
        }
        catch(JSONException e)
        {
            tmp = "Null";
        }
        return tmp;
    }

    // methods
    public int getCourseID() {
        return courseID;
    }

    public int getAssID() {
        return assID;
    }

    public String getAssName() {
        return assName;
    }

    public String getAssDate() {
        return assDate;
    }

    public LocalDate getDateFormat() {
        return assDateFormat;
    }
}
