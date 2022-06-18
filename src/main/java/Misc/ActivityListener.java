package Misc;

import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateActivitiesEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ActivityListener extends ListenerAdapter {
 /*   @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-test")) {
                TextChannel channel = event.getTextChannel();
                channel.sendMessage("test pass ").queue();

                for (int i = 0; i < event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().size(); i++) {
                   /* if(event.getMember().getActivities().get(i).
                    {
                        channel.sendMessage("TRUE").queue();
                    }
                    else
                    {
                        channel.sendMessage("F").queue();
                    }*/

    // channel.sendMessage("ac "+event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities()).queue();
               /*     channel.sendMessage("ac " + event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities()).queue();
                    for (int j = 0; j < event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities().size(); j++) {
                        if (event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities().get(j).getType().name().equals("STREAMING")) {
                            channel.sendMessage("STREAMING TRUE").queue();
                        }
                  /*  if(event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities().get(i).getType().name().equals("STREAMING")){
                        channel.sendMessage("TRUE").queue();
                    }*/
    //   }
    //       }
    //  }
    //   }
    // }


    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        for (int i = 0; i < Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getSelfMember().getVoiceState()).getChannel()).getMembers().size(); i++) {
            for (int j = 0; j < event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities().size(); j++) {
                if (event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities().get(j).getType().name().equals("STREAMING")) {
                    final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getChannelJoined().getGuild());
                    musicManager.scheduler.streamer = true;
                    final String streamUser = event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getEffectiveName();
                    musicManager.scheduler.streamerUser = streamUser;
                    musicManager.scheduler.volume = musicManager.scheduler.player.getVolume();
                    musicManager.scheduler.player.setVolume(0);
                    musicManager.scheduler.player.setPaused(true);
                }
            }
        }
    }

  /*  @Override
    public void onUserUpdateActivities(UserUpdateActivitiesEvent event) {
        for (int i = 0; i < Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getSelfMember().getVoiceState()).getChannel()).getMembers().size(); i++) {
            for (int j = 0; j < event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities().size(); j++) {
                if (event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getActivities().get(j).getType().name().equals("STREAMING")) {
                    final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getMember().getGuild());
                    musicManager.scheduler.streamer = true;
                    final String streamUser = event.getGuild().getSelfMember().getVoiceState().getChannel().getMembers().get(i).getEffectiveName();
                    musicManager.scheduler.streamerUser = streamUser;
                    musicManager.scheduler.volume = musicManager.scheduler.player.getVolume();
                    musicManager.scheduler.player.setVolume(0);
                    musicManager.scheduler.player.setPaused(true);
                }
            }
        }
    }
}*/
}
