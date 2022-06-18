package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.TimeUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SeekCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-ff") || input[0].equalsIgnoreCase("-fastforward") || input[0].equalsIgnoreCase("-forward") || input[0].equalsIgnoreCase("-fast")) {

                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ Can't fast forward your life. Suffer.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("\uD83D\uDC40 ok. go back to your online classes now.").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ why?").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();

                if (input.length == 1) {
                    //channel.sendMessage("\uD83D\uDC40 I can't read your mind. How much you wanna fast forward?").queue();
                    int initialSeek;
                    long maxLength = track.getDuration();
                    try {
                        initialSeek = 20;
                        long amounttoSeek = initialSeek * 1000;
                        if (amounttoSeek > maxLength)
                            channel.sendMessage("⚠ Exceeds song length. Unable to fast forward. ").queue();
                        else {
                            long finalSeek = track.getPosition() + amounttoSeek;
                            track.setPosition(finalSeek);
                            if (amounttoSeek < 61000) {
                                channel.sendMessage(String.format("✅ **%d seconds skipped**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            } else {
                                channel.sendMessage(String.format("✅ **%d min, %d seconds skipped**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            }

                        }
                    } catch (NumberFormatException ignored) {

                    }
                } else {
                    int initialSeek;
                    long maxLength = track.getDuration();
                    try {
                        initialSeek = Integer.parseInt(input[1]);
                        long amounttoSeek = initialSeek * 1000;
                        if (amounttoSeek > maxLength)
                            channel.sendMessage("⚠ Specified amount of seconds exceeds the song length.").queue();
                        else {
                            long finalSeek = track.getPosition() + amounttoSeek;
                            track.setPosition(finalSeek);
                            if (amounttoSeek < 61000) {
                                channel.sendMessage(String.format("✅ **%d seconds skipped**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            } else {
                                channel.sendMessage(String.format("✅ **%d min, %d seconds skipped**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            }

                        }
                    } catch (NumberFormatException ignored) {

                    }
                }
            } else if (input[0].equalsIgnoreCase("-rev") || input[0].equalsIgnoreCase("-reverse")) {

                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ wut did u sey?").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠ meow meow").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("\uD83D\uDC40 yeehaw.").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();

                if (input.length == 1) {
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
                                channel.sendMessage(String.format("✅ **%d seconds reversed**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            } else {
                                channel.sendMessage(String.format("✅ **%d min, %d seconds reversed**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            }

                        }
                    } catch (NumberFormatException ignored) {

                    }
                } else {
                    int initialSeek;
                    long maxLength = track.getDuration();
                    try {
                        initialSeek = Integer.parseInt(input[1]);
                        long amounttoSeek = initialSeek * 1000L;
                        if (amounttoSeek > maxLength || amounttoSeek > track.getPosition() || input[1].equalsIgnoreCase("restart") || input[1].equalsIgnoreCase("0")) {
                            track.setPosition(0);
                            channel.sendMessage("✅ **Reversed to the beginning!**").queue();
                        } else {
                            long finalSeek = track.getPosition() - amounttoSeek;
                            track.setPosition(finalSeek);
                            if (amounttoSeek < 61000) {
                                channel.sendMessage(String.format("✅ **%d seconds reversed**", TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            } else {
                                channel.sendMessage(String.format("✅ **%d min, %d seconds reversed**", TimeUnit.MILLISECONDS.toMinutes(amounttoSeek), TimeUnit.MILLISECONDS.toSeconds(amounttoSeek) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(amounttoSeek)))).queue();
                            }

                        }
                    } catch (NumberFormatException ignored) {

                    }
                }
            } else if (input[0].equalsIgnoreCase("-seek")) {

                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ the world is flat. wut say").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠ heh. go back to your online class now.").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("\uD83D\uDC40 beep boop beep beep boop?").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack track = audioPlayer.getPlayingTrack();

                int coloncounter = 0;

                if (input.length == 1) {
                    channel.sendMessage("⚠ Hmm... You seem to have missed the right turn. The syntax to use this command is: `-seek <minutes:seconds>` or `-seek <hours:minutes:seconds>`").queue();
                    return;
                } else if (!track.isSeekable()) {
                    channel.sendMessage("⚠ This track is not seekable").queue();
                    return;
                } else {
                    String time = input[1];
                    long maxLength = track.getDuration();
                    int colon = 0;
                    int h, m, s;
                    String hh, mm, ss;
                    hh = mm = ss = "";
                    String[] t;
                    long seek = 0;

                    for (int i = 0; i < time.length(); i++) {
                        if (time.charAt(i) == ':') {
                            coloncounter++;
                        }
                    }

                    if (coloncounter > 2 || coloncounter==0) {
                        channel.sendMessage("⚠ Hmm... Lost? The syntax to use this command is: `-seek <minutes:seconds>` or `-seek <hours:minutes:seconds>`").queue();
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
                        channel.sendMessage("⚠ Specified timestamp exceeds the song length.").queue();
                        return;
                    } else {
                        track.setPosition(seek * 1000);
                        channel.sendMessage("✅  **Seeked to** `" + m + ":" + s + "`" + " **timestamp**!").queue();
                    }
                }
            }
        }
    }
}


