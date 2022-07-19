package lavaplayer;

import FairQueue.Queueable;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 *
 */

public class QueuedTrack implements Queueable {
    private final AudioTrack track;

    public QueuedTrack(AudioTrack track, User owner) {
        this(track, owner.getIdLong());
    }

    public QueuedTrack(AudioTrack track, long owner) {
        this.track = track;
        this.track.setUserData(owner);
    }

    @Override
    public long getIdentifier() {
        return track.getUserData(Long.class);
    }

    public AudioTrack getTrack() {
        return track;
    }

    @Override
    public String toString() {
        //return "`[" + formatTime(track.getDuration()) + "]` **" + track.getInfo().title + "** - <@" + track.getUserData(Long.class) + ">";
        return "Powered by FairQueue";
    }
}