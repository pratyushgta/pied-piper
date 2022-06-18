package SlashCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RepeatSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("repeatall")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ Your mistakes cannot be repeated.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Get a life bruv.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ Stop doing that shit.").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

            final boolean newRepeating = !musicManager.scheduler.repeating;
            final boolean newRepeatAll = !musicManager.scheduler.repeatAll;


            if (musicManager.scheduler.queue.isEmpty()) {
                musicManager.scheduler.repeating = newRepeating;
                event.replyFormat("\uD83D\uDD02 This song has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
            } else {
                musicManager.scheduler.repeatAll = newRepeatAll;
                if (musicManager.scheduler.repeatAll) {
                    musicManager.scheduler.rephistory = true;
                    musicManager.scheduler.historyQueue.clear();
                    final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);

                    for (int i = 0; i < trackList.size(); i++) {
                        musicManager.scheduler.historyQueue.offer(trackList.get(i).makeClone());
                    }
                } else {
                    musicManager.scheduler.rephistory = false;
                }
                event.replyFormat("\uD83D\uDD01 The queue has been set to **%s**", newRepeatAll ? "repeating" : "not repeating").queue();
            }
        } else if (event.getName().equals("loop")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ Your mistakes cannot be repeated.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Get a life bruv.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ Stop doing that shit.").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

            final boolean newRepeating = !musicManager.scheduler.repeating;
            final boolean newRepeatAll = !musicManager.scheduler.repeatAll;

            musicManager.scheduler.repeating = newRepeating;
            if (!musicManager.scheduler.repeatAll) {
                musicManager.scheduler.skiphistory = true;
                musicManager.scheduler.historyQueue.add(musicManager.audioPlayer.getPlayingTrack());
            } else
                musicManager.scheduler.skiphistory = false;
            event.replyFormat("\uD83D\uDD02 This song has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
        }
        else if (event.getName().equals("restart")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ Your mistakes cannot be repeated.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Get a life bruv.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ Stop doing that shit.").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioTrack track = musicManager.audioPlayer.getPlayingTrack();
            if (track == null) {
                track = musicManager.scheduler.lastTrack;
            }

            if (track != null) {
                event.reply("\uD83D\uDD01  **Restarting**: " + track.getInfo().title).queue();
                musicManager.audioPlayer.playTrack(track.makeClone());
            } else {
                event.reply("\uD83D\uDEAB Uh! Oh! Previous song not found!").queue();
            }
        }
    }
}
