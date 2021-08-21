package org.upsilongang.upsilon;

import org.bukkit.plugin.java.JavaPlugin;
import org.upsilongang.upsilon.command.CommandHandler;
import org.upsilongang.upsilon.command.UpItemCommand;
import org.upsilongang.upsilon.command.UpSpellCommand;
import org.upsilongang.upsilon.util.UpsilonLogger;

public final class Upsilon extends JavaPlugin
{
    private static Upsilon instance;
    public static Upsilon getPlugin()
    {
        return instance;
    }

    public CommandHandler ch;

    @Override
    public void onEnable()
    {
        instance = this;
        // Things to-do during enable write here
        loadCommands();
        UpsilonLogger.info("Enabled " + this.getDescription().getFullName());
    }

    @Override
    public void onDisable()
    {
        instance = null;
        // Things to-do during disable write here
        UpsilonLogger.info("Disabled " + this.getDescription().getFullName());
    }

    private void loadCommands()
    {
        ch = new CommandHandler();
        ch.add(new UpItemCommand());
        ch.add(new UpSpellCommand());
        int amount = ch.getCommandAmount();
        UpsilonLogger.info("Loaded " + amount + " command" + (amount != 1 ? "s" : "") + ".");
    }
}
