package github.com.raspberry.parkourplugin.checkpoint;

import github.com.raspberry.parkourplugin.playerItemsManager.PlayerCapabilityController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPcp implements CommandExecutor {
    PlayerCapabilityController playercontroller;
    public CommandPcp(PlayerCapabilityController pcapabcntl) {
        playercontroller = pcapabcntl;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (playercontroller.getCapabilities((Player)commandSender) == PlayerCapabilityController.playerState.INPARKOUR) {
            checkpointManager.getInstance().teleportToCheckpoint(((Player) commandSender).getPlayer());
            }
        }
        return true;
    }
}
