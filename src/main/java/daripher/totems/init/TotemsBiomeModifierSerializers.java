package daripher.totems.init;

import com.mojang.serialization.Codec;

import daripher.totems.TotemsMod;
import daripher.totems.worldgen.modifier.TotemsBiomeModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

public class TotemsBiomeModifierSerializers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> REGISTRY = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, TotemsMod.MOD_ID);

	public static final RegistryObject<Codec<TotemsBiomeModifier>> TOTEMS = REGISTRY.register("totems", TotemsBiomeModifier::createCodec);
}
