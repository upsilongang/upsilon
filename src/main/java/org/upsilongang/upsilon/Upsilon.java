package org.upsilongang.upsilon;

import org.bukkit.plugin.java.JavaPlugin;
import org.upsilongang.upsilon.util.UpsilonLogger;

public final class Upsilon extends JavaPlugin
{
    private static Upsilon instance;
    public static Upsilon getPlugin()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        // Things to-do during enable write here
        UpsilonLogger.info("Enabled " + this.getDescription().getFullName());
    }

    @Override
    public void onDisable()
    {
        instance = null;
        // Things to-do during disable write here
        UpsilonLogger.info("Disabled " + this.getDescription().getFullName());
    }
}
