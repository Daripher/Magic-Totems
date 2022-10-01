package daripher.totems.datagen;

import daripher.totems.TotemsMod;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class TotemsLanguageProvider extends LanguageProvider
{
	public TotemsLanguageProvider(DataGenerator gen)
	{
		super(gen, TotemsMod.MOD_ID, "en_us");
	}
	
	@Override
	protected void addTranslations()
	{
		add(TotemsBlocks.SURFACE_TOTEM.get(), "Totem of Power");
		add(TotemsBlocks.UNDERGROUND_TOTEM.get(), "Totem of Power");
		add(TotemsBlocks.NETHER_TOTEM.get(), "Totem of Power");
		add("itemGroup." + TotemsMod.MOD_ID, "Totems of Power");
		add("tooltip.totems.cooldown", "Cooldown: %s");
	}
}
