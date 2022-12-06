
import SlashCommands.*;
import Misc.*;
import Spotify.PlaySpotifySlashCommand;
import Spotify.SpotifyUserPlaylistCommand;
import lavaplayer.MusixMatchAPITest;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import utils.config;

import javax.security.auth.login.LoginException;

/**
 * This is the main class which contains the main method
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs to setup the bot
 */

public class nexPoggersMain {
    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {

        config obj = new config();
        JDABuilder builder = JDABuilder.createDefault(obj.Bottoken);

        builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableCache(CacheFlag.ACTIVITY);
        builder.setMemberCachePolicy(MemberCachePolicy.ONLINE);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        jda = builder.build().awaitReady();
        jda.getPresence().setActivity(Activity.listening("Pied Piper v1.4 Alpha"));

      /*  jda.getPresence().setStatus(OnlineStatus.ONLINE);
        final String[] messages = {"Beta Testing", "Pied Piper v1.3 Beta"};
        final int[] currentIndex = {0};

        new Timer().schedule(new TimerTask() {
            public void run() {
                jda.getPresence().setActivity(Activity.playing(messages[currentIndex[0]]));
                currentIndex[0] = (currentIndex[0] + 1) % messages.length;
            }
        }, 0, 5_000);*/

        // jda.getPresence().setStatus(OnlineStatus.ONLINE);
        //jda.getPresence().setPresence(OnlineStatus.IDLE,Activity.watching("you"));
        // jda.getPresence().setActivity(Activity.playing("Pied Piper v1.4 Alpha"));

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.watching("The Next Chapter..."));
        // jda.getPresence().setActivity(Activity.playing("with your feelings"));

        jda.addEventListener(new Wish());
        jda.addEventListener(new About());
        jda.addEventListener(new DetailedHelp());
        jda.addEventListener(new SpecialEvents());
        //jda.addEventListener(new Wish());



        jda.addEventListener(new HandlerSlashCommand());
        jda.addEventListener(new NowPlayingSlashCommand());
        jda.addEventListener(new PlaySlashCommand());
        jda.addEventListener(new PlaylistSlashCommand());
        jda.addEventListener(new SearchSlashCommand());
        jda.addEventListener(new RepeatSlashCommand());
        jda.addEventListener(new VolumeSlashCommand());
        jda.addEventListener(new HelpSlashCommands());
        jda.addEventListener(new SkipSlashCommand());
        jda.addEventListener(new StopSlashCommand());
        jda.addEventListener(new LyricsSearchSlashCommand());
        jda.addEventListener(new QueueSlashCommand());
        jda.addEventListener(new QueueMoveSlashCommand());
        jda.addEventListener(new QueuePlaySlashCommand());
        jda.addEventListener(new QueueRemoveSlashCommand());
        jda.addEventListener(new QueueShuffleSlashCommand());
        jda.addEventListener(new QueueSwapSlashCommand());
        jda.addEventListener(new SeekSlashCommand());

        jda.addEventListener(new Settings());
        jda.addEventListener(new ReportSlashCommand());
        jda.addEventListener(new MusixMatchAPITest());

        jda.addEventListener(new PlaySpotifySlashCommand());
        jda.addEventListener(new SpotifyUserPlaylistCommand());

        jda.addEventListener(new VideoSlashCommand());
        jda.addEventListener(new StreamerModeSlashCommand());


        Guild guild = jda.getGuildById("816287216350527508");


        jda.upsertCommand("about", "Know more about Piper").queue();
        jda.upsertCommand("ping", "Check Piper's response time").queue();
        jda.upsertCommand("new", "Check out whats new with Piper's latest update").queue();
        jda.upsertCommand("news", "Check out whats cooking behind the scenes").queue();
        jda.upsertCommand("version", "Check Piper's current version details").queue();
        jda.upsertCommand("status", "Check Piper's runtime status").queue();

