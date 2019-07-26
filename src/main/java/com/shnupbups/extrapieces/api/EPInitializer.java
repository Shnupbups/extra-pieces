package com.shnupbups.extrapieces.api;

import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

/**
 * For mods to use to add new piece types and other stuff.
 */
public interface EPInitializer {
	/**
	 * Called after the config is loaded.<br>
	 * But before any piece packs have been loaded.<br>
	 *<br>
	 * Create a {@link PieceType} and register it with {@link PieceTypes#register(PieceType)}
	 */
	void onInitialize();

	/**
	 * Called after all pieces blocks have been registered.<br>
	 * Also after recipes, loot tables and tags have been added.<br>
	 * Use this to register any data in the Artifice datapack.
	 *
	 * @param data The datapack builder
	 */
	void addData(ArtificeResourcePack.ServerResourcePackBuilder data);
}
