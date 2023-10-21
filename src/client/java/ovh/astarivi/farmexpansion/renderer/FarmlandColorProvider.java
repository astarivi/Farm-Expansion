package ovh.astarivi.farmexpansion.renderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import ovh.astarivi.farmexpansion.block.farmland.FarmlandRenderData;

import java.awt.*;


public class FarmlandColorProvider implements BlockColorProvider {
    @Override
    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (world == null) return 0x9b7653;

        final Object renderData = world.getBlockEntityRenderData(pos);

        if (!(renderData instanceof FarmlandRenderData farmlandRenderData)) return 0x9b7653;

        Color finalColor = Color.getHSBColor(
                0.08F,
                (farmlandRenderData.getCombinedNutrientScale() / 300F) * 0.7F + 0.3F,
                0.6F - ((farmlandRenderData.moisture() / 100F) * 0.4F + 0.1F)
        );

        return finalColor.hashCode();
    }
}
