package rps;

import java.text.DecimalFormat;

public class Player 
{
    // variables
    private String name;
    private int wins, losses, draws, totalGames;
    private double winPercent;
    
    public Player(String nombre)
    {
        name = nombre;     
        wins = 0;
        losses = 0;
        draws = 0;
        totalGames = 0;
        winPercent = 0.0;
    }
    
    public Player(Player p)
    {
        name = p.getName();
        wins = p.getWins();
        losses = p.getLosses();
        draws = p.getDraws();
        totalGames = p.getTotalGames();
        winPercent = p.getWinPercent();
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int getWins() 
    {
        return wins;
    }

    public void setWins(int wins) 
    {
        this.wins = wins;
    }

    public int getLosses() 
    {
        return losses;
    }

    public void setLosses(int losses) 
    {
        this.losses = losses;
    }

    public int getDraws() 
    {
        return draws;
    }

    public void setDraws(int draws) 
    {
        this.draws = draws;
    }

    public int getTotalGames() 
    {
        return totalGames;
    }

    public void setTotalGames(int games) 
    {
        this.totalGames = games;
    }

    public double getWinPercent() 
    { 
        return winPercent;
    }

    public void setWinPercent(double winPercent) 
    {
        this.winPercent = winPercent;
    }
    
    public double calcWinPercent()
    {   DecimalFormat twoDForm = new DecimalFormat("#.##"); 
        return Double.valueOf(twoDForm.format((1.0 * getWins() / getTotalGames()) *100));
    }
}// end of class
