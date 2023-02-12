/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class RegisterWorldgen {
	private RegisterWorldgen() {
		throw new UnsupportedOperationException("RegisterWorldgen contains only static declarations.");
	}

	public static final ResourceKey<Biome> CYPRESS_WETLANDS = register("cypress_wetlands");
	public static final ResourceKey<Biome> JELLYFISH_CAVES = register("jellyfish_caves");
	public static final ResourceKey<Biome> MIXED_FOREST = register("mixed_forest");
	public static final ResourceKey<Biome> WARM_RIVER = register("warm_river");
	public static final ResourceKey<Biome> OASIS = register("oasis");
	public static final ResourceKey<Biome> BIRCH_TAIGA = register("birch_taiga");
	public static final ResourceKey<Biome> FLOWER_FIELD = register("flower_field");
	public static final ResourceKey<Biome> ARID_SAVANNA = register("arid_savanna");
	public static final ResourceKey<Biome> PARCHED_FOREST = register("parched_forest");
	public static final ResourceKey<Biome> ARID_FOREST = register("arid_forest");
	public static final ResourceKey<Biome> SNOWY_OLD_GROWTH_PINE_TAIGA = register("snowy_old_growth_pine_taiga");
	public static final ResourceKey<Biome> OLD_GROWTH_BIRCH_TAIGA = register("old_growth_birch_taiga");
	public static final ResourceKey<Biome> BIRCH_JUNGLE = register("birch_jungle");
	public static final ResourceKey<Biome> SPARSE_BIRCH_JUNGLE = register("sparse_birch_jungle");
	public static final ResourceKey<Biome> OLD_GROWTH_DARK_FOREST = register("old_growth_dark_forest");
	public static final ResourceKey<Biome> DARK_BIRCH_FOREST = register("dark_birch_forest");
	public static final ResourceKey<Biome> SEMI_BIRCH_FOREST = register("semi_birch_forest");

	public static void bootstrap(BootstapContext<Biome> context) {
		context.register(RegisterWorldgen.CYPRESS_WETLANDS, RegisterWorldgen.cypressWetlands(context));
		context.register(RegisterWorldgen.JELLYFISH_CAVES, RegisterWorldgen.jellyfishCaves(context));
		context.register(RegisterWorldgen.MIXED_FOREST, RegisterWorldgen.mixedForest(context));
		context.register(RegisterWorldgen.WARM_RIVER, RegisterWorldgen.warmRiver(context));
		context.register(RegisterWorldgen.OASIS, RegisterWorldgen.oasis(context));
		context.register(RegisterWorldgen.BIRCH_TAIGA, RegisterWorldgen.birchTaiga(context, false));
		context.register(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA, RegisterWorldgen.birchTaiga(context, true));
		context.register(RegisterWorldgen.FLOWER_FIELD, RegisterWorldgen.flowerField(context));
		context.register(RegisterWorldgen.ARID_SAVANNA, RegisterWorldgen.aridSavanna(context));
		context.register(RegisterWorldgen.PARCHED_FOREST, RegisterWorldgen.parchedForest(context));
		context.register(RegisterWorldgen.ARID_FOREST, RegisterWorldgen.aridForest(context));
		context.register(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA, oldGrowthSnowyTaiga(context));
		context.register(RegisterWorldgen.BIRCH_JUNGLE, RegisterWorldgen.birchJungle(context));
		context.register(RegisterWorldgen.SPARSE_BIRCH_JUNGLE, RegisterWorldgen.sparseBirchJungle(context));
		context.register(RegisterWorldgen.OLD_GROWTH_DARK_FOREST, RegisterWorldgen.oldGrowthDarkForest(context));
		context.register(RegisterWorldgen.DARK_BIRCH_FOREST, RegisterWorldgen.darkBirchForest(context));
		context.register(RegisterWorldgen.SEMI_BIRCH_FOREST, RegisterWorldgen.semiBirchForest(context));
	}

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, WilderSharedConstants.id(name));
    }

	public static Biome mixedForest(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addMixedForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.5F)
				.downfall(0.7F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.5F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome cypressWetlands(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		addCypressWetlandsMobs(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addCypressWetlandsFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.6F)
				.downfall(0.7F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4552818)
								.waterFogColor(4552818)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.8F))
								.foliageColorOverride(5877296)
								.grassColorOverride(7979098)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome jellyfishCaves(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addJellyfishCavesFeatures(builder2);
		Music music = Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_JELLYFISH_CAVES);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.8F)
				.downfall(0.4F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(9817343)
								.waterFogColor(6069471)
								.fogColor(0)
								.skyColor(OverworldBiomes.calculateSkyColor(0.8F))
								.ambientLoopSound(RegisterSounds.AMBIENT_JELLYFISH_CAVES_LOOP)
								.ambientAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_JELLYFISH_CAVES_ADDITIONS, 0.0005D))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(music).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome warmRiver(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4)).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 100, 1, 1));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addWarmRiverFeatures(builder2);
		Music music = Musics.GAME;
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(1.5F)
				.downfall(0.0F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.grassColorOverride(12564309)
								.foliageColorOverride(11445290)
								.waterColor(4566514)
								.waterFogColor(267827)
								.skyColor(OverworldBiomes.calculateSkyColor(1.5F))
								.fogColor(12638463)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(music)
								.build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome oasis(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.desertSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addOasisFeatures(builder2);
		Music music = Musics.GAME;
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(2.0F)
				.downfall(0.0F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.grassColorOverride(8569413)
								.foliageColorOverride(3193611)
								.waterColor(4566514)
								.waterFogColor(267827)
								.skyColor(OverworldBiomes.calculateSkyColor(2.0F))
								.fogColor(12638463)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(music)
								.build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome birchTaiga(BootstapContext<Biome> entries, boolean old) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.farmAnimals(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 3));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addBirchTaigaFeatures(builder2, old);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.45F)
				.downfall(0.8F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.45F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome flowerField(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 8, 2, 5));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addFlowerFieldFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_GAME);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.8F)
				.downfall(0.5F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.foliageColorOverride(5877296)
								.skyColor(OverworldBiomes.calculateSkyColor(0.8F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome aridSavanna(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addAridSavannaFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_GAME);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(2.0F)
				.downfall(0.0F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(2.0F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome parchedForest(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addParchedForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(1.35F)
				.downfall(0.2F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(1.35F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome aridForest(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addAridForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(1.75F)
				.downfall(0.05F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(1.75F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome oldGrowthSnowyTaiga(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
		BiomeDefaultFeatures.caveSpawns(builder);
		BiomeDefaultFeatures.monsters(builder, 100, 25, 100, false);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addOldGrowthSnowyTaigaFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.SNOW)
				.temperature(-0.45F)
				.downfall(0.4F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.grassColorOverride(8434839)
								.foliageColorOverride(6332795)
								.skyColor(OverworldBiomes.calculateSkyColor(-0.5F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome birchJungle(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.baseJungleSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addBirchJungleFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.825F)
				.downfall(0.85F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.825F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome sparseBirchJungle(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.baseJungleSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addSparseBirchJungleFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.825F)
				.downfall(0.85F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.825F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome oldGrowthDarkForest(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addOldGrowthDarkForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.7F)
				.downfall(0.8F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.7F))
								.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome darkBirchForest(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addDarkBirchForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.65F)
				.downfall(0.7F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.65F))
								.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome semiBirchForest(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 2, 2));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addSemiBirchForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(0.65F)
				.downfall(0.7F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(12638463)
								.skyColor(OverworldBiomes.calculateSkyColor(0.65F))
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static void addCypressPaths(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH);
	}

	public static void addCypressWetlandsFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FERN_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEAGRASS_CYPRESS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_CYPRESS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED_CYPRESS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER);
		if (WilderSharedConstants.config().fallenLogs()) {
			builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED);
		}
		addCypressPaths(builder);
		addBasicFeatures(builder, CYPRESS_WETLANDS);
		addCypressVegetation(builder);
	}

	public static void addCypressVegetation(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
	}

	public static void addMixedForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_5);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_TREES);
		if (WilderSharedConstants.config().fallenLogs()) {
			builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED);
		}
		addBasicFeatures(builder, MIXED_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
    }

	public static void addJellyfishCavesFeatures(BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_DEEPSLATE_POOL);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_STONE_POOL);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addPlainGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder, true);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addPlainVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_MESOGLEA_PURPLE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_MESOGLEA_BLUE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.MESOGLEA_CLUSTER_PURPLE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.MESOGLEA_CLUSTER_BLUE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.BLUE_MESOGLEA_PILLAR);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PURPLE_MESOGLEA_PILLAR);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.BLUE_MESOGLEA_PATH);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.PURPLE_MESOGLEA_PATH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_CALCITE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST_BLUE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST_PURPLE);
	}

	public static void addWarmRiverFeatures(BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWaterTrees(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH_RIVER);
	}

	public static void addOasisFeatures(BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDesertExtraDecoration(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_DESERT);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.SAND_POOL);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.MESSY_SAND_POOL);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.GRASS_PATH);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_GRASS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_BUSH_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_CACTUS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALMS_OASIS);
	}

	public static void addBirchTaigaFeatures(BiomeGenerationSettings.Builder builder, boolean old) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, old ? WilderPlacedFeatures.TREES_OLD_GROWTH_BIRCH_TAIGA : WilderPlacedFeatures.BIRCH_TAIGA_TREES);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_10);
		if (WilderSharedConstants.config().fallenLogs()) {
			builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_AND_SPRUCE_PLACED);
		}
		addBasicFeatures(builder, BIRCH_TAIGA);
		BiomeDefaultFeatures.addFerns(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addTaigaTrees(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addTaigaGrass(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addCommonBerryBushes(builder);
	}

	public static void addFlowerFieldFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, FLOWER_FIELD);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FLOWER_FIELD);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_FLOWER_FLOWER_FIELD);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FIELD_GRASS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_TALL_GRASS_FF);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FIELD_BUSH_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_FLOWER_FIELD);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED_2);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	public static void addAridSavannaFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_ARID_SAVANNA);
		addBasicFeatures(builder, ARID_SAVANNA);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_CACTUS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_BUSH_PLACED);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.GRASS_PATH_RARE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.ARID_COARSE_PATH);
	}

	public static void addParchedForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_PARCHED_FOREST);
		addBasicFeatures(builder, PARCHED_FOREST);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	public static void addAridForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_ARID_FOREST);
		addBasicFeatures(builder, ARID_FOREST);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_CACTUS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_BUSH_PLACED);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.GRASS_PATH_RARE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.ARID_COARSE_PATH);
	}

	public static void addOldGrowthSnowyTaigaFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SNOWY_OLD_GROWTH_PINE_TAIGA);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addFerns(builder);
		BiomeDefaultFeatures.addMossyStoneBlock(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addGiantTaigaVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_SNOWY_PINE_TAIGA);
	}

	public static void addBirchJungleFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, BIRCH_JUNGLE);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addLightBambooVegetation(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addJungleGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addJungleVines(builder);
		BiomeDefaultFeatures.addJungleMelons(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_JUNGLE);
	}

	public static void addSparseBirchJungleFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SPARSE_BIRCH_JUNGLE);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addJungleGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addJungleVines(builder);
		BiomeDefaultFeatures.addSparseJungleMelons(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SPARSE_BIRCH_JUNGLE);
	}

	public static void addOldGrowthDarkForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED);
		addBasicFeatures(builder, OLD_GROWTH_DARK_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION);
	}

	public static void addDarkBirchForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED);
		addBasicFeatures(builder, DARK_BIRCH_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_BIRCH_FOREST_VEGETATION);
	}

	public static void addSemiBirchForestFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SEMI_BIRCH_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SEMI_BIRCH_AND_OAK);
	}

	private static void addBasicFeatures(BiomeGenerationSettings.Builder builder, ResourceKey<Biome> biome) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		if (biome == CYPRESS_WETLANDS) {
			builder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
		} else {
			BiomeDefaultFeatures.addDefaultSprings(builder);
		}
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
	}

	public static void addCypressWetlandsMobs(MobSpawnSettings.Builder builder) {
		builder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 2, 6));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 12, 4, 5));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 3, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 6, 4, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 4, 4));
		builder.addSpawn(FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "fireflies"), new MobSpawnSettings.SpawnerData(RegisterEntities.FIREFLY, 1, 2, 6));
	}
}
