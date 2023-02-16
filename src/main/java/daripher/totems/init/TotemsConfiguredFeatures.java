package daripher.totems.init;

import daripher.totems.TotemsMod;
import daripher.totems.worldgen.feature.TotemsConfiguredFeature;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TotemsConfiguredFeatures {
	public static final DeferredRegister<ConfiguredFeature<?, ?>> REGISTRY = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, TotemsMod.MOD_ID);

	public static final RegistryObject<ConfiguredFeature<?, ?>> SURFACE_TOTEMS = REGISTRY.register("surface_totems",
			() -> TotemsConfiguredFeature.create(TotemsBlocks.SURFACE_TOTEM, BlockTags.DIRT));
	public static final RegistryObject<ConfiguredFeature<?, ?>> UNDERGROUND_TOTEMS = REGISTRY.register("underground_totems",
			() -> TotemsConfiguredFeature.create(TotemsBlocks.UNDERGROUND_TOTEM, BlockTags.BASE_STONE_OVERWORLD));
	public static final RegistryObject<ConfiguredFeature<?, ?>> NETHER_TOTEMS = REGISTRY.register("nether_totems",
			() -> TotemsConfiguredFeature.create(TotemsBlocks.NETHER_TOTEM, BlockTags.BASE_STONE_NETHER));
}
