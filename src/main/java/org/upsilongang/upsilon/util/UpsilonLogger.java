package org.upsilongang.upsilon.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpsilonLogger
{
    private static void log(Object o, Level level)
    {
        Logger.getLogger("Minecraft").log(level, "[upsilon] " + o.toString());
    }

    public static void info(Object o)
    {
        log(o, Level.INFO);
    }

    public static void warn(Object o)
    {
        log(o, Level.WARNING);
    }

    public static void err(Object o)
    {
        log(o, Level.SEVERE);
    }
}
