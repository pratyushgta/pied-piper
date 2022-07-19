package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class contains methods for playing tracks from the returned search results
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SearchPlayCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        String userid = event.getMember().getId();
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-sp") || input[0].equalsIgnoreCase("-searchplay")) {
                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();


                if (connectedChannel == null) {
                    channel.sendMessage("⚠️Umm sir? Are you drunk? You need to be in a voice channel first").queue();
                    return;
                } else if (SelfConnected == null) {
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    audioManager.openAudioConnection(connectedChannel);
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("\uD83E\uDEC2️We're not in the same vc. How do you think I will play that?").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();

                if (musicManager.scheduler.streamer) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.red);
                    eb.setTitle("\uD83D\uDEAB Cannot play music while Streamer Mode is on!");
                    channel.sendMessage(eb.build()).queue();
                    eb.clear();
                    return;
                }

                int counter = 0;
                int trackNo = 0;

                if (input.length == 1) {
                    channel.sendMessage("⚠ You need a shower.").queue();
                    return;
                } else if (musicManager.scheduler.search == 0) {
                    channel.sendMessage("⚠ Search list is empty. You need to search for songs before playing one.").queue();
                    return;
                } else {
                    try {
                        trackNo = Integer.parseInt(input[1]);
                    } catch (NumberFormatException ex) {
                        channel.sendMessage("⚠ You need to enter the track number that you want to play from the search list,").queue();
                    }
                    trackNo = Integer.parseInt(input[1]);
                    final List<AudioTrack> searchTrack = new ArrayList<>(musicManager.scheduler.searchQueue);
                    musicManager.scheduler.queue(searchTrack.get(Integer.parseInt(input[1]) - 1));
                    EmbedBuilder embed = new EmbedBuilder();
                    if (musicManager.scheduler.queue.isEmpty()) {
                        embed.setColor(Color.green);
                        embed.setAuthor("\uD83C\uDFB6 Starting to play:");
                        embed.setTitle(searchTrack.get(Integer.parseInt(input[1]) - 1).getInfo().title, searchTrack.get(Integer.parseInt(input[1]) - 1).getInfo().uri);
                        channel.sendMessage(embed.build()).queue();
                        embed.clear();
                    } else {
                        embed.setColor(Color.green);
                        embed.setTitle(searchTrack.get(Integer.parseInt(input[1]) - 1).getInfo().title, searchTrack.get(Integer.parseInt(input[1]) - 1).getInfo().uri);
                        embed.setDescription("\uD83C\uDFB5 Added to the queue at position `" + musicManager.scheduler.queue.size() + "`");
                        channel.sendMessage(embed.build()).queue();
                        embed.clear();
                    }
                    musicManager.scheduler.search = 0;
                    musicManager.scheduler.searchQueue.clear();
                }
            }
        }
    }
}