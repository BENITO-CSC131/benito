package org.bot;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;


public class CanvasGet {
    public static JSONArray getClasses() throws Exception {
        String url = "https://csus.instructure.com/api/v1/courses";
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + API_keys.CanvasKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            JSONArray courses = new JSONArray(response);
            reader.close();

            return courses;
        } else {
            return null;
        }
    }

    public static JSONArray getHW() throws Exception {
        String url = "https://csus.instructure.com/api/v1/courses/" + 102203 + "/assignments";
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + API_keys.CanvasKey);
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            JSONArray assignments = new JSONArray(response);
            reader.close();

            return assignments;
        } else {
            return null;
        }
    }

    public static JSONArray getAnnouncements() throws Exception {
        String url = "https://csus.instructure.com/api/v1/courses/"+ 102203 + "/discussion_topics?only_announcements=true";
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + API_keys.CanvasKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            JSONArray announcements = new JSONArray(responseBuilder.toString());

            return announcements;
        } else {
            return null;
        }
    }


    //this method is in progress
    public static JSONArray getAllNotifs() throws Exception {
        JSONArray allNotifs = new JSONArray();
        JSONArray courses = getClasses();
        for (int i = 0; i < courses.length(); i++) {
            String courseId = courses.getJSONObject(i).getString("id");
            String url = "https://csus.instructure.com/api/v1/courses/" + courseId + "/account_notifications";
            HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + API_keys.CanvasKey);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                JSONArray courseNotifs = new JSONArray(response);
                reader.close();
                allNotifs.put(courseNotifs);
            }
        }
        return allNotifs;
    }
}
