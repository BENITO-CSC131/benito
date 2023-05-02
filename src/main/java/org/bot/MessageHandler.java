package org.bot;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MessageHandler
{
    public ArrayList<String> content = new ArrayList<>();
    private static final int MAX_CHAR_COUNT = 2000;
    private static final String assFormat = "[**%s**]->*%s* - %s\n";
    private static final String courseFormat = "***%s***\n";
    private int currentindex = 0;

    /**
     * Creates a MessageHandler object initializing the first element of the class' ArrayList
     * with a blank string
     *
     */
    public  MessageHandler()
    {
        this.content.add("");
    }

    /**
     * This returns the assignment's parent coursed based off if the id matches the course list provided
     *
     * @return The Course name
     * @throws NoSuchElementException If there is an error retrieving the course from the List
     */
    private String courseById(int assCourseId, ArrayList<Course> courses) throws NoSuchElementException
    {
        for (Course course : courses) {
            if (course.getCourseID() == assCourseId)
            {
                return course.getCourseName();
            }
        }
        return "[CourseNotFound]";
    }

    /**
     * Formats the course based on the available elements within the course,
     * any elements that are not available will be accounted for.
     *
     * @return The formatted course name
     * @throws NoSuchElementException If there is an error retrieving the course from the List
     */
    private String formatCourse(Course object) throws NoSuchElementException
    {
        //all messages need to be formatted using
        return String.format(courseFormat, object.getCourseName());
    }

    /**
     * Formats the assignment based on the available elements within the assignment object.
     *
     * @return Formatted String of assignment elements
     * @throws NoSuchElementException If there is an error retrieving the assignment from the List
     */
    private String formatAssignment(Assignment object) throws NoSuchElementException
    {
        //all messages need to be formatted using
        return String.format(assFormat, courseById(object.getCourseID(), App.db.getCourses_AL()), object.getAssName(), object.getAssDate());
    }

    /**
     * This takes in a list of courses and passes each course into the formatter courseToMessage
     *
     * @throws NoSuchElementException If there is an error retrieving the course from the List
     */
    public void coursesToMessages(ArrayList<Course> courses) throws NoSuchElementException
    {
        for (Course course : courses) {
            courseToMessage(course);
        }
    }

    /**
     * This takes in a list of assignments and passes each assignment into the formatter assToMessage
     *
     * @throws NoSuchElementException If there is an error retrieving the assignment from the List
     */
    public void assmtsToMessages(ArrayList<Assignment> assignments) throws NoSuchElementException
    {
        for (Assignment ass : assignments) {
            assToMessage(ass);
        }
    }

    /**
     * Takes the name of the course and formats it for Discord usage, then adds it to
     * this class' ArrayList.
     *
     * @throws NoSuchElementException If there is an error retrieving the course from the List
     */
    private void courseToMessage(Course course) throws NoSuchElementException
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

    /**
     * Formats the assignment and adds it to the list
     *
     * @throws NoSuchElementException If there is an error retrieving the assignment from the List
     */
    private void assToMessage(Assignment assignment) throws NoSuchElementException{
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
     * Prints the contents of the accumulated courses or assignments to the given channel
     *
     * @throws NoSuchElementException If there is an error retrieving the course from the API
     */
    public void print(MessageChannel channel)
    {
        for (String s : this.content) {
            channel.sendMessage(s).queue();
        }
    }

    /**
     * Clears the list of assignments or courses that were accumulated (often done to make room
     * for the next request)
     *
     */
    public void clear()
    {
        this.content.clear();
    }

    /*
      This gets the ArrayList of all the formatted objects

      @throws Exception if there is an error while loading the Course objects
     */
    /*public ArrayList<String> getAllMessages() throws Exception
    {
        return this.content;
    }*/
    /*
     * This gets the String of all the formatted objects at the ArrayList's index
     *
     */
    /*public String getByIndex(int index)
    {
        return this.content.get(index);
    }*/

    /*public String getLines(int lines)
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
    */
}
