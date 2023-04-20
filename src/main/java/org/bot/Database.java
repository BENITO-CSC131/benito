package org.bot;

import org.json.JSONArray;

import java.util.ArrayList;

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
    //TODO
    // had an idea that we could use a hash map for each ASS_ categories and to have key values of course objects (COURSE ID). Ill discuss more on the meet on saturday

}

