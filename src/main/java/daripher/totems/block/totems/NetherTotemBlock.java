package daripher.totems.block.totems;

import daripher.totems.block.TotemBlock;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class NetherTotemBlock extends TotemBlock
{
	public NetherTotemBlock()
	{
		super(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1.5F, 6.0F), TotemsBlocks.NETHER_TOTEM_TOP);
	}
}
