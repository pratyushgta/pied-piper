package BetterQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This class contains methods for displaying the queue
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */

public class QueueCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-q") ||input[0].equalsIgnoreCase("-queue") || input[0].equalsIgnoreCase("-list")) {

                TextChannel channel = event.getChannel();
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

                if (queue.isEmpty()) {
                    channel.sendMessage("⭕ Current Queue is empty!").queue();
                    return;
                }

                final int trackCount = Math.min(queue.size(), 10);
                final List<AudioTrack> trackList = new ArrayList<>(queue);
                int pagenum=0;
                if(queue.size()<11)
                    pagenum=1;
                else if(queue.size()%10==0)
                    pagenum=(queue.size()/10);
                else
                    pagenum=(queue.size()/10)+1;


                int pg=0;
                int start=0;
                int end=0;
                if(input.length==1 && queue.size()>10)
                {
                    end=10;
                    pg=1;
                }
                else if(input.length==1 && queue.size()<10)
                {
                    end=queue.size();
                    pg=1;
                }
                else {
                    if(Integer.parseInt(input[1])>pagenum) {
                        channel.sendMessage("⚠ Queue only has " + pagenum +" pages").queue();
                        return;
                    }
                    pg=Integer.parseInt(input[1]);
                    end = (Integer.parseInt(input[1])) * 10;
                    start = end - 10;
                    if (queue.size() > start && queue.size() < end)
                        end = queue.size();
                }

                EmbedBuilder eb=new EmbedBuilder();
                eb.setTitle("\uD83D\uDDCF Next in Queue | `"+queue.size()+"` tracks "+(musicManager.scheduler.repeatAll ? "| \uD83D\uDD01 " : "") + (musicManager.scheduler.repeating ? "| \uD83D\uDD02" : ""));
                eb.setColor(Color.PINK);
                eb.setDescription("Use `-qp <track #>` to play a specific track from the queue | `-add <song_name/URL>` to add a YT song/ playlist | `-remove <track # / @username` to remove a song");


                for (int i = start; i < end; i++) {
                    final AudioTrack track = trackList.get(i);
                    final AudioTrackInfo info = track.getInfo();
                    RequestMetadata rm = trackList.get(i).getUserData(RequestMetadata.class);

                    eb.addField((i + 1) + ". " + info.title, "`[" + formatTime(track.getDuration()) + "]` by " + info.author + " `[" + rm.user.username + "]`", false);
                }

              if(trackList.size() > trackCount){
                    eb.setFooter("And "+ (trackList.size() - trackCount) +" more tracks.... | Page "+pg+"/"+pagenum+" | Use '-q <page #>' to view more pages | Powered by BetterQueue.");
                }
                else
                {
                    eb.setFooter("Page 1/"+pagenum+" | Powered by BetterQueue");
                }
                channel.sendMessage(eb.build()).queue();
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