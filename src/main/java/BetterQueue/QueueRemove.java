package BetterQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.IMentionable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;


public class QueueRemove extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-remove")) {

            TextChannel channel = event.getTextChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);


            if (input.length == 1 || musicManager.scheduler.queue.isEmpty()) {
                channel.sendMessage("\uD83D\uDE36 I'm not thanos. I cannot remove your stubborn sister. The syntax you're looking for is `-remove <track number / @username>`").queue();
            } else {
                if (input[1].equalsIgnoreCase("last")) {
                    if (queue.size() == 1) {
                        queue.remove(trackList.get(0));
                    } else {
                        queue.remove(trackList.get(queue.size() - 1));
                    }
                    channel.sendMessage("✅ Removed the last song form the list!").queue();
                } else if (input[1].equalsIgnoreCase("first")) {
                    queue.remove(trackList.get(0));
                    channel.sendMessage("✅ Removed the first song form the list!").queue();
                } else if (!event.getMessage().getMentions().getMembers().isEmpty()) { //!event.getMessage().getMentionedMembers().isEmpty()
                    final Member target = event.getMessage().getMentions().getMembers().get(0); //event.getMessage().getMentionedMembers().get(0);
                    String targetName = target.getEffectiveName();
                    int counter = 0;
                    for (int i = 0; i < trackList.size(); i++) {
                        RequestMetadata rm = trackList.get(i).getUserData(RequestMetadata.class);
                        if (rm.user.username.equalsIgnoreCase(targetName)) {
                            queue.remove(trackList.get(i));
                            counter = 1;
                        }
                    }
                    if (counter == 0)
                        channel.sendMessage("\uD83D\uDEAB Tracks added by **" + targetName + "** not found in the queue.").queue();
                    else
                        channel.sendMessage("✅ Removed all songs added by **" + targetName + "**").queue();
                } else {
                    int pos;
                    try {
                        pos = Integer.parseInt(input[1]);
                    } catch (NumberFormatException e) {
                        pos = 0;
                    }
                    if (pos < 1 || pos > musicManager.scheduler.queue.size()) {
                        channel.sendMessage("❌ Cannot delete something that doesn't even exist").queue();
                        return;
                    } else {
                        final AudioTrack track = trackList.get(pos - 1);
                        final AudioTrackInfo info = track.getInfo();
                        queue.remove(trackList.get(pos - 1));

                        channel.sendMessageFormat("✅ " + event.getMember().getEffectiveName() + " Removed `%s`", info.title, info.author, info.uri).queue();
                    }
                }
            }
        }
    }
}
