package daripher.totems.init;

import com.mojang.serialization.Codec;

import daripher.totems.TotemsMod;
import daripher.totems.worldgen.modifier.NetherTotemsBiomeModifier;
import daripher.totems.worldgen.modifier.SurfaceTotemsBiomeModifier;
import daripher.totems.worldgen.modifier.UndergroundTotemsBiomeModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

public class TotemsBiomeModifierSerializers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> REGISTRY = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, TotemsMod.MOD_ID);

	public static final RegistryObject<Codec<? extends BiomeModifier>> SURFACE_TOTEMS = REGISTRY.register("surface_totems", SurfaceTotemsBiomeModifier::createCodec);
	public static final RegistryObject<Codec<? extends BiomeModifier>> UNDERGROUND_TOTEMS = REGISTRY.register("underground_totems", UndergroundTotemsBiomeModifier::createCodec);
	public static final RegistryObject<Codec<? extends BiomeModifier>> NETHER_TOTEMS = REGISTRY.register("nether_totems", NetherTotemsBiomeModifier::createCodec);
}
