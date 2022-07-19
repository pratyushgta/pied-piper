package lavaplayer;

import FairQueue.FairQueue;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 *
 */


public class TaskScheduler extends AudioEventAdapter {
    public final AudioPlayer player;
    public final BlockingQueue<AudioTrack> queue;
    public final BlockingQueue<AudioTrack> searchQueue;
   public final FairQueue<QueuedTrack> fqueue = new FairQueue<QueuedTrack>();
    public boolean repeating = false;
    public boolean repeatAll = false;
    public boolean streamer=false;
    public String streamerUser="";
    public int volume=0;
    public int search=0;

    public MessageEmbed Play;
    public EmbedBuilder PlayCmd = new EmbedBuilder();



    public TaskScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.searchQueue=new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void searchQueue(AudioTrack track){
        this.searchQueue.offer(track);
    }

    public void userqueue(String name){

    }


    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
    }



    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            if(this.repeating){
                this.player.startTrack(track.makeClone(),false);
                return;
            }
            else if(this.repeatAll){
               // this.queue.offer(track);
                this.queue.offer(track.makeClone());
                nextTrack();
                return;
            }
            nextTrack();
        }
    }
}