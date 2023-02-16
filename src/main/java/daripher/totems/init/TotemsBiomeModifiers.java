package daripher.totems.init;

import daripher.totems.TotemsMod;
import daripher.totems.worldgen.modifier.NetherTotemsBiomeModifier;
import daripher.totems.worldgen.modifier.SurfaceTotemsBiomeModifier;
import daripher.totems.worldgen.modifier.UndergroundTotemsBiomeModifier;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TotemsBiomeModifiers {
	public static final DeferredRegister<BiomeModifier> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIERS, TotemsMod.MOD_ID);

	public static final RegistryObject<BiomeModifier> SURFACE_TOTEMS = REGISTRY.register("surface_totems", () -> new SurfaceTotemsBiomeModifier(BiomeTags.IS_OVERWORLD));
	public static final RegistryObject<BiomeModifier> UNDERGROUND_TOTEMS = REGISTRY.register("underground_totems", () -> new UndergroundTotemsBiomeModifier(BiomeTags.IS_OVERWORLD));
	public static final RegistryObject<BiomeModifier> NETHER_TOTEMS = REGISTRY.register("nether_totems", () -> new NetherTotemsBiomeModifier(BiomeTags.IS_NETHER));
}
