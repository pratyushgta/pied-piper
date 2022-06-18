package Misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class HelpSlashCommands extends ListenerAdapter {
    int page;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if (event.getName().equals("help") && Objects.equals(event.getSubcommandName(), "page") || event.getName().equals("911")) {

            // OptionMapping option = event.getOption("pageno");
            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            //embed1.setTitle("Who is ñothïng.?");
            //embed1.setDescription("Pied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.\n\nPied Piper uses its own unique prefix: `-`, which, cannot be changed. All bot commands have to be prefixed with `-`.\n\nFacing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!\n\n");
            embed1.setDescription("Pied Piper uses its own unique prefix: `-`, which, cannot be changed. All bot commands have to be prefixed with `-`. Pied Piper now also works with Slash Commands.\n\n");//Facing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!

            embed1.addField("", "Use ⬅️  ➡️  to navigate through the categories.\n", false);
            embed1.addField("2. ▶️ Player Commands", "   Lists out all commands related to playing music", false);
            embed1.addField("3. \uD83D\uDD0A General Commands", "   All bot related commands", false);
            embed1.addField("4. \uD83C\uDFB6 Queue Commands", "   Commands related to queueing songs", false);
            embed1.addField("5. \uD83D\uDCE1 Streamer Commands", "   Commands for the Streamer Mode", false);
            embed1.addField("6. \uD83D\uDCA1 Misc Commands", "  Extra stuff which didn't fit in above categories", false);
            embed1.addField("", "\n\nNeed help with a particular command? Use `-help <command_name>` or `/help command <commandname>`\n\nFound a bug? Help it smash by reporting it using `/report bug <your_description>`!", false);
            //embed1.addField("-misc", "Some extra commands which are not essential and made this list unnecessary long so i've added them to another list. *phew!*", false);

            page = 1;
            embed1.setColor(Color.yellow);
            embed1.setFooter("Page: 1/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            event.replyEmbeds(embed1.build()).setEphemeral(true)
                    .addActionRow(Button.success("Prev", "⬅️"), Button.success("Next", "➡️"), Button.link("https://discordapp.com/users/349907501829062656/","Butt Support")).queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        if (event.getComponentId().equals("Prev")) {
            if (page == 1) {
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
                page = 6;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 2) {
                // OptionMapping option = event.getOption("pageno");
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                //embed1.setTitle("Who is ñothïng.?");
                embed1.setDescription("Pied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.\n\nPied Piper uses its own unique prefix: `-`, which, cannot be changed. All bot commands have to be prefixed with `-`.\n\nFacing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!\n\n");

                embed1.addField("", "Use `-help <category #>` to view the commands in that category.\n", false);
                embed1.addField("2. ▶️ Player Commands", "   Lists out all commands related to playing music", false);
                embed1.addField("3. \uD83D\uDD0A General Commands", "   All bot related commands", false);
                embed1.addField("4. \uD83C\uDFB6 Queue Commands", "   Commands related to queueing songs", false);
                embed1.addField("5. \uD83D\uDCE1 Streamer Commands", "   Commands for the Streamer Mode", false);
                embed1.addField("6. \uD83D\uDCA1 Misc Commands", "  Extra stuff which didn't fit in above categories", false);
                embed1.addField("", "\n\nNeed help with a particular command? Use `-help <command_name>` or `/help <commandname>`\n\nFound a bug? Help it smash by reporting it using `-report bug <your_description>`!", false);
                //embed1.addField("-misc", "Some extra commands which are not essential and made this list unnecessary long so i've added them to another list. *phew!*", false);
                page = 1;
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 1/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 3) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("▶️ Player Commands");
                //  embed1.setDescription("Pied Piper uses its own unique prefix: '-', which, cannot be changed. All bot commands can be prefixed with '-'.\n");
                embed1.addField("-play <song_name / URL>", "Plays the song. duh!", false);
                // embed1.addField("-scplay <song name / URL>", "Plays the song from SoundCloud", false);
                embed1.addField("-px <playlist_name / URL>", "To load a playlist from YouTube", false);
                embed1.addField("-sp <track #>", "Plays the specified track from the search list", false);
                embed1.addField("-qp <track #>", "Plays the specified track from the current queue", false);
                embed1.addField("-stop", "Stops the currently playing song and clears the queue", false);
                embed1.addField("-pause", "Pauses the currently playing song", false);
                embed1.addField("-resume", "Resumes the paused song", false);
                embed1.addField("-seek <hh:mm:ss> or <mm:ss>", "Seek to the specified timestamp", false);
                embed1.addField("-ff <time>", "Fast-forwards the currently playing track", false);
                embed1.addField("-rev <time>", "Reverses the currently playing track", false);
                embed1.addField("-vol", "To view current master volume", false);
                embed1.addField("-vol <number>", "To increase/ decrease the volume", false);
                embed1.addField("-mute / -max", "To mute/ increase the volume to 100", false);
                embed1.addField("-repeat", "Toggle a single song to repeat", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 2/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                page = 2;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 4) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("\uD83D\uDD0A General Commands");
                embed1.addField("-join", "To invite the bot in a VC", false);
                embed1.addField("-dc", "To disconnect the bot from VC", false);
                embed1.addField("-rc", "Reconnects the bot to vc & clears cache", false);
                embed1.addField("-np", "Shows details about currently playing song", false);
                embed1.addField("-skip", "Skip currently playing song", false);
                embed1.addField("-replay", "Replays the previously played track", false);
                embed1.addField("-lyrics", "To view the lyrics of currently playing song", false);
                embed1.addField("-lyrics <song_name>", "To view the lyrics of a song", false);
                embed1.addField("-search <song_name>", "To search a song on YouTube", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 3/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                page = 3;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 5) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("\uD83C\uDFB6 Queue Commands");
                embed1.addField("-queue", "Shows the current songs queue", false);
                embed1.addField("-queue <page #>", "Shows the specified page of the queue", false);
                embed1.addField("-clear", "Clears the queue", false);
                embed1.addField("-remove <track number | @username>", "Removes the specified track from the queue", false);
                embed1.addField("-move <song no.> <new position>", "Move a song to the specified position in the queue", false);
                embed1.addField("-shuffle", "Shuffles the songs in the queue", false);
                embed1.addField("-swap <track #> <swap with track #>", "Swap the two tracks in the queue", false);
                embed1.addField("-repeat all", "Toggle the queue to repeat", false);
                embed1.addField("", "Powered by BetterQueue.", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 4/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                page = 4;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 6) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("\uD83D\uDCE1 Streamer Commands");
                embed1.addField("-streamer <on/off>", "Toggle streamer mode", false);
                embed1.addField("", "Know more about the Streamer Mode by checking out the help page: `-help streamer`. Powered by Streamer Mode Lite [BETA v1.0.0]", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 5/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                page = 5;
                event.editMessageEmbeds(embed1.build()).queue();

            }
        } else if (event.getComponentId().equals("Next")) {
            if (page == 1) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("▶️ Player Commands");
                //  embed1.setDescription("Pied Piper uses its own unique prefix: '-', which, cannot be changed. All bot commands can be prefixed with '-'.\n");
                embed1.addField("-play <song_name / URL>", "Plays the song. duh!", false);
                // embed1.addField("-scplay <song name / URL>", "Plays the song from SoundCloud", false);
                embed1.addField("-px <playlist_name / URL>", "To load a playlist from YouTube", false);
                embed1.addField("-sp <track #>", "Plays the specified track from the search list", false);
                embed1.addField("-qp <track #>", "Plays the specified track from the current queue", false);
                embed1.addField("-stop", "Stops the currently playing song and clears the queue", false);
                embed1.addField("-pause", "Pauses the currently playing song", false);
                embed1.addField("-resume", "Resumes the paused song", false);
                embed1.addField("-seek <hh:mm:ss> or <mm:ss>", "Seek to the specified timestamp", false);
                embed1.addField("-ff <time>", "Fast-forwards the currently playing track", false);
                embed1.addField("-rev <time>", "Reverses the currently playing track", false);
                embed1.addField("-vol", "To view current master volume", false);
                embed1.addField("-vol <number>", "To increase/ decrease the volume", false);
                embed1.addField("-mute / -max", "To mute/ increase the volume to 100", false);
                embed1.addField("-repeat", "Toggle a single song to repeat", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 2/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                page = 2;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 2) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("\uD83D\uDD0A General Commands");
                embed1.addField("-join", "To invite the bot in a VC", false);
                embed1.addField("-dc", "To disconnect the bot from VC", false);
                embed1.addField("-rc", "Reconnects the bot to vc & clears cache", false);
                embed1.addField("-np", "Shows details about currently playing song", false);
                embed1.addField("-skip", "Skip currently playing song", false);
                embed1.addField("-replay", "Replays the previously played track", false);
                embed1.addField("-lyrics", "To view the lyrics of currently playing song", false);
                embed1.addField("-lyrics <song_name>", "To view the lyrics of a song", false);
                embed1.addField("-search <song_name>", "To search a song on YouTube", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 3/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                page = 3;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 3) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("\uD83C\uDFB6 Queue Commands");
                embed1.addField("-queue", "Shows the current songs queue", false);
                embed1.addField("-queue <page #>", "Shows the specified page of the queue", false);
                embed1.addField("-clear", "Clears the queue", false);
                embed1.addField("-remove <track number | @username>", "Removes the specified track from the queue", false);
                embed1.addField("-move <song no.> <new position>", "Move a song to the specified position in the queue", false);
                embed1.addField("-shuffle", "Shuffles the songs in the queue", false);
                embed1.addField("-swap <track #> <swap with track #>", "Swap the two tracks in the queue", false);
                embed1.addField("-repeat all", "Toggle the queue to repeat", false);
                embed1.addField("", "Powered by BetterQueue.", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 4/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");

                page = 4;
                event.editMessageEmbeds(embed1.build()).queue();

            } else if (page == 4) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setTitle("\uD83D\uDCE1 Streamer Commands");
                embed1.addField("-streamer <on/off>", "Toggle streamer mode", false);
                embed1.addField("", "Know more about the Streamer Mode by checking out the help page: `-help streamer`. Powered by Streamer Mode Lite [BETA v1.0.0]", false);
                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 5/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");

                page = 5;
                event.editMessageEmbeds(embed1.build()).queue();

            } else if (page == 5) {
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

                page = 6;
                event.editMessageEmbeds(embed1.build()).queue();

            } else if (page == 6) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                //embed1.setTitle("Who is ñothïng.?");
                embed1.setDescription("Pied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.\n\nPied Piper uses its own unique prefix: `-`, which, cannot be changed. All bot commands have to be prefixed with `-`.\n\nFacing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!\n\n");

                embed1.addField("", "Use `-help <category #>` to view the commands in that category.\n", false);
                embed1.addField("2. ▶️ Player Commands", "   Lists out all commands related to playing music", false);
                embed1.addField("3. \uD83D\uDD0A General Commands", "   All bot related commands", false);
                embed1.addField("4. \uD83C\uDFB6 Queue Commands", "   Commands related to queueing songs", false);
                embed1.addField("5. \uD83D\uDCE1 Streamer Commands", "   Commands for the Streamer Mode", false);
                embed1.addField("6. \uD83D\uDCA1 Misc Commands", "  Extra stuff which didn't fit in above categories", false);
                embed1.addField("", "\n\nNeed help with a particular command? Use `-help <command_name>`\n\nFound a bug? Help it smash by reporting it using `-report bug <your_description>`!", false);
                //embed1.addField("-misc", "Some extra commands which are not essential and made this list unnecessary long so i've added them to another list. *phew!*", false);

                embed1.setColor(Color.yellow);
                embed1.setFooter("Page: 1/6 | Pied Piper | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");

                page = 1;
                event.editMessageEmbeds(embed1.build()).queue();

            }
        }
    }
}