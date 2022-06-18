package Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import lavaplayer.RequestMetadata;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class PlaySCCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] input = event.getMessage().getContentRaw().split("\\s+");
        String userid = event.getMember().getId();
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-scplay")) {

            EmbedBuilder eb=new EmbedBuilder();
            eb.setColor(Color.yellow);
            eb.setTitle("\uD83D\uDC23️ You just cracked the Mona Moussa Code...");
            //eb.setDescription("This command is currently made unavailable since it would have led to a compromised experience that does not meet the bot's standards.");
            // eb.setFooter("Stay tuned for future updates!","https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            eb.setDescription("You have discovered something that many failed to. You have come to the deep and dark graveyard of the commands which won't make it to the bot..like this one.\n1. `scplay`: To play songs off SoundCloud\n2. `-save`: A feature to save user queues, forever, in the bot\n3. `-tplay`: To play twitch streams\n4. `-import`: Import music files from PC");
            eb.setFooter("R.I.P");
            event.getChannel().sendMessageEmbeds(eb.build()).queue();
            eb.clear();
            return;
           /* final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();


            if(input.length==1){
                if( musicManager.scheduler.player.isPaused()){
                    final AudioTrackInfo info = track.getInfo();
                    channel.sendMessageFormat("▶ Resumed `%s`", info.title, info.author, info.uri).queue();

                    musicManager.scheduler.player.setPaused(false);
                }
                else {
                    channel.sendMessage("Play wut again?").queue();
                    return;
                }
            }
            else {
                String link = "";
                for (int i = 1; i < input.length; i++) {
                    link += input[i] + " ";
                }
                channel.sendMessage("\uD83D\uDD0D Searching...").queue();
                if (!isUrl(link)) {
                    link = "scsearch:" + link;
                }


                //PlayerManager.getInstance()
                  //      .loadAndPlay(channel, link);
            }

        }
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}*/
        }
    }
}
