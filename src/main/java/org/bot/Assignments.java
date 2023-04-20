package org.bot;

import java.util.Date;

public class Assignments {
    //fields
    private final int courseID;
    private final int assID;
    private final String assName;
    private final String assDate;

    private final Date assDateFormat;
    //TODO: We made this type into data becuase I forgot what was said during the meeting of how to do this. *Julian
    //Constructor
    public Assignments(int courseID, int assID, String assName, String assDate, Date assDateFormat){
        this.courseID = courseID;
        this.assID = assID;
        this.assName = assName;
        this.assDate = assDate;
        this.assDateFormat = assDateFormat;
    }
    //methods
    public int getCourseID(){
        return courseID;
    }
    public int getAssID(){
        return assID;
    }
    public String getAssName(){
        return assName;
    }
    public String getAssDate(){
        return assDate;
    }
    public Date getDateFormat(){
        return assDateFormat;
    }
}
