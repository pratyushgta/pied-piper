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
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import utils.FormatUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import static java.awt.Color.gray;
import static java.awt.Color.yellow;

public class NowPlayingSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("nowplaying") || event.getName().equals("player")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getSelfMember().getVoiceState()).getChannel();

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            if (track == null) {
                EmbedBuilder eb1 = new EmbedBuilder();
                eb1.setTitle("⭕ No track playing!");
                eb1.setColor(yellow);
                event.replyEmbeds(eb1.build()).queue();
                return;
            }
            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;

            eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
            eb.setColor(gray);
            eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
            eb.setTitle(track.getInfo().title, track.getInfo().uri);

            RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

            double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
            eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                    + " " + FormatUtil.progressBar(progress)
                    + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                    + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                    + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");
            // +"\n\n"+(counter ? "⏭️ **Next-Up:** "+trackList.get(0).getInfo().title:"")+"\n"); //➰ ➿ 📺

            eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);


            if (connectedChannel == null || connectedChannel != SelfConnected) {
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("Previous", "⏮️").asDisabled(), musicManager.audioPlayer.isPaused() ? Button.success("Resume", "▶️").asDisabled() : Button.success("Pause", "⏸️").asDisabled(),
                                Button.success("Skip", "⏭️").asDisabled(), musicManager.scheduler.repeatAll ? Button.success("repeatall", "\uD83D\uDD01").asDisabled() : Button.success("Loop", "\uD83D\uDD02").asDisabled(), Button.success("Stop", "⏹").asDisabled()).queue();

            } else {
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("Previous", "⏮️"), musicManager.audioPlayer.isPaused() ? Button.success("Resume", "▶️") : Button.success("Pause", "⏸️"),
                                Button.success("Skip", "⏭️"), musicManager.scheduler.repeatAll ? Button.success("repeatall", "\uD83D\uDD01") : Button.success("Loop", "\uD83D\uDD02"), Button.success("Stop", "⏹")).queue();

                //musicManager.scheduler.historyQueue.size() > 0 ? Button.success("Previous", "⏮️") : Button.success("Previous", "⏮️").asDisabled()

            }//, musicManager.scheduler.queue.size()>0?Button.success("Queue","\uD83D\uDDCF"):Button.success("Queue","\uD83D\uDDCF").asDisabled()) ,Button.success("Stop","⏹") ,Button.success("repeatall","\uD83D\uDD01")


         /*   if(musicManager.audioPlayer.isPaused() && musicManager.scheduler.historyQueue.size() > 0) {
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("Previous","⏮️"),Button.success("Resume","▶️"),Button.success("Skip","⏭️"),Button.success("Loop","\uD83D\uDD02"),Button.success("repeatall","\uD83D\uDD01")).queue();
            }
            else if(musicManager.audioPlayer.isPaused() && musicManager.scheduler.historyQueue.size() <= 0){
                event.replyEmbeds(eb.build())
                 .addActionRow(Button.success("Previous","⏮️").asDisabled(),Button.success("Resume","▶️"),Button.success("Skip","⏭️"),Button.success("Loop","\uD83D\uDD02"),Button.success("repeatall","\uD83D\uDD01")).queue();

            }
            else if(!musicManager.audioPlayer.isPaused() && musicManager.scheduler.historyQueue.size() <= 0){
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("Previous","⏮️").asDisabled(),Button.success("Pause","⏸️"),Button.success("Skip","⏭️"),Button.success("Loop","\uD83D\uDD02"),Button.success("repeatall","\uD83D\uDD01")).queue();

            }
            else {
                event.replyEmbeds(eb.build())
                        .addActionRow(Button.success("Previous","⏮️"),Button.success("Pause","⏸️"),Button.success("Skip","⏭️"),Button.success("Loop","\uD83D\uDD02"),Button.success("repeatall","\uD83D\uDD01")).queue();
            }*/
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        TextChannel channel = event.getTextChannel();
        VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (event.getComponentId().equals("Resume")) {
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("What? Why are you here? Just to suffer?");
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
                eb1.setDescription("Resume what? Your life?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (musicManager.scheduler.streamer) {
                eb1.clear();
                eb1.setColor(Color.red);
                eb1.setTitle("⚠️Cannot unpause while Streamer Mode is on!");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }


           // event.editButton(Button.success("Pause", "⏸️")).queue();
            // event.replyFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();
            musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
            musicManager.scheduler.volume = 0;
            musicManager.scheduler.player.setPaused(false);


            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;

            final AudioTrack track = audioPlayer.getPlayingTrack();


            eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
            eb.setColor(gray);
            eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
            eb.setTitle(track.getInfo().title, track.getInfo().uri);

            RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

            double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
            eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                    + " " + FormatUtil.progressBar(progress)
                    + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                    + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                    + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");
            // +"\n\n"+(counter ? "⏭️ **Next-Up:** "+trackList.get(0).getInfo().title:"")+"\n"); //➰ ➿ 📺

            eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);


            event.editMessageEmbeds(eb.build())
                    .setActionRow(musicManager.scheduler.historyQueue.size() > 0 ? Button.success("Previous", "⏮️") : Button.success("Previous", "⏮️").asDisabled(), musicManager.audioPlayer.isPaused() ? Button.success("Resume", "▶️") : Button.success("Pause", "⏸️"),
                            Button.success("Skip", "⏭️"), musicManager.scheduler.repeatAll ? Button.success("repeatall", "\uD83D\uDD01") : Button.success("Loop", "\uD83D\uDD02"), Button.success("Stop", "⏹")).queue();


        } else if (event.getComponentId().equals("Pause")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("You can't pause your life. Nor your gf.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("meh. not in the mood to pause rn.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Pause exactly what again?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }

           // event.editButton(Button.success("Resume", "▶️")).queue();
            //event.replyFormat("⏸ Paused `%s`", info.title, info.author, info.uri).queue();
            musicManager.scheduler.volume = musicManager.scheduler.player.getVolume();
            musicManager.scheduler.player.setVolume(0);

            musicManager.scheduler.player.setPaused(true);


            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;

            final AudioTrack track = audioPlayer.getPlayingTrack();


            eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
            eb.setColor(gray);
            eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
            eb.setTitle(track.getInfo().title, track.getInfo().uri);

            RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

            double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
            eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                    + " " + FormatUtil.progressBar(progress)
                    + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                    + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                    + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");
            // +"\n\n"+(counter ? "⏭️ **Next-Up:** "+trackList.get(0).getInfo().title:"")+"\n"); //➰ ➿ 📺

            eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);


            event.editMessageEmbeds(eb.build())
                    .setActionRow(musicManager.scheduler.historyQueue.size() > 0 ? Button.success("Previous", "⏮️") : Button.success("Previous", "⏮️").asDisabled(), musicManager.audioPlayer.isPaused() ? Button.success("Resume", "▶️") : Button.success("Pause", "⏸️"),
                            Button.success("Skip", "⏭️"), musicManager.scheduler.repeatAll ? Button.success("repeatall", "\uD83D\uDD01") : Button.success("Loop", "\uD83D\uDD02"), Button.success("Stop", "⏹")).queue();



        } else if (event.getComponentId().equals("Stop")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Shut up and lemme do my job.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Stop playing what? your gf?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Go dance.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }

            eb.clear();

          /* if (audioPlayer.getPlayingTrack() == null) {
               event.reply("\uD83D\uDE43 There's no music playin. What do you plan to stop huh?").queue();
               return;
           }*/
            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
            musicManager.scheduler.repeatAll = false;
            musicManager.scheduler.repeating = false;
            musicManager.scheduler.search = 0;
            musicManager.scheduler.searchQueue.clear();
            musicManager.scheduler.QueueCmd.clear();
            musicManager.scheduler.NowPlayCmd.clear();
            musicManager.scheduler.PlayCmd.clear();
            musicManager.audioPlayer.setPaused(false);

            musicManager.scheduler.skiphistory = false;
            musicManager.scheduler.rephistory = false;


            eb.setTitle("⭕ No track playing!");
            eb.setColor(yellow);
            if (musicManager.scheduler.historyQueue.size() > 0) {
                eb.setDescription("✅ The player has stopped and the queue has been cleared!\nPress ⏮ to replay the previous queue.");
                event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️").asEnabled(), Button.success("Pause", "⏸️").asDisabled(), Button.success("Skip", "⏭️").asDisabled(), Button.success("Loop", "\uD83D\uDD02").asDisabled(), Button.danger("Leave", "\uD83D\uDC4B").asEnabled()).queue();
            }
            else {
                eb.setDescription("✅ The player has stopped and the queue has been cleared!");
                event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️").asDisabled(), Button.success("Pause", "⏸️").asDisabled(), Button.success("Skip", "⏭️").asDisabled(), Button.success("Loop", "\uD83D\uDD02").asDisabled(), Button.danger("Leave", "\uD83D\uDC4B").asEnabled()).queue();
            }
        } else if (event.getComponentId().equals("Leave")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Dude...I am not connected to a voice channel. You can't disconnect me buahahaha");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Bruh you thought you could disconnect me while people are listening? Too bad. I'm programmed to curse people like you.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("What did you think? you'd disconnect me while others are listening? BOOOO");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }

            event.getGuild().getAudioManager().closeAudioConnection();

            // Notify the user.
            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
            musicManager.audioPlayer.setVolume(100);
            musicManager.scheduler.repeatAll = false;
            musicManager.scheduler.repeating = false;
            musicManager.scheduler.streamer = false;
            musicManager.scheduler.streamerUser = "";
            musicManager.scheduler.search = 0;
            musicManager.scheduler.searchQueue.clear();
            musicManager.scheduler.lastTrack = null;
            musicManager.scheduler.historyQueue.clear();
            musicManager.scheduler.NowPlayCmd.clear();
            musicManager.scheduler.PlayCmd.clear();

            musicManager.scheduler.skiphistory = false;
            musicManager.scheduler.rephistory = false;

            event.reply("\uD83D\uDC4B Goodbye, Senorita!").queue();

        } else if (event.getComponentId().equals("Previous")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("And you thought this hasn't been coded?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("Jokes on you");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("What? Why are you here? Just to suffer?");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (musicManager.scheduler.streamer) {
                eb1.clear();
                eb1.setColor(Color.red);
                eb1.setTitle("⚠️Cannot unpause while Streamer Mode is on!");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;

            } else if (musicManager.scheduler.repeatAll) {

                musicManager.scheduler.repeatAll = false;
                final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);
                final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);
                final AudioTrack currenttrack = audioPlayer.getPlayingTrack();
                final AudioTrack prevtrack = trackList.get(trackList.size() - 1);

                musicManager.scheduler.queue.clear();
                temptrackList.remove(temptrackList.size() - 1);

                musicManager.scheduler.queue.offer(prevtrack.makeClone());

                musicManager.scheduler.queue.offer(currenttrack.makeClone());

                for (int i = 0; i < temptrackList.size(); i++) {
                    musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                }
                musicManager.scheduler.repeatAll = true;
                musicManager.scheduler.nextTrack();

                final AudioTrack track = audioPlayer.getPlayingTrack();
                eb.clear();
                eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
                eb.setColor(gray);
                eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
                eb.setTitle(track.getInfo().title, track.getInfo().uri);

                RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

                double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
                eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                        + " " + FormatUtil.progressBar(progress)
                        + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                        + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                        + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");

                eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
                event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️").asEnabled(), Button.success("Pause", "⏸️").asEnabled(), Button.success("Skip", "⏭️").asEnabled(), Button.success("Loop", "\uD83D\uDD02").asEnabled(), Button.success("Stop", "⏹").asEnabled()).queue();


            } else {
                if (musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.getPlayingTrack() != null || musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.isPaused()) {
                    final List<AudioTrack> htrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                    final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);

                    final AudioTrack prevtrack = htrackList.get(htrackList.size() - 1);
                    final AudioTrack currenttrack = audioPlayer.getPlayingTrack();

                    musicManager.scheduler.queue.clear();


                    if (musicManager.scheduler.repeating)
                        musicManager.scheduler.repeating = false;

                    musicManager.scheduler.queue.offer(prevtrack.makeClone());

                    musicManager.scheduler.queue.offer(currenttrack.makeClone());

                    musicManager.scheduler.skiphistory = true;

                    //   event.getChannel().sendMessage("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();
                    for (int i = 0; i < temptrackList.size(); i++) {
                        musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                    }
                    musicManager.scheduler.nextTrack();
                    musicManager.scheduler.historyQueue.remove(htrackList.get(htrackList.size() - 1));

                    musicManager.scheduler.skiphistory = false;

                    final AudioTrack track = audioPlayer.getPlayingTrack();
                    eb.clear();
                    eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
                    eb.setColor(gray);
                    eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
                    eb.setTitle(track.getInfo().title, track.getInfo().uri);

                    RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

                    double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
                    eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                            + " " + FormatUtil.progressBar(progress)
                            + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                            + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                            + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");

                    eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
                    event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️").asEnabled(), Button.success("Pause", "⏸️").asEnabled(), Button.success("Skip", "⏭️").asEnabled(), Button.success("Loop", "\uD83D\uDD02").asEnabled(), Button.success("Stop", "⏹").asEnabled()).queue();


                } else if (musicManager.scheduler.historyQueue.size() > 0 && audioPlayer.getPlayingTrack() == null) {
                    final List<AudioTrack> htrackList = new ArrayList<>(musicManager.scheduler.historyQueue);
                    final List<AudioTrack> temptrackList = new ArrayList<>(musicManager.scheduler.queue);

                    final AudioTrack prevtrack = htrackList.get(htrackList.size() - 1);
                    musicManager.scheduler.queue.clear();
                    musicManager.scheduler.queue.offer(prevtrack.makeClone());
                    //    event.getChannel().sendMessage("\uD83D\uDD01  **Replaying**: " + prevtrack.getInfo().title).queue();
                    for (int i = 0; i < temptrackList.size(); i++) {
                        musicManager.scheduler.queue.offer(temptrackList.get(i).makeClone());
                    }
                    musicManager.scheduler.nextTrack();
                    musicManager.scheduler.historyQueue.remove(htrackList.get(htrackList.size() - 1));

                    final AudioTrack track = audioPlayer.getPlayingTrack();
                    eb.clear();
                    eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
                    eb.setColor(gray);
                    eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
                    eb.setTitle(track.getInfo().title, track.getInfo().uri);

                    RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

                    double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
                    eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                            + " " + FormatUtil.progressBar(progress)
                            + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                            + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                            + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");

                    eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
                    event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️").asEnabled(), Button.success("Pause", "⏸️").asEnabled(), Button.success("Skip", "⏭️").asEnabled(), Button.success("Loop", "\uD83D\uDD02").asEnabled(), Button.success("Stop", "⏹").asEnabled()).queue();


                } else {
                    final AudioTrack track = audioPlayer.getPlayingTrack();
                    track.setPosition(0);
                    event.editButton(Button.success("Previous", "⏮").asDisabled()).queue();
                }
            }

        } else if (event.getComponentId().equals("Skip")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
            EmbedBuilder eb1 = new EmbedBuilder();

            if (connectedChannel == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("There's no music playin. Your ears are ringing.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (SelfConnected == null) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("lemme sleep.");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                eb1.clear();
                eb1.setTitle("\uD83D\uDE42 Bruh!");
                eb1.setDescription("bruh I'm not playing music in your voice channel");
                event.replyEmbeds(eb1.build()).setEphemeral(true).queue();
                return;
            }

            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            if (musicManager.scheduler.repeating) {
                musicManager.scheduler.repeating = false;
                musicManager.scheduler.skiphistory = false;
            }

            if (audioPlayer.getPlayingTrack() == null && !queue.isEmpty()) {
                event.reply("\uD83D\uDEAB You can't skip time..nor life..nor cupcakes.").queue();
            } else if (musicManager.scheduler.repeatAll) {
                final AudioTrack trackx = audioPlayer.getPlayingTrack();
                musicManager.scheduler.queue.offer(trackx.makeClone());
                musicManager.scheduler.nextTrack();

             /*  final AudioTrackInfo info = track.getInfo();
               event.replyFormat(event.getMember().getEffectiveName() + " Skipped `%s`", info.title, info.author, info.uri).queue();

               final AudioTrack trackx = audioPlayer.getPlayingTrack();
               final AudioTrackInfo infox = trackx.getInfo();
               event.replyFormat("⏭️ Next-Up: " + infox.title).queue();*/

                final AudioTrack track = audioPlayer.getPlayingTrack();
                eb.clear();
                eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
                eb.setColor(gray);
                eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
                eb.setTitle(track.getInfo().title, track.getInfo().uri);

                RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

                double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
                eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                        + " " + FormatUtil.progressBar(progress)
                        + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                        + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                        + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");
                // +"\n\n"+(counter ? "⏭️ **Next-Up:** "+trackList.get(0).getInfo().title:"")+"\n"); //➰ ➿ 📺

                eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
                //  event.editButton(Button.success("Previous","⏮️").asEnabled()).queue();
                event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️"), Button.success("Pause", "⏸️"), Button.success("Skip", "⏭️"), Button.success("Loop", "\uD83D\uDD02"), Button.success("Stop", "⏹")).queue();


            } else if (queue.isEmpty()) {
                musicManager.scheduler.nextTrack();
                //  event.replyFormat(event.getMember().getEffectiveName() + " Skipped `%s`", info.title, info.author, info.uri).queue();
                // event.reply("\uD83D\uDDC5 The Queue is empty! You've grooved to all the tracks in the queue...").queue();

                eb.clear();
                eb.setTitle("⭕ No track playing!");
                eb.setDescription("The Queue is empty! You've grooved to all the tracks in the queue...");
                eb.setColor(yellow);

                event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️"), Button.success("Pause", "⏸️").asDisabled(), Button.success("Skip", "⏭️").asDisabled(), Button.success("Loop", "\uD83D\uDD02").asDisabled(), Button.danger("Leave", "\uD83D\uDC4B")).queue();

            } else {
                musicManager.scheduler.nextTrack();
                final AudioTrack track = audioPlayer.getPlayingTrack();
                eb.clear();
                eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
                eb.setColor(gray);
                eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
                eb.setTitle(track.getInfo().title, track.getInfo().uri);

                RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

                double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
                eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                        + " " + FormatUtil.progressBar(progress)
                        + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                        + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                        + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");

                eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
                event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️"), Button.success("Pause", "⏸️"), Button.success("Skip", "⏭️"), Button.success("Loop", "\uD83D\uDD02"), Button.success("Stop", "⏹")).queue();
            }

        } else if (event.getComponentId().equals("Loop")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
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

            musicManager.scheduler.repeating = newRepeating;
            if (!musicManager.scheduler.repeatAll) {
                musicManager.scheduler.skiphistory = true;
                musicManager.scheduler.historyQueue.add(musicManager.audioPlayer.getPlayingTrack());
            } else
                musicManager.scheduler.skiphistory = false;

            eb.clear();
            final AudioTrack track = audioPlayer.getPlayingTrack();
            eb.clear();
            eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
            eb.setColor(gray);
            eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
            eb.setTitle(track.getInfo().title, track.getInfo().uri);

            RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

            double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
            eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                    + " " + FormatUtil.progressBar(progress)
                    + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                    + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                    + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");

            eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
            event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️").asEnabled(), Button.success("Pause", "⏸️").asEnabled(), Button.success("Skip", "⏭️").asEnabled(), Button.success("repeatall", "\uD83D\uDD01").asEnabled(), Button.success("Stop", "⏹").asEnabled()).queue();

        } else if (event.getComponentId().equals("repeatall")) {

            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            EmbedBuilder eb = musicManager.scheduler.NowPlayCmd;
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

            final AudioTrack track = audioPlayer.getPlayingTrack();
            eb.clear();
            eb.setAuthor("\uD83C\uDFB5  Now Playing in " + SelfConnected.getName() + "...");
            eb.setColor(gray);
            eb.setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", track.getInfo().identifier));
            eb.setTitle(track.getInfo().title, track.getInfo().uri);

            RequestMetadata rm = audioPlayer.getPlayingTrack().getUserData(RequestMetadata.class);

            double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
            eb.setDescription((audioPlayer.isPaused() ? "⏸" : "▶️")
                    + " " + FormatUtil.progressBar(progress)
                    + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                    + FormatUtil.volumeIcon(audioPlayer.getVolume()) + "\n\n\t\t"
                    + (musicManager.scheduler.repeatAll ? "\uD83D\uDD01" : "") + "\t\t\t" + (musicManager.scheduler.repeating ? "\uD83D\uDD02" : "") + "\t\t\t" + (musicManager.scheduler.streamer ? "\uD83D\uDCE1" : "") + "\n");

            eb.setFooter("Added by: " + rm.user.username, rm.user.avatar);
            event.editMessageEmbeds(eb.build()).setActionRow(Button.success("Previous", "⏮️").asEnabled(), Button.success("Pause", "⏸️").asEnabled(), Button.success("Skip", "⏭️").asEnabled(), Button.success("Loop", "\uD83D\uDD02").asEnabled(), Button.success("Stop", "⏹").asEnabled()).queue();

        }
    }
}
