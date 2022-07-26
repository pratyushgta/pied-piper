package lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 *
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 *
 */

public class GuildMusicManager {
    public final AudioPlayer audioPlayer;
    public final TaskScheduler scheduler;
    private final AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager){
        this.audioPlayer=manager.createPlayer();
        this.scheduler=new TaskScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler=new AudioPlayerSendHandler(this.audioPlayer);
    }

    public AudioPlayerSendHandler getSendHandler(){
        return sendHandler;
    }
}