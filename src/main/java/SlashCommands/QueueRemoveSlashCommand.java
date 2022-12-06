package SlashCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for removing a track from the queue
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueueRemoveSlashCommand extends ListenerAdapter {
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("queue") && Objects.equals(event.getSubcommandName(), "remove")) {
            TextChannel channel = event.getTextChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);

            OptionMapping operator1 = event.getOption("track_no");

            if (operator1 == null) {
                return;
            }

            int input = (int) operator1.getAsLong();

            if (musicManager.scheduler.queue.isEmpty()) {
                event.reply("\uD83D\uDE36 I'm not thanos. I cannot remove your stubborn sister.").queue();
                return;
            }
            int pos;
            try {
                pos = input;
            } catch (NumberFormatException e) {
                pos = 0;
            }
            if (pos < 1 || pos > musicManager.scheduler.queue.size()) {
                event.reply("❌ Cannot delete something that doesn't even exist").queue();
                return;
            } else {
                final AudioTrack track = trackList.get(pos - 1);
                final AudioTrackInfo info = track.getInfo();
                queue.remove(trackList.get(pos - 1));

                event.replyFormat("✅ " + event.getMember().getEffectiveName() + " Removed `%s`", info.title, info.author, info.uri).queue();
            }
        }
    }
}