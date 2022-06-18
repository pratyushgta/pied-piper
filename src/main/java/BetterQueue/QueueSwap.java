package BetterQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class QueueSwap extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-swap")) {

                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel)event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel)event.getGuild().getSelfMember().getVoiceState().getChannel();


                if (connectedChannel == null) {
                    channel.sendMessage("⚠ r/drunk").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("\uD83D\uDC40 go do boing boing").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ you in senses?").queue();
                    return;
                }

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
                final List<AudioTrack> trackList = new ArrayList<>(queue);
                final List<AudioTrack> tempTrackList = new ArrayList<>(queue);

                if (input.length == 3) {
                    if (queue.size() == 0) {
                        channel.sendMessage("\uD83D\uDEAB oioi you do not have any song in the queue.").queue();
                    } else if (queue.size() == 1) {
                        channel.sendMessage("⚠️Dude. Dude. Are you high on maths? There's only 1 track in the queue.").queue();
                    } else {
                        Collections.swap(trackList, Integer.parseInt(input[1]) - 1, Integer.parseInt(input[2]) - 1);
                        for (int i = 0; i < trackList.size(); i++) {
                            queue.remove(tempTrackList.get(i));
                            queue.add(trackList.get(i));
                        }
                        channel.sendMessage("\uD83D\uDD00 Swapped `" + tempTrackList.get(Integer.parseInt(input[1]) - 1).getInfo().title + "` with `" + tempTrackList.get(Integer.parseInt(input[2]) - 1).getInfo().title + "`").queue();
                    }
                } else {
                    channel.sendMessage("⚠️What are you trying to swap? The syntax is `-swap <track #> <swap with track #>").queue();
                }
            }
        }
    }
}
