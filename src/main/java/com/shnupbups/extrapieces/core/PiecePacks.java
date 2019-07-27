package com.shnupbups.extrapieces.core;

import java.util.ArrayList;
import java.util.HashMap;

public class PiecePacks {
	public static HashMap<String,PiecePack> packs = new HashMap<>();
	public static ArrayList<PiecePack> primedPacks = new ArrayList<>();

	public static PiecePack addPack(PiecePack pack) {
		return packs.put(pack.name,pack);
	}

	public static PiecePack getPack(String name) {
		return packs.get(name);
	}

	public static boolean allReady() {
		boolean ready = true;
		for (PiecePack pp:packs.values()) {
			if(!pp.isReady()) ready = false;
		}
		return ready;
	}

	public static boolean allBuilt() {
		boolean built = true;
		for (PiecePack pp:packs.values()) {
			if(!pp.isBuilt()) built = false;
		}
		return built;
	}

	public static void buildAll() {
		if(allReady()&&!allBuilt()) {
			for (PiecePack pp:packs.values()) {
				pp.build();
			}
		}
	}

	public static int getTotalSetCount() {
		int count = 0;
		for (PiecePack pp:packs.values()) {
			count += pp.getSetCount();
		}
		return count;
	}
}
