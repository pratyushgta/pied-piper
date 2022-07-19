package lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static utils.FormatUtil.formatTime;

/**
 * This class contains methods related to music playback
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 *
 */

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;


    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl, RequestMetadata rm, boolean plist, boolean search) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());


        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);
                //musicManager.scheduler.fqueue.add(track);
                // musicManager.scheduler.add

                channel.sendMessage("Adding to queue: `")
                        .append(track.getInfo().title)
                        .append("` by `")
                        .append(track.getInfo().author)
                        .append('`')
                        .queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

                if (!plist && !search) {
                    if (playlist.isSearchResult()) {
                        musicManager.scheduler.queue(tracks.get(0));
                        tracks.get(0).setUserData(rm);
                    }
                    if (musicManager.scheduler.queue.isEmpty()) {
                        MessageEmbed eb = musicManager.scheduler.Play;
                        channel.sendMessage(eb).queue(message -> {
                            musicManager.scheduler.PlayCmd.setColor(Color.green);
                            musicManager.scheduler.PlayCmd.setAuthor("\uD83C\uDFB6 Starting to play:");
                            musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                            message.editMessage(musicManager.scheduler.PlayCmd.build()).queueAfter(1, TimeUnit.SECONDS);
                            musicManager.scheduler.PlayCmd.clear();
                            musicManager.scheduler.Play = null;
                        });

                      /*  musicManager.scheduler.PlayCmd.clear();
                        channel.sendMessage("\uD83C\uDFB6 Starting to play: `")
                                .append(tracks.get(0).getInfo().title)
                                .append('`')
                                .queue();*/
                    } else {
                        String pos = String.valueOf(musicManager.scheduler.queue.size());

                        MessageEmbed eb = musicManager.scheduler.Play;
                        channel.sendMessage(eb).queue(message -> {
                            musicManager.scheduler.PlayCmd.setColor(Color.green);
                            musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                            musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Added to the queue at position `" + pos + "`");
                            message.editMessage(musicManager.scheduler.PlayCmd.build()).queueAfter(1, TimeUnit.SECONDS);
                        });

                      /*  channel.sendMessage("\uD83C\uDFB5 `")
                                .append(tracks.get(0).getInfo().title)
                                .append("` added to the queue at position ")
                                .append(pos)
                                .queue();*/
                    }
                } else if (plist && !search) {
                    MessageEmbed eb = musicManager.scheduler.Play;
                    channel.sendMessage(eb).queue(message -> {
                        musicManager.scheduler.PlayCmd.setColor(Color.green);
                        // musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                        musicManager.scheduler.PlayCmd.setTitle("✅  Playlist Loaded");
                        musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Adding to the queue `"+tracks.size()+"` tracks from the playlist `"+playlist.getName()+"`");
                        message.editMessage(musicManager.scheduler.PlayCmd.build()).queueAfter(1, TimeUnit.SECONDS);
                        musicManager.scheduler.PlayCmd.clear();
                        musicManager.scheduler.Play = null;
                    });

                    RequestMetadata xrm = rm;

                    for (int i = 0; i < tracks.size(); i++) {
                        musicManager.scheduler.queue(tracks.get(i));
                        tracks.get(i).setUserData(xrm);
                    }
                } else if (search && !plist) {
                    RequestMetadata s = rm;
                    musicManager.scheduler.searchQueue.clear();
                    musicManager.scheduler.search=1;
                    for (int i = 0; i < 5; i++) {
                        musicManager.scheduler.searchQueue(tracks.get(i));
                        tracks.get(i).setUserData(s);
                    }
                    final List<AudioTrack> trackList = new ArrayList<>( musicManager.scheduler.searchQueue);
                    final MessageAction messageAction = channel.sendMessage("\uD83D\uDCD9  Here are the `"+playlist.getName()+"`\n");


                    for (int i = 0; i < 5; i++) {
                        final AudioTrack track = trackList.get(i);
                        final AudioTrackInfo info = track.getInfo();
                        RequestMetadata rm = trackList.get(i).getUserData(RequestMetadata.class);

                        messageAction.append('#')
                                .append(String.valueOf(i + 1))
                                .append(" `")
                                .append(String.valueOf(info.title))
                                .append(" by ")
                                .append(info.author)
                                .append("` [`")
                                .append(formatTime(track.getDuration()))
                                .append("`]\n");
                    }
                    messageAction.queue();
                    channel.sendMessage("**Use** `-sp <track #1-5>` **to play a song from this list.**").queue();
                }
            }

            AudioPlaylist pl = new AudioPlaylist() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public List<AudioTrack> getTracks() {
                    return null;
                }

                @Override
                public AudioTrack getSelectedTrack() {
                    return null;
                }

                @Override
                public boolean isSearchResult() {
                    return false;
                }
            };



            @Override
            public void noMatches() {
                channel.sendMessage("\uD83D\uDEAB  Uh oh! Nothing found by " + trackUrl).queue();
            }


            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("⭕ Failed to load! Error: " + exception.getMessage()).queue();
            }
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

}