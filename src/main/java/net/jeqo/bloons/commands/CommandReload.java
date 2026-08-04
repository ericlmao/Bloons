package net.jeqo.bloons.commands;

import net.jeqo.bloons.Bloons;
import net.jeqo.bloons.commands.manager.Command;
import net.jeqo.bloons.commands.manager.types.CommandPermission;
import net.jeqo.bloons.management.MultipartBalloonManagement;
import net.jeqo.bloons.management.SingleBalloonManagement;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A command used to reload the Bloons configurations
 */
public class CommandReload extends Command {

    /**
     *                 Constructor for the CommandReload class
     * @param plugin   The instance of the plugin, type org.bukkit.plugin.java.JavaPlugin
     */
    public CommandReload(JavaPlugin plugin) {
        super(plugin);
        this.addCommandAlias("reload");
        this.addCommandAlias("rl");
        this.setCommandDescription("Reload the Bloons config");
        this.setCommandSyntax("/bloons reload");
        this.setRequiredPermission(CommandPermission.RELOAD);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Bloons.getConfigurationManager().reload();

        Bloons.getBalloonCore().initialize();

        Bloons.getPlayerSingleBalloons().forEach((uuid, balloon) ->
                SingleBalloonManagement.restoreBalloon(balloon.getPlayer(), balloon));
        Bloons.getPlayerMultipartBalloons().keySet().forEach(MultipartBalloonManagement::restorePlayerBalloon);

        String configReloadedMessage = Bloons.getConfigurationManager().getMessage("prefix") + Bloons.getConfigurationManager().getMessage("config-reloaded");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configReloadedMessage));

        return false;
    }
}
