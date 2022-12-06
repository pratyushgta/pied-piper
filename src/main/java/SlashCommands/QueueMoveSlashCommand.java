package SlashCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * This class contains methods for facilitating movement of tracks in a queue
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * BetterQueue by Pratyush Kumar
 */


public class QueueMoveSlashCommand extends ListenerAdapter {
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("queue") && Objects.equals(event.getSubcommandName(), "move")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                event.reply("⚠ can't move you away from your failure").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("\uD83D\uDC40 ABCD...\nEFGADHD Ooo look a butterfly!").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ beep boop boop beep boop?").queue();
                return;
            }


            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);


            OptionMapping operator1 = event.getOption("track_no");
            OptionMapping operator2 = event.getOption("new_pos");

            if (operator1 == null || operator2 == null) {
                return;
            }

            int Pos1 = (int) operator1.getAsLong();
            int Pos2 = (int) operator2.getAsLong();

            if (Pos1 == Pos2) {
                event.reply("⚠️Ha! You thought the devs didn't code this out?").queue();
            } else if (Pos1 - 1 > queue.size()) {
                event.reply("⚠️...a...what song?").queue();
            } else if (Pos2 - 1 > queue.size()) {
                event.reply("⚠️eh go learn maths. New position does not exist").queue();
            } else { //https://stackoverflow.com/questions/36011356/moving-elements-in-arraylist-java/42043159
                for (int i = 0; i < tempTrackList.size(); i++) {
                    queue.remove(tempTrackList.get(i));
                }
                trackList.add(Pos2 - 1, trackList.remove(Pos1 - 1));
                queue.addAll(trackList);
                event.reply("\uD83D\uDE9A Moved `" + tempTrackList.get(Pos1 - 1).getInfo().title + "` to **#" + Pos2 + "**").queue();
            }
        }
    }
}
