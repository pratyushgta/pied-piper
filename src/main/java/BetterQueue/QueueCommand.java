package BetterQueue;

import com.jagrosh.jdautilities.menu.Paginator;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-q") || input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-list")) {

                TextChannel channel = event.getTextChannel();
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

                if (queue.isEmpty() && musicManager.scheduler.historyQueue.isEmpty()) {
                    channel.sendMessage("⭕ Current Queue is empty!").queue();
                    return;
                } else if (queue.isEmpty() && musicManager.scheduler.historyQueue.size() > 1) {
                    channel.sendMessage("⭕ Current Queue is empty! Use `-p` to replay the previous queue.").queue();
                    return;
                } else if (queue.isEmpty() && musicManager.scheduler.historyQueue.size() == 1) {
                    channel.sendMessage("⭕ Current Queue is empty! Use `-p` to replay the previous track.").queue();
                    return;
                }


                final int trackCount = Math.min(queue.size(), 10);

                final List<AudioTrack> trackList = new ArrayList<>(queue);
                int pagenum = 0;
                if (queue.size() < 11)
                    pagenum = 1;
                else if (queue.size() % 10 == 0)
                    pagenum = (queue.size() / 10);
                else
                    pagenum = (queue.size() / 10) + 1;


                int pg = 0;
                int start = 0;
                int end = 0;
                if (input.length == 1 && queue.size() > 10) {
                    end = 10;
                    pg = 1;
                } else if (input.length == 1 && queue.size() < 10) {
                    end = queue.size();
                    pg = 1;
                } else {
                    if (Integer.parseInt(input[1]) > pagenum) {
                        channel.sendMessage("⚠ Queue only has " + pagenum + " pages").queue();
                        return;
                    }
                    pg = Integer.parseInt(input[1]);
                    end = (Integer.parseInt(input[1])) * 10;
                    start = end - 10;
                    if (queue.size() > start && queue.size() < end)
                        end = queue.size();
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("\uD83D\uDDCF Next in Queue | `" + queue.size() + "` tracks " + (musicManager.scheduler.repeatAll ? "| \uD83D\uDD01 " : "") + (musicManager.scheduler.repeating ? "| \uD83D\uDD02" : ""));
                eb.setColor(Color.PINK);
                eb.setDescription("Use `-qp <track #>` to play a specific track from the queue | `-add <song_name/URL>` to add a YT song/ playlist | `-remove <track # / @username` to remove a song");

               /* RequestMetadata rms = musicManager.audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);
                eb.addField((0) + ". " + musicManager.audioPlayer.getPlayingTrack().getInfo().title, "`[" + formatTime(musicManager.audioPlayer.getPlayingTrack().getDuration()) + "]` by " + musicManager.audioPlayer.getPlayingTrack().getInfo().author + " `[" + rms.user.username + "]`", false);*/

                for (int i = start; i < end; i++) {
                    final AudioTrack track = trackList.get(i);
                    final AudioTrackInfo info = track.getInfo();
                    RequestMetadata rm = trackList.get(i).getUserData(RequestMetadata.class);
                    eb.addField((i + 1) + ". " + info.title, "`[" + formatTime(track.getDuration()) + "]` by " + info.author + " `[" + rm.user.username + "]`", false);
                }

                if (trackList.size() > trackCount) {
                    eb.setFooter("And " + (trackList.size() - trackCount) + " more tracks.... | Page " + pg + "/" + pagenum + " | Use '-q <page #>' to view more pages | Powered by BetterQueue.");
                } else {
                    eb.setFooter("Page 1/" + pagenum + " | Powered by BetterQueue");
                }
                channel.sendMessageEmbeds(eb.build()).queue();
            }
        }
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

/*package BetterQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input.length==1 ) {
            if (input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-q")) {

                TextChannel channel = event.getChannel();
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

                if (queue.isEmpty()) {
                    channel.sendMessage("\uD83D\uDE36 Current Queue is empty!").queue();
                    return;
                }
                final int trackCount = Math.min(queue.size(), 20);
                final List<AudioTrack> trackList = new ArrayList<>(queue);
                final MessageAction messageAction = channel.sendMessage("**\uD83C\uDFBC Next in Queue:**\t\t\t" + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\n");


                for (int i = 0; i < trackCount; i++) {
                    final AudioTrack track = trackList.get(i);
                    final AudioTrackInfo info = track.getInfo();
                    RequestMetadata rm = trackList.get(i).getUserData(RequestMetadata.class);

                    messageAction.append('#')
                            .append(String.valueOf(i + 1))
                            .append(" `")
                            .append(String.valueOf(info.title))
                            .append(" by ")
                            .append(info.author)
                            .append("` [`")
                            .append(formatTime(track.getDuration()))
                            .append("`]")
                            .append(" -- ")
                            .append(rm.user.username + "\n");
                }

                if (trackList.size() > trackCount) {
                    messageAction.append("And `")
                            .append(String.valueOf(trackList.size() - trackCount))
                            .append("` more...");
                }

                messageAction.queue();
            }
        }
    }
    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}*/

