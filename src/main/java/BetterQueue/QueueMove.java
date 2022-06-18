package BetterQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class QueueMove extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-move")) {

            TextChannel channel = event.getTextChannel();
            VoiceChannel connectedChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();
            VoiceChannel SelfConnected = (VoiceChannel) event.getGuild().getSelfMember().getVoiceState().getChannel();


            if (connectedChannel == null) {
                channel.sendMessage("⚠ can't move you away from your failure").queue();
                return;
            } else if (SelfConnected == null) {
                channel.sendMessage("\uD83D\uDC40 ABCD...\nEFGADHD Ooo look a butterfly!").queue();
                return;
            } else if (connectedChannel != SelfConnected) {
                channel.sendMessage("⚠ beep boop boop beep boop?").queue();
                return;
            }


            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            AudioPlayerSendHandler handler = (AudioPlayerSendHandler) event.getGuild().getAudioManager().getSendingHandler();
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final List<AudioTrack> tempTrackList = new ArrayList<>(queue);

            if (input.length < 3) {
                channel.sendMessage("⚠️Can't displace air duh. Specify what to move and where to move it. Correct syntax is `-move <track #> <new position>`. For more info on a specific command, use `-help <command_name>`").queue();
                return;
            }

            int Pos1;
            int Pos2;

            Pos1 = Integer.parseInt(input[1]);
            Pos2 = Integer.parseInt(input[2]);

            if (Pos1 == Pos2) {
                channel.sendMessage("⚠️Ha! You thought the devs didn't code this out?").queue();
            } else if (Pos1 - 1 > queue.size()) {
                channel.sendMessage("⚠️...a...what song?").queue();
            } else if (Pos2 - 1 > queue.size()) {
                channel.sendMessage("⚠️eh go learn maths. New position does not exist").queue();
            } else { //https://stackoverflow.com/questions/36011356/moving-elements-in-arraylist-java/42043159
                for (int i = 0; i < tempTrackList.size(); i++) {
                    queue.remove(tempTrackList.get(i));
                }
                trackList.add(Pos2 - 1, trackList.remove(Pos1 - 1));
                for (int i = 0; i < trackList.size(); i++) {
                    queue.add(trackList.get(i));
                }
                channel.sendMessage("\uD83D\uDE9A Moved `" + tempTrackList.get(Pos1 - 1).getInfo().title + "` to **#" + Pos2 + "**").queue();
            }
        }
    }
}



//queue.remove(tempTrackList.get(Pos1-1));
//queue.add(trackList.set(Pos1-1, trackList.get(Pos2-1)));
// queue.remove(trackList.get(Pos1-1));
//trackList.add(Pos1, trackList.get(Pos2-1));
//  queue.remove(trackList.get(Pos2-1))
// trackList.set(Pos2-1, trackList.get(Pos2));

// }
            /*else if(Pos2>Pos1){
                for (int j = 0; j < trackList.size(); j++) {
                    queue.remove(tempTrackList.get(j));
                }
                int counter = 0;
                for (int i = 0; i < trackList.size(); i++) {
                    if (i == Pos2 - 1) {
                        queue.add(trackList.get(Pos1 - 1));
                    }
                    else if (i == Pos1 - 1) {
                        queue.add(trackList.get(i + 1));
                        counter = 1;
                    } else if (counter == 1) {
                        queue.add(trackList.get(i + 1));
                    } else {
                        queue.add(trackList.get(i));
                    }
               }
                channel.sendMessage("\uD83D\uDE9A Moved `"+trackList.get(Pos1-1).getInfo().title+"` to **#"+Pos2+"**").queue();
            }*/
           /* else if(Pos2<Pos1){
                for (int j = 0; j < trackList.size(); j++) {
                    queue.remove(tempTrackList.get(j));
                }
                int counter = 0;
                for (int i = 0; i < trackList.size(); i++) {
                    if (i == Pos2 - 1) {
                        queue.add(trackList.get(Pos1 - 1));
                        counter = 1;
                    }
                    else if (i == Pos1 - 1) {
                        queue.add(trackList.get(i + 1));

                    } else if (counter == 1) {
                        queue.add(tempTrackList.get(i - 1));
                    } else {
                        queue.add(trackList.get(i));
                    }
                }
                channel.sendMessage("\uD83D\uDE9A Moved `"+trackList.get(Pos1-1).getInfo().title+"` to **#"+Pos2+"**").queue();
            }

        }
    }
}*/