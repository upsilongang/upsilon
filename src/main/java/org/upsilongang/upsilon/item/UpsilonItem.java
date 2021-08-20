package org.upsilongang.upsilon.item;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.upsilongang.upsilon.util.UpsilonUtil;

import java.util.ArrayList;
import java.util.List;

public class UpsilonItem
{
    private ItemStack bukkitStack;

    private int amount;
    private final ItemStat stat;

    private UpsilonItem(ItemStack bukkitStack, int amount, ItemStat stat)
    {
        this.bukkitStack = bukkitStack;
        this.amount = amount;
        this.bukkitStack.setAmount(this.amount);
        this.stat = stat;
    }

    public UpsilonItem update()
    {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(bukkitStack);
        NBTTagCompound compound = nmsStack.getOrCreateTag();
        compound.remove("stat");
        compound.remove("amount");
        compound.setString("stat", this.stat.name());
        compound.setInt("amount", this.amount);
        nmsStack.setTag(compound);
        bukkitStack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        ItemMeta meta = bukkitStack.getItemMeta();
        meta.setDisplayName(getDisplayName());
        meta.setLore(generateLore());
        bukkitStack.setItemMeta(meta);
        return this;
    }

    public UpsilonItem setAmount(int amount)
    {
        this.amount = amount;
        this.bukkitStack.setAmount(amount);
        return this;
    }

    public int getAmount()
    {
        return amount;
    }

    private List<String> generateLore()
    {
        List<String> lines = new ArrayList<>();
        lines.add(" ");
        ItemFunctionality functionality = stat.getFunctionality();
        if (functionality != null)
        {
            String description = functionality.getDescription(this);
            if (description != null)
            {
                lines.addAll(UpsilonUtil.loreText(stat.getFunctionality().getDescription(this)));
                lines.add(" ");
            }
        }
        lines.add(stat.getRarity().getDisplay());
        lines.add(ChatColor.DARK_GRAY + "upsilon ID: " + stat.name());
        return lines;
    }

    public Rarity getRarity()
    {
        return stat.getRarity();
    }

    public String getDisplayName()
    {
        return getRarity().getColor() + stat.getName();
    }

    public ItemStack toBukkitStack()
    {
        return bukkitStack;
    }

    public static UpsilonItem create(ItemStat stat)
    {
        UpsilonItem item = new UpsilonItem(new ItemStack(stat.getMaterial()), 1, stat);
        item.update();
        return item;
    }
}