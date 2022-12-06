package Misc;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

/**
 * This class contains methods for special promotions
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class Wish extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!(event.getMember()).getUser().isBot()) {
            for(int i=0;i<input.length;i++)
            {
                if (input[i].equalsIgnoreCase("merry") || input[i].equalsIgnoreCase("christmas") || input[i].equalsIgnoreCase("merry christmas") || input[i].equalsIgnoreCase("merrychristmas")){
                    event.getChannel().sendMessage("Merry Christmas lads! \uD83C\uDF85 Stop asking for a girlfriend from santa ✝️").queue();
                    break;
                }
            }
        }
    }
}