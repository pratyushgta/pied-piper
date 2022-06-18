package Misc;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class Wish extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            for(int i=0;i<input.length;i++)
            {
                if (input[i].equalsIgnoreCase("happy") || input[i].equalsIgnoreCase("new") || input[i].equalsIgnoreCase("year")){
                    event.getChannel().sendMessage("Yes, Merry Christmas and Happy Diwali :)").queue();
                    break;
                }
            }
        }
    }
}