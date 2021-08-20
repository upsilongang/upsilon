package org.upsilongang.upsilon.item.special;

import org.bukkit.ChatColor;
import org.upsilongang.upsilon.item.ItemFunctionality;
import org.upsilongang.upsilon.item.UpsilonItem;

public class Silon implements ItemFunctionality
{
    @Override
    public String getDescription(UpsilonItem instance)
    {
        return ChatColor.RED + "How the hell did you get this item... that's impossible!";
    }
}
