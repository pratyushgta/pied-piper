package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for searching and displaying results of search query, from YouTube
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SearchSlashCommand extends ListenerAdapter {
    int start;
    int end;

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("search")) {
            TextChannel channel = event.getTextChannel();

            OptionMapping option = event.getOption("searchterm");

            if (option == null) {
                event.reply("⚠ What do you wanna search? A hidden treasure?").queue();
                return;
            }

            String input = option.getAsString();
            String link;
            link = "ytsearch:" + input;
            // EmbedBuilder embed2 = new EmbedBuilder();
            // event.reply("\uD83D\uDD0D Searching...").queue();
           /* if (!isUrl(link)) {
                link = "ytsearch:" + link;
            }*/

            //User user = new new RequestMetadata(owner)
            RequestMetadata rm = new RequestMetadata(event.getUser());

            start = 0;
            end = 5;

            PlayerManager.getInstance().loadAndPlay(channel, link, rm, false, true, false, event, null);
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

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {

        if (event.getComponentId().equals("One") || (event.getComponentId().equals("Two")) || (event.getComponentId().equals("Three")) || (event.getComponentId().equals("Four")) || (event.getComponentId().equals("Five"))) {
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                event.reply("⚠️Umm sir? Are you drunk? You need to be in a voice channel first").setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                AudioManager audioManager = event.getGuild().getAudioManager();
                audioManager.openAudioConnection(connectedChannel);
            } else if (connectedChannel != SelfConnected) {
                event.reply("\uD83E\uDEC2️We're not in the same vc. How do you think I will play that?").setEphemeral(true).queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getTextChannel().getGuild());

            if (musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(220,77,77));
                eb.setTitle("\uD83D\uDEAB Cannot play music while Streamer Mode is on!");
                eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                event.replyEmbeds(eb.build()).setEphemeral(true).queue();
                eb.clear();
            } else if (musicManager.scheduler.search == 0) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(242,202,9));
                embed.setTitle("⚠ Search list is empty");
                embed.setDescription("You need to search for songs before playing one.");
                event.editMessageEmbeds(embed.build())
                        .setActionRow(Button.success("One", "1️⃣️").asDisabled(), Button.success("Two", "2️⃣️").asDisabled(), Button.success("Three", "3️⃣").asDisabled(), Button.success("Four", "4️⃣").asDisabled(), Button.success("Five", "5️⃣").asDisabled()).queue();
                embed.clear();
            } else {

                if (event.getComponentId().equals("One")) {
                    send(0, event);
                } else if (event.getComponentId().equals("Two")) {
                    send(1, event);
                } else if (event.getComponentId().equals("Three")) {
                    send(2, event);
                } else if (event.getComponentId().equals("Four")) {
                    send(3, event);
                } else if (event.getComponentId().equals("Five")) {
                    send(4, event);
                }
            }
        }
    }

    public void send(int i, ButtonClickEvent event) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getTextChannel().getGuild());

        final List<AudioTrack> searchTrack = new ArrayList<>(musicManager.scheduler.searchQueue);
        //musicManager.scheduler.queue(searchTrack.get(i));
        RequestMetadata rm = new RequestMetadata(event.getUser());
        musicManager.scheduler.queue.add(searchTrack.get(i));
        searchTrack.get(i).setUserData(rm);
        if (musicManager.scheduler.queue.size() == 1 || musicManager.audioPlayer.getPlayingTrack() == null) {
            musicManager.scheduler.nextTrack();
        }

        EmbedBuilder embed = new EmbedBuilder();
        if (musicManager.scheduler.queue.isEmpty()) {
            embed.setColor(new Color(21,86,80));
            embed.setAuthor("\uD83C\uDFB6 Starting to play:");
            embed.setTitle(searchTrack.get(i).getInfo().title, searchTrack.get(i).getInfo().uri);
            event.editMessageEmbeds(embed.build())
                    .setActionRow(Button.success("One", "1️⃣️").asDisabled(), Button.success("Two", "2️⃣️").asDisabled(), Button.success("Three", "3️⃣").asDisabled(), Button.success("Four", "4️⃣").asDisabled(), Button.success("Five", "5️⃣").asDisabled()).queue();

            embed.clear();
        } else {
            embed.setColor(new Color(21,86,80));
            embed.setTitle(searchTrack.get(i).getInfo().title, searchTrack.get(i).getInfo().uri);
            embed.setDescription("\uD83C\uDFB5 Added to the queue at position `" + musicManager.scheduler.queue.size() + "`");
            event.editMessageEmbeds(embed.build())
                    .setActionRow(Button.success("One", "1️⃣️").asDisabled(), Button.success("Two", "2️⃣️").asDisabled(), Button.success("Three", "3️⃣").asDisabled(), Button.success("Four", "4️⃣").asDisabled(), Button.success("Five", "5️⃣").asDisabled()).queue();
            embed.clear();
        }
    }
}