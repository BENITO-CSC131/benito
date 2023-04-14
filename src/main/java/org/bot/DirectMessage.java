/*
DirectMessage Class

1 Constructor 2 Methods

1st Method: messageUser(User user,String message) 
    
    this can be easily called with DirectMessage.messageUser(  ,  );
    

User must come from net.dv8tion.jda.api.entities.User class (this jda class can be found here: https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/entities/User.html)

2nd Method: messageUser(String message)
    
    requires a call from constructor like so ------> DirectMessage msg = new DirectMessage(User) comes from user class above
*/
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
