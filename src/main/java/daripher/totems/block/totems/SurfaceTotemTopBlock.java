package daripher.totems.block.totems;

import daripher.totems.block.TotemTopBlock;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class SurfaceTotemTopBlock extends TotemTopBlock
{
	public SurfaceTotemTopBlock()
	{
		super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD), TotemsBlocks.SURFACE_TOTEM);
	}
}
