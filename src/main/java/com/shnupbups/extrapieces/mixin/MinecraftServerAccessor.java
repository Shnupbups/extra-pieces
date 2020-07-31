package com.shnupbups.extrapieces.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.logging.Logger;

@Mixin(MinecraftServer.class)
public interface MinecraftServerAccessor {
    @Accessor
    Logger getLogger();
}
