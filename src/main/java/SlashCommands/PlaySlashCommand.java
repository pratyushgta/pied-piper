package SlashCommands;

import Spotify.PlaySpotifySlashCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class contains methods for processing and playing a track or YouTube playlist
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class PlaySlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("play")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) ((event.getMember()).getVoiceState()).getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) ((event.getGuild()).getSelfMember().getVoiceState()).getChannel();

            if (connectedChannel == null) {
                event.reply("⚠️You need to be in a voice channel to play that!").queue();
                return;
            } else if (SelfConnected == null) {
                AudioManager audioManager = event.getGuild().getAudioManager();
                audioManager.openAudioConnection(connectedChannel);
            } else if (connectedChannel != SelfConnected) {
                event.reply("\uD83E\uDEC2️We need to be in the same voice channel to play that!").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            if (musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(220,77,77));
                eb.setTitle("\uD83D\uDEAB Cannot play music while Streamer Mode is on!");
                eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
                return;
            }

            OptionMapping option = event.getOption("input");

            if (option == null) {
                if (musicManager.scheduler.player.isPaused()) {
                    final AudioTrackInfo info = track.getInfo();
                    musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
                    musicManager.scheduler.volume = 0;
                    event.replyFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();

                    musicManager.scheduler.player.setPaused(false);
                    return;
                } else if (musicManager.scheduler.pointer == musicManager.scheduler.queue.size()-1) {
                    musicManager.scheduler.pointer=-1;
                    musicManager.scheduler.nextTrack();
                    event.reply("▶  Starting to play previous queue...").queue();
                    return;
                } else {
                    event.reply("Play go brrrr").queue();
                    return;
                }
            }

            String input = option.getAsString();

            if (input.contains("playlist") && input.contains("youtube.com") && input.contains("https://")) { //httpCheck.equalsIgnoreCase("https://www.youtube.com/playlist")) {
                musicManager.scheduler.search = 0;
                String link;
                musicManager.scheduler.searchQueue.clear();
                link = input;
                RequestMetadata rm = new RequestMetadata(event.getUser());
                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, true, false, false, event, null);
            } else if (input.equalsIgnoreCase("happybirthday") || input.equalsIgnoreCase("happy birthday") || input.equalsIgnoreCase("happy birthday!") || input.equalsIgnoreCase("happy birthday song")) {
                String link;
                link = "https://youtu.be/u7P10onJbkY";
                event.reply("\uD83E\uDD42").queue();
                musicManager.scheduler.PlayCmd.setTitle("\uD83E\uDD73 Happy Birthday!");
                musicManager.scheduler.PlayCmd.setDescription("");
                musicManager.scheduler.PlayCmd.setColor(new Color(239, 149, 157));
                musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();
                musicManager.scheduler.repeating = true;
                RequestMetadata rm = new RequestMetadata(event.getUser());
                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, false, false, false, null, null);
            } else if (input.contains("https://open.spotify.com/track") || input.contains("https://open.spotify.com/playlist") || input.contains("https://open.spotify.com/album")) {
                PlaySpotifySlashCommand ob = new PlaySpotifySlashCommand();
                if (input.contains("https://open.spotify.com/track"))
                    ob.playSpotifyTrack(event, null);
                else if (input.contains("https://open.spotify.com/playlist")) {
                    ob.playSpotifyPlaylist(event, null);
                }
                else
                    event.reply("⚠ Hmmm...only Spotify tracks and playlists can be played at the moment...").queue();
            } else {
                musicManager.scheduler.search = 0;
                musicManager.scheduler.searchQueue.clear();
                String link;
                link = input;
                musicManager.scheduler.PlayCmd.setDescription("");
                musicManager.scheduler.PlayCmd.setColor(new Color(242,202,9));
                musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();


               /* if (!isUrl(link)) {
                    link = "ytsearch:" + link;
                }*/

                if (!input.contains("https://www.youtube.com")) {
                    link = "ytsearch:" + link;
                }

                RequestMetadata rm = new RequestMetadata(event.getUser());


                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, false, false, false, event, null);
            }
        }
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
