package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This class contains methods for processing and playing YouTube playlists only
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class PlaylistCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        String userid = event.getMember().getId();
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-playlist") || input[0].equalsIgnoreCase("-px")) {
                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();


                if (connectedChannel == null) {
                    channel.sendMessage("⚠️You, sir, yes YOU...need to be in a voice channel to play that!").queue();
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
                    eb.setTitle("Cannot play music while Streamer Mode is on!");
                    channel.sendMessage(eb.build()).queue();
                    eb.clear();
                    return;
                }


                else if (input.length == 1) {
                    if (musicManager.scheduler.player.isPaused()) {
                        final AudioTrackInfo info = track.getInfo();
                        musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
                        musicManager.scheduler.volume = 0;
                        channel.sendMessageFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();

                        musicManager.scheduler.player.setPaused(false);
                    } else {
                        channel.sendMessage("Play wut again?").queue();
                        return;
                    }
                } else {
                    String link = "";
                    String httpCheck = "";

                    if(input.length==2 && input[1].length()>6) {
                        for (int i = 0; i < 6; i++) {
                            httpCheck += input[1].charAt(i);
                        }
                        //channel.sendMessage(httpCheck).queue();
                        if (httpCheck.equalsIgnoreCase("https:")) {
                            musicManager.scheduler.search = 0;
                            musicManager.scheduler.searchQueue.clear();
                            link = input[1];
                            musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                            musicManager.scheduler.PlayCmd.setDescription("");
                            musicManager.scheduler.PlayCmd.setColor(Color.yellow);
                            musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();
                            RequestMetadata rm = new RequestMetadata(event.getAuthor());
                            PlayerManager.getInstance()
                                    .loadAndPlay(channel, link, rm, true, false);
                        } else {
                            musicManager.scheduler.search = 0;
                            musicManager.scheduler.searchQueue.clear();
                            //String link = "";
                            for (int i = 1; i < input.length; i++) {
                                link += input[i] + " ";
                            }

                            // EmbedBuilder embed2 = new EmbedBuilder();
                            musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                            musicManager.scheduler.PlayCmd.setDescription("");
                            musicManager.scheduler.PlayCmd.setColor(Color.yellow);
                            musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();

                            // channel.sendMessage("\uD83D\uDD0D Searching...").queue();
                            if (!isUrl(link)) {
                                link = "ytsearch:" + link;
                            }

                            //User user = new new RequestMetadata(owner)
                            RequestMetadata rm = new RequestMetadata(event.getAuthor());


                            PlayerManager.getInstance()
                                    .loadAndPlay(channel, link, rm, true, false);
                            //User user = new new RequestMetadata(owner)
                        }
                    }
                    else
                    {
                        musicManager.scheduler.search = 0;
                        musicManager.scheduler.searchQueue.clear();
                        //String link = "";
                        for (int i = 1; i < input.length; i++) {
                            link += input[i] + " ";
                        }

                        // EmbedBuilder embed2 = new EmbedBuilder();
                        musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                        musicManager.scheduler.PlayCmd.setDescription("");
                        musicManager.scheduler.PlayCmd.setColor(Color.yellow);
                        musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();

                        // channel.sendMessage("\uD83D\uDD0D Searching...").queue();
                        if (!isUrl(link)) {
                            link = "ytsearch:" + link;
                        }

                        //User user = new new RequestMetadata(owner)
                        RequestMetadata rm = new RequestMetadata(event.getAuthor());


                        PlayerManager.getInstance()
                                .loadAndPlay(channel, link, rm, true, false);
                    }

                }
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

