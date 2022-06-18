package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Handler extends ListenerAdapter {
    // Only listening to guild messages.
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Good practise to ignore bots.
        if (event.getAuthor().isBot()) {
            return;
        }
        // Gets the raw message content and binds it to a local variable.
        String message1 = event.getMessage().getContentRaw().toLowerCase();
        // So we don't have to access event.getChannel() every time.
        TextChannel channel = event.getTextChannel();
        VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
        if (message1.equalsIgnoreCase("-join") || message1.equalsIgnoreCase("-summon") || message1.equalsIgnoreCase("-j") || message1.equalsIgnoreCase("-come") || message1.equalsIgnoreCase("-cum")) {
            // Checks if the bot has permissions.

            // Creates a variable equal to the channel that the user is in.
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            // Checks if they are in a channel -- not being in a channel means that the variable = null.
            if (connectedChannel == null) {
                // Don't forget to .queue()!
                channel.sendMessage("\uD83D\uDE36\u200D\uD83C\uDF2B️Not gonna play music in an empty voice channel. Connect to a voice channel first.").queue();
                return;
            } else if (SelfConnected != null && musicManager.audioPlayer.getPlayingTrack() != null && SelfConnected.getMembers().size() > 1) {
                channel.sendMessage("❗ Uh Oh! I am already playing in **\uD83D\uDD0A " + SelfConnected.getName() + "**").queue();
                return;
            }
            // Gets the audio manager.
            AudioManager audioManager = event.getGuild().getAudioManager();
            // When somebody really needs to chill.

            // Connects to the channel.
            audioManager.openAudioConnection(connectedChannel);
            // Obviously people do not notice someone/something connecting.
            channel.sendMessage("\uD83D\uDE4C Ready to rick-n-roll!").queue();
        } else if (message1.equalsIgnoreCase("-dc") || message1.equalsIgnoreCase("-leave") || message1.equalsIgnoreCase("-die") || message1.equalsIgnoreCase("-d") || message1.equalsIgnoreCase("-l") || message1.equalsIgnoreCase("-fuckoff")) { // Checks if the command is !leave.
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

            if (SelfConnected == null) {
                channel.sendMessage("✝ Dude...I am not connected to a voice channel. You can't disconnect me buahahaha.").queue();
                return;
            } else if (connectedChannel == null && SelfConnected.getMembers().size() > 1) {
                channel.sendMessage("\uD83D\uDE42 Bruh you thought you could disconnect me while people are listening? Too bad. I'm programmed to curse people like you.").queue();
                return;
            } else if (connectedChannel != SelfConnected && SelfConnected.getMembers().size() > 1) {
                channel.sendMessage("\uD83E\uDD13 What did you think? you'd disconnect me while others are listening? BOOOO").queue();
                return;
            }

            event.getGuild().getAudioManager().closeAudioConnection();

            // Notify the user.
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
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
            musicManager.scheduler.historyQueue.clear();
            musicManager.scheduler.QueueCmd.clear();
            musicManager.scheduler.NowPlayCmd.clear();
            musicManager.scheduler.PlayCmd.clear();
            musicManager.scheduler.streamer=false;
            musicManager.scheduler.streamerUser="";
            musicManager.audioPlayer.setPaused(false);

            musicManager.scheduler.skiphistory = false;
            musicManager.scheduler.rephistory = false;
            //musicManager.scheduler.eqstop(true);
            channel.sendMessage("\uD83D\uDC4B Goodbye, Senorita!").queue();
        } else if (message1.equalsIgnoreCase("-rc") || message1.equalsIgnoreCase("-reconnect") || message1.equalsIgnoreCase("-retry")) { // Checks if the command is !leave.
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                channel.sendMessage("\uD83D\uDE42 Stop disturbing and go get a job.").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("✝ Are you having hallucinations? I'm not connected to a vc!").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("\uD83E\uDD13 Ok :)").queue();
                return;
            }

            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setTitle("\uD83D\uDD17 Hold on...flushing out bot juice...RECONNECTING...brb");
            embed1.setColor(Color.yellow);
            event.getGuild().getAudioManager().closeAudioConnection();
            AudioManager audioManager = event.getGuild().getAudioManager();
            //audioManager.openAudioConnection(connectedChannel);

            MessageEmbed eb = embed1.build();

            channel.sendMessageEmbeds(eb).queue(message -> {
                embed1.setColor(Color.green);
                embed1.setTitle("✅ RECONNECTED. Hello Kitty!");
                event.getGuild().getAudioManager().openAudioConnection(connectedChannel);
                message.editMessageEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            });

            //channel.sendMessage("\uD83D\uDC4B Goodbye, Senorita!").queue();
        }
    }

    //public class LeaveEvent extends ListenerAdapter {
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
        VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();

        if (event.getChannelLeft().equals(SelfConnected)) {
            if (SelfConnected.getMembers().size() == 1) {
                event.getGuild().getAudioManager().closeAudioConnection();
            }
        }
    }
}


