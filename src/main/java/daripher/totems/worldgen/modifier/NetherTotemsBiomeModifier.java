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

public class NetherTotemsBiomeModifier implements BiomeModifier {
	private final TagKey<Biome> biomes;
	private Holder<PlacedFeature> placedFeatureHolder;

	public NetherTotemsBiomeModifier(TagKey<Biome> biomes) {
		this.biomes = biomes;
	}

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (placedFeatureHolder == null) {
			var placedFeature = TotemsPlacedFeature.create(TotemsConfiguredFeatures.NETHER_TOTEMS, Config.COMMON.netherTotemsFrequency.get());
			placedFeatureHolder = Holder.direct(placedFeature);
		}

		if (phase == Phase.ADD && biome.is(biomes)) {
			builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, placedFeatureHolder);
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return TotemsBiomeModifierSerializers.NETHER_TOTEMS.get();
	}

	public TagKey<Biome> getBiomes() {
		return biomes;
	}

	public static Codec<NetherTotemsBiomeModifier> createCodec() {
		return RecordCodecBuilder.create(builder -> {
			var biomesCodec = TagKey.codec(Registry.BIOME_REGISTRY).fieldOf("biomes").forGetter(NetherTotemsBiomeModifier::getBiomes);
			return builder.group(biomesCodec).apply(builder, NetherTotemsBiomeModifier::new);
		});
	}
}
