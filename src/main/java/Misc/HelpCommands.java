package Misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.util.Objects;

/**
 * This class contains methods for displaying help page
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */


public class HelpCommands extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-help") ||input[0].equalsIgnoreCase("-911")||input[0].equalsIgnoreCase("-112")) {

                if(input.length==1 || input[1].equalsIgnoreCase("1"))
                {
                    event.getChannel().sendTyping().queue();
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    //embed1.setTitle("Who is ñothïng.?");
                    embed1.setDescription("Pied Piper uses its own unique prefix: `-`, which, cannot be changed. All bot commands have to be prefixed with `-`.\n\nFacing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!\n\n");

                    embed1.addField("","Use `-help <category #>` to view the commands in that category.\n",false);
                    embed1.addField("2. ▶️ Player Commands", "   Lists out all commands related to playing music", false);
                    embed1.addField("3. \uD83D\uDD0A General Commands", "   All bot related commands", false);
                    embed1.addField("4. \uD83C\uDFB6 Queue Commands", "   Commands related to queueing songs", false);
                    embed1.addField("5. \uD83D\uDCE1 Streamer Commands", "   Commands for the Streamer Mode", false);
                    embed1.addField("6. \uD83D\uDCA1 Misc Commands", "  Extra stuff which didn't fit in above categories", false);
                    embed1.addField("", "\n\nNeed help with a particular command? Use `-help <command_name>`", false);
                    //embed1.addField("-misc", "Some extra commands which are not essential and made this list unnecessary long so i've added them to another list. *phew!*", false);

                    // embed1.addField("", "⚠️Remember to invite the bot to a VC before asking it to playing a song! It's not a bug, but an in-built feature.\n\nType '-help <command> to view more information on a specific command.", false);
                    embed1.setColor(Color.yellow);
                    embed1.setFooter("Page: 1/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    event.getChannel().sendMessage(embed1.build()).queue();
                    embed1.clear();

                }
                else if (input.length == 2 && input[1].equalsIgnoreCase("2") || input.length == 3 && input[1].equalsIgnoreCase("player") && input[2].equalsIgnoreCase("commands")) {
                    event.getChannel().sendTyping().queue();
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    embed1.setTitle("▶️ Player Commands");
                  //  embed1.setDescription("Pied Piper uses its own unique prefix: '-', which, cannot be changed. All bot commands can be prefixed with '-'.\n");
                    embed1.addField("-play <song_name / URL>", "Plays the song. duh!", false);
                    // embed1.addField("-scplay <song name / URL>", "Plays the song from SoundCloud", false);
                    embed1.addField("-px <song_name / URL>", "To load a playlist from YouTube", false);
                    embed1.addField("-sp <track #>", "Plays the specified track from the search list", false);
                    embed1.addField("-qp <track #>", "Plays the specified track from the current queue", false);
                    embed1.addField("-stop", "Stops the currently playing song and clears the queue", false);
                    embed1.addField("-pause", "Pauses the currently playing song", false);
                    embed1.addField("-resume", "Resumes the paused song", false);
                    embed1.addField("-ff <time>", "Fast-forwards the currently playing track", false);
                    embed1.addField("-rev <time>", "Reversed the currently playing track", false);
                    embed1.addField("-vol", "To view current master volume", false);
                    embed1.addField("-vol <number>", "To increase/ decrease the volume", false);
                    embed1.addField("-mute / -max", "To mute/ increase the volume to 100", false);
                    embed1.addField("-repeat", "Toggle a single song to repeat", false);
                    embed1.setColor(Color.yellow);
                    embed1.setFooter("Page: 2/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    event.getChannel().sendMessage(embed1.build()).queue();
                    embed1.clear();
                }
                else if (input.length == 2 && input[1].equalsIgnoreCase("3") || input.length == 3 && input[1].equalsIgnoreCase("general") && input[2].equalsIgnoreCase("commands")) {
                    event.getChannel().sendTyping().queue();
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    embed1.setTitle("\uD83D\uDD0A General Commands");
                    embed1.addField("-join", "To invite the bot in a VC", false);
                    embed1.addField("-dc", "To disconnect the bot from VC", false);
                    embed1.addField("-rc", "Reconnects the bot to vc & clears cache", false);
                    embed1.addField("-np", "Shows details about currently playing song", false);
                    embed1.addField("-skip", "Skip currently playing song", false);
                    embed1.addField("-lyrics", "To view the lyrics of currently playing song", false);
                    embed1.addField("-lyrics <song_name>", "To view the lyrics of a song", false);
                    embed1.addField("-search <song_name>", "To search a song on YouTube", false);
                    embed1.setColor(Color.yellow);
                    embed1.setFooter("Page: 3/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    event.getChannel().sendMessage(embed1.build()).queue();
                    embed1.clear();
                }
                else if (input.length == 2 && input[1].equalsIgnoreCase("4") || input.length == 3 && input[1].equalsIgnoreCase("queue") && input[2].equalsIgnoreCase("commands")) {
                    event.getChannel().sendTyping().queue();
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    embed1.setTitle("\uD83C\uDFB6 Queue Commands");
                    embed1.addField("-queue", "Shows the current songs queue", false);
                    embed1.addField("-queue <page #>", "Shows the specified page of the queue", false);
                    embed1.addField("-clear", "Clears the queue", false);
                    embed1.addField("-remove <track number | @username>", "Removes the specified track from the queue", false);
                    embed1.addField("-move <song no.> <new position>", "Move a song to the specified position in the queue", false);
                    embed1.addField("-shuffle", "Shuffles the songs in the queue", false);
                    embed1.addField("-repeat all", "Toggle the queue to repeat", false);
                    embed1.addField("","Powered by BetterQueue.",false);
                    embed1.setColor(Color.yellow);
                    embed1.setFooter("Page: 4/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    event.getChannel().sendMessage(embed1.build()).queue();
                    embed1.clear();
                } else if (input.length == 2 && input[1].equalsIgnoreCase("5") || input.length == 3 && input[1].equalsIgnoreCase("Streamer") && input[2].equalsIgnoreCase("commands")) {
                    event.getChannel().sendTyping().queue();
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    embed1.setTitle("\uD83D\uDCE1 Streamer Commands");
                    embed1.addField("-streamer <on/off>", "Toggle streamer mode", false);
                    embed1.addField("","Know more about the Streamer Mode by checking out the help page: `-help streamer`. Powered by Streamer Mode Lite [BETA v1.0.0]",false);
                    embed1.setColor(Color.yellow);
                    embed1.setFooter("Page: 5/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    event.getChannel().sendMessage(embed1.build()).queue();
                    embed1.clear();
                }else if (input.length == 2 && input[1].equalsIgnoreCase("6") || input.length == 3 && input[1].equalsIgnoreCase("Misc") && input[2].equalsIgnoreCase("commands")) {
                    event.getChannel().sendTyping().queue();
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    embed1.setTitle("\uD83D\uDCA1 Misc Commands");
                    embed1.addField("-about", "Know more about the bot!", false);
                    embed1.addField("-whatsnew", "Check out the update changelog", false);
                    embed1.addField("-news", "See what's going on behind the scenes!", false);
                    embed1.addField("-ping", "To check bot's latency", false);
                    embed1.addField("-version", "To check for updates / current version", false);
                    embed1.addField("-status", "To check the current status of the bot", false);
                    embed1.addField("-report <bug/suggestion>", "To report bugs/ suggestions", false);
                    embed1.setColor(Color.yellow);
                    embed1.setFooter("Page: 6/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    event.getChannel().sendMessage(embed1.build()).queue();
                    embed1.clear();
                }
            }
        }
    }
}