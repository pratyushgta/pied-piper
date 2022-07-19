package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Objects;

/**
 * This class contains methods to change player volume
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class VolumeCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() ) {
            if(input.length>1 && input[0].equalsIgnoreCase("-vol") ||  input.length>1 &&input[0].equalsIgnoreCase("-v")) {

                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠  Go back to your online classes, you little ball of shit.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠  Master volume can only be changed while in a vc!").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠  Stop disturbing my masters.").queue();
                    return;
                }

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();


                    if (Integer.parseInt(input[1]) > 100) {
                        channel.sendMessage("\uD83D\uDEAB Geez. You want your ear drums to burst open? Volume must be a valid natural number between 0 and 100!").queue();
                    } else {
                        try {
                            int volume = Integer.parseInt(input[1]);
                            musicManager.scheduler.player.setVolume(volume);
                            channel.sendMessage("\uD83D\uDD0A Volume changed to: " + volume + "%").queue();
                        } catch (NumberFormatException ignored) {
                            channel.sendMessage("\uD83D\uDEAB Geez. You want your ear drums to burst open? Volume must be a valid natural number between 0 and 100!").queue();
                        }
                    }
            } else if(input[0].equalsIgnoreCase("-max")){
                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠  Go back to your online classes, you little ball of shit.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠  Master volume can only be changed while in a vc!").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠  Stop disturbing my masters.").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();
                musicManager.scheduler.player.setVolume(100);
                channel.sendMessage("\uD83D\uDD0A Volume changed to: 100%").queue();
            } else if(input[0].equalsIgnoreCase("-mute")){
                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠  Go back to your online classes, you little ball of shit.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠  Master volume can only be changed while in a vc!").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠  Stop disturbing my masters.").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();
                musicManager.scheduler.player.setVolume(0);
                channel.sendMessage("\uD83D\uDD07 Volume changed to: 0%").queue();
            } else if(input.length==1 && input[0].equalsIgnoreCase("-vol") || input.length==1 && input[0].equalsIgnoreCase("-v")){
                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();
                int vol = musicManager.scheduler.player.getVolume();
                channel.sendMessage("\uD83D\uDD0A Current volume is: " + vol + "%").queue();
            }
        }
    }
}
