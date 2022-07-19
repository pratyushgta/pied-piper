package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.*;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This class contains methods for searching and displaying results of search query, from YouTube
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SearchCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-search") || input[0].equalsIgnoreCase("-s") || input[0].equalsIgnoreCase("-hunt")) {

                TextChannel channel = event.getChannel();

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();

                if (input.length == 1) {
                    channel.sendMessage("⚠ What do you wanna search? A hidden treasure?").queue();
                    return;
                } else {
                    String link = "";
                    for (int i = 1; i < input.length; i++) {
                        link += input[i] + " ";
                    }

                    // EmbedBuilder embed2 = new EmbedBuilder();
                    channel.sendMessage("\uD83D\uDD0D Searching...").queue();
                    if (!isUrl(link)) {
                        link = "ytsearch:" + link;
                    }

                    //User user = new new RequestMetadata(owner)
                    RequestMetadata rm = new RequestMetadata(event.getAuthor());

                    PlayerManager.getInstance()
                            .loadAndPlay(channel, link, rm, false, true);
                }

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