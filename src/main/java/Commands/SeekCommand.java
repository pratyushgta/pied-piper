package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class contains methods for fast-forwarding / reversing a track
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class SeekCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() ) {
            if (input[0].equalsIgnoreCase("-ff") || input[0].equalsIgnoreCase("-fastforward") || input[0].equalsIgnoreCase("-forward") || input[0].equalsIgnoreCase("-fast")) {

                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
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

                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
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
                        long amounttoSeek = initialSeek * 1000;
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
            }
        }
    }
}