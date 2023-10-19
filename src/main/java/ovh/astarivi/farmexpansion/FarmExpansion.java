package ovh.astarivi.farmexpansion;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ovh.astarivi.farmexpansion.manager.BlockManager;
import ovh.astarivi.farmexpansion.manager.ItemManager;
import software.bernie.geckolib.GeckoLib;


public class FarmExpansion implements ModInitializer {
	public static final String modId = "farmexpansion";
    public static final Logger LOGGER = LoggerFactory.getLogger("farmexpansion");

	@Override
	public void onInitialize() {
		BlockManager.initialize();
		ItemManager.initialize();
		GeckoLib.initialize();

		LOGGER.info("Hello Fabric world!");
	}
}