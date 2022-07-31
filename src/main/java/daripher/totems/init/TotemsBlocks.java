package daripher.totems.init;

import daripher.totems.TotemsMod;
import daripher.totems.block.totems.NetherTotemBlock;
import daripher.totems.block.totems.NetherTotemTopBlock;
import daripher.totems.block.totems.SurfaceTotemBlock;
import daripher.totems.block.totems.SurfaceTotemTopBlock;
import daripher.totems.block.totems.UndergroundTotemBlock;
import daripher.totems.block.totems.UndergroundTotemTopBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TotemsBlocks
{
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TotemsMod.MOD_ID);
	
	public static final RegistryObject<Block> SURFACE_TOTEM = REGISTRY.register("surface_totem", SurfaceTotemBlock::new);
	public static final RegistryObject<Block> UNDERGROUND_TOTEM = REGISTRY.register("underground_totem", UndergroundTotemBlock::new);
	public static final RegistryObject<Block> NETHER_TOTEM = REGISTRY.register("nether_totem", NetherTotemBlock::new);
	public static final RegistryObject<Block> SURFACE_TOTEM_TOP = REGISTRY.register("surface_totem_top", SurfaceTotemTopBlock::new);
	public static final RegistryObject<Block> UNDERGROUND_TOTEM_TOP = REGISTRY.register("underground_totem_top", UndergroundTotemTopBlock::new);
	public static final RegistryObject<Block> NETHER_TOTEM_TOP = REGISTRY.register("nether_totem_top", NetherTotemTopBlock::new);
}
