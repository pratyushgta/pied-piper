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

/**
 * This class contains methods to change player volume
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class VolumeSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("volume")) {
            OptionMapping option = event.getOption("value");
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null) {
                event.reply("⚠  Go back to your online classes, you little ball of shit.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠  Master volume can only be changed while in a vc!").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠  Stop disturbing my masters.").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            if(option==null)
            {
                int vol = musicManager.scheduler.player.getVolume();
                event.reply("\uD83D\uDD0A Current volume is: " + vol + "%").queue();
                return;
            }
            int volume = (int) option.getAsLong();

            musicManager.scheduler.player.setVolume(volume);
            event.reply("\uD83D\uDD0A Volume changed to: " + volume + "%").queue();
        }
    }
}
