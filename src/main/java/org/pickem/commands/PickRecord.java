package org.pickem.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.pickem.templates.PickemUser;
import org.pickem.templates.PickemUserContainer;

import java.util.Objects;

public class PickRecord
{
    public PickRecord(SlashCommandInteractionEvent ie, User user, String tag)
    {
        PickemUserContainer pc = new PickemUserContainer();
        pc.deserializeData();
        for(PickemUser p : pc.getUsers())
        {
            if(Objects.equals(p.getUserId(), user.getId()) && Objects.equals(p.getTag(), tag))
            {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Pickem Record");
                eb.setDescription("Tag: " + tag);
                eb.addField("Wins", String.valueOf(p.getWins()), true);
                eb.addField("Losses", String.valueOf(p.getLosses()), true);
                eb.setFooter("Record of user " + user.getName());
                eb.setThumbnail(user.getAvatarUrl());
                ie.replyEmbeds(eb.build()).queue();
                return;
            }
        }
        ie.reply("Either the user mentioned has no record on that tag or that tag does not exist. Please try again after adjusting the parameters.").queue();
    }
}
