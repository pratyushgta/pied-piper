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
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaySlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("play")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getSelfMember().getVoiceState()).getChannel();

            if (connectedChannel == null) {
                event.reply("⚠️You need to be in a voice channel to play that!").queue();
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
                eb.setColor(Color.red);
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
                } else if (musicManager.scheduler.queue.isEmpty() && musicManager.scheduler.historyQueue.size() > 1) {
                   // final java.util.List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);
                    final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                    // AudioTrack xtrack=temptrackList.get(0);
                    musicManager.scheduler.queue.addAll(temptrackList);
                    event.reply("\uD83D\uDD04 Replaying `" + musicManager.scheduler.historyQueue.size() + "` tracks from the previous queue!").queue();
                    //musicManager.scheduler.player.startTrack(xtrack.makeClone(),false);
                    musicManager.scheduler.nextTrack();
                    musicManager.scheduler.historyQueue.clear();
                    return;
                } else {
                    event.reply("Play go brrrr").queue();
                    return;
                }
            }

            String input = option.getAsString();

            if (input.contains("https://www.youtube.com/playlist")) { //httpCheck.equalsIgnoreCase("https://www.youtube.com/playlist")) {
                musicManager.scheduler.search = 0;
                String link;
                musicManager.scheduler.searchQueue.clear();
                link = input;
                event.reply("✓").queue();
                musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                musicManager.scheduler.PlayCmd.setDescription("");
                musicManager.scheduler.PlayCmd.setColor(Color.yellow);
                musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();
                RequestMetadata rm = new RequestMetadata(event.getUser());
                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, true, false, null, null);
            } else if (input.equalsIgnoreCase("happybirthday") || input.equalsIgnoreCase("happy birthday")) {
                String link;
                link = "https://www.youtube.com/watch?v=ykHAwUhjjGE";
                event.reply("✓").queue();
                musicManager.scheduler.PlayCmd.setTitle("\uD83E\uDD73 Happy Birthday!");
                musicManager.scheduler.PlayCmd.setDescription("");
                musicManager.scheduler.PlayCmd.setColor(Color.magenta);
                musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();
                musicManager.scheduler.repeating = true;
                RequestMetadata rm = new RequestMetadata(event.getUser());
                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, false, false, null, null);
            } else if (input.contains("https://open.spotify.com/track") || input.contains("https://open.spotify.com/playlist") || input.contains("https://open.spotify.com/album")) {
                channel.sendMessage(":( Uh oh! Spotify tracks cannot be played at the moment. The Spotify update is in the pipeline and you shall soon be able to groove to your fav Spotify beats!").queue();
            } else {
                musicManager.scheduler.search = 0;
                musicManager.scheduler.searchQueue.clear();
                String link;
                link = input;
                event.reply("✓").queue();
                musicManager.scheduler.PlayCmd.setTitle("\uD83D\uDD0D Searching...");
                musicManager.scheduler.PlayCmd.setDescription("");
                musicManager.scheduler.PlayCmd.setColor(Color.yellow);
                musicManager.scheduler.Play = musicManager.scheduler.PlayCmd.build();


               /* if (!isUrl(link)) {
                    link = "ytsearch:" + link;
                }*/

                if (!input.contains("https://www.youtube.com")) {
                    link = "ytsearch:" + link;
                }

                RequestMetadata rm = new RequestMetadata(event.getUser());


                PlayerManager.getInstance()
                        .loadAndPlay(channel, link, rm, false, false, null, null);
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
