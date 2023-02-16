package daripher.totems.worldgen.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import daripher.totems.init.TotemsBiomeModifierSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class TotemsBiomeModifier implements BiomeModifier {
	private final TagKey<Biome> biomes;
	private final Holder<PlacedFeature> placedFeature;
	private final ResourceLocation placedFeatureId;

	public TotemsBiomeModifier(TagKey<Biome> biomes, ResourceLocation placedFeatureId) {
		this.biomes = biomes;
		this.placedFeature = Holder.direct(BuiltinRegistries.PLACED_FEATURE.get(placedFeatureId));
		this.placedFeatureId = placedFeatureId;
	}

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD && biome.is(biomes)) {
			builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, placedFeature);
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return TotemsBiomeModifierSerializers.TOTEMS.get();
	}

	public TagKey<Biome> getBiomes() {
		return biomes;
	}

	public ResourceLocation getPlacedFeatureId() {
		return placedFeatureId;
	}

	public static Codec<TotemsBiomeModifier> createCodec() {
		return RecordCodecBuilder.create(builder -> {
			var biomeCodec = TagKey.codec(Registry.BIOME_REGISTRY).fieldOf("biomes").forGetter(TotemsBiomeModifier::getBiomes);
			var featureIdCodec = ResourceLocation.CODEC.fieldOf("feature").forGetter(TotemsBiomeModifier::getPlacedFeatureId);
			return builder.group(biomeCodec, featureIdCodec).apply(builder, TotemsBiomeModifier::new);
		});
	}
}
