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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for facilitating movement of tracks in a queue
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueueMove extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-move")) {

            TextChannel channel = event.getChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                channel.sendMessage("⚠ can't move you away from your failure").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("\uD83D\uDC40 ABCD...\nEFGADHD Ooo look a butterfly!").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("⚠ beep boop boop beep boop?").queue();
                return;
            }


            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);

            if (input.length < 3) {
                channel.sendMessage("⚠️Can't displace air duh. Specify what to move and where to move it.").queue();
                return;
            }

            int Pos1;
            int Pos2;

            Pos1 = Integer.parseInt(input[1]);
            Pos2 = Integer.parseInt(input[2]);

            if (Pos1 == Pos2) {
                channel.sendMessage("⚠️Ha! You thought the devs didn't code this out?").queue();
            } else if (Pos1 - 1 > queue.size()) {
                channel.sendMessage("⚠️...a...what song?").queue();
            } else if (Pos2 - 1 > queue.size()) {
                channel.sendMessage("⚠️eh go learn maths. New position does not exist").queue();
            } else { //https://stackoverflow.com/questions/36011356/moving-elements-in-arraylist-java/42043159
                for (int i = 0; i < tempTrackList.size(); i++) {
                    queue.remove(tempTrackList.get(i));
                }
                trackList.add(Pos2 - 1, trackList.remove(Pos1 - 1));
                for (int i = 0; i < trackList.size(); i++) {
                    queue.add(trackList.get(i));
                }
                channel.sendMessage("\uD83D\uDE9A Moved `" + tempTrackList.get(Pos1 - 1).getInfo().title + "` to **#" + Pos2 + "**").queue();
            }
        }
    }
}