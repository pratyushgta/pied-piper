package lavaplayer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import utils.config;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class contains methods for processing Lyrics requests from MusixMatch API
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */
public class MusixMatchAPITest extends ListenerAdapter {

    config obj = new config();
    String apiKey = obj.MusixMatchapiKey;
    MusixMatch musixMatch = new MusixMatch(apiKey);

    String artist = "";
    String track = "";
    SlashCommandEvent e = null;

    public void SearchLyrics(String artistName, String trackName, SlashCommandEvent event1, MessageReceivedEvent event2) {
        artist = artistName;
        track = trackName;

        TextChannel channel = event1.getTextChannel();

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

        List<Track> tracks = null;
        try {
            tracks = musixMatch.searchTracks("", artistName, trackName, 10, 5, true);
        } catch (MusixMatchException e) {
            event1.reply("\uD83D\uDE35\u200D\uD83D\uDCAB ️ Uh oh! Lyrics not found on MusixMatch!").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("\uD83D\uDD0E  Search Results for " + artistName + " " + trackName);

        int i = 0;

        if (tracks.size() == 0) {
            event1.reply("\uD83D\uDE35\u200D\uD83D\uDCAB ️ Uh oh! Lyrics not found on MusixMatch!").queue();
            return;
        }

        for (Track trk : tracks) {
            TrackData trkData = trk.getTrack();
            musicManager.scheduler.LyricsQueue(trk.getTrack());

            eb.addField((i + 1) + ". " + trkData.getTrackName(), " by " + trkData.getArtistName(), false);

            i++;
        }

        eb.setColor(new Color(0,151,136));
        e = event1;
        eb.setFooter("Brought to you by MusixMatch", "https://pbs.twimg.com/profile_images/1194637308244430848/JUj4SljE_400x400.jpg");
        event1.replyEmbeds(eb.build())
                .addActionRow(Button.success("A", "1️⃣️"), Button.success("B", "2️⃣️"), Button.success("C", "3️⃣"), Button.success("D", "4️⃣"), Button.success("E", "5️⃣")).queue();
    }

    public void Lyrics(String artistName, String trackName, SlashCommandEvent event1, MessageReceivedEvent event2, ButtonClickEvent event3) {

        Track track = null;
        try {
            track = musixMatch.getMatchingTrack(trackName, artistName);
        } catch (MusixMatchException e) {
            if (event1 == null) {
                event2.getChannel().sendMessage("\uD83D\uDE35\u200D\uD83D\uDCAB ️ Uh oh! Lyrics could not be found MusixMatch! Try being more specific by entering the artist name using `-lyrics <song_name> , <artist_name>`").queue();
                return;
            } else {
                event1.reply("\uD83D\uDE35\u200D\uD83D\uDCAB ️ Uh oh! Lyrics not found on MusixMatch!").queue();
                return;
            }
        }

        EmbedBuilder eb = new EmbedBuilder();

        TrackData data = track.getTrack();

        int trackID = data.getTrackId();

        Lyrics lyrics = null;
        try {
            lyrics = musixMatch.getLyrics(trackID);
        } catch (MusixMatchException e) {
            if (event1 == null) {
                event2.getChannel().sendMessage("\uD83D\uDE35\u200D\uD83D\uDCAB ️ Uh oh! Lyrics could not be found MusixMatch! Try being more specific by entering the artist name using `-lyrics <song_name> , <artist_name>`").queue();
                return;
            } else {
                event1.reply("\uD83D\uDE35\u200D\uD83D\uDCAB ️ Uh oh! Lyrics not found on MusixMatch!").queue();
                return;
            }
        }

        eb.setTitle(data.getTrackName(), data.getTrackShareUrl());
        eb.setAuthor(data.getArtistName());

        eb.setDescription(lyrics.getLyricsBody());
        eb.setColor(new Color(0,151,136));
        eb.setFooter("Brought to you by MusixMatch", "https://pbs.twimg.com/profile_images/1194637308244430848/JUj4SljE_400x400.jpg");

        if (event1 != null) {
            event1.replyEmbeds(eb.build()).queue();
        } else if (event2 != null) {
            event2.getChannel().sendMessageEmbeds(eb.build()).queue();
        } else {
            event3.editMessageEmbeds(eb.build())
                    .setActionRow(Button.success("A", "1️⃣️").asDisabled(), Button.success("B", "2️⃣️").asDisabled(), Button.success("C", "3️⃣").asDisabled(), Button.success("D", "4️⃣").asDisabled(), Button.success("E", "5️⃣").asDisabled()).queue();

        }

    }

    public void onButtonClick(@NotNull ButtonClickEvent event) {

        if (event.getComponentId().equals("A") || event.getComponentId().equals("B") || event.getComponentId().equals("C") || event.getComponentId().equals("D") || event.getComponentId().equals("E")) {

            TextChannel channel = event.getTextChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

            final List<TrackData> trackList = new ArrayList<>(musicManager.scheduler.LyricsQueue);


            if (event.getComponentId().equals("A")) {
                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.Lyrics(trackList.get(0).getArtistName(), trackList.get(0).getTrackName(), null, null, event);
            } else if (event.getComponentId().equals("B")) {
                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.Lyrics(trackList.get(1).getArtistName(), trackList.get(1).getTrackName(), null, null, event);
            } else if (event.getComponentId().equals("C")) {
                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.Lyrics(trackList.get(2).getArtistName(), trackList.get(2).getTrackName(), null, null, event);
            } else if (event.getComponentId().equals("D")) {
                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.Lyrics(trackList.get(3).getArtistName(), trackList.get(3).getTrackName(), null, null, event);
            } else if (event.getComponentId().equals("E")) {
                MusixMatchAPITest ob = new MusixMatchAPITest();
                ob.Lyrics(trackList.get(4).getArtistName(), trackList.get(4).getTrackName(), null, null, event);
            }
        }
    }
}