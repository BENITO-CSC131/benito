package org.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;

public class MyListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)

        switch (content) {
            case "!ping" -> {
                MessageChannel channel = event.getChannel();
                channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

            }
            case "!classes" -> {
                MessageChannel channel = event.getChannel();

                if (App.courses.isEmpty()) {
                    channel.sendMessage("Getting classes").queue();
                    try {
                        System.out.println("Connecting for classes");
                        App.courses = CanvasGet.getClasses();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                for (int i = 0; i < App.courses.length(); i++) {
                    JSONObject course = App.courses.getJSONObject(i);

                    if (course.getInt("id") > 100000) {
                        channel.sendMessage(course.getString("name")).queue();
                    }
                }
            }
            case "!hw" -> {
                MessageChannel channel = event.getChannel();

                if (App.assignments.isEmpty()) {
                    channel.sendMessage("Getting Assignments").queue();
                    try {
                        System.out.println("Connecting for hw");
                        App.assignments = CanvasGet.getHW();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                for (int i = 0; i < App.assignments.length(); i++) {
                    JSONObject assignment = App.assignments.getJSONObject(i);
                    channel.sendMessage(assignment.getString("name")).queue();
                }
            }

            case "!notifs" ->{
                MessageChannel channel = event.getChannel();

                if (App.announcements.isEmpty()) {
                    channel.sendMessage("Getting announcements").queue();
                    try {
                        System.out.println("Connecting for announcements");
                        App.announcements = CanvasGet.getAnnouncements();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                for (int i = 0; i < App.announcements.length(); i++) {
                    JSONObject course = App.announcements.getJSONObject(i);

                    if (course.getInt("id") > 100000) {
                        channel.sendMessage(course.getString("name")).queue();
                    }
                }
            }

        }
    }
}
