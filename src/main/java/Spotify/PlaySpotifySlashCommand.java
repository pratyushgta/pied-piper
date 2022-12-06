package Spotify;

import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import utils.config;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains methods to handle Spotify track requests
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class PlaySpotifySlashCommand extends ListenerAdapter {
    public void playSpotifyTrack(SlashCommandEvent event1, MessageReceivedEvent event2) {


        TextChannel channel;
        String input;


        if (event2 == null) {
            channel = event1.getTextChannel();
            OptionMapping operator1 = event1.getOption("input");

            if (operator1 == null) {
                return;
            }
            input = operator1.getAsString();
        } else {
            channel = event2.getTextChannel();
            String[] msg = event2.getMessage().getContentRaw().split("\\s+");
            input = msg[1];
        }

        String link = "ytsearch:";
        int errorCounter = 0;

        input = input.replace("https://open.spotify.com/track/", "");
        if(input.contains("?"))
            input = input.substring(0, input.indexOf('?'));
        //input = input.substring(0, input.indexOf('?'));


        config obj = new config();

        final String clientId = obj.SpotifyclientId;
        final String clientSecret = obj.SpotifyclientSecret;
        final String refreshToken = obj.SpotifyrefreshToken;

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();
        final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();


        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            //System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            if (event2 == null)
                event1.reply("⚠ Lost my way :( Error: " + e.getMessage() + "\nplease raise a report this using `/report`.").queue();
            else
                channel.sendMessage("⚠ Lost my way :( Error: " + e.getMessage() + "\nplease raise a report this using `/report`.").queue();
            errorCounter = 1;
        }
        if (errorCounter == 0) {


            final GetTrackRequest getTrackRequest = spotifyApi.getTrack(input)
                    .build();



            try {
                final Track track = getTrackRequest.execute();

                // System.out.println("Name: " + track.getName());
                link += track.getName();
                String artist = Arrays.toString(track.getArtists());
                SpotifyArtistCommand ob = new SpotifyArtistCommand();
                String a = ob.SpotifyArtist(artist);
                /*String a = "";
                for (int i = 0; i < artist.length(); i++) {
                    if (artist.charAt(i) == 'n' && artist.charAt(i + 1) == 'a' && artist.charAt(i + 2) == 'm' && artist.charAt(i + 3) == 'e') {
                        for (int j = i + 5; j < artist.length(); j++) {
                            if (artist.charAt(j) == ',')
                                break;
                            else
                                a += artist.charAt(j);
                        }
                        break;
                    }
                }*/

                link += " by " + a;

                if (event2 == null) {
                    RequestMetadata rm = new RequestMetadata(event1.getUser());
                    PlayerManager.getInstance()
                            .loadAndPlay(channel, link, rm, false, false, false, event1, null);
                } else {
                    RequestMetadata rm = new RequestMetadata(event2.getAuthor());
                    final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

                    musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                    musicManager.scheduler.PlayCmd.setDescription("");
                    musicManager.scheduler.PlayCmd.setColor(new Color(242,202,9));
                    musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();

                    PlayerManager.getInstance()
                            .loadAndPlay(channel, link, rm, false, false, false, null, event2);
                }


            } catch (IOException | SpotifyWebApiException | ParseException e) {
                if (event2 == null)
                    event1.reply("\uD83E\uDD74 Uh oh! I'm lost! Error: " + e.getMessage() + "\nIf this error is not related to song ID, please raise a report this using `/report`.").queue();
                else
                    channel.sendMessage("\uD83E\uDD74 Uh oh! I'm lost! Error: " + e.getMessage() + "\nIf this error is not related to song ID, please raise a report this using `/report`.").queue();

            }
        }
    }

    public void playSpotifyPlaylist(SlashCommandEvent event1, MessageReceivedEvent event2) {

        TextChannel channel;
        String input;


        if (event2 == null) {
            channel = event1.getTextChannel();
            OptionMapping operator1 = event1.getOption("input");

            if (operator1 == null) {
                return;
            }
            input = operator1.getAsString();
        } else {
            channel = event2.getTextChannel();
            String[] msg = event2.getMessage().getContentRaw().split("\\s+");
            input = msg[1];
        }

        String ListLink = "ytsearch:";
        int errorCounter = 0;

        input = input.replace("https://open.spotify.com/playlist/", "");
        if(input.contains("?"))
            input = input.substring(0, input.indexOf('?'));



        config obj = new config();

        final String clientId = obj.SpotifyclientId;
        final String clientSecret = obj.SpotifyclientSecret;
        final String refreshToken = obj.SpotifyrefreshToken;

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();
        final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();


        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            //System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            if (event2 == null)
                event1.reply("⚠ Lost my way :( Error: " + e.getMessage() + "\nplease raise a report this using `/report`.").queue();
            else
                channel.sendMessage("⚠ Lost my way :( Error: " + e.getMessage() + "\nplease raise a report this using `/report`.").queue();
            errorCounter = 1;
        }

        if (errorCounter == 0) {

            final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                    .getPlaylistsItems(input)
                    .build();

            try {
                String nextPage;
                int limit = 100;
                int offset = 0;
                List<PlaylistTrack> playlists = new ArrayList<>();
                do {
                    Paging<PlaylistTrack> playlistTrackPaging = spotifyApi.getPlaylistsItems(input).offset(offset).limit(limit).build().execute();
                    playlists.addAll(Arrays.asList(playlistTrackPaging.getItems()));
                    offset = offset + limit;
                    nextPage = playlistTrackPaging.getNext();
                } while (nextPage != null);


                // System.out.println("Total: " + playlistTrackPaging.getTotal());
                // System.out.println("Track's first artist: " + ((Track) playlistTrackPaging.getItems()[0].getTrack()).getArtists()[0]);
                //System.out.println("Episode's show: " + ((Episode) playlistTrackPaging.getItems()[0].getTrack()).getShow());

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(30, 215, 96)); //SPOTIFY GREEN: 30 215 96
                eb.setTitle("✅  Playlist Loaded");
                eb.setThumbnail("https://storage.googleapis.com/pr-newsroom-wp/1/2018/11/Spotify_Logo_RGB_Green.png");
                eb.setDescription("Adding to the queue `" + playlists.size() + "` tracks from your Spotify playlist"); //🎵
                eb.setFooter("Ho ho ho! It may take a few seconds to load all songs to the queue...", "https://i.imgur.com/BMH6UcT.jpeg");


                if (event2 == null)
                    event1.replyEmbeds(eb.build()).queue();
                else
                    channel.sendMessageEmbeds(eb.build()).queue();


                for (PlaylistTrack playlist : playlists) {
                    // channel.sendMessage(i+"").queue();
                    ListLink += playlist.getTrack().getName();

                    //String artist = Arrays.toString(((Track) playlistTrackPaging.getItems()[i].getTrack()).getArtists());
                    String artist = Arrays.toString(((Track) playlist.getTrack()).getArtists());

                    SpotifyArtistCommand ob = new SpotifyArtistCommand();
                    String ax = ob.SpotifyArtist(artist);
                   /* StringBuilder ax = new StringBuilder();
                    System.out.println(artist);

                    System.out.println(spotifyApi.getArtist("0Vi7Lik2egTpQq5beemygj"));
                    for (int j = 0; j < artist.length(); j++) {
                        if (artist.charAt(j) == 'n' && artist.charAt(j + 1) == 'a' && artist.charAt(j + 2) == 'm' && artist.charAt(j + 3) == 'e') {
                            for (int k = j + 5; k < artist.length(); k++) {
                                if (artist.charAt(k) == ',')
                                    break;
                                else
                                    ax.append(artist.charAt(k));
                            }
                            break;
                        }
                    }*/

                    ListLink += " by " + ax;


                    if (event2 == null) {
                        RequestMetadata rm = new RequestMetadata(event1.getUser());
                        PlayerManager.getInstance()
                                .loadAndPlay(channel, ListLink, rm, false, false, true, event1, null);
                    } else {
                        RequestMetadata rm = new RequestMetadata(event2.getAuthor());

                        PlayerManager.getInstance()
                                .loadAndPlay(channel, ListLink, rm, false, false, true, null, event2);
                    }

                    ListLink = "ytsearch:";
                }

            } catch (IOException | SpotifyWebApiException | ParseException e) {
                if (event2 == null)
                    event1.reply("\uD83E\uDD74 Uh oh! I'm lost! Error: " + e.getMessage() + "\nIf this error is not related to playlist ID, please raise a report this using `/report`.").queue();
                else
                    channel.sendMessage("\uD83E\uDD74 Uh oh! I'm lost! Error: " + e.getMessage() + "\nIf this error is not related to playlist ID, please raise a report this using `/report`.").queue();
            }

        }
    }
}
