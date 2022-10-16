package daripher.totems.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import daripher.totems.config.Config;
import daripher.totems.init.TotemsBlockEntities;
import daripher.totems.init.TotemsBlocks;
import daripher.totems.init.TotemsSounds;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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
	private boolean effectHidden;
	
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
		effectHidden = tag.getBoolean("EffectHidden");
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put("Effect", getEffect().save(new CompoundTag()));
		tag.putInt("Cooldown", cooldown);
		tag.putInt("MaxCooldown", maxCooldown);
		tag.putBoolean("EffectHidden", effectHidden);
	}
	
	@Override
	public void saveToItem(ItemStack stack)
	{
		stack.getOrCreateTagElement("BlockEntityTag").put("Effect", getEffect().save(new CompoundTag()));
		stack.getOrCreateTagElement("BlockEntityTag").putInt("Cooldown", cooldown);
		stack.getOrCreateTagElement("BlockEntityTag").putInt("MaxCooldown", maxCooldown);
		stack.getOrCreateTagElement("BlockEntityTag").putBoolean("EffectHidden", effectHidden);
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
			if (Config.COMMON.playSounds.get())
			{
				SoundEvent playedSound;
				
				if (getEffect().getEffect().getCategory() == MobEffectCategory.HARMFUL)
				{
					playedSound = TotemsSounds.TOTEM_NEGATIVE.get();
				}
				else
				{
					playedSound = TotemsSounds.TOTEM_POSITIVE.get();
				}
				
				player.level.playSound(player, worldPosition, playedSound, SoundSource.BLOCKS, 1.0F, 1.0F);
			}
			
			player.addEffect(getEffect());
			cooldown = maxCooldown;
			
			if (effectHidden && Config.COMMON.revealAfterUse.get())
			{
				effectHidden ^= true;
			}
			
			if (Config.COMMON.shuffle.get())
			{
				generateRandomEffect();
			}
			else
			{
				setChanged();
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
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
	
	public boolean isEffectHidden()
	{
		return effectHidden;
	}
	
	private void generateRandomEffect()
	{
		if (!level.isClientSide)
		{
			if (!Config.AllowedEffects.initialized)
			{
				List<MobEffect> effects = ForgeRegistries.MOB_EFFECTS.getEntries().stream().collect(ArrayList::new, (list, entry) -> list.add(entry.getValue()), (list1, list2) -> list1.addAll(list2));
				
				if (!Config.COMMON.whitelistedEffects.get().isEmpty())
				{
					Config.COMMON.whitelistedEffects.get().forEach(effectId ->
					{
						String[] splitEffectId = effectId.split(":");
						
						if (splitEffectId.length == 2 && splitEffectId[1].equals("*"))
						{
							effects.removeIf(effect -> !ForgeRegistries.MOB_EFFECTS.getKey(effect).getNamespace().equals(splitEffectId[0]));
						}
					});
					
					effects.removeIf(effect -> !Config.COMMON.whitelistedEffects.get().contains(ForgeRegistries.MOB_EFFECTS.getKey(effect).toString()));
				}
				else if (!Config.COMMON.blacklistedEffects.get().isEmpty())
				{
					Config.COMMON.blacklistedEffects.get().forEach(effectId ->
					{
						String[] splitEffectId = effectId.split(":");
						
						if (splitEffectId.length == 2 && splitEffectId[1].equals("*"))
						{
							effects.removeIf(effect -> ForgeRegistries.MOB_EFFECTS.getKey(effect).getNamespace().equals(splitEffectId[0]));
						}
					});
					
					effects.removeIf(effect -> Config.COMMON.blacklistedEffects.get().contains(ForgeRegistries.MOB_EFFECTS.getKey(effect).toString()));
				}
				
				if (Config.COMMON.excludeNegativeEffects.get())
				{
					effects.removeIf(effect -> effect.getCategory() == MobEffectCategory.HARMFUL);
				}
				
				Config.AllowedEffects.EFFECTS_LIST.addAll(effects);
				Config.AllowedEffects.initialized = true;
			}
			
			if (Config.AllowedEffects.EFFECTS_LIST.isEmpty())
			{
				return;
			}
			
			Random random;
			
			if (effectInstance == null)
			{
				random = new Random(getBlockPos().asLong());
			}
			else
			{
				random = new Random(effectInstance.hashCode());
			}
			
			List<MobEffect> effects = Config.AllowedEffects.EFFECTS_LIST;
			effectHidden = Config.COMMON.mysteryIcon.get();
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
