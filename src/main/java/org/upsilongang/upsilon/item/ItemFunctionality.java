package org.upsilongang.upsilon.item;

public abstract class ItemFunctionality
{
    private final UpsilonItem instance;

    public ItemFunctionality(UpsilonItem instance)
    {
        this.instance = instance;
    }

    public String getDescription()
    {
        return null;
    }
}