        /*jda.upsertCommand("help", "View or get help with bot commands")
                .addSubcommands(new SubcommandData("page", "To view help page(s)"))
                .addSubcommands(new SubcommandData("command", "To get help with a specific command").addOption(OptionType.STRING, "name", "To get help with a specific command", true)).queue();*/

        jda.upsertCommand("help", "View or get help with bot commands")
                .addOption(OptionType.STRING, "name", "Enter command name to get detailed info about", false)
                .queue();


        jda.upsertCommand("911", "View bot commands").queue();
        jda.upsertCommand("join", "Makes the bot join the Voice Channel").queue();
        jda.upsertCommand("dc", "Resets the audio player and leaves the Voice Channel").queue();
        jda.upsertCommand("leave", "Resets the audio player and leaves the Voice Channel").queue();
        jda.upsertCommand("rc", "Disconnects the bot from VC, clears cache and reconnects it back").queue();
        jda.upsertCommand("reconnect", "Disconnects the bot from VC, clears cache and reconnects it back").queue();
        jda.upsertCommand("lyrics", "To view the lyrics of currently playing song or other specified song")
                .addOption(OptionType.STRING, "artist_name", "Enter the song name to view its lyrics", false)
                .addOption(OptionType.STRING, "song_name", "Enter the song name to view its lyrics", false)
                .queue();

        jda.upsertCommand("np", "Shows the currently playing track").queue();
        jda.upsertCommand("player", "Displays the multi-functional Audio Player").queue();
        jda.upsertCommand("play", "To play a song in the VC")
                .addOption(OptionType.STRING, "input", "Enter the name of the song to play", false)
                .queue();

        jda.upsertCommand("playlist", "To load and play playlist from YouTube")
                .addOption(OptionType.STRING, "input", "Enter the name of the playlist to tune in", true)
                .queue();

        jda.upsertCommand("repeatall", "Toggle the queue to repeat").queue();

        jda.upsertCommand("loop", "Toggle a single song to repeat").queue();
        jda.upsertCommand("restart", "Replays the current song").queue();
        jda.upsertCommand("search", "Searches for a song on YouTube")
                .addOption(OptionType.STRING, "searchterm", "Enter the name of the song to search", true)
                .queue();

        jda.upsertCommand("volume", "Change or display the volume")
                .addOptions(
                        new OptionData(OptionType.INTEGER, "value", "Enter the value to set the volume to", false)
                                .setRequiredRange(1, 100)
                )
                .queue();

        jda.upsertCommand("queue", "All queue related commands")
                .addSubcommands(new SubcommandData("view", "View the songs in the queue"))

                .addSubcommands(new SubcommandData("move", "Move a song to the specified position in the queue")
                        .addOptions(
                                new OptionData(OptionType.INTEGER, "track_no", "Track # to move", true),
                                new OptionData(OptionType.INTEGER, "new_pos", "New position of the track", true)
                        ))

                .addSubcommands(new SubcommandData("play", "Plays the specified track from queue").addOption(OptionType.INTEGER, "track_no", "Enter the track # to start playing", true))

                .addSubcommands(new SubcommandData("remove", "Removes the specified track from the queue").addOption(OptionType.INTEGER, "track_no", "Enter the track # to remove from the queue", true))
                .addSubcommands(new SubcommandData("shuffle", "Shuffles the songs in the queue"))
                .addSubcommands(new SubcommandData("swap", "Swap the two tracks in the queue")
                        .addOptions(
                                new OptionData(OptionType.INTEGER, "track_no", "Track # to swap", true),
                                new OptionData(OptionType.INTEGER, "swap_with", "Track # to swap with", true)
                        )).queue();


        jda.upsertCommand("seek", "Seeks the playing track to the specified timestamp")
                .addOption(OptionType.INTEGER, "input", "The timestamp to seek to in format: <hh:mm:ss> or <mm:ss>", true)
                .queue();
        jda.upsertCommand("ff", "Fast-forwards the currently playing track")
                .addOption(OptionType.INTEGER, "input", "The amount of seconds to fast-forward", false)
                .queue();
        jda.upsertCommand("rev", "Reverses the currently playing track")
                .addOption(OptionType.INTEGER, "input", "The amount of seconds to rewind", false)
                .queue();

