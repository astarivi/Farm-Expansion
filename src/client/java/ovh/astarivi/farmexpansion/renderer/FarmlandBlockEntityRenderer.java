package ovh.astarivi.farmexpansion.renderer;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

import ovh.astarivi.farmexpansion.block.farmland.FarmlandBlockEntity;

import software.bernie.geckolib.renderer.GeoBlockRenderer;


public class FarmlandBlockEntityRenderer extends GeoBlockRenderer<FarmlandBlockEntity> {
    public FarmlandBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(new FarmlandBlockModel());
    }
}
