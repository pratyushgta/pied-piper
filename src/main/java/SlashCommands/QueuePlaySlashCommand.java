package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for playing a specified track from the queue
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueuePlaySlashCommand extends ListenerAdapter {
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("queue") && Objects.equals(event.getSubcommandName(), "play")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);

            if (queue.isEmpty()) {
                event.reply("⚠ Are you drunk? The queue is empty...").queue();
                return;
            }
            OptionMapping operator1 = event.getOption("track_no");
            if (operator1 == null) {
                return;
            }

            int trackno = (int) operator1.getAsLong();

            if (trackno-1  >= queue.size() || trackno <= 0) {
                event.reply("⚠️theee....which song?").queue();
            } else {
                musicManager.scheduler.pointer = trackno-2;
                musicManager.scheduler.nextTrack();
                event.replyFormat("⏭️ Now Playing #" + trackno + ". `" + tempTrackList.get(trackno-1).getInfo().title + "` from the queue.").queue();
            }
        }
    }
}