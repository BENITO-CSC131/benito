package org.bot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class DirectMessage
{
    private User user = null;

    public DirectMessage(User user)
    {
        this.user = user;
    }

    public static boolean messageUser(User user,String message)
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

    public boolean messageUser(String message)
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
