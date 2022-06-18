package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SearchSlashCommand extends ListenerAdapter {
    int start;
    int end;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
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

            PlayerManager.getInstance().loadAndPlay(channel, link, rm, false, true, event, null);
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

  /*  public void loadAndPlay(TextChannel channel, String trackUrl, RequestMetadata rm, boolean plist, boolean search, SlashCommandInteractionEvent event, int st, int ed) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

        final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                event.reply("⭕ Failed to load! Please try again later.").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();
                RequestMetadata s = rm;
                musicManager.scheduler.searchQueue.clear();
                musicManager.scheduler.search = 1;
                for (int i = 0; i < 5; i++) {
                    musicManager.scheduler.searchQueue(tracks.get(i));
                    tracks.get(i).setUserData(s);
                }
                final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.searchQueue);
                //final MessageAction messageAction = channel.sendMessage("\uD83D\uDCD9  Here are the `"+playlist.getName()+"`\n");


                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("\uD83D\uDCD9 Here are the `" + playlist.getName() + "`");
                for (int i = 0; i < 5; i++) {
                    final AudioTrack track = trackList.get(i);
                    final AudioTrackInfo info = track.getInfo();
                    RequestMetadata rm = trackList.get(i).getUserData(RequestMetadata.class);

                    eb.addField((i + 1) + ". " + info.title, " by " + info.author + " `[" + formatTime(track.getDuration()) + "]`", false);
                }
                eb.setFooter("Click on the buttons below to play the track!");
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("One", "1️⃣️"), Button.success("Two", "2️⃣️"), Button.success("Three", "3️⃣"), Button.success("Four", "4️⃣"), Button.success("Five", "5️⃣")).queue();
            }

            @Override
            public void noMatches() {
                event.reply("\uD83D\uDEAB  Uh oh! Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                event.reply("⭕ Failed to load! Error: " + e.getMessage()).queue();
            }
        });
    }*/

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

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
                eb.setColor(Color.red);
                eb.setTitle("\uD83D\uDEAB Cannot play music while Streamer Mode is on!");
                eb.setDescription("Check out `-streamer` or `/streamer` to know more.");
                event.replyEmbeds(eb.build()).setEphemeral(true).queue();
                eb.clear();
            } else if (musicManager.scheduler.search == 0) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.yellow);
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

    public void send(int i, ButtonInteractionEvent event) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getTextChannel().getGuild());

        final List<AudioTrack> searchTrack = new ArrayList<>(musicManager.scheduler.searchQueue);
        musicManager.scheduler.queue(searchTrack.get(i));
        EmbedBuilder embed = new EmbedBuilder();
        if (musicManager.scheduler.queue.isEmpty()) {
            embed.setColor(Color.green);
            embed.setAuthor("\uD83C\uDFB6 Starting to play:");
            embed.setTitle(searchTrack.get(i).getInfo().title, searchTrack.get(i).getInfo().uri);
            event.editMessageEmbeds(embed.build())
                    .setActionRow(Button.success("One", "1️⃣️").asDisabled(), Button.success("Two", "2️⃣️").asDisabled(), Button.success("Three", "3️⃣").asDisabled(), Button.success("Four", "4️⃣").asDisabled(), Button.success("Five", "5️⃣").asDisabled()).queue();

            embed.clear();
        } else {
            embed.setColor(Color.green);
            embed.setTitle(searchTrack.get(i).getInfo().title, searchTrack.get(i).getInfo().uri);
            embed.setDescription("\uD83C\uDFB5 Added to the queue at position `" + musicManager.scheduler.queue.size() + "`");
            event.editMessageEmbeds(embed.build())
                    .setActionRow(Button.success("One", "1️⃣️").asDisabled(), Button.success("Two", "2️⃣️").asDisabled(), Button.success("Three", "3️⃣").asDisabled(), Button.success("Four", "4️⃣").asDisabled(), Button.success("Five", "5️⃣").asDisabled()).queue();
            embed.clear();
        }
    }
}