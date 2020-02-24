package com.shnupbups.extrapieces.core;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.FakePieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.recipe.PieceRecipe;
import com.shnupbups.extrapieces.recipe.StonecuttingPieceRecipe;
import com.shnupbups.extrapieces.recipe.WoodmillingPieceRecipe;
import com.shnupbups.extrapieces.register.ModConfigs;
import com.shnupbups.extrapieces.register.ModItemGroups;
import com.shnupbups.extrapieces.register.ModLootTables;
import com.shnupbups.extrapieces.register.ModRecipes;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class PieceSet {
	public static final HashSet<PieceType> NO_SLAB;
	public static final HashSet<PieceType> NO_SLAB_OR_STAIRS;
	public static final HashSet<PieceType> NO_SLAB_STAIRS_OR_WALL;
	public static final HashSet<PieceType> JUST_EXTRAS_AND_WALL;
	public static final HashSet<PieceType> JUST_EXTRAS_AND_FENCE_GATE;
	public static final HashSet<PieceType> NO_LAYER;

	static {
		NO_SLAB = new HashSet<>(PieceTypes.getTypesNoBase());
		NO_SLAB.remove(PieceTypes.SLAB);

		NO_SLAB_OR_STAIRS = new HashSet<>(NO_SLAB);
		NO_SLAB_OR_STAIRS.remove(PieceTypes.STAIRS);

		NO_SLAB_STAIRS_OR_WALL = new HashSet<>(NO_SLAB_OR_STAIRS);
		NO_SLAB_STAIRS_OR_WALL.remove(PieceTypes.WALL);

		JUST_EXTRAS_AND_WALL = new HashSet<>(NO_SLAB_OR_STAIRS);
		JUST_EXTRAS_AND_WALL.remove(PieceTypes.FENCE);
		JUST_EXTRAS_AND_WALL.remove(PieceTypes.FENCE_GATE);

		JUST_EXTRAS_AND_FENCE_GATE = new HashSet<>(NO_SLAB_STAIRS_OR_WALL);
		JUST_EXTRAS_AND_FENCE_GATE.remove(PieceTypes.FENCE);

		NO_LAYER = new HashSet<>(PieceTypes.getTypesNoBase());
		NO_LAYER.remove(PieceTypes.LAYER);
	}

	private final Block base;
	private final String name;
	private final String originalName;
	private PieceType[] genTypes;
	private Map<PieceType, PieceBlock> pieces = new HashMap<>();
	private boolean registered = false;
	private boolean stonecuttable;
	private boolean woodmillable;
	private ArrayList<PieceType> uncraftable = new ArrayList<>();
	private Identifier mainTexture;
	private Identifier topTexture;
	private Identifier bottomTexture;
	private boolean opaque;
	private boolean includeMode = false;

	PieceSet(Block base, String name, Collection<PieceType> types) {
		this(base, name, types, false);
	}

	PieceSet(Block base, String name) {
		this(base, name, false);
	}

	PieceSet(Block base, String name, boolean isDefault) {
		this(base, name, PieceTypes.getTypesNoBase(), isDefault);
	}

	PieceSet(Block base, String name, Collection<PieceType> types, boolean isDefault) {
		if (base == Blocks.AIR) ExtraPieces.log("PieceSet " + name + " has air as its base! This is VERY BAD!");
		this.base = base;
		this.originalName = name.toLowerCase();
		this.name = PieceSets.getNewSetName(originalName);
		Identifier id = Registry.BLOCK.getId(base);
		this.mainTexture = new Identifier(id.getNamespace(), "block/" + id.getPath());
		this.topTexture = mainTexture;
		this.bottomTexture = topTexture;
		this.opaque = base.getDefaultState().isOpaque();
		this.genTypes = types.toArray(new PieceType[types.size()]);
		this.stonecuttable = isNormallyStonecuttable();
		this.woodmillable = isNormallyWoodmillable();
		if (isDefault) PieceSets.registerDefaultSet(this);
		else PieceSets.registerSet(this);
	}

	public PieceSet setOpaque() {
		this.opaque = true;
		return this;
	}

	public PieceSet setTransparent() {
		this.opaque = false;
		return this;
	}

	public PieceSet setTexture(Identifier id) {
		Identifier newId = new Identifier(id.getNamespace(), (id.getPath().contains("block/") ? id.getPath() : "block/" + id.getPath()));
		this.mainTexture = newId;
		this.topTexture = newId;
		this.bottomTexture = newId;
		return this;
	}

	public PieceSet setTopTexture(Identifier id) {
		Identifier newId = new Identifier(id.getNamespace(), (id.getPath().contains("block/") ? id.getPath() : "block/" + id.getPath()));
		this.topTexture = newId;
		this.bottomTexture = newId;
		return this;
	}

	public PieceSet setBottomTexture(Identifier id) {
		Identifier newId = new Identifier(id.getNamespace(), (id.getPath().contains("block/") ? id.getPath() : "block/" + id.getPath()));
		this.bottomTexture = newId;
		return this;
	}

	public PieceSet setTexture(String id) {
		return setTexture(new Identifier(id));
	}

	public boolean isOpaque() {
		return opaque;
	}

	public PieceSet setOpaque(boolean opaque) {
		this.opaque = opaque;
		return this;
	}

	public boolean isTransparent() {
		return !isOpaque();
	}

	public Identifier getMainTexture() {
		return mainTexture;
	}

	public Identifier getTopTexture() {
		return topTexture;
	}

	public PieceSet setTopTexture(String id) {
		return setTopTexture(new Identifier(id));
	}

	public Identifier getBottomTexture() {
		return bottomTexture;
	}

	public PieceSet setBottomTexture(String id) {
		return setBottomTexture(new Identifier(id));
	}

	public boolean hasCustomTexture() {
		return hasBottomTexture() || hasTopTexture() || hasMainTexture();
	}

	public boolean hasMainTexture() {
		Identifier id = Registry.BLOCK.getId(base);
		Identifier def = new Identifier(id.getNamespace(), "block/" + id.getPath());
		return !getMainTexture().equals(def);
	}

	public boolean hasTopTexture() {
		return !getTopTexture().equals(getMainTexture());
	}

	public boolean hasBottomTexture() {
		return !getBottomTexture().equals(getTopTexture());
	}

	/**
	 * Adds a vanilla (or just not-generated) block as a piece to this set.
	 *
	 * @param type  The piece type of the block
	 * @param block The block to add
	 * @return This set
	 */
	public PieceSet addVanillaPiece(PieceType type, Block block) {
		FakePieceBlock fpb = new FakePieceBlock(block, type, this);
		PieceSets.registerVanillaPiece(block, fpb);
		this.excludePiece(type);
		pieces.put(type, fpb);
		setUncraftable(type);
		//System.out.println("ADDING VANILLA PIECE! "+block+" as type "+type+" to set "+getName()+" with base "+getBase());
		return this;
	}

	public boolean isVanillaPiece(PieceType type) {
		return pieces.get(type) instanceof FakePieceBlock && PieceSets.isVanillaPiece(pieces.get(type).getBlock());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PieceSet{ base: ").append(getBase()).append(", pieces: [");

		for (Map.Entry<PieceType, PieceBlock> piece : pieces.entrySet()) {
			sb.append("{").append(piece.getKey().toString()).append(" = ").append(piece.getValue());

			if (isVanillaPiece(piece.getKey())) sb.append(", Vanilla Piece!");
			else if (!isCraftable(piece.getKey())) sb.append(", Uncraftable!");

			sb.append("} , ");
		}

		sb.delete(sb.length() - 2, sb.length());
		sb.append("] ");
		if (isStonecuttable()) sb.append(", Stonecuttable! ");
		if (isTransparent()) sb.append(", Transparent! ");
		if (hasMainTexture()) sb.append(", Main Texture = ").append(getMainTexture()).append(" ");
		if (hasTopTexture()) sb.append(", Top Texture = ").append(getTopTexture()).append(" ");
		if (hasBottomTexture()) sb.append(", Bottom Texture = ").append(getBottomTexture()).append(" ");
		sb.append("}");
		return sb.toString();
	}

	public boolean isStonecuttable() {
		return stonecuttable;
	}

	public PieceSet setStonecuttable(boolean stonecuttable) {
		this.stonecuttable = stonecuttable;
		return this;
	}

	public boolean isNormallyStonecuttable() {
		return (base.getDefaultState().getMaterial().equals(Material.STONE) || base.getDefaultState().getMaterial().equals(Material.METAL));
	}

	public boolean isWoodmillable() {
		return woodmillable;
	}

	public PieceSet setWoodmillable(boolean woodmillable) {
		this.woodmillable = woodmillable;
		return this;
	}

	public boolean isNormallyWoodmillable() {
		return base.getDefaultState().getMaterial().equals(Material.WOOD);
	}

	public boolean isCraftable(PieceType type) {
		return !uncraftable.contains(type);
	}

	public PieceSet setUncraftable(PieceType type) {
		uncraftable.add(type);
		return this;
	}

	/**
	 * Creates the instances of each {@link PieceType} in this {@link PieceSet}.
	 *
	 * @return This {@link PieceSet} with all {@link PieceType}s generated.
	 */
	public PieceSet generate() {
		for (PieceType p : genTypes) {
			if (!hasPiece(p)) {
				pieces.put(p, p.getNew(this));
			}
		}
		return this;
	}

	/**
	 * Registers each {@link PieceType} in this {@link PieceSet} to the {@link Registry}.<br>
	 * If {@link #isGenerated()} returns {@code false}, runs {@link #generate()}.
	 *
	 * @return This {@link PieceSet}
	 * @throws IllegalStateException If a {@link PieceSet} has already been registered with the same base {@link Block}
	 */
	public PieceSet register(ArtificeResourcePack.ServerResourcePackBuilder data) {
		if (isRegistered())
			return this;
		if (!isGenerated()) generate();
		for (PieceType type : genTypes) {
			PieceBlock block = pieces.get(type);

			Identifier id = new Identifier(type.getId().getNamespace(), type.getBlockId(getName()));

			Registry.register(Registry.BLOCK, id, block.getBlock());

			if (this.getBase() != Blocks.AIR) ModItemGroups.getItemGroup(block);

			BlockItem item = type.getBlockItem(block);
			item.appendBlocks(Item.BLOCK_ITEMS, item);
			Registry.register(Registry.ITEM, id, item);
		}
		registered = true;
		//System.out.println("DEBUG! PieceSet register: "+this.toString());
		return this;
	}

	/**
	 * Gets the name of this {@link PieceSet}.<br>
	 * Used for registry.
	 *
	 * @return The name of this {@link PieceSet}.
	 */
	public String getName() {
		return name;
	}

	public String getOriginalName() {
		return originalName;
	}

	/**
	 * Gets a {@link PieceType} from this {@link PieceSet}, if it exists.<br>
	 * If {@link #isGenerated()} returns {@code false}, runs {@link #generate()} first.
	 *
	 * @param piece The {@link PieceType} to get.
	 * @return The {@link PieceType} from this {@link PieceSet}, or null if no such PieceType exists.
	 */
	public Block getPiece(PieceType piece) {
		if (piece.equals(PieceTypes.BASE)) return getBase();
		if (!isGenerated()) generate();
		if (hasPiece(piece)) return pieces.get(piece).getBlock();
		return null;
	}

	/**
	 * Gets the {@link Block} which this {@link PieceSet} is based upon.
	 *
	 * @return The {@link Block} which this {@link PieceSet} is based upon.
	 */
	public Block getBase() {
		return base;
	}

	/**
	 * Gets whether this {@link PieceSet} has a {@link PieceType} of type {@code piece}.
	 *
	 * @param piece The {@link PieceType} to query for.
	 * @return Whether this {@link PieceSet} has a {@link PieceType} of type {@code piece}.
	 */
	public boolean hasPiece(PieceType piece) {
		return (pieces.containsKey(piece) || piece.equals(PieceTypes.BASE));
	}

	public HashSet<PieceType> getPieceTypes() {
		return new HashSet<>(pieces.keySet());
	}

	public HashSet<PieceBlock> getPieceBlocks() {
		return new HashSet<>(pieces.values());
	}

	public Map<PieceType, PieceBlock> getPieces() {
		return pieces;
	}

	/**
	 * Gets whether each {@link PieceType} for this {@link PieceSet} has been generated.<br>
	 * Generation is done with {@link #generate()}.
	 *
	 * @return Whether each {@link PieceType} for this {@link PieceSet} has been generated.
	 */
	public boolean isGenerated() {
		for (PieceType p : genTypes) {
			if (!pieces.containsKey(p)) return false;
		}
		return true;
	}

	/**
	 * Gets whether this {@link PieceSet}'s {@link PieceType}s have been added to the {@link Registry}.<br>
	 * Registration is done with register.
	 *
	 * @return Whether this {@link PieceSet}'s {@link PieceType}s have been added to the {@link Registry}.
	 */
	public boolean isRegistered() {
		return registered;
	}

	public List<PieceType> getGenTypes() {
		return Arrays.asList(genTypes);
	}

	public Set<PieceType> getVanillaTypes() {
		HashSet<PieceType> vt = new HashSet<>();
		List gt = Arrays.asList(genTypes);
		for (PieceType p : this.getPieces().keySet()) {
			if (!gt.contains(p)) {
				vt.add(p);
			}
		}
		return vt;
	}

	public Set<PieceType> getExcludedTypes() {
		HashSet<PieceType> et = new HashSet<>(PieceTypes.getTypesNoBase());
		et.removeAll(this.getGenTypes());
		return et;
	}

	public Set<PieceType> getUncraftableTypes() {
		HashSet<PieceType> uc = new HashSet<>(uncraftable);
		uc.removeAll(this.getVanillaTypes());
		return uc;
	}

	public PieceSet excludePiece(PieceType type) {
		List<PieceType> types = Arrays.asList(this.genTypes);
		HashSet<PieceType> newTypes = new HashSet<>(types);
		newTypes.remove(type);
		this.genTypes = newTypes.toArray(new PieceType[newTypes.size()]);
		return this;
	}

	public String getTranslationKey() {
		if (Language.getInstance().hasTranslation("pieceSet." + this.getName())) {
			return "pieceSet." + this.getName();
		} else if (Language.getInstance().hasTranslation("pieceSet." + this.getOriginalName())) {
			return "pieceSet." + this.getOriginalName();
		} else return this.getBase().getTranslationKey();
	}

	public PieceSet setInclude() {
		this.includeMode = true;
		return this;
	}

	public JsonObject toJson() {
		JsonObject ob = new JsonObject();
		ob.put("base", new JsonPrimitive(Registry.BLOCK.getId(this.getBase())));
		if (this.isStonecuttable() != this.isNormallyStonecuttable()) {
			ob.put("stonecuttable", new JsonPrimitive(this.isStonecuttable()));
		}
		if (this.isWoodmillable() != this.isNormallyWoodmillable()) {
			ob.put("woodmillable", new JsonPrimitive(this.isWoodmillable()));
		}
		if (this.isOpaque() != this.getBase().getDefaultState().isOpaque()) {
			ob.put("opaque", new JsonPrimitive(this.isOpaque()));
		}
		if (this.hasCustomTexture()) {
			JsonObject tx = new JsonObject();
			if (hasMainTexture()) {
				tx.put("main", new JsonPrimitive(this.getMainTexture()));
			}
			if (hasTopTexture()) {
				tx.put("top", new JsonPrimitive(this.getTopTexture()));
			}
			if (hasBottomTexture()) {
				tx.put("bottom", new JsonPrimitive(this.getBottomTexture()));
			}
			ob.put("textures", tx);
		}
		if (!this.getVanillaTypes().isEmpty()) {
			JsonObject vp = new JsonObject();
			for (PieceType p : this.getVanillaTypes()) {
				vp.put(p.toString(), new JsonPrimitive(Registry.BLOCK.getId(this.getPiece(p))));
			}
			ob.put("vanilla_pieces", vp);
		}
		if (!this.getUncraftableTypes().isEmpty()) {
			JsonArray uc = new JsonArray();
			for (PieceType p : this.getUncraftableTypes()) {
				uc.add(new JsonPrimitive(p));
			}
			ob.put("uncraftable", uc);
		}
		if (!this.getExcludedTypes().isEmpty() && !includeMode) {
			JsonArray ex = new JsonArray();
			for (PieceType p : this.getExcludedTypes()) {
				if (!this.isVanillaPiece(p)) {
					ex.add(new JsonPrimitive(p));
				}
			}
			if (!ex.isEmpty()) ob.put("exclude", ex);
		}
		if (includeMode) {
			JsonArray in = new JsonArray();
			for (PieceType p : this.getGenTypes()) {
				in.add(new JsonPrimitive(p));
			}
			ob.put("include", in);
		}
		return ob;
	}

	public void addRecipes(ArtificeResourcePack.ServerResourcePackBuilder data) {
		for (PieceBlock pb : this.getPieceBlocks()) {
			if (isCraftable(pb.getType())) {
				int i = 0;
				for (PieceRecipe pr : pb.getType().getCraftingRecipes()) {
					if (pr.canAddForSet(this)) {
						Identifier bid = Registry.BLOCK.getId(pb.getBlock());
						if(!ModRecipes.checkIsAir(bid, this)) {
							Identifier id = ExtraPieces.getID(bid.getPath() + "_" + (i++));
							pr.add(data, id, this);
							ModRecipes.incrementRecipes();
						}
					}
				}

			}
			if (!isVanillaPiece(pb.getType()) && pb.getType() != PieceTypes.BASE && (isStonecuttable() || ModConfigs.everythingStonecuttable)) {
				Identifier bid = Registry.BLOCK.getId(pb.getBlock());
				if(!ModRecipes.checkIsAir(bid, this)) {
					Identifier id = ExtraPieces.getID(bid.getPath() + "_stonecutting");
					StonecuttingPieceRecipe r = pb.getType().getStonecuttingRecipe();
					if (r != null) {
						r.add(data, id, this);
						ModRecipes.incrementStonecuttingRecipes();
					}
				}
			}
			if (ExtraPieces.isWoodmillInstalled() && !isVanillaPiece(pb.getType()) && pb.getType() != PieceTypes.BASE && isWoodmillable()) {
				Identifier bid = Registry.BLOCK.getId(pb.getBlock());
				if(!ModRecipes.checkIsAir(bid, this)) {
					Identifier id = ExtraPieces.getID(bid.getPath() + "_woodmilling");
					WoodmillingPieceRecipe r = pb.getType().getWoodmillingRecipe();
					if (r != null) {
						r.add(data, id, this);
						ModRecipes.incrementWoodmillingRecipes();
					}
				}
			}
		}
		ModRecipes.addMiscRecipes(data, this);
	}

	public void addLootTables(ArtificeResourcePack.ServerResourcePackBuilder data) {
		for (PieceBlock pb : this.getPieceBlocks()) {
			if (!isVanillaPiece(pb.getType())) {
				pb.getType().addLootTable(data, pb);
				ModLootTables.incrementLootTables();
			}
		}
	}

	public void addRenderLayers() {
		for (PieceBlock pb : this.getPieceBlocks()) {
			if (!isVanillaPiece(pb.getType())) {
				BlockRenderLayerMap.INSTANCE.putBlock(pb.getBlock(), RenderLayers.getBlockLayer(pb.getBase().getDefaultState()));
			}
		}
	}

	public static class Builder {
		public final String name;
		public final Identifier base;
		public String requiredMod = "minecraft";
		public boolean built = false;
		public Boolean stonecuttable;
		public Boolean woodmillable;
		public Boolean opaque;
		public Identifier mainTexture;
		public Identifier topTexture;
		public Identifier bottomTexture;
		public boolean includeMode = false;
		public HashSet<PieceType> genTypes = PieceTypes.getTypesNoBase();
		public HashSet<PieceType> uncraftable = new HashSet<>();
		public HashMap<Identifier, Identifier> vanillaPieces = new HashMap<>();
		public String packName;

		public Builder(String name, JsonObject ob, String packName) {
			this.name = name;
			this.packName = packName;
			this.base = new Identifier(Objects.requireNonNull(ob.get(String.class, "base")));
			if (ob.containsKey("stonecuttable")) {
				this.stonecuttable = ob.get("stonecuttable").equals(JsonPrimitive.TRUE);
			}
			if (ob.containsKey("woodmillable")) {
				this.woodmillable = ob.get("woodmillable").equals(JsonPrimitive.TRUE);
			}
			if (ob.containsKey("opaque")) {
				this.opaque = ob.get("opaque").equals(JsonPrimitive.TRUE);
			}
			if (ob.containsKey("textures")) {
				JsonObject tx = ob.getObject("textures");
				if (tx.containsKey("main")) {
					this.mainTexture = new Identifier(Objects.requireNonNull(tx.get(String.class, "main")));
				}
				if (tx.containsKey("top")) {
					this.topTexture = new Identifier(Objects.requireNonNull(tx.get(String.class, "top")));
				}
				if (tx.containsKey("bottom")) {
					this.bottomTexture = new Identifier(Objects.requireNonNull(tx.get(String.class, "bottom")));
				}
			}
			if (ob.containsKey("vanilla_pieces")) {
				JsonObject vp = ob.getObject("vanilla_pieces");

				for (Map.Entry<String, JsonElement> entry : vp.entrySet()) {
					String value = vp.getMarshaller().marshall(String.class, entry.getValue());

					this.vanillaPieces.put(new Identifier(entry.getKey()), new Identifier(value));
				}
			}
			if (ob.containsKey("exclude")) {
				JsonArray ex = ob.get(JsonArray.class, "exclude");
				for (JsonElement je : ex) {
					JsonPrimitive jp = (JsonPrimitive) je;
					String s = jp.asString();
					PieceType pt = PieceTypes.getType(s);
					this.genTypes.remove(pt);
				}
			} else if (ob.containsKey("include")) {
				this.includeMode = true;
				this.genTypes.clear();
				JsonArray in = ob.get(JsonArray.class, "include");
				for (JsonElement je : in) {
					JsonPrimitive jp = (JsonPrimitive) je;
					String s = jp.asString();
					PieceType pt = PieceTypes.getType(s);
					this.genTypes.add(pt);
				}
			}
			if (ob.containsKey("uncraftable")) {
				JsonArray uc = ob.get(JsonArray.class, "uncraftable");
				for (JsonElement je : uc) {
					JsonPrimitive jp = (JsonPrimitive) je;
					String s = jp.asString();
					PieceType pt = PieceTypes.getType(s);
					this.uncraftable.add(pt);
				}
			}
			if (ob.containsKey("required_mod")) {
				requiredMod = ob.get(String.class, "required_mod");
			}
		}

		public PieceSet build() {
			if(shouldLoad()) {
				if (built) {
					return PieceSets.getSet(Registry.BLOCK.get(base));
				}
				
				PieceSet ps = new PieceSet(Registry.BLOCK.get(base), name, genTypes);
				if (this.stonecuttable != null) ps.setStonecuttable(this.stonecuttable);
				if (this.woodmillable != null) ps.setWoodmillable(this.woodmillable);
				if (this.opaque != null) ps.setOpaque(this.opaque);
				if (this.mainTexture != null) ps.setTexture(this.mainTexture);
				if (this.topTexture != null) ps.setTopTexture(this.topTexture);
				if (this.bottomTexture != null) ps.setBottomTexture(this.bottomTexture);
				
				for (Map.Entry<PieceType, Identifier> vanillaPiece : this.getVanillaPieces().entrySet()) {
					ps.addVanillaPiece(vanillaPiece.getKey(), Registry.BLOCK.get(vanillaPiece.getValue()));
				}
				
				if (this.includeMode) ps.setInclude();
				for (PieceType pt : this.uncraftable) {
					ps.setUncraftable(pt);
				}
				
				this.built = true;
				return ps;
			}
			return null;
		}

		public void addVanillaPiece(Identifier type, Identifier piece) {
			vanillaPieces.put(type, piece);
		}

		public HashMap<PieceType, Identifier> getVanillaPieces() {
			HashMap<PieceType, Identifier> collected = new HashMap<>();

			for (Map.Entry<Identifier, Identifier> entry : vanillaPieces.entrySet()) {
				PieceTypes.getTypeOrEmpty(entry.getKey()).ifPresent(type -> collected.put(type, entry.getValue()));
			}

			return collected;
		}

		public Identifier getBaseID() {
			return base;
		}

		public boolean isBuilt() {
			return built;
		}

		public String getPackName() {
			return packName;
		}
		
		public String getRequiredMod() {
			return requiredMod;
		}
		
		public boolean shouldLoad() {
			return FabricLoader.getInstance().isModLoaded(getRequiredMod());
		}

		public boolean isReady() {
			if (!Registry.BLOCK.getOrEmpty(base).isPresent() || !Registry.ITEM.getOrEmpty(base).isPresent()) {
				return false;
			}

			for (Identifier id : getVanillaPieces().values()) {
				if (!Registry.BLOCK.getOrEmpty(id).isPresent() || !Registry.ITEM.getOrEmpty(id).isPresent()) {
					return false;
				}
			}

			return true;
		}

		public String toString() {
			return "PieceSet.Builder{ name: " + name + " , pack: " + getPackName() + " , base: " + getBaseID().toString() + " }";
		}
	}
}
