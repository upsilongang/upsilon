package org.upsilongang.upsilon.spell;

import java.lang.reflect.InvocationTargetException;

public enum SpellStat
{
    HELLISH_FURY("Hellish Fury"),
    ;

    private final String name;
    private final Class<? extends SpellFunctionality> funct;

    SpellStat(String name, Class<? extends SpellFunctionality> funct)
    {
        this.name = name;
        this.funct = funct;
    }

    SpellStat(String name)
    {
        this(name, null);
    }

    public String getName()
    {
        return name;
    }

    public SpellFunctionality getFunctionality(Spell instance)
    {
        if (funct == null)
            return null;
        try
        {
            return funct.getConstructor(Spell.class).newInstance(instance);
        }
        catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ex)
        {
            return null;
        }
    }

    public static SpellStat getStat(String name)
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