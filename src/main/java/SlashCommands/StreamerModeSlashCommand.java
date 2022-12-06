package SlashCommands;


import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

/**
 * This class contains methods for Streamer Mode
 * For Discord SLASH COMMANDS
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 * StreamerMode & Streamer Mode Lite (v2.0.0 Beta) by Pratyush Kumar
 */

public class StreamerModeSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("streamer") && Objects.equals(event.getSubcommandName(), "info")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            if (connectedChannel == null) {
                event.reply("⚠ You're not allowed to access Streamer Mode while not in a VC.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Streamer Mode cannot be accessed while the bot is not in a VC").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ Streamer Mode cannot be accessed while the bot is not in the same VC").queue();
                return;
            }

            if (!musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("\uD83D\uDCE1 What exactly do you wanna do with Streamer Mode?");
                eb.setDescription("Streamer mode can be enabled using `streamer on` and disabled using `streamer off`");
                eb.setFooter("Streamer Mode [BETA v1.0.0]");
                eb.setColor(new Color(242,202,9));
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            } else {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("\uD83D\uDCE1 Streamer Mode is ON!");
                eb.setDescription("To avoid copyright strikes, Streamer mode turns on automatically when someone in the VC starts Twitch Streaming or someone already streaming joins the VC.");
                eb.addField("", "Streamer Mode has been turned on for **" + musicManager.scheduler.streamerUser + "**.\n\nStreamer Mode can only be disabled by " + musicManager.scheduler.streamerUser, false);
                eb.setFooter("Streamer Mode [BETA v1.0.0]");
                eb.setColor(new Color(242,202,9));
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            }
        } else if (event.getName().equals("streamer") && Objects.equals(event.getSubcommandName(), "on")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            if (connectedChannel == null) {
                event.reply("⚠ You're not allowed to turn on Streamer Mode while not in a VC.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Streamer Mode cannot be turned on while the bot is not in a VC").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ Streamer Mode cannot be turned on while the bot is not in the same VC").queue();
                return;
            }


            if (musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("\uD83D\uDCE1 Streamer Mode already enabled!");
                eb.setDescription("Streamer Mode is already on for **" + musicManager.scheduler.streamerUser + "**.");
                eb.setFooter("Streamer Mode [BETA v1.0.0]");
                eb.setColor(new Color(242,202,9));
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            } else {
                musicManager.scheduler.streamer = true;
                musicManager.scheduler.streamerUser = event.getMember().getEffectiveName();
                musicManager.scheduler.volume = musicManager.scheduler.player.getVolume();
                musicManager.scheduler.player.setVolume(0);
                musicManager.scheduler.player.setPaused(true);
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("\uD83D\uDCE1 Streamer Mode Activated!");
                eb.setDescription("Streamer Mode has been activated by **" + event.getMember().getEffectiveName() + "** in " + connectedChannel.getName() + ".\nMusic playback will not be available until the Streamer Mode is on.\n");
                // eb.addField("", "Streamer Mode can only be disabled by "+event.getMember().getEffectiveName()+".", false);
                eb.setFooter("Streamer Mode [BETA v1.0.0]");
                eb.setColor(new Color(21,86,80));
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            }
        } else if (event.getName().equals("streamer") && Objects.equals(event.getSubcommandName(), "off")) {
            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = event.getGuild().getSelfMember().getVoiceState().getChannel();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            if (connectedChannel == null) {
                event.reply("⚠ You're not allowed to access Streamer Mode while not in a VC.").queue();
                return;
            } else if (SelfConnected == null) {
                event.reply("⚠ Streamer Mode cannot be accessed while the bot is not in a VC").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                event.reply("⚠ Streamer Mode cannot be accessed while the bot is not in the same VC").queue();
                return;
            }

            if (!event.getMember().getEffectiveName().equals(musicManager.scheduler.streamerUser) && musicManager.scheduler.streamer) {
                event.reply("⚠️  Streamer Mode can only be disabled by: " + musicManager.scheduler.streamerUser).queue();
            } else if (!musicManager.scheduler.streamer) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("\uD83D\uDCE1 Streamer Mode already disabled!");
                eb.setFooter("Streamer Mode [BETA v1.0.0]");
                eb.setColor(new Color(242,202,9));
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            } else {
                musicManager.scheduler.streamer = false;
                musicManager.scheduler.streamerUser = "";
                musicManager.scheduler.player.setPaused(false);
                musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
                musicManager.scheduler.volume = 0;
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("✅ Streamer Mode Deactivated!");
                eb.setDescription("Continue grooving to your fav tracks ;)");
                eb.setFooter("Streamer Mode [BETA v1.0.0]");
                eb.setColor(new Color(21,86,80));
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            }
        }
    }
}