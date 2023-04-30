package org.bot;

import java.util.ArrayList;

public class MessageHandler
{
    public ArrayList<String> content = new ArrayList<>();
    private static final int MAX_CHAR_COUNT = 2000;
    private static final String assFormat = "[%d]->[%d]: %s - %s\n";
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


    private void courseToMessage(Course course) {
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

    public ArrayList<String> getAllMessages()
    {
        return this.content;
    }

    public String getByIndex(int index)
    {
        return this.content.get(index);
    }




}
