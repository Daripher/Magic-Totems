package daripher.totems.init;

import daripher.totems.TotemsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TotemsSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TotemsMod.MOD_ID);

	public static final RegistryObject<SoundEvent> TOTEM_POSITIVE = REGISTRY.register("totem_positive",
			() -> new SoundEvent(new ResourceLocation(TotemsMod.MOD_ID, "totem_positive")));
	public static final RegistryObject<SoundEvent> TOTEM_NEGATIVE = REGISTRY.register("totem_negative",
			() -> new SoundEvent(new ResourceLocation(TotemsMod.MOD_ID, "totem_negative")));
}
