package org.upsilongang.upsilon.item;

import org.bukkit.Material;
import org.upsilongang.upsilon.Rarity;
import org.upsilongang.upsilon.item.special.Silon;

import java.lang.reflect.InvocationTargetException;

public enum ItemStat
{
    SILON("Silon", Rarity.ASYNITHIS, Material.NETHER_STAR, Silon.class),
    ;

    private final String name;
    private final Rarity rarity;
    private final Material material;
    private final ItemGrouping grouping;
    private final Class<? extends ItemFunctionality> funct;

    ItemStat(String name, Rarity rarity, Material material, ItemGrouping grouping, Class<? extends ItemFunctionality> funct)
    {
        this.name = name;
        this.rarity = rarity;
        this.material = material;
        this.grouping = grouping;
        this.funct = funct;
    }

    ItemStat(String name, Rarity rarity, Material material, Class<? extends ItemFunctionality> funct)
    {
        this(name, rarity, material, null, funct);
    }

    ItemStat(String name, Rarity rarity, Material material, ItemGrouping grouping)
    {
        this(name, rarity, material, grouping, null);
    }

    ItemStat(String name, Rarity rarity, Material material)
    {
        this(name, rarity, material, null, null);
    }

    public String getName()
    {
        return name;
    }

    public Rarity getRarity()
    {
        return rarity;
    }

    public Material getMaterial()
    {
        return material;
    }

    public ItemGrouping getGrouping()
    {
        return grouping;
    }

    public ItemFunctionality getFunctionality(UpsilonItem instance)
    {
        if (funct == null)
            return null;
        try
        {
            return funct.getConstructor(UpsilonItem.class).newInstance(instance);
        }
        catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ex)
        {
            return null;
        }
    }

    public static ItemStat getStat(String name)
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