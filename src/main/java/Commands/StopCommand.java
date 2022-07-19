package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.util.Objects;

/**
 * This class contains methods to stop playing a track
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class StopCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-stop")) {

            TextChannel channel = event.getChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
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
            channel.sendMessage("✅ The player has stopped and queue has been cleared!").queue();
        } else if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-pause")) {

            TextChannel channel = event.getChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
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

            final AudioTrackInfo info = track.getInfo();
            channel.sendMessageFormat("⏸ Paused `%s`", info.title, info.author, info.uri).queue();
            musicManager.scheduler.volume=musicManager.scheduler.player.getVolume();
            musicManager.scheduler.player.setVolume(0);

           musicManager.scheduler.player.setPaused(true);


        } else if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-resume")) {

            TextChannel channel = event.getChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
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

            if(musicManager.scheduler.streamer){
                EmbedBuilder eb=new EmbedBuilder();
                eb.setColor(Color.red);
                eb.setTitle("Cannot unpause while Streamer Mode is on!");
                channel.sendMessage(eb.build()).queue();
                eb.clear();
                return;
            }



            final AudioTrackInfo info = track.getInfo();
            channel.sendMessageFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();
            musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
            musicManager.scheduler.volume=0;
            musicManager.scheduler.player.setPaused(false);

        } else if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-clear")) {

            TextChannel channel = event.getChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
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

            if (audioPlayer.getPlayingTrack() == null) {
                channel.sendMessage("\uD83D\uDE43 There's no music playin. What do you plan to stop huh?").queue();
                return;
            }
            musicManager.scheduler.queue.clear();
            channel.sendMessage("✅ The queue has been cleared!").queue();

        }
    }
}
