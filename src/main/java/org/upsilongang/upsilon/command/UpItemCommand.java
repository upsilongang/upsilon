package org.upsilongang.upsilon.command;

import org.upsilongang.upsilon.item.ItemStat;
import org.upsilongang.upsilon.item.UpsilonItem;

@CommandParameters(description = "Obtain an item from upsilon.", usage = "/<command> <item>", aliases = "ui", source = CommandSource.IN_GAME, op = true)
public class UpItemCommand extends UpsilonCommand
{
    @Override
    public void run(CommandUser sender, String[] args)
    {
        checkArgs(args.length != 1);
        ItemStat stat = ItemStat.getStat(args[0]);
        if (stat == null)
            throw new CommandFailException("No such item found.");
        UpsilonItem ui = UpsilonItem.create(stat);
        sender.getPlayer().getInventory().addItem(ui.toBukkitStack());
        send(" + " + ui.getDisplayName());
    }
}
