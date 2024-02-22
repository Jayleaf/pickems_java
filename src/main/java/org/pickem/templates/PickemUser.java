package org.pickem.templates;

import java.io.Serializable;

public class PickemUser
{
    private int wins;
    private int losses;

    private double winPct;

    private String userId;

    private String tag;

    public PickemUser()
    {

    }

    public PickemUser(String userId, String tag)
    {
        this.wins = 0;
        this.losses = 0;
        this.winPct = 0.00;
        this.userId = userId;
        this.tag = tag;
    }

    private void calculateWinPct()
    {
        this.winPct = ((double) this.wins / (this.wins + this.losses)) * 100;
    }

    public String getTag()
    {
        return this.tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public int getWins()
    {
        return this.wins;
    }

    public int getLosses()
    {
        return this.losses;
    }

    public void setWins(int x)
    {
        this.wins = x;
        calculateWinPct();
    }

    public void setLosses(int x)
    {
        this.losses = x;
        calculateWinPct();
    }

    public void addWin()
    {
        this.wins += 1;
        calculateWinPct();
    }

    public void addLoss()
    {
        this.losses += 1;
        calculateWinPct();
    }

    public void removeWin()
    {
        this.wins -= 1;
        calculateWinPct();
    }

    public void removeLoss()
    {
        this.losses -= 1;
        calculateWinPct();
    }

    public String getUserId()
    {
        return this.userId;
    }

    public String toString()
    {
        return this.userId + " : " + this.tag + " (" + this.wins + "-" + this.losses + ")";
    }


}
