package ovh.astarivi.farmexpansion;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

import ovh.astarivi.farmexpansion.manager.BlockManager;
import ovh.astarivi.farmexpansion.renderer.FarmlandBlockEntityRenderer;
import ovh.astarivi.farmexpansion.renderer.FarmlandColorProvider;


public class FarmExpansionClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockManager.PARSNIP_CROP_BLOCK, BlockManager.FARMLAND_BLOCK);
		ColorProviderRegistry.BLOCK.register(new FarmlandColorProvider(), BlockManager.FARMLAND_BLOCK);

		BlockEntityRendererFactories.register(BlockManager.FARMLAND_BLOCK_ENTITY, FarmlandBlockEntityRenderer::new);
	}
}
