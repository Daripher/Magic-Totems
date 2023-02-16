package daripher.totems.init;

import daripher.totems.TotemsMod;
import daripher.totems.block.entity.TotemBlockEntity;
import daripher.totems.block.entity.TotemTopBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TotemsBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TotemsMod.MOD_ID);

	public static final RegistryObject<BlockEntityType<TotemBlockEntity>> TOTEM = REGISTRY.register("totem", TotemBlockEntity::createType);
	public static final RegistryObject<BlockEntityType<TotemTopBlockEntity>> TOTEM_TOP = REGISTRY.register("totem_top", TotemTopBlockEntity::createType);
}
