package Misc;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Objects;
import java.util.Random;

/**
 * This class contains methods for handling special commands
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */


public class SpecialEvents extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-thankyou") || input[0].equalsIgnoreCase("-thanks")){
                event.getChannel().sendMessage("Oh hoy! happy to be here for you :)").queue();
        }
            else if(input[0].equalsIgnoreCase("-hello") || input[0].equalsIgnoreCase("-hi") || input[0].equalsIgnoreCase("-hey")){
                {
                    Random rand = new Random();
                    String [] hello={"Buna ziua","hallo","Përshëndetje","ሰላም","Բարեւ Ձեզ","Salam","হ্যালো","kaixo","добры дзень","zdravo","Здравейте","မင်္ဂလာပါ","Hola","kumusta","你好","你好","Bonghjornu","zdravo","Hej","Hallo","Hello","Henlo"};
                    int random1;
                    random1 = rand.nextInt(50);
                    String name = event.getMember().getUser().getName();
                    while(random1>hello.length) {
                        random1 = rand.nextInt(50);
                    }
                    event.getChannel().sendMessage(hello[random1] + " " + name + "!").queue();
                }
            }
        }
    }
}