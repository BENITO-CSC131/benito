package org.bot;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageHandlerTest {
    JSONObject jsonAssignment;
    JSONArray jsonAssignments;
    JSONObject jsonCourse;
    JSONArray jsonCourses;
    Course course;
    Assignment assignment;
    Database db;
    MessageHandler msgHandler = new MessageHandler();
    @BeforeEach
    void setup() {
        db = new Database();
        jsonAssignment = new JSONObject();
        jsonAssignments = new JSONArray();
        jsonAssignment.put("course_id", 100);
        jsonAssignment.put("id", 100);
        jsonAssignment.put("name", "Assignment 1");
        jsonAssignment.put("due_at", "2023-04-23T06:59:59Z");
        jsonAssignment.put("has_submitted_submissions", true);
        jsonAssignment.put("description", "This is an assignment, but this description shouldn't show up in assignment list.");
        jsonAssignments.put(jsonAssignment);
        db.assLOAD(jsonAssignments);

        jsonCourses = new JSONArray();
        jsonCourse = new JSONObject();
        jsonCourse.put("id", 100);
        jsonCourse.put("name", "Course 1");
        jsonCourse.put("description", "This is a course, but this description shouldn't show up in course list.");
        jsonCourses.put(jsonCourse);
        db.courseLOAD(jsonCourses);
    }
    @Test
    void coursesToMessages() {
        msgHandler.coursesToMessages(db.getCourses_AL());
        assertEquals("***Course 1***\n", msgHandler.content.get(0));
    }

    @Test
    void assmtsToMessages() {
        msgHandler.assmtsToMessages(db.getAllAss_AL());
        assertEquals("[**[CourseNotFound]**]->*Assignment 1* - 2023-04-23T06:59:59Z\n",msgHandler.content.get(0));
    }

    @Test
    void clear() {
        msgHandler.clear();
    }

    @AfterEach
    void clearTests()
    {
        msgHandler.clear();
    }
}