package org.pickem.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Pick {

    /*
     *********************************************************************
     *                DO NOT CHANGE ANYTHING IN THIS CLASS               *
     *                       WHILE DATA IS BEING STORED                  *
     *                            IT WILL CORRUPT IT                     *
     *********************************************************************
     */


    private String username;
    private String userId;
    private Player choice;

    public Pick()
    {

    }


    public Pick(String username, String userId, Player choice)
    {
        this.username = username;
        this.userId = userId;
        this.choice = choice;
    }


    public String getUsername()
    {
        return this.username;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public Player getChoice()
    {
        return this.choice;
    }

    public void setChoice(Player choice)
    {
        this.choice = choice;
    }
}
