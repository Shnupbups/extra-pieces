package com.shnupbups.extrapieces.recipe;

import com.google.gson.*;
import com.shnupbups.extrapieces.PieceSet;
import com.shnupbups.extrapieces.PieceType;
import com.shnupbups.extrapieces.blocks.ExtraPiece;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PieceIngredient implements Predicate<PieceType> {
	private static final Predicate<? super PieceIngredient.Entry> NON_EMPTY = (ingredient$Entry_1) -> {
		return !(ingredient$Entry_1.getPieces()==null);
	};
	public static final PieceIngredient EMPTY = new PieceIngredient(Stream.empty());
	private PieceType[] pieceArray;
	private final PieceIngredient.Entry[] entries;

	private PieceIngredient(Stream<? extends PieceIngredient.Entry> stream_1) {
		this.entries = (PieceIngredient.Entry[])stream_1.filter(NON_EMPTY).toArray((int_1) -> {
			return new PieceIngredient.Entry[int_1];
		});
	}

	public static PieceIngredient ofEntries(Stream<? extends PieceIngredient.Entry> stream_1) {
		PieceIngredient ingredient_1 = new PieceIngredient(stream_1);
		return ingredient_1.entries.length == 0 ? EMPTY : ingredient_1;
	}

	public static PieceIngredient fromJson(JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			if (jsonElement.isJsonObject()) {
				return ofEntries(Stream.of(entryFromJson(jsonElement.getAsJsonObject())));
			} else if (jsonElement.isJsonArray()) {
				JsonArray jsonArray_1 = jsonElement.getAsJsonArray();
				if (jsonArray_1.size() == 0) {
					throw new JsonSyntaxException("Piece array cannot be empty, at least one type must be defined");
				} else {
					return ofEntries(StreamSupport.stream(jsonArray_1.spliterator(), false).map((jsonElement_1x) -> {
						return entryFromJson(JsonHelper.asObject(jsonElement_1x, "piece"));
					}));
				}
			} else {
				throw new JsonSyntaxException("Expected piece to be object or array of objects");
			}
		} else {
			throw new JsonSyntaxException("Item cannot be null");
		}
	}

	public static PieceIngredient fromPacket(PacketByteBuf buf) {
		int int_1 = buf.readVarInt();
		return ofEntries(Stream.generate(() -> {
			return new PieceIngredient.PieceEntry(PieceType.readPieceType(buf));
		}).limit((long)int_1));
	}

	public static PieceIngredient.Entry entryFromJson(JsonObject jsonObject_1) {
		Identifier id;
		if (jsonObject_1.has("piece")) {
			id = new Identifier(JsonHelper.getString(jsonObject_1, "piece"));
			PieceType pieceType = (PieceType) PieceType.getTypeOrEmpty(id).orElseThrow(() -> {
				return new JsonSyntaxException("Unknown piece '" + id + "'");
			});
			return new PieceIngredient.PieceEntry(pieceType);
		} else {
			throw new JsonParseException("An ingredient entry needs a piece type");
		}
	}

	public boolean test(PieceType type) {
		if (type == null) {
			return false;
		} else if (this.entries.length == 0) {
			return false;
		} else {
			this.createPieceArray();
			PieceType[] pieceArray = this.pieceArray;
			int pieceArrayLength = pieceArray.length;

			for(int i = 0; i < pieceArrayLength; ++i) {
				PieceType type2 = pieceArray[i];
				if (type2.equals(type)) {
					return true;
				}
			}

			return false;
		}
	}

	public boolean test(ItemStack stack) {
		if(stack.getItem() instanceof BlockItem) {
			BlockItem blockItem = (BlockItem)stack.getItem();
			if(blockItem.getBlock() instanceof ExtraPiece || PieceSet.hasSet(blockItem.getBlock())) {
				return true;
			}
		} return false;
	}

	private void createPieceArray() {
		if (this.pieceArray == null) {
			this.pieceArray = (PieceType[]) Arrays.stream(this.entries).flatMap((ingredient$Entry_1) -> {
				return ingredient$Entry_1.getPieces().stream();
			}).distinct().toArray((int_1) -> {
				return new PieceType[int_1];
			});
		}

	}

	static class PieceEntry implements PieceIngredient.Entry {
		private final PieceType piece;

		private PieceEntry(PieceType piece) {
			this.piece = piece;
		}

		public Collection<PieceType> getPieces() {
			return Collections.singleton(this.piece);
		}

		public JsonObject toJson() {
			JsonObject jsonObject_1 = new JsonObject();
			jsonObject_1.addProperty("piece", piece.getId().toString());
			return jsonObject_1;
		}
	}

	interface Entry {
		Collection<PieceType> getPieces();

		JsonObject toJson();
	}
}
