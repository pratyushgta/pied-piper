package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for skipping a track
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SkipCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() ) {
            if(input[0].equalsIgnoreCase("-skip") || input[0].equalsIgnoreCase("-next")) {

                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ There's no music playin. Your ears are ringing.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠ lemme sleep.").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ bruh I'm not playing music in your voice channel").queue();
                    return;
                }

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();
                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
                final List<AudioTrack> trackList = new ArrayList<>(queue);

                if (audioPlayer.getPlayingTrack() == null) {
                    channel.sendMessage("\uD83D\uDEAB You can't skip time..nor life..nor cupcakes.").queue();
                } else if (musicManager.scheduler.repeatAll) {
                    musicManager.scheduler.queue.offer(track.makeClone());
                    musicManager.scheduler.nextTrack();
                } else if (queue.isEmpty()) {
                    musicManager.scheduler.nextTrack();
                    final AudioTrackInfo info = track.getInfo();
                    channel.sendMessageFormat(event.getMember().getEffectiveName() + " Skipped `%s`", info.title, info.author, info.uri).queue();
                    channel.sendMessage("\uD83D\uDDC5 The Queue is empty! You've listened to all song in the queue...").queue();
                } else {
                    musicManager.scheduler.nextTrack();
                    final AudioTrackInfo info = track.getInfo();
                    channel.sendMessageFormat(event.getMember().getEffectiveName() + " Skipped `%s`", info.title, info.author, info.uri).queue();

                    final AudioTrack trackx = audioPlayer.getPlayingTrack();
                    final AudioTrackInfo infox = trackx.getInfo();
                    channel.sendMessageFormat("⏭️ Next-Up: " + infox.title).queue();
                }
            }

        }
    }
}