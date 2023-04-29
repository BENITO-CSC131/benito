package org.bot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Stores all info retrieved from the Canvas API.
 * JSONObjects are stored as custom objects in ArrayLists.
 * ArrayLists are populated by various methods in and outside of this class.
 */
public class Database {
    static LocalDateTime today = LocalDateTime.now();
    private ArrayList<Course> courses_AL = new ArrayList<>();
    private ArrayList<Assignment> allAss_AL = new ArrayList<>();
    private ArrayList<Assignment> upcomingAss_AL = new ArrayList<>();
    private ArrayList<Assignment> overdueAss_AL = new ArrayList<>();
    private ArrayList<Assignment> pastSubmittedAss_AL = new ArrayList<>();
    private ArrayList<Assignment> undatedAss_AL = new ArrayList<>();

    /**
     * upcomingDue method sorts through allASS_AL and only populates based on a
     * certain set of requirements.
     * REQUIREMENTS for Assignment Object to enter upcomingAss_AL
     * 1.must not be current or past time compared to local time
     *
     * @param allAssignments the list of all assignments
     * @return returns an arraylist to populate the upcoming assignment category
     */
    public static ArrayList<Assignment> upcomingDue(ArrayList<Assignment> allAssignments) {
        ArrayList<Assignment> upcoming = new ArrayList<>();
        today = LocalDateTime.now();

        // Filters out overdue assignments
        for (Assignment a : allAssignments) {
            if (a.getDateFormat().isAfter(today) || a.getDateFormat().isEqual(today)) {
                upcoming.add(a);
            }
        }

        // Sorts assignments by due date, closest to today first
        upcoming.sort((a1, a2) -> a1.getDateFormat().compareTo(a2.getDateFormat()));

        return upcoming;
    }

    /**
     * Filters out overdue assignments from the provided list of assignments and
     * returns a sorted list of overdue assignments. Sorted by due date, closest to
     * today first
     *
     * @param allAssignments the list of all assignments
     * @return a sorted list of overdue assignments
     */

    public static ArrayList<Assignment> overDue(ArrayList<Assignment> allAssignments) {
        ArrayList<Assignment> overdue = new ArrayList<>();
        today = LocalDateTime.now();

        // Filters out upcoming assignments
        for (Assignment a : allAssignments) {
            if (a.getDateFormat().isBefore(today) && !a.getHasBeenSubmited()) {
                overdue.add(a);
            }
        }

        // Sorts assignments by due date, closest to today first
        overdue.sort((a1, a2) -> a2.getDateFormat().compareTo(a1.getDateFormat()));

        return overdue;
    }

    public static ArrayList<Assignment> pastSubmitted(ArrayList<Assignment> allAssignments) {
        ArrayList<Assignment> pastSubmitted = new ArrayList<>();
        today = LocalDateTime.now();

        // Filters out past/submitted assignments
        for (Assignment a : allAssignments) {
            if ((a.getDateFormat().isBefore(today) || a.getDateFormat().isEqual(today)) && a.getHasBeenSubmited()) {
                pastSubmitted.add(a);
            }
        }

        // Sorts assignments by due date, closest to today first
        pastSubmitted.sort((a1, a2) -> a2.getDateFormat().compareTo(a1.getDateFormat()));

        return pastSubmitted;
    }

    public static ArrayList<Assignment> undated(ArrayList<Assignment> allAssignments) {
        ArrayList<Assignment> undated = new ArrayList<>();
        today = LocalDateTime.now();

        // Filters out past/submitted assignments
        for (Assignment a : allAssignments) {
            if (a.getDateFormat() == null) {
                undated.add(a);
            }
        }

        // Sort undated by courseID
        undated.sort(new Comparator<Assignment>() {
            @Override
            public int compare(Assignment o1, Assignment o2) {
                return o1.getCourseID() - o2.getCourseID();
            }
        });

        return undated;
    }

    /**
     * Returns true if the given JSONObject has non-null values for all the given
     * keys,
     * and false otherwise.
     *
     * @param obj  the JSONObject to check
     * @param keys the keys to check for non-null values
     * @return true if the JSONObject has non-null values for all the given keys,
     * and false otherwise.
     */
    private static boolean hasValues(JSONObject obj, String... keys) {
        for (String key : keys) {
//            if (!obj.has(key) || obj.isNull(key)) {
            if (!obj.has(key)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasNonNullValues(JSONObject obj, String... keys) {
        for (String key : keys) {
            if (!obj.has(key) || obj.isNull(key)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Assignment> getUndatedAss_AL() {
        return undatedAss_AL;
    }

    public void setUndatedAss_AL(ArrayList<Assignment> undatedAss_AL) {
        this.undatedAss_AL = undatedAss_AL;
    }

    public ArrayList<Assignment> getPastSubmittedAss_AL() {
        return pastSubmittedAss_AL;
    }

    public void setPastSubmittedAss_AL(ArrayList<Assignment> pastSubmittedAss_AL) {
        this.pastSubmittedAss_AL = pastSubmittedAss_AL;
    }

    public ArrayList<Assignment> getOverdueAss_AL() {
        return overdueAss_AL;
    }

    public void setOverdueAss_AL(ArrayList<Assignment> overdueAss_AL) {
        this.overdueAss_AL = overdueAss_AL;
    }

    public void clear() {
        courses_AL.clear();
        allAss_AL.clear();
        upcomingAss_AL.clear();
        overdueAss_AL.clear();
        pastSubmittedAss_AL.clear();
    }

    public ArrayList<Assignment> getUpcomingAss_AL() {
        return upcomingAss_AL;
    }

    // Setter for populating upcomingAss_AL with upcoming assignments
    public void setUpcomingAss_AL(ArrayList<Assignment> upcomingAss_AL) {
        this.upcomingAss_AL = upcomingAss_AL;
    }

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
     */
    public void courseLOAD(JSONArray courses) {
        for (int i = 0; i < courses.length(); i++) {
            if (hasNonNullValues(courses.getJSONObject(i), "name", "id")) {
                courses_AL.add(new Course(courses.getJSONObject(i)));
            } else {
                System.out.println("Course " + i + " is missing a field");
            }
        }
    }

    /**
     * Populates allAss_AL Converts an input JSONArray into an ArrayList of
     * Assignment objects
     *
     * @param assignments the JSONArray containing Course objects
     */
    public void assLOAD(JSONArray assignments) {
        for (int i = 0; i < assignments.length(); i++) {
            if (hasValues(assignments.getJSONObject(i), "course_id", "id", "name", "has_submitted_submissions", "due_at")) {
                allAss_AL.add(new Assignment(assignments.getJSONObject(i)));
//            } else {
                System.out.println("Assignment " + i + " is missing a field");
//            }
            }
        }
    }
}
