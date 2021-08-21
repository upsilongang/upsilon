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

        // keep track of the color and line of the lore
        ChatColor currentColor = ChatColor.GRAY;
        StringBuilder currentString = new StringBuilder();

        // split off string into parts so it's easier to manage
        for (String part : s.split(" "))
        {
            if (currentString.length() + part.length() > 32) // is the content of this part too large to fit in the current string?
            {
                // if so, break off the current string
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }

            boolean first = currentString.length() == 0;
            if (first) // are we at the beginning of the current string?
                currentString.append(currentColor); // if so, add the current color
            else
                currentString.append(" "); // otherwise, add a space

            // split off part by newline
            String[] nls = part.split("\n");

            if (nls.length <= 1) // does this part have no newline or one at the end?
            {
                currentString.append(part);

                // change color if necessary
                int colorCodeIndex = part.lastIndexOf("§");
                if (colorCodeIndex != -1 && colorCodeIndex + 1 < part.length())
                {
                    ChatColor newColor = ChatColor.getByChar(part.charAt(colorCodeIndex + 1));
                    if (newColor != null)
                        currentColor = newColor;
                }
            }
            else
            {
                // add the first line and break it
                currentString.append(nls[0]);
                lines.add(currentString.toString());
                currentString = new StringBuilder();

                // loop through the rest of the lines with a newline
                for (int i = 1; i < nls.length; i++)
                {
                    String nl = nls[i];
                    currentString.append(nl);

                    // check for the color changes and apply em if need be
                    int colorCodeIndex = nl.lastIndexOf("§");
                    if (colorCodeIndex != -1 && colorCodeIndex + 1 < nl.length())
                    {
                        ChatColor newColor = ChatColor.getByChar(nl.charAt(colorCodeIndex + 1));
                        if (newColor != null)
                            currentColor = newColor;
                    }

                    if (i != nls.length - 1) // are there more lines after this one?
                    {
                        // if so, break it off
                        lines.add(currentString.toString());
                        currentString = new StringBuilder().append(currentColor);
                    }
                }
            }

            if (part.endsWith("\n")) // do we have a newline at the end?
            {
                // if so, start new line
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }
        }

        // add current string if there's still another one to be added
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