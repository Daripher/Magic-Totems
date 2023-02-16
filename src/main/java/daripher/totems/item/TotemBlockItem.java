package daripher.totems.item;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import daripher.totems.init.TotemsTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class TotemBlockItem extends BlockItem {
	public TotemBlockItem(Block block) {
		super(block, new Item.Properties().tab(TotemsTabs.TOTEMS));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag tooltipFlag) {
		var effectInstance = getMobEffect(stack);
		var attributeModifiers = new ArrayList<Pair<Attribute, AttributeModifier>>();
		var effectHidden = stack.getOrCreateTag().getCompound("BlockEntityTag").getBoolean("EffectHidden");

		if (effectHidden) {
			components.add(new TranslatableComponent("tooltip.totems.effect_hidden").withStyle(ChatFormatting.GRAY));
			return;
		}

		if (effectInstance == null) {
			components.add(new TranslatableComponent("effect.none").withStyle(ChatFormatting.GRAY));
		} else {
			var effectDescription = new TranslatableComponent(effectInstance.getDescriptionId());
			var effect = effectInstance.getEffect();
			var effectAttributeModifiers = effect.getAttributeModifiers();

			effectAttributeModifiers.forEach((attribute, modifier) -> {
				var modifierValue = effect.getAttributeModifierValue(effectInstance.getAmplifier(), modifier);
				var modifierCopy = new AttributeModifier(modifier.getName(), modifierValue, modifier.getOperation());
				attributeModifiers.add(new Pair<>(attribute, modifierCopy));
			});

			if (effectInstance.getAmplifier() > 0) {
				var potencyDescription = new TranslatableComponent("potion.potency." + effectInstance.getAmplifier());
				effectDescription = new TranslatableComponent("potion.withAmplifier", effectDescription, potencyDescription);
			}

			if (effectInstance.getDuration() > 20) {
				var formattedDuration = MobEffectUtil.formatDuration(effectInstance, 1.0F);
				effectDescription = new TranslatableComponent("potion.withDuration", effectDescription, formattedDuration);
			}

			components.add(effectDescription.withStyle(effect.getCategory().getTooltipFormatting()));
		}

		if (!attributeModifiers.isEmpty()) {
			components.add(new TextComponent(""));
			components.add(new TranslatableComponent("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

			attributeModifiers.forEach(pair -> {
				var attributeModifier = pair.getSecond();
				var modifierValue = attributeModifier.getAmount();
				var modifierValueForDescription = attributeModifier.getAmount();

				if (attributeModifier.getOperation() != Operation.ADDITION) {
					modifierValueForDescription *= 100;
				}

				if (modifierValue < 0) {
					modifierValueForDescription *= -1;
				}

				MutableComponent modifierDescription = null;
				var formattedModifierValue = ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(modifierValueForDescription);
				var formattedAttributeDescription = new TranslatableComponent(pair.getFirst().getDescriptionId());
				var modifierOperationId = attributeModifier.getOperation().toValue();

				if (modifierValue > 0) {
					modifierDescription = new TranslatableComponent("attribute.modifier.plus." + modifierOperationId, formattedModifierValue,
							formattedAttributeDescription).withStyle(ChatFormatting.BLUE);
				} else if (modifierValue < 0) {
					modifierDescription = new TranslatableComponent("attribute.modifier.take." + modifierOperationId, formattedModifierValue,
							formattedAttributeDescription).withStyle(ChatFormatting.RED);
				}

				if (modifierDescription != null) {
					components.add(modifierDescription);
				}
			});
		}

		if (effectInstance != null) {
			var cooldown = stack.getOrCreateTag().getCompound("BlockEntityTag").getInt("MaxCooldown");
			var formattedCooldown = StringUtil.formatTickDuration(cooldown);
			components.add(new TextComponent(""));
			components.add(new TranslatableComponent("tooltip.totems.cooldown", formattedCooldown).withStyle(ChatFormatting.GRAY));
		}
	}

	private MobEffectInstance getMobEffect(ItemStack stack) {
		return MobEffectInstance.load(stack.getOrCreateTag().getCompound("BlockEntityTag").getCompound("Effect"));
	}
}
