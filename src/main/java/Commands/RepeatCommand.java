package Commands;

import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Objects;

/**
 * This class contains methods for repeating a single track or the queue
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class RepeatCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-repeat") || input[0].equalsIgnoreCase("-loop")) {

                TextChannel channel = event.getChannel();
                VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ Your mistakes cannot be repeated.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠ Get a life bruv.").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ Stop doing that shit.").queue();
                    return;
                }
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

                final boolean newRepeating = !musicManager.scheduler.repeating;
                final boolean newRepeatAll = !musicManager.scheduler.repeatAll;

                if (input.length > 1 && input[1].equalsIgnoreCase("on")) {
                    musicManager.scheduler.repeating = true;
                    channel.sendMessageFormat("\uD83D\uDD02 This song has been set to **repeating**").queue();
                } else if (input.length > 1 && input[1].equalsIgnoreCase("off")) {
                    musicManager.scheduler.repeating = false;
                    channel.sendMessageFormat("\uD83D\uDEAB This song has been set to **not repeating**").queue();
                } else if (input.length == 3 && input[1].equalsIgnoreCase("all") && input[2].equalsIgnoreCase("on")) {
                    musicManager.scheduler.repeatAll = true;
                    channel.sendMessageFormat("\uD83D\uDD01 The queue has been set to **repeating**").queue();
                } else if (input.length == 3 && input[1].equalsIgnoreCase("all") && input[2].equalsIgnoreCase("off")) {
                    musicManager.scheduler.repeatAll = false;
                    channel.sendMessageFormat("\uD83D\uDEAB The queue has been set to **not repeating**").queue();
                } else if (input.length > 1 && input[1].equalsIgnoreCase("all")) {
                    if (musicManager.scheduler.queue.isEmpty()) {
                        musicManager.scheduler.repeating = newRepeating;
                        channel.sendMessageFormat("\uD83D\uDD02 This song has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
                    } else {
                        musicManager.scheduler.repeatAll = newRepeatAll;
                        channel.sendMessageFormat("\uD83D\uDD01 The queue has been set to **%s**", newRepeatAll ? "repeating" : "not repeating").queue();
                    }
                } else {
                    musicManager.scheduler.repeating = newRepeating;
                    channel.sendMessageFormat("\uD83D\uDD02 This song has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
                }
            }
        }

    }
}