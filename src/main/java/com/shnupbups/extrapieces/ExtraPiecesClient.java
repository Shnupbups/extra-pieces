package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.register.ModModels;
import com.shnupbups.extrapieces.register.ModRenderLayers;
import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ClientModInitializer;

public class ExtraPiecesClient implements ClientModInitializer {

	public void onInitializeClient() {
		//try {
		Artifice.registerAssets(ExtraPieces.getID("ep_assets"), assets -> {
			ModModels.init(assets);
			assets.setDescription("Assets necessary for Extra Pieces.");
		})/*.dumpResources(FabricLoader.getInstance().getConfigDirectory().getParent()+"/dump")*/;
		/*} catch(Exception e) {
			ExtraPieces.log("BIG OOF: "+e.getMessage());
		}*/

		ModRenderLayers.init();
	}
}
