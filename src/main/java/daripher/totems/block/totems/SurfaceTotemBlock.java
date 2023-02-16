package daripher.totems.block.totems;

import daripher.totems.block.TotemBlock;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class SurfaceTotemBlock extends TotemBlock {
	public SurfaceTotemBlock() {
		super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD), TotemsBlocks.SURFACE_TOTEM_TOP);
	}
}
