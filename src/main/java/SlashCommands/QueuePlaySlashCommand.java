package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class QueuePlaySlashCommand extends ListenerAdapter {
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("queue") && Objects.equals(event.getSubcommandName(), "play")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);

            if (queue.isEmpty()) {
                event.reply("⚠ Are you drunk? The queue is empty...").queue();
                return;
            }
            OptionMapping operator1 = event.getOption("track_no");
            if (operator1 == null) {
                return;
            }

            int input = operator1.getAsInt();
            int trackno = input;
            if (trackno - 1 > queue.size()) {
                event.reply("⚠️theee....which song?").queue();
            } else if (trackno == 1) {
                musicManager.scheduler.nextTrack();
                event.replyFormat("⏭️ Now Playing #" + trackno + ". `" + tempTrackList.get(trackno-1).getInfo().title + "` from the queue.").queue();
            } else {
                int Pos1=trackno;
                int Pos2=1;
                for (int i = 0; i < tempTrackList.size(); i++) {
                    queue.remove(tempTrackList.get(i));
                }
                trackList.add(Pos2 - 1, trackList.remove(Pos1 - 1));
                for (int i = 0; i < trackList.size(); i++) {
                    queue.add(trackList.get(i));
                }
                if (musicManager.scheduler.repeatAll) {
                    musicManager.scheduler.queue.offer(audioPlayer.getPlayingTrack().makeClone());
                    musicManager.scheduler.nextTrack();
                }
                else
                {
                    musicManager.scheduler.nextTrack();
                }
                event.replyFormat("⏭️ Now Playing #" + trackno + ". `" + tempTrackList.get(trackno-1).getInfo().title + "` from the queue.").queue();
            }
        }
    }
}