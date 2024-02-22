package org.pickem.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.pickem.commands.*;
import org.pickem.templates.Event;
import org.pickem.templates.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class CommandListener extends ListenerAdapter
{
    private final String[] modRoleIds = {"849889265063428098", "1147165441953906768"};
    public boolean noPermission(Member user) {
        for(Role r : user.getRoles())
        {
            if(Arrays.asList(modRoleIds).contains(r.getId()))
            {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {


        if(event.getName().equals("manualevent"))
        {
            if(noPermission(Objects.requireNonNull(event.getMember())))
            {
                event.reply("You cannot run this command.").setEphemeral(true).queue();
                return;
            }

            Player player = new Player(Objects.requireNonNull(event.getOption("player")).getAsString(), Objects.requireNonNull(event.getOption("playeremoji")).getAsString());
            Player opponent = new Player(Objects.requireNonNull(event.getOption("opponent")).getAsString(), Objects.requireNonNull(event.getOption("opponentemoji")).getAsString());
            Event entry = new Event(player, opponent, "null", Objects.requireNonNull(event.getOption("tag")).getAsString(), false);
            ManualEvent e = new ManualEvent(entry, (TextChannel) event.getChannel(), event.getUser(), event);
        }
        else if(event.getName().equals("listevents"))
        {
            ListEvents l = new ListEvents(event);
        }
        else if(event.getName().equals("closeevent"))
        {

            if(noPermission(event.getMember()))
            {
                event.reply("You cannot run this command.").setEphemeral(true).queue();
                return;
            }
            CloseEvent c = new CloseEvent(Objects.requireNonNull(event.getOption("eventid")).getAsString(), event);
        }
        else if(event.getName().equals("decideevent"))
        {
            if(noPermission(event.getMember()))
            {
                event.reply("You cannot run this command.").setEphemeral(true).queue();
                return;
            }
            DecideEvent d = new DecideEvent(event, Objects.requireNonNull(event.getOption("eventid")).getAsString(), Objects.requireNonNull(event.getOption("winner")).getAsString());
        }
        else if(event.getName().equals("pickrecord"))
        {
            User user = event.getOption("user") == null ? event.getUser() : Objects.requireNonNull(event.getOption("user")).getAsUser();
            PickRecord p = new PickRecord(event, user, Objects.requireNonNull(event.getOption("tag")).getAsString());
        }

    }
}
