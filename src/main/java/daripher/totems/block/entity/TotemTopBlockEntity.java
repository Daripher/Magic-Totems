package daripher.totems.block.entity;

import javax.annotation.Nullable;

import daripher.totems.init.TotemsBlockEntities;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TotemTopBlockEntity extends BlockEntity
{
	private MobEffectInstance effectInstance;
	
	public TotemTopBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(TotemsBlockEntities.TOTEM_TOP.get(), blockPos, blockState);
	}
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		effectInstance = MobEffectInstance.load(tag.getCompound("Effect"));
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put("Effect", getEffect().save(new CompoundTag()));
	}
	
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket()
	{
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public CompoundTag getUpdateTag()
	{
		return saveWithoutMetadata();
	}
	
	@Nullable
	public MobEffectInstance getEffect()
	{
		TotemBlockEntity bottomBlockEntity = level.getBlockEntity(getBlockPos().below(), TotemsBlockEntities.TOTEM.get()).orElse(null);
		
		if (bottomBlockEntity != null)
		{
			effectInstance = bottomBlockEntity.getEffect();
		}
		
		if (effectInstance == null)
		{
			return null;
		}
		
		return new MobEffectInstance(effectInstance);
	}
	
	public static BlockEntityType<TotemTopBlockEntity> createType()
	{
		return BlockEntityType.Builder.of(TotemTopBlockEntity::new, TotemsBlocks.SURFACE_TOTEM_TOP.get(), TotemsBlocks.UNDERGROUND_TOTEM_TOP.get(), TotemsBlocks.NETHER_TOTEM_TOP.get())
				.build(Util.fetchChoiceType(References.BLOCK_ENTITY, "totem_top"));
	}
}
