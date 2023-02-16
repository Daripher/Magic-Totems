package daripher.totems.worldgen.feature;

import daripher.totems.block.TotemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraftforge.registries.RegistryObject;

public class TotemsConfiguredFeature {
	public static ConfiguredFeature<?, ?> create(RegistryObject<Block> blockObject, TagKey<Block> acceptableBlocksBelow) {
		var blockBelowPredicate = BlockPredicate.matchesTag(acceptableBlocksBelow, BlockPos.ZERO.below());
		var blockAtPosPredicate = BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO);
		var blockAbovePredicate = BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO.above());
		var blocksAroundPredicate = BlockPredicate.allOf(blockBelowPredicate, blockAtPosPredicate, blockAbovePredicate);
		var blocksAroundFilter = BlockPredicateFilter.forPredicate(blocksAroundPredicate);
		var defaultBlockState = blockObject.get().defaultBlockState();
		var weightedListBuilder = new SimpleWeightedRandomList.Builder<BlockState>();
		Direction.Plane.HORIZONTAL.forEach(facing -> weightedListBuilder.add(defaultBlockState.setValue(TotemBlock.FACING, facing), 1));
		var blockStateProvider = new WeightedStateProvider(weightedListBuilder);
		var featureConfiguration = BlockColumnConfiguration.simple(ConstantInt.of(1), blockStateProvider);
		var placedFeatureHolder = PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, featureConfiguration, blocksAroundFilter);
		var randomPatchConfiguration = new RandomPatchConfiguration(100, 0, 0, placedFeatureHolder);
		return new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomPatchConfiguration);
	}
}
