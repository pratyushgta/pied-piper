import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Settings extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (event.getMember().getUser().getId().equalsIgnoreCase("349907501829062656") && input[0].equalsIgnoreCase("-settings")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("Operations Overview");
                int servers = event.getJDA().getGuilds().size();
                int vcSize = (int) event.getJDA().getGuilds().stream().filter(g -> Objects.requireNonNull(g.getSelfMember().getVoiceState()).inAudioChannel()).count();
                embed1.addField("Servers currently in", "" + servers, true);
                embed1.addField("Active VCs", "" + vcSize, true);
                embed1.setColor(Color.black);
                // embed1.setFooter("v0.10.4 Beta. For internal testing in select servers only. | Pied Piper by LangoOr Bellic ", "https://mpng.subpng.com/20180703/rvo/kisspng-pied-piper-of-hamelin-computer-icons-clip-art-pied-piper-5b3b6309e4d5b3.3174947215306186339373.jpg");
                //event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(embed1.build())).queue();
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Beep boop boop beep boink").queue();
                //event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(">>SERVER LIST<<")).queue();
                EmbedBuilder eb = new EmbedBuilder();
                for (Guild guild : event.getJDA().getGuilds()) {
                    embed1.addField("" + guild.getName(), "" + guild.getIdLong(), false);
                }
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed1.build())).queue();
            } else if (input[0].equalsIgnoreCase("-report")) {

                if (input[1].equalsIgnoreCase("bug")) {
                    Random random = new Random();
                    int reportno = random.nextInt(100000);
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("\uD83D\uDCDD BUG REPORT");
                    embed1.setColor(Color.lightGray);
                    String query = "";
                    for (int i = 2; i < input.length; i++) {
                        query += input[i] + " ";
                    }
                    embed1.setDescription(query);
                    embed1.addField("Reported by: ", "" + event.getAuthor().getName(), false);
                    embed1.addField("User ID: ", "" + event.getAuthor().getId(), false);
                    embed1.addField("Report no.: ", "BUG" + reportno, false);
                    embed1.addField("User Avatar: ", "" + event.getAuthor().getAsTag(), false);
                    embed1.addField("User Server: ", "" + event.getGuild().getName(), false);

                    EmbedBuilder embed2 = new EmbedBuilder();
                    embed2.setTitle("\uD83D\uDCDD Send Confirmation: ");
                    embed2.setColor(Color.lightGray);
                    embed2.addField("Thank you for your reporting a bug! You are the one making Piped Piper better, every day!", "Your report number is mentioned below. You can use this number to get in touch with Pied Piper Support#1168 regarding your query. Please DO NOT share this number with others as it helps identify your identity.\n\n**Here is a transcript of your report:**\n`"+query+"`", false);
                    embed2.setFooter("Report No.: BUG" + reportno);


                    User user = event.getJDA().retrieveUserById("349907501829062656").complete();
                    user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed1.build())).queue();
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed2.build())).queue();
                    event.getChannel().sendMessage("✅ Your bug report has been sent! Please check your DM for more info.").queue();
                } else if (input[1].equalsIgnoreCase("suggestion")) {
                    Random random = new Random();
                    int reportno = random.nextInt(10000);
                    EmbedBuilder embed1 = new EmbedBuilder();
                    embed1.setTitle("\uD83D\uDCDD SUGGESTION REPORT");
                    embed1.setColor(Color.lightGray);
                    String query = "";
                    for (int i = 2; i < input.length; i++) {
                        query += input[i] + " ";
                    }
                    embed1.setDescription(query);
                    embed1.addField("Reported by: ", "" + event.getAuthor().getName(), false);
                    embed1.addField("User ID: ", "" + event.getAuthor().getId(), false);
                    embed1.addField("Report no.: ", "SUG" + reportno, false);
                    embed1.addField("User Avatar: ", "" + event.getAuthor().getAsTag(), false);
                    embed1.addField("User Server: ", "" + event.getGuild().getName(), false);

                    EmbedBuilder embed2 = new EmbedBuilder();
                    embed2.setTitle("\uD83D\uDCDD Send Confirmation: ");
                    embed2.setColor(Color.lightGray);
                    embed2.addField("Thank you for your suggestions!", "Your report number is mentioned below. You can use this number to get in touch with Pied Piper Support#1168 regarding your query. Please DO NOT share this number with others as it helps identify your identity.\n\n**Here is a transcript of your report:**\n`"+query+"`", false);
                    embed2.setFooter("Report No.: SUG" + reportno);


                    User user = event.getJDA().retrieveUserById("349907501829062656").complete();
                    user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed1.build())).queue();
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed2.build())).queue();
                    event.getChannel().sendMessage("✅ Thank You for sharing your valuable suggestions! Please check your DM for more info.").queue();
                } else {
                    event.getChannel().sendMessage("\uD83D\uDEAB Did you get lost? To report a bug/ suggestion, use: `-report <bug / suggestion> <your query>`").queue();
                }
            }
            else if (input[0].equalsIgnoreCase("-guild") && input[1].equalsIgnoreCase("dc") && event.getMember().getUser().getId().equalsIgnoreCase("349907501829062656")) {
                String ID = "";
                int counter=0;
                if (input.length == 2) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Invalid. Correct usage: -guild remove `<Guild ID Long>`")).queue();
                }
                for (Guild guild : event.getJDA().getGuilds()) {
                    if (guild.getId().equalsIgnoreCase(input[2])) {
                        ID = input[2];
                        counter=1;
                        break;
                    }
                }
                // event.getChannel().sendMessage("" + ID).queue();
                if (counter==0) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Server not found!")).queue();
                    return;
                }
                Objects.requireNonNull(event.getJDA().getGuildById(ID)).getAudioManager().closeAudioConnection();
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Disconnected bot from VC of: "+ Objects.requireNonNull(event.getJDA().getGuildById(input[2])).getName())).queue();
            }
            else if (input[0].equalsIgnoreCase("-guild") && input[1].equalsIgnoreCase("remove") && event.getMember().getUser().getId().equalsIgnoreCase("349907501829062656")) {
                String ID = "";
                int counter=0;
                if (input.length == 2) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Invalid. Correct usage: -guild remove `<Guild ID Long>`")).queue();
                }
                for (Guild guild : event.getJDA().getGuilds()) {
                    if (guild.getId().equalsIgnoreCase(input[2])) {
                        ID = input[2];
                        counter=1;
                        break;
                    }
                }
                // event.getChannel().sendMessage("" + ID).queue();
                if (counter==0) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Server not found!")).queue();
                    return;
                }
                Objects.requireNonNull(event.getJDA().getGuildById(ID)).leave().queue();
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("REMOVED the bot from: "+ Objects.requireNonNull(event.getJDA().getGuildById(input[2])).getName())).queue();
            }
        }
    }
}