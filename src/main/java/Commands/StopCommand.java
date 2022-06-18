package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StopCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-stop")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("⚠ Shut up and lemme do my job.").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("⚠ Stop playing what? your gf?").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("\uD83D\uDC40 Go dance.").queue();
                return;
            }


            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            if (audioPlayer.getPlayingTrack() == null) {
                channel.sendMessage("\uD83D\uDE43 There's no music playin. What do you plan to stop huh?").queue();
                return;
            }
            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
            if (musicManager.scheduler.historyQueue.size() > 0)
                channel.sendMessage("✅ The player has stopped and the queue has been cleared!\nUse `-p` to replay the previous queue.").queue();
            else
                channel.sendMessage("✅ The player has stopped and the queue has been cleared!").queue();

        } else if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-pause")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("⚠ You can't pause your life. Nor your gf.").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("⚠ meh. not in the mood to pause rn.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("⚠ Pause exactly what again?").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();


            if(audioPlayer.getPlayingTrack()==null)
            {
                channel.sendMessageFormat("⚠ Do you hear music playing? Your ears are prolly ringing.").queue();
                return;
            }

            final AudioTrackInfo info = track.getInfo();
            channel.sendMessageFormat("⏸ Paused `%s`", info.title, info.author, info.uri).queue();
            musicManager.scheduler.volume = musicManager.scheduler.player.getVolume();
            musicManager.scheduler.player.setVolume(0);

            musicManager.scheduler.player.setPaused(true);

        } else if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-resume")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("⚠ Can't. Bye.").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("⚠ Lemme sleep. You go too.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("⚠ Resume what? Your life?").queue();
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
                channel.sendMessageEmbeds(eb.build()).queue();
                eb.clear();
                return;
            }

            if(audioPlayer.getPlayingTrack()==null)
            {
                channel.sendMessageFormat("⚠ Ahhh...what? Are you a robot?").queue();
                return;
            }

            final AudioTrackInfo info = track.getInfo();
            channel.sendMessageFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();
            musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
            musicManager.scheduler.volume = 0;
            musicManager.scheduler.player.setPaused(false);

        } else if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-clear")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("\uD83D\uDE42 Stop disturbing me.").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("⚠ Lemme sleep. You go too.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("⚠ bruh I'm not even playing music in your voice channel").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();


            musicManager.scheduler.queue.clear();
            musicManager.scheduler.historyQueue.clear();
            channel.sendMessage("✅ The queue has been cleared!").queue();

        } else if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-replay")) { //previous / prev

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("⚠ Are you in your senses?").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("⚠ the...what?").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("⚠ r/stop").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;

            if (musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.red);
                eb.setTitle("Cannot replay while Streamer Mode is on!");
                eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                channel.sendMessageEmbeds(eb.build()).queue();
                eb.clear();
                return;
            }
            else if (musicManager.scheduler.repeatAll) {
                musicManager.scheduler.repeatAll=false;
                final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);
                final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);
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
                event.getChannel().sendMessage("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();

            } else {
                if (musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.getPlayingTrack()!=null || musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.isPaused()) {
                    final List<AudioTrack> htrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                    final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);

                    final AudioTrack prevtrack = htrackList.get(htrackList.size() - 1);
                    final AudioTrack currenttrack = audioPlayer.getPlayingTrack();

                    musicManager.scheduler.queue.clear();

                    int repeatCounter = 0;

                    if (musicManager.scheduler.repeating)
                        musicManager.scheduler.repeating = false;

                    musicManager.scheduler.queue.offer(prevtrack.makeClone());

                    musicManager.scheduler.queue.offer(currenttrack.makeClone());

                    musicManager.scheduler.skiphistory = true;

                    event.getChannel().sendMessage("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();
                    for (int i = 0; i < temptrackList.size(); i++) {
                        musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                    }
                    musicManager.scheduler.nextTrack();
                    musicManager.scheduler.historyQueue.remove(htrackList.get(htrackList.size() - 1));

                    musicManager.scheduler.skiphistory = false;
                } else if (musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.getPlayingTrack()==null){
                    final List<AudioTrack> htrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                    final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);

                    final AudioTrack prevtrack = htrackList.get(htrackList.size() - 1);
                    musicManager.scheduler.queue.clear();
                    musicManager.scheduler.queue.offer(prevtrack.makeClone());
                    event.getChannel().sendMessage("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();
                    for (int i = 0; i < temptrackList.size(); i++) {
                        musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                    }
                    musicManager.scheduler.nextTrack();
                    musicManager.scheduler.historyQueue.remove(htrackList.get(htrackList.size() - 1));
                } else{
                    channel.sendMessage("⚠ Failed to replay! Unable to find the previously played track.").queue();
                }
            }
        }

    }
}
