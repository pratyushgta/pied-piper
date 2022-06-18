package Commands;

import com.jagrosh.jdautilities.menu.ButtonMenu;
import com.jagrosh.jdautilities.menu.OrderedMenu;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import utils.FormatUtil;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.awt.FileDialog.LOAD;

public class SearchCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-search") || input[0].equalsIgnoreCase("-s") || input[0].equalsIgnoreCase("-hunt")) {
            /*EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.red);
            embed.setTitle("TEST");
            MessageEmbed eb = embed.build();

            channel.sendMessage(eb).queue(message -> {
                message.addReaction("✔").queue();
                message.addReaction("\uD83D\uDE0A").queue();
                message.addReaction("\uD83D\uDE42").queue();
            });*/

                TextChannel channel = event.getTextChannel();

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
                            .loadAndPlay(channel, link, rm, false, true, null, event);
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

