package Misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


/**
 * This class contains methods for displaying detailed help pages of commands
 *
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class DetailedHelp extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-help") && input.length > 1 || input[0].equalsIgnoreCase("-911") && input.length > 1) {
                if (input[1].equalsIgnoreCase("play")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Play command");
                    embed1.setDescription("Searches for a song on YouTube or Spotify and plays it. To play a Spotify track, enter the Track URL and not just the track ID." +
                            "Similarly, to play Spotify Playlist, enter the Playlist URL.");
                    embed1.addField("Aliases:", "`play` | `p` | `add`", true);
                    embed1.addField("Syntax:", "`-play <song_name>` / `-play <URL>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("spotify") || input[1].equalsIgnoreCase("spotify_user")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Spotify User Profile Command");
                    embed1.setDescription("Fetches an user's Spotify account details. Only Playlist information is currently retrieved by this command." +
                            "To search for a user, enter their profile URL.");
                    embed1.addField("Aliases:", "`spotify_user`", true);
                    embed1.addField("Syntax:", "`-spotify_user <profile_URL>` / `-play <URL>`", true);
                    embed1.setThumbnail("https://storage.googleapis.com/pr-newsroom-wp/1/2018/11/Spotify_Logo_CMYK_Green.png");
                    embed1.setFooter("Listen on Spotify. Music for Everyone.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("pause")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Pause command");
                    embed1.setDescription("Pauses a currently playing song.");
                    embed1.addField("Aliases: ", "`pause`", true);
                    embed1.addField("Syntax:", "`-pause`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("scplay")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("SoundCloud command");
                    embed1.setDescription("Search & play a music from SoundCloud\n⚠️This command is currently made unavailable since it would have led to a compromised experience that does not meet our standards.");
                    embed1.addField("Aliases:", "`scplay`", true);
                    embed1.addField("Syntax:", "`-scplay <song_name>` / `-scplay <URL>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } /*else if (input[1].equalsIgnoreCase("px")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Playlist command");
                    embed1.setDescription("Adds songs in the specified YouTube playlist, to the queue");
                    embed1.addField("Aliases:", "`px` | `playlist`", true);
                    embed1.addField("Syntax:", "`-px <song_name>` / `-playlist <URL>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                }*/ else if (input[1].equalsIgnoreCase("search")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Search command");
                    embed1.setDescription("Searches for a song on YouTube, and lists out the best possible results. To ensure accuracy, at any given point, only first 5 queries are returned. Search list empties as soon as any command other than Play-from-Search command is used.");
                    embed1.addField("Aliases:", "`search`", true);
                    embed1.addField("Syntax:", "`-search <search_term>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } /*else if (input[1].equalsIgnoreCase("sp")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Play-from-Search command");
                    embed1.setDescription("To play a song from the returned Search list. This command won't work if the Search list is empty.");
                    embed1.addField("Aliases:", "`sp`", true);
                    embed1.addField("Syntax:", "`-sp <track_number_from_search_list>`", true);
                    embed1.setColor(new Color(216,213,190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
            }*/ else if (input[1].equalsIgnoreCase("queue play")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Queue Play command");
                    embed1.setDescription("Skips the current playing song and plays the specified track number from the queue.");
                    embed1.addField("Aliases:", "`qp`", true);
                    embed1.addField("Syntax:", "`-qp <track_number_from_queue>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("resume")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Resume command");
                    embed1.setDescription("Bruh. It resumes a paused song. As simple as that!");
                    embed1.addField("Aliases:", "`resume`", true);
                    embed1.addField("Syntax:", "`-resume`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("ff")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Fast-forward command");
                    embed1.setDescription("Fast forwards a song by specified time. If no time is provided, it fast-forwards the song by 20 seconds.");
                    embed1.addField("Aliases:", "`ff` | `fastforward` | `forward` | `fast`", true);
                    embed1.addField("Syntax:", "`-ff <time>` / `-ff`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("rev")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Reverse command");
                    embed1.setDescription("Reverses a song by specified time. If no time is provided, it reversed the song by 20 seconds.");
                    embed1.addField("Aliases:", "`rev` | `reverse`", true);
                    embed1.addField("Syntax:", "`-rev <time>` / `-rev`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("vol") || input[1].equalsIgnoreCase("volume")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Volume command");
                    embed1.setDescription("To view, increase or decrease the player volume. Volume can be set anywhere between 0 to 100. You can view the current volume by typing the suffix without a number." +
                            "\n**Representations:**\n\uD83D\uDD07 - Mute\n\uD83D\uDD08 - Volume less than 30\n\uD83D\uDD09 - Volume less than 70\n\uD83D\uDD0A - Max Volume / Volume > 70\n");
                    embed1.addField("Aliases:", "`vol` | `v` | `max` | `mute` | ", true);
                    embed1.addField("Syntax:", "`-vol <number>` | `-vol` | `-mute`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("streamer")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Streamer Mode Command");
                    embed1.setDescription("Streamer Mode is an exclusive feature to prevent copyright stikes while someone is Twtich Streaming. \nWhen the Streamer Mode gets activated, the audio player disables for everyone in the VC. This prevents copyrighted music being played in a shared vc that is being streamed.\n" +
                            "\n**Representations:**\n\uD83D\uDCE1 - Streamer Mode ON\n\n");
                    embed1.addField("Aliases:", "`streamer` | `stream`", true);
                    embed1.addField("Syntax:", "`-streamer on` | `-stream off`", true);
                    embed1.setFooter("Streamer Mode [BETA v1.0.0]");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("about")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("About command");
                    embed1.setDescription("Shows information about the bot- what it is and what it does.");
                    embed1.addField("Aliases:", "`about`", true);
                    embed1.addField("Syntax:", "`-about`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("whatsnew")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("What's New command");
                    embed1.setDescription("Shows the official update changelog.");
                    embed1.addField("Aliases:", "`new` | `whatsnew` | `wutnew`", true);
                    embed1.addField("Syntax:", "`-new`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("ping")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Ping command");
                    embed1.setDescription("To check bot's latency & WebSocket connection ping.");
                    embed1.addField("Aliases:", "`ping`", true);
                    embed1.addField("Syntax:", "`-ping`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("join")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Join command");
                    embed1.setDescription("To make the bot join your voice channel.");
                    embed1.addField("Aliases:", "`join` | `summon` | `j`", true);
                    embed1.addField("Syntax:", "`-join`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("dc")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Leave/ Disconnect command");
                    embed1.setDescription("To disconnect a bot from the voice channel it was playing in. The bot auto-disconnects from the VC as soon as the last user leaves the VC.");
                    embed1.addField("Aliases:", "`dc` | `leave` | `die` | `l`", true);
                    embed1.addField("Syntax:", "`-dc`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("player")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Now-Playing command");
                    embed1.setDescription("Shows details about the song currently playing in the voice channel. Besides, it also indicates if repeat or streamer modes are on or not.");
                    embed1.addField("Aliases:", "`np` | `nowplaying` | `now` | `playing` | `n`", true);
                    embed1.addField("Syntax:", "`-np`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("loop")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Repeat command");
                    embed1.setDescription("To toggle repeat mode for the song which is currently playing." +
                            "\n**Representations:**\n\uD83D\uDD02 - Single Repeat Mode ON\n");
                    embed1.addField("Aliases:", "`repeat` | `loop`", true);
                    embed1.addField("Syntax:", "`-repeat` | `-repeat <on`/`off>`", true);
                    embed1.setColor(new Color(216,213,190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("repeatall")) { //&& input[2].equalsIgnoreCase("all")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Repeat All / Repeat Queue command");
                    embed1.setDescription("To repeat the whole queue. New songs can still be added to the queue while this mode is on." +
                            "\n**Representations:**\n\uD83D\uDD01 - Repeat Queue Mode ON\n");
                    embed1.addField("Aliases:", "`repeat all` | `loop all`", true);
                    embed1.addField("Syntax:", "`-repeat all` | `-repeat all <on`/`off>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("skip")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Skip command");
                    embed1.setDescription("To skip the currently playing song. If queue repeat is on, the song gets added back to the queue.");
                    embed1.addField("Aliases:", "`skip` | `next`", true);
                    embed1.addField("Syntax:", "`-skip`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("queue view")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Queue command");
                    embed1.setDescription("To view the current playlist of songs. If the queue has more than 1 page, you can use `-q <page number> to view the specified page of the queue.\nOnce all tracks of a queue have been played, it can be replayed using `-p` command. The previous queue cannot be replayed if a new track is added to the current queue.\n\n" +
                            "**Other Queue Commands:**\n1. `/queue play`\n2. `/queue move`\n3. `/queue remove`\n4. `/queue shuffle`\n5. `/queue swap`\n6. `/clear`\n7. `/repeatll`");
                    embed1.addField("Aliases:", "`queue` | `q` | `list`", true);
                    embed1.addField("Syntax:", "`-queue` | `-q 2`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("clear")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Clear queue command");
                    embed1.setDescription("To clear the current queue without stopping the currently playing song.");
                    embed1.addField("Aliases:", "`clear`", true);
                    embed1.addField("Syntax:", "`-clear`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("stop")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Stop command");
                    embed1.setDescription("Stops the currently playing song in the voice channel and clears the queue. This bot remains in the vc.");
                    embed1.addField("Aliases:", "`stop`", true);
                    embed1.addField("Syntax:", "`-stop`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("queue remove")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Remove command");
                    embed1.setDescription("Added a song by mistake? No worries! You can use this command to remove a specific song from the queue.");
                    embed1.addField("Aliases:", "`remove`", true);
                    embed1.addField("Syntax:", "`-remove <track number / @username>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("queue move")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Move command");
                    embed1.setDescription("This command is used to move a specific song in the queue to the specified new position");
                    embed1.addField("Aliases:", "`move`", true);
                    embed1.addField("Syntax:", "`-move <track #> <new position>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("lyrics")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Lyrics command");
                    embed1.setDescription("You can view the lyrics of a currently playing song or any other specific song. For best results, enter song name and artist name when using text commands.");
                    embed1.addField("Aliases:", "`lyrics`", true);
                    embed1.addField("Syntax:", "``-lyrics <song_name> , <artist_name> | /lyrics <song_name | /lyrics <artist_name>`", true);
                    embed1.setFooter("Lyrics are brought to you by Musixmatch", "https://pbs.twimg.com/profile_images/1194637308244430848/JUj4SljE_400x400.jpg");
                    //embed1.setFooter("Lyrics are brought to you by A-Z Lyrics.","https://download.cnet.com/a/img/resize/ccba7cc1e86ee344022b6807533bac63cededbef/catalog/2019/12/10/3eeaa564-0594-4c15-b238-535242e80227/imgingest-1293706355108209810.jpg?auto=webp&fit=crop&height=675&width=1200");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("queue shuffle")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Shuffle command");
                    embed1.setDescription("You can use this to shuffle the songs in the queue. This command will fail to work if there is only 1 song or no song in the queue.");
                    embed1.addField("Aliases:", "`shuffle`", true);
                    embed1.addField("Syntax:", "`-shuffle`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("news")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("News & Updates command");
                    embed1.setDescription("You can check out all the upcoming cool stuff, planned for the Pied Piper!");
                    embed1.addField("Aliases:", "`news` | `updates`", true);
                    embed1.addField("Syntax:", "`-news`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("rc")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Reconnect command");
                    embed1.setDescription("Facing playback issues? No worries! This command disconnects the bot from the vc and reconnects it back. Any music playing which the bot disconnected will automatically resume form where it left off.");
                    embed1.addField("Aliases:", "`reconnect` | `rc`", true);
                    embed1.addField("Syntax:", "`-rc`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("version")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Version-check command");
                    embed1.setDescription("Using this, you can check for any available bot updates, current bot version and versions of Bot utilities: Lavaplayer and JDA.");
                    embed1.addField("Aliases:", "`version`", true);
                    embed1.addField("Syntax:", "`-version`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("status")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Check status command");
                    embed1.setDescription("You can check the current status of bot and if there are any scheduled maintenance breaks coming up.");
                    embed1.addField("Aliases:", "`status`", true);
                    embed1.addField("Syntax:", "`-status`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("report")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Bug/ Suggestions Reporting Command");
                    embed1.setDescription("Reporting bugs or suggestions has gotten a lot more easier! You can use the following two commands to report your bugs or suggestions:\n\n1. `-report bug <your_query>`\n2. `-report suggestion <your_query>`\n\nEvery report sent out gets an unique Report number which is used to identify your report. It is like an user identification number which allows us to seamlessly retrieve your report." +
                            "You can use this Report number to get in touch with Pied Piper Support#1168 regarding your query.\n\nAdditionally, if you want to share further details in the form of screenshots, regarding your query, you can dm them to Pied Piper Support#1168. Don't forget to mention your Report number!\n\nFor new suggestions/ bug reports, please use the inbuilt -report command.\nAny reports which are irrelevant, will be discarded automatically. Spamming the Piped Piper Support might ban you from sending out new reports.");
                    embed1.addField("Aliases:", "`report`", true);
                    embed1.addField("Syntax:", "`-report <bug / suggestion> <your_description>`", true);
                    embed1.addField("Example:", "`-report bug the bug flies off and comes back down send haalp asap`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("replay")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Replay Command");
                    embed1.setDescription("To replay the previous track. This command will not work if there is no previously played track.");
                    embed1.addField("Aliases:", "`replay`", true);
                    embed1.addField("Syntax:", "`-replay`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("seek")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Seek Command");
                    embed1.setDescription("To seek to the specified time stamp. Time stamps provided can be in two formats:\n1. HH:MM:SS\n2.MM:SS\n\nThe timestamp has to be colon `:` separated integers, without any whitespace in between. If the provided timestamp exceeds the track length, then an exception will be thrown.");
                    embed1.addField("Aliases:", "`seek`", true);
                    embed1.addField("Syntax:", "`-seek <hh:mm:ss / mm:ss>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("queue swap")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Swap Command");
                    embed1.setDescription("It can swap two tracks in the queue. This command accepts two arguments:\n1. The track to swap\n2. Track # to swap the first track with\n\nThis command will fail to work if there are no tracks or only 1 track in the queue.");
                    embed1.addField("Aliases:", "`swap`", true);
                    embed1.addField("Syntax:", "`-swap <track #> <swap with track #>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                    event.getChannel().sendTyping().queue();
                    embed1.clear();
                } else if (input[1].equalsIgnoreCase("slash") || input[1].equalsIgnoreCase("commands")) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("/ Slash Commands", "https://discord.com/api/oauth2/authorize?client_id=894494474149957632&permissions=8&scope=applications.commands%20bot");

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
                    eb.addField("", "* Audio Player is only available with Slash Commands. Type `/player` to access it while listening to music.\n" +
                            "\n^ Interactive buttons and menus only accessible with Slash Commands", false);
                    eb.setFooter("Unable to use Slash Commands? Click the on title to re-invite the bot.", "https://i.imgur.com/BMH6UcT.jpeg");

                    eb.setColor(new Color(216, 213, 190));
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();

                }
            }
        }
    }


    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {

        if (event.getName().equals("help")) {

            OptionMapping option = event.getOption("name");

            if (option != null) {


                //OptionMapping option = event.getOption("name");

            /*if (option == null) {
                event.reply("⚠️Uh oh! Something went wrong!").queue();
                return;
            }*/
                String cmd = option.getAsString();

                if (cmd.equalsIgnoreCase("play")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Play command");
                    embed1.setDescription("Searches for a song on YouTube or Spotify and plays it. To play a Spotify track, enter the Track URL and not just the track ID." +
                            "Similarly, to play Spotify Playlist, enter the Playlist URL.");
                    embed1.addField("Aliases:", "`play` | `p` | `add`", true);
                    embed1.addField("Syntax:", "`-play <song_name>` / `-play <URL>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("spotify") || cmd.equalsIgnoreCase("spotify_user")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Spotify User Profile Command");
                    embed1.setDescription("Fetches an user's Spotify account details. Only Playlist information is currently retrieved by this command." +
                            "To search for a user, enter their profile URL.");
                    embed1.addField("Aliases:", "`spotify_user`", true);
                    embed1.addField("Syntax:", "`-spotify_user <profile_URL>` / `-play <URL>`", true);
                    embed1.setThumbnail("https://storage.googleapis.com/pr-newsroom-wp/1/2018/11/Spotify_Logo_CMYK_Green.png");
                    embed1.setFooter("Listen on Spotify. Music for Everyone.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("pause")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Pause command");
                    embed1.setDescription("Pauses a currently playing song.");
                    embed1.addField("Aliases: ", "`pause`", true);
                    embed1.addField("Syntax:", "`-pause`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("scplay")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("SoundCloud command");
                    embed1.setDescription("Search & play a music from SoundCloud\n⚠️This command is currently made unavailable since it would have led to a compromised experience that does not meet our standards.");
                    embed1.addField("Aliases:", "`scplay`", true);
                    embed1.addField("Syntax:", "`-scplay <song_name>` / `-scplay <URL>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } /*else if (cmd.equalsIgnoreCase("px")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Playlist command");
                    embed1.setDescription("Adds songs in the specified YouTube playlist, to the queue");
                    embed1.addField("Aliases:", "`px` | `playlist`", true);
                    embed1.addField("Syntax:", "`-px <song_name>` / `-playlist <URL>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                }*/ else if (cmd.equalsIgnoreCase("search")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Search command");
                    embed1.setDescription("Searches for a song on YouTube, and lists out the best possible results. To ensure accuracy, at any given point, only first 5 queries are returned. Search list empties as soon as any command other than Play-from-Search command is used.");
                    embed1.addField("Aliases:", "`search`", true);
                    embed1.addField("Syntax:", "`-search <search_term>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } /*else if (cmd.equalsIgnoreCase("sp")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Play-from-Search command");
                    embed1.setDescription("To play a song from the returned Search list. This command won't work if the Search list is empty.");
                    embed1.addField("Aliases:", "`sp`", true);
                    embed1.addField("Syntax:", "`-sp <track_number_from_search_list>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                }*/ else if (cmd.equalsIgnoreCase("queue play")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Queue Play command");
                    embed1.setDescription("Skips the current playing song and plays the specified track number from the queue.");
                    embed1.addField("Aliases:", "`qp`", true);
                    embed1.addField("Syntax:", "`-qp <track_number_from_queue>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("resume")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Resume command");
                    embed1.setDescription("Bruh. It resumes a paused song. As simple as that!");
                    embed1.addField("Aliases:", "`resume`", true);
                    embed1.addField("Syntax:", "`-resume`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("ff")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Fast-forward command");
                    embed1.setDescription("Fast forwards a song by specified time. If no time is provided, it fast-forwards the song by 20 seconds.");
                    embed1.addField("Aliases:", "`ff` | `fastforward` | `forward` | `fast`", true);
                    embed1.addField("Syntax:", "`-ff <time>` / `-ff`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("rev")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Reverse command");
                    embed1.setDescription("Reverses a song by specified time. If no time is provided, it reversed the song by 20 seconds.");
                    embed1.addField("Aliases:", "`rev` | `reverse`", true);
                    embed1.addField("Syntax:", "`-rev <time>` / `-rev`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("volume") || cmd.equalsIgnoreCase("vol")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Volume command");
                    embed1.setDescription("To view, increase or decrease the player volume. Volume can be set anywhere between 0 to 100. You can view the current volume by typing the suffix without a number." +
                            "\n**Representations:**\n\uD83D\uDD07 - Mute\n\uD83D\uDD08 - Volume less than 30\n\uD83D\uDD09 - Volume less than 70\n\uD83D\uDD0A - Max Volume / Volume > 70\n");
                    embed1.addField("Aliases:", "`vol` | `v` | `max` | `mute` | ", true);
                    embed1.addField("Syntax:", "`-vol <number>` | `-vol` | `-mute`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("streamer")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Streamer Mode Command");
                    embed1.setDescription("Streamer Mode is an exclusive feature to prevent copyright stikes while someone is Twtich Streaming. \nWhen the Streamer Mode gets activated, the audio player disables for everyone in the VC. This prevents copyrighted music being played in a shared vc that is being streamed.\n" +
                            "\n**Representations:**\n\uD83D\uDCE1 - Streamer Mode ON\n\n");
                    embed1.addField("Aliases:", "`streamer` | `stream`", true);
                    embed1.addField("Syntax:", "`-streamer on` | `-stream off`", true);
                    embed1.setFooter("Streamer Mode [BETA v1.0.0]");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("about")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("About command");
                    embed1.setDescription("Shows information about the bot- what it is and what it does.");
                    embed1.addField("Aliases:", "`about`", true);
                    embed1.addField("Syntax:", "`-about`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("whatsnew")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("What's New command");
                    embed1.setDescription("Shows the official update changelog.");
                    embed1.addField("Aliases:", "`new` | `whatsnew` | `wutnew`", true);
                    embed1.addField("Syntax:", "`-new`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("ping")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Ping command");
                    embed1.setDescription("To check bot's latency & WebSocket connection ping.");
                    embed1.addField("Aliases:", "`ping`", true);
                    embed1.addField("Syntax:", "`-ping`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("join")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Join command");
                    embed1.setDescription("To make the bot join your voice channel.");
                    embed1.addField("Aliases:", "`join` | `summon` | `j`", true);
                    embed1.addField("Syntax:", "`-join`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("dc")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Leave/ Disconnect command");
                    embed1.setDescription("To disconnect a bot from the voice channel it was playing in. The bot auto-disconnects from the VC as soon as the last user leaves the VC.");
                    embed1.addField("Aliases:", "`dc` | `leave` | `die` | `l`", true);
                    embed1.addField("Syntax:", "`-dc`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("player")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Now-Playing command");
                    embed1.setDescription("Shows details about the song currently playing in the voice channel. Besides, it also indicates if repeat or streamer modes are on or not.");
                    embed1.addField("Aliases:", "`np` | `nowplaying` | `now` | `playing` | `n`", true);
                    embed1.addField("Syntax:", "`-np`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("loop")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Repeat command");
                    embed1.setDescription("To toggle repeat mode for the song which is currently playing." +
                            "\n**Representations:**\n\uD83D\uDD02 - Single Repeat Mode ON\n");
                    embed1.addField("Aliases:", "`repeat` | `loop`", true);
                    embed1.addField("Syntax:", "`-repeat` | `-repeat <on`/`off>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("repeat all")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Repeat All / Repeat Queue command");
                    embed1.setDescription("To repeat the whole queue. New songs can still be added to the queue while this mode is on." +
                            "\n**Representations:**\n\uD83D\uDD01 - Repeat Queue Mode ON\n");
                    embed1.addField("Aliases:", "`repeat all` | `loop all`", true);
                    embed1.addField("Syntax:", "`-repeat all` | `-repeat all <on`/`off>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("skip")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Skip command");
                    embed1.setDescription("To skip the currently playing song. If queue repeat is on, the song gets added back to the queue.");
                    embed1.addField("Aliases:", "`skip` | `next`", true);
                    embed1.addField("Syntax:", "`-skip`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("queue view")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Queue command");
                    embed1.setDescription("To view the current playlist of songs. If the queue has more than 1 page, you can use `-q <page number> to view the specified page of the queue.\nOnce all tracks of a queue have been played, it can be replayed using `-p` command. The previous queue cannot be replayed if a new track is added to the current queue.\n\n" +
                            "**Other Queue Commands:**\n1. `/queue play`\n2. `/queue move`\n3. `/queue remove`\n4. `/queue shuffle`\n5. `/queue swap`\n6. `/clear`\n7. `/repeatll`");
                    embed1.addField("Aliases:", "`queue` | `q` | `list`", true);
                    embed1.addField("Syntax:", "`-queue` | `-q 2`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("clear")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Clear queue command");
                    embed1.setDescription("To clear the current queue without stopping the currently playing song.");
                    embed1.addField("Aliases:", "`clear`", true);
                    embed1.addField("Syntax:", "`-clear`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("stop")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Stop command");
                    embed1.setDescription("Stops the currently playing song in the voice channel and clears the queue. This bot remains in the vc.");
                    embed1.addField("Aliases:", "`stop`", true);
                    embed1.addField("Syntax:", "`-stop`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("queue remove")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Remove command");
                    embed1.setDescription("Added a song by mistake? No worries! You can use this command to remove a specific song from the queue.");
                    embed1.addField("Aliases:", "`remove`", true);
                    embed1.addField("Syntax:", "`-remove <track number / @username>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("queue move")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Move command");
                    embed1.setDescription("This command is used to move a specific song in the queue to the specified new position");
                    embed1.addField("Aliases:", "`move`", true);
                    embed1.addField("Syntax:", "`-move <track #> <new position>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("lyrics")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Lyrics command");
                    embed1.setDescription("You can view the lyrics of a currently playing song or any other specific song. For best results, enter song name and artist name when using text commands.");
                    embed1.addField("Aliases:", "`lyrics`", true);
                    embed1.addField("Syntax:", "``-lyrics <song_name> , <artist_name> | /lyrics <song_name | /lyrics <artist_name>`", true);
                    embed1.setFooter("Lyrics are brought to you by Musixmatch", "https://pbs.twimg.com/profile_images/1194637308244430848/JUj4SljE_400x400.jpg");
                    //embed1.setFooter("Lyrics are brought to you by A-Z Lyrics.","https://download.cnet.com/a/img/resize/ccba7cc1e86ee344022b6807533bac63cededbef/catalog/2019/12/10/3eeaa564-0594-4c15-b238-535242e80227/imgingest-1293706355108209810.jpg?auto=webp&fit=crop&height=675&width=1200");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("queue shuffle")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Shuffle command");
                    embed1.setDescription("You can use this to shuffle the songs in the queue. This command will fail to work if there is only 1 song or no song in the queue.");
                    embed1.addField("Aliases:", "`shuffle`", true);
                    embed1.addField("Syntax:", "`-shuffle`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("news")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("News & Updates command");
                    embed1.setDescription("You can check out all the upcoming cool stuff, planned for the Pied Piper!");
                    embed1.addField("Aliases:", "`news` | `updates`", true);
                    embed1.addField("Syntax:", "`-news`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("rc")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Reconnect command");
                    embed1.setDescription("Facing playback issues? No worries! This command disconnects the bot from the vc and reconnects it back. Any music playing which the bot disconnected will automatically resume form where it left off.");
                    embed1.addField("Aliases:", "`reconnect` | `rc`", true);
                    embed1.addField("Syntax:", "`-rc`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("version")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Version-check command");
                    embed1.setDescription("Using this, you can check for any available bot updates, current bot version and versions of Bot utilities: Lavaplayer and JDA.");
                    embed1.addField("Aliases:", "`version`", true);
                    embed1.addField("Syntax:", "`-version`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("status")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Check status command");
                    embed1.setDescription("You can check the current status of bot and if there are any scheduled maintenance breaks coming up.");
                    embed1.addField("Aliases:", "`status`", true);
                    embed1.addField("Syntax:", "`-status`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("report")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Bug/ Suggestions Reporting Command");
                    embed1.setDescription("Reporting bugs or suggestions has gotten a lot more easier! You can use the following two commands to report your bugs or suggestions:\n\n1. `-report bug <your_query>`\n2. `-report suggestion <your_query>`\n\nEvery report sent out gets an unique Report number which is used to identify your report. It is like an user identification number which allows us to seamlessly retrieve your report." +
                            "You can use this Report number to get in touch with Pied Piper Support#1168 regarding your query.\n\nAdditionally, if you want to share further details in the form of screenshots, regarding your query, you can dm them to Pied Piper Support#1168. Don't forget to mention your Report number!\n\nFor new suggestions/ bug reports, please use the inbuilt -report command.\nAny reports which are irrelevant, will be discarded automatically. Spamming the Piped Piper Support might ban you from sending out new reports.");
                    embed1.addField("Aliases:", "`report <bug / suggestion> <your_description>`", true);
                    embed1.addField("Syntax:", "`-report bug the bug flies off and comes back down send haalp asap`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("replay")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Replay Command");
                    embed1.setDescription("To replay the previous track. This command will not work if there is no previously played track.");
                    embed1.addField("Aliases:", "`replay`", true);
                    embed1.addField("Syntax:", "`-replay`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("seek")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Seek Command");
                    embed1.setDescription("To seek to the specified time stamp. Time stamps provided can be in two formats:\n1. HH:MM:SS\n2.MM:SS\n\nThe timestamp has to be colon `:` separated integers, without any whitespace in between. If the provided timestamp exceeds the track length, then an exception will be thrown.");
                    embed1.addField("Aliases:", "`seek`", true);
                    embed1.addField("Syntax:", "`-seek <hh:mm:ss / mm:ss>`", true);
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("queue swap")) {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("Swap Command");
                    embed1.setDescription("It can swap two tracks in the queue. This command accepts two arguments:\n1. The track to swap\n2. Track # to swap the first track with\n\nThis command will fail to work if there are no tracks or only 1 track in the queue.");
                    embed1.addField("Aliases:", "`swap`", true);
                    embed1.addField("Syntax:", "`-swap <track #> <swap with track #>`", true);
                    embed1.setFooter("Powered by BetterQueue.");
                    embed1.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                } else if (cmd.equalsIgnoreCase("slash") || cmd.equalsIgnoreCase("commands")) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("/ Slash Commands", "https://discord.com/api/oauth2/authorize?client_id=894494474149957632&permissions=8&scope=applications.commands%20bot");

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
                    eb.addField("", "* Audio Player is only available with Slash Commands. Type `/player` to access it while listening to music.\n" +
                            "\n^ Interactive buttons and menus only accessible with Slash Commands", false);
                    eb.setFooter("Unable to use Slash Commands? Click the on title to re-invite the bot.", "https://i.imgur.com/BMH6UcT.jpeg");

                    eb.setColor(new Color(216, 213, 190));
                    event.replyEmbeds(eb.build()).queue();
                    eb.clear();
                } else {
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("⚠️  Invalid Input!");
                    embed1.setDescription("Hmmm...looks like you've entered a wrong command name... Try again maybe? \uD83E\uDD14");
                    embed1.setColor(new Color(242, 202, 9));
                    event.replyEmbeds(embed1.build()).queue();
                    embed1.clear();
                }
            }
        }
    }
}
