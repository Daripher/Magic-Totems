package daripher.totems.client.init;

import daripher.totems.TotemsMod;
import daripher.totems.client.render.TotemEffectRenderer;
import daripher.totems.init.TotemsBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = TotemsMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class TotemRenderers
{
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		BlockEntityRenderers.register(TotemsBlockEntities.TOTEM.get(), TotemEffectRenderer::new);
	}
}
