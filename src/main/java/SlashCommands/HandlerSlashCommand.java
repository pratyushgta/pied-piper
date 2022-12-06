package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;


import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This class contains methods for handling voice channel commands
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class HandlerSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {

        VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();

        if (event.getName().equals("join")) {

            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            if (connectedChannel == null) {
                event.reply("\uD83D\uDE36\u200D\uD83C\uDF2B️Not gonna play music in an empty voice channel. Connect to a voice channel first.").queue();
                return;
            } else if (SelfConnected != null && musicManager.audioPlayer.getPlayingTrack() != null && SelfConnected.getMembers().size() > 1) {
                event.reply("❗ Uh Oh! I am already playing in **\uD83D\uDD0A " + SelfConnected.getName() + "**").queue();
                return;
            }

            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.openAudioConnection(connectedChannel);
            event.reply("\uD83D\uDE4C Ready to rick-n-roll!").queue();
        } else if (event.getName().equals("dc") || event.getName().equals("leave")) { // Checks if the command is !leave.
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

            if (SelfConnected == null) {
                event.reply("✝ Dude...I am not connected to a voice channel. You can't disconnect me buahahaha.").queue();
                return;
            } else if (connectedChannel == null && SelfConnected.getMembers().size() > 1) {
                event.reply("\uD83D\uDE42 Bruh you thought you could disconnect me while people are listening? Too bad. I'm programmed to curse people like you.").queue();
                return;
            } else if (connectedChannel != SelfConnected && SelfConnected.getMembers().size() > 1) {
                event.reply("\uD83E\uDD13 What did you think? you'd disconnect me while others are listening? BOOOO").queue();
                return;
            }

            event.getGuild().getAudioManager().closeAudioConnection();


            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();
            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
            musicManager.audioPlayer.setVolume(100);
            musicManager.scheduler.repeatAll = false;
            musicManager.scheduler.repeating = false;
            musicManager.scheduler.streamer = false;
            musicManager.scheduler.streamerUser = "";
            musicManager.scheduler.search = 0;
            musicManager.scheduler.searchQueue.clear();
            musicManager.scheduler.lastTrack = null;
            musicManager.scheduler.QueueCmd.clear();
            musicManager.scheduler.NowPlayCmd.clear();
            musicManager.scheduler.PlayCmd.clear();
            musicManager.audioPlayer.setPaused(false);
            musicManager.scheduler.LyricsQueue.clear();
            musicManager.scheduler.searchQueue.clear();
            musicManager.scheduler.pointer=-1;
            //musicManager.scheduler.eqstop(true);

            event.reply("\uD83D\uDC4B See you soon, Senorita!").queue();

        } else if (event.getName().equals("rc") || event.getName().equals("reconnect")) {

            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("\uD83D\uDE42 Stop disturbing and go get a job.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("✝ Are you having hallucinations? I'm not connected to a vc!").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("\uD83E\uDD13 Ok :)").queue();
                return;
            }

            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setTitle("\uD83D\uDD17 Hold on...flushing out bot juice...RECONNECTING...brb");
            embed1.setColor(new Color(242,202,9));
            event.getGuild().getAudioManager().closeAudioConnection();

            MessageEmbed eb = embed1.build();

            event.replyEmbeds(eb).queue(message -> {
                embed1.setColor(new Color(21,86,80));
                embed1.setTitle("✅ RECONNECTED. Hello Kitty!");
                event.getGuild().getAudioManager().openAudioConnection(connectedChannel);
                message.editOriginalEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            });
        }
    }

    //public class LeaveEvent extends ListenerAdapter {
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();

        if (event.getChannelLeft().equals(SelfConnected)) {
            if (SelfConnected.getMembers().size() == 1) {
                event.getGuild().getAudioManager().closeAudioConnection();
            }
        }
    }
}

