package ovh.astarivi.farmexpansion.manager;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import ovh.astarivi.farmexpansion.FarmExpansion;

import static net.minecraft.registry.Registry.register;


public class ItemManager {
    public static Item PARSNIP_SEEDS;

    public static void initialize() {
        register(
                Registries.ITEM,
                new Identifier(FarmExpansion.modId, "farmland_block"),
                new BlockItem(BlockManager.FARMLAND_BLOCK, new FabricItemSettings())
        );

        PARSNIP_SEEDS = register(
                Registries.ITEM,
                new Identifier(FarmExpansion.modId, "parsnip_seeds"),
                new AliasedBlockItem(
                        BlockManager.PARSNIP_CROP_BLOCK,
                        new Item.Settings()
                )
        );;
    }
}
