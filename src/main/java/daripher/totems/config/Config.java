package daripher.totems.config;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Config
{
	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;
	
	public static class Common
	{
		public final ConfigValue<Integer> maxEffectAmplifier;
		public final ConfigValue<Integer> minEffectDuration;
		public final ConfigValue<Integer> maxEffectDuration;
		public final ConfigValue<Integer> minCooldown;
		public final ConfigValue<Integer> maxCooldown;
		public final ConfigValue<Boolean> shuffle;
		
		public Common(ForgeConfigSpec.Builder builder)
		{
			Predicate<Object> positiveOrZeroInteger = o -> o instanceof Integer && (Integer) o >= 0;
			Predicate<Object> positiveInteger = o -> o instanceof Integer && (Integer) o > 0;
			builder.push("effects");
			maxEffectAmplifier = builder.define("max_amplifier", 4, positiveOrZeroInteger);
			minEffectDuration = builder.define("min_duration", 10, positiveInteger);
			maxEffectDuration = builder.define("max_duration", 120, positiveInteger);
			minCooldown = builder.define("min_cooldown", 20, positiveInteger);
			maxCooldown = builder.define("max_cooldown", 240, positiveInteger);
			shuffle = builder.define("shuffle", true);
			builder.pop();
		}
	}
	
	static
	{
		Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
