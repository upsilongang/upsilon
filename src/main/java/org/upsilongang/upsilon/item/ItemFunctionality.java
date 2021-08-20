package org.upsilongang.upsilon.item;

public interface ItemFunctionality
{
    default String getDescription(UpsilonItem instance)
    {
        return null;
    }
}