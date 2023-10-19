package ovh.astarivi.farmexpansion.mixin;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ovh.astarivi.farmexpansion.manager.BlockManager;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;


@Mixin(HoeItem.class)
public abstract class HoeItemMixin {
    @Shadow @Final protected static Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLING_ACTIONS;
    @Unique
    private boolean injected = false;

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void constructorMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!injected) {
            TILLING_ACTIONS.clear();
            TILLING_ACTIONS.putAll(
                    Maps.newHashMap(Map.of(Blocks.GRASS_BLOCK, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(BlockManager.FARMLAND_BLOCK.getDefaultState())), Blocks.DIRT_PATH, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(BlockManager.FARMLAND_BLOCK.getDefaultState())), Blocks.DIRT, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(BlockManager.FARMLAND_BLOCK.getDefaultState())), Blocks.COARSE_DIRT, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.DIRT.getDefaultState())), Blocks.ROOTED_DIRT, Pair.of(itemUsageContext -> true, HoeItem.createTillAndDropAction(Blocks.DIRT.getDefaultState(), Items.HANGING_ROOTS))))
            );
            injected = true;
        }
    }
}
