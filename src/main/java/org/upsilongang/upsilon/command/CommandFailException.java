package org.upsilongang.upsilon.command;

import org.bukkit.ChatColor;

public class CommandFailException extends RuntimeException
{
    public CommandFailException(String message)
    {
        super(ChatColor.RED + message);
    }
}