package org.bot;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageHandlerTest {
    JSONObject jsonAssignment;
    JSONArray jsonAssignments;
    JSONObject jsonCourse;
    JSONArray jsonCourses;
    MessageHandler msgHandler = new MessageHandler();

    @BeforeEach
    void setup() {
        App.db.clear();
        jsonAssignment = new JSONObject();
        jsonAssignments = new JSONArray();
        jsonAssignment.put("course_id", 100);
        jsonAssignment.put("id", 100);
        jsonAssignment.put("name", "Assignment 1");
        jsonAssignment.put("due_at", "2023-04-23T06:59:59Z");
        jsonAssignment.put("has_submitted_submissions", true);
        jsonAssignment.put("description", "This is an assignment, but this description shouldn't show up in assignment list.");
        jsonAssignments.put(jsonAssignment);
        App.db.assLOAD(jsonAssignments);

        jsonCourses = new JSONArray();
        jsonCourse = new JSONObject();
        jsonCourse.put("id", 100);
        jsonCourse.put("name", "Course 1");
        jsonCourse.put("description", "This is a course, but this description shouldn't show up in course list.");
        jsonCourses.put(jsonCourse);
        App.db.courseLOAD(jsonCourses);
    }

    @Test
    void coursesToMessages() {
        msgHandler.coursesToMessages(App.db.getCourses_AL());
        assertEquals("***Course 1***\n", msgHandler.content.get(0));
    }

    @Test
    void assmtsToMessages() {
        msgHandler.assmtsToMessages(App.db.getAllAss_AL());
        assertEquals("[**Course 1**]->*Assignment 1* - 2023-04-23T06:59:59Z\n", msgHandler.content.get(0));
    }

    @Test
    void assmtsToMessageNoCourse() {

        App.db.clear();

        jsonAssignment = new JSONObject();
        jsonAssignments = new JSONArray();
        jsonAssignment.put("course_id", 100);
        jsonAssignment.put("id", 100);
        jsonAssignment.put("name", "Assignment 1");
        jsonAssignment.put("due_at", "2023-04-23T06:59:59Z");
        jsonAssignment.put("has_submitted_submissions", true);
        jsonAssignment.put("description", "This is an assignment, but this description shouldn't show up in assignment list.");
        jsonAssignments.put(jsonAssignment);
        App.db.assLOAD(jsonAssignments);

        msgHandler.assmtsToMessages(App.db.getAllAss_AL());
        assertEquals("[**[CourseNotFound]**]->*Assignment 1* - 2023-04-23T06:59:59Z\n", msgHandler.content.get(0));
    }

    @Test
    void test2000charAssignments() {
        App.db.clear();

        jsonAssignments = new JSONArray();
        for (int i = 0; i < 100; i++) {
            jsonAssignment = new JSONObject();
            jsonAssignment.put("course_id", 100);
            jsonAssignment.put("id", 100);
            jsonAssignment.put("name", "Assignment 1");
            jsonAssignment.put("due_at", "2023-04-23T06:59:59Z");
            jsonAssignment.put("has_submitted_submissions", true);
            jsonAssignment.put("description", "This is an assignment, but this description shouldn't show up in assignment list.");
            jsonAssignments.put(jsonAssignment);
        }
        App.db.assLOAD(jsonAssignments);
        msgHandler.assmtsToMessages(App.db.getAllAss_AL());
        assertEquals(4, msgHandler.content.size());
    }

    @Test
    void test2000charCourses() {
        App.db.clear();

        jsonCourses = new JSONArray();

        for (int i = 0; i < 500; i++) {
            jsonCourse = new JSONObject();
            jsonCourse.put("id", 100);
            jsonCourse.put("name", "Course 1");
            jsonCourse.put("description", "This is a course, but this description shouldn't show up in course list.");
            jsonCourses.put(jsonCourse);
        }
        App.db.courseLOAD(jsonCourses);
        msgHandler.coursesToMessages(App.db.getCourses_AL());
        assertEquals(4, msgHandler.content.size());
    }

    @Test
    void clear() {
        msgHandler.clear();
    }

    @AfterEach
    void clearTests() {
        msgHandler.clear();
    }
}