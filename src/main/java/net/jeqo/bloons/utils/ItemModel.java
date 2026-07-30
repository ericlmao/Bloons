package net.jeqo.bloons.utils;

import net.jeqo.bloons.logger.Logger;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Applies namespaced item models to item metadata.
 */
public final class ItemModel {
    private ItemModel() {}

    /**
     * Applies an item model when one is configured.
     *
     * @param meta      the item metadata to update
     * @param itemModel the namespaced item model identifier
     */
    public static void apply(ItemMeta meta, String itemModel) {
        if (itemModel == null || itemModel.isBlank()) return;

        NamespacedKey itemModelKey = NamespacedKey.fromString(itemModel);
        if (itemModelKey == null) {
            Logger.logError("Failed to apply item model: '" + itemModel + "' is not a valid namespaced key");
            return;
        }

        meta.setItemModel(itemModelKey);
    }
}