        jda.upsertCommand("skip", "Skips to the next song").queue();
        jda.upsertCommand("stop", "Stops the currently playing song and clears the queue").queue();
        jda.upsertCommand("pause", "Pauses the currently playing song").queue();
        jda.upsertCommand("resume", "Resumes the paused song").queue();
        jda.upsertCommand("clear", "Clears the queue").queue();
        jda.upsertCommand("replay", "Replays the previous track").queue();


        jda.upsertCommand("report", "For reporting bugs and suggestions")
                .addOption(OptionType.STRING, "bug", "Enter a description of your bug. Please make sure to include necessary information", false)
                .addOption(OptionType.STRING, "suggestion", "Type in your suggestion here", false)
                .queue();
        jda.upsertCommand("streamer", "Disable music playback while someone in VC is Streaming")
                .addSubcommands(new SubcommandData("info", "View info about Streamer Mode"))
                .addSubcommands(new SubcommandData("on", "Activate Streamer Mode"))
                .addSubcommands(new SubcommandData("off", "Deactivate Streamer Mode"))
                .queue();

        jda.upsertCommand("spotify", "Spotify related commands")
                .addSubcommands(new SubcommandData("profile", "Fetches Spotify user details")
                        .addOption(OptionType.STRING, "profile_url", "Enter the user's Spotify profile URL", true))
                .queue();

        jda.upsertCommand("video", "To generate a YouTube video embed").queue();

