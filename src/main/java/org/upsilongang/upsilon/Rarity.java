package org.upsilongang.upsilon;

import org.bukkit.ChatColor;

public enum Rarity
{
    SYNITHIS(ChatColor.YELLOW),
    ALPHA(ChatColor.WHITE),
    GAMMA(ChatColor.BLUE),
    THETA(ChatColor.DARK_PURPLE),
    SIGMA(ChatColor.LIGHT_PURPLE),
    DIAVOLOS(ChatColor.DARK_RED),
    ASYNITHIS(ChatColor.GOLD);

    private final ChatColor color;

    Rarity(ChatColor color)
    {
        this.color = color;
    }

    public String getDisplay()
    {
        return color + "" + ChatColor.BOLD + name();
    }

    public ChatColor getColor()
    {
        return color;
    }

    public String getBoldedColor()
    {
        return color + "" + ChatColor.BOLD;
    }

    public static Rarity getRarity(String name)
    {
        try
        {
            return valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException ex)
        {
            return null;
        }
    }
}