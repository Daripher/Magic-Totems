package daripher.totems;

import daripher.totems.config.Config;
import daripher.totems.init.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@EventBusSubscriber(bus = Bus.MOD)
@Mod(TotemsMod.MOD_ID)
public class TotemsMod
{
	public static final String MOD_ID = "totemsofpower";
	
	public TotemsMod()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		TotemsBlocks.REGISTRY.register(modEventBus);
		TotemsBlockEntities.REGISTRY.register(modEventBus);
		TotemsItems.REGISTRY.register(modEventBus);
		TotemsConfiguredFeatures.REGISTRY.register(modEventBus);
		TotemsPlacedFeatures.REGISTRY.register(modEventBus);
		TotemsSounds.REGISTRY.register(modEventBus);
	}
}
