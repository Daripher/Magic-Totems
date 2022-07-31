package daripher.totems.datagen;

import daripher.totems.TotemsMod;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TotemsBlockStateProvider extends BlockStateProvider
{
	public TotemsBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper)
	{
		super(dataGenerator, TotemsMod.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerStatesAndModels()
	{
		totemBlock(TotemsBlocks.SURFACE_TOTEM, Blocks.OAK_LOG);
		totemBlock(TotemsBlocks.UNDERGROUND_TOTEM, Blocks.MOSSY_COBBLESTONE);
		totemBlock(TotemsBlocks.NETHER_TOTEM, Blocks.BLACKSTONE);
		totemTopBlock(TotemsBlocks.SURFACE_TOTEM_TOP, Blocks.OAK_LOG);
		totemTopBlock(TotemsBlocks.UNDERGROUND_TOTEM_TOP, Blocks.MOSSY_COBBLESTONE);
		totemTopBlock(TotemsBlocks.NETHER_TOTEM_TOP, Blocks.BLACKSTONE);
	}
	
	private void totemTopBlock(RegistryObject<Block> block, Block particleBlock)
	{
		String particleTexture = ":block/" + ForgeRegistries.BLOCKS.getKey(particleBlock).getPath();
		horizontalBlock(block.get(), models()
				.withExistingParent(block.getId().getPath(), "air")
				.texture("particle", particleTexture));
	}
	
	private void totemBlock(RegistryObject<Block> block, Block particleBlock)
	{
		String texture = TotemsMod.MOD_ID + ":block/" + block.getId().getPath();
		String particleTexture = ":block/" + ForgeRegistries.BLOCKS.getKey(particleBlock).getPath();
		horizontalBlock(block.get(), models()
				.withExistingParent(block.getId().getPath(), TotemsMod.MOD_ID + ":totem_animals")
				.texture("0", texture)
				.texture("particle", particleTexture));
		simpleBlockItem(block.get(), models()
				.getExistingFile(block.getId()));
	}
}
