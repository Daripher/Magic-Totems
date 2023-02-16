package daripher.totems.worldgen;

import daripher.totems.TotemsMod;
import daripher.totems.config.Config;
import daripher.totems.init.TotemsConfiguredFeatures;
import daripher.totems.worldgen.feature.TotemsPlacedFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = TotemsMod.MOD_ID)
public class TotemsWorldGen {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
		var surfaceTotemsFeature = TotemsPlacedFeature.create(TotemsConfiguredFeatures.SURFACE_TOTEMS, Config.COMMON.surfaceTotemsFrequency.get());
		var undergroundTotemsFeature = TotemsPlacedFeature.create(TotemsConfiguredFeatures.UNDERGROUND_TOTEMS, Config.COMMON.undergroundTotemsFrequency.get());
		var netherTotemsFeature = TotemsPlacedFeature.create(TotemsConfiguredFeatures.NETHER_TOTEMS, Config.COMMON.netherTotemsFrequency.get());

		if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND) {
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, Holder.direct(surfaceTotemsFeature));
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, Holder.direct(undergroundTotemsFeature));
		}

		if (event.getCategory() == BiomeCategory.NETHER) {
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, Holder.direct(netherTotemsFeature));
		}
	}
}
