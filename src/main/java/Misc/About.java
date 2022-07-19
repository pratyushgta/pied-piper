package Misc;

import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo;
import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This class contains methods for displaying bot ping, about page, settings & bug reports
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs for more info
 */


public class About extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (input[0].equalsIgnoreCase("-") && input.length < 2 || input[0].equalsIgnoreCase("pied") && input.length < 2 || input[0].equalsIgnoreCase("piper") && input.length < 2) {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Hello There! did you call me? Type `-help` to view all the bot commands!").queue();
            } else if (input[0].equalsIgnoreCase("-about")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setAuthor("Pied Piper is here to groove!", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                embed1.setDescription("An incredibly easy to use music bot for Discord that makes it easy to listen to any of your favorite songs with a group of friends or by yourself! \n\nType `-help` to view the commands!\n\n");
                embed1.addField("Fuelled by the UseLess Engine.", "\uD83C\uDFA7 High-definition music playback powered by Lavaplayer™\n\uD83C\uDFA7 Easy queuing with BetterQueue technology\n\uD83C\uDFA7 Simple & easy-to-use interface", false);
                embed1.addField("", "Pied Piper relies on your network and WebSocket connections. Music playback may be affected if your internet speeds are slow.\n\n", false);

                embed1.setColor(Color.cyan);
                embed1.setFooter("For the love of music. Pied Piper by LangoOr Bellic | Let's start groovin' ;) ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                event.getChannel().sendMessage(embed1.build()).queue();
                event.getChannel().sendTyping().queue();
                embed1.clear();
            } else if (input[0].equalsIgnoreCase("-ping")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Retrieving bot's latency...");
                embed1.setColor(Color.yellow);
                MessageEmbed eb = embed1.build();
                event.getChannel().sendMessage(eb).queue(message -> {
                            embed1.setTitle("\uD83D\uDCF6 Ping Pong!");
                            embed1.setDescription("Check bot's latency");
                            long ping = event.getMessage().getTimeCreated().getMinute();
                            embed1.addField("Ping", "" + ping + "ms", true);
                            embed1.addField("Websocket", event.getJDA().getGatewayPing() + "ms", true);
                            embed1.setColor(Color.green);
                            embed1.setFooter("Pied Piper | Let's start groovin' ;)", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                        });
                embed1.clear();

            } else if (input[0].equalsIgnoreCase("-new") || input[0].equalsIgnoreCase("-wutnew") || input[0].equalsIgnoreCase("-whatsnew")) {
                EmbedBuilder embed2 = new EmbedBuilder();
                embed2.setTitle("\uD83C\uDF89 What's new in town?");
                embed2.setDescription("Checkout the official update changelogs here!");
                embed2.addField("","**Welcoming more people to the club!** Introducing v1.0.0!",false);
                embed2.addField("PiedPiper is here to groove!","-Happy to announce that PiedPiper is now not only open-source but also open to more servers! Welcome to the cult, everyone!",false);
                embed2.addField("","**Few more Essentials.... Introducing v0.10.5 Beta!**",false);
                embed2.addField("BetterQueue is here!", "An exclusive queue management system, built from ground up. BetterQueue gives you the flexibility to customize the queue as you like! From moving a song to deleting the tracks added by an user, it does everything! BetterQueue introduces the following new commands:\n`-move`\n`-remove`\n`-shuffle`\n`-swap` ...and more!\n", false);
                embed2.addField("Introducing Streamer Mode Lite!","You asked for it and it's finally here! When enabled, Streamer Mode disables the audio player. Streamers do not have to worry about someone playing copyrighted music in the vc, anymore!",false);
                embed2.addField("Loads of new features!","-Having playback issues? Bot isn't responding? No worries! The -reconnect mode is here! It quickly disconnects the bot, clears cache and reconnects to the vc, resuming the song it left at.\n-You can now check for bot updates and upcoming maintenance schedule with the -version command\n-The whole queue can now be repeated as well. *FINALLY!*",false);
                embed2.addField("Quality of Life Improvements", "-Introducing an updated Now Playing embed which shows all essential info about the player!\n-Improved lyrics command. It's finally fixed! uff..I lost a few brain cells solving the issue with it.\n-You can not directly mute the player using -mute command. Saves typing time.", false);
                embed2.addField("New Synonyms!", "-First letter of most commands is now a command trigger. *Less typing.. phew!*", false);
                embed2.addField("Someone said bugs?", "Minor bug fixes here and there\n", false);
                embed2.setColor(Color.pink);
                embed2.addBlankField(false);
                embed2.addField("BUG FIX UPDATE v0.10.1 Beta", "-Fixed a bug in lyrics module\n-Fixed a small bug in Streamer Mode Lite\n\n", false);
                embed2.addField("MINI UPDATE v0.10.2 Beta", "-Fixed error mechanism in the Player manager. Bot will now throw an exception error if no new sound is found\n-Added -status command to check for bot status.\n", false);
                embed2.addField("MINI UPDATE v0.10.3 Beta", "-Added a bug/ suggestions reporting mechanism for seamless bug resolutions!\n",false);
                embed2.addField("BUG FIX UPDATE v0.10.4 Beta", "-FIXED: Moving a song from higher to lower position in the queue would delete the last song from the queue",false);
                embed2.addField("FEATURE UPDATE v0.10.5 Beta", "-Introducing the Search command! Now search for your fav songs before adding them to the queue!\n-Playlists are here! Seamlessly add your YT playlist to the queue!\n-You can now choose any song from the queue, to play next.\nMajor overhaul to the Queue move command. You couldn't imagine how buggy it was before!\n-New, clutter-free help page with categories for various commands.\n-**NEW KEYWORDS:** `-next` to skip the song & `-add` to add a song to the queue",false);
                embed2.addField("MINI UPDATE v0.10.6 Beta", "-New and improved Queue menu\n-Now play YT playlists using the `-p` command\n-Minor bug fixes\n",false);
                embed2.setFooter("this literally takes more brain cells to write", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                event.getChannel().sendMessage(embed2.build()).queue();
                event.getChannel().sendTyping().queue();
                embed2.clear();
            }

            /**Please enter the discord ID (preferably the OWNER's discord ID) where you want the bot settings report be sent to**/

            else if (event.getMember().getUser().getId().equalsIgnoreCase("<OWNER_DISCORD_ID>") && input[0].equalsIgnoreCase("-settings")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("Operations Overview");
                int servers=event.getJDA().getGuilds().size();
                int vcSize= (int) event.getJDA().getGuilds().stream().filter(g -> g.getSelfMember().getVoiceState().inVoiceChannel()).count();
                embed1.addField("Servers currently in", "" + servers , true);
                embed1.addField("Active VCs", ""+vcSize, true);
                embed1.setColor(Color.black);
                embed1.setFooter("Pied Piper by LangoOr Bellic ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(embed1.build())).queue();
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Beep boop boop beep boink").queue();
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(">>SERVER LIST<<")).queue();
                for (Guild guild : event.getJDA().getGuilds()) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(guild.getName())).queue();
                }
            }

            else if (input[0].equalsIgnoreCase("-news") || input[0].equalsIgnoreCase("-updates")){
                EmbedBuilder embed2 = new EmbedBuilder();
                embed2.setTitle("\uD83C\uDF73 What's cookin'");
                embed2.setDescription("Check out what's up with Pied Piper! This is the section where I can interact with everyone and put down my ideas on what's next with Piper!");

                embed2.addField("Going out-of-beta!", "Well, are months and hours and minutes of beta testing, I finally think PiedPiper is polished enough to be made open to more servers! One thing though: This bot will still remain invite-only and will be exclusively available to a few servers.", false);
                embed2.addField("*Current Status:*","✅ Completed",true);
                embed2.addField("*ETR:*","v1.0.0 Public version released",true);

                embed2.addField("Streamer Mode", "Streamer Mode Lite has already been introduced. Though it not only lacks lots of essential features but also might be bugged atm. Since Streamer Mode is a fairly new concept, I plan to build it based on the reviews I get from our users.", false);
                embed2.addField("*Current Status:*","Lite version under beta testing",true);
                embed2.addField("*ETR:*","Fall, 2022",true);

                embed2.addField("Spotify Update", "This has been the most requested feature, and I'm happy to announce that you will soon be able to listen to your favourite Spotify playlists! Implementation of a new Spotify Web API is under progress.", false);
                embed2.addField("*Current Status:*","New API implementation in progress",true);
                embed2.addField("*ETR:*","Early Summer",true);

                embed2.addField("Opening the Pipes","After the bot goes public, I do plan to make this project open source by providing the source code on GitHub! I strongly believe that open source encourages innovation through collaboration. Only with everyone's contribution can this bot become the next greatest music bot.",false);
                embed2.addField("*Current Status:*","Cleaning up the code for GitHub",true);
                embed2.addField("*ETR:*","February 10th",true);

                embed2.setColor(Color.orange);
                embed2.setFooter("ETR: Estimated Time of Release | Stay tuned for more updates! | Last updated on 04/02/2022 by LangoOr Bellic", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                event.getChannel().sendMessage(embed2.build()).queue();
                event.getChannel().sendTyping().queue();
                event.getChannel().notifyAll();
                embed2.clear();
            }  else if (input[0].equalsIgnoreCase("-version")){

                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Checking for Updates...");
                embed1.setColor(Color.yellow);
                MessageEmbed eb = embed1.build();

                event.getChannel().sendMessage(eb).queue(message -> {
                    embed1.setColor(Color.green);
                    embed1.setTitle("\uD83E\uDD73 wohoo! The bot is Up-to-Date! ");
                    embed1.addField("Current Bot build: ","v1.0.0 | Version 1",true);
                    embed1.addField("Updated on: ","04-Feb-2022",true);
                    embed1.addField("", "**SYSTEM PROPERTIES:**", false);
                    embed1.addField("","**JDA Version:** "+ JDAInfo.VERSION,false);
                    embed1.addField("","**JDA-Utilities Version:** "+ JDAUtilitiesInfo.VERSION,false);
                    embed1.addField("","**Lavaplayer Version:** "+ PlayerLibrary.VERSION,false);
                    long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
                    long used = total - (Runtime.getRuntime().freeMemory() / 1024 / 1024);
                    embed1.addField("", "**\nRUNTIME INFORMATION:**", false);
                    embed1.addField("Total Memory:",""+total,true);
                    embed1.addField("Used Memory:",""+used,true);
                    embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.","https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
            } else if (input[0].equalsIgnoreCase("-status")){

                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDD0E Updating status...");
                embed1.setColor(Color.yellow);
                MessageEmbed eb = embed1.build();

                event.getChannel().sendMessage(eb).queue(message -> {
                    embed1.setColor(Color.green);
                    embed1.setTitle("\uD83D\uDFE2 Bot is online!");
                    embed1.setDescription("All bot functions are working as intended.");
                    embed1.setFooter("Built for select servers only | Pied Piper by LangoOr Bellic.","https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                    message.editMessage(embed1.build()).queueAfter(2, TimeUnit.SECONDS);
                });
            } else if (input[0].equalsIgnoreCase("-report")) {

                if(input[1].equalsIgnoreCase("bug"))
                {
                    Random random = new Random();
                    int reportno=random.nextInt(100000);
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("\uD83D\uDCDD BUG REPORT");
                    embed1.setColor(Color.lightGray);
                    String query="";
                    for(int i=2;i<input.length;i++)
                    {
                        query+=input[i]+" ";
                    }
                    embed1.setDescription(query);
                    embed1.addField("Reported by: ",""+event.getAuthor().getName(),false);
                    embed1.addField("User ID: ",""+event.getAuthor().getId(),false);
                    embed1.addField("Report no.: ","BUG"+reportno,false);
                    embed1.addField("User Avatar: ",""+event.getAuthor().getAsTag(),false);
                    embed1.addField("User Server: ",""+event.getGuild().getName(),false);

                    EmbedBuilder embed2 = new EmbedBuilder();
                    embed2.setTitle("\uD83D\uDCDD Send Confirmation: ");
                    embed2.setColor(Color.lightGray);
                    embed2.addField("Thank you for your reporting a bug! You are the one making Piped Piper better, every day!","Your report number is mentioned below. You can use this number to get in touch with Pied Piper Support#1168 regarding your query. Please DO NOT share this number with others as it helps identify your identity.",false);
                    embed2.setFooter("Report No.: BUG"+reportno);

                    /**Please enter YOUR discord ID where you want the bug/suggestions report to be sent to**/

                    User user= event.getJDA().retrieveUserById("<OWNER_DISCORD_ID>").complete();
                    user.openPrivateChannel().flatMap(channel -> channel.sendMessage(embed1.build())).queue();
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(embed2.build())).queue();
                    event.getChannel().sendMessage("✅ Your bug report has been sent! Please check your dm for more info.").queue();
                }
                else if(input[1].equalsIgnoreCase("suggestion"))
                {
                    Random random = new Random();
                    int reportno=random.nextInt(10000);
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("\uD83D\uDCDD SUGGESTION REPORT");
                    embed1.setColor(Color.lightGray);
                    String query="";
                    for(int i=2;i<input.length;i++)
                    {
                        query+=input[i]+" ";
                    }
                    embed1.setDescription(query);
                    embed1.addField("Reported by: ",""+event.getAuthor().getName(),false);
                    embed1.addField("User ID: ",""+event.getAuthor().getId(),false);
                    embed1.addField("Report no.: ","SUG"+reportno,false);
                    embed1.addField("User Avatar: ",""+event.getAuthor().getAsTag(),false);
                    embed1.addField("User Server: ",""+event.getGuild().getName(),false);

                    EmbedBuilder embed2 = new EmbedBuilder();
                    embed2.setTitle("\uD83D\uDCDD Send Confirmation: ");
                    embed2.setColor(Color.lightGray);
                    embed2.addField("Thank you for your suggestions!","Your report number is mentioned below. You can use this number to get in touch with Pied Piper Support#1168 regarding your query. Please DO NOT share this number with others as it helps identify your identity.",false);
                    embed2.setFooter("Report No.: SUG"+reportno);

                    /**Please enter YOUR discord ID where you want the bug/suggestions report to be sent to**/
                    
                    User user= event.getJDA().retrieveUserById("<OWNER_DISCORD_ID>").complete();
                    user.openPrivateChannel().flatMap(channel -> channel.sendMessage(embed1.build())).queue();
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(embed2.build())).queue();
                    event.getChannel().sendMessage("✅ Your suggestions have been shared! Please check your dm for more info.").queue();
                }
                else{
                    event.getChannel().sendMessage("\uD83D\uDEAB Did you get lost? To report a bug/ suggestion, use: `-report <bug / suggestion> <your query>`").queue();
                }
            }
        }
        }
    }