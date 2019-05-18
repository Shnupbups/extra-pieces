package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.recipe.PieceRecipe;
import com.shnupbups.extrapieces.recipe.PieceRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
	public static RecipeType<PieceRecipe> PIECE_RECIPE;
	public static RecipeSerializer<PieceRecipe> PIECE_RECIPE_SERIALIZER;

	public static void init() {
		PIECE_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier("extrapieces","piece_crafting"), new RecipeType<PieceRecipe>() {
			public String toString() {
				return "piece_crafting";
			}
		});
		PIECE_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("extrapieces","piece_crafting"), new PieceRecipeSerializer());
	}
}
