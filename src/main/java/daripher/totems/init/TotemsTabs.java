package daripher.totems.init;

import daripher.totems.TotemsMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TotemsTabs {
	public static final CreativeModeTab TOTEMS = new CreativeModeTab(TotemsMod.MOD_ID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TotemsBlocks.SURFACE_TOTEM.get());
		}
	};
}
