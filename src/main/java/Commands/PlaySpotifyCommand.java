package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

public class PlaySpotifyCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        String userid = event.getMember().getId();
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-spotify") || input[0].equalsIgnoreCase("-psp")) {
                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


                if (connectedChannel == null) {
                    channel.sendMessage("⚠️You need to be in a voice channel to play that!").queue();
                    return;
                } else if (SelfConnected == null) {
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    audioManager.openAudioConnection(connectedChannel);
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("\uD83E\uDEC2️We need to be in the same voice channel to play that!").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();

                if (musicManager.scheduler.streamer) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.red);
                    eb.setTitle("\uD83D\uDEAB Cannot play music while Streamer Mode is on!");
                    channel.sendMessageEmbeds(eb.build()).queue();
                    eb.clear();
                    return;
                }

                musicManager.scheduler.searchQueue.clear();

                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.searchQueue;
                java.util.List<AudioTrack> trackList = new ArrayList<>(queue);
                List<ArrayList> tempTrackList = new ArrayList<ArrayList>();
            }
        }
    }
}