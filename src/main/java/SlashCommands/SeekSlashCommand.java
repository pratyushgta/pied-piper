package SlashCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class contains methods for fast-forwarding / reversing a track
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */


public class SeekSlashCommand extends ListenerAdapter {
    public void onSlashCommand(@NotNull SlashCommandEvent event) {

        if (event.getName().equals("ff")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ Can't fast forward your life. Suffer.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("\uD83D\uDC40 ok. go back to your online classes now.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ why?").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            OptionMapping operator1 = event.getOption("input");

            if (operator1 == null) {
                int initialSeek;
                long maxLength = track.getDuration();
                try {
                    initialSeek = 20;
                    long amounttoSeek = initialSeek * 1000;
                    if (amounttoSeek > maxLength)
                        event.reply("⚠ Exceeds song length. Unable to fast forward. ").queue();
                    else {
                        long finalSeek = track.getPosition() + amounttoSeek;
                        track.setPosition(finalSeek);
                        if (amounttoSeek < 61000) {
                            event.reply(String.format("✅ **%d seconds skipped**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                        } else {
                            event.reply(String.format("✅ **%d min, %d seconds skipped**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                        }

                    }
                } catch (NumberFormatException ex) {
                    event.reply("⚠️ Integer. aka Number. Is all that you had to enter...\nError: "+ex).queue();
                }
                return;
            }

            int input = (int) operator1.getAsLong();

            int initialSeek;
            long maxLength = track.getDuration();

            try {
                initialSeek = input;
                long amounttoSeek = initialSeek * 1000L;
                if (amounttoSeek > maxLength)
                    event.reply("⚠ Specified amount of seconds exceeds the song length.").queue();
                else {
                    long finalSeek = track.getPosition() + amounttoSeek;
                    track.setPosition(finalSeek);
                    if (amounttoSeek < 61000) {
                        event.reply(String.format("✅ **%d seconds skipped**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                    } else {
                        event.reply(String.format("✅ **%d min, %d seconds skipped**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                    }

                }
            } catch (NumberFormatException ex) {
                event.reply("⚠️ Integer. aka Number. Is all that you had to enter...\nError: "+ex).queue();
            }
        } else if (event.getName().equals("rev")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ wut did u sey?").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ meow meow").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("\uD83D\uDC40 yeehaw.").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            OptionMapping operator1 = event.getOption("input");

            if (operator1 == null) {
                int initialSeek;
                long maxLength = track.getDuration();
                try {
                    initialSeek = 20;
                    long amounttoSeek = initialSeek * 1000;
                    if (amounttoSeek > maxLength)
                        track.setPosition(0);
                    else {
                        long finalSeek = track.getPosition() - amounttoSeek;
                        track.setPosition(finalSeek);
                        if (amounttoSeek < 61000) {
                            event.reply(String.format("✅ **%d seconds reversed**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                        } else {
                            event.reply(String.format("✅ **%d min, %d seconds reversed**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                        }

                    }
                } catch (NumberFormatException ex) {
                    event.reply("⚠️ Integer. aka Number. Is all that you had to enter...\nError: "+ex).queue();
                }
                return;
            }

            int input = (int) operator1.getAsLong();

            int initialSeek;

            try {
                initialSeek = input;
                long amounttoSeek = initialSeek * 1000L;

                long finalSeek = track.getPosition() - amounttoSeek;
                track.setPosition(finalSeek);
                if (amounttoSeek < 61000) {
                    event.reply(String.format("✅ **%d seconds reversed**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                } else {
                    event.reply(String.format("✅ **%d min, %d seconds reversed**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                }

            } catch (NumberFormatException ex) {
                event.reply("⚠️ Integer. aka Number. Is all that you had to enter...\nError: "+ex).queue();

            }
        } else if (event.getName().equals("seek")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠ the world is flat. wut say").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ heh. go back to your online class now.").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("\uD83D\uDC40 beep boop beep beep boop?").queue();
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            int coloncounter = 0;

            OptionMapping operator1 = event.getOption("input");

            if (operator1 == null) {
                return;
            }

            String input = operator1.getAsString();

            if (!track.isSeekable()) {
                event.reply("⚠ This track is not seekable").queue();
                return;
            } else {
                String time = input;
                long maxLength = track.getDuration();
                int colon = 0;
                int h, m, s;
                String hh, mm, ss;
                hh = mm = ss = "";
                long seek = 0;

                for (int i = 0; i < time.length(); i++) {
                    if (time.charAt(i) == ':') {
                        coloncounter++;
                    }
                }

                if (coloncounter > 2 || coloncounter == 0) {
                    event.reply("⚠ Hmm... Lost? The syntax to use this command is: `-seek <minutes:seconds>` or `-seek <hours:minutes:seconds>`").queue();
                    return;
                } else {
                    if (coloncounter == 1) {
                        for (int i = 0; i < time.length(); i++) {
                            if (time.charAt(i) == ':') {
                                colon = 1;
                            } else if (colon == 0) {
                                mm += time.charAt(i);
                            } else {
                                ss += time.charAt(i);
                            }
                        }
                        hh = "0";
                    } else {
                        for (int i = 0; i < time.length(); i++) {
                            if (time.charAt(i) == ':' && colon == 0) {
                                colon = 1;
                            } else if (time.charAt(i) == ':' && colon == 1) {
                                colon = 2;
                            } else if (colon == 0) {
                                hh += time.charAt(i);
                            } else if (colon == 1) {
                                mm += time.charAt(i);
                            } else {
                                ss += time.charAt(i);
                            }
                        }
                    }
                }

                h = Integer.parseInt(hh);
                m = Integer.parseInt(mm);
                s = Integer.parseInt(ss);

                seek = (h * 3600L) + (m * 60L) + s;

                if (seek * 1000 > maxLength) {
                    event.reply("⚠ Specified timestamp exceeds the song length.").queue();
                } else {
                    track.setPosition(seek * 1000);
                    event.reply("✅  **Seeked to** `" + m + ":" + s + "`" + " **timestamp**!").queue();
                }
            }
        }
    }
}

