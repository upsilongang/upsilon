package org.upsilongang.upsilon.item;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.upsilongang.upsilon.Rarity;
import org.upsilongang.upsilon.spell.Spell;
import org.upsilongang.upsilon.spell.SpellStat;
import org.upsilongang.upsilon.util.UpsilonUtil;

import java.util.ArrayList;
import java.util.List;

public class UpsilonItem
{
    private ItemStack bukkitStack;

    private int amount;
    private final ItemStat stat;

    private List<Spell> spells;

    private UpsilonItem(ItemStack bukkitStack, int amount, ItemStat stat, List<Spell> spells)
    {
        this.bukkitStack = bukkitStack;
        this.amount = amount;
        this.bukkitStack.setAmount(this.amount);
        this.stat = stat;

        this.spells = spells;
    }

    public UpsilonItem update()
    {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(bukkitStack);
        NBTTagCompound compound = nmsStack.getOrCreateTag();
        compound.remove("stat");
        compound.remove("amount");
        compound.remove("spells");
        compound.setString("stat", this.stat.name());
        compound.setInt("amount", this.amount);
        if (this.spells.size() != 0)
        {
            NBTTagCompound spellTag = new NBTTagCompound();
            for (Spell spell : this.spells)
                spellTag.setString(spell.getStat().name(), spell.getRarity().name());
            compound.set("spells", spellTag);
        }
        nmsStack.setTag(compound);
        bukkitStack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        ItemMeta meta = bukkitStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(getDisplayName());
        meta.setLore(generateLore());
        if (this.spells.size() > 0 && !meta.hasEnchant(Enchantment.OXYGEN))
            meta.addEnchant(Enchantment.OXYGEN, 1, false);
        if (this.spells.size() == 0 && meta.hasEnchant(Enchantment.OXYGEN))
            meta.removeEnchant(Enchantment.OXYGEN);
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

    public void addSpell(Spell spell)
    {
        this.spells.removeIf(s -> s.getStat() == spell.getStat());
        this.spells.add(spell);
        this.update();
    }

    public void addSpell(SpellStat stat, Rarity rarity)
    {
        this.spells.removeIf(s -> s.getStat() == stat);
        this.spells.add(new Spell(stat, rarity));
        this.update();
    }

    public void removeSpell(Spell spell)
    {
        this.spells.removeIf(s -> s.getStat() == spell.getStat());
        this.update();
    }

    public void removeSpell(SpellStat stat)
    {
        this.spells.removeIf(s -> s.getStat() == stat);
        this.update();
    }

    private List<String> generateLore()
    {
        List<String> lines = new ArrayList<>();
        lines.add(" ");
        ItemFunctionality functionality = stat.getFunctionality(this);
        if (functionality != null)
        {
            String description = functionality.getDescription();
            if (description != null)
            {
                lines.addAll(UpsilonUtil.loreText(description));
                lines.add(" ");
            }
        }
        for (Spell spell : spells)
            lines.add(spell.getDisplayName());
        if (spells.size() > 0)
            lines.add(" ");
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
        UpsilonItem item = new UpsilonItem(new ItemStack(stat.getMaterial()), 1, stat, new ArrayList<>());
        item.update();
        return item;
    }

    public static UpsilonItem parse(ItemStack stack)
    {
        if (!validate(stack))
            return null;
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = nmsStack.getTag();
        if (compound == null)
            return null;
        List<Spell> spells = new ArrayList<>();
        if (compound.hasKey("spells"))
        {
            NBTTagCompound spellTag = compound.getCompound("spells");
            for (String key : spellTag.getKeys())
                spells.add(new Spell(SpellStat.getStat(key), Rarity.getRarity(spellTag.getString(key))));
        }
        return new UpsilonItem(stack,
                compound.getInt("amount"),
                ItemStat.getStat(compound.getString("stat")),
                spells);
    }

    public static boolean validate(ItemStack stack)
    {
        if (stack == null)
            return false;
        if (stack.getItemMeta() == null)
            return false;
        if (stack.getItemMeta().getLore() == null)
            return false;
        if (stack.getItemMeta().getLore().size() == 0)
            return false;
        return stack.getItemMeta().getLore().get(stack.getItemMeta().getLore().size() - 1).contains("upsilon");
    }
}