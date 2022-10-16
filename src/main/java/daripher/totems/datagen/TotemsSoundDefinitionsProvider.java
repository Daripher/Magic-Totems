package daripher.totems.datagen;

import daripher.totems.TotemsMod;
import daripher.totems.init.TotemsSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;

public class TotemsSoundDefinitionsProvider extends SoundDefinitionsProvider
{
	public TotemsSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, TotemsMod.MOD_ID, existingFileHelper);
	}
	
	@Override
	public void registerSounds()
	{
		addSound(TotemsSounds.TOTEM_POSITIVE);
		addSound(TotemsSounds.TOTEM_NEGATIVE);
	}
	
	private void addSound(RegistryObject<SoundEvent> soundObject)
	{
		add(soundObject, definition().subtitle(soundObject.getId().getNamespace() + "." + soundObject.getId().getPath() + ".subtitle").with(sound(soundObject.getId())));
	}
}
