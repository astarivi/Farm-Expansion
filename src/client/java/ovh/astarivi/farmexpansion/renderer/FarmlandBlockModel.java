package ovh.astarivi.farmexpansion.renderer;

import net.minecraft.util.Identifier;
import ovh.astarivi.farmexpansion.FarmExpansion;
import ovh.astarivi.farmexpansion.block.farmland.FarmlandBlockEntity;

import software.bernie.geckolib.model.GeoModel;


public class FarmlandBlockModel extends GeoModel<FarmlandBlockEntity> {
    @Override
    public Identifier getModelResource(FarmlandBlockEntity animatable) {
        return new Identifier(FarmExpansion.modId, "geo/farmland_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(FarmlandBlockEntity animatable) {
        return new Identifier(FarmExpansion.modId, "textures/block/cfarmland_u.png");
    }

    @Override
    public Identifier getAnimationResource(FarmlandBlockEntity animatable) {
        return new Identifier(FarmExpansion.modId, "animations/farmland_block.animation.json");
    }
}
