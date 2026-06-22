package net.jeqo.bloons.management;

import net.jeqo.bloons.Bloons;
import net.jeqo.bloons.balloon.multipart.balloon.MultipartBalloon;
import net.jeqo.bloons.configuration.ConfigConfiguration;

import java.util.UUID;

/**
 * A class to manage the active balloons tied to a player
 */
public class MultipartBalloonManagement {

    /**
     *                  Set the player's balloon in the active balloons map
     * @param playerId  The player's UUID, type java.util.UUID
     * @param balloon   The balloon to set, type net.jeqo.bloons.balloon.multipart.balloon.MultipartBalloon
     */
    public static void setPlayerBalloon(UUID playerId, MultipartBalloon balloon) {
        Bloons.getPlayerMultipartBalloons().put(playerId, balloon);
    }

    /**
     *                  Get the player's balloon from the active balloons map
     * @param playerId  The player's UUID, type java.util.UUID
     * @return          The player's balloon, type net.jeqo.bloons.balloon.multipart.balloon.MultipartBalloon
     */
    public static MultipartBalloon getPlayerBalloon(UUID playerId) {
        return Bloons.getPlayerMultipartBalloons().get(playerId);
    }

    /**
     *                  Remove the player's balloon from the active balloons map
     * @param playerId  The player's UUID, type java.util.UUID
     */
    public static void removePlayerBalloon(UUID playerId) {
        MultipartBalloon balloon = Bloons.getPlayerMultipartBalloons().remove(playerId);
        if (balloon != null) {
            balloon.destroy();
        }
    }

    /**
     *                  Hide the player's balloon entities while preserving the equipped balloon
     * @param playerId  The player's UUID, type java.util.UUID
     */
    public static void storePlayerBalloon(UUID playerId) {
        MultipartBalloon balloon = Bloons.getPlayerMultipartBalloons().get(playerId);
        if (balloon != null) {
            balloon.destroy();
        }
    }

    /**
     *                  Spawn the equipped balloon if the owner is in a whitelisted world
     * @param playerId  The player's UUID, type java.util.UUID
     */
    public static void restorePlayerBalloon(UUID playerId) {
        MultipartBalloon balloon = Bloons.getPlayerMultipartBalloons().get(playerId);
        if (balloon == null) return;

        if (!ConfigConfiguration.canSpawnBalloonsInWorld(balloon.getOwner().getWorld())) {
            balloon.destroy();
            return;
        }

        if (!balloon.isSpawned()) {
            balloon.initialize();
            balloon.run();
        }
    }
}
