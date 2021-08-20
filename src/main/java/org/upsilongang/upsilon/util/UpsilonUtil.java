package org.upsilongang.upsilon.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UpsilonUtil
{
    public static List<String> loreText(String s)
    {
        List<String> lines = new ArrayList<>();
        ChatColor currentColor = ChatColor.GRAY;
        StringBuilder currentString = new StringBuilder();
        for (String part : s.split(" "))
        {
            if (currentString.length() + part.length() > 32)
            {
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }
            boolean first = currentString.length() == 0;
            if (first)
                currentString.append(currentColor);
            else
                currentString.append(" ");
            currentString.append(part);
            int colorCodeIndex = part.indexOf("§");
            if (colorCodeIndex != -1 && colorCodeIndex + 1 < part.length())
            {
                ChatColor newColor = ChatColor.getByChar(part.charAt(colorCodeIndex + 1));
                if (newColor != null)
                    currentColor = newColor;
            }
        }
        if (currentString.length() != 0)
            lines.add(currentString.toString());
        return lines;
    }

    public static List<String> getPlayerNameList()
    {
        List<String> names = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
            names.add(player.getName());
        return names;
    }
}