package org.bot;

import org.json.JSONObject;

public class Course {
    private final int courseID;
    private final String courseName;

    // Constructor
    public Course(int courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    // Constructor that takes a JSONObject
    public Course(JSONObject course) {
        this.courseID = course.getInt("id");
        this.courseName = course.getString("name");
    }

    // Methods
    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }
}
