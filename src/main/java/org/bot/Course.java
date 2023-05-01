package org.bot;

import org.json.JSONException;
import org.json.JSONObject;

public class Course {
    private final int courseID;
    private final String courseName;

    // Constructor that takes a JSONObject
    public Course(JSONObject course) {
        int tmp = 0;
        String strtmp = "";
        try
        {
            tmp = course.getInt("id");
        }
        catch(JSONException e)
        {
            tmp = -1;
        }
        this.courseID = tmp;
        try
        {
            strtmp = course.getString("name");
        }
        catch(JSONException e)
        {
            strtmp = "CourseNotFound";
        }
        this.courseName = strtmp;

        //this.courseID = course.getInt("id");
        //this.courseName = course.getString("name");
    }

    // Getter for id
    public int getCourseID() {return courseID;}

    public String getCourseName() {
        return courseName;
    }
}
