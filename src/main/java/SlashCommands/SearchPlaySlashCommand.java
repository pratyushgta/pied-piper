package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SearchPlaySlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("sp")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                event.reply("⚠️Umm sir? Are you drunk? You need to be in a voice channel first").queue();
                return;
            } else if (SelfConnected == null) {
                AudioManager audioManager = event.getGuild().getAudioManager();
                audioManager.openAudioConnection(connectedChannel);
            } else if (connectedChannel != SelfConnected) {
                event.reply("\uD83E\uDEC2️We're not in the same vc. How do you think I will play that?").queue();
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
                channel.sendMessageEmbeds(eb.build()).queue();
                eb.clear();
                return;
            }

            OptionMapping option = event.getOption("trackno");

            int counter;
            int trackNo;

            int input = option.getAsInt();
            if (musicManager.scheduler.search == 0) {
                event.reply("⚠ Search list is empty. You need to search for songs before playing one.").queue();
                return;
            } else {
                try {
                    trackNo = input;
                } catch (NumberFormatException ex) {
                    event.reply("⚠ You need to enter the track number that you want to play from the search list!").queue();
                }
                final List<AudioTrack> searchTrack = new ArrayList<>(musicManager.scheduler.searchQueue);
                musicManager.scheduler.queue(searchTrack.get(input - 1));
                EmbedBuilder embed = new EmbedBuilder();
                if (musicManager.scheduler.queue.isEmpty()) {
                    embed.setColor(Color.green);
                    embed.setAuthor("\uD83C\uDFB6 Starting to play:");
                    embed.setTitle(searchTrack.get(input - 1).getInfo().title, searchTrack.get(input - 1).getInfo().uri);
                    event.replyEmbeds(embed.build()).queue();
                    embed.clear();
                } else {
                    embed.setColor(Color.green);
                    embed.setTitle(searchTrack.get(input - 1).getInfo().title, searchTrack.get(input - 1).getInfo().uri);
                    embed.setDescription("\uD83C\uDFB5 Added to the queue at position `" + musicManager.scheduler.queue.size() + "`");
                    event.replyEmbeds(embed.build()).queue();
                    embed.clear();
                }
                musicManager.scheduler.search = 0;
                musicManager.scheduler.searchQueue.clear();
            }
        }
    }
}
