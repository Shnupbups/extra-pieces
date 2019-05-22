package com.shnupbups.extrapieces.recipe;

import com.google.gson.*;
import com.shnupbups.extrapieces.PieceSet;
import com.shnupbups.extrapieces.PieceType;
import com.shnupbups.extrapieces.blocks.ExtraPiece;
import net.minecraft.block.Block;
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

public class PieceIngredient implements Predicate<PieceStack> {
	private static final Predicate<? super PieceIngredient.Entry> NON_EMPTY = (ingredient$Entry_1) -> {
		return !ingredient$Entry_1.getPieces().stream().allMatch(PieceStack::isEmpty);
	};
	public static final PieceIngredient EMPTY = new PieceIngredient(Stream.empty());
	private PieceStack[] pieceArray;
	private final PieceIngredient.Entry[] entries;

	private PieceIngredient(Stream<? extends PieceIngredient.Entry> stream_1) {
		this.entries = (PieceIngredient.Entry[])stream_1.filter(NON_EMPTY).toArray((int_1) -> {
			return new PieceIngredient.Entry[int_1];
		});
		//System.out.println("EXTRA PIECES DEBUG! new PieceIngredient! "+this.toString());
	}

	public void write(PacketByteBuf buf) {
		this.createPieceArray();
		buf.writeVarInt(this.pieceArray.length);

		for(int int_1 = 0; int_1 < this.pieceArray.length; ++int_1) {
			this.pieceArray[int_1].write(buf);
		}

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
			throw new JsonSyntaxException("Piece cannot be null");
		}
	}

	public static PieceIngredient fromPacket(PacketByteBuf buf) {
		int int_1 = buf.readVarInt();
		return ofEntries(Stream.generate(() -> {
			return new PieceIngredient.Entry(PieceType.readPieceType(buf));
		}).limit((long)int_1));
	}

	public static PieceIngredient.Entry entryFromJson(JsonObject jsonObject_1) {
		Identifier id;
		if (jsonObject_1.has("piece")) {
			id = new Identifier(JsonHelper.getString(jsonObject_1, "piece"));
			PieceType pieceType = (PieceType) PieceType.getTypeOrEmpty(id).orElseThrow(() -> {
				return new JsonSyntaxException("Unknown piece '" + id + "'");
			});
			return new PieceIngredient.Entry(pieceType);
		} else {
			throw new JsonParseException("An ingredient entry needs a piece type");
		}
	}

	public boolean isEmpty() {
		return this.entries.length == 0;
	}

	public boolean test(PieceStack stack) {
		if (stack.isEmpty()) {
			return this.isEmpty();
		} else if (!isEmpty()) {
			this.createPieceArray();
			PieceStack[] pieceArray = this.pieceArray;

			for(PieceStack p:pieceArray) {
				if (p.typeEquals(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean test(ItemStack stack) {
		if(stack.isEmpty()) {
			return this.isEmpty();
		}
		if(stack.getItem() instanceof BlockItem) {
			PieceType type = PieceType.getType(stack);
			if(type!=null) {
				for(PieceStack p:pieceArray) {
					if(p.getType().equals(type)) return true;
				}
			}
		}
		return false;
	}

	private void createPieceArray() {
		if (this.pieceArray == null) {
			this.pieceArray = (PieceStack[]) Arrays.stream(this.entries).flatMap((ingredient$Entry_1) -> {
				return ingredient$Entry_1.getPieces().stream();
			}).distinct().toArray((int_1) -> {
				return new PieceStack[int_1];
			});
		}

	}

	static class Entry {
		private final PieceStack piece;

		private Entry(PieceStack piece) {
			this.piece = piece;
		}
		private Entry(PieceType piece) {this.piece = new PieceStack(piece);}

		public Collection<PieceStack> getPieces() {
			return Collections.singleton(this.piece);
		}

		public JsonObject toJson() {
			JsonObject jsonObject_1 = new JsonObject();
			jsonObject_1.addProperty("piece", piece.getType().getId().toString());
			return jsonObject_1;
		}
	}

	public String toString() {
		createPieceArray();
		String s = "PieceIngredient{types: ";
		for(PieceStack p:pieceArray) {
			s=s+p.getType().getId()+", ";
		}
		s=s.substring(0,s.length()-2);
		s+="}";
		return s;
	}

	public String toString(Block base) {
		createPieceArray();
		String s = "PieceIngredient{types: ";
		for(PieceStack p:pieceArray) {
			s=s+p.toItemStack(base).getTranslationKey()+", ";
		}
		s=s.substring(0,s.length()-2);
		s+="}";
		return s;
	}
}
