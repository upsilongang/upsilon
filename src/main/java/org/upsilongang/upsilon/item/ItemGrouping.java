package org.upsilongang.upsilon.item;

public enum ItemGrouping
{
    SPELL,
    DUNGEON_ITEM;

    public String getDisplay()
    {
        return name().replaceAll("_", " ");
    }
}