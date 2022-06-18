package Commands;

import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class StreamerMode extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-stream") || input[0].equalsIgnoreCase("-streamer")) {

                TextChannel channel = event.getTextChannel();
                VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
                VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
                if (connectedChannel == null) {
                    channel.sendMessage("⚠ You're not allowed to turn on Streamer Mode while not in a VC.").queue();
                    return;
                } else if (SelfConnected == null) {
                    channel.sendMessage("⚠ Streamer Mode cannot be turned on while the bot is not in a VC").queue();
                    return;
                } else if (connectedChannel != SelfConnected) {
                    channel.sendMessage("⚠ Streamer Mode cannot be turned on while the bot is not in the same VC").queue();
                    return;
                }

                if(input.length==1 && !musicManager.scheduler.streamer){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("\uD83D\uDCE1 What exactly do you wanna do with Streamer Mode?");
                    eb.setDescription("Streamer mode can be enabled using `streamer on` and disabled using `streamer off`");
                    eb.setFooter("Streamer Mode [BETA v2.0.0]");
                    eb.setColor(Color.yellow);
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                    eb.clear();
                }
                else if(input.length==1 && musicManager.scheduler.streamer){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("\uD83D\uDCE1 Streamer Mode is ON!");
                    eb.setDescription("To avoid copyright strikes, Streamer mode turns on automatically when someone in the VC starts Twitch Streaming or someone already streaming joins the VC.");
                    eb.addField("","Streamer Mode has been turned on for **"+musicManager.scheduler.streamerUser+"**.\n\nStreamer Mode can only be disabled by "+musicManager.scheduler.streamerUser,false);
                    eb.setFooter("Streamer Mode [BETA v2.0.0]");
                    eb.setColor(Color.yellow);
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                    eb.clear();
                }
                else if (input[1].equalsIgnoreCase("on")) {
                    if (musicManager.scheduler.streamer) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("\uD83D\uDCE1 Streamer Mode already ON!");
                        eb.setDescription("Streamer Mode is already on for **"+musicManager.scheduler.streamerUser+"**.");
                        eb.setFooter("Streamer Mode [BETA v2.0.0]");
                        eb.setColor(Color.yellow);
                        event.getChannel().sendMessageEmbeds(eb.build()).queue();
                        eb.clear();
                    } else {
                        musicManager.scheduler.streamer = true;
                        final String streamUser = event.getMember().getEffectiveName();
                        musicManager.scheduler.streamerUser = streamUser;
                        musicManager.scheduler.volume=musicManager.scheduler.player.getVolume();
                        musicManager.scheduler.player.setVolume(0);
                        musicManager.scheduler.player.setPaused(true);
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("\uD83D\uDCE1 Streamer Mode Activated!");
                        eb.setDescription("Streamer Mode has been activated by **" + event.getMember().getEffectiveName() + "** in " + connectedChannel.getName() + ".\nMusic playback will not be available until the Streamer Mode is on.\n");
                        // eb.addField("", "Streamer Mode can only be disabled by "+event.getMember().getEffectiveName()+".", false);
                        eb.setFooter("Streamer Mode [BETA v2.0.0]");
                        eb.setColor(Color.green);
                        event.getChannel().sendMessageEmbeds(eb.build()).queue();
                        eb.clear();
                    }
                }
                else if(input.length > 1 && input[1].equalsIgnoreCase("off")){
                    if(!event.getMember().getEffectiveName().equals(musicManager.scheduler.streamerUser) && musicManager.scheduler.streamer){
                        channel.sendMessage("⚠️  Streamer Mode can only be disabled by: "+musicManager.scheduler.streamerUser).queue();
                    } else if(!musicManager.scheduler.streamer){
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("\uD83D\uDCE1 Streamer Mode already OFF!");
                        eb.setFooter("Streamer Mode [BETA v2.0.0]");
                        eb.setColor(Color.yellow);
                        event.getChannel().sendMessageEmbeds(eb.build()).queue();
                        eb.clear();
                    }
                    else {
                        musicManager.scheduler.streamer = false;
                        musicManager.scheduler.streamerUser = "";
                        musicManager.scheduler.player.setPaused(false);
                        musicManager.scheduler.player.setVolume(musicManager.scheduler.volume);
                        musicManager.scheduler.volume=0;
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("✅ Streamer Mode Deactivated!");
                        eb.setDescription("Continue grooving to your fav tracks ;)");
                        eb.setFooter("Streamer Mode [BETA v2.0.0]");
                        eb.setColor(Color.green);
                        event.getChannel().sendMessageEmbeds(eb.build()).queue();
                        eb.clear();
                    }
                }
                else {
                    channel.sendMessage("Uh Oh! Cannot turn on Streamer Mode at the moment! Please try again later.").queue();
                }
            }
        }
    }
}
