package Commands;

import com.jagrosh.jlyrics.Lyrics;
import com.jagrosh.jlyrics.LyricsClient;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavaplayer.GuildMusicManager;
import lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.StringTokenizer;


public class LyricsCommand extends ListenerAdapter {
    private Lyrics lyrics;
    //private final LyricsClient client = new LyricsClient();
    LyricsClient client = new LyricsClient("MusixMatch");
    //LyricsClient client = new LyricsClient();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {


        String[] input = event.getMessage().getContentRaw().split("\\s+");
        if (!Objects.requireNonNull(event.getMember()).getUser().isBot() && input[0].equalsIgnoreCase("-lyrics")) {


            // LyricsClient client = new LyricsClient("A-Z Lyrics");

            TextChannel channel = event.getTextChannel();

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack track = audioPlayer.getPlayingTrack();

            event.getChannel().sendTyping().queue();
            String title = "";
            String info = "";
            int counter = 0;
            if (input.length == 1) {
                int len = 5;
                info = track.getInfo().title;
                StringTokenizer st = new StringTokenizer(info);
                String currenttoken = "";
                if (st.countTokens() == 1)
                    len = 1;
                else if (st.countTokens() == 2)
                    len = 2;
                else if (st.countTokens() == 3)
                    len = 3;
                else if (st.countTokens() == 4)
                    len = 4;
                for (int i = 0; i < len; i++) {
                    currenttoken = st.nextToken();
                    if (!currenttoken.equalsIgnoreCase("-")) {
                        title += currenttoken + " ";
                        currenttoken = "";
                    }
                }
            } else {
                for (int i = 1; i < input.length; i++) {
                    title += input[i] + " ";
                    counter = 1;
                }
            }
            // channel.sendMessage(title).queue();
            int finalCounter = counter;
            client.getLyrics(title).thenAccept(lyrics ->
            {
                if (lyrics == null) {
                    if (finalCounter == 0) {
                        event.getChannel().sendMessage("\uD83D\uDE36\u200D\uD83C\uDF2B️ Uh oh! Lyrics could not be fetched. Try entering the song name manually: `lyrics <song name>`").queue();
                        // event.getChannel().sendMessage("Lyrics for  could not be found!" + (input[1].isEmpty() ? " Try entering the song name manually (`lyrics [song name]`)" : "")).queue();
                        return;
                    } else {
                        event.getChannel().sendMessage("\uD83D\uDE35\u200D\uD83D\uDCAB ️ Uh oh! Lyrics not found in MusixMatch").queue();
                        // event.getChannel().sendMessage("Lyrics for  could not be found!" + (input[1].isEmpty() ? " Try entering the song name manually (`lyrics [song name]`)" : "")).queue();
                        return;
                    }
                }

                EmbedBuilder eb = new EmbedBuilder()
                        .setAuthor(lyrics.getAuthor())
                        //.setColor(event.getSelfMember().getColor())
                        .setTitle(lyrics.getTitle(), lyrics.getURL())
                        .setFooter("Lyrics are brought to you by MusixMatch", "https://pbs.twimg.com/profile_images/1194637308244430848/JUj4SljE_400x400.jpg");
                //.setFooter("Lyrics are brought to you by A-Z Lyrics.","https://download.cnet.com/a/img/resize/ccba7cc1e86ee344022b6807533bac63cededbef/catalog/2019/12/10/3eeaa564-0594-4c15-b238-535242e80227/imgingest-1293706355108209810.jpg?auto=webp&fit=crop&height=675&width=1200");

                if (lyrics.getContent().length() > 15000) {
                    event.getChannel().sendMessage("⚠️Lyrics found but are likely incorrect: " + lyrics.getURL()).queue();
                } else if (lyrics.getContent().length() > 2000) {
                    String content = lyrics.getContent().trim();
                    while (content.length() > 2000) {
                        int index = content.lastIndexOf("\n\n", 2000);
                        if (index == -1)
                            index = content.lastIndexOf("\n", 2000);
                        if (index == -1)
                            index = content.lastIndexOf(" ", 2000);
                        if (index == -1)
                            index = 2000;
                        event.getChannel().sendMessageEmbeds(eb.setDescription(content.substring(0, index).trim()).build()).queue();
                        content = content.substring(index).trim();
                        eb.setAuthor(null).setTitle(null, null);
                    }
                    event.getChannel().sendMessageEmbeds(eb.setDescription(content).build()).queue();
                } else
                    event.getChannel().sendMessageEmbeds(eb.setDescription(lyrics.getContent()).build()).queue();
            });
        }
    }
}