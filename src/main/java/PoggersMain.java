import Commands.*;
import BetterQueue.*;
import Misc.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the main class which contains the main method
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs to setup the bot
 */
public class PoggersMain {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault("<BOT_TOKEN>>").build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        //jda.getPresence().setPresence(OnlineStatus.IDLE,Activity.watching("to -help you")); //Bot's custom status


      /**Uncomment the following lines (26-33) if you want your bot to have a status which keeps changing at specified time*/
          final String[] messages={"Alpha Testing","Testing Beta 10"};
        final int[] currentIndex = {0};

        new Timer().schedule(new TimerTask(){
            public void run(){
                jda.getPresence().setActivity(Activity.playing(messages[currentIndex[0]]));
                currentIndex[0] =(currentIndex[0] +1)%messages.length;
            }},0,5_000);




        jda.addEventListener(new Handler());
        jda.addEventListener(new PlayCommand());
        jda.addEventListener(new StopCommand());
        jda.addEventListener(new SkipCommand());
        jda.addEventListener(new NowPlayingCommand());
        jda.addEventListener(new QueueCommand());
        jda.addEventListener(new VolumeCommand());
        jda.addEventListener(new LyricsCommand());
        jda.addEventListener(new RepeatCommand());
        jda.addEventListener(new SeekCommand());
        jda.addEventListener(new QueueRemove());
        jda.addEventListener(new QueueMove());
        jda.addEventListener(new QueueShuffle());
        jda.addEventListener(new SearchCommand());
        jda.addEventListener(new SearchPlayCommand());
        jda.addEventListener(new StreamerMode());
        jda.addEventListener(new PlaylistCommand());
        jda.addEventListener(new QueuePlay());
        jda.addEventListener(new About());
        jda.addEventListener(new HelpCommands());
        jda.addEventListener(new DetailedHelp());
        jda.addEventListener(new SpecialEvents());
    }
}

