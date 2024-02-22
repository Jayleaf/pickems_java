package org.pickem.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.pickem.templates.Event;
import org.pickem.templates.EventContainer;

public class ManualEvent {

    private Event event;
    private TextChannel channel;

    public ManualEvent(Event event, TextChannel channel, User initiator, SlashCommandInteractionEvent ie)
    {
        this.event = event;
        this.channel = channel;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(event.toFormattedString());
        eb.setDescription("React below to vote on this event.");
        eb.build();
        channel.sendMessageEmbeds(eb.build()).queue((message) -> {
            this.event.setMessageId(message.getId());
            message.addReaction((Emoji.fromUnicode(this.event.getPlayer().getEmoji()))).queue();
            message.addReaction((Emoji.fromUnicode(this.event.getOpponent().getEmoji()))).queue();
            eb.setFooter("Manual Event: Initiated by " + initiator.getName() + "\n" + "Event ID: " + this.event.getMessageId() + "\n" + "Tag: " + event.getTag());
            message.editMessageEmbeds(eb.build()).queue();


            // I REALLY do not want to put this all inside the callback, but I can't make the message ID visible outside of callback scope,
            // so I have to cope.


            // Now that everything is sent, we need to serialize the event instance for later use.

            EventContainer events = new EventContainer();
            events.deserializeData();
            events.addEntry(this.event);
            events.serializeData();
            ie.reply("Successfully created event").setEphemeral(true).queue();
        });
    }
}
