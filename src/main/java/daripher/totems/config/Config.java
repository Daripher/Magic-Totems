package daripher.totems.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.registries.ForgeRegistries;

public class Config {
	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	public static class Common {
		public final ConfigValue<Integer> maxEffectAmplifier;
		public final ConfigValue<Integer> minEffectDuration;
		public final ConfigValue<Integer> maxEffectDuration;
		public final ConfigValue<Integer> minCooldown;
		public final ConfigValue<Integer> maxCooldown;
		public final ConfigValue<Boolean> shuffle;
		public final ConfigValue<Boolean> mysteryIcon;
		public final ConfigValue<Boolean> revealAfterUse;
		public final ConfigValue<List<String>> blacklistedEffects;
		public final ConfigValue<List<String>> whitelistedEffects;
		public final ConfigValue<Boolean> excludeNegativeEffects;
		public final ConfigValue<Boolean> playSounds;

		public Common(ForgeConfigSpec.Builder builder) {
			Predicate<Object> positiveOrZeroInteger = o -> o instanceof Integer && (Integer) o >= 0;
			Predicate<Object> positiveInteger = o -> o instanceof Integer && (Integer) o > 0;
			builder.push("effects");
			maxEffectAmplifier = builder.define("max_amplifier", 4, positiveOrZeroInteger);
			minEffectDuration = builder.define("min_duration", 10, positiveInteger);
			maxEffectDuration = builder.define("max_duration", 120, positiveInteger);
			minCooldown = builder.define("min_cooldown", 20, positiveInteger);
			maxCooldown = builder.define("max_cooldown", 240, positiveInteger);
			shuffle = builder.define("shuffle", false);
			mysteryIcon = builder.define("mystery_icon", true);
			revealAfterUse = builder.define("reveal_after_use", true);
			blacklistedEffects = builder.define("blacklist", new ArrayList<String>());
			whitelistedEffects = builder.define("whitelist", new ArrayList<String>());
			excludeNegativeEffects = builder.define("exclude_negative", false);
			playSounds = builder.define("play_sounds", true);
			builder.pop();
		}
	}

	public static class AllowedEffects {
		public static final List<MobEffect> EFFECTS_LIST = new ArrayList<>();
		public static boolean initialized;

		public static void initialize() {
			var effects = ForgeRegistries.MOB_EFFECTS.getEntries().stream().map(Map.Entry::getValue).collect(Collectors.toList());
			var effectWhitelist = Config.COMMON.whitelistedEffects.get();

			if (!effectWhitelist.isEmpty()) {
				var namespaceWhitelist = effectWhitelist.stream().map(s -> s.split(":")).filter(a -> a.length == 2 && a[1].equals("*")).map(a -> a[0]);

				namespaceWhitelist.forEach(namespace -> {
					effects.removeIf(effect -> !getId(effect).getNamespace().equals(namespace));
				});

				effects.removeIf(effect -> !effectWhitelist.contains(getId(effect).toString()));
			} else {
				var effectBlacklist = Config.COMMON.blacklistedEffects.get();

				if (!effectBlacklist.isEmpty()) {
					var namespaceBlacklist = effectBlacklist.stream().map(s -> s.split(":")).filter(a -> a.length == 2 && a[1].equals("*")).map(a -> a[0]);

					namespaceBlacklist.forEach(namespace -> {
						effects.removeIf(effect -> getId(effect).getNamespace().equals(namespace));
					});

					effects.removeIf(effect -> effectBlacklist.contains(getId(effect).toString()));
				}
			}

			if (Config.COMMON.excludeNegativeEffects.get()) {
				effects.removeIf(effect -> effect.getCategory() == MobEffectCategory.HARMFUL);
			}

			EFFECTS_LIST.addAll(effects);
			initialized = true;
		}

		private static @Nullable ResourceLocation getId(MobEffect effect) {
			return ForgeRegistries.MOB_EFFECTS.getKey(effect);
		}
	}

	static {
		Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
