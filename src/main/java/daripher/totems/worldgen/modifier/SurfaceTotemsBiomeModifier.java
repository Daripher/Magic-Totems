package daripher.totems.worldgen.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import daripher.totems.config.Config;
import daripher.totems.init.TotemsBiomeModifierSerializers;
import daripher.totems.init.TotemsConfiguredFeatures;
import daripher.totems.worldgen.feature.TotemsPlacedFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class SurfaceTotemsBiomeModifier implements BiomeModifier {
	private final TagKey<Biome> biomes;
	private Holder<PlacedFeature> placedFeatureHolder;

	public SurfaceTotemsBiomeModifier(TagKey<Biome> biomes) {
		this.biomes = biomes;
	}

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (placedFeatureHolder == null) {
			var placedFeature = TotemsPlacedFeature.create(TotemsConfiguredFeatures.SURFACE_TOTEMS, Config.COMMON.surfaceTotemsFrequency.get());
			placedFeatureHolder = Holder.direct(placedFeature);
		}

		if (phase == Phase.ADD && biome.is(biomes)) {
			builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, placedFeatureHolder);
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return TotemsBiomeModifierSerializers.SURFACE_TOTEMS.get();
	}

	public TagKey<Biome> getBiomes() {
		return biomes;
	}

	public static Codec<SurfaceTotemsBiomeModifier> createCodec() {
		return RecordCodecBuilder.create(builder -> {
			var biomesCodec = TagKey.codec(Registry.BIOME_REGISTRY).fieldOf("biomes").forGetter(SurfaceTotemsBiomeModifier::getBiomes);
			return builder.group(biomesCodec).apply(builder, SurfaceTotemsBiomeModifier::new);
		});
	}
}
