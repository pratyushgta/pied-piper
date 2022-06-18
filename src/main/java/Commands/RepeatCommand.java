package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepeatCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-repeat") || input[0].equalsIgnoreCase("-loop")) {

                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ Your mistakes cannot be repeated.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠ Get a life bruv.").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ Stop doing that shit.").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

                final boolean newRepeating = !musicManager.scheduler.repeating;
                final boolean newRepeatAll = !musicManager.scheduler.repeatAll;

                if (input.length > 1 && input[1].equalsIgnoreCase("on")) {
                    musicManager.scheduler.repeating = true;
                    musicManager.scheduler.skiphistory = true;
                    channel.sendMessageFormat("\uD83D\uDD02 This song has been set to **repeating**").queue();
                } else if (input.length > 1 && input[1].equalsIgnoreCase("off")) {
                    musicManager.scheduler.repeating = false;
                    musicManager.scheduler.skiphistory = false;
                    channel.sendMessageFormat("\uD83D\uDC4C This song has been set to **not repeating**").queue();
                } else if (input.length == 3 && input[1].equalsIgnoreCase("all") && input[2].equalsIgnoreCase("on")) {
                    musicManager.scheduler.repeatAll = true;
                    channel.sendMessageFormat("\uD83D\uDD01 The queue has been set to **repeating**").queue();
                    musicManager.scheduler.rephistory = true;

                    musicManager.scheduler.historyQueue.clear();
                    final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);

                    for (int i = 0; i < trackList.size(); i++) {
                        musicManager.scheduler.historyQueue.offer(trackList.get(i).makeClone());
                    }
                } else if (input.length == 3 && input[1].equalsIgnoreCase("all") && input[2].equalsIgnoreCase("off")) {
                    musicManager.scheduler.repeatAll = false;
                    musicManager.scheduler.rephistory = false;
                    channel.sendMessageFormat("\uD83D\uDC4C The queue has been set to **not repeating**").queue();
                } else if (input.length > 1 && input[1].equalsIgnoreCase("all")) {
                    if (musicManager.scheduler.queue.isEmpty()) {
                        musicManager.scheduler.repeating = newRepeating;
                        channel.sendMessageFormat("\uD83D\uDD02 This song has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
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
                        channel.sendMessageFormat("\uD83D\uDD01 The queue has been set to **%s**", newRepeatAll ? "repeating" : "not repeating").queue();
                    }
                } else {
                    musicManager.scheduler.repeating = newRepeating;
                    if (!musicManager.scheduler.repeatAll) {
                        musicManager.scheduler.skiphistory = true;
                        musicManager.scheduler.historyQueue.add(musicManager.audioPlayer.getPlayingTrack());
                    } else
                        musicManager.scheduler.skiphistory = false;
                    channel.sendMessageFormat("\uD83D\uDD02 This song has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
                }
            } else if (input[0].equalsIgnoreCase("-restart")) {
                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ Your mistakes cannot be repeated.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠ Get a life bruv.").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ Stop doing that shit.").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                AudioTrack track = musicManager.audioPlayer.getPlayingTrack();
                if (track == null) {
                    track = musicManager.scheduler.lastTrack;
                }

                if (track != null) {
                    event.getChannel().sendMessage("\uD83D\uDD01  **Restarting**: " + track.getInfo().title).queue();
                    musicManager.audioPlayer.playTrack(track.makeClone());
                } else {
                    event.getChannel().sendMessage("\uD83D\uDEAB Uh! Oh! Previous song not found!").queue();
                }
            }
        }
    }
}

