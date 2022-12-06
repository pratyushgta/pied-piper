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

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class contains methods for processing and playing YouTube playlists only
 * For Discord SLASH COMMANDS. Redundant class. Will be removed
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class PlaylistSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("playlist")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                event.reply("⚠️You, sir, yes YOU...need to be in a voice channel to play that!").queue();
                return;
            } else if (SelfConnected == null) {
                AudioManager audioManager = event.getGuild().getAudioManager();
                audioManager.openAudioConnection(connectedChannel);
            } else if (connectedChannel != SelfConnected) {
                event.reply("\uD83E\uDEC2️We need to be in the same voice channel to play that!").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            if (musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(220,77,77));
                eb.setTitle("\uD83D\uDEAB Cannot play music while Streamer Mode is on!");
                eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
                return;
            }

            OptionMapping option = event.getOption("input");


            if (option == null) {
                if (musicManager.scheduler.player.isPaused()) {
                    final AudioTrackInfo info = track.getInfo();
                    musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
                    musicManager.scheduler.volume = 0;
                    event.replyFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();

                    musicManager.scheduler.player.setPaused(false);
                    return;
                } else {
                    event.reply("Play willi wonka what?").queue();
                    return;
                }
            }
            String input = option.getAsString();
            String link = "";
            int counter = 0;

            if (input.contains("https:")) {
                counter = 1;
                musicManager.scheduler.search = 0;
                musicManager.scheduler.searchQueue.clear();
                link = input;
                event.reply("✓").queue();
                musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                musicManager.scheduler.PlayCmd.setDescription("");
                musicManager.scheduler.PlayCmd.setColor(new Color(242,202,9));
                musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();
                RequestMetadata rm = new RequestMetadata(event.getUser());
                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, true, false,false, event, null);
            } else {
                musicManager.scheduler.search = 0;
                musicManager.scheduler.searchQueue.clear();
                //String link = "";
                link = input;

                // EmbedBuilder embed2 = new EmbedBuilder();
                event.reply("✓").queue();
                musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                musicManager.scheduler.PlayCmd.setDescription("");
                musicManager.scheduler.PlayCmd.setColor(new Color(242,202,9));
                musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();

                // channel.sendMessage("\uD83D\uDD0D Searching...").queue();
                if (!isUrl(link)) {
                    link = "ytsearch:" + link;
                }

                //User user = new new RequestMetadata(owner)
                RequestMetadata rm = new RequestMetadata(event.getUser());


                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, true, false,false, event, null);
                //User user = new new RequestMetadata(owner)
            }
        }
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
