package SlashCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for shuffling the queue
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueueShuffleSlashCommand extends ListenerAdapter {
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("queue") && Objects.equals(event.getSubcommandName(), "shuffle")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                event.reply("⚠ shut up and go shuffle yourself ...you little piece of junk").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("\uD83D\uDC40 ok. i rick rolled you hehe").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ beep beep boop beep?").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);


            if (queue.size() == 0) {
                event.reply("\uD83D\uDEAB eh you do not have any song in the queue.").queue();
            } else if (queue.size() == 1) {
                event.reply("⚠️How do you think I will shuffle just 1 song?").queue();
            } else {
                Collections.shuffle(trackList);
                for (int i = 0; i < trackList.size(); i++) {
                    queue.remove(tempTrackList.get(i));
                    queue.add(trackList.get(i));
                }
                event.reply("✅ Queue shuffled!").queue();
            }
        }
    }
}


