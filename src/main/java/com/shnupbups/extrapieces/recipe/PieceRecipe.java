package com.shnupbups.extrapieces.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import com.shnupbups.extrapieces.PieceSet;
import com.shnupbups.extrapieces.PieceType;
import com.shnupbups.extrapieces.register.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PieceRecipe extends SpecialCraftingRecipe {

	public PieceStack output;
	public DefaultedList<PieceIngredient> inputs;
	public String group;
	public int width,height;

	public PieceRecipe(Identifier id, String group, int width, int height, DefaultedList<PieceIngredient> inputs, PieceStack output) {
		super(id);
		this.output=output;
		this.width=width;
		this.height=height;
		this.inputs=inputs;
		this.group=group;
	}

	public boolean matches(CraftingInventory c, World world) {
		ItemStack base = getBase(c);
		if(base==null||base.equals(ItemStack.EMPTY)||base.getItem()==Items.AIR) return false;
		else {
			if (PieceSet.hasSet(getBaseAsBlock(c))&&PieceSet.getSet(getBaseAsBlock(c)).hasPiece(output.getType())) {
				return properMatches(c,world);
			}
		}
		return false;
	}

	public boolean properMatches(CraftingInventory craftingInventory_1, World world_1) {
		for(int int_1 = 0; int_1 <= craftingInventory_1.getWidth() - this.width; ++int_1) {
			for(int int_2 = 0; int_2 <= craftingInventory_1.getHeight() - this.height; ++int_2) {
				if (this.matchesSmall(craftingInventory_1, int_1, int_2, true)) {
					return true;
				}
				if (this.matchesSmall(craftingInventory_1, int_1, int_2, false)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean matchesSmall(CraftingInventory craftingInventory_1, int int_1, int int_2, boolean boolean_1) {
		for(int int_3 = 0; int_3 < craftingInventory_1.getWidth(); ++int_3) {
			for(int int_4 = 0; int_4 < craftingInventory_1.getHeight(); ++int_4) {
				int int_5 = int_3 - int_1;
				int int_6 = int_4 - int_2;
				PieceIngredient ingredient_1 = PieceIngredient.EMPTY;
				if (int_5 >= 0 && int_6 >= 0 && int_5 < this.width && int_6 < this.height) {
					if (boolean_1) {
						ingredient_1 = (PieceIngredient)this.inputs.get(this.width - int_5 - 1 + int_6 * this.width);
					} else {
						ingredient_1 = (PieceIngredient)this.inputs.get(int_5 + int_6 * this.width);
					}
				}

				if (!ingredient_1.test(craftingInventory_1.getInvStack(int_3 + int_4 * craftingInventory_1.getWidth()))) {
					return false;
				}
			}
		}
		return true;
	}

	public ItemStack getBase(CraftingInventory c) {
		ItemStack base = ItemStack.EMPTY;
		for(int i = 0; i<c.getInvSize(); i++) {
			ItemStack stack = c.getInvStack(i);
			if(stack!=null&&!stack.isEmpty()) {
				if(base==null||base.equals(ItemStack.EMPTY)) {
					base = new ItemStack(StackUtils.getBase(stack));
				} else {
					if(!StackUtils.getBase(stack).asItem().equals(base.getItem())) {
						return ItemStack.EMPTY;
					}
				}
			}
		}
		return base;
	}

	public Block getBaseAsBlock(CraftingInventory c) {
		ItemStack stack = getBase(c);
		return ((BlockItem)stack.getItem()).getBlock();
	}

	public ItemStack craft(CraftingInventory c) {
		return output.toItemStack(getBaseAsBlock(c));
	}

	public boolean fits(int width, int height) {
		return this.width<=width&&this.height<=height;
	}

	public static Map<String, PieceIngredient> getComponents(JsonObject json) {
		Map<String, PieceIngredient> components = Maps.newHashMap();
		Iterator var2 = json.entrySet().iterator();

		while(var2.hasNext()) {
			Map.Entry<String, JsonElement> map$Entry_1 = (Map.Entry)var2.next();
			if (((String)map$Entry_1.getKey()).length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + (String)map$Entry_1.getKey() + "' is an invalid symbol (must be 1 character only).");
			}

			if (" ".equals(map$Entry_1.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			components.put(map$Entry_1.getKey(), PieceIngredient.fromJson((JsonElement)map$Entry_1.getValue()));
		}

		components.put(" ", PieceIngredient.EMPTY);
		return components;
	}

	public static DefaultedList<PieceIngredient> getIngredients(String[] strings_1, Map<String, PieceIngredient> map_1, int int_1, int int_2) {
		DefaultedList<PieceIngredient> defaultedList_1 = DefaultedList.create(int_1 * int_2, PieceIngredient.EMPTY);
		Set<String> set_1 = Sets.newHashSet(map_1.keySet());
		set_1.remove(" ");

		for(int int_3 = 0; int_3 < strings_1.length; ++int_3) {
			for(int int_4 = 0; int_4 < strings_1[int_3].length(); ++int_4) {
				String string_1 = strings_1[int_3].substring(int_4, int_4 + 1);
				PieceIngredient ingredient_1 = (PieceIngredient)map_1.get(string_1);
				if (ingredient_1 == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + string_1 + "' but it's not defined in the key");
				}

				set_1.remove(string_1);
				defaultedList_1.set(int_4 + int_1 * int_3, ingredient_1);
			}
		}

		if (!set_1.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set_1);
		} else {
			return defaultedList_1;
		}
	}

	public static String[] combinePattern(String... strings_1) {
		int int_1 = Integer.MAX_VALUE;
		int int_2 = 0;
		int int_3 = 0;
		int int_4 = 0;

		for(int int_5 = 0; int_5 < strings_1.length; ++int_5) {
			String string_1 = strings_1[int_5];
			int_1 = Math.min(int_1, findNextIngredient(string_1));
			int int_6 = findNextIngredientReverse(string_1);
			int_2 = Math.max(int_2, int_6);
			if (int_6 < 0) {
				if (int_3 == int_5) {
					++int_3;
				}

				++int_4;
			} else {
				int_4 = 0;
			}
		}

		if (strings_1.length == int_4) {
			return new String[0];
		} else {
			String[] strings_2 = new String[strings_1.length - int_4 - int_3];

			for(int int_7 = 0; int_7 < strings_2.length; ++int_7) {
				strings_2[int_7] = strings_1[int_7 + int_3].substring(int_1, int_2 + 1);
			}

			return strings_2;
		}
	}

	private static int findNextIngredient(String string_1) {
		int int_1;
		for(int_1 = 0; int_1 < string_1.length() && string_1.charAt(int_1) == ' '; ++int_1) {
		}

		return int_1;
	}

	private static int findNextIngredientReverse(String string_1) {
		int int_1;
		for(int_1 = string_1.length() - 1; int_1 >= 0 && string_1.charAt(int_1) == ' '; --int_1) {
		}

		return int_1;
	}

	public static String[] getPattern(JsonArray jsonArray_1) {
		String[] strings_1 = new String[jsonArray_1.size()];
		if (strings_1.length > 3) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
		} else if (strings_1.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for(int int_1 = 0; int_1 < strings_1.length; ++int_1) {
				String string_1 = JsonHelper.asString(jsonArray_1.get(int_1), "pattern[" + int_1 + "]");
				if (string_1.length() > 3) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
				}

				if (int_1 > 0 && strings_1[0].length() != string_1.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}

				strings_1[int_1] = string_1;
			}

			return strings_1;
		}
	}

	public static PieceStack getPieceStack(JsonObject jsonObject_1) {
		String id = JsonHelper.getString(jsonObject_1, "piece");
		PieceType pieceType = (PieceType) PieceType.getTypeOrEmpty(new Identifier(id)).orElseThrow(() -> {
			return new JsonSyntaxException("Unknown piece '" + id + "'");
		});
		if (jsonObject_1.has("data")) {
			throw new JsonParseException("Disallowed data tag found");
		} else {
			int count = JsonHelper.getInt(jsonObject_1, "count", 1);
			return new PieceStack(pieceType,count);
		}
	}

	public RecipeSerializer getSerializer() {
		return ModRecipes.PIECE_RECIPE_SERIALIZER;
	}

	public String toString() {
		String s = "PieceRecipe{ width: "+width+" height: "+height+" group: "+group+" output: "+output.getType().getId()+"*"+output.getCount()+" inputs: ";
		for(PieceIngredient i:inputs) {
			s+=i.toString()+", ";
		}
		s=s.substring(0,s.length()-2);
		s+="}";
		return s;
	}

	public String toString(Block base) {
		String s = "PieceRecipe{ width: "+width+" height: "+height+" group: "+group+" output: "+PieceSet.getPiece(base,output.getType())+"*"+output.getCount()+" inputs: ";
		for(PieceIngredient i:inputs) {
			s+=i.toString(base)+", ";
		}
		s=s.substring(0,s.length()-2);
		s+="}";
		return s;
	}
}
