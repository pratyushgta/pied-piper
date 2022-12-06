package lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static utils.FormatUtil.formatTime;

/**
 * This class contains methods related to music playback
 *
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 */

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;


    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        //   this.audioPlayerManager = new YoutubeAudioSourceManager()

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


    public void loadAndPlay(TextChannel channel, String trackUrl, RequestMetadata rm, boolean plist, boolean search, boolean spotify, SlashCommandEvent event1, MessageReceivedEvent event2) {

        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());


        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                /*musicManager.scheduler.queue(track);
                if(musicManager.scheduler.queue.isEmpty()){
                    musicManager.scheduler.queue.add(track);
                }*/

                musicManager.scheduler.queue.add(track);
                track.setUserData(rm);

                if (musicManager.scheduler.queue.size() == 1 || musicManager.audioPlayer.getPlayingTrack() == null) {
                    musicManager.scheduler.nextTrack();
                }

                track.setUserData(rm);

                if (event1 != null) {
                    event1.deferReply().queue();
                    EmbedBuilder eb = new EmbedBuilder();
                    if (musicManager.scheduler.queue.isEmpty()) {

                        eb.setColor(new Color(21,86,80));
                        eb.setAuthor("\uD83C\uDFB6 Starting to play:");
                        eb.setTitle(track.getInfo().title, track.getInfo().uri);
                        event1.getHook().sendMessageEmbeds(eb.build()).queueAfter(2, TimeUnit.SECONDS);
                        eb.clear();
                        musicManager.scheduler.Play = null;

                    } else if (!spotify) {
                        String pos = String.valueOf(musicManager.scheduler.queue.size());

                        musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                        musicManager.scheduler.PlayCmd.setTitle(track.getInfo().title, track.getInfo().uri);
                        musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Added to the queue at position `" + pos + "`");
                        event1.getHook().sendMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(2, TimeUnit.SECONDS);

                    }
                } else {

                    if (musicManager.scheduler.queue.isEmpty()) {
                        MessageEmbed eb = musicManager.scheduler.Play;
                        channel.sendMessageEmbeds(eb).queue(message -> {
                            musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                            musicManager.scheduler.PlayCmd.setAuthor("\uD83C\uDFB6 Starting to play:");
                            musicManager.scheduler.PlayCmd.setTitle(track.getInfo().title, track.getInfo().uri);
                            message.editMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(2, TimeUnit.SECONDS);
                            musicManager.scheduler.PlayCmd.clear();
                            musicManager.scheduler.Play = null;
                        });
                    } else if (!spotify) {
                        String pos = String.valueOf(musicManager.scheduler.queue.size());

                        MessageEmbed eb = musicManager.scheduler.Play;
                        channel.sendMessageEmbeds(eb).queue(message -> {
                            musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                            musicManager.scheduler.PlayCmd.setTitle(track.getInfo().title, track.getInfo().uri);
                            musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Added to the queue at position `" + pos + "`");
                            message.editMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(2, TimeUnit.SECONDS);
                        });
                    }
                }


            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

                if (!plist && !search) {
                    if (playlist.isSearchResult()) {
                        //musicManager.scheduler.queue(tracks.get(0));
                        musicManager.scheduler.queue.add(tracks.get(0));
                        tracks.get(0).setUserData(rm);
                        if (musicManager.scheduler.queue.size() == 1 || musicManager.audioPlayer.getPlayingTrack() == null) { //musicManager.scheduler.queue.isEmpty() ||
                            //musicManager.scheduler.queue.add(tracks.get(0));
                            musicManager.scheduler.nextTrack();
                        }
                    }


                    if (event1 != null) {
                        EmbedBuilder eb = new EmbedBuilder();
                        if (musicManager.scheduler.queue.size() == 1 && !spotify) {
                            event1.deferReply().queue();
                            eb.setColor(new Color(21,86,80));
                            eb.setAuthor("\uD83C\uDFB6 Starting to play:");
                            eb.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                            event1.getHook().sendMessageEmbeds(eb.build()).queueAfter(2, TimeUnit.SECONDS);
                            eb.clear();
                            musicManager.scheduler.Play = null;

                        } else if (!spotify) {
                            event1.deferReply().queue();
                            String pos = String.valueOf(musicManager.scheduler.queue.size());
                            musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                            musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                            musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Added to the queue at position `" + pos + "`");
                            event1.getHook().sendMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(2, TimeUnit.SECONDS);

                        }
                    } else {
                        if (musicManager.scheduler.queue.size() == 1) {
                            MessageEmbed eb = musicManager.scheduler.Play;
                            channel.sendMessageEmbeds(eb).queue(message -> {
                                musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                                musicManager.scheduler.PlayCmd.setAuthor("\uD83C\uDFB6 Starting to play:");
                                musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                                message.editMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(1, TimeUnit.SECONDS);
                                musicManager.scheduler.PlayCmd.clear();
                                musicManager.scheduler.Play = null;
                            });

                        } else if (!spotify) {
                            String pos = String.valueOf(musicManager.scheduler.queue.size());

                            MessageEmbed eb = musicManager.scheduler.Play;
                            channel.sendMessageEmbeds(eb).queue(message -> {
                                musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                                musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                                musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Added to the queue at position `" + pos + "`");
                                message.editMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(1, TimeUnit.SECONDS);
                            });

                      /*  channel.sendMessage("\uD83C\uDFB5 `")
                                .append(tracks.get(0).getInfo().title)
                                .append("` added to the queue at position ")
                                .append(pos)
                                .queue();*/
                        }
                    }
                } else if (plist && !search) {
                    if (event1 != null) {
                        event1.deferReply().queue();
                        musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                        // musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                        musicManager.scheduler.PlayCmd.setTitle("✅  Playlist Loaded");
                        musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Adding to the queue `" + tracks.size() + "` tracks from the playlist `" + playlist.getName() + "`");
                        event1.getHook().sendMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(1, TimeUnit.SECONDS);
                        musicManager.scheduler.PlayCmd.clear();
                        musicManager.scheduler.Play = null;

                    } else if (event2 != null) {
                        MessageEmbed eb = musicManager.scheduler.Play;
                        channel.sendMessageEmbeds(eb).queue(message -> {
                            musicManager.scheduler.PlayCmd.setColor(new Color(21,86,80));
                            // musicManager.scheduler.PlayCmd.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
                            musicManager.scheduler.PlayCmd.setTitle("✅  Playlist Loaded");
                            musicManager.scheduler.PlayCmd.setDescription("\uD83C\uDFB5 Adding to the queue `" + tracks.size() + "` tracks from the playlist `" + playlist.getName() + "`");
                            message.editMessageEmbeds(musicManager.scheduler.PlayCmd.build()).queueAfter(1, TimeUnit.SECONDS);
                            musicManager.scheduler.PlayCmd.clear();
                            musicManager.scheduler.Play = null;
                        });
                    }


                    for (AudioTrack track : tracks) {
                        musicManager.scheduler.queue(track);
                        track.setUserData(rm);
                    }


                } else if (search && !plist) {
                    musicManager.scheduler.searchQueue.clear();
                    musicManager.scheduler.search = 1;
                    for (int i = 0; i < 5; i++) {
                        musicManager.scheduler.searchQueue(tracks.get(i));
                        tracks.get(i).setUserData(rm);
                    }
                    final List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.searchQueue);

                    if (event1 == null) {
                        final MessageAction messageAction = channel.sendMessage("\uD83D\uDCD9  Here are the `" + playlist.getName() + "`\n");
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
                        messageAction.append("**Use** `-sp <track #1-5>` **to play a song from this list.**");
                        messageAction.queue();
                        //channel.sendMessage("**Use** `-sp <track #1-5>` **to play a song from this list.**").queue();
                    } else if (event2 == null) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("\uD83D\uDCD9 Here are the `" + playlist.getName() + "`");
                        for (int i = 0; i < 5; i++) {
                            final AudioTrack track = trackList.get(i);
                            final AudioTrackInfo info = track.getInfo();
                            RequestMetadata rm = trackList.get(i).getUserData(RequestMetadata.class);

                            eb.addField((i + 1) + ". " + info.title, " by " + info.author + " `[" + formatTime(track.getDuration()) + "]`", false);
                        }
                        eb.setFooter("Click on the buttons below to play the track!");
                        event1.replyEmbeds(eb.build())
                                .addActionRow(Button.success("One", "1️⃣️"), Button.success("Two", "2️⃣️"), Button.success("Three", "3️⃣"), Button.success("Four", "4️⃣"), Button.success("Five", "5️⃣")).queue();
                    }
                }
            }

            final AudioPlaylist pl = new AudioPlaylist() {
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
                if (event1 == null) {
                    channel.sendMessage("\uD83D\uDEAB  Uh oh! Nothing found by " + trackUrl).queue();
                } else {
                    event1.reply("\uD83D\uDEAB  Uh oh! Nothing found by " + trackUrl).queue();
                }
            }


            @Override
            public void loadFailed(FriendlyException exception) {

                if (event1 == null) {
                    channel.sendMessage("⭕ Failed to load! Error: " + exception.getMessage()).queue();
                } else {
                    event1.reply("⭕ Failed to load! Error: " + exception.getMessage()).queue();
                }
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

}