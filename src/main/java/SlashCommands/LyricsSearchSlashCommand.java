package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.*;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

/**
 * This class contains methods for sending search query for displaying lyrics
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class LyricsSearchSlashCommand extends ListenerAdapter {
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getName().equals("lyrics")) {
            OptionMapping operator1 = event.getOption("artist_name");
            OptionMapping operator2 = event.getOption("song_name");


            TextChannel channel = event.getTextChannel();

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            musicManager.scheduler.LyricsQueue.clear();

            if (operator1 == null && operator2 == null) {

                if (audioPlayer.getPlayingTrack() == null) {
                    event.reply("\uD83D\uDE43 Eh? There's no music playin' What lyrics are you looking for?").queue();
                    return;
                }

                String artistName = track.getInfo().author;
                String trackName = track.getInfo().title;

                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.Lyrics(artistName, trackName, event, null, null);

            } else if (operator2 == null) {
                String artistName = operator1.getAsString();
                String trackName = "";

                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.SearchLyrics(artistName, trackName, event, null);


            } else if (operator1 == null) {
                String trackName = operator2.getAsString();
                String artistName = "";

                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.SearchLyrics(artistName, trackName, event, null);
            } else
            {
                String artistName = operator1.getAsString();
                String trackName = operator2.getAsString();

                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.Lyrics(artistName, trackName, event, null, null);
            }
        }
    }
}