package org.bot;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Assignment {
    private final int courseID;
    private final int assID;
    private final String assName;
    private final String assDateString;
    private final Boolean has_submitted_submissions;
    private final LocalDateTime assDateFormat;

    // Constructor that takes a JSONObject
    public Assignment(JSONObject assignment) {
        this.courseID = assignment.getInt("course_id");
        this.assID = assignment.getInt("id");
        this.assName = assignment.getString("name");
        this.has_submitted_submissions = assignment.getBoolean("has_submitted_submissions");

        if (assignment.isNull("due_at")) {
            this.assDateString = null;
            this.assDateFormat = null;
        } else {
            this.assDateString = assignment.getString("due_at");
            this.assDateFormat = ZonedDateTime.parse(assDateString, DateTimeFormatter.ISO_DATE_TIME)
                    .withZoneSameInstant(java.time.ZoneId.systemDefault())
                    .toLocalDateTime();
        }
    }

    public boolean hasDueDate() {
        return assDateFormat != null;
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

    public String getAssDateString() {
        return assDateString;
    }

    public LocalDateTime getDateFormat() {
        return assDateFormat;
    }

    public boolean getHasBeenSubmited() {
        return has_submitted_submissions;
    }
}
