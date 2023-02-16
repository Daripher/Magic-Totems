package daripher.totems.block;

import daripher.totems.block.entity.TotemTopBlockEntity;
import daripher.totems.init.TotemsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

public abstract class TotemTopBlock extends TotemBlock {
	protected TotemTopBlock(Properties properties, RegistryObject<Block> bottomBlock) {
		super(properties, null);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return super.getShape(blockState, blockGetter, blockPos, collisionContext).move(0, -1, 0);
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.INVISIBLE;
	}

	@Override
	public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
		levelAccessor.destroyBlock(blockPos.below(), false);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
		level.getBlockEntity(blockPos.below(), TotemsBlockEntities.TOTEM.get()).ifPresent(totem -> totem.use(player));
		return InteractionResult.SUCCESS;
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
		var stack = new ItemStack(level.getBlockState(pos.below()).getBlock());
		level.getBlockEntity(pos.below(), TotemsBlockEntities.TOTEM.get()).ifPresent(totem -> totem.saveToItem(stack));
		return stack;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TotemTopBlockEntity(blockPos, blockState);
	}
}
