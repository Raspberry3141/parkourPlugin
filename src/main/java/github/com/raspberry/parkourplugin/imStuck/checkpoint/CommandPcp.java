package github.com.raspberry.parkourplugin.imStuck.checkpoint;

import github.com.raspberry.parkourplugin.imStuck.playerItemsManager.PlayerCapabilityController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPcp implements CommandExecutor {
    PlayerCapabilityController playercontroller;
    checkpointManager cpmanager;
    public CommandPcp(PlayerCapabilityController pcapabcntl,checkpointManager cpmgr) {
        playercontroller = pcapabcntl;
        cpmanager = cpmgr;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (playercontroller.getCapabilities((Player)commandSender) == PlayerCapabilityController.playerState.INPARKOUR) {
                cpmanager.teleportToCheckpoint(((Player) commandSender).getPlayer());
            }
        }
        return true;
    }
}
