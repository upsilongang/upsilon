package org.upsilongang.upsilon.spell;

import org.upsilongang.upsilon.Rarity;

public class Spell
{
    private final SpellStat stat;
    private final Rarity rarity;

    public Spell(SpellStat stat, Rarity rarity)
    {
        this.stat = stat;
        this.rarity = rarity;
    }

    public String getDisplayName()
    {
        return rarity.getColor() + stat.getName();
    }

    public String getName()
    {
        return stat.getName();
    }

    public SpellStat getStat()
    {
        return stat;
    }

    public Rarity getRarity()
    {
        return rarity;
    }
}