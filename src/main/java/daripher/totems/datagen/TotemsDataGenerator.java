package daripher.totems.datagen;

import daripher.totems.TotemsMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TotemsMod.MOD_ID, bus = Bus.MOD)
public class TotemsDataGenerator {
	@SubscribeEvent
	public static void addDataProviders(GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		dataGenerator.addProvider(event.includeClient(), new TotemsBlockStateProvider(dataGenerator, existingFileHelper));
		dataGenerator.addProvider(event.includeClient(), new TotemsLanguageProvider(dataGenerator));
		dataGenerator.addProvider(event.includeClient(), new TotemsSoundDefinitionsProvider(dataGenerator, existingFileHelper));
		dataGenerator.addProvider(event.includeServer(), new TotemsLootTableProvider(dataGenerator));
	}
}
