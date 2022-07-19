package BetterQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for shuffling the queue
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueueShuffle extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-shuffle") || input[0].equalsIgnoreCase("-mix")) {

                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();


                if (connectedChannel == null) {
                    channel.sendMessage("⚠ shut up and go shuffle yourself ...you little piece of junk").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("\uD83D\uDC40 ok. i rick rolled you hehe").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ beep beep boop beep?").queue();
                    return;
                }

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
                final List<AudioTrack> trackList = new ArrayList<>(queue);
                final List<AudioTrack> tempTrackList = new ArrayList<>(queue);


                if (queue.size() == 0) {
                    channel.sendMessage("\uD83D\uDEAB eh you do not have any song in the queue.");
                } else if (queue.size() == 1) {
                    channel.sendMessage("⚠️How do you think I will shuffle just 1 song?");
                } else {
                    Collections.shuffle(trackList);
                    for (int i = 0; i < trackList.size(); i++) {
                        queue.remove(tempTrackList.get(i));
                        queue.add(trackList.get(i));
                    }
                    channel.sendMessage("✅ Queue shuffled!").queue();
                }
            }
        }
    }
}
