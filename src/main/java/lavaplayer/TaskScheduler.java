package lavaplayer;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.jmusixmatch.entity.track.TrackData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 */

public class TaskScheduler extends AudioEventAdapter {
    public final AudioPlayer player;
    public final BlockingQueue<AudioTrack> queue;
    public final BlockingQueue<AudioTrack> historyQueue; //for use in future
    public final BlockingQueue<AudioTrack> searchQueue;
    public final BlockingQueue<TrackData> LyricsQueue;

    public boolean repeating = false;
    public boolean repeatAll = false;
    public boolean streamer = false;
    public String streamerUser = "";
    public int volume = 0;
    public int search = 0;
    public int pointer = -1;

    //public int front = -1; //1st np is -1. 1st song in queue is 0
    public AudioTrack lastTrack = null;
    public MessageEmbed Play;
    public EmbedBuilder PlayCmd = new EmbedBuilder();
    public EmbedBuilder NowPlayCmd = new EmbedBuilder();
    public EmbedBuilder QueueCmd = new EmbedBuilder();


    //public GuildMessageReceivedEvent ev;


    public TaskScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.searchQueue = new LinkedBlockingQueue<>();
        this.LyricsQueue = new LinkedBlockingQueue<>();
        this.historyQueue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void LyricsQueue(TrackData trackx) {

        this.LyricsQueue.offer(trackx);

    }

    public void searchQueue(AudioTrack track) {
        this.searchQueue.offer(track);
    }

    public void userqueue(String name) {
    }


    public void nextTrack() {
        pointer++;
        System.out.println("++ value: " + pointer);
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        this.player.startTrack(trackList.get(pointer).makeClone(), false); //CIRCULAR QUEUE
    }
    //this.player.startTrack(this.queue.poll(), false);


    public void queueMessages() {

    }


    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        this.lastTrack = track;
        if (endReason.mayStartNext) {
            if (this.repeating) {
                this.player.startTrack(track.makeClone(), false);
                //nextTrack();
            } else if (this.repeatAll) {
                // this.queue.offer(track);
                //this.queue.offer(track.makeClone());
                if(pointer == this.queue.size()-1){
                    pointer = -1;
                }
                nextTrack();
            }
            else if(pointer < this.queue.size()-1 && !queue.isEmpty()) {
                nextTrack();
            }
        }
    }
}