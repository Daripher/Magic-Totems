package daripher.totems.init;

import daripher.totems.TotemsMod;
import daripher.totems.worldgen.modifier.TotemsBiomeModifier;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TotemsBiomeModifiers
{
	public static final DeferredRegister<BiomeModifier> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIERS, TotemsMod.MOD_ID);
	
	public static final RegistryObject<BiomeModifier> SURFACE_TOTEMS_FOREST = REGISTRY.register("surface_totems_forest", () -> new TotemsBiomeModifier(BiomeTags.IS_OVERWORLD, TotemsPlacedFeatures.SURFACE_TOTEMS.getId()));
	public static final RegistryObject<BiomeModifier> UNDERGROUND_TOTEMS = REGISTRY.register("underground_totems", () -> new TotemsBiomeModifier(BiomeTags.IS_OVERWORLD, TotemsPlacedFeatures.UNDERGROUND_TOTEMS.getId()));
	public static final RegistryObject<BiomeModifier> NETHER_TOTEMS = REGISTRY.register("nether_totems", () -> new TotemsBiomeModifier(BiomeTags.IS_NETHER, TotemsPlacedFeatures.NETHER_TOTEMS.getId()));
}
