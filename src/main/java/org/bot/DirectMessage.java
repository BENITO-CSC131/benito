package org.bot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class DirectMessage
{
    private User user = null;

    public DirectMessage(User user)//OBJECT MUST BE CONSTRUCTED WITH THE JDA USER TYPE AS THE PARAMETER
    {
        this.user = user;
    }

    public static boolean messageUser(User user,String message)//this is used to send a message without the need for an object
    {
        try {
            user.openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(message))
                    .queue();
            return true;
        }
        catch(InsufficientPermissionException e)
        {
            return false;
        }
    }

    public boolean messageUser(String message)//this is used to send the message with the object OBJECT MUST BE INSTANIATED FIRST OR IT WILL NOT WORK
    {
        try {
            this.user.openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(message))
                    .queue();
            return true;
        }
        catch(InsufficientPermissionException e)
        {
            return false;
        }
    }
}
