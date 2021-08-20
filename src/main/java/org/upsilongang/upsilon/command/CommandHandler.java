package org.upsilongang.upsilon.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler
{
    private List<UpsilonCommand> commands;

    public CommandHandler()
    {
        commands = new ArrayList<>();
    }

    public void add(UpsilonCommand command)
    {
        commands.add(command);
        command.register();
    }

    public int getCommandAmount()
    {
        return commands.size();
    }
}