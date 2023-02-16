package daripher.totems.block.totems;

import daripher.totems.block.TotemBlock;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.world.level.material.Material;

public class UndergroundTotemBlock extends TotemBlock {
	public UndergroundTotemBlock() {
		super(Properties.of(Material.STONE).strength(2.0F, 6.0F), TotemsBlocks.UNDERGROUND_TOTEM_TOP);
	}
}
