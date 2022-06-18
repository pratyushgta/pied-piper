package SlashCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueSlashCommand extends ListenerAdapter {

    int pg = 0;
    int start = 0;
    int end = 0;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("queue") && Objects.equals(event.getSubcommandName(), "view")) {

            TextChannel channel = event.getTextChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            if (queue.isEmpty() && musicManager.scheduler.historyQueue.isEmpty()) {
                event.reply("⭕ Current Queue is empty!").queue();
                return;
            } else if (queue.isEmpty() && musicManager.scheduler.historyQueue.size() > 1) {
                event.reply("⭕ Current Queue is empty! Use `-p` to replay the previous queue.").queue();
                return;
            } else if (queue.isEmpty() && musicManager.scheduler.historyQueue.size() == 1) {
                event.reply("⭕ Current Queue is empty! Use `-p` to replay the previous track.").queue();
                return;
            }


            final int trackCount = Math.min(queue.size(), 10);

            final List<AudioTrack> trackList = new ArrayList<>(queue);

            int pagenum;
            if (queue.size() < 11)
                pagenum = 1;
            else if (queue.size() % 10 == 0)
                pagenum = (queue.size() / 10);
            else
                pagenum = (queue.size() / 10) + 1;


            end = Math.min(queue.size(), 10);

            EmbedBuilder eb = musicManager.scheduler.QueueCmd;
            eb.clear();
            eb.setTitle("\uD83D\uDDCF Next in Queue | `" + queue.size() + "` tracks " + (musicManager.scheduler.repeatAll ? "| \uD83D\uDD01 " : "") + (musicManager.scheduler.repeating ? "| \uD83D\uDD02" : ""));
            eb.setColor(Color.PINK);
            eb.setDescription("Use `-qp <track #>` to play a specific track from the queue | `-add <song_name/URL>` to add a YT song/ playlist | `-remove <track # / @username` to remove a song");


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

            pg = 1;

            if (queue.size() > 10) {
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("Prev", "⬅️"), Button.success("Next", "➡️"), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), Button.success("Shuffle", "\uD83D\uDD00")).queue();
            } else {
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("Prev", "⬅️").asDisabled(), Button.success("Next", "➡️").asDisabled(), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), queue.size() == 1 ? Button.success("Shuffle", "\uD83D\uDD00").asDisabled() : Button.success("Shuffle", "\uD83D\uDD00")).queue();

            }
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        TextChannel channel = event.getTextChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if (event.getComponentId().equals("Prev")) {
            final int trackCount = Math.min(queue.size(), 10);

            final List<AudioTrack> trackList = new ArrayList<>(queue);
            int pagenum;
            if (queue.size() < 11)
                pagenum = 1;
            else if (queue.size() % 10 == 0)
                pagenum = (queue.size() / 10);
            else
                pagenum = (queue.size() / 10) + 1;

            if (pg == 1) {
                pg = pagenum;
            } else {
                pg -= 1;
            }

            end = pg * 10;
            start = end - 10;
            if (queue.size() > start && queue.size() < end)
                end = queue.size();

            EmbedBuilder eb = musicManager.scheduler.QueueCmd;
            eb.clear();
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

            event.editMessageEmbeds(eb.build())
                    .setActionRow(Button.success("Prev", "⬅️"), Button.success("Next", "➡️"), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), Button.success("Shuffle", "\uD83D\uDD00")).queue();


        } else if (event.getComponentId().equals("Next")) {

            final int trackCount = Math.min(queue.size(), 10);

            final List<AudioTrack> trackList = new ArrayList<>(queue);
            int pagenum;
            if (queue.size() < 11)
                pagenum = 1;
            else if (queue.size() % 10 == 0)
                pagenum = (queue.size() / 10);
            else
                pagenum = (queue.size() / 10) + 1;

            if (pg == pagenum) {
                pg = 1;
            } else {
                pg += 1;
            }
            end = pg * 10;
            start = end - 10;
            if (queue.size() > start && queue.size() < end)
                end = queue.size();

            EmbedBuilder eb = musicManager.scheduler.QueueCmd;
            eb.clear();
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

            event.editMessageEmbeds(eb.build())
                    .setActionRow(Button.success("Prev", "⬅️"), Button.success("Next", "➡️"), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), Button.success("Shuffle", "\uD83D\uDD00")).queue();


        } else if (event.getComponentId().equals("Clear")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Stop disturbing me.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Lemme sleep. You go too.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("bruh I'm not even playing music in your voice channel");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }


            musicManager.scheduler.queue.clear();
            musicManager.scheduler.historyQueue.clear();

            EmbedBuilder eb = musicManager.scheduler.QueueCmd;
            eb.clear();
            eb.setTitle("\uD83D\uDDCF Next in Queue | `" + queue.size() + "` tracks " + (musicManager.scheduler.repeatAll ? "| \uD83D\uDD01 " : "") + (musicManager.scheduler.repeating ? "| \uD83D\uDD02" : ""));
            eb.setColor(Color.PINK);
            eb.setDescription("⭕ The queue has been cleared!");
            // eb.setDescription("Use `-qp <track #>` to play a specific track from the queue | `-add <song_name/URL>` to add a YT song/ playlist | `-remove <track # / @username` to remove a song");


            event.editMessageEmbeds(eb.build())
                    .setActionRow(Button.success("Prev", "⬅️").asDisabled(), Button.success("Next", "➡️").asDisabled(), Button.success("Clear","⭕ Clear ").asDisabled(), Button.success("RepeatAll", "\uD83D\uDD01").asDisabled(), Button.success("Shuffle", "\uD83D\uDD00").asDisabled()).queue();

        } else if (event.getComponentId().equals("Shuffle")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("shut up and go shuffle yourself ...you little piece of junk");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("ok. i rick rolled you hehe");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("beep beep boop beep?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }


            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);


            Collections.shuffle(trackList);
            for (int i = 0; i < trackList.size(); i++) {
                queue.remove(tempTrackList.get(i));
                queue.add(trackList.get(i));
            }

            int pagenum;
            if (queue.size() < 11)
                pagenum = 1;
            else if (queue.size() % 10 == 0)
                pagenum = (queue.size() / 10);
            else
                pagenum = (queue.size() / 10) + 1;


            end = pg * 10;
            start = end - 10;
            if (queue.size() > start && queue.size() < end)
                end = queue.size();

            final int trackCount = Math.min(queue.size(), 10);

            EmbedBuilder eb = musicManager.scheduler.QueueCmd;
            eb.clear();
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

            if (queue.size() > 10) {
                event.editMessageEmbeds(eb.build())
                        .setActionRow(Button.success("Prev", "⬅️"), Button.success("Next", "➡️"), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), Button.success("Shuffle", "\uD83D\uDD00")).queue();
            } else {
                event.editMessageEmbeds(eb.build())
                        .setActionRow(Button.success("Prev", "⬅️").asDisabled(), Button.success("Next", "➡️").asDisabled(), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), queue.size() == 1 ? Button.success("Shuffle", "\uD83D\uDD00").asDisabled() : Button.success("Shuffle", "\uD83D\uDD00")).queue();

            }
        } else if (event.getComponentId().equals("RepeatAll")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Your mistakes cannot be repeated.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Get a life bruv.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Stop doing that shit.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }
            final boolean newRepeating = !musicManager.scheduler.repeating;
            final boolean newRepeatAll = !musicManager.scheduler.repeatAll;

            musicManager.scheduler.repeatAll = newRepeatAll;
            if (musicManager.scheduler.repeatAll) {
                musicManager.scheduler.rephistory = true;
                musicManager.scheduler.historyQueue.clear();
                final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);

                for (int i = 0; i < trackList.size(); i++) {
                    musicManager.scheduler.historyQueue.offer(trackList.get(i).makeClone());
                }
            } else {
                musicManager.scheduler.rephistory = false;
            }

            final List<AudioTrack> trackList = new ArrayList<>(queue);

            int pagenum;
            if (queue.size() < 11)
                pagenum = 1;
            else if (queue.size() % 10 == 0)
                pagenum = (queue.size() / 10);
            else
                pagenum = (queue.size() / 10) + 1;


            end = pg * 10;
            start = end - 10;
            if (queue.size() > start && queue.size() < end)
                end = queue.size();

            final int trackCount = Math.min(queue.size(), 10);


            EmbedBuilder eb = musicManager.scheduler.QueueCmd;
            eb.clear();
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

            if (queue.size() > 10) {
                event.editMessageEmbeds(eb.build())
                        .setActionRow(Button.success("Prev", "⬅️"), Button.success("Next", "➡️"), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), Button.success("Shuffle", "\uD83D\uDD00")).queue();
            } else {
                event.editMessageEmbeds(eb.build())
                        .setActionRow(Button.success("Prev", "⬅️").asDisabled(), Button.success("Next", "➡️").asDisabled(), Button.success("Clear","⭕ Clear "), Button.success("RepeatAll", "\uD83D\uDD01"), queue.size() == 1 ? Button.success("Shuffle", "\uD83D\uDD00").asDisabled() : Button.success("Shuffle", "\uD83D\uDD00")).queue();

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