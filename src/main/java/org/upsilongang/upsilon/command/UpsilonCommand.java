package org.upsilongang.upsilon.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.upsilongang.upsilon.Upsilon;
import org.upsilongang.upsilon.util.UpsilonUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class UpsilonCommand implements CommandExecutor, TabCompleter
{
    public static final String COMMAND_SUFFIX = "Command";

    private static CommandMap cmap;
    private final String name;
    private final String description;
    private final String usage;
    private final String aliases;
    private final CommandSource source;
    private final CommandParameters params;

    private CommandUser sender;
    private String[] args;

    protected final Upsilon plugin = Upsilon.getPlugin();
    protected final Server server = plugin.getServer();

    protected final ChatColor BASE_COLOR = ChatColor.GRAY;
    protected final String NO_PERMISSION = "No permission.";
    protected final String ONLY_IN_GAME = "Console senders are not allowed to execute this command!";
    protected final String ONLY_CONSOLE = "Only console senders are allowed to execute this command!";
    protected final String PLAYER_NOT_FOUND = "Player not found.";
    protected final String CONFIG_ERROR = "There is an issue with a configuration entry. Please contact the server's administrator.";
    protected final String INVALID_NUMBER = "Invalid number.";

    UpsilonCommand()
    {
        params = getClass().getAnnotation(CommandParameters.class);
        this.name = getClass().getSimpleName().replace(COMMAND_SUFFIX, "").toLowerCase();
        this.description = params.description();
        this.usage = params.usage();
        this.aliases = params.aliases();
        this.source = params.source();
    }

    public void register()
    {
        UCommand cmd = new UCommand(this.name);
        if (this.aliases != null) cmd.setAliases(Arrays.asList(StringUtils.split(this.aliases, ",")));
        if (this.description != null) cmd.setDescription(this.description);
        if (this.usage != null) cmd.setUsage(this.usage);
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);
    }

    final CommandMap getCommandMap()
    {
        if (cmap == null)
        {
            try
            {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (cmap != null)
        {
            return cmap;
        }
        return getCommandMap();
    }

    private final class UCommand extends Command
    {
        private UpsilonCommand cmd = null;
        private UCommand(String command)
        {
            super(command);
        }
        public void setExecutor(UpsilonCommand cmd)
        {
            this.cmd = cmd;
        }

        public boolean execute(CommandSender sender, String c, String[] args)
        {
            if (cmd != null)
            {
                cmd.sender = new CommandUser(sender);
                cmd.args = args;

                if (!cmd.sender.isOP())
                {
                    send(NO_PERMISSION, ChatColor.RED);
                    return true;
                }

                if (params.source() == CommandSource.IN_GAME && sender instanceof ConsoleCommandSender)
                {
                    send(ONLY_IN_GAME, ChatColor.RED);
                    return true;
                }

                if (params.source() == CommandSource.CONSOLE && sender instanceof Player)
                {
                    send(ONLY_CONSOLE, ChatColor.RED);
                    return true;
                }
                return cmd.onCommand(sender, this, c, args);
            }
            return false;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args)
        {
            if (cmd != null)
            {
                return cmd.onTabComplete(sender, this, alias, args);
            }
            return null;
        }
    }

    protected void send(String s, CommandUser sender)
    {
        sender.sendMessage(BASE_COLOR + s);
    }

    protected void send(String s, CommandSender sender)
    {
        sender.sendMessage(BASE_COLOR + s);
    }

    protected void send(String s, Player player)
    {
        player.sendMessage(BASE_COLOR + s);
    }

    protected void send(String s, ChatColor color)
    {
        send(color + s);
    }

    protected void send(String s)
    {
        send(s, sender);
    }

    protected void blast(String s)
    {
        Bukkit.broadcastMessage(s);
    }

    protected void checkArgs(boolean failCondition)
    {
        if (failCondition)
        {
            throw new CommandArgumentException();
        }
    }

    protected void checkOP()
    {
        if (!sender.isOP())
        {
            throw new CommandFailException(NO_PERMISSION);
        }
    }

    protected void argsFailure()
    {
        throw new CommandArgumentException();
    }

    protected void onlyConsoleFailure()
    {
        throw new CommandFailException(ONLY_CONSOLE);
    }

    protected void onlyInGameFailure()
    {
        throw new CommandFailException(ONLY_IN_GAME);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args)
    {
        CommandUser cu = new CommandUser(sender);
        try
        {
            run(cu, args);
        }
        catch (CommandArgumentException ex)
        {
            send(cmd.getUsage().replace("<command>", cmd.getLabel()), ChatColor.WHITE);
            return true;
        }
        catch (PlayerNotFoundException ex)
        {
            send(ChatColor.RED + PLAYER_NOT_FOUND);
        }
        catch (CommandFailException ex)
        {
            send(ex.getMessage());
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String c, String[] args)
    {
        List<String> options = getTabCompleteOptions(this.sender, args);
        if (options == null)
        {
            return UpsilonUtil.getPlayerNameList();
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], options, new ArrayList<>());
    }

    public abstract void run(CommandUser sender, String[] args);

    protected List<String> getTabCompleteOptions(CommandUser sender, String[] args)
    {
        return UpsilonUtil.getPlayerNameList();
    }

    protected boolean isConsole()
    {
        return sender.getBase() instanceof ConsoleCommandSender;
    }


    protected Player getPlayer(String name)
    {
        return Bukkit.getPlayer(name);
    }

    protected int parseInt(String s)
    {
        int i;
        try
        {
            i = Integer.parseInt(s);
        }
        catch (NumberFormatException ex)
        {
            throw new CommandFailException(INVALID_NUMBER);
        }
        return i;
    }

    protected Player getNonNullPlayer(String name)
    {
        Player player = getPlayer(name);
        if (player == null)
            throw new PlayerNotFoundException();
        return player;
    }
}