package Spotify;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;
import org.apache.hc.core5.http.ParseException;
import org.jetbrains.annotations.NotNull;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.data.library.*;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetUsersProfileRequest;
import utils.config;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * This class contains methods to handle Spotify playlist requests
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SpotifyUserPlaylistCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        //String userid = event.getMember().getId();
        if (!(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-spotify_profile")) {
                TextChannel channel = event.getTextChannel();

                if (input.length == 1) {
                    channel.sendMessage("⚠ Yeehaw...lost? The syntax to find someone on Spotify is: `-spotify_user <user_id>`").queue();
                } else {
                    SpotifyUserPlaylist(null, event);
                }
            }
        }
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("spotify") && Objects.equals(event.getSubcommandName(), "profile")) {
            SpotifyUserPlaylist(event, null);
        }
    }

    public void SpotifyUserPlaylist(SlashCommandEvent event1, MessageReceivedEvent event2) {
        TextChannel channel = null;
        String input = "";

        if (event2 == null) {
            channel = event1.getTextChannel();
            OptionMapping operator1 = event1.getOption("profile_url");

            if (operator1 == null) {
                return;
            }
            input = operator1.getAsString();
        } else {
            channel = event2.getTextChannel();
            String[] msg = event2.getMessage().getContentRaw().split("\\s+");
            input = msg[1];
        }

        if(input.equalsIgnoreCase("pied piper") || input.equalsIgnoreCase("pied") || input.equalsIgnoreCase("piper")){
            input = "https://open.spotify.com/user/31gawmgyebtvsameogo6u2lac5hy?si=d184045a4c0542b0";
        }

        input = input.replace("https://open.spotify.com/user/", "");
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

        int errorCounter = 0;
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            //System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            if (event2 == null)
                event1.reply("⚠ Ding ding di-....Error: " + e.getMessage() + "\nplease raise a report this using `/report`.").queue();
            else
                channel.sendMessage("⚠ Ding ding di-....Error: " + e.getMessage() + "\nplease raise a report this using `/report`.").queue();
            errorCounter = 1;
        }
        if (errorCounter == 0) {
            final GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = spotifyApi
                    .getListOfUsersPlaylists(input)
                    .build();

            final GetUsersSavedTracksRequest getUsersSavedTracksRequest = spotifyApi.getUsersSavedTracks()
                    .build();

            final GetUsersProfileRequest getUsersProfileRequest = spotifyApi.getUsersProfile(input)
                    .build();



            try {
                final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();
                final User user = getUsersProfileRequest.execute();
                final Paging<SavedTrack> savedTrackPaging = getUsersSavedTracksRequest.execute();
                Image[] img = user.getImages();

                EmbedBuilder eb = new EmbedBuilder();
                if (img.length != 0)
                    eb.setAuthor(user.getDisplayName() + " 's Profile", "https://open.spotify.com/user/" + user.getId(), img[0].getUrl());
                else
                    eb.setAuthor(user.getDisplayName());
                eb.setThumbnail("https://storage.googleapis.com/pr-newsroom-wp/1/2018/11/Spotify_Logo_RGB_Green.png");
                //eb.setTitle("User Profile");
                eb.setColor(new Color(30, 215, 96));

                eb.setDescription(user.getFollowers().getTotal() + " Followers | " + playlistSimplifiedPaging.getTotal() + " Public Playlists | " + savedTrackPaging.getTotal() + " Saved Tracks");
                eb.addField("🗏  " + playlistSimplifiedPaging.getTotal() + " Playlists found", "Use `/play <playlist_url>` to load a playlist", false);

                if (playlistSimplifiedPaging.getTotal() != 0) {
                    for (int i = 0; i < playlistSimplifiedPaging.getTotal(); i++) {
                        eb.addField("#" + (i + 1) + ". " + playlistSimplifiedPaging.getItems()[i].getName(), "(" + playlistSimplifiedPaging.getItems()[i].getTracks().getTotal() + " tracks)" + " https://open.spotify.com/playlist/" + playlistSimplifiedPaging.getItems()[i].getId(), true);
                    }
                }
                if (savedTrackPaging.getTotal() != 0) {
                    eb.addBlankField(false);
                    eb.addField("\uD83C\uDFB6  " + savedTrackPaging.getTotal() + "Saved Tracks found", "Use `-play <track_url>` to load a track", false);
                    for (int i = 0; i < savedTrackPaging.getTotal(); i++) {
                        eb.addField("#" + (i + 1) + ". " + savedTrackPaging.getItems()[i].getTrack().getName(), "" + savedTrackPaging.getItems()[i].getTrack().getUri(), true);
                    }
                }
                eb.setFooter("For the love of music. Pied Piper.", "https://i.imgur.com/BMH6UcT.jpeg");

                if (event2 == null)
                    event1.replyEmbeds(eb.build()).queue();
                else
                    channel.sendMessageEmbeds(eb.build()).queue();

            } catch (IOException | SpotifyWebApiException | ParseException e) {
                if (event2 == null)
                    event1.reply("\uD83E\uDD74 Uh oh! I'm lost! Error: " + e.getMessage() + "\nIf this error is not related to user ID, please raise a report this using `/report`.").queue();
                else
                    channel.sendMessage("\uD83E\uDD74 Uh oh! I'm lost! Error: " + e.getMessage() + "\nIf this error is not related to user ID, please raise a report this using `/report`.").queue();

            }


        }
    }
}
