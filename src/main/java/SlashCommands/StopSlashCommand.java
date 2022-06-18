package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.gray;
import static java.awt.Color.yellow;

public class StopSlashCommand extends ListenerAdapter {
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("stop")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Shut up and lemme do my job.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Stop playing what? your gf?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Go dance.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }

            eb.clear();

            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();

            eb.setTitle("⭕ No track playing!");
            eb.setColor(yellow);
            if (musicManager.scheduler.historyQueue.size() > 0)
                eb.setDescription("✅ The player has stopped and the queue has been cleared!\nPress ⏮ to replay the previous queue.");
            else
                eb.setDescription("✅ The player has stopped and the queue has been cleared!");
            event.replyEmbeds(eb.build()).addActionRow(Button.success("Previous", "⏮️"), Button.success("Pause", "⏸️").asDisabled(), Button.success("Skip", "⏭️").asDisabled(), Button.success("Loop", "\uD83D\uDD02").asDisabled(), Button.danger("Leave", "\uD83D\uDC4B").asEnabled()).queue();

        } else if (event.getName().equals("pause")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("You can't pause your life. Nor your gf.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("meh. not in the mood to pause rn.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Pause exactly what again?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }

            musicManager.scheduler.volume = musicManager.scheduler.player.getVolume();
            musicManager.scheduler.player.setVolume(0);

            musicManager.scheduler.player.setPaused(true);


            eb.clear();
            eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
            eb.setColor(gray);
            eb.setTitle("⏸ Player is Paused!");
            eb.setDescription("Press ▶ to resume!");

            RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

            eb.setFooter("Paused by: " + event.getUser().getName(), event.getUser().getAvatarUrl());


            event.replyEmbeds(eb.build())
                    .addActionRow(musicManager.scheduler.historyQueue.size() > 0 ? Button.success("Previous", "⏮️") : Button.success("Previous", "⏮️").asDisabled(), musicManager.audioPlayer.isPaused() ? Button.success("Resume", "▶️") : Button.success("Pause", "⏸️"),
                            Button.success("Skip", "⏭️"), musicManager.scheduler.repeatAll ? Button.success("repeatall", "\uD83D\uDD01") : Button.success("Loop", "\uD83D\uDD02"), Button.success("Stop", "⏹")).queue();


        } else if (event.getName().equals("resume")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ Can't. Bye.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Lemme sleep. You go too.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ Resume what? Your life?").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            if (musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.red);
                eb.setTitle("Cannot unpause while Streamer Mode is on!");
                eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
                return;
            }

            if(audioPlayer.getPlayingTrack()==null)
            {
                event.replyFormat("⚠ Ahhh...what? Are you a robot?").queue();
                return;
            }

            final AudioTrackInfo info = track.getInfo();
            event.replyFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();
            musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
            musicManager.scheduler.volume = 0;
            musicManager.scheduler.player.setPaused(false);

        } else if (event.getName().equals("clear")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("\uD83D\uDE42 Stop disturbing me.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Lemme sleep. You go too.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ bruh I'm not even playing music in your voice channel").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();


            musicManager.scheduler.queue.clear();
            musicManager.scheduler.historyQueue.clear();
            event.reply("✅ The queue has been cleared!").queue();
        }

        else if (event.getName().equals("replay")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ Are you in your senses?").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ the...what?").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ r/stop").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;

            if (musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.red);
                eb.setTitle("Cannot replay while Streamer Mode is on!");
                eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
                return;
            }
            else if (musicManager.scheduler.repeatAll) {
                musicManager.scheduler.repeatAll=false;
                final java.util.List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);
                final java.util.List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);
                final AudioTrack currenttrack = audioPlayer.getPlayingTrack();
                final AudioTrack prevtrack = trackList.get(trackList.size() - 1);

                musicManager.scheduler.queue.clear();
                temptrackList.remove(temptrackList.size()-1);

                musicManager.scheduler.queue.offer(prevtrack.makeClone());

                musicManager.scheduler.queue.offer(currenttrack.makeClone());

                for(int i=0;i<temptrackList.size();i++)
                {
                    musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                }
                musicManager.scheduler.repeatAll=true;
                musicManager.scheduler.nextTrack();
                event.reply("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();

            } else {
                if (musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.getPlayingTrack()!=null || musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.isPaused()) {
                    final java.util.List<AudioTrack> htrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                    final java.util.List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);

                    final AudioTrack prevtrack = htrackList.get(htrackList.size() - 1);
                    final AudioTrack currenttrack = audioPlayer.getPlayingTrack();

                    musicManager.scheduler.queue.clear();

                    int repeatCounter = 0;

                    if (musicManager.scheduler.repeating)
                        musicManager.scheduler.repeating = false;

                    musicManager.scheduler.queue.offer(prevtrack.makeClone());

                    musicManager.scheduler.queue.offer(currenttrack.makeClone());

                    musicManager.scheduler.skiphistory = true;

                    event.reply("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();
                    for (int i = 0; i < temptrackList.size(); i++) {
                        musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                    }
                    musicManager.scheduler.nextTrack();
                    musicManager.scheduler.historyQueue.remove(htrackList.get(htrackList.size() - 1));

                    musicManager.scheduler.skiphistory = false;
                } else if (musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.getPlayingTrack()==null){
                    final java.util.List<AudioTrack> htrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                    final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);

                    final AudioTrack prevtrack = htrackList.get(htrackList.size() - 1);
                    musicManager.scheduler.queue.clear();
                    musicManager.scheduler.queue.offer(prevtrack.makeClone());
                    event.reply("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();
                    for (int i = 0; i < temptrackList.size(); i++) {
                        musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                    }
                    musicManager.scheduler.nextTrack();
                    musicManager.scheduler.historyQueue.remove(htrackList.get(htrackList.size() - 1));
                } else{
                    event.reply("⚠ Failed to replay! Unable to find the previously played track.").queue();
                }
            }
        }

    }
}
