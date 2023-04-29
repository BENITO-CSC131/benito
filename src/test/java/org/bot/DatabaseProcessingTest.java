package org.bot;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseProcessingTest {
    JSONObject assignment;
    ArrayList<Assignment> assignments = new ArrayList<>();

    @BeforeEach
    void setUp() {
        assignment = new JSONObject();
        assignment.put("id", 100);
        assignment.put("name", "Test Assignment");
        assignment.put("due_at", "2021-04-20T23:59:00Z");
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", true);

        assignments.add(new Assignment(assignment));

        assignment = new JSONObject();
        assignment.put("id", 200);
        assignment.put("name", "Test Assignment 2");
        assignment.put("due_at", "9999-04-21T23:59:00Z");
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", true);

        assignments.add(new Assignment(assignment));

        assignment = new JSONObject();
        assignment.put("id", 300);
        assignment.put("name", "Test Assignment 3");
        assignment.put("due_at", "9999-04-22T23:59:00Z");
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", true);

        assignments.add(new Assignment(assignment));
    }

    @Test
    void testUpcomingDue() {
        ArrayList<Assignment> upcoming = Database.upcomingDue(assignments);
        assertEquals(2, upcoming.size(), "Only the two assignments due in the future should be returned");
        assertEquals("Test Assignment 2", upcoming.get(0).getAssName(), "Test Assignment 2 should be first");
        assertEquals("Test Assignment 3", upcoming.get(1).getAssName(), "Test Assignment 3 should be second");

        // Testing for null due date
        assignment = new JSONObject();
        assignment.put("id", 1000);
        assignment.put("name", "Test Assignment 4");
        assignment.put("due_at", JSONObject.NULL);
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", true);
        assignments.add(new Assignment(assignment));

        upcoming = Database.upcomingDue(assignments);

        // New assignment shouldn't be included
        assertEquals(2, upcoming.size(), "Size should still be 2");
    }

    @Test
    void testOverDue() {

        assignment = new JSONObject();
        assignment.put("id", 100);
        assignment.put("name", "Test Overdue Assignment");
        assignment.put("due_at", "2021-04-20T23:59:00Z");
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", false);

        assignments.add(new Assignment(assignment));

        ArrayList<Assignment> overdue = Database.overDue(assignments);
        assertEquals(1, overdue.size(), "Only the one assignment in past and unsubmitted should appear");
        assertEquals("Test Overdue Assignment", overdue.get(0).getAssName(), "Test Overdue Assignment should be first");

        // Testing for null due date
        assignment = new JSONObject();
        assignment.put("id", 1000);
        assignment.put("name", "Test Overdue Assignment");
        assignment.put("due_at", JSONObject.NULL);
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", false);

        assignments.add(new Assignment(assignment));

        // New assignment shouldn't be included
        assertEquals(1, overdue.size(), "Size should still be 1");

    }

    @Test
    void testPastSubmitted() {
        assignment = new JSONObject();
        assignment.put("id", 100);
        assignment.put("name", "Test Past Submitted Assignment");
        assignment.put("due_at", "2021-04-20T23:59:00Z");
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", true);

        assignments.add(new Assignment(assignment));

        ArrayList<Assignment> pastSubmitted = Database.pastSubmitted(assignments);
        assertEquals(2, pastSubmitted.size(), "2 should appear, the one in this test and the one in setup");
        assertEquals("Test Assignment", pastSubmitted.get(0).getAssName(), "Test Assignment should be first");
        assertEquals("Test Past Submitted Assignment", pastSubmitted.get(1).getAssName(),
                "Test Past Submitted Assignment should be second");

        // Testing for null due date
        assignment = new JSONObject();
        assignment.put("id", 100);
        assignment.put("name", "Test Overdue Assignment");
        assignment.put("due_at", JSONObject.NULL);
        assignment.put("course_id", 12345);
        assignment.put("has_submitted_submissions", false);

        assignments.add(new Assignment(assignment));

        // New assignment shouldn't be included
        assertEquals(2, pastSubmitted.size(), "Size should still be 2");
    }

    @Test
    void testUndated() {
        // Creating 2 undated assignments
        assignment = new JSONObject();
        assignment.put("id", 100);
        assignment.put("name", "Test Undated Assignment 1");
        assignment.put("due_at", JSONObject.NULL);
        assignment.put("course_id", 2000);
        assignment.put("has_submitted_submissions", true);

        assignments.add(new Assignment(assignment));

        assignment = new JSONObject();
        assignment.put("id", 101);
        assignment.put("name", "Test Undated Assignment 2");
        assignment.put("due_at", JSONObject.NULL);
        assignment.put("course_id", 1000);
        assignment.put("has_submitted_submissions", true);

        assignments.add(new Assignment(assignment));

        ArrayList<Assignment> undated = Database.undated(assignments);

        // Should be 2 undated assignments
        assertEquals(2, undated.size(), "Only the one undated assignment should appear");

        // Should be in order of course_id least-to-greatest
        assertEquals("Test Undated Assignment 2", undated.get(0).getAssName(), "2 (course_id: 1000) should be first");
        assertEquals("Test Undated Assignment 1", undated.get(1).getAssName(), "1 (course_id: 2000) should be second");
    }
}
