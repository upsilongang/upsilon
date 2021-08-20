package org.upsilongang.upsilon.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUser
{
    private CommandSender base;

    public CommandUser(CommandSender base)
    {
        this.base = base;
    }

    public CommandSender getBase()
    {
        return base;
    }

    public boolean isPlayer()
    {
        return base instanceof Player;
    }

    public Player getPlayer()
    {
        return (Player) base;
    }

    public void sendMessage(String s)
    {
        base.sendMessage(s);
    }

    public boolean isOP()
    {
        return base.isOp();
    }
}