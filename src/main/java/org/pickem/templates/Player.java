package org.pickem.templates;

import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.io.Serializable;

public class Player
{

    /*
     *********************************************************************
     *                DO NOT CHANGE ANYTHING IN THIS CLASS               *
     *                       WHILE DATA IS BEING STORED                  *
     *                            IT WILL CORRUPT IT                     *
     *********************************************************************
     */


    private String name;
    private String emoji;

    public Player()
    {

    }
    public Player(String name, String emoji)
    {
        this.emoji = emoji;
        this.name = name;
    }

    public String getEmoji()
    {
        return emoji;
    }

    public String getName()
    {
        return this.name;
    }

    public String getNameFormatted()
    {
        return this.name +  " (" + this.emoji + ")";
    }

    public String toString()
    {
        return name + ": " + emoji;
    }
}
