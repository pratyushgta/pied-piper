package BetterQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
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
 * This class contains methods for playing a specified track from the queue
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueuePlay extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-qp")) {

            TextChannel channel = event.getChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);

            if (input.length == 1) {
                channel.sendMessage("⚠ First decide what you wanna play and then come back").queue();
                return;
            } else if (queue.isEmpty()) {
                channel.sendMessage("⚠ Are you drunk?").queue();
                return;
            }

            int trackno = Integer.parseInt(input[1]);
            if (trackno - 1 > queue.size()) {
                channel.sendMessage("⚠️theee....which song?").queue();
            } else if (trackno == 1) {
                musicManager.scheduler.nextTrack();
                channel.sendMessageFormat("⏭️ Now Playing #" + trackno + ". `" + tempTrackList.get(trackno-1).getInfo().title + "` from the queue.").queue();
            } else {
                int Pos1=trackno;
                int Pos2=1;
                for (int i = 0; i < tempTrackList.size(); i++) {
                    queue.remove(tempTrackList.get(i));
                }
                trackList.add(Pos2 - 1, trackList.remove(Pos1 - 1));
                for (int i = 0; i < trackList.size(); i++) {
                    queue.add(trackList.get(i));
                }
                if (musicManager.scheduler.repeatAll) {
                    musicManager.scheduler.queue.offer(audioPlayer.getPlayingTrack().makeClone());
                    musicManager.scheduler.nextTrack();
                }
                else
                {
                    musicManager.scheduler.nextTrack();
                }
                channel.sendMessageFormat("⏭️ Now Playing #" + trackno + ". `" + tempTrackList.get(trackno-1).getInfo().title + "` from the queue.").queue();
                }
            }
        }
    }
