package com.shnupbups.extrapieces.core;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.shnupbups.extrapieces.ExtraPieces;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PiecePack {
	public ArrayList<PieceSet.Builder> builders = new ArrayList<>();
	public final String name;
	public final Version version;

	public PiecePack(String name, Version version) {
		this.name=name;
		this.version=version;
	}

	public boolean add(PieceSet.Builder builder) {
		return builders.add(builder);
	}

	public boolean isReady() {
		boolean ready = true;
		for(PieceSet.Builder psb:builders) {
			if(!psb.isReady()) ready = false;
		}
		return ready;
	}

	public boolean isBuilt() {
		boolean built = true;
		for(PieceSet.Builder psb:builders) {
			if(!psb.isBuilt()) built = false;
		}
		return built;
	}

	public ArrayList<PieceSet> build() {
		ArrayList<PieceSet> builtSets = new ArrayList<>();
		if(isReady()&&!isBuilt()) {
			for(PieceSet.Builder psb:builders) {
				builtSets.add(psb.build());
			}
		}
		return builtSets;
	}

	public int getSetCount() {
		return builders.size();
	}

	public static PiecePack fromFile(File file) {
		try (FileReader reader = new FileReader(file)) {
			JsonObject pp = Jankson.builder().build().load(file);
			return fromJson(file.getName(), pp);
		} catch (IOException e) {
			ExtraPieces.log("IOException loading piece pack " + file.getName());
			ExtraPieces.log(e.getMessage());
		} catch (SyntaxError e) {
			ExtraPieces.log("SyntaxError loading piece pack " + file.getName());
			ExtraPieces.log(e.getCompleteMessage());
		}
		return null;
	}

	public static PiecePack fromJson(String name, JsonObject object) {
		Version ver;
		if(object.containsKey("version")) {
			ver = new Version(object.get(String.class, "version"));
		} else {
			ver = new Version("0.0");
		}
		PiecePack pack = new PiecePack(name, ver);
		JsonObject sets;
		if(ver.version.equals("0.0")) {
			sets = object;
		} else {
			sets = object.getObject("sets");
		}
		for (String s : sets.keySet()) {
			JsonObject jsonSet = (JsonObject) object.get(s);
			PieceSet.Builder psb = new PieceSet.Builder(s, jsonSet, name);
			pack.add(psb);
		}
		return pack;
	}

	public boolean shouldReplace(PiecePack toReplace) {
		return (toReplace.name.equals(this.name) && this.version.compareTo(toReplace.version)==1);
	}

	public boolean shouldReplace(JsonObject object) {
		return shouldReplace(fromJson(this.name,object));
	}

	public boolean shouldReplace(File file) {
		return shouldReplace(fromFile(file));
	}

	public static class Version implements Comparable<Version> {

		private String version;

		public final String get() {
			return this.version;
		}

		public Version(String version) {
			if(version == null)
				throw new IllegalArgumentException("Version can not be null");
			if(!version.matches("[0-9]+(\\.[0-9]+)*"))
				throw new IllegalArgumentException("Invalid version format");
			this.version = version;
		}

		@Override
		public int compareTo(Version that) {
			if(that == null)
				return 1;
			String[] thisParts = this.get().split("\\.");
			String[] thatParts = that.get().split("\\.");
			int length = Math.max(thisParts.length, thatParts.length);
			for(int i = 0; i < length; i++) {
				int thisPart = i < thisParts.length ?
						Integer.parseInt(thisParts[i]) : 0;
				int thatPart = i < thatParts.length ?
						Integer.parseInt(thatParts[i]) : 0;
				if(thisPart < thatPart)
					return -1;
				if(thisPart > thatPart)
					return 1;
			}
			return 0;
		}

		@Override
		public boolean equals(Object that) {
			if(this == that)
				return true;
			if(that == null)
				return false;
			if(this.getClass() != that.getClass())
				return false;
			return this.compareTo((Version) that) == 0;
		}

	}
}
