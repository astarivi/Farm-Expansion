package ovh.astarivi.farmexpansion.manager;

import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import ovh.astarivi.farmexpansion.FarmExpansion;

import static net.minecraft.registry.Registry.register;


public class ItemManager {
    public static final Item PARSNIP_SEEDS = register(
            Registries.ITEM,
            new Identifier(FarmExpansion.modId, "parsnip_seeds"),
            new AliasedBlockItem(
                BlockManager.PARSNIP_CROP_BLOCK,
                new Item.Settings()
            )
    );
}
