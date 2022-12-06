package SlashCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for swapping elements in the queue
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueueSwapSlashCommand extends ListenerAdapter {
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("queue") && Objects.equals(event.getSubcommandName(), "swap")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                event.reply("⚠ r/drunk").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("\uD83D\uDC40 go do boing boing").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ you in senses?").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);

            OptionMapping operator1 = event.getOption("track_no");
            OptionMapping operator2 = event.getOption("swap_with");

            if (operator1 == null || operator2 == null) {
                return;
            }

            int Pos1 = (int) operator1.getAsLong();
            int Pos2 = (int) operator2.getAsLong();

            if (queue.size() == 0) {
                event.reply("\uD83D\uDEAB oioi you do not have any song in the queue.").queue();
            } else if (queue.size() == 1) {
                channel.sendMessage("⚠️Dude. Dude. Are you high on maths? There's only 1 track in the queue.").queue();
            } else {
                Collections.swap(trackList, Pos1 - 1, Pos2 - 1);
                for (int i = 0; i < trackList.size(); i++) {
                    queue.remove(tempTrackList.get(i));
                    queue.add(trackList.get(i));
                }
                event.reply("\uD83D\uDD00 Swapped `" + tempTrackList.get(Pos1 - 1).getInfo().title + "` with `" + tempTrackList.get(Pos2 - 1).getInfo().title + "`").queue();
            }
        }
    }
}
