package org.bot;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

public class Database {
    //fields
    private JSONArray allCourse_JSON = new JSONArray();
    private JSONArray allAss_JSON = new JSONArray();
    private ArrayList<Course> coursesAL;
    private ArrayList<Assignments> allAss_AL;
    private ArrayList<Assignments> upcoming;
    private ArrayList<Assignments> past;
    private ArrayList<Assignments> undated;
    private ArrayList<Assignments> overdue;

    // idea of fast getting of courses since technically the amount of classes can can change from student to student and so Integer would be courseID and will make faster performance of getting courses.
    private HashMap<Integer, Assignments> pastHASH;
    private HashMap<Integer, Assignments> upcomingHASH;
    private HashMap<Integer, Assignments> undatedHASH;
    private HashMap<Integer, Assignments> overdueHASH;


    //TODO
    // had an idea that we could use a hash map for each ASS_ categories and to have key values of course objects (COURSE ID). Ill discuss more on the meet on saturday
    public Database(String CanvasKey) throws Exception {
        courseLOAD();
        //assLOAD
    }

    public void courseLOAD() throws Exception {
        //Start the data transfer from JSON ARRAY to ARRAYLIST of Course objects
        allCourse_JSON = CanvasGet.getClasses(API_keys.CanvasKey);
        //A lot of this code is from the myListener and from various other sources
        for (int i = 0; i < App.allCourse_JSON.length(); i++) {
            JSONObject course = App.allCourse_JSON.getJSONObject(i);
            if (course.getInt("id") > 100000) {
                coursesAL.add(new Course(course.getInt("course_ID"),course.getString("name")));
            }
        }
        //end of Course load
    }

    public void assLOAD() throws Exception{
        //TODO loads assignments to assignments and their respective datastructures.
    }
}

