package org.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Scanner;

public class MessageHandler
{
    public ArrayList<String> content = new ArrayList<>();
    private static final int MAX_CHAR_COUNT = 2000;
    private static final String assFormat = "[%s]->[%d]: %s - %s\n";
    private static final String courseFormat = "[%d]: %s\n";
    private int currentindex = 0;
    private int getSize() { return 0; }

    public  MessageHandler()
    {
        this.content.add("");
    }

    private String formatCourse(Course object)
    {
        //all messages need to be formatted using
        return String.format(courseFormat, object.getCourseID(), object.getCourseName());
    }

    private String formatAssignment(Assignment object)
    {
        //all messages need to be formatted using
        return String.format(assFormat, object.getCourseID(), object.getAssID(), object.getAssName(), object.getAssDate());
    }    

    public void coursesToMessages(ArrayList<Course> courses)
    {
        for (Course course : courses) {
            courseToMessage(course);
        }
    }

    public void assmtsToMessages(ArrayList<Assignment> assignments)
    {
        for (Assignment ass : assignments) {
            assToMessage(ass);
        }
    }

    private void courseToMessage(Course course)
    {
        String content = formatCourse(course);

        if ((content.length() + this.content.get(currentindex).length()) <= MAX_CHAR_COUNT)
        {
            this.content.set(currentindex, this.content.get(currentindex).concat(content));
        }
        else
        {
            this.currentindex++;
            this.content.add(content);
        }
    }

    private void assToMessage(Assignment assignment) {
        String content = formatAssignment(assignment);

        if ((content.length() + this.content.get(currentindex).length()) <= MAX_CHAR_COUNT)
        {
            this.content.set(currentindex, this.content.get(currentindex).concat(content));
        }
        else
        {
            this.currentindex++;
            this.content.add(content);
        }
    }

    /**
     * This gets the ArrayList of all the formatted objects
     *
     * @throws Exception if there is an error while loading the Course objects
     */
    public ArrayList<String> getAllMessages() throws Exception
    {
        return this.content;
    }
    /**
     * This gets the String of all the formatted objects at the ArrayList's index
     *
     * @param index this gets an element of the formatted ArrayList
     * @throws Exception if there is an error while loading the Course objects
     */
    public String getByIndex(int index)
    {
        return this.content.get(index);
    }

    public String getLines(int lines)
    {
        String incomingString;
        String compiledString = "";
        int totalLines = 0;
        Scanner getter;
        for(int arrayIndex = 0; arrayIndex <= currentindex && totalLines < lines; arrayIndex++)
        {
            getter = new Scanner(getByIndex(arrayIndex));
            while (getter.hasNextLine()
                        && (compiledString.length() + (incomingString = getter.nextLine()).length()) < MAX_CHAR_COUNT
                        && totalLines <= lines)
            {
                compiledString = compiledString.concat(incomingString);
                totalLines++;
            }
            getter.close();
        }
        return compiledString;
    }


    public void print(MessageChannel channel)
    {
        for (String s : this.content) {
            channel.sendMessage(s).queue();
        }
    }




}
