package daripher.totems.block.entity;

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
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockState;

public class TotemBlockEntity extends BlockEntity {
	private MobEffectInstance effectInstance;
	private int prevEffectAnimation;
	private int effectAnimation;
	private int cooldown;
	private int maxCooldown;
	private boolean effectHidden;

	public TotemBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(TotemsBlockEntities.TOTEM.get(), blockPos, blockState);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		effectInstance = MobEffectInstance.load(tag.getCompound("Effect"));
		cooldown = tag.getInt("Cooldown");
		maxCooldown = tag.getInt("MaxCooldown");
		effectHidden = tag.getBoolean("EffectHidden");
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Effect", getEffect().save(new CompoundTag()));
		tag.putInt("Cooldown", cooldown);
		tag.putInt("MaxCooldown", maxCooldown);
		tag.putBoolean("EffectHidden", effectHidden);
	}

	@Override
	public void saveToItem(ItemStack stack) {
		var effect = getEffect();

		if (effect != null) {
			stack.getOrCreateTagElement("BlockEntityTag").put("Effect", effect.save(new CompoundTag()));
			stack.getOrCreateTagElement("BlockEntityTag").putInt("Cooldown", cooldown);
			stack.getOrCreateTagElement("BlockEntityTag").putInt("MaxCooldown", maxCooldown);
			stack.getOrCreateTagElement("BlockEntityTag").putBoolean("EffectHidden", effectHidden);
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}

	public void use(Player player) {
		if (cooldown == 0) {
			if (Config.COMMON.playSounds.get()) {
				SoundEvent playedSound;

				if (getEffect().getEffect().getCategory() == MobEffectCategory.HARMFUL) {
					playedSound = TotemsSounds.TOTEM_NEGATIVE.get();
				} else {
					playedSound = TotemsSounds.TOTEM_POSITIVE.get();
				}

				player.level.playSound(player, worldPosition, playedSound, SoundSource.BLOCKS, 0.7F, 1.0F);
			}

			player.addEffect(getEffect());
			cooldown = maxCooldown;

			if (effectHidden && Config.COMMON.revealAfterUse.get()) {
				effectHidden ^= true;
			}

			if (Config.COMMON.shuffle.get()) {
				generateRandomEffect();
			} else {
				setChanged();
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
			}
		}
	}

	@Nullable
	public MobEffectInstance getEffect() {
		if (effectInstance == null) {
			generateRandomEffect();
		}

		if (effectInstance == null) {
			return null;
		}

		return new MobEffectInstance(effectInstance);
	}

	public float getEffectAnimation(float partialTicks) {
		return Mth.lerp(partialTicks, prevEffectAnimation, effectAnimation);
	}

	public int getCooldown() {
		return cooldown;
	}

	public boolean isEffectHidden() {
		return effectHidden;
	}

	private void generateRandomEffect() {
		if (level.isClientSide) {
			return;
		}

		if (!Config.AllowedEffects.initialized) {
			Config.AllowedEffects.initialize();
		}

		if (Config.AllowedEffects.EFFECTS_LIST.isEmpty()) {
			return;
		}

		var random = new Random();
		var allowedEffects = Config.AllowedEffects.EFFECTS_LIST;
		effectHidden = Config.COMMON.mysteryIcon.get();
		var effect = allowedEffects.get(random.nextInt(allowedEffects.size()));
		var maxAmplifier = Config.COMMON.maxEffectAmplifier.get();
		var minDuration = Config.COMMON.minEffectDuration.get();
		var maxDuration = Config.COMMON.maxEffectDuration.get();
		var minCooldown = Config.COMMON.minCooldown.get();
		var maxCooldown = Config.COMMON.maxCooldown.get();
		var duration = (minDuration + random.nextInt(maxDuration - minDuration + 1)) * 20;
		var amplifier = random.nextInt(maxAmplifier + 1);
		effectInstance = new MobEffectInstance(effect, duration, amplifier);
		this.maxCooldown = (minCooldown + random.nextInt(maxCooldown - minCooldown + 1)) * 20;
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T entity) {
		if (entity instanceof TotemBlockEntity totem) {
			totem.prevEffectAnimation = totem.effectAnimation;
			totem.effectAnimation += 2;

			if (totem.cooldown > 0) {
				totem.cooldown--;
				totem.setChanged();
			}
		}
	}

	public static BlockEntityType<TotemBlockEntity> createType() {
		var builder = Builder.of(TotemBlockEntity::new, TotemsBlocks.SURFACE_TOTEM.get(), TotemsBlocks.UNDERGROUND_TOTEM.get(), TotemsBlocks.NETHER_TOTEM.get());
		return builder.build(Util.fetchChoiceType(References.BLOCK_ENTITY, "totem"));
	}
}
