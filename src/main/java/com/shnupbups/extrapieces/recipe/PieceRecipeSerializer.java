package com.shnupbups.extrapieces.recipe;

import com.google.gson.JsonObject;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;

import java.util.Map;

public class PieceRecipeSerializer implements RecipeSerializer<PieceRecipe> {

	public PieceRecipe read(Identifier id, JsonObject json) {
		String group = JsonHelper.getString(json, "group", "");
		Map<String, PieceIngredient> keyMap = PieceRecipe.getComponents(JsonHelper.getObject(json, "key"));
		String[] pattern = PieceRecipe.combinePattern(PieceRecipe.getPattern(JsonHelper.getArray(json, "pattern")));
		int width = pattern[0].length();
		int height = pattern.length;
		DefaultedList<PieceIngredient> inputs = PieceRecipe.getIngredients(pattern, keyMap, width, height);
		PieceStack pieceStack = PieceRecipe.getPieceStack(JsonHelper.getObject(json, "result"));
		return new PieceRecipe(id, group, width, height, inputs, pieceStack);
	}

	public PieceRecipe read(Identifier id, PacketByteBuf buf) {
		int width = buf.readVarInt();
		int height = buf.readVarInt();
		String group = buf.readString(32767);
		DefaultedList<PieceIngredient> inputs = DefaultedList.create(width * height, PieceIngredient.EMPTY);

		for(int i = 0; i < inputs.size(); ++i) {
			inputs.set(i, PieceIngredient.fromPacket(buf));
		}

		PieceStack pieceType = PieceStack.readPieceStack(buf);
		return new PieceRecipe(id, group, width, height, inputs, pieceType);
	}

	public void write(PacketByteBuf buf, PieceRecipe recipe) {

	}
}
