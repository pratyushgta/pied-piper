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

public class VideoSlashCommand extends ListenerAdapter {
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getName().equals("video")) {
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

            if (track == null) {
                event.reply("⚠️ Umm...cannot show your wedding video. It never happened.").queue();
                return;
            } else {
                event.reply(track.getInfo().uri).queue();
            }
        }
    }
}

