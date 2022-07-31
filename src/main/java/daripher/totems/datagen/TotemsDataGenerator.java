package daripher.totems.datagen;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import daripher.totems.TotemsMod;
import daripher.totems.init.TotemsBiomeModifiers;
import daripher.totems.init.TotemsPlacedFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = TotemsMod.MOD_ID, bus = Bus.MOD)
public class TotemsDataGenerator
{
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event)
	{
		DataGenerator dataGenerator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		
		dataGenerator.addProvider(event.includeClient(), new TotemsBlockStateProvider(dataGenerator, existingFileHelper));
		dataGenerator.addProvider(event.includeClient(), new TotemsLanguageProvider(dataGenerator));
		
		dataGenerator.addProvider(event.includeServer(), new TotemsLootTableProvider(dataGenerator));
		dataGenerator.addProvider(event.includeServer(), forDataPackRegistry(dataGenerator, existingFileHelper, Registry.PLACED_FEATURE_REGISTRY, TotemsPlacedFeatures.REGISTRY));
		dataGenerator.addProvider(event.includeServer(), forDataPackRegistry(dataGenerator, existingFileHelper, ForgeRegistries.Keys.BIOME_MODIFIERS, TotemsBiomeModifiers.REGISTRY));
	}
	
	private static <T> DataProvider forDataPackRegistry(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, ResourceKey<Registry<T>> registryKey, DeferredRegister<T> registry)
	{
		RegistryAccess registryAccess = RegistryAccess.builtinCopy();
		RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);
		Map<ResourceLocation, T> idToObjectMap = registry.getEntries().stream().collect(HashMap::new, (map, object) -> map.put(object.getId(), object.get()), (map1, map2) -> map1.putAll(map2));
		return JsonCodecProvider.forDatapackRegistry(dataGenerator, existingFileHelper, TotemsMod.MOD_ID, registryOps, registryKey, idToObjectMap);
	}
}
