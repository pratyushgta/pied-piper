package Misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class ReportSlashCommand extends ListenerAdapter {
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("report")) {
            OptionMapping operator1 = event.getOption("bug");
            OptionMapping operator2 = event.getOption("suggestion");

            if (operator1 == null && operator2 == null) {
                event.deferReply().queue();
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("Reporting Bug/ Suggestions");
                embed1.setDescription("Reporting bugs or suggestions has gotten a lot more easier! You can use the following two commands to report your bugs or suggestions:\n\n1. `-report bug <your_query>`\n2. `-report suggestion <your_query>`\n\nEvery report sent out gets an unique Report number which is used to identify your report. It is like an user identification number which allows us to seamlessly retrieve your report." +
                        "You can use this Report number to get in touch with Pied Piper Support#1168 regarding your query.\n\nAdditionally, if you want to share further details in the form of screenshots, regarding your query, you can dm them to Pied Piper Support#1168. Don't forget to mention your Report number!\n\nFor new suggestions/ bug reports, please use the inbuilt -report command.\nAny reports which are irrelevant, will be discarded automatically. Spamming the Piped Piper Support might ban you from sending out new reports.");
                embed1.addField("Aliases:", "`report`", true);
                embed1.addField("Syntax:", "`/report <bug / suggestion> <your_description>`", true);
                embed1.addField("Example:", "`/report bug the bug flies off and comes back down send haalp asap`", true);
                embed1.setColor(Color.white);
                event.getHook().sendMessageEmbeds(embed1.build()).queue();
                embed1.clear();
            } else if (operator2 == null) {
                String query = operator1.getAsString();

                Random random = new Random();
                int reportno = random.nextInt(100000);
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDCDD BUG REPORT");
                embed1.setColor(Color.lightGray);

                embed1.setDescription(query);
                embed1.addField("Reported by: ", "" + event.getUser().getName(), false);
                embed1.addField("User ID: ", "" + event.getUser().getId(), false);
                embed1.addField("Report no.: ", "BUG" + reportno, false);
                embed1.addField("User Avatar: ", "" + event.getUser().getAsTag(), false);
                embed1.addField("User Server: ", "" + event.getGuild().getName(), false);

                EmbedBuilder embed2 = new EmbedBuilder();
                embed2.setTitle("\uD83D\uDCDD Send Confirmation: ");
                embed2.setColor(Color.lightGray);
                embed2.addField("Thank you for your reporting a bug! You are the one making Piped Piper better, every day!", "Your report number is mentioned below. You can use this number to get in touch with Pied Piper Support#1168 regarding your query. Please DO NOT share this number with others as it helps identify your identity.\n\n**Here is a transcript of your report:**\n`" + query + "`", false);
                embed2.setFooter("Report No.: BUG" + reportno);


                User user = event.getJDA().retrieveUserById("349907501829062656").complete();
                user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed1.build())).queue();
                event.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed2.build())).queue();
                event.reply("✅ Your bug report has been sent! Please check your DM for more info.").setEphemeral(true).queue();

            } else if (operator1 == null) {
                String query = operator2.getAsString();

                Random random = new Random();
                int reportno = random.nextInt(10000);
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("\uD83D\uDCDD SUGGESTION REPORT");
                embed1.setColor(Color.lightGray);

                embed1.setDescription(query);
                embed1.addField("Reported by: ", "" + event.getUser().getName(), false);
                embed1.addField("User ID: ", "" + event.getUser().getId(), false);
                embed1.addField("Report no.: ", "SUG" + reportno, false);
                embed1.addField("User Avatar: ", "" + event.getUser().getAsTag(), false);
                embed1.addField("User Server: ", "" + event.getGuild().getName(), false);

                EmbedBuilder embed2 = new EmbedBuilder();
                embed2.setTitle("\uD83D\uDCDD Send Confirmation: ");
                embed2.setColor(Color.lightGray);
                embed2.addField("Thank you for your suggestions!", "Your report number is mentioned below. You can use this number to get in touch with Pied Piper Support#1168 regarding your query. Please DO NOT share this number with others as it helps identify your identity.\n\n**Here is a transcript of your report:**\n`" + query + "`", false);
                embed2.setFooter("Report No.: SUG" + reportno);


                User user = event.getJDA().retrieveUserById("349907501829062656").complete();
                user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed1.build())).queue();
                event.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed2.build())).queue();
                event.reply("✅ Thank You for sharing your valuable suggestions! Please check your DM for more info.").setEphemeral(true).queue();

            }
        }
    }
}
