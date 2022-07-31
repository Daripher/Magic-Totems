package daripher.totems.init;

import daripher.totems.TotemsMod;
import daripher.totems.item.TotemBlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TotemsItems
{
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TotemsMod.MOD_ID);

    public static final RegistryObject<Item> SURFACE_TOTEM = REGISTRY.register("surface_totem", () -> new TotemBlockItem(TotemsBlocks.SURFACE_TOTEM.get()));
    public static final RegistryObject<Item> UNDERGROUND_TOTEM = REGISTRY.register("underground_totem", () -> new TotemBlockItem(TotemsBlocks.UNDERGROUND_TOTEM.get()));
    public static final RegistryObject<Item> NETHER_TOTEM = REGISTRY.register("nether_totem", () -> new TotemBlockItem(TotemsBlocks.NETHER_TOTEM.get()));
}
