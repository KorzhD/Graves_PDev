package org.example.dmytrok.dkgraveplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.example.dmytrok.dkgraveplugin.items.GraveBreakerCreator;
import org.example.dmytrok.dkgraveplugin.items.SuperKeyCreator;

public class SuperKeyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player");
            return false;
        }

        Player player = (Player) commandSender;

        if(!(player.hasPermission("superKey.admin"))) {
            commandSender.sendMessage("You must be an admin");
            return false;
        }

        ItemStack itemStack = SuperKeyCreator.createItem();

        player.getInventory().addItem(itemStack);
        player.sendMessage("§5Want to steal something?");

        return true;
    }
}
