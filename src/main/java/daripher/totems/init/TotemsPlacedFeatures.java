package daripher.totems.init;

import daripher.totems.TotemsMod;
import daripher.totems.worldgen.feature.TotemsPlacedFeature;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TotemsPlacedFeatures
{
	public static final DeferredRegister<PlacedFeature> REGISTRY = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, TotemsMod.MOD_ID);
	
	public static final RegistryObject<PlacedFeature> SURFACE_TOTEMS = REGISTRY.register("surface_totems", () -> TotemsPlacedFeature.create(TotemsConfiguredFeatures.SURFACE_TOTEMS, 7));
	public static final RegistryObject<PlacedFeature> UNDERGROUND_TOTEMS = REGISTRY.register("underground_totems", () -> TotemsPlacedFeature.create(TotemsConfiguredFeatures.UNDERGROUND_TOTEMS, 30));
	public static final RegistryObject<PlacedFeature> NETHER_TOTEMS = REGISTRY.register("nether_totems", () -> TotemsPlacedFeature.create(TotemsConfiguredFeatures.NETHER_TOTEMS, 5));
}
