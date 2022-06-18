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
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        //String userid = event.getMember().getId();
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-play") || input[0].equalsIgnoreCase("-p") || input[0].equalsIgnoreCase("-add")) {
                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) Objects.requireNonNull(event.getMember().getVoiceState()).getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) Objects.requireNonNull(event.getGuild().getSelfMember().getVoiceState()).getChannel();


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
                    eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                    channel.sendMessageEmbeds(eb.build()).queue();
                    eb.clear();
                    return;
                }

               /* String httpCheck = "";

                if (input.length == 2 && input[1].length() > 32) {
                    for (int i = 0; i < 32; i++) {
                        httpCheck += input[1].charAt(i);
                    }
                    channel.sendMessage(httpCheck).queue();*/
                if (input.length == 1) {
                    if (musicManager.scheduler.player.isPaused()) {
                        final AudioTrackInfo info = track.getInfo();
                        musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
                        musicManager.scheduler.volume = 0;
                        channel.sendMessageFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();

                        musicManager.scheduler.player.setPaused(false);
                    } else if (musicManager.scheduler.queue.isEmpty() && musicManager.scheduler.historyQueue.size() > 1) {
                       // final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);
                        final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                        // AudioTrack xtrack=temptrackList.get(0);
                        musicManager.scheduler.queue.addAll(temptrackList);
                        channel.sendMessage("\uD83D\uDD04 Replaying `" + musicManager.scheduler.historyQueue.size() + "` tracks from the previous queue!").queue();
                        //musicManager.scheduler.player.startTrack(xtrack.makeClone(),false);
                        musicManager.scheduler.nextTrack();
                        musicManager.scheduler.historyQueue.clear();
                    } else {
                        channel.sendMessage("Play go brrrr").queue();
                    }
                } else if (input[1].contains("https://www.youtube.com/playlist")) { //httpCheck.equalsIgnoreCase("https://www.youtube.com/playlist")) {
                    String link;
                    musicManager.scheduler.search = 0;
                    musicManager.scheduler.searchQueue.clear();
                    link = input[1];
                    musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                    musicManager.scheduler.PlayCmd.setDescription("");
                    musicManager.scheduler.PlayCmd.setColor(Color.yellow);
                    musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();
                    RequestMetadata rm = new RequestMetadata(event.getAuthor());
                    PlayerManager.getInstance()
                            .loadAndPlay(channel, link, rm, true, false, null, null);
                } else if (input[1].equalsIgnoreCase("happybirthday") || input[1].equalsIgnoreCase("happy") && input[2].equalsIgnoreCase("birthday")) {
                    String link;
                    link = "https://www.youtube.com/watch?v=ykHAwUhjjGE";
                    musicManager.scheduler.PlayCmd.setTitle("\uD83E\uDD73 Happy Birthday!");
                    musicManager.scheduler.PlayCmd.setDescription("");
                    musicManager.scheduler.PlayCmd.setColor(Color.magenta);
                    musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();
                    musicManager.scheduler.repeating = true;
                    RequestMetadata rm = new RequestMetadata(event.getAuthor());
                    PlayerManager.getInstance()
                            .loadAndPlay(channel, link, rm, false, false, null, null);
                } else if (input[1].contains("https://open.spotify.com/track") || input[1].contains("https://open.spotify.com/playlist") || input[1].contains("https://open.spotify.com/album")) {
                    channel.sendMessage(":( Uh oh! Spotify tracks cannot be played at the moment. The Spotify update is in the pipeline and you shall soon be able to groove to your fav Spotify beats!").queue();
                    return;
                } else {
                    musicManager.scheduler.search = 0;
                    musicManager.scheduler.searchQueue.clear();
                    String link = "";
                    for (int i = 1; i < input.length; i++) {
                        link += input[i] + " ";
                    }

                    musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                    musicManager.scheduler.PlayCmd.setDescription("");
                    musicManager.scheduler.PlayCmd.setColor(Color.yellow);
                    musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();

                    if (!isUrl(link)) {
                        link = "ytsearch:" + link;
                    }

                    RequestMetadata rm = new RequestMetadata(event.getAuthor());


                    PlayerManager.getInstance()
                            .loadAndPlay(channel, link, rm, false, false, null, null);
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