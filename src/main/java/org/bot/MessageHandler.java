package org.bot;

public class MessageHandler
{
    public MessageHandler next;
    private int size = 0;
    private String incomingString;
    private static final int MAX_CHAR_COUNT = 2000;
    private static final String assFormat = "%d - %d - %s - %d";
    private static final String courseFormat = "%d - %s";
    private boolean atCapacity = false;

    public MessageHandler(MessageHandler next)
    {
        this.next = next;
    }

    public MessageHandler(String s)
    {
        //this constructor needs to take one of two things
        //the overflow of the previous MessageHandler
        //or
        //a new mass string object
        //NOTE: when grabbing the overflow, 
        //if it overflows more than once then break apart the string
        //link a new messagehandler then store the content recursively
        
        if (s.length() <= MAX_CHAR_COUNT)
        {
            this.incomingString = s;
            this.size = s.length();
        } 
        else
        {
            this.atCapacity = true;
            int startIndex = 0;
            int endIndex = MAX_CHAR_COUNT;
            while (endIndex < s.length()) 
            {
                String subStr = s.substring(startIndex, endIndex); //create substring
                MessageHandler newHandler = new MessageHandler(subStr); //new object created with substring recurses this method
                this.size += newHandler.getSize(); //unsure: check this one
                this.linkNextHandler(newHandler); //link the next handler to this object
                startIndex = endIndex; //start equal to 2000
                endIndex += MAX_CHAR_COUNT; //endIndex is now 4000
            }
            String lastSubStr = s.substring(startIndex);
            MessageHandler lastHandler = new MessageHandler(lastSubStr);
            this.size += lastHandler.getSize();
            this.linkNextHandler(lastHandler);
        }
    }

    private int getSize() { return 0; }

    private void linkNextHandler(MessageHandler nextHandler)
    {
        if (this.next == null) {
            this.next = nextHandler;
        } else {
            this.next.linkNextHandler(nextHandler);
        }
    }

    private String formatCourse(String content, Course object) 
    {
        //all messages need to be formatted using
        String.format(courseFormat, object.getClass(), content);
        return "";
    }

    private String formatAssignment(String content, Assignment object) 
    {
        //all messages need to be formatted using
        String.format(assFormat, object.getAssName(), content);
        return "";
    }    
    
    private MessageHandler newBuffer()
    {
        MessageHandler newHandler = new MessageHandler(incomingString);
        this.next = newHandler;
        return newHandler;
    }

    private void massLoad(String content)
    {
        //most likely makes use of newBuffer but roll over the string using a String class method using recursion
        int length = content.length();
        int start = 0;
        while (start < length)
        {
            int end = Math.min(start + MAX_CHAR_COUNT, length);
            String substring = content.substring(start, end);
            start = end;
        
            if (incomingString == null) 
            {
                incomingString = substring;
            } 
            else 
            {
                incomingString += substring;
            }
            
            if (incomingString.length() >= MAX_CHAR_COUNT)
            {
                newBuffer();
            }
        }
    }

    private String loadLine(String line)
    {
        //check that if you add the line it wont overflow if it does make a new buffer and set flag 
        int lineLength = line.length();
        if (size + lineLength > MAX_CHAR_COUNT)
        {
            MessageHandler newBuffer = new MessageHandler(incomingString);
            this.next = newBuffer;
            return newBuffer.loadLine(line);
        }
        else
        {
            incomingString += line;
            size += lineLength;
            if (size == MAX_CHAR_COUNT)
            {
                atCapacity = true;
            }
            return incomingString;
        }
        // return "";
    }
}
