package daripher.totems.worldgen;

import daripher.totems.TotemsMod;
import daripher.totems.init.TotemsPlacedFeatures;
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
		if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND) {
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, Holder.direct(TotemsPlacedFeatures.SURFACE_TOTEMS.get()));
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, Holder.direct(TotemsPlacedFeatures.UNDERGROUND_TOTEMS.get()));
		}

		if (event.getCategory() == BiomeCategory.NETHER) {
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, Holder.direct(TotemsPlacedFeatures.NETHER_TOTEMS.get()));
		}
	}
}
