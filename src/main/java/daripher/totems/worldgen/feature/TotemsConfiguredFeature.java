package daripher.totems.worldgen.feature;

import daripher.totems.block.TotemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

public class TotemsConfiguredFeature
{
	public static ConfiguredFeature<?, ?> create(RegistryObject<Block> blockObject, TagKey<Block> blockBelow)
	{
		BlockPredicate blockPredicate = BlockPredicate.allOf(
				BlockPredicate.matchesTag(blockBelow, BlockPos.ZERO.below()),
				BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO),
				BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO.above()));
		BlockPredicateFilter blockPredicateFilter = BlockPredicateFilter.forPredicate(blockPredicate);
		BlockStateProvider blockStateProvider = new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
				.add(blockObject.get().defaultBlockState().setValue(TotemBlock.FACING, Direction.EAST), 1)
				.add(blockObject.get().defaultBlockState().setValue(TotemBlock.FACING, Direction.NORTH), 1)
				.add(blockObject.get().defaultBlockState().setValue(TotemBlock.FACING, Direction.SOUTH), 1)
				.add(blockObject.get().defaultBlockState().setValue(TotemBlock.FACING, Direction.WEST), 1));
		BlockColumnConfiguration featureConfiguration = BlockColumnConfiguration.simple(ConstantInt.of(1), blockStateProvider);
		Holder<PlacedFeature> placedFeatureHolder = PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, featureConfiguration, blockPredicateFilter);
		RandomPatchConfiguration randomPatchConfiguration = new RandomPatchConfiguration(100, 0, 0, placedFeatureHolder);
		return new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomPatchConfiguration);
	}
}
