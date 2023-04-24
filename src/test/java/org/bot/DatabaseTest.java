package org.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
    JSONArray jsonCourses = new JSONArray();
    JSONArray jsonAssignments = new JSONArray();
    JSONObject jsonCourse;
    JSONObject jsonAssignment;
    Database db;

    @BeforeEach
    void setUp() {
        // Setting up dummy JSONArrays for testing
        // Each has an extra unnecessary field to make sure it's not being used
        // Update tests when new fields are needed
        jsonCourse = new JSONObject();
        jsonCourse.put("id", 100);
        jsonCourse.put("name", "Course 1");
        jsonCourse.put("description", "This is a course, but this description shouldn't show up in course list.");
        jsonCourses.put(jsonCourse);

        jsonCourse = new JSONObject();
        jsonCourse.put("id", 101);
        jsonCourse.put("name", "Course 2");
        jsonCourse.put("description", "This is a course, but this description shouldn't show up in course list.");
        jsonCourses.put(jsonCourse);

        jsonAssignment = new JSONObject();
        jsonAssignment.put("id", 1000);
        jsonAssignment.put("name", "Assignment 1");
        jsonAssignment.put("due_at", "2023-04-23T06:59:59Z");
        jsonAssignment.put("course_id", 100);
        jsonAssignment.put("description",
                "This is an assignment, but this description shouldn't show up in assignment list.");
        jsonAssignments.put(jsonAssignment);

        jsonAssignment = new JSONObject();
        jsonAssignment.put("id", 1010);
        jsonAssignment.put("name", "Assignment 1");
        jsonAssignment.put("due_at", "2023-04-24T06:59:59Z");
        jsonAssignment.put("course_id", 101);
        jsonAssignment.put("description",
                "This is an assignment, but this description shouldn't show up in assignment list.");
        jsonAssignments.put(jsonAssignment);
    }

    @Test
    void testCourseLOAD() throws Exception {
        db = new Database();
        db.courseLOAD(jsonCourses);

        assertEquals(2, db.getCourses_AL().size());

        // Checking that the courses were loaded correctly
        assertEquals(100, db.getCourses_AL().get(0).getId(), "Testing ID getter");
        assertEquals("Course 1", db.getCourses_AL().get(0).getCourseName(), "Testing name getter");
        assertEquals(101, db.getCourses_AL().get(1).getId(), "Testing ID getter");
        assertEquals("Course 2", db.getCourses_AL().get(1).getCourseName(), "Testing name getter");
    }

    @Test
    void testAssLOAD() throws Exception {
        db = new Database();
        db.assLOAD(jsonAssignments);

        assertEquals(2, db.getAllAss_AL().size());

        // Checking that the assignments were loaded correctly
        assertEquals(1000, db.getAllAss_AL().get(0).getAssID(), "Testing ID getter");
        assertEquals("Assignment 1", db.getAllAss_AL().get(0).getAssName(), "Testing name getter");
        assertEquals("2023-04-23T06:59:59Z", db.getAllAss_AL().get(0).getAssDate().toString(), "Testing date getter");
        assertEquals(100, db.getAllAss_AL().get(0).getCourseID(), "Testing courseID getter");

        assertEquals(1010, db.getAllAss_AL().get(1).getAssID(), "Testing ID getter");
        assertEquals("Assignment 1", db.getAllAss_AL().get(1).getAssName(), "Testing name getter");
        assertEquals("2023-04-24T06:59:59Z", db.getAllAss_AL().get(1).getAssDate().toString(), "Testing date getter");
        assertEquals(101, db.getAllAss_AL().get(1).getCourseID(), "Testing courseID getter");
    }
}
