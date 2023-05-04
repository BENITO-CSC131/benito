package org.bot;

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
        MessageChannel channel = event.getChannel();
        switch (content) {
            case "!ping" -> {
                channel.sendMessage("Pong!").queue();
            }

            case "!shutdown" -> {
                channel.sendMessage("Shutting Down... Thank you for using me! -Benito Team").queue();
                channel.getJDA().shutdown();
            }

            // Temp; UI guys redo this
            case "!courses" -> {

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
                msgHandler.clear();
            }

            // Temp; UI guys redo this
            case "!hw" -> {

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

                msgHandler.assmtsToMessages(App.db.getAllAss_AL());
                msgHandler.print(event.getChannel());
                msgHandler.clear();
            }

            // Temp; UI guys redo this
            case "!upcoming" -> {

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
                    App.db.setUpcomingAss_AL(Database.upcomingDue(App.db.getAllAss_AL()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                /*for (int i = 0; i < App.db.getUpcomingAss_AL().size(); i++) {
                    channel.sendMessage(App.db.getUpcomingAss_AL().get(i).getAssName()).queue();
                }*/
                msgHandler.assmtsToMessages(App.db.getUpcomingAss_AL());
                msgHandler.print(event.getChannel());
                msgHandler.clear();
            }

            case "!help" -> {
                channel.sendMessage("!ping - Pong!\n" +
                        "!shutdown - Turn off the bot\n" +
                        "!hw - Displays All Homework from all Courses\n" +
                        "!upcoming - Displays All Upcoming Homework Assignments\n" +
                        "!help - view these same commands again\n" +
                        "!overdue - Displays overdue assignments\n" +
                        "!past - Displays any past assignments\n" +
                        "!undated - Displays any undated assignments\n" +
                        "!submitted - Displays any submitted Assignments\n").queue();
            }

            // Temp; UI guys redo this
            case "!overdue" -> {
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

                channel.sendMessage("Getting Overdue Assignments").queue();
                try {
                    App.db.setOverdueAss_AL(Database.overDue(App.db.getAllAss_AL()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                msgHandler.assmtsToMessages(App.db.getOverdueAss_AL());
                msgHandler.print(event.getChannel());
                msgHandler.clear();
               /* for (int i = 0; i < App.db.getOverdueAss_AL().size(); i++) {
                    channel.sendMessage(App.db.getOverdueAss_AL().get(i).getAssName()).queue();
                }*/
            }

            // Temp; UI guys redo this
            case "!past" -> {
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

                channel.sendMessage("Getting Past Submitted Assignments").queue();
                try {
                    App.db.setPastSubmittedAss_AL(Database.pastSubmitted(App.db.getAllAss_AL()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                msgHandler.assmtsToMessages(App.db.getPastSubmittedAss_AL());
                msgHandler.print(event.getChannel());
                msgHandler.clear();

               /* for (int i = 0; i < App.db.getPastSubmittedAss_AL().size(); i++) {
                    channel.sendMessage(App.db.getPastSubmittedAss_AL().get(i).getAssName()).queue();
                }*/
            }

            case "!undated" -> {
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

                channel.sendMessage("Getting Undated Assignments").queue();
                try {
                    App.db.setUndatedAss_AL(Database.undatedAssignments(App.db.getAllAss_AL()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                msgHandler.assmtsToMessages(App.db.getUndatedAss_AL());
                msgHandler.print(event.getChannel());
                msgHandler.clear();
                /*for (int i = 0; i < App.db.getUndatedAss_AL().size(); i++) {
                    channel.sendMessage(App.db.getUndatedAss_AL().get(i).getAssName()).queue();
                }*/
            }

            // Temp; UI guys redo this
            case "!submitted" -> {
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

                channel.sendMessage("Getting Submitted Assignments").queue();
                try {
                    App.db.setPastSubmittedAss_AL(Database.pastSubmitted(App.db.getAllAss_AL()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                msgHandler.assmtsToMessages(App.db.getPastSubmittedAss_AL());
                msgHandler.print(event.getChannel());
                msgHandler.clear();
                /*for (int i = 0; i < App.db.getPastSubmittedAss_AL().size(); i++) {
                    channel.sendMessage(App.db.getPastSubmittedAss_AL().get(i).getAssName()).queue();
                }*/
            }
        }
    }
}
