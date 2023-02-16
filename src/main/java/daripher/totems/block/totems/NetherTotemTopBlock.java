package daripher.totems.block.totems;

import daripher.totems.block.TotemTopBlock;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class NetherTotemTopBlock extends TotemTopBlock {
	public NetherTotemTopBlock() {
		super(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1.5F, 6.0F), TotemsBlocks.NETHER_TOTEM);
	}
}
