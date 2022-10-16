package daripher.totems.datagen.loot;

import java.util.ArrayList;

import daripher.totems.init.TotemsBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction.Builder;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;

public class TotemsBlockLoot extends BlockLoot
{
	@Override
	protected void addTables()
	{
		add(TotemsBlocks.SURFACE_TOTEM.get(), createTotemDrop(TotemsBlocks.SURFACE_TOTEM.get()));
		add(TotemsBlocks.SURFACE_TOTEM_TOP.get(), createTotemDrop(TotemsBlocks.SURFACE_TOTEM.get()));
		add(TotemsBlocks.UNDERGROUND_TOTEM.get(), createTotemDrop(TotemsBlocks.UNDERGROUND_TOTEM.get()));
		add(TotemsBlocks.UNDERGROUND_TOTEM_TOP.get(), createTotemDrop(TotemsBlocks.UNDERGROUND_TOTEM.get()));
		add(TotemsBlocks.NETHER_TOTEM.get(), createTotemDrop(TotemsBlocks.NETHER_TOTEM.get()));
		add(TotemsBlocks.NETHER_TOTEM_TOP.get(), createTotemDrop(TotemsBlocks.NETHER_TOTEM.get()));
	}
	
	private LootTable.Builder createTotemDrop(Block block)
	{
		Builder copyNbtFunction = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
				.copy("Effect", "BlockEntityTag.Effect")
				.copy("EffectHidden", "BlockEntityTag.EffectHidden")
				.copy("Cooldown", "BlockEntityTag.Cooldown")
				.copy("MaxCooldown", "BlockEntityTag.MaxCooldown");
		return createSilkTouchOnlyTable(block).apply(copyNbtFunction);
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks()
	{
		return TotemsBlocks.REGISTRY.getEntries().stream().collect(() -> new ArrayList<Block>(), (list, registryObject) -> list.add(registryObject.get()), (list, list1) -> list.addAll(list1));
	}
}
