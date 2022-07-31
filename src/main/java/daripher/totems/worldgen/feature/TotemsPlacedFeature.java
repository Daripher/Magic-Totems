package daripher.totems.worldgen.feature;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.RegistryObject;

public class TotemsPlacedFeature
{
	public static PlacedFeature create(RegistryObject<ConfiguredFeature<?, ?>> configuredFetureObject, int count)
	{
		List<PlacementModifier> placementModifiers = List.of(CountPlacement.of(count), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), BiomeFilter.biome());
		return new PlacedFeature(Holder.direct(configuredFetureObject.get()), placementModifiers);
	}
}
