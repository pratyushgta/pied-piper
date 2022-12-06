import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import utils.config;

import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.Random;

/**
 * TThis class contains methods for handling admin features
 * @author Pratyush Kumar (pratyushgta@gmail.com)
 * Please refer the Pied Piper Docs to setup the bot
 */

public class Settings extends ListenerAdapter {
    config ob = new config();

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent xevent) {
        if (!xevent.getAuthor().isBot() && !xevent.getAuthor().getId().equalsIgnoreCase(ob.mod)) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("New Message from " + xevent.getAuthor().getName());
            eb.setDescription(xevent.getMessage().getContentRaw());
            eb.setFooter(xevent.getAuthor().getId());
            eb.setColor(new Color(216, 213, 190));

            User user = xevent.getJDA().retrieveUserById(ob.mod).complete();
            user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(eb.build())).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");

        if (!(event.getMember()).getUser().isBot()) {
            if (event.getMember().getUser().getId().equalsIgnoreCase(ob.mod) && input[0].equalsIgnoreCase("-settings")) {
                EmbedBuilder embed1 = new EmbedBuilder();
                embed1.setTitle("Operations Overview");
                int servers = event.getJDA().getGuilds().size();
                int vcSize = (int) event.getJDA().getGuilds().stream().filter(g -> (g.getSelfMember().getVoiceState()).inVoiceChannel()).count();
                embed1.addField("Servers currently in", "" + servers, true);
                embed1.addField("Active VCs", "" + vcSize, true);
                embed1.setColor(new Color(35, 33, 28));
                embed1.setFooter("To dc bot from VC: -guild dc <server ID> | To remove bot from server: -guild remove <server ID>");
                // embed1.setFooter("v0.10.4 Beta. For internal testing in select servers only. | Pied Piper by LangoOr Bellic ", "https://i.imgur.com/BMH6UcT.jpeg");
                //event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(embed1.build())).queue();
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Beep boop boop beep boink").queue();
                //event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(">>SERVER LIST<<")).queue();
                EmbedBuilder eb = new EmbedBuilder();
                for (Guild guild : event.getJDA().getGuilds()) {
                    embed1.addField("" + guild.getName(), "" + guild.getIdLong(), false);
                }
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed1.build())).queue();
            } else if (input[0].equalsIgnoreCase("-guild") && input[1].equalsIgnoreCase("dc") && event.getMember().getUser().getId().equalsIgnoreCase(ob.mod)) {
                String ID = "";
                int counter = 0;
                if (input.length == 2) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Invalid. Correct usage: -guild dc `<Guild ID Long>`")).queue();
                }
                for (Guild guild : event.getJDA().getGuilds()) {
                    if (guild.getId().equalsIgnoreCase(input[2])) {
                        ID = input[2];
                        counter = 1;
                        break;
                    }
                }
                // event.getChannel().sendMessage("" + ID).queue();
                if (counter == 0) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Server not found!")).queue();
                    return;
                }
                (event.getJDA().getGuildById(ID)).getAudioManager().closeAudioConnection();
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Disconnected bot from VC of: " + (event.getJDA().getGuildById(input[2])).getName())).queue();
            } else if (input[0].equalsIgnoreCase("-guild") && input[1].equalsIgnoreCase("remove") && event.getMember().getUser().getId().equalsIgnoreCase(ob.mod)) {
                String ID = "";
                int counter = 0;
                if (input.length == 2) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Invalid. Correct usage: -guild remove `<Guild ID Long>`")).queue();
                }
                for (Guild guild : event.getJDA().getGuilds()) {
                    if (guild.getId().equalsIgnoreCase(input[2])) {
                        ID = input[2];
                        counter = 1;
                        break;
                    }
                }
                // event.getChannel().sendMessage("" + ID).queue();
                if (counter == 0) {
                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Server not found!")).queue();
                    return;
                }
                (event.getJDA().getGuildById(ID)).leave().queue();
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("REMOVED the bot from: " + (event.getJDA().getGuildById(input[2])).getName())).queue();
            } else if (input[0].equalsIgnoreCase("-guildinfo")) {
                String id = input[1];
                Guild guild = event.getJDA().getGuildById(id);
                if (guild != null) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(guild.getName(), guild.getIconUrl());
                    eb.setTitle(guild.getOwner().getEffectiveName() + " " + guild.getOwner().getId());
                    eb.setDescription(guild.getMemberCount() + " members in the server");
                    for (int i = 0; i < guild.getChannels().size(); i++) {
                        eb.addField("" + guild.getChannels().get(i).getName(), "", false);
                    }

                    event.getChannel().sendMessageEmbeds(eb.build()).queue();

                }
            }
        }
    }
}
