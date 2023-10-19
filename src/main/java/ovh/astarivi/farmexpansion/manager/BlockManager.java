package ovh.astarivi.farmexpansion.manager;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import ovh.astarivi.farmexpansion.FarmExpansion;
import ovh.astarivi.farmexpansion.block.farmland.FarmlandBlock;
import ovh.astarivi.farmexpansion.block.farmland.FarmlandBlockEntity;
import ovh.astarivi.farmexpansion.crop.parsnip.ParsnipCropBlock;

import static net.minecraft.registry.Registry.register;


public class BlockManager {
    public static CropBlock PARSNIP_CROP_BLOCK;

    public static Block FARMLAND_BLOCK;

    public static BlockEntityType<FarmlandBlockEntity> FARMLAND_BLOCK_ENTITY;

    public static void initialize() {
        FARMLAND_BLOCK = register(
                Registries.BLOCK,
                new Identifier(FarmExpansion.modId, "farmland_block"),
                new FarmlandBlock(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.DIRT_BROWN)
                                .ticksRandomly()
                                .strength(0.6f)
                                .sounds(BlockSoundGroup.GRAVEL)
                                .dropsNothing()
                                .blockVision(Blocks::always)
                                .suffocates(Blocks::always)
                )
        );

        FARMLAND_BLOCK_ENTITY = register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(FarmExpansion.modId, "farmland_block_entity"),
                FabricBlockEntityTypeBuilder.create(FarmlandBlockEntity::new, FARMLAND_BLOCK).build()
        );

        PARSNIP_CROP_BLOCK = register(
                Registries.BLOCK,
                new Identifier(FarmExpansion.modId, "parsnip_crop_block"),
                new ParsnipCropBlock(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.GREEN)
                                .pistonBehavior(PistonBehavior.DESTROY)
                                .nonOpaque()
                                .noCollision()
                                .ticksRandomly()
                                .breakInstantly()
                                .sounds(BlockSoundGroup.CROP)
                )
        );
    }
}
