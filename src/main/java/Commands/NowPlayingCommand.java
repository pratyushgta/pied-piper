package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import utils.FormatUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import static java.awt.Color.*;


public class NowPlayingCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-np") || input[0].equalsIgnoreCase("-playing") || input[0].equalsIgnoreCase("-now") || input[0].equalsIgnoreCase("-nowplaying") || input[0].equalsIgnoreCase("-n")) {

                TextChannel channel = event.getTextChannel();
                //VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();
                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
                final List<AudioTrack> trackList = new ArrayList<>(queue);

                if (track == null) {
                    EmbedBuilder eb1 = new EmbedBuilder();
                    eb1.setTitle("⭕ No track playing!");
                    eb1.setColor(yellow);
                    channel.sendMessageEmbeds(eb1.build()).queue();
                    return;
                }
                EmbedBuilder eb = new EmbedBuilder();
                final AudioTrackInfo info = track.getInfo();
                eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
                eb.setColor(gray);
                eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
                eb.setTitle(track.getInfo().title, track.getInfo().uri);

                RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

                double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
                eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                        + " " + FormatUtil.progressBar(progress)
                        + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                        + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                        + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");
                // +"\n\n"+(counter ? "⏭️ **Next-Up:** "+trackList.get(0).getInfo().title:"")+"\n"); //➰ ➿ 📺

                eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
                channel.sendMessageEmbeds(eb.build()).queue();
            }
        }
    }
}