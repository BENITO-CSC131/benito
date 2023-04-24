package org.bot;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

public class Database {
    //fields
    private JSONArray allCourse_JSON = new JSONArray();
    private JSONArray allAss_JSON = new JSONArray();
    private ArrayList<Course> coursesAL = new ArrayList<>();
    private ArrayList<Assignment> allAss_AL = new ArrayList<>();
    private ArrayList<Assignment> upcoming = new ArrayList<>();
    private ArrayList<Assignment> past = new ArrayList<>();
    private ArrayList<Assignment> undated = new ArrayList<>();
    private ArrayList<Assignment> overdue = new ArrayList<>();

    // idea of fast getting of courses since technically the amount of classes can can change from student to student and so Integer would be courseID and will make faster performance of getting courses.
    // private HashMap<Integer, Assignment> pastHASH;
    // private HashMap<Integer, Assignment> upcomingHASH;
    // private HashMap<Integer, Assignment> undatedHASH;
    // private HashMap<Integer, Assignment> overdueHASH;
    
    public Database() throws Exception {

    }

    public ArrayList<Course> getCoursesAL() {
        return coursesAL;
    }

    public ArrayList<Assignment> getAllAss_AL() {
        return allAss_AL;
    }

    public void courseLOAD(JSONArray courses) throws Exception {
        for (int i = 0; i < courses.length(); i++ ){
            coursesAL.add(new Course(courses.getJSONObject(i)));
        }
    }

    public void assLOAD(JSONArray assignments) throws Exception{
        for (int i = 0; i < assignments.length(); i++) {
            allAss_AL.add(new Assignment(assignments.getJSONObject(i)));
        }
    }
}

