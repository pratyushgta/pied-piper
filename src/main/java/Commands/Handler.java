package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This class contains methods for handling voice channel commands
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class Handler extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        String message1 = event.getMessage().getContentRaw().toLowerCase();
        TextChannel channel = event.getChannel();
        if(message1.equalsIgnoreCase("-join") || message1.equalsIgnoreCase("-summon") || message1.equalsIgnoreCase("-j") || message1.equalsIgnoreCase("-come")) {
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            if(connectedChannel == null) {
                channel.sendMessage("\uD83D\uDE36\u200D\uD83C\uDF2B️Not gonna play music in an empty voice channel. Connect to a voice channel first.").queue();
                return;
            }
            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.openAudioConnection(connectedChannel);
            channel.sendMessage("\uD83D\uDE4C Ready to rick-n-roll!").queue();
        }else if(message1.equalsIgnoreCase("-dc")|| message1.equalsIgnoreCase("-leave") || message1.equalsIgnoreCase("-die") || message1.equalsIgnoreCase("-d") || message1.equalsIgnoreCase("-l")) {
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("\uD83D\uDE42 Bruh you thought you could disconnect me while people are listening? Too bad. I'm programmed to curse people like you.").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("✝ Dude...I am not connected to a voice channel. You can't disconnect me buahahaha.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("\uD83E\uDD13 What did you think? you'd disconnect me while others are listening? BOOOO").queue();
                return;
            }

            event.getGuild().getAudioManager().closeAudioConnection();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();
            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
            musicManager.audioPlayer.setVolume(100);
            musicManager.scheduler.repeatAll=false;
            musicManager.scheduler.repeating=false;
            musicManager.scheduler.streamer=false;
            musicManager.scheduler.streamerUser = "";
            musicManager.scheduler.search=0;
            musicManager.scheduler.searchQueue.clear();
            channel.sendMessage("\uD83D\uDC4B Goodbye, Senorita!").queue();
        } else if(message1.equalsIgnoreCase("-rc")|| message1.equalsIgnoreCase("-reconnect") || message1.equalsIgnoreCase("-retry")) { // Checks if the command is !leave.
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("\uD83D\uDE42 Bruh you thought you could disconnect me while people are listening? Too bad. I'm programmed to curse people like you.").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("✝ Dude...I am not connected to a voice channel. You can't disconnect me buahahaha.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("\uD83E\uDD13 What did you think? you'd disconnect me while others are listening? BOOOO").queue();
                return;
            }

            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setTitle("\uD83D\uDD17 Hold on...RECONNECTING...brb");
            embed1.setColor(Color.yellow);
            event.getGuild().getAudioManager().closeAudioConnection();
            AudioManager audioManager = event.getGuild().getAudioManager();

            MessageEmbed eb = embed1.build();

                channel.sendMessage(eb).queue(message -> {
                    embed1.setColor(Color.green);
                    embed1.setTitle("✅ RECONNECTED. Hello there!");
                    event.getGuild().getAudioManager().openAudioConnection(connectedChannel);
                    message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
        }
    }
}
