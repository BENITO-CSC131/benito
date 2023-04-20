package org.bot;

public class Course {
    private final int courseID;
    private final String courseName;

    //Constructor
    public Course (int courseID, String courseName){
        this.courseID = courseID;
        this.courseName = courseName;
    }
    //Methods
    public int getCourseID(){
        return courseID;
    }
    public String getCourseName(){
        return courseName;
    }
}

