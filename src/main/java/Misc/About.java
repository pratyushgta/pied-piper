package Misc;


import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.MessageEmbed;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;


import java.awt.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class contains methods for displaying bot ping, about page, settings & bug reports
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */

public class About extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-") && input.length < 2 || input[0].equalsIgnoreCase("pied") && input.length < 2 || input[0].equalsIgnoreCase("piper") && input.length < 2) {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Hello There! did you call me? Type `-help` or use slash commands to view all the bot commands!").queue();
            } else if (input[0].equalsIgnoreCase("-about")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("Pied Piper is here to groove!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed1.setDescription("An incredibly easy to use music bot for Discord that makes it easy to listen to any of your favorite songs with a group of friends or by yourself! \n\nType `-help` or `/help` to view the commands!\n\n");
                embed1.addField("Fuelled by the Lavaplayer™", "\uD83C\uDFA7 High-definition music playback and support for Slash Commands!\n\uD83C\uDFA7 Easy queuing with improved BetterQueue technology\n\uD83C\uDFA7 Simple, easy-to-use interface & support for Slash Commands\n\uD83C\uDFA7 Seamlessly play Spotify tracks & Playlists", false); //Easy navigation using Slash Commands
                embed1.addField("Find it on: ","<:spotify:991956139258413096>  [Spotify](https://open.spotify.com/user/31gawmgyebtvsameogo6u2lac5hy?si=ff1897bd2f604486) \n<:GitHub:1049218567167557682>  [GitHub](https://github.com/pratyushgta/pied-piper.git) ", false);
                //  embed1.addField("", "Pied Piper relies on your network and WebSocket connections. Music playback may be affected if your internet speeds are slow.\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.", false);

                embed1.setColor(new Color(0,151,136));
                // embed1.setFooter("Pied Piper by LangoOr Bellic", "https://cdn.discordapp.com/embed/avatars/1.png");
                embed1.setFooter("For the love of music. Pied Piper by LangoOr Bellic | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
                //embed1.setImage("https://cdn.vox-cdn.com/thumbor/PE_KcVvPxk1rUfyEQ7xqMtJC4FU=/0x0:1500x1000/1200x800/filters:focal(630x380:870x620)/cdn.vox-cdn.com/uploads/chorus_image/image/68726487/Nothing_RGB_Signalweiss.0.jpg");
                //embed1.setThumbnail("https://i.pinimg.com/originals/3c/5e/e5/3c5ee5788cbf613bcad691ab5d10e4e1.gif");
                // event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                event.getChannel().sendTyping().queue();
                embed1.clear();
            } else if (input[0].equalsIgnoreCase("-ping")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Retrieving bot's latency...");
                embed1.setColor(new Color(242,202,9));
                MessageEmbed eb = embed1.build();
                event.getChannel().sendMessageEmbeds(eb).queue(message -> {
                    embed1.setTitle("\uD83D\uDCF6 Ping Pong!");
                    embed1.setDescription("Check bot's ping");
                    long ping = event.getMessage().getTimeCreated().getMinute();
                    embed1.addField("Message Latency", "" + ping + "ms", true);
                    embed1.addField("API Latency", event.getJDA().getGatewayPing() + "ms", true);

                    if (event.getJDA().getGatewayPing() > 99) {
                        embed1.setColor(new Color(220,77,77));
                        embed1.addField("", "⚠ Looks like the ping is too high. Expect playback issues.", false);
                    } else if (event.getJDA().getGatewayPing() > 79) {
                        embed1.setColor(new Color(242,202,9));
                        embed1.addField("", "⚠ Looks like the ping is a bit high. Expect music lag/ distorted playback.", false);
                    } else if (event.getJDA().getGatewayPing() <= 0 || ping < 0) {
                        embed1.setColor(new Color(35, 33, 28));
                        embed1.addField("", "⚠ WebSocket ping looks incorrect. Please try again later.", false);
                    } else {
                        embed1.setColor(new Color(21,86,80));
                    }
                    embed1.setFooter("Pied Piper | Let's start groovin' ;)", "https://i.imgur.com/BMH6UcT.jpeg");
                    // message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                    message.editMessageEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
                embed1.clear();

            } else if (input[0].equalsIgnoreCase("-new") || input[0].equalsIgnoreCase("-wutnew") || input[0].equalsIgnoreCase("-whatsnew")) {
                EmbedBuilder embed2 = new EmbedBuilder();
                //embed1.setAuthor("Ping-Pong!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed2.setTitle("\uD83D\uDCE2  What's new in town?");
                //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
                embed2.setDescription("Check out the official update changelogs here!\n");
                embed2.addField("", "\uD83C\uDFBA **Next is Now. Introducing v1.5!** \uD83C\uDFB7", false);
                embed2.addField("Introducing Project Tanpura","-Next phase of Pied Piper begins now! Experience the sophisticated side of the bot with more utilitarian functions and further bug fixes to the core!",false);
                embed2.addField("Ringa Ringa Roses", "-Introducing improved BetterQueue with circular queue implementation for peak queue accuracy!", false);
                embed2.addField("Seeing is Believing", "-New `/video` command which generates a YouTube video embed in the text channel!", false);
                embed2.addField("Smash-em-up.", "-Tons of bug fixes an various QoL improvements, making the experience a whole lot better", false);
                embed2.setColor(new Color(239,149,157));
                //embed2.addBlankField(false);
                embed2.addField("", "**Previous Update Changelog**", false);
                embed2.addField("FEATURE DROP v1.3 & v1.35", "-Slash Commands are here! Experience the all new Audio Player, Queue Menus, and interactive buttons, only with Slash Commands!\n-New and improved Streamer Mode is here! Streamer Mode will now turn on automatically when someone in the VC starts Twitch Streaming or someone already streaming joins the VC.\n-Lyrics have been fixed. Now get accurate and clean lyrics directly from MusixMatch!\n-Bug Fixes\n", false);
                embed2.addField("FEATURE UPDATE v1.4", "-Seamlessly tune into Spotify tracks & playlists!\n-Directly access playlists and tracks on your Spotify profile without logging in!\n-Peak accuracy with high-definition music playback and 95% playlist conversion accuracy\n", false);
                embed2.setFooter("man this seriously takes so long to write", "https://i.imgur.com/BMH6UcT.jpeg");
                //this literally takes more brain cells to write
                // event.getChannel().sendMessage(embed2.build()).queue();
                event.getChannel().sendMessageEmbeds(embed2.build()).queue();
                event.getChannel().sendTyping().queue();
                embed2.clear();
            } else if (input[0].equalsIgnoreCase("-news") || input[0].equalsIgnoreCase("-updates")) {
                EmbedBuilder embed2 = new EmbedBuilder();
                //embed1.setAuthor("Ping-Pong!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
                embed2.setTitle("\uD83C\uDF73 What's cookin'");
                embed2.setDescription("Check out what's up with Pied Piper! This newsletter shares ideas and plans for what's coming up next ;)");

                embed2.addField("Project Tanpura", "Pied Piper's Tanpura build marks the next step in the musical journey. Phase 1 was all about providing a feature-rich music bot, whereas this phase is all about re-writing the magic that happens behind the scenes including polishing and optimizing the bot to boost its performance. This build will bring you a more sophisticated side of the bot with more utilitarian functions and further bug fixes to the core.", false);
                embed2.addField("*Current Status:*", "Build 1 rolling out now!", true);
                embed2.addField("*ETR:*", "Final release by Summer 2023", true);

                embed2.addField("Text Commands", "Well, Slash commands have not been made mandatory for every bot, however, text commands have been noticed to be causing rate limiting issues. For further monitoring, Text Commands have been disabled entirely. PS: They have not been removed and will be back!", false);
                embed2.addField("*Current Status:*", "Disabled until next update", true);
                embed2.addField("*ETR:*", "Spring, 2023", true);

                embed2.addField("Streamer Mode", "Streamer Mode Lite v2 has been facing performance issues and bugs since its roll out. Until it has been fixed, Streamer Mode Lite rolled back to v1.0.", false);
                embed2.addField("*Current Status:*", "v2 under Bux fixing", true);
                embed2.addField("*ETR:*", "~~Fall, 2022~~ Spring, 2023", true);

                embed2.addField("Repeating Queues", "Changing the way we imagine BetterQueue. Previously, once a queue was over, there was no way to replay it. This changed with v1.2 where users were able to repeat the previously played queue or track which was done by using multiple queues. This system has been replaced with a proper circular queue implementation for better accuracy.", false);
                embed2.addField("*Current Status:*", "Rolling out with v1.5", true);
                embed2.addField("*ETR:*", "~~Autumn, 2022~~ Released", true);

                embed2.addField("Support for multiple Audio Sources", "Imagine a place....where you can listen to live radio stations, twitch streams, or perhaps pull up a local audio file from your computer, directly on to the VC. LavaPlayer supports tons of file format. This feature can be utilised and implemented in the bot. This has been delayed and shall be rolled out with a future Tanpura build.", false);
                embed2.addField("*Current Status:*", "Under Conceptualisation Phase", true);
                embed2.addField("*ETR:*", "~~Autumn, 2022~~ Spring, 2023", true);

                embed2.setColor(new Color(242, 202, 9));
                embed2.setFooter("ETR: Estimated Time of Release | Stay tuned for more updates! | Last updated on 05/12/2023 by LangoOr Bellic", "https://i.imgur.com/BMH6UcT.jpeg");
                event.getChannel().sendMessageEmbeds(embed2.build()).queue();
                event.getChannel().sendTyping().queue();
                embed2.clear();
            } else if (input[0].equalsIgnoreCase("-version")) {

                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Checking for Updates...");
                embed1.setColor(new Color(242,202,9));
                MessageEmbed eb = embed1.build();

                event.getChannel().sendMessageEmbeds(eb).queue(message -> {
                    embed1.setColor(new Color(21,86,80));
                    embed1.setTitle("\uD83E\uDD73 wohoo! The bot is Up-to-Date! ");
                    // embed1.addField("Current Bot Version: ","v0.10.5 | Verion 0 Beta 10.5",true);
                    embed1.addField("Current Bot build: ", "v1.5 | Version 2 | Tanpura Build", true);
                    embed1.addField("Updated on: ", "05-Dec-2022", true);
                    embed1.addField("", "**SYSTEM PROPERTIES:**", false);
                    embed1.addField("", "**JDA Version:** " + JDAInfo.VERSION, false);
                    //embed1.addField("", "**JDA-Utilities Version:** " + JDAUtilitiesInfo.VERSION, false);
                    embed1.addField("", "**Lavaplayer Version:** " + PlayerLibrary.VERSION, false);
                    embed1.addField("", "**MusixMatch Web API Version:** 1.1.4", false);
                    embed1.addField("", "**Spotify Web API Version:** 7.1.0", false);
                    long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
                    long used = total - (Runtime.getRuntime().freeMemory() / 1024 / 1024);
                    embed1.addField("", "**\nRUNTIME INFORMATION:**", false);
                    embed1.addField("Total Memory:", "" + total, true);
                    embed1.addField("Used Memory:", "" + used, true);
                    // embed1.addField("","Testing Phase- Beta 10",false);
                    embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://i.imgur.com/BMH6UcT.jpeg");
                    message.editMessageEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
            } else if (input[0].equalsIgnoreCase("-status")) {

                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Updating status...");
                embed1.setColor(new Color(242,202,9));
                MessageEmbed eb = embed1.build();

                event.getChannel().sendMessageEmbeds(eb).queue(message -> {
                    embed1.setColor(new Color(21,86,80));
                    embed1.setTitle("\uD83D\uDFE2 Bot is online!");
                    //embed1.setDescription("Upcoming Planned Maintenance Update: 01/02/22"); //\nNext planned update: Version 0 Beta 10.5");
                    embed1.setDescription("All bot functions are working as intended.");
                    //embed1.addField("Rate Limit Pool", "" + event.getJDA().getRateLimitPool(), false);
                    embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://i.imgur.com/BMH6UcT.jpeg");
                    message.editMessageEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
            }
        }
    }


    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {

        if (event.getName().equals("about")) {

            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setAuthor("Pied Piper is here to groove!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
            embed1.setDescription("An incredibly easy to use music bot for Discord that makes it easy to listen to any of your favorite songs with a group of friends or by yourself! \n\nType `-help` or `/help` to view the commands!\n\n");
            embed1.addField("Fuelled by the Lavaplayer™", "\uD83C\uDFA7 High-definition music playback and support for Slash Commands!\n\uD83C\uDFA7 Easy queuing with improved BetterQueue technology\n\uD83C\uDFA7 Simple, easy-to-use interface & support for Slash Commands\n\uD83C\uDFA7 Seamlessly play Spotify tracks & Playlists", false); //Easy navigation using Slash Commands
            embed1.addField("Find it on: ","<:spotify:991956139258413096>  [Spotify](https://open.spotify.com/user/31gawmgyebtvsameogo6u2lac5hy?si=ff1897bd2f604486) \n<:GitHub:1049218567167557682>  [GitHub](https://github.com/pratyushgta/pied-piper.git) ", false);

            embed1.setColor(new Color(0,151,136));
            embed1.setFooter("For the love of music. Pied Piper by LangoOr Bellic | Let's start groovin' ;) ", "https://i.imgur.com/BMH6UcT.jpeg");
            event.replyEmbeds(embed1.build()).addActionRow(Button.link("https://discordapp.com/users/YOUR_DISCORD_ID/", "Butt Support")).queue();
            embed1.clear();

        } else if (event.getName().equals("ping")) {
            EmbedBuilder embed1 = new EmbedBuilder();
            event.deferReply().queue();

            embed1.setTitle("\uD83D\uDCF6 Ping Pong!");
            embed1.setDescription("Check bot's ping");
            long ping = event.getInteraction().getTimeCreated().getMinute();
            //long ping = event.getTimeCreated().until(event.getTimeCreated(), ChronoUnit.MILLIS);
            //long ping = event.getMessage().getTimeCreated().until(event.getMessage().getTimeCreated(), ChronoUnit.MILLIS);
            embed1.addField("Message Latency", "" + ping + "ms", true);
            embed1.addField("API Latency", event.getJDA().getGatewayPing() + "ms", true);
            if (event.getJDA().getGatewayPing() > 99) {
                embed1.setColor(new Color(220,77,77));
                embed1.addField("", "⚠ Looks like the ping is too high. Expect playback issues.", false);
            } else if (event.getJDA().getGatewayPing() > 79) {
                embed1.setColor(new Color(242,202,9));
                embed1.addField("", "⚠ Looks like the ping is a bit high. Expect music lag/ distorted playback.", false);
            } else if (event.getJDA().getGatewayPing() <= 0 || ping < 0) {
                embed1.setColor(new Color(35, 33, 28));
                embed1.addField("", "⚠ WebSocket ping looks incorrect. Please try again later.", false);
            } else {
                embed1.setColor(new Color(21,86,80));
            }
            embed1.setFooter("Pied Piper | Let's start groovin' ;)", "https://i.imgur.com/BMH6UcT.jpeg");
            // message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            event.getHook().sendMessageEmbeds(embed1.build()).addActionRow(Button.primary("refresh", "⟳ Refresh")).queue();

            embed1.clear();

        } else if (event.getName().equals("new")) {
            EmbedBuilder embed2 = new EmbedBuilder();
            embed2.setTitle("\uD83D\uDCE2  What's new in town?");
            //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
            embed2.setDescription("Check out the official update changelogs here!\n");
            embed2.addField("", "\uD83C\uDFBA **Next is Now. Introducing v1.5!** \uD83C\uDFB7", false);
            embed2.addField("Introducing Project Tanpura","-Next phase of Pied Piper begins now! Experience the sophisticated side of the bot with more utilitarian functions and further bug fixes to the core!",false);
            embed2.addField("Ringa Ringa Roses", "-Introducing improved BetterQueue with circular queue implementation for peak queue accuracy!", false);
            embed2.addField("Seeing is Believing", "-New `/video` command which generates a YouTube video embed in the text channel!", false);
            embed2.addField("Smash-em-up.", "-Tons of bug fixes an various QoL improvements, making the experience a whole lot better", false);
            embed2.setColor(new Color(239,149,157));
            embed2.setFooter("man this seriously takes so long to write", "https://i.imgur.com/BMH6UcT.jpeg");
            event.replyEmbeds(embed2.build()).addActionRow(Button.success("PrevPage", "⬅️").asDisabled(), Button.success("NexPage", "➡️")).queue();
            embed2.clear();
        } else if (event.getName().equals("news")) {
            EmbedBuilder embed2 = new EmbedBuilder();
            //embed1.setAuthor("Ping-Pong!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://i.imgur.com/BMH6UcT.jpeg");
            embed2.setTitle("\uD83C\uDF73 What's cookin'");
            embed2.setDescription("Check out what's up with Pied Piper! This newsletter shares ideas and plans for what's coming up next ;)");

            embed2.addField("Project Tanpura", "Pied Piper's Tanpura build marks the next step in the musical journey. Phase 1 was all about providing a feature-rich music bot, whereas this phase is all about re-writing the magic that happens behind the scenes including polishing and optimizing the bot to boost its performance. This build will bring you a more sophisticated side of the bot with more utilitarian functions and further bug fixes to the core.", false);
            embed2.addField("*Current Status:*", "Build 1 rolling out now!", true);
            embed2.addField("*ETR:*", "Final release by Summer 2023", true);

            embed2.addField("Text Commands", "Well, Slash commands have not been made mandatory for every bot, however, text commands have been noticed to be causing rate limiting issues. For further monitoring, Text Commands have been disabled entirely. PS: They have not been removed and will be back!", false);
            embed2.addField("*Current Status:*", "Disabled until next update", true);
            embed2.addField("*ETR:*", "Spring, 2023", true);

            embed2.addField("Streamer Mode", "Streamer Mode Lite v2 has been facing performance issues and bugs since its roll out. Until it has been fixed, Streamer Mode Lite rolled back to v1.0.", false);
            embed2.addField("*Current Status:*", "v2 under Bux fixing", true);
            embed2.addField("*ETR:*", "~~Fall, 2022~~ Spring, 2023", true);

            embed2.addField("Repeating Queues", "Changing the way we imagine BetterQueue. Previously, once a queue was over, there was no way to replay it. This changed with v1.2 where users were able to repeat the previously played queue or track which was done by using multiple queues. This system has been replaced with a proper circular queue implementation for better accuracy.", false);
            embed2.addField("*Current Status:*", "Rolling out with v1.5", true);
            embed2.addField("*ETR:*", "~~Autumn, 2022~~ Released", true);

            embed2.addField("Support for multiple Audio Sources", "Imagine a place....where you can listen to live radio stations, twitch streams, or perhaps pull up a local audio file from your computer, directly on to the VC. LavaPlayer supports tons of file format. This feature can be utilised and implemented in the bot. This has been delayed and shall be rolled out with a future Tanpura build.", false);
            embed2.addField("*Current Status:*", "Under Conceptualisation Phase", true);
            embed2.addField("*ETR:*", "~~Autumn, 2022~~ Spring, 2023", true);

            embed2.setColor(new Color(242, 202, 9));
            embed2.setFooter("ETR: Estimated Time of Release | Stay tuned for more updates! | Last updated on 05/12/2023 by LangoOr Bellic", "https://i.imgur.com/BMH6UcT.jpeg");
            event.replyEmbeds(embed2.build()).setEphemeral(true).queue();
            embed2.clear();
        } else if (event.getName().equals("version")) {
            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setTitle("\uD83D\uDD0E Checking for Updates...");
            embed1.setColor(new Color(242,202,9));
            MessageEmbed eb = embed1.build();

            event.replyEmbeds(eb).queue(message -> {
                embed1.setColor(new Color(21,86,80));
                embed1.setTitle("\uD83E\uDD73 wohoo! The bot is Up-to-Date! ");
                // embed1.addField("Current Bot Version: ","v0.10.5 | Verion 0 Beta 10.5",true);
                embed1.addField("Current Bot build: ", "v1.5 | Version 2 | Tanpura Build", true);
                embed1.addField("Updated on: ", "05-Dec-2022", true);
                embed1.addField("", "**SYSTEM PROPERTIES:**", false);
                embed1.addField("", "**JDA Version:** " + JDAInfo.VERSION, false);
                //embed1.addField("", "**JDA-Utilities Version:** " + JDAUtilitiesInfo.VERSION, false);
                embed1.addField("", "**Lavaplayer Version:** " + PlayerLibrary.VERSION, false);
                embed1.addField("", "**MusixMatch Web API Version:** 1.1.4", false);
                embed1.addField("", "**Spotify Web API Version:** 7.1.0", false);
                long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
                long used = total - (Runtime.getRuntime().freeMemory() / 1024 / 1024);
                embed1.addField("", "**\nRUNTIME INFORMATION:**", false);
                embed1.addField("Total Memory:", "" + total, true);
                embed1.addField("Used Memory:", "" + used, true);
                // embed1.addField("","Testing Phase- Beta 10",false);
                embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://i.imgur.com/BMH6UcT.jpeg");
                message.editOriginalEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            });
        } else if (event.getName().equals("status")) {
            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setTitle("\uD83D\uDD0E Updating status...");
            embed1.setColor(new Color(242,202,9));
            MessageEmbed eb = embed1.build();

            event.replyEmbeds(eb).queue(message -> {
                embed1.setColor(new Color(21,86,80));
                embed1.setTitle("\uD83D\uDFE2 Bot is online!");
                //embed1.setDescription("Upcoming Planned Maintenance Update: 01/02/22"); //\nNext planned update: Version 0 Beta 10.5");
                embed1.setDescription("All bot functions are working as intended.");
                //embed1.addField("Rate Limit Pool", "" + event.getJDA().getRateLimitPool(), false);
                embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://i.imgur.com/BMH6UcT.jpeg");
                message.editOriginalEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            });
        }
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (event.getComponentId().equals("refresh")) {
            EmbedBuilder embed1 = new EmbedBuilder();

            embed1.setTitle("\uD83D\uDCF6 Ping Pong!");
            embed1.setDescription("Check bot's ping");
            long ping = event.getInteraction().getTimeCreated().getMinute();
            //long ping = event.getTimeCreated().until(event.getTimeCreated(), ChronoUnit.MILLIS);
            //long ping = event.getMessage().getTimeCreated().until(event.getMessage().getTimeCreated(), ChronoUnit.MILLIS);
            embed1.addField("Message Latency", "" + ping + "ms", true);
            embed1.addField("API Latency", event.getJDA().getGatewayPing() + "ms", true);
            if (event.getJDA().getGatewayPing() > 99) {
                embed1.setColor(new Color(220,77,77));
                embed1.addField("", "⚠ Looks like the ping is too high. Expect playback issues.", false);
            } else if (event.getJDA().getGatewayPing() > 79) {
                embed1.setColor(new Color(242,202,9));
                embed1.addField("", "⚠ Looks like the ping is a bit high. Expect music lag/ distorted playback.", false);
            } else if (event.getJDA().getGatewayPing() <= 0 || ping < 0) {
                embed1.setColor(new Color(35, 33, 28));
                embed1.addField("", "⚠ WebSocket ping looks incorrect. Please try again later.", false);
            } else {
                embed1.setColor(new Color(21,86,80));
            }
            embed1.setFooter("Pied Piper | Let's start groovin' ;)", "https://i.imgur.com/BMH6UcT.jpeg");
            // message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            event.editMessageEmbeds(embed1.build()).setActionRow(Button.primary("refresh", "⟳ Refresh")).queueAfter(1, TimeUnit.SECONDS);
            embed1.clear();
        } else if (event.getComponentId().equals("NexPage")) {
            EmbedBuilder embed2 = new EmbedBuilder();
            embed2.setTitle("\uD83D\uDCE2  What's new in town?");
            //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
            embed2.setDescription("Check out the official update changelogs here!\n");
            embed2.setColor(new Color(239,149,157));
            embed2.addField("", "**Previous Update Changelog**", false);
            embed2.addField("FEATURE DROP v1.3 & v1.35", "-Slash Commands are here! Experience the all new Audio Player, Queue Menus, and interactive buttons, only with Slash Commands!\n-New and improved Streamer Mode is here! Streamer Mode will now turn on automatically when someone in the VC starts Twitch Streaming or someone already streaming joins the VC.\n-Lyrics have been fixed. Now get accurate and clean lyrics directly from MusixMatch!\n-Bug Fixes\n", false);
            embed2.addField("FEATURE UPDATE v1.4", "-Seamlessly tune into Spotify tracks & playlists!\n-Directly access playlists and tracks on your Spotify profile without logging in!\n-Peak accuracy with high-definition music playback and 95% playlist conversion accuracy\n", false);

            embed2.setFooter("man this seriously takes so long to write", "https://i.imgur.com/BMH6UcT.jpeg");
            event.editMessageEmbeds(embed2.build()).setActionRow(Button.success("PrevPage", "⬅️").asEnabled(), Button.success("NexPage", "➡️").asDisabled()).queue();
            embed2.clear();
        } else if (event.getComponentId().equals("PrevPage")) {
            EmbedBuilder embed2 = new EmbedBuilder();
            embed2.setTitle("\uD83D\uDCE2  What's new in town?");
            //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
            embed2.setDescription("Check out the official update changelogs here!\n");

            embed2.addField("", "\uD83C\uDFBA **Next is Now. Introducing v1.5!** \uD83C\uDFB7", false);
            embed2.addField("Introducing Project Tanpura","-Next phase of Pied Piper begins now! Experience the sophisticated side of the bot with more utilitarian functions and further bug fixes to the core!",false);
            embed2.addField("Ringa Ringa Roses", "-Introducing improved BetterQueue with circular queue implementation for peak queue accuracy!", false);
            embed2.addField("Seeing is Believing", "-New `/video` command which generates a YouTube video embed in the text channel!", false);
            embed2.addField("Smash-em-up.", "-Tons of bug fixes an various QoL improvements, making the experience a whole lot better", false);
            embed2.setColor(new Color(239,149,157));
            embed2.setFooter("man this seriously takes so long to write", "https://i.imgur.com/BMH6UcT.jpeg");
            event.editMessageEmbeds(embed2.build()).setActionRow(Button.success("PrevPage", "⬅️").asDisabled(), Button.success("NexPage", "➡️").asEnabled()).queue();
            embed2.clear();
        }

    }
}