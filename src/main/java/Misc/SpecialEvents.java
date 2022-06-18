package Misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class SpecialEvents extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-thankyou") || input[0].equalsIgnoreCase("-thanks")) {
                event.getChannel().sendMessage("Oh hoy! happy to be here for you :)").queue();
            } else if (input[0].equalsIgnoreCase("-ukraine") || input[0].equalsIgnoreCase("-war") || input[0].equalsIgnoreCase("-peace")) {

                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor("Pied Piper", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                eb.setThumbnail("https://img5.goodfon.com/original/1920x1080/7/fc/ukraine-flag-flag-of-ukraine-ukrainian-flag-ukrainian.jpg");
                eb.setTitle("Standing in solidarity with Ukraine");
                eb.setDescription("The war will end. The leaders will shake hands. But the old woman will keep waiting for her martyred son and those children will wait for their heroic father. Everyone behind Pied Piper stands united with the people of Ukraine, who are suffering in this aggression.");
                // eb.addField("```The world is organized murder, and nothing else.```","-Harry Patch, last surviving soldier of WWI",false);
                eb.setFooter("-LangoOr Bellic");
                eb.setColor(Color.BLUE);
                event.getChannel().sendMessageEmbeds(eb.build()).queue();
            }/* else if(input[0].equalsIgnoreCase("-p") || input[0].equalsIgnoreCase("-play") || input[0].equalsIgnoreCase("-j") || input[0].equalsIgnoreCase("-join")|| input[0].equalsIgnoreCase("-q") || input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-nowplaying") || input[0].equalsIgnoreCase("-np") || input[0].equalsIgnoreCase("-dc") || input[0].equalsIgnoreCase("-search")){
                Random rand = new Random();
                int random2;
                random2 = rand.nextInt(71);
                if(random2==10||random2==20||random2==30||random2==40||random2==50){
                    EmbedBuilder eb=new EmbedBuilder();
                    eb.setTitle("United we Stand.");
                   // eb.setThumbnail("https://img5.goodfon.com/original/1920x1080/7/fc/ukraine-flag-flag-of-ukraine-ukrainian-flag-ukrainian.jpg");
                    eb.setDescription("Pied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
                    eb.setColor(Color.yellow);
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            } */ else if (input[0].equalsIgnoreCase("-p") || input[0].equalsIgnoreCase("-play") || input[0].equalsIgnoreCase("-j") || input[0].equalsIgnoreCase("-join") || input[0].equalsIgnoreCase("-q") || input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-nowplaying") || input[0].equalsIgnoreCase("-np") || input[0].equalsIgnoreCase("-dc") || input[0].equalsIgnoreCase("-search")) {
                Random rand = new Random();
                int random2;
                random2 = rand.nextInt(51);
                if (random2 % 5 == 0) { //random2 == 10 || random2 == 20 || random2 == 30 || random2 == 40 || random2 == 50
                   /* EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Your Feedback is hella Important!");
                    eb.setDescription("Facing playback issues? Bot not responding? Bugs?\n\nPied Piper is built upon user feedback.... and your feedback is really important! If you have anything to tell the developers, please share your feedback using `-report suggestion <your feedback>`. For further troubleshooting, slide into `LangoOr Bellic#7193`'s dms!");
                    eb.setColor(Color.yellow);
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();*/

                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Pied Piper now has Slash Commands! \uD83D\uDE2E");
                    eb.setDescription("Apart from regular text commands, Pied Piper now has Slash commands too! \nType `/` to experience the new interactive Audio Player, menus, and much more! Aaandd...dw, text commands are here to stay.\n\nCheck out `-slash` to know more!");
                    eb.setColor(Color.PINK);
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();

                } /*else if (random2 == 60 || random2 == 70 || random2 == 80 || random2 == 90 || random2 == 100) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Pied Piper now has Slash Commands! \uD83D\uDE2E");
                    eb.setDescription("Apart from regular text commands, Pied Piper now has Slash commands too! Type `/` to experience the new interactive Audio Player, menus, and much more! Aaandd...dw, text commands are here to stay. Check out `-slash` to know more! Unable to use the Slash Commands? Re-invite the bot using the following link: <>");
                    eb.setColor(Color.PINK);
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
            } else if (input[0].equalsIgnoreCase("-slash") || input[0].equalsIgnoreCase("-commands")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("/ Slash Commands","https://discord.com/api/oauth2/authorize?client_id=894494474149957632&permissions=8&scope=applications.commands%20bot");

                eb.setDescription("Slash Commands are the new way to interact with Pied Piper and other bots. With Slash Commands, Pied Piper now has interactive buttons^ and menus^.\n\nWith the new Audio Player*, " +
                        "you can now control your tracks, effortlessly without having to input the commands multiple times. Audio Player allows you to skip, stop, pause, resume, replay, repeat and disconnect, all from a single embed." +
                        "\n\n Here are the new interactive buttons introduced across the bot: ");
                eb.addField("▶️", "Play / Resume", true);
                eb.addField("⏸️", "Pause", true);
                eb.addField("⏮️", "Replay", true);
                eb.addField("⏭️", "Next Track / Skip", true);
                eb.addField("\uD83D\uDD02", "Repeat once", true);
                eb.addField("\uD83D\uDD01", "Repeat Queue", true);
                eb.addField("⏹️", "Stop", true);
                eb.addField("⬅️", "Previous Page", true);
                eb.addField("➡️", "Next Page", true);
                eb.addField("➡️", "Next Page", true);
                eb.addField("⭕️", "Clear Queue", true);
                eb.addField("\uD83D\uDD00", "Shuffle Queue", true);
                eb.addField("⟳", "Refresh Page", true);
                eb.addField("\uD83D\uDCE1", "Toggle Streamer Mode", true);
                eb.addField("\uD83D\uDC4B", "Leave VC/ Disconnect", true);
                eb.addField("","* Audio PLayer is only available with Slash Commands. Type `/player` to access it while listening to music.\n" +
                        "\n^ Interactive buttons and menus only accessible with Slash Commands",false);
                eb.setFooter("Unable to use Slash Commands? Click the on title to re-invite the bot.", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");

                eb.setColor(Color.ORANGE);
                event.getChannel().sendMessageEmbeds(eb.build()).queue();
            }
        }
    }
}

