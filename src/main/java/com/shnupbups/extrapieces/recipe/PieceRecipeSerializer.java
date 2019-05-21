package com.shnupbups.extrapieces.recipe;

import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;

import java.util.Map;

public class PieceRecipeSerializer implements RecipeSerializer<PieceRecipe> {

	public PieceRecipe read(Identifier id, JsonObject json) {
		try {
			String group = JsonHelper.getString(json, "group", "");
			Map<String, PieceIngredient> keyMap = PieceRecipe.getComponents(JsonHelper.getObject(json, "key"));
			String[] pattern = PieceRecipe.combinePattern(PieceRecipe.getPattern(JsonHelper.getArray(json, "pattern")));
			int width = pattern[0].length();
			int height = pattern.length;
			DefaultedList<PieceIngredient> inputs = PieceRecipe.getIngredients(pattern, keyMap, width, height);
			PieceStack pieceStack = PieceRecipe.getPieceStack(JsonHelper.getObject(json, "result"));
			System.out.println("EXTRA PIECES DEBUG! Read from JSON! id: "+id.toString());
			for(PieceIngredient pi:inputs) {
				System.out.println(pi.toString()+" / "+pi.toString(Blocks.ACACIA_LOG));
			}
			return new PieceRecipe(id, group, width, height, inputs, pieceStack);
		} catch(Exception e) {
			System.out.println("BIG OL' EXCEPTION READING PieceRecipe FROM JSON!");
			System.out.println("ID: "+ id.toString());
			System.out.println("JSON: "+json.toString());
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}

	public PieceRecipe read(Identifier id, PacketByteBuf buf) {
		int width = buf.readVarInt();
		int height = buf.readVarInt();
		String group = buf.readString(32767);
		DefaultedList<PieceIngredient> inputs = DefaultedList.create(width * height, PieceIngredient.EMPTY);

		for(int i = 0; i < inputs.size(); ++i) {
			inputs.set(i, PieceIngredient.fromPacket(buf));
		}

		PieceStack pieceType = PieceStack.read(buf);
		System.out.println("EXTRA PIECES DEBUG! Read from packet!");
		return new PieceRecipe(id, group, width, height, inputs, pieceType);
	}

	public void write(PacketByteBuf buf, PieceRecipe recipe) {
		buf.writeVarInt(recipe.width);
		buf.writeVarInt(recipe.height);
		buf.writeString(recipe.group);
		for(int i = 0; i < recipe.inputs.size(); ++i) {
			recipe.inputs.get(i).write(buf);
		}
		recipe.output.write(buf);
		System.out.println("EXTRA PIECES DEBUG! Write to packet!");
	}
}
