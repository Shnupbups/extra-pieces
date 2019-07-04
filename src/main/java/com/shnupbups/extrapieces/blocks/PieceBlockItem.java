package com.shnupbups.extrapieces.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class PieceBlockItem extends BlockItem {

	private final PieceBlock pb;

	public PieceBlockItem(PieceBlock block_1, Item.Settings item$Settings_1) {
		super(block_1.getBlock(), item$Settings_1);
		pb = block_1;
	}

	public PieceBlock getPieceBlock() {
		return pb;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName(ItemStack stack) {
		return new TranslatableText(getPieceBlock().getType().getTranslationKey(), new TranslatableText(getPieceBlock().getSet().getTranslationKey()));
	}
}
