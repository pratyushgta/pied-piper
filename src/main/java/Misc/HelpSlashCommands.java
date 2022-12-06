package Misc;

import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

/**
 * This class contains methods for displaying help page
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class HelpSlashCommands extends ListenerAdapter {
    int page;

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {

        if (event.getName().equals("help") || event.getName().equals("911")) {

            OptionMapping option = event.getOption("name");

            if (option == null) {

                // OptionMapping option = event.getOption("pageno");
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                //embed1.setTitle("Who is ñothïng.?");
                //embed1.setDescription("Pied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.\n\nPied Piper uses its own unique prefix: `-`, which, cannot be changed. All bot commands have to be prefixed with `-`.\n\nFacing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!\n\n");
                embed1.setDescription("Pied Piper works with Slash Commands! Checkout `-slash` or `/help slash` to know more!\n\n");//Facing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!

                embed1.addField("", "Use the arrow button to navigate through the categories!\n", false);
                embed1.addField("2. ▶️ Player Commands", "   Lists out all commands related to playing music", false);
                embed1.addField("3. \uD83D\uDD0A General Commands", "   All bot related commands", false);
                embed1.addField("4. \uD83C\uDFB6 Queue Commands", "   Commands related to queueing songs", false);
                embed1.addField("5. <:spotify:991956139258413096> Spotify Commands", "   Commands for Spotify features", false);
                embed1.addField("6. \uD83D\uDCE1 Streamer Commands", "   Commands for the Streamer Mode", false);
                embed1.addField("7. \uD83D\uDCA1 Misc Commands", "  Extra stuff which didn't fit in above categories", false);
                embed1.addField("", "\n\nNeed help with a particular command? Use `-help <command_name>` or `/help <commandname>`\n\nFound a bug? Help it smash by reporting it using `-report bug <your_description>`!", false);
                //embed1.addField("-misc", "Some extra commands which are not essential and made this list unnecessary long so i've added them to another list. *phew!*", false);

                page = 1;
                embed1.setColor(new Color(242, 202, 9));
                embed1.setFooter("Page: 1/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                event.replyEmbeds(embed1.build()).setEphemeral(true)
                        .addActionRow(Button.success("Pre", "⬅️"), Button.success("Nex", "➡️"), Button.link("https://discordapp.com/users/YOUR_DISCORD_ID/", "Butt Support")).queue();
            }
        }
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {

        if (event.getComponentId().equals("Pre")) {
            if (page == 1) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83D\uDCA1 Misc Commands");
                embed1.addField("/about", "Know more about the bot!", false);
                embed1.addField("/whatsnew", "Check out the update changelog", false);
                embed1.addField("/news", "See what's going on behind the scenes!", false);
                embed1.addField("/ping", "To check bot's latency", false);
                embed1.addField("/version", "To check for updates / current version", false);
                embed1.addField("/status", "To check the current status of the bot", false);
                embed1.addField("/report <bug/suggestion>", "To report bugs/ suggestions", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 7/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 7;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 2) {
                // OptionMapping option = event.getOption("pageno");
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                //embed1.setTitle("Who is ñothïng.?");
                embed1.setDescription("Pied Piper works with Slash Commands! Checkout `-slash` or `/help slash` to know more!\n\n");//Facing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!

                embed1.addField("", "Use the arrow button to navigate through the categories!\n", false);
                embed1.addField("2. ▶️ Player Commands", "   Lists out all commands related to playing music", false);
                embed1.addField("3. \uD83D\uDD0A General Commands", "   All bot related commands", false);
                embed1.addField("4. \uD83C\uDFB6 Queue Commands", "   Commands related to queueing songs", false);
                embed1.addField("5. <:spotify:991956139258413096> Spotify Commands", "   Commands for Spotify features", false);
                embed1.addField("6. \uD83D\uDCE1 Streamer Commands", "   Commands for the Streamer Mode", false);
                embed1.addField("7. \uD83D\uDCA1 Misc Commands", "  Extra stuff which didn't fit in above categories", false);
                embed1.addField("", "\n\nNeed help with a particular command? Use `-help <command_name>` or `/help <commandname>`\n\nFound a bug? Help it smash by reporting it using `-report bug <your_description>`!", false);
                //embed1.addField("-misc", "Some extra commands which are not essential and made this list unnecessary long so i've added them to another list. *phew!*", false);
                page = 1;
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 1/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 3) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("▶️ Player Commands");
                //  embed1.setDescription("Pied Piper uses its own unique prefix: '-', which, cannot be changed. All bot commands can be prefixed with '-'.\n");
                embed1.addField("/play <song_name / URL>", "Plays the song. duh!", false);
                // embed1.addField("-scplay <song name / URL>", "Plays the song from SoundCloud", false);
                //embed1.addField("/px <playlist_name / URL>", "To load a playlist from YouTube", false);
                //embed1.addField("/sp <track #>", "Plays the specified track from the search list", false);
                //embed1.addField("/qp <track #>", "Plays the specified track from the current queue", false);
                embed1.addField("/player", "Displays the music player", false);
                embed1.addField("/stop", "Stops the currently playing song and clears the queue", false);
                embed1.addField("/pause", "Pauses the currently playing song", false);
                embed1.addField("/resume", "Resumes the paused song", false);
                embed1.addField("/seek <hh:mm:ss> or <mm:ss>", "Seek to the specified timestamp", false);
                embed1.addField("/ff <seconds>", "Fast-forwards the currently playing track", false);
                embed1.addField("/rev <seconds>", "Reverses the currently playing track", false);
                embed1.addField("/vol", "To view current master volume", false);
                embed1.addField("/vol <number>", "To increase/ decrease the volume", false);
                //embed1.addField("/mute / -max", "To mute/ increase the volume to 100", false);
                //embed1.addField("/repeat", "Toggle a single song to repeat", false);
                embed1.addField("/loop", "Toggle a single song to repeat", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 2/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 2;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 4) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83D\uDD0A General Commands");
                embed1.addField("/join", "To invite the bot in a VC", false);
                embed1.addField("/dc", "To disconnect the bot from VC", false);
                embed1.addField("/rc", "Reconnects the bot to vc & clears cache", false);
                //embed1.addField("/np", "Shows details about currently playing song", false);
                embed1.addField("/skip", "Skip currently playing song", false);
                embed1.addField("/replay", "Replays the previously played track", false);
                embed1.addField("/lyrics", "To view the lyrics of currently playing song", false);
                embed1.addField("/lyrics <song_name>", "To view the lyrics of a song", false);
                embed1.addField("/search <song_name>", "To search a song on YouTube", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 3/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 3;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 5) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83C\uDFB6 Queue Commands");
                //embed1.addField("/queue", "Shows the current songs queue", false);
                embed1.addField("/queue view", "Shows the current songs queue", false);
                //embed1.addField("/queue <page #>", "Shows the specified page of the queue", false);
                embed1.addField("/clear", "Clears the queue", false);
                embed1.addField("/queue remove <track number | @username>", "Removes the specified track from the queue", false);
                embed1.addField("/queue move <song no.> <new position>", "Move a song to the specified position in the queue", false);
                embed1.addField("/queue shuffle", "Shuffles the songs in the queue", false);
                embed1.addField("/queue swap <track #> <swap with track #>", "Swap the two tracks in the queue", false);
                embed1.addField("/repeatall", "Toggle the queue to repeat", false);
                embed1.addField("", "Powered by BetterQueue.", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 4/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 4;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 6) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("<:spotify:991956139258413096> Spotify Commands");
                embed1.addField("/play <track_URL>", "Plays a Spotify track", false);
                embed1.addField("/play <playlist_URL>", "Loads the Spotify Playlist", false);
                embed1.addField("/spotify_profile <user_profile_URL>", "Fetches Spotify user details", false);
                embed1.addField("", "Listen on Spotify. Music for Everyone.", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 5/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 5;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 7) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83D\uDCE1 Streamer Commands");
                embed1.addField("/streamer info", "View streamer mode console", false);
                embed1.addField("/streamer <on/off>", "Toggle streamer mode", false);
                embed1.addField("", "Know more about the Streamer Mode by checking out the help page: `/help streamer`. Powered by Streamer Mode Lite [BETA v1.0.0]", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 6/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 6;
                event.editMessageEmbeds(embed1.build()).queue();
            }
        } else if (event.getComponentId().equals("Nex")) {
            if (page == 1) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("▶️ Player Commands");
                //  embed1.setDescription("Pied Piper uses its own unique prefix: '-', which, cannot be changed. All bot commands can be prefixed with '-'.\n");
                embed1.addField("/play <song_name / URL>", "Plays the song. duh!", false);
                // embed1.addField("-scplay <song name / URL>", "Plays the song from SoundCloud", false);
                //embed1.addField("/px <playlist_name / URL>", "To load a playlist from YouTube", false);
                //embed1.addField("/sp <track #>", "Plays the specified track from the search list", false);
                //embed1.addField("/qp <track #>", "Plays the specified track from the current queue", false);
                embed1.addField("/player", "Displays the music player", false);
                embed1.addField("/stop", "Stops the currently playing song and clears the queue", false);
                embed1.addField("/pause", "Pauses the currently playing song", false);
                embed1.addField("/resume", "Resumes the paused song", false);
                embed1.addField("/seek <hh:mm:ss> or <mm:ss>", "Seek to the specified timestamp", false);
                embed1.addField("/ff <seconds>", "Fast-forwards the currently playing track", false);
                embed1.addField("/rev <seconds>", "Reverses the currently playing track", false);
                embed1.addField("/vol", "To view current master volume", false);
                embed1.addField("/vol <number>", "To increase/ decrease the volume", false);
                //embed1.addField("/mute / -max", "To mute/ increase the volume to 100", false);
                //embed1.addField("/repeat", "Toggle a single song to repeat", false);
                embed1.addField("/loop", "Toggle a single song to repeat", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 2/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 2;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 2) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83D\uDD0A General Commands");
                embed1.addField("/join", "To invite the bot in a VC", false);
                embed1.addField("/dc", "To disconnect the bot from VC", false);
                embed1.addField("/rc", "Reconnects the bot to vc & clears cache", false);
                //embed1.addField("/np", "Shows details about currently playing song", false);
                embed1.addField("/skip", "Skip currently playing song", false);
                embed1.addField("/replay", "Replays the previously played track", false);
                embed1.addField("/lyrics", "To view the lyrics of currently playing song", false);
                embed1.addField("/lyrics <song_name>", "To view the lyrics of a song", false);
                embed1.addField("/search <song_name>", "To search a song on YouTube", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 3/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 3;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 3) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83C\uDFB6 Queue Commands");
                //embed1.addField("/queue", "Shows the current songs queue", false);
                embed1.addField("/queue view", "Shows the current songs queue", false);
                //embed1.addField("/queue <page #>", "Shows the specified page of the queue", false);
                embed1.addField("/clear", "Clears the queue", false);
                embed1.addField("/queue remove <track number | @username>", "Removes the specified track from the queue", false);
                embed1.addField("/queue move <song no.> <new position>", "Move a song to the specified position in the queue", false);
                embed1.addField("/queue shuffle", "Shuffles the songs in the queue", false);
                embed1.addField("/queue swap <track #> <swap with track #>", "Swap the two tracks in the queue", false);
                embed1.addField("/repeatall", "Toggle the queue to repeat", false);
                embed1.addField("", "Powered by BetterQueue.", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 4/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");

                page = 4;
                event.editMessageEmbeds(embed1.build()).queue();

            } else if (page == 4) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("<:spotify:991956139258413096> Spotify Commands");
                embed1.addField("/play <track_URL>", "Plays a Spotify track", false);
                embed1.addField("/play <playlist_URL>", "Loads the Spotify Playlist", false);
                embed1.addField("/spotify_profile <user_profile_URL>", "Fetches Spotify user details", false);
                embed1.addField("", "Listen on Spotify. Music for Everyone.", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 5/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                page = 5;
                event.editMessageEmbeds(embed1.build()).queue();
            } else if (page == 5) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83D\uDCE1 Streamer Commands");
                embed1.addField("/streamer info", "View streamer mode console", false);
                embed1.addField("/streamer <on/off>", "Toggle streamer mode", false);
                embed1.addField("", "Know more about the Streamer Mode by checking out the help page: `-help streamer`. Powered by Streamer Mode Lite [BETA v1.0.0]", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 6/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");

                page = 6;
                event.editMessageEmbeds(embed1.build()).queue();

            } else if (page == 6) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setTitle("\uD83D\uDCA1 Misc Commands");
                embed1.addField("/about", "Know more about the bot!", false);
                embed1.addField("/whatsnew", "Check out the update changelog", false);
                embed1.addField("/news", "See what's going on behind the scenes!", false);
                embed1.addField("/ping", "To check bot's latency", false);
                embed1.addField("/version", "To check for updates / current version", false);
                embed1.addField("/status", "To check the current status of the bot", false);
                embed1.addField("/report <bug/suggestion>", "To report bugs/ suggestions", false);
                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 7/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");

                page = 7;
                event.editMessageEmbeds(embed1.build()).queue();

            } else if (page == 7) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("911- Help is at hand!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                //embed1.setTitle("Who is ñothïng.?");
                embed1.setDescription("Pied Piper works with Slash Commands! Checkout `-slash` or `/help slash` to know more!\n\n");//Facing playback issues? Check your internet connection! Pied Piper relies on your network and WebSocket connections with Replit to bring you a High-Definition playback experience!

                embed1.addField("", "Use the arrow button to navigate through the categories!\n", false);
                embed1.addField("2. ▶️ Player Commands", "   Lists out all commands related to playing music", false);
                embed1.addField("3. \uD83D\uDD0A General Commands", "   All bot related commands", false);
                embed1.addField("4. \uD83C\uDFB6 Queue Commands", "   Commands related to queueing songs", false);
                embed1.addField("5. <:spotify:991956139258413096> Spotify Commands", "   Commands for Spotify features", false);
                embed1.addField("6. \uD83D\uDCE1 Streamer Commands", "   Commands for the Streamer Mode", false);
                embed1.addField("7. \uD83D\uDCA1 Misc Commands", "  Extra stuff which didn't fit in above categories", false);
                embed1.addField("", "\n\nNeed help with a particular command? Use `-help <command_name>` or `/help <commandname>`\n\nFound a bug? Help it smash by reporting it using `-report bug <your_description>`!", false);
                //embed1.addField("-misc", "Some extra commands which are not essential and made this list unnecessary long so i've added them to another list. *phew!*", false);

                embed1.setColor(new Color(242,202,9));
                embed1.setFooter("Page: 1/7 | Pied Piper | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");

                page = 1;
                event.editMessageEmbeds(embed1.build()).queue();

            }
        }
    }
}