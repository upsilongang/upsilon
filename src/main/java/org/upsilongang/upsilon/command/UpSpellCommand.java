package org.upsilongang.upsilon.command;

import org.upsilongang.upsilon.Rarity;
import org.upsilongang.upsilon.item.UpsilonItem;
import org.upsilongang.upsilon.spell.Spell;
import org.upsilongang.upsilon.spell.SpellStat;

@CommandParameters(description = "Apply a spell from upsilon.", usage = "/<command> <spell> <rarity>", aliases = "us", source = CommandSource.IN_GAME, op = true)
public class UpSpellCommand extends UpsilonCommand
{
    @Override
    public void run(CommandUser sender, String[] args)
    {
        checkArgs(args.length != 2);
        SpellStat stat = SpellStat.getStat(args[0]);
        if (stat == null)
            throw new CommandFailException("No such spell found.");
        Rarity rarity = Rarity.getRarity(args[1]);
        if (rarity == null)
            throw new CommandFailException("No such rarity found.");
        Spell spell = new Spell(stat, rarity);
        UpsilonItem ui = UpsilonItem.parse(sender.getPlayer().getInventory().getItemInMainHand());
        if (ui == null)
            throw new CommandFailException("This item cannot be parsed.");
        ui.addSpell(spell);
        send(spell.getDisplayName() + BASE_COLOR + " -> " + ui.getDisplayName());
    }
}
