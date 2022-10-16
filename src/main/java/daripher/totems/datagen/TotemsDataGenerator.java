package daripher.totems.datagen;

import daripher.totems.TotemsMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = TotemsMod.MOD_ID, bus = Bus.MOD)
public class TotemsDataGenerator
{
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event)
	{
		DataGenerator dataGenerator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		
		if (event.includeClient())
		{
			dataGenerator.addProvider(new TotemsBlockStateProvider(dataGenerator, existingFileHelper));
			dataGenerator.addProvider(new TotemsLanguageProvider(dataGenerator));
			dataGenerator.addProvider(new TotemsSoundDefinitionsProvider(dataGenerator, existingFileHelper));
		}
		
		if (event.includeServer())
		{
			dataGenerator.addProvider(new TotemsLootTableProvider(dataGenerator));
		}
	}
}
