package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for skipping a track
 * For Discord SLASH COMMANDS
 *
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SkipSlashCommand extends ListenerAdapter {
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("skip")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ There's no music playin. Your ears are ringing.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ lemme sleep.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ bruh I'm not playing music in your voice channel").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            if (musicManager.scheduler.repeating) {
                musicManager.scheduler.repeating = false;
            }

            if (audioPlayer.getPlayingTrack() == null && !queue.isEmpty()) {
                event.reply("\uD83D\uDEAB You can't skip time..nor life..nor cupcakes.").queue();
            } else if (musicManager.scheduler.repeatAll) {
                musicManager.scheduler.queue.offer(track.makeClone());
                musicManager.scheduler.nextTrack();

                final AudioTrackInfo info = track.getInfo();
                final AudioTrack trackx = audioPlayer.getPlayingTrack();
                final AudioTrackInfo infox = trackx.getInfo();
                event.replyFormat(event.getMember().getEffectiveName() + " Skipped `%s` \n⏭️ Next-Up: "+infox.title, info.title, info.author, info.uri).queue();

            } else if (queue.isEmpty()) {
                musicManager.audioPlayer.stopTrack();

                final AudioTrackInfo info = track.getInfo();
                event.replyFormat(event.getMember().getEffectiveName() + " Skipped `%s` \n\uD83D\uDDC5 The Queue is empty! You've grooved to all the tracks in the queue...", info.title, info.author, info.uri).queue();

            } else {
                if (musicManager.scheduler.pointer == queue.size() - 1)
                    musicManager.scheduler.pointer = -1;

                musicManager.scheduler.nextTrack();

                final AudioTrackInfo info = track.getInfo();
                final AudioTrack trackx = audioPlayer.getPlayingTrack();
                final AudioTrackInfo infox = trackx.getInfo();
                event.replyFormat(event.getMember().getEffectiveName() + " Skipped `%s` \n⏭️ Next-Up: "+infox.title, info.title, info.author, info.uri).queue();

            }
        }

    }
}
