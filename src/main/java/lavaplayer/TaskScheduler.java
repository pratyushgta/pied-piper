package lavaplayer;


import FairQueue.FairQueue;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import FairQueue.Queueable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TaskScheduler extends AudioEventAdapter {
    public final AudioPlayer player;
    public final BlockingQueue<AudioTrack> queue;
    public final BlockingQueue<AudioTrack> searchQueue;
    public final BlockingQueue<AudioTrack> historyQueue;
    private final FairQueue<QueuedTrack> fqueue;
    public boolean repeating = false;
    public boolean repeatAll = false;
    public boolean streamer = false;
    public String streamerUser = "";
    public int volume = 0;
    public int search = 0;

    public int front=-1; //1st np is -1. 1st song in queue is 0
    public AudioTrack lastTrack = null;
    public boolean skiphistory = false;
    public boolean rephistory = false;
    public MessageEmbed Play;
    public EmbedBuilder PlayCmd = new EmbedBuilder();
    public EmbedBuilder NowPlayCmd = new EmbedBuilder();
    public EmbedBuilder QueueCmd = new EmbedBuilder();

    //public GuildMessageReceivedEvent ev;


    public TaskScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.searchQueue = new LinkedBlockingQueue<>();
        this.historyQueue = new LinkedBlockingQueue<>();
        this.fqueue = new FairQueue<QueuedTrack>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public FairQueue<QueuedTrack> getQueue()
    {
        return fqueue;
    }

    public void searchQueue(AudioTrack track) {
        this.searchQueue.offer(track);
    }

    public void historyQueue(AudioTrack track) {
        this.historyQueue.offer(track);
    }

    public void userqueue(String name) {
    }


    public void nextTrack() {
       /* front++;
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        this.player.startTrack(trackList.get(front),false);*/ //CIRCULAR QUEUE
        this.player.startTrack(this.queue.poll(),false);
    }


    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        this.lastTrack = track;


        if (!skiphistory) {
            historyQueue.offer(track.makeClone());
            if (rephistory) {
                final List<AudioTrack> htrackList = new ArrayList<>(historyQueue);
                historyQueue.remove(htrackList.get(historyQueue.size() - 1));
            }
        }

        /*if(!skiphistory)
        {
            historyQueue.offer(track.makeClone());
        }
        else if(rephistory)
        {
            final List<AudioTrack> htrackList = new ArrayList<>(historyQueue);
            historyQueue.remove(htrackList.get(historyQueue.size() - 1));
        }*/

        if (endReason.mayStartNext) {
            if (this.repeating) {
                this.player.startTrack(track.makeClone(), false);
                return;
            } else if (this.repeatAll) {
                // this.queue.offer(track);
                this.queue.offer(track.makeClone());
                nextTrack();
                return;
            }
            nextTrack();
        }
    }
}