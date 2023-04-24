package org.bot;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * Stores all info retrieved from the Canvas API.
 * JSONObjects are stored as custom objects in ArrayLists.
 * ArrayLists are populated by various methods in and outside of this class.
 */
public class Database {
    // private JSONArray allCourse_JSON = new JSONArray();
    // private JSONArray allAss_JSON = new JSONArray();
    private ArrayList<Course> courses_AL = new ArrayList<>();
    private ArrayList<Assignment> allAss_AL = new ArrayList<>();

    // Getter for courses_AL; returns an ArrayList of Course objects
    public ArrayList<Course> getCourses_AL() {
        return courses_AL;
    }

    // Getter for allAss_AL; returns an ArrayList of Assignment objects
    public ArrayList<Assignment> getAllAss_AL() {
        return allAss_AL;
    }

    /**
     * Populates coursesAL. Converts an input JSONArray into an ArrayList of Course
     * objects
     *
     * @param courses the JSONArray containing Course objects
     * @throws Exception if there is an error while loading the Course objects
     */
    public void courseLOAD(JSONArray courses) throws Exception {
        for (int i = 0; i < courses.length(); i++) {
            courses_AL.add(new Course(courses.getJSONObject(i)));
        }
    }

    /**
     * Populates allAss_AL Converts an input JSONArray into an ArrayList of
     * Assignment objects
     *
     * @param courses the JSONArray containing Course objects
     * @throws Exception if there is an error while loading the Course objects
     */
    public void assLOAD(JSONArray assignments) throws Exception {
        for (int i = 0; i < assignments.length(); i++) {
            allAss_AL.add(new Assignment(assignments.getJSONObject(i)));
        }
    }
}
