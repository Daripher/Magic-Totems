package daripher.totems.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import daripher.totems.config.Config;
import daripher.totems.init.TotemsBlockEntities;
import daripher.totems.init.TotemsBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class TotemBlockEntity extends BlockEntity
{
	private MobEffectInstance effectInstance;
	private int prevEffectAnimation;
	private int effectAnimation;
	private int cooldown;
	private int maxCooldown;
	
	public TotemBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(TotemsBlockEntities.TOTEM.get(), blockPos, blockState);
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		generateRandomEffect();
	}
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		effectInstance = MobEffectInstance.load(tag.getCompound("Effect"));
		cooldown = tag.getInt("Cooldown");
		maxCooldown = tag.getInt("MaxCooldown");
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put("Effect", getEffect().save(new CompoundTag()));
		tag.putInt("Cooldown", cooldown);
		tag.putInt("MaxCooldown", maxCooldown);
	}
	
	@Override
	public void saveToItem(ItemStack stack)
	{
		stack.getOrCreateTagElement("BlockEntityTag").put("Effect", getEffect().save(new CompoundTag()));
		stack.getOrCreateTagElement("BlockEntityTag").putInt("Cooldown", cooldown);
		stack.getOrCreateTagElement("BlockEntityTag").putInt("MaxCooldown", maxCooldown);
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
	
	public void use(Player player)
	{
		if (cooldown == 0)
		{
			player.addEffect(getEffect());
			cooldown = maxCooldown;
			
			if (Config.COMMON.shuffle.get())
			{
				generateRandomEffect();
			}
			else
			{
				setChanged();
			}
		}
	}
	
	@Nullable
	public MobEffectInstance getEffect()
	{
		if (effectInstance == null)
		{
			generateRandomEffect();
		}
		
		if (effectInstance == null)
		{
			return null;
		}
		
		return new MobEffectInstance(effectInstance);
	}
	
	public float getEffectAnimation(float partialTicks)
	{
		return Mth.lerp(partialTicks, prevEffectAnimation, effectAnimation);
	}
	
	public int getCooldown()
	{
		return cooldown;
	}
	
	private void generateRandomEffect()
	{
		if (!level.isClientSide)
		{
			Random random;
			
			if (effectInstance == null)
			{
				random = new Random(getBlockPos().asLong());
			}
			else
			{
				random = new Random(effectInstance.hashCode());
			}
			
			List<MobEffect> effects = ForgeRegistries.MOB_EFFECTS.getEntries().stream().collect(ArrayList::new, (list, entry) -> list.add(entry.getValue()), (list1, list2) -> list1.addAll(list2));
			MobEffect effect = effects.get(random.nextInt(effects.size()));
			int maxAmplifier = Config.COMMON.maxEffectAmplifier.get();
			int minDuration = Config.COMMON.minEffectDuration.get();
			int maxDuration = Config.COMMON.maxEffectDuration.get();
			int minCooldown = Config.COMMON.minCooldown.get();
			int maxCooldown = Config.COMMON.maxCooldown.get();
			int duration = (minDuration + random.nextInt(maxDuration - minDuration + 1)) * 20;
			int amplifier = random.nextInt(maxAmplifier + 1);
			effectInstance = new MobEffectInstance(effect, duration, amplifier);
			this.maxCooldown = (minCooldown + random.nextInt(maxCooldown - minCooldown + 1)) * 20;
			setChanged();
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
		}
	}
	
	public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T entity)
	{
		if (entity instanceof TotemBlockEntity)
		{
			TotemBlockEntity totem = (TotemBlockEntity) entity;
			totem.prevEffectAnimation = totem.effectAnimation;
			totem.effectAnimation += 2;
			
			if (totem.cooldown > 0)
			{
				totem.cooldown--;
				totem.setChanged();
			}
		}
	}
	
	public static BlockEntityType<TotemBlockEntity> createType()
	{
		return BlockEntityType.Builder.of(TotemBlockEntity::new, TotemsBlocks.SURFACE_TOTEM.get(), TotemsBlocks.UNDERGROUND_TOTEM.get(), TotemsBlocks.NETHER_TOTEM.get())
				.build(Util.fetchChoiceType(References.BLOCK_ENTITY, "totem"));
	}
}