        if (guild != null) {
            guild.upsertCommand("about", "Know more about Piper").queue();
            guild.upsertCommand("ping", "Check Piper's response time").queue();
            guild.upsertCommand("new", "Check out whats new with Piper's latest update").queue();
            guild.upsertCommand("news", "Check out whats cooking behind the scenes").queue();
            guild.upsertCommand("version", "Check Piper's current version details").queue();
            guild.upsertCommand("status", "Check Piper's runtime status").queue();


            /*guild.upsertCommand("help", "View or get help with bot commands")
                    .addSubcommands(new SubcommandData("page", "To view help page(s)"))
                    .addSubcommands(new SubcommandData("command", "To get help with a specific command").addOption(OptionType.STRING, "name", "To get help with a specific command", true)).queue();*/

            jda.upsertCommand("help", "View or get help with bot commands")
                    .addOption(OptionType.STRING, "name", "Enter command name to get detailed info about", false)
                    .queue();


            guild.upsertCommand("911", "View bot commands").queue();
            guild.upsertCommand("join", "Makes the bot join the Voice Channel").queue();
            guild.upsertCommand("dc", "Resets the audio player and leaves the Voice Channel").queue();
            guild.upsertCommand("leave", "Resets the audio player and leaves the Voice Channel").queue();
            guild.upsertCommand("rc", "Disconnects the bot from VC, clears cache and reconnects it back").queue();
            guild.upsertCommand("reconnect", "Disconnects the bot from VC, clears cache and reconnects it back").queue();
            /*guild.upsertCommand("lyrics", "To view the lyrics of currently playing song or other specified song")
                    .addOption(OptionType.STRING, "query", "Enter the song name to view its lyrics", false)
                    .queue();*/
            guild.upsertCommand("lyrics", "To view the lyrics of currently playing song or other specified song")
                    .addOption(OptionType.STRING, "artist_name", "Enter the song name to view its lyrics", false)
                    .addOption(OptionType.STRING, "song_name", "Enter the song name to view its lyrics", false)
                    .queue();
            guild.upsertCommand("nowplaying", "Shows the currently playing track").queue();
            guild.upsertCommand("player", "Displays the multi-functional Audio Player").queue();
            guild.upsertCommand("play", "To play a song in the VC")
                    .addOption(OptionType.STRING, "input", "Enter the name of the song to play", false)
                    .queue();

            guild.upsertCommand("playlist", "To load and play playlist from YouTube")
                    .addOption(OptionType.STRING, "input", "Enter the name of the playlist to tune in", true)
                    .queue();

            guild.upsertCommand("repeatall", "Toggle the queue to repeat").queue();

            guild.upsertCommand("loop", "Toggle a single song to repeat").queue();
            guild.upsertCommand("restart", "Replays the current song").queue();
            guild.upsertCommand("search", "Searches for a song on YouTube")
                    .addOption(OptionType.STRING, "searchterm", "Enter the name of the song to search", true)
                    .queue();
            guild.upsertCommand("volume", "Change or display the volume")
                    .addOptions(
                            new OptionData(OptionType.INTEGER, "value", "Enter the value to set the volume to", false)
                                    .setRequiredRange(1, 100)
                    )
                    .queue();


            guild.upsertCommand("queue", "All queue related commands")
                    .addSubcommands(new SubcommandData("view", "View the songs in the queue"))

                    .addSubcommands(new SubcommandData("move", "Move a song to the specified position in the queue")
                            .addOptions(
                                    new OptionData(OptionType.INTEGER, "track_no", "Track # to move", true),
                                    new OptionData(OptionType.INTEGER, "new_pos", "New position of the track", true)
                            ))

                    .addSubcommands(new SubcommandData("play", "Plays the specified track from queue").addOption(OptionType.INTEGER, "track_no", "Enter the track # to start playing", true))

                    .addSubcommands(new SubcommandData("remove", "Removes the specified track from the queue").addOption(OptionType.INTEGER, "track_no", "Enter the track # to remove from the queue", true))
                    .addSubcommands(new SubcommandData("shuffle", "Shuffles the songs in the queue"))
                    .addSubcommands(new SubcommandData("swap", "Swap the two tracks in the queue")
                            .addOptions(
                                    new OptionData(OptionType.INTEGER, "track_no", "Track # to swap", true),
                                    new OptionData(OptionType.INTEGER, "swap_with", "Track # to swap with", true)
                            )).queue();

            guild.upsertCommand("seek", "Seeks the playing track to the specified timestamp")
                    .addOption(OptionType.STRING, "input", "The timestamp to seek to in format: <hh:mm:ss> or <mm:ss>", true)
                    .queue();
            guild.upsertCommand("ff", "Fast-forwards the currently playing track")
                    .addOption(OptionType.INTEGER, "input", "The amount of seconds to fast-forward", false)
                    .queue();
            guild.upsertCommand("rev", "Reverses the currently playing track")
                    .addOption(OptionType.INTEGER, "input", "The amount of seconds to rewind", false)
                    .queue();

            guild.upsertCommand("skip", "Skips to the next song").queue();
            guild.upsertCommand("stop", "Stops the currently playing song and clears the queue").queue();
            guild.upsertCommand("pause", "Pauses the currently playing song").queue();
            guild.upsertCommand("resume", "Resumes the paused song").queue();
            guild.upsertCommand("clear", "Clears the queue").queue();
            guild.upsertCommand("replay", "Replays the previous track").queue();


            guild.upsertCommand("report", "For reporting bugs and suggestions")
                    .addOption(OptionType.STRING, "bug", "Enter a description of your bug. Please make sure to include necessary information", false)
                    .addOption(OptionType.STRING, "suggestion", "Type in your suggestion here", false)
                    .queue();
            guild.upsertCommand("streamer", "Disable music playback while someone in VC is Streaming")
                    .addSubcommands(new SubcommandData("info", "View info about Streamer Mode"))
                    .addSubcommands(new SubcommandData("on", "Activate Streamer Mode"))
                    .addSubcommands(new SubcommandData("off", "Deactivate Streamer Mode"))
                    .queue();


            guild.upsertCommand("spotify", "Spotify related commands")
                    .addSubcommands(new SubcommandData("profile", "Fetches Spotify user details")
                            .addOption(OptionType.STRING, "profile_url", "Enter the user's Spotify profile URL", true))
                    .queue();

            guild.upsertCommand("video", "To generate a YouTube video embed").queue();
        }
    }
}