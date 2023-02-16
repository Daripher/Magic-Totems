package daripher.totems.block;

import daripher.totems.block.entity.TotemBlockEntity;
import daripher.totems.init.TotemsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

public abstract class TotemBlock extends HorizontalDirectionalBlock implements EntityBlock {
	protected static final VoxelShape BASE_SHAPE = createBaseShape();
	protected static final VoxelShape WINGS_NORTH_SOUTH_SHAPE = createWingsNorthSouthShape();
	protected static final VoxelShape WINGS_EAST_WEST_SHAPE = createWingsEastWestShape();
	protected static final VoxelShape NORTH_SOUTH_SHAPE = Shapes.or(BASE_SHAPE, WINGS_NORTH_SOUTH_SHAPE);
	protected static final VoxelShape EAST_WEST_SHAPE = Shapes.or(BASE_SHAPE, WINGS_EAST_WEST_SHAPE);
	private final RegistryObject<Block> topBlock;

	protected TotemBlock(Properties properties, RegistryObject<Block> topBlock) {
		super(properties.lightLevel(state -> 1));
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
		this.topBlock = topBlock;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		var facing = blockState.getValue(FACING);
		return facing == Direction.NORTH || facing == Direction.SOUTH ? NORTH_SOUTH_SHAPE : EAST_WEST_SHAPE;
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		level.setBlockAndUpdate(blockPos.above(), topBlock.get().defaultBlockState().setValue(FACING, level.getBlockState(blockPos).getValue(FACING)));
	}

	@Override
	public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
		levelAccessor.destroyBlock(blockPos.above(), false);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
		level.getBlockEntity(blockPos, TotemsBlockEntities.TOTEM.get()).ifPresent(totem -> totem.use(player));
		return InteractionResult.SUCCESS;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TotemBlockEntity(blockPos, blockState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return TotemBlockEntity::tick;
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
		var itemStack = new ItemStack(this);
		level.getBlockEntity(pos, TotemsBlockEntities.TOTEM.get()).ifPresent(totem -> totem.saveToItem(itemStack));
		return itemStack;
	}

	private static VoxelShape createBaseShape() {
		return Shapes.or(voxelBox(1, 0, 1, 14, 1, 14), voxelBox(5, 10, 5, 6, 2, 6), voxelBox(5, 21, 5, 6, 2, 6), voxelBox(4, 1, 4, 8, 9, 8), voxelBox(4, 12, 4, 8, 9, 8),
				voxelBox(4, 23, 4, 8, 9, 8));
	}

	private static VoxelShape createWingsNorthSouthShape() {
		return Shapes.or(voxelBox(-3, 29, 7.5, 1, 3, 1), voxelBox(18, 29, 7.5, 1, 3, 1), voxelBox(-2, 27, 7.5, 2, 5, 1), voxelBox(16, 27, 7.5, 2, 5, 1),
				voxelBox(14, 26, 7.5, 2, 6, 1), voxelBox(0, 26, 7.5, 2, 6, 1), voxelBox(12, 24, 7.5, 2, 7, 1), voxelBox(2, 24, 7.5, 2, 7, 1));
	}

	private static VoxelShape createWingsEastWestShape() {
		return Shapes.or(voxelBox(7.5, 29, -3, 1, 3, 1), voxelBox(7.5, 29, 18, 1, 3, 1), voxelBox(7.5, 27, -2, 1, 5, 2), voxelBox(7.5, 27, 16, 1, 5, 2),
				voxelBox(7.5, 26, 14, 1, 6, 2), voxelBox(7.5, 26, 0, 1, 6, 2), voxelBox(7.5, 24, 12, 1, 7, 2), voxelBox(7.5, 24, 2, 1, 7, 2));
	}

	private static VoxelShape voxelBox(double x, double y, double z, double xSize, double ySize, double zSize) {
		return Block.box(x, y, z, x + xSize, y + ySize, z + zSize);
	}
}
