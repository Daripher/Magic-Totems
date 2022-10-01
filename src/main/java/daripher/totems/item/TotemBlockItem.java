package daripher.totems.item;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import daripher.totems.init.TotemsTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class TotemBlockItem extends BlockItem
{
	public TotemBlockItem(Block block)
	{
		super(block, new Item.Properties().tab(TotemsTabs.TOTEMS));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag tooltipFlag)
	{
		MobEffectInstance effect = getMobEffect(stack);
		List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
		
		if (effect == null)
		{
			components.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
		}
		else
		{
			MutableComponent mutablecomponent = Component.translatable(effect.getDescriptionId());
			MobEffect mobeffect = effect.getEffect();
			Map<Attribute, AttributeModifier> map = mobeffect.getAttributeModifiers();
			
			if (!map.isEmpty())
			{
				for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet())
				{
					AttributeModifier attributemodifier = entry.getValue();
					AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), mobeffect.getAttributeModifierValue(effect.getAmplifier(), attributemodifier),
							attributemodifier.getOperation());
					list1.add(new Pair<>(entry.getKey(), attributemodifier1));
				}
			}
			
			if (effect.getAmplifier() > 0)
			{
				mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + effect.getAmplifier()));
			}
			
			if (effect.getDuration() > 20)
			{
				mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(effect, 1.0F));
			}
			
			components.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
		}
		
		if (!list1.isEmpty())
		{
			components.add(CommonComponents.EMPTY);
			components.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
			
			for (Pair<Attribute, AttributeModifier> pair : list1)
			{
				AttributeModifier attributemodifier2 = pair.getSecond();
				double d0 = attributemodifier2.getAmount();
				double d1;
				
				if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL)
				{
					d1 = attributemodifier2.getAmount();
				}
				else
				{
					d1 = attributemodifier2.getAmount() * 100.0D;
				}
				
				if (d0 > 0.0D)
				{
					components.add(Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1),
							Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.BLUE));
				}
				else if (d0 < 0.0D)
				{
					d1 *= -1.0D;
					components.add(Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1),
							Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.RED));
				}
			}
		}
		
		if (effect != null)
		{
			int cooldown = stack.getOrCreateTag().getCompound("BlockEntityTag").getInt("MaxCooldown");
			String formattedCooldown = StringUtil.formatTickDuration(cooldown);
			components.add(CommonComponents.EMPTY);
			components.add(Component.translatable("tooltip.totems.cooldown", formattedCooldown).withStyle(ChatFormatting.GRAY));
		}
	}
	
	private MobEffectInstance getMobEffect(ItemStack stack)
	{
		return MobEffectInstance.load(stack.getOrCreateTag().getCompound("BlockEntityTag").getCompound("Effect"));
	}
}
