package daripher.totems;

import daripher.totems.config.Config;
import daripher.totems.init.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
		TotemsBiomeModifierSerializers.REGISTRY.register(modEventBus);
		TotemsBiomeModifiers.REGISTRY.register(modEventBus);
		TotemsConfiguredFeatures.REGISTRY.register(modEventBus);
		TotemsPlacedFeatures.REGISTRY.register(modEventBus);
		TotemsSounds.REGISTRY.register(modEventBus);
	}
}
