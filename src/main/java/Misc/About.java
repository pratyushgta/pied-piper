package Misc;

import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo;
import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class About extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-") && input.length < 2 || input[0].equalsIgnoreCase("pied") && input.length < 2 || input[0].equalsIgnoreCase("piper") && input.length < 2) {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Hello There! did you call me? Type `-help` or use slash commands to view all the bot commands!").queue();
            } else if (input[0].equalsIgnoreCase("-about")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("Pied Piper is here to groove!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setDescription("An incredibly easy to use music bot for Discord that makes it easy to listen to any of your favorite songs with a group of friends or by yourself! \n\nType `-help` to view the commands!\n\n");
                embed1.addField("Fuelled by the UseLess Engine.", "\uD83C\uDFA7 High-definition music playback powered by Lavaplayer™\n\uD83C\uDFA7 Easy queuing with BetterQueue technology\n\uD83C\uDFA7 Simple & easy-to-use interface\n\uD83C\uDFA7 Easy navigation using Slash Commands", false);
                //  embed1.addField("", "Pied Piper relies on your network and WebSocket connections. Music playback may be affected if your internet speeds are slow.\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.", false);

                embed1.setColor(Color.cyan);
                // embed1.setFooter("Pied Piper by LangoOr Bellic", "https://cdn.discordapp.com/embed/avatars/1.png");
                embed1.setFooter("For the love of music. Pied Piper by LangoOr Bellic | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                //embed1.setImage("https://cdn.vox-cdn.com/thumbor/PE_KcVvPxk1rUfyEQ7xqMtJC4FU=/0x0:1500x1000/1200x800/filters:focal(630x380:870x620)/cdn.vox-cdn.com/uploads/chorus_image/image/68726487/Nothing_RGB_Signalweiss.0.jpg");
                //embed1.setThumbnail("https://i.pinimg.com/originals/3c/5e/e5/3c5ee5788cbf613bcad691ab5d10e4e1.gif");
                // event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                event.getChannel().sendMessageEmbeds(embed1.build()).queue();
                event.getChannel().sendTyping().queue();
                embed1.clear();
            } else if (input[0].equalsIgnoreCase("-ping")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Retrieving bot's latency...");
                embed1.setColor(Color.yellow);
                MessageEmbed eb = embed1.build();
                event.getChannel().sendMessageEmbeds(eb).queue(message -> {
                    embed1.setTitle("\uD83D\uDCF6 Ping Pong!");
                    embed1.setDescription("Check bot's ping");
                    long ping = event.getMessage().getTimeCreated().getMinute();
                    // long ping = event.getMessage().getTimeCreated().until(event.getMessage().getTimeCreated(), ChronoUnit.MILLIS);
                    embed1.addField("Ping", "" + ping + "ms", true);
                    embed1.addField("Websocket", event.getJDA().getGatewayPing() + "ms", true);
                    if (event.getJDA().getGatewayPing() > 99) {
                        embed1.setColor(Color.red);
                        embed1.addField("", "⚠ Looks like the ping is too high. Expect playback issues!", false);
                    } else if (event.getJDA().getGatewayPing() > 79) {
                        embed1.setColor(Color.yellow);
                        embed1.addField("", "⚠ Looks like the ping is a bit high. Expect music lag/ distorted playback.", false);
                    } else if (event.getJDA().getGatewayPing() <= 0 || ping < 0) {
                        embed1.setColor(Color.gray);
                        embed1.addField("", "⚠ WebSocket ping looks incorrect. Please try again later.", false);
                    } else {
                        embed1.setColor(Color.green);
                    }
                    embed1.setFooter("Pied Piper | Let's start groovin' ;)", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    // message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                    message.editMessageEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
                embed1.clear();

            } else if (input[0].equalsIgnoreCase("-new") || input[0].equalsIgnoreCase("-wutnew") || input[0].equalsIgnoreCase("-whatsnew")) {
                EmbedBuilder embed2 = new EmbedBuilder();
                //embed1.setAuthor("Ping-Pong!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed2.setTitle("\uD83C\uDF89 What's new in town?");
                //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
                embed2.setDescription("Check out the official update changelogs here!\n");
                /*embed2.addField("","**FEATURE DROP v1.3**",false);
                embed2.addField("Seek, Swap, Repeat!","-Introducing the swap command! (because why not)\n-Presenting the seek command! Efficiently seek to a particular timestamp in the track!\n-Introducing Repeating queues! Now effortlessly replay the previous track or the queue!\n-Slightly improved response quality & time of lyrics command\n-Smashed bugs in the now playing & volume commands\n-Quality of Life Improvements (Improved responses and error messages)",false);*/

                embed2.addField("", "**Slash it or Dice it! Introducing v1.3!**", false);
                embed2.addField("Slash Commands are here! *FINALLY!*", "-Slash Commands are the new, exciting way to interact with bots on Discord. With Slash Commands, all you have to do is type `/` and you're ready to use your favorite bot! Enjoy the all new Audio Player, Queue Menus, and interactive buttons, only with Slash Commands! Check out `-slash` to get started!",false);
                embed2.addField("Introducing Streamer Mode v2!", "-New and improved Streamer Mode is here! Streamer Mode will now automatically turn on automatically when someone in the VC starts Twitch Streaming or someone already streaming joins the VC.", false);
                embed2.addField("Bug Ninja", "-Various bug fixes and Quality of Life improvements", false);
                //embed2.addField("PiedPiper is here to groove!","-Happy to announce that PiedPiper is now not only open-source but also open to more servers! Welcome to the cult, everyone!",false);
                /*embed2.addField("","**Few more Essentials.... Introducing v0.10.5 Beta!**",false);
                embed2.addField("BetterQueue is here!", "An exclusive queue management system, built from ground up. BetterQueue gives you the flexibility to customize the queue as you like! From moving a song to deleting the tracks added by an user, it does everything! BetterQueue introduces the following new commands:\n`-move`\n`-remove`\n`-shuffle`\n`-swap` ...and more!\n", false);
                embed2.addField("Introducing Streamer Mode Lite!","You asked for it and it's finally here! When enabled, Streamer Mode disables the audio player. Streamers do not have to worry about someone playing copyrighted music in the vc, anymore!",false);
                embed2.addField("Loads of new features!","-Having playback issues? Bot isn't responding? No worries! The -reconnect mode is here! It quickly disconnects the bot, clears cache and reconnects to the vc, resuming the song it left at.\n-You can now check for bot updates and upcoming maintenance schedule with the -version command\n-The whole queue can now be repeated as well. *FINALLY!*",false);
                embed2.addField("Quality of Life Improvements", "-Introducing an updated Now Playing embed which shows all essential info about the player!\n-Improved lyrics command. It's finally fixed! uff..I lost a few brain cells solving the issue with it.\n-You can not directly mute the player using -mute command. Saves typing time.", false);
                embed2.addField("New Synonyms!", "-First letter of most commands is now a command trigger. *Less typing.. phew!*", false);
                embed2.addField("Someone said bugs?", "Minor bug fixes here and there\n", false);*/
                embed2.setColor(Color.pink);
                //embed2.addBlankField(false);
                embed2.addField("", "**Previous Update Changelog**", false);
                embed2.addField("RELEASE UPDATE v1.0.0", "-Happy to announce that PiedPiper is now not only open-source but also open to more servers! Welcome to the cult, everyone!", false);
                embed2.addField("MINI UPDATE v1.1.0", "-A transcript of the reported bug/suggestions is now sent to the sender as well\n-Bot can now be disconnected from an empty VC\n-Minor Bug fixes", false);
                embed2.addField("BUG FIX UPDATE v1.1.1", "-Fixed the response mechanism in disconnect command\n-Improved response time of reconnect command\n-Ping command embed now changes color whenever the ping is beyond permissible limits\n-Fixed a bug in join command which used to let an user invite the bot to the vc while the bot is playing in a different vc", false);
               // embed2.addField("FEATURE UPDATE v1.2.2", "-Introducing the `swap` command! (because why not)\n-Presenting the `seek` command! Efficiently seek to a particular timestamp in the track!\n-Introducing Repeating queues! Now effortlessly replay the previous track or the queue!\n-Improved responses and error messages\n-Bot now auto-disconnects from the VC after the last user leaves. *FINALLY!*\n-Now experience better audio-quality and lesser buffering, across all devices\n-Bug fixes in `Now-Playing`, `Volume` and `Resume` commands & repeating queues\n-Improved response time of `lyrics` command\n", false);
                embed2.addField("FEATURE UPDATE v1.2.2", "-Introducing the `swap` & `-seek` commands!\n-Introducing Repeating queues! Now effortlessly replay the previous track or the queue!\n-Bot now auto-disconnects from the VC after the last user leaves. *FINALLY!*\n-Now experience better audio-quality and lesser buffering, across all devices\n-Bug fixes in `Now-Playing`, `Volume` and `Resume` commands & repeating queues\n", false);

                embed2.setFooter("oh and dw..text commands are still here", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                //this literally takes more brain cells to write
                // event.getChannel().sendMessage(embed2.build()).queue();
                event.getChannel().sendMessageEmbeds(embed2.build()).queue();
                event.getChannel().sendTyping().queue();
                embed2.clear();
            } else if (input[0].equalsIgnoreCase("-news") || input[0].equalsIgnoreCase("-updates")) {
                EmbedBuilder embed2 = new EmbedBuilder();
                //embed1.setAuthor("Ping-Pong!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed2.setTitle("\uD83C\uDF73 What's cookin'");
                embed2.setDescription("Check out what's up with Pied Piper! This is the section where I can interact with everyone and put down my ideas on what's next with Piper!");
                //embed2.addField("", "Check out what's up with Pied Piper!", false);
               /* embed2.addField("Playlists, new help menu & more!", "Playlists are here! As promised, v0.10.5 brings out the playlists, a new layout for the help menu, and the essential Search command!", false);
                embed2.addField("*Current Status:*","✅ Completed",true);
                embed2.addField("*ETR:*","Released with Beta v0.10.5",true);*/


               // embed2.addField("United We Stand", "Pied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.", false);
                //  embed2.addField("*Current Status:*","",true);
                // embed2.addField("*ETR:*","v1.0.0 Public version released",true);

                /*embed2.addField("Going out-of-beta!", "Well, are months and hours and minutes of beta testing, I finally think PiedPiper is polished enough to be made open to more servers! One thing though: This bot will still remain invite-only and will be exclusively available to a few servers.", false);
                embed2.addField("*Current Status:*","✅ Completed",true);
                embed2.addField("*ETR:*","v1.0.0 Public version released",true);*/


                embed2.addField("Slash Commands", "Discord is making changes to how users interact with bots. And for this, they are pushing the use of Slash Commands. While this would unify the way we interact it bots, it would also make it very difficult to sort through the extensive list commands, in every server. Luckily, Slash commands are only mandatory for bots which are either verified or in more than 75 servers. Since Pied Piper is an exclusive bot, Pied Piper will NOT be making use of Slash Commands anytime soon.", false);
                embed2.addField("*Current Status:*", "Huh.. turntables.", true);
                embed2.addField("*ETR:*", "Released with v1.3", true);

                embed2.addField("Spotify Update", "This has been the most requested feature. Implementation of a new Spotify Web API is still under progress but is taking longer than usual. Fingers-crossed \uD83E\uDD1E", false);
                embed2.addField("*Current Status:*", "Functional Testing underway", true);
                embed2.addField("*ETR:*", "Late Summer, 2022", true);

                embed2.addField("Streamer Mode", "Streamer Mode Lite has already been introduced. Though it not only lacks lots of essential features but also might be bugged atm. Since Streamer Mode is a fairly new concept, I plan to build it based on the reviews I get from our users.", false);
                embed2.addField("*Current Status:*", "Version 2 out for beta testing", true);
                embed2.addField("*ETR:*", "Late Summer, 2022", true);

                embed2.addField("Fixing 'Lyrics' forever", "Well, the Lyrics command has been quite unstable since the time it was first introduced. The main issue lies within the lyrics API which is being used. It is quite outdated and isn't regularly maintained. Recently, I came across the Genius API. Genius is already an industry leading Lyrics provider and using it's API will significantly improve user experience.", false);
                embed2.addField("*Current Status:*", "Genius API Implementation underway", true);
                embed2.addField("*ETR:*", "Fall, 2022", true);

                embed2.addField("Repeating Queues", "Changing the way we imagine BetterQueue. Currently, once a queue is over, there is no way to replay it. But it is about to change with the circular queue implementation in BetterQueue. Starting v1.3, users will be able to repeat the previously played queue or track. However, currently, the only way to view the previous queue/ track is by adding the previous queue to the live queue once the current playing queue is over. This is a fairly experimental concept and will be built upon user behaviour.", false);
                embed2.addField("*Current Status:*", "Under Conceptualisation Phase", true);
                embed2.addField("*ETR:*", "Late Summer, 2022", true);

                /*embed2.addField("Opening the Pipes","After the bot goes public, I do plan to make this project open source by providing the source code on GitHub! I strongly believe that open source encourages innovation through collaboration. Only with everyone's contribution can this bot become the next greatest music bot.",false);
                //embed2.addField("*Current Status:*","Cleaning up the code for GitHub",true);
                embed2.addField("*Current Status:*","GitHub code released: https://github.com/pratyushgta/pied-piper.git",true);
                embed2.addField("*ETR:*","✅ Completed",true);*/

                // embed2.addField("", "Stay Tuned for more updates!", false);
                //embed2.addField("", "", false);
                embed2.setColor(Color.orange);
                embed2.setFooter("ETR: Estimated Time of Release | Stay tuned for more updates! | Last updated on 18/04/2022 by LangoOr Bellic", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                event.getChannel().sendMessageEmbeds(embed2.build()).queue();
                event.getChannel().sendTyping().queue();
                event.getChannel().notifyAll();
                embed2.clear();
            } else if (input[0].equalsIgnoreCase("-version")) {

                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Checking for Updates...");
                embed1.setColor(Color.yellow);
                MessageEmbed eb = embed1.build();

                event.getChannel().sendMessageEmbeds(eb).queue(message -> {
                    embed1.setColor(Color.green);
                    embed1.setTitle("\uD83E\uDD73 wohoo! The bot is Up-to-Date! ");
                    // embed1.addField("Current Bot Version: ","v0.10.5 | Verion 0 Beta 10.5",true);
                    embed1.addField("Current Bot build: ", "v1.3 | Version 1", true);
                    embed1.addField("Updated on: ", "18-June-2022", true);
                    embed1.addField("", "**SYSTEM PROPERTIES:**", false);
                    embed1.addField("", "**JDA Version:** " + JDAInfo.VERSION, false);
                    embed1.addField("", "**JDA-Utilities Version:** " + JDAUtilitiesInfo.VERSION, false);
                    embed1.addField("", "**Lavaplayer Version:** " + PlayerLibrary.VERSION, false);
                    long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
                    long used = total - (Runtime.getRuntime().freeMemory() / 1024 / 1024);
                    embed1.addField("", "**\nRUNTIME INFORMATION:**", false);
                    embed1.addField("Total Memory:", "" + total, true);
                    embed1.addField("Used Memory:", "" + used, true);
                    // embed1.addField("","Testing Phase- Beta 10",false);
                    embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    message.editMessageEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
            } else if (input[0].equalsIgnoreCase("-status")) {

                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Updating status...");
                embed1.setColor(Color.yellow);
                MessageEmbed eb = embed1.build();

                event.getChannel().sendMessageEmbeds(eb).queue(message -> {
                    embed1.setColor(Color.green);
                    embed1.setTitle("\uD83D\uDFE2 Bot is online!");
                    //embed1.setDescription("Upcoming Planned Maintenance Update: 01/02/22"); //\nNext planned update: Version 0 Beta 10.5");
                    embed1.setDescription("All bot functions are working as intended.");
                    embed1.addField("Rate Limit Pool",""+event.getJDA().getRateLimitPool(),false);
                    embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    message.editMessageEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if (event.getName().equals("about")) {

            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setAuthor("Pied Piper is here to groove!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            embed1.setDescription("An incredibly easy to use music bot for Discord that makes it easy to listen to any of your favorite songs with a group of friends or by yourself! \n\nType `-help` to view the commands!\n\n");
            embed1.addField("Fuelled by the UseLess Engine.", "\uD83C\uDFA7 High-definition music playback powered by Lavaplayer™\n\uD83C\uDFA7 Easy queuing with BetterQueue technology\n\uD83C\uDFA7 Simple & easy-to-use interface\n\uD83C\uDFA7 Easy navigation using Slash Commands", false);

            embed1.setColor(Color.cyan);
            embed1.setFooter("For the love of music. Pied Piper by LangoOr Bellic | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            event.replyEmbeds(embed1.build()).addActionRow(Button.link("https://discordapp.com/users/349907501829062656/", "Butt Support")).queue();
            embed1.clear();

        } else if (event.getName().equals("ping")) {
            EmbedBuilder embed1 = new EmbedBuilder();
            event.deferReply().queue();

            embed1.setTitle("\uD83D\uDCF6 Ping Pong!");
            embed1.setDescription("Check bot's ping");
            long ping = event.getInteraction().getTimeCreated().getMinute();
            // long ping = event.getMessage().getTimeCreated().until(event.getMessage().getTimeCreated(), ChronoUnit.MILLIS);
            embed1.addField("Ping", "" + ping + "ms", true);
            embed1.addField("Websocket", event.getJDA().getGatewayPing() + "ms", true);
            if (event.getJDA().getGatewayPing() > 99) {
                embed1.setColor(Color.red);
                embed1.addField("", "⚠ Looks like the ping is too high. Expect playback issues!", false);
            } else if (event.getJDA().getGatewayPing() > 79) {
                embed1.setColor(Color.yellow);
                embed1.addField("", "⚠ Looks like the ping is a bit high. Expect music lag/ distorted playback.", false);
            } else if (event.getJDA().getGatewayPing() <= 0 || ping < 0) {
                embed1.setColor(Color.gray);
                embed1.addField("", "⚠ WebSocket ping looks incorrect. Please try again later.", false);
            } else {
                embed1.setColor(Color.green);
            }
            embed1.setFooter("Pied Piper | Let's start groovin' ;)", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            // message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            event.getHook().sendMessageEmbeds(embed1.build()).addActionRow(Button.primary("refresh","⟳ Refresh")).queue();

            embed1.clear();

        } else if (event.getName().equals("new")) {
            EmbedBuilder embed2 = new EmbedBuilder();
            embed2.setTitle("\uD83C\uDF89 What's new in town?");
            //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
            embed2.setDescription("Check out the official update changelogs here!\n");
            embed2.addField("", "**Slash it or Dice it! Introducing v1.3!**", false);
            embed2.addField("Slash Commands are here! *FINALLY!*", "-Slash Commands are the new, exciting way to interact with bots on Discord. With Slash Commands, all you have to do is type `/` and you're ready to use your favorite bot! Enjoy the all new Audio Player, Queue Menus, and interactive buttons, only with Slash Commands! Check out `-slash` to get started!",false);
            embed2.addField("Introducing Streamer Mode v2!", "-New and improved Streamer Mode is here! Streamer Mode will now automatically turn on automatically when someone in the VC starts Twitch Streaming or someone already streaming joins the VC.", false);
            embed2.addField("Bug Ninja", "-Various bug fixes and Quality of Life improvements", false);embed2.setColor(Color.pink);
            embed2.setFooter("oh and dw..text commands are still here", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            event.replyEmbeds(embed2.build()).addActionRow(Button.success("PrevPage","⬅️").asDisabled(),Button.success("NexPage","➡️")).queue();
            embed2.clear();
        } else if (event.getName().equals("news")) {
            EmbedBuilder embed2 = new EmbedBuilder();
            //embed1.setAuthor("Ping-Pong!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            embed2.setTitle("\uD83C\uDF73 What's cookin'");
            embed2.setDescription("Check out what's up with Pied Piper! This is the section where I can interact with everyone and put down my ideas on what's next with Piper!");


            embed2.addField("Slash Commands", "Discord is making changes to how users interact with bots. And for this, they are pushing the use of Slash Commands. While this would unify the way we interact it bots, it would also make it very difficult to sort through the extensive list commands, in every server. Luckily, Slash commands are only mandatory for bots which are either verified or in more than 75 servers. Since Pied Piper is an exclusive bot, Pied Piper will NOT be making use of Slash Commands anytime soon.", false);
            embed2.addField("*Current Status:*", "Huh.. turntables.", true);
            embed2.addField("*ETR:*", "Released with v1.3", true);

            embed2.addField("Spotify Update", "This has been the most requested feature. Implementation of a new Spotify Web API is still under progress but is taking longer than usual. Fingers-crossed \uD83E\uDD1E", false);
            embed2.addField("*Current Status:*", "Functional Testing underway", true);
            embed2.addField("*ETR:*", "Late Summer, 2022", true);

            embed2.addField("Streamer Mode", "Streamer Mode Lite has already been introduced. Though it not only lacks lots of essential features but also might be bugged atm. Since Streamer Mode is a fairly new concept, I plan to build it based on the reviews I get from our users.", false);
            embed2.addField("*Current Status:*", "Version 2 out for beta testing", true);
            embed2.addField("*ETR:*", "Late Summer, 2022", true);

            embed2.addField("Fixing 'Lyrics' forever", "Well, the Lyrics command has been quite unstable since the time it was first introduced. The main issue lies within the lyrics API which is being used. It is quite outdated and isn't regularly maintained. Recently, I came across the Genius API. Genius is already an industry leading Lyrics provider and using it's API will significantly improve user experience.", false);
            embed2.addField("*Current Status:*", "Genius API Implementation underway", true);
            embed2.addField("*ETR:*", "Fall, 2022", true);

            embed2.addField("Repeating Queues", "Changing the way we imagine BetterQueue. Currently, once a queue is over, there is no way to replay it. But it is about to change with the circular queue implementation in BetterQueue. Starting v1.3, users will be able to repeat the previously played queue or track. However, currently, the only way to view the previous queue/ track is by adding the previous queue to the live queue once the current playing queue is over. This is a fairly experimental concept and will be built upon user behaviour.", false);
            embed2.addField("*Current Status:*", "Under Conceptualisation Phase", true);
            embed2.addField("*ETR:*", "Late Summer, 2022", true);

            embed2.setColor(Color.orange);
            embed2.setFooter("ETR: Estimated Time of Release | Stay tuned for more updates! | Last updated on 18/04/2022 by LangoOr Bellic", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            event.replyEmbeds(embed2.build()).setEphemeral(true).queue();
            embed2.clear();
        } else if (event.getName().equals("version")) {
            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setTitle("\uD83D\uDD0E Checking for Updates...");
            embed1.setColor(Color.yellow);
            MessageEmbed eb = embed1.build();

            event.replyEmbeds(eb).queue(message -> {
                embed1.setColor(Color.green);
                embed1.setTitle("\uD83E\uDD73 wohoo! The bot is Up-to-Date! ");
                // embed1.addField("Current Bot Version: ","v0.10.5 | Verion 0 Beta 10.5",true);
                embed1.addField("Current Bot build: ", "Pied Piper Preview v1.4 Alpha | Version 1", true);
                embed1.addField("Updated on: ", "17-June-2022", true); //21-May-2022
                embed1.addField("", "**SYSTEM PROPERTIES:**", false);
                embed1.addField("", "**JDA Version:** " + JDAInfo.VERSION, false);
                embed1.addField("", "**JDA-Utilities Version:** " + JDAUtilitiesInfo.VERSION, false);
                embed1.addField("", "**Lavaplayer Version:** " + PlayerLibrary.VERSION, false);
                long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
                long used = total - (Runtime.getRuntime().freeMemory() / 1024 / 1024);
                embed1.addField("", "**\nRUNTIME INFORMATION:**", false);
                embed1.addField("Total Memory:", "" + total, true);
                embed1.addField("Used Memory:", "" + used, true);
                // embed1.addField("","Testing Phase- Beta 10",false);
                embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                message.editOriginalEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            });
        } else if (event.getName().equals("status")) {
            EmbedBuilder embed1 = new EmbedBuilder();
            embed1.setTitle("\uD83D\uDD0E Updating status...");
            embed1.setColor(Color.yellow);
            MessageEmbed eb = embed1.build();

            event.replyEmbeds(eb).queue(message -> {
                embed1.setColor(Color.green);
                embed1.setTitle("\uD83D\uDFE2 Bot is online!");
                //embed1.setDescription("Upcoming Planned Maintenance Update: 01/02/22"); //\nNext planned update: Version 0 Beta 10.5");
                embed1.setDescription("All bot functions are working as intended.");
                embed1.addField("Rate Limit Pool",""+event.getJDA().getRateLimitPool(),false);
                embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                message.editOriginalEmbeds(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            });
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getComponentId().equals("refresh")) {
            EmbedBuilder embed1 = new EmbedBuilder();

            embed1.setTitle("\uD83D\uDCF6 Ping Pong!");
            embed1.setDescription("Check bot's ping");
            long ping = event.getInteraction().getTimeCreated().getMinute();
            // long ping = event.getMessage().getTimeCreated().until(event.getMessage().getTimeCreated(), ChronoUnit.MILLIS);
            embed1.addField("Ping", "" + ping + "ms", true);
            embed1.addField("Websocket", event.getJDA().getGatewayPing() + "ms", true);
            if (event.getJDA().getGatewayPing() > 99) {
                embed1.setColor(Color.red);
                embed1.addField("", "⚠ Looks like the ping is too high. Expect playback issues!", false);
            } else if (event.getJDA().getGatewayPing() > 79) {
                embed1.setColor(Color.yellow);
                embed1.addField("", "⚠ Looks like the ping is a bit high. Expect music lag/ distorted playback.", false);
            } else if (event.getJDA().getGatewayPing() <= 0 || ping < 0) {
                embed1.setColor(Color.gray);
                embed1.addField("", "⚠ WebSocket ping looks incorrect. Please try again later.", false);
            } else {
                embed1.setColor(Color.green);
            }
            embed1.setFooter("Pied Piper | Let's start groovin' ;)", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            // message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
            event.editMessageEmbeds(embed1.build()).setActionRow(Button.primary("refresh","⟳ Refresh")).queueAfter(1, TimeUnit.SECONDS);
            embed1.clear();
        }

        else if(event.getComponentId().equals("NexPage")){
            EmbedBuilder embed2 = new EmbedBuilder();
            embed2.setTitle("\uD83C\uDF89 What's new in town?");
            //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
            embed2.setDescription("Check out the official update changelogs here!\n");

            embed2.setColor(Color.pink);
            embed2.addField("", "**Previous Update Changelog**", false);
            embed2.addField("", "**Previous Update Changelog**", false);
            embed2.addField("RELEASE UPDATE v1.0.0", "-Happy to announce that PiedPiper is now not only open-source but also open to more servers! Welcome to the cult, everyone!", false);
            embed2.addField("MINI UPDATE v1.1.0", "-A transcript of the reported bug/suggestions is now sent to the sender as well\n-Bot can now be disconnected from an empty VC\n-Minor Bug fixes", false);
            embed2.addField("BUG FIX UPDATE v1.1.1", "-Fixed the response mechanism in disconnect command\n-Improved response time of reconnect command\n-Ping command embed now changes color whenever the ping is beyond permissible limits\n-Fixed a bug in join command which used to let an user invite the bot to the vc while the bot is playing in a different vc", false);
            embed2.addField("FEATURE UPDATE v1.2.2", "-Introducing the `swap` & `-seek` commands!\n-Introducing Repeating queues! Now effortlessly replay the previous track or the queue!\n-Bot now auto-disconnects from the VC after the last user leaves. *FINALLY!*\n-Now experience better audio-quality and lesser buffering, across all devices\n-Bug fixes in `Now-Playing`, `Volume` and `Resume` commands & repeating queues\n", false);

            embed2.setFooter("oh and dw..text commands are still here", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            event.editMessageEmbeds(embed2.build()).setActionRow(Button.success("PrevPage","⬅️").asEnabled(),Button.success("NexPage","➡️").asDisabled()).queue();
            embed2.clear();
        }
        else if(event.getComponentId().equals("PrevPage")){
            EmbedBuilder embed2 = new EmbedBuilder();
            embed2.setTitle("\uD83C\uDF89 What's new in town?");
            //embed2.setDescription("Check out the official update changelogs here!\n\nPied Piper stands with Ukraine  \uD83C\uDDFA\uD83C\uDDE6  and world peace. Check out `-peace` for more info.");
            embed2.setDescription("Check out the official update changelogs here!\n");
            embed2.addField("", "**Slash it or Dice it! Introducing v1.3!**", false);
            embed2.addField("Slash Commands are here! *FINALLY!*", "-Slash Commands are the new, exciting way to interact with bots on Discord. With Slash Commands, all you have to do is type `/` and you're ready to use your favorite bot! Enjoy the all new Audio Player, Queue Menus, and interactive buttons, only with Slash Commands! Check out `-slash` to get started!",false);
            embed2.addField("Introducing Streamer Mode v2!", "-New and improved Streamer Mode is here! Streamer Mode will now automatically turn on automatically when someone in the VC starts Twitch Streaming or someone already streaming joins the VC.", false);
            embed2.addField("Bug Ninja", "-Various bug fixes and Quality of Life improvements", false);embed2.setColor(Color.pink);
            embed2.setFooter("oh and dw..text commands are still here", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
            event.editMessageEmbeds(embed2.build()).setActionRow(Button.success("PrevPage","⬅️").asDisabled(),Button.success("NexPage","➡️").asEnabled()).queue();
            embed2.clear();
        }

    }
}