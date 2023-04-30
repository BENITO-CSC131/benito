package org.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MyListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot())
            return;
        MessageHandler msgHandler = new MessageHandler();
        Message message = event.getMessage();
        String content = message.getContentRaw();

        switch (content) {
            case "!ping" -> {
                MessageChannel channel = event.getChannel();
                channel.sendMessage("Pong!").queue();
            }
            
            // Temp; UI guys redo this
            case "!courses" -> {
                MessageChannel channel = event.getChannel();

                if (App.db.getCourses_AL().isEmpty()) {
                    channel.sendMessage("Getting classes").queue();
                    try {
                        App.db.courseLOAD(CanvasGet.getCourses());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                // channel.sendMessage(messageBuilder(App.db.getCourses_AL(), "name")).queue();
                //for (int i = 0; i < App.db.getCourses_AL().size(); i++) {
                //    channel.sendMessage(App.db.getCourses_AL().get(i).getCourseName()).queue();
                //}
                msgHandler.coursesToMessages(App.db.getCourses_AL());
                msgHandler.print(event.getChannel());
            }

            // Temp; UI guys redo this
            case "!hw" -> {
                MessageChannel channel = event.getChannel();

                if (App.db.getCourses_AL().isEmpty()) {
                    channel.sendMessage("Getting Classes").queue();

                    try {
                        App.db.courseLOAD(CanvasGet.getCourses());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                channel.sendMessage("Getting All Assignments").queue();
                try {
                    App.db.assLOAD(CanvasGet.getAllAssignments());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                for (int i = 0; i < App.db.getAllAss_AL().size(); i++) {
                    channel.sendMessage(App.db.getAllAss_AL().get(i).getAssName()).queue();
                }
            }

            // Temp; UI guys redo this
            case "!upcoming" -> {
                MessageChannel channel = event.getChannel();
                if (App.db.getCourses_AL().isEmpty()) {
                    channel.sendMessage("Getting Classes").queue();

                    try {
                        App.db.courseLOAD(CanvasGet.getCourses());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (App.db.getAllAss_AL().isEmpty()) {
                    channel.sendMessage("Getting All Assignments").queue();

                    try {
                        App.db.assLOAD(CanvasGet.getAllAssignments());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                channel.sendMessage("Getting Upcoming Assignments").queue();
                try {
                    App.db.setUpcomingAss_AL(DueDateHandler.upcomingDue(App.db.getAllAss_AL()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                // channel.sendMessage(messageBuilder(App.db.getUpcomingAss_AL(), "name")).queue();
                /*for (int i = 0; i < App.db.getUpcomingAss_AL().size(); i++) {
                    channel.sendMessage(App.db.getUpcomingAss_AL().get(i).getAssName()).queue();
                }*/
                msgHandler.assmtsToMessages(App.db.getUpcomingAss_AL());
                msgHandler.print(event.getChannel());

            }
        }
    }
}
