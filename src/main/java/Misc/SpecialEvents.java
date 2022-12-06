package Misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

/**
 * This class contains methods for handling special commands
 *
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SpecialEvents extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!(event.getMember().getUser().isBot())) {
            if (input[0].equalsIgnoreCase("-thankyou") || input[0].equalsIgnoreCase("-thanks")) {
                event.getChannel().sendMessage("Oh hoy! happy to be here for you :)").queue();
            } else if (input[0].equalsIgnoreCase("-ukraine") || input[0].equalsIgnoreCase("-war") || input[0].equalsIgnoreCase("-peace")) {

                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor("Pied Piper", "https://i.imgur.com/BMH6UcT.jpeg", "https://i.imgur.com/BMH6UcT.jpeg");
                eb.setThumbnail("https://img5.goodfon.com/original/1920x1080/7/fc/ukraine-flag-flag-of-ukraine-ukrainian-flag-ukrainian.jpg");
                eb.setTitle("Standing in solidarity with Ukraine");
                eb.setDescription("The war will end. The leaders will shake hands. But the old woman will keep waiting for her martyred son and those children will wait for their heroic father. Everyone behind Pied Piper stands united with the people of Ukraine, who are suffering in this aggression.");
                // eb.addField("```The world is organized murder, and nothing else.```","-Harry Patch, last surviving soldier of WWI",false);
                eb.setFooter("-LangoOr Bellic");
                eb.setColor(new Color(37, 135, 221));
                event.getChannel().sendMessageEmbeds(eb.build()).queue();
            }else if(input[0].equalsIgnoreCase("-p") || input[0].equalsIgnoreCase("-play") || input[0].equalsIgnoreCase("-j") || input[0].equalsIgnoreCase("-join")|| input[0].equalsIgnoreCase("-q") || input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-nowplaying") || input[0].equalsIgnoreCase("-np") || input[0].equalsIgnoreCase("-dc") || input[0].equalsIgnoreCase("-search") || input[0].equalsIgnoreCase("-vol") ) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("❗  Text Commands have been disabled!");
                eb.setDescription("Hello fellow listeners! Text commands in Pied Piper have been disabled due to performance issues, until next update! Check out `/news` for more info!\n\nUntil then, you can use Slash Commands! Know more using `/help slash`!");
                eb.setColor(new Color(242,202,9));
                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                /* else if(input[0].equalsIgnoreCase("-p") || input[0].equalsIgnoreCase("-play") || input[0].equalsIgnoreCase("-j") || input[0].equalsIgnoreCase("-join")|| input[0].equalsIgnoreCase("-q") || input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-nowplaying") || input[0].equalsIgnoreCase("-np") || input[0].equalsIgnoreCase("-dc") || input[0].equalsIgnoreCase("-search")){
                Random rand = new Random();
                int random2;
                random2 = rand.nextInt(71);
                if(random2==10||random2==20||random2==30||random2==40||random2==50){
                    EmbedBuilder eb=new EmbedBuilder();
                    eb.setTitle("United we Stand.");
                   // eb.setThumbnail("https://img5.goodfon.com/original/1920x1080/7/fc/ukraine-flag-flag-of-ukraine-ukrainian-flag-ukrainian.jpg");
                    eb.setDescription("Pied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
                    eb.setColor(new Color(242,202,9));
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            } else if (input[0].equalsIgnoreCase("-p") || input[0].equalsIgnoreCase("-play") || input[0].equalsIgnoreCase("-j") || input[0].equalsIgnoreCase("-join") || input[0].equalsIgnoreCase("-q") || input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-nowplaying") || input[0].equalsIgnoreCase("-np") || input[0].equalsIgnoreCase("-dc") || input[0].equalsIgnoreCase("-search")) {
                Random rand = new Random();
                int random2;
                random2 = rand.nextInt(51);
                if (random2 % 10 == 0) { //random2 == 10 || random2 == 20 || random2 == 30 || random2 == 40 || random2 == 50
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Your Feedback is hella Important!");
                    eb.setDescription("Facing playback issues? Bot not responding? Bugs?\n\nPied Piper is built upon user feedback.... and your feedback is really important! If you have anything to tell the developers, please share your feedback using `-report suggestion <your feedback>`. For further troubleshooting, slide into `LangoOr Bellic#7193`'s dms!");
                    eb.setColor(new Color(242,202,9));
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();

                    EmbedBuilder eb = new EmbedBuilder();
                   eb.setTitle("Pied Piper now has Slash Commands! \uD83D\uDE2E");
                   eb.setDescription("Apart from regular text commands, Pied Piper now has Slash commands too! \nType `/` to experience the new interactive Audio Player, menus, and much more! Aaandd...dw, text commands are here to stay.\n\nCheck out `-slash` to know more!");
                   eb.setColor(new Color(239,149,157));

                    eb.setTitle("\uD83C\uDFBA  Spotify is HERE!");
                    eb.setDescription("**The moment we've all been waiting for, is here!**\nNow seamlessly play all your **Spotify** tracks and playlists with 95% conversion accuracy!\n\nUse `-play <track/playlist URL>` to groove to your favourite Spotify tracks!");
                    eb.setThumbnail("https://storage.googleapis.com/pr-newsroom-wp/1/2018/11/Spotify_Logo_RGB_Green.png");
                    eb.setColor(new Color(21,86,80));
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();

                } else if (random2 == 60 || random2 == 70 || random2 == 80 || random2 == 90 || random2 == 100) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Pied Piper now has Slash Commands! \uD83D\uDE2E");
                    eb.setDescription("Apart from regular text commands, Pied Piper now has Slash commands too! Type `/` to experience the new interactive Audio Player, menus, and much more! Aaandd...dw, text commands are here to stay. Check out `-slash` to know more! Unable to use the Slash Commands? Re-invite the bot using the following link: <>");
                    eb.setColor(new Color(239,149,157));
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                }*/
            } else if (input[0].equalsIgnoreCase("hello") || input[0].equalsIgnoreCase("hi") || input[0].equalsIgnoreCase("hey")) {
                Random rand = new Random();
                String[] hello = {"Buna ziua", "hallo", "Përshëndetje", "ሰላም", "Բարեւ Ձեզ", "Salam", "হ্যালো", "kaixo", "добры дзень", "zdravo", "Здравейте", "မင်္ဂလာပါ", "Hola", "kumusta", "你好", "你好", "Bonghjornu", "zdravo", "Hej", "Hallo", "Hello", "Henlo"};
                int random1;
                random1 = rand.nextInt(50);
                String name = event.getMember().getUser().getName();
                while (random1 > hello.length) {
                    random1 = rand.nextInt(50);
                }
                event.getChannel().sendMessage(hello[random1] + " " + name + "!").queue();
            }
        }
    }
}

