/*
 * Copyright 2023-2024 FrozenBlock
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

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.api.FrozenCeilingHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenSignBlock;
import net.frozenblock.lib.block.api.FrozenWallHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenWallSignBlock;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.item.api.axe.AxeBehaviors;
import net.frozenblock.lib.item.api.bonemeal.BonemealBehaviors;
import net.frozenblock.lib.storage.api.NoInteractionStorage;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.BaobabLeavesBlock;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.FlowerLichenBlock;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.GloryOfTheSnowBlock;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.LeafLitterBlock;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.MilkweedBlock;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.OstrichEggBlock;
import net.frozenblock.wilderwild.block.PalmFrondsBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.SculkSlabBlock;
import net.frozenblock.wilderwild.block.SculkStairBlock;
import net.frozenblock.wilderwild.block.SculkWallBlock;
import net.frozenblock.wilderwild.block.SeedingFlowerBlock;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.WilderBushBlock;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.entity.ai.TermiteManager;
import net.frozenblock.wilderwild.particle.options.LeafParticleOptions;
import net.frozenblock.wilderwild.worldgen.impl.sapling.WWTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class WWBlocks {
	public static final BlockSetType BAOBAB_SET = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(WWConstants.id("baobab"));
	public static final BlockSetType CYPRESS_SET = BlockSetTypeBuilder.copyOf(BlockSetType.BIRCH).register(WWConstants.id("cypress"));
	public static final BlockSetType PALM_SET = BlockSetTypeBuilder.copyOf(BlockSetType.JUNGLE).register(WWConstants.id("palm"));
	public static final BlockSetType MAPLE_SET = BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE).register(WWConstants.id("maple"));
	public static final WoodType BAOBAB_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.ACACIA).register(WWConstants.id("baobab"), BAOBAB_SET);
	public static final WoodType CYPRESS_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.BIRCH).register(WWConstants.id("cypress"), CYPRESS_SET);
	public static final WoodType PALM_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.JUNGLE).register(WWConstants.id("palm"), PALM_SET);
	public static final WoodType MAPLE_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.SPRUCE).register(WWConstants.id("maple"), MAPLE_SET);

	// OTHER (BUILDING BLOCKS)

	public static final Block CHISELED_MUD_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final Block CRACKED_MUD_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final Block MOSSY_MUD_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final Block MOSSY_MUD_BRICK_STAIRS = new StairBlock(
		MOSSY_MUD_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_MUD_BRICKS)
	);
	public static final Block MOSSY_MUD_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_MUD_BRICKS));
	public static final Block MOSSY_MUD_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_MUD_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_MUD_BRICK = BlockFamilies.familyBuilder(MOSSY_MUD_BRICKS)
		.stairs(MOSSY_MUD_BRICK_STAIRS)
		.slab(MOSSY_MUD_BRICK_SLAB)
		.wall(MOSSY_MUD_BRICK_WALL)
		.getFamily();

	public static final ScorchedBlock SCORCHED_SAND = new ScorchedBlock(
		Blocks.SAND.defaultBlockState(),
		true,
		SoundEvents.BRUSH_SAND,
		SoundEvents.BRUSH_SAND_COMPLETED,
		BlockBehaviour.Properties.of()
			.strength(1.5F)
			.sound(WWSoundTypes.SCORCHED_SAND)
			.mapColor(MapColor.SAND)
			.randomTicks()
	);

	public static final ScorchedBlock SCORCHED_RED_SAND = new ScorchedBlock(
		Blocks.RED_SAND.defaultBlockState(),
		true,
		SoundEvents.BRUSH_SAND,
		SoundEvents.BRUSH_SAND_COMPLETED,
		BlockBehaviour.Properties.of()
			.strength(1.5F)
			.sound(WWSoundTypes.SCORCHED_SAND)
			.mapColor(MapColor.COLOR_ORANGE)
			.randomTicks()
	);

	public static final BaobabNutBlock BAOBAB_NUT = new BaobabNutBlock(
		WWTreeGrowers.BAOBAB,
		BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO)
			.sound(WWSoundTypes.BAOBAB_NUT)
	);
	public static final Block POTTED_BAOBAB_NUT = Blocks.flowerPot(BAOBAB_NUT);

	public static final PricklyPearCactusBlock PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS)
			.noCollission()
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);

	public static final WaterloggableSaplingBlock CYPRESS_SAPLING = new WaterloggableSaplingBlock(
		WWTreeGrowers.CYPRESS,
		BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_CYPRESS_SAPLING = Blocks.flowerPot(CYPRESS_SAPLING);

	public static final CoconutBlock COCONUT = new CoconutBlock(
		WWTreeGrowers.PALM,
		BlockBehaviour.Properties.of().instabreak().randomTicks().sound(WWSoundTypes.COCONUT)
	);
	public static final Block POTTED_COCONUT = Blocks.flowerPot(COCONUT);

	public static final SaplingBlock MAPLE_SAPLING = new SaplingBlock(
		WWTreeGrowers.MAPLE,
		BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_MAPLE_SAPLING = Blocks.flowerPot(MAPLE_SAPLING);

	public static final Block CYPRESS_LEAVES = Blocks.leaves(SoundType.GRASS); // in front so the other leaves can have a copy of its settings
	public static final Block BAOBAB_LEAVES = new BaobabLeavesBlock(BlockBehaviour.Properties.ofFullCopy(CYPRESS_LEAVES));
	public static final PalmFrondsBlock PALM_FRONDS = new PalmFrondsBlock(BlockBehaviour.Properties.ofFullCopy(CYPRESS_LEAVES));
	public static final Block YELLOW_MAPLE_LEAVES = new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(CYPRESS_LEAVES).mapColor(MapColor.COLOR_YELLOW));
	public static final Block ORANGE_MAPLE_LEAVES = new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(CYPRESS_LEAVES).mapColor(MapColor.COLOR_ORANGE));
	public static final Block RED_MAPLE_LEAVES = new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(CYPRESS_LEAVES).mapColor(MapColor.COLOR_RED));

	public static final HollowedLogBlock HOLLOWED_OAK_LOG = createHollowedLogBlock(MapColor.WOOD, MapColor.PODZOL);
	public static final HollowedLogBlock HOLLOWED_SPRUCE_LOG =  createHollowedLogBlock(MapColor.PODZOL, MapColor.COLOR_BROWN);
	public static final HollowedLogBlock HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MapColor.SAND, MapColor.QUARTZ);
	public static final HollowedLogBlock HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MapColor.DIRT, MapColor.PODZOL);
	public static final HollowedLogBlock HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MapColor.COLOR_ORANGE, MapColor.STONE);
	public static final HollowedLogBlock HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN);
	public static final HollowedLogBlock HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MapColor.COLOR_RED, MapColor.PODZOL);
	public static final HollowedLogBlock HOLLOWED_CHERRY_LOG = createHollowedLogBlock(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, WWSoundTypes.HOLLOWED_CHERRY_LOG);
	public static final HollowedLogBlock HOLLOWED_CRIMSON_STEM = createHollowedStemBlock(MapColor.CRIMSON_STEM);
	public static final HollowedLogBlock HOLLOWED_WARPED_STEM = createHollowedStemBlock(MapColor.WARPED_STEM);
	public static final HollowedLogBlock HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MapColor.COLOR_ORANGE, MapColor.COLOR_BROWN);
	public static final HollowedLogBlock HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MapColor.COLOR_LIGHT_GRAY, MapColor.STONE);
	public static final HollowedLogBlock HOLLOWED_MAPLE_LOG = createHollowedLogBlock(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_YELLOW);

	// STRIPPED HOLLOWED LOGS
	public static final HollowedLogBlock STRIPPED_HOLLOWED_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_OAK_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_SPRUCE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_SPRUCE_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_BIRCH_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_BIRCH_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CHERRY_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_CHERRY_LOG.defaultMapColor(), WWSoundTypes.HOLLOWED_CHERRY_LOG);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_JUNGLE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_JUNGLE_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_ACACIA_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_ACACIA_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_DARK_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_DARK_OAK_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_MANGROVE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_MANGROVE_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CRIMSON_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_CRIMSON_STEM.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_WARPED_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_WARPED_STEM.defaultMapColor());

	// LEAF LITTER
	public static final LeafLitterBlock OAK_LEAF_LITTER = leafLitter(
		Blocks.OAK_LEAVES,
		WWParticleTypes.OAK_LEAVES,
		0.004F,
		0.0075F,
		0.125F,
		2.5F
	);
	public static final LeafLitterBlock SPRUCE_LEAF_LITTER = leafLitter(
		Blocks.SPRUCE_LEAVES,
		WWParticleTypes.SPRUCE_LEAVES,
		0.004F,
		0.0025F,
		0.125F,
		2F
	);
	public static final LeafLitterBlock BIRCH_LEAF_LITTER = leafLitter(
		Blocks.BIRCH_LEAVES,
		WWParticleTypes.BIRCH_LEAVES,
		0.002F,
		0.0025F,
		0.125F,
		2F
	);
	public static final LeafLitterBlock JUNGLE_LEAF_LITTER = leafLitter(
		Blocks.JUNGLE_LEAVES,
		WWParticleTypes.JUNGLE_LEAVES,
		0.004F,
		0.0075F,
		0.125F,
		3.5F
	);
	public static final LeafLitterBlock ACACIA_LEAF_LITTER = leafLitter(
		Blocks.ACACIA_LEAVES,
		WWParticleTypes.ACACIA_LEAVES,
		0.002F,
		0.0075F,
		0.125F,
		2F
	);
	public static final LeafLitterBlock DARK_OAK_LEAF_LITTER = leafLitter(
		Blocks.DARK_OAK_LEAVES,
		WWParticleTypes.DARK_OAK_LEAVES,
		0.004F,
		0.0075F,
		0.125F,
		2.5F
	);
	public static final LeafLitterBlock MANGROVE_LEAF_LITTER = leafLitter(
		Blocks.MANGROVE_LEAVES,
		WWParticleTypes.MANGROVE_LEAVES,
		0.004F,
		0.0075F,
		0.125F,
		3.5F
	);
	public static final LeafLitterBlock CHERRY_LEAF_LITTER = leafLitter(
		Blocks.CHERRY_LEAVES,
		WWParticleTypes.CHERRY_LEAVES,
		0.004F,
		0.0125F,
		0.125F,
		1F
	);
	public static final LeafLitterBlock AZALEA_LEAF_LITTER = leafLitter(
		Blocks.AZALEA_LEAVES,
		WWParticleTypes.AZALEA_LEAVES,
		0.004F,
		0.0075F,
		0.125F,
		2F
	);
	public static final LeafLitterBlock FLOWERING_AZALEA_LEAF_LITTER = leafLitter(
		Blocks.FLOWERING_AZALEA_LEAVES,
		WWParticleTypes.FLOWERING_AZALEA_LEAVES,
		0.004F,
		0.0075F,
		0.125F,
		2F
	);
	public static final LeafLitterBlock BAOBAB_LEAF_LITTER = leafLitter(
		BAOBAB_LEAVES,
		WWParticleTypes.BAOBAB_LEAVES,
		0.002F,
		0.0075F,
		0.125F,
		2F
	);
	public static final LeafLitterBlock CYPRESS_LEAF_LITTER = leafLitter(
		CYPRESS_LEAVES,
		WWParticleTypes.CYPRESS_LEAVES,
		0.004F,
		0.0025F,
		0.125F,
		2F
	);
	public static final LeafLitterBlock PALM_FROND_LITTER = leafLitter(
		PALM_FRONDS,
		WWParticleTypes.PALM_FRONDS,
		0.001F,
		0.00055F,
		0.125F,
		4.5F
	);
	public static final LeafLitterBlock YELLOW_MAPLE_LEAF_LITTER = leafLitter(YELLOW_MAPLE_LEAVES, WWParticleTypes.YELLOW_MAPLE_LEAVES, 0.04F);
	public static final LeafLitterBlock ORANGE_MAPLE_LEAF_LITTER = leafLitter(ORANGE_MAPLE_LEAVES, WWParticleTypes.ORANGE_MAPLE_LEAVES, 0.04F);
	public static final LeafLitterBlock RED_MAPLE_LEAF_LITTER = leafLitter(RED_MAPLE_LEAVES, WWParticleTypes.RED_MAPLE_LEAVES, 0.04F);

	// SCULK
	public static final SculkStairBlock SCULK_STAIRS = new SculkStairBlock(
		Blocks.SCULK.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.SCULK)
	);

	public static final SculkSlabBlock SCULK_SLAB = new SculkSlabBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.SCULK)
	);

	public static final SculkWallBlock SCULK_WALL = new SculkWallBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.SCULK)
	);

	public static final OsseousSculkBlock OSSEOUS_SCULK = new OsseousSculkBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.SAND)
			.strength(2.0F)
			.sound(WWSoundTypes.OSSEOUS_SCULK)
	);

	public static final HangingTendrilBlock HANGING_TENDRIL = new HangingTendrilBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.SCULK_SENSOR)
			.strength(0.7F)
			.noCollission()
			.noOcclusion()
			.randomTicks()
			.lightLevel(state -> 1)
			.sound(WWSoundTypes.HANGING_TENDRIL)
			.emissiveRendering((state, level, pos) -> HangingTendrilBlock.shouldHavePogLighting(state))
	);

	public static final EchoGlassBlock ECHO_GLASS = new EchoGlassBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS)
			.mapColor(MapColor.COLOR_CYAN)
			.noOcclusion()
			.randomTicks()
			.sound(WWSoundTypes.ECHO_GLASS)
			.instrument(NoteBlockInstrument.HAT)
	);

	// Mesoglea
	public static final MesogleaBlock BLUE_PEARLESCENT_MESOGLEA = mesoglea(
		MapColor.QUARTZ,
		WWParticleTypes.BLUE_PEARLESCENT_HANGING_MESOGLEA,
		true
	);
	public static final MesogleaBlock PURPLE_PEARLESCENT_MESOGLEA = mesoglea(
		MapColor.COLOR_PURPLE,
		WWParticleTypes.PURPLE_PEARLESCENT_HANGING_MESOGLEA,
		true
	);
	public static final MesogleaBlock YELLOW_MESOGLEA = mesoglea(
		MapColor.COLOR_YELLOW,
		WWParticleTypes.YELLOW_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock BLUE_MESOGLEA = mesoglea(
		MapColor.COLOR_LIGHT_BLUE,
		WWParticleTypes.BLUE_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock LIME_MESOGLEA = mesoglea(
		MapColor.COLOR_LIGHT_GREEN,
		WWParticleTypes.LIME_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock RED_MESOGLEA = mesoglea(
		MapColor.COLOR_RED,
		WWParticleTypes.RED_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock PINK_MESOGLEA = mesoglea(
		MapColor.COLOR_PINK,
		WWParticleTypes.PINK_HANGING_MESOGLEA,
		false
	);

	public static final NematocystBlock BLUE_PEARLESCENT_NEMATOCYST = nematocyst(MapColor.QUARTZ);
	public static final NematocystBlock PURPLE_PEARLESCENT_NEMATOCYST = nematocyst(MapColor.COLOR_PURPLE);
	public static final NematocystBlock YELLOW_NEMATOCYST = nematocyst(MapColor.COLOR_YELLOW);
	public static final NematocystBlock BLUE_NEMATOCYST = nematocyst(MapColor.COLOR_BLUE);
	public static final NematocystBlock LIME_NEMATOCYST = nematocyst(MapColor.COLOR_LIGHT_GREEN);
	public static final NematocystBlock RED_NEMATOCYST = nematocyst(MapColor.COLOR_RED);
	public static final NematocystBlock PINK_NEMATOCYST = nematocyst(MapColor.COLOR_PINK);

	// MISC

	public static final TermiteMoundBlock TERMITE_MOUND = new TermiteMoundBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_BROWN)
			.strength(0.3F)
			.sound(WWSoundTypes.TERMITE_MOUND)
			.hasPostProcess(Blocks::always)
			.randomTicks()
	);

	public static final StoneChestBlock STONE_CHEST = new StoneChestBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST)
			.sound(SoundType.DEEPSLATE)
			.strength(35.0F, 12.0F),
		() -> WWBlockEntities.STONE_CHEST
	);

	// PLANTS

	public static final SeedingFlowerBlock SEEDING_DANDELION = new SeedingFlowerBlock(
		MobEffects.SLOW_FALLING,
		12,
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_SEEDING_DANDELION = Blocks.flowerPot(SEEDING_DANDELION);

	public static final FlowerBlock CARNATION = new FlowerBlock(
		MobEffects.REGENERATION,
		12,
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_CARNATION = Blocks.flowerPot(CARNATION);

	public static final FlowerBlock MARIGOLD = new FlowerBlock(
		MobEffects.DAMAGE_RESISTANCE,
		8,
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_MARIGOLD = Blocks.flowerPot(MARIGOLD);

	public static final GloryOfTheSnowBlock GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
			.randomTicks()
	);
	public static final FlowerLichenBlock ALBA_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
			.mapColor(MapColor.QUARTZ)
			.sound(SoundType.VINE)
			.noCollission()
			.offsetType(BlockBehaviour.OffsetType.NONE)
	);
	public static final FlowerLichenBlock PINK_GIANT_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		BlockBehaviour.Properties.ofFullCopy(ALBA_GLORY_OF_THE_SNOW)
			.mapColor(MapColor.CRIMSON_STEM)
	);
	public static final FlowerLichenBlock VIOLET_BEAUTY_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		BlockBehaviour.Properties.ofFullCopy(ALBA_GLORY_OF_THE_SNOW)
			.mapColor(MapColor.COLOR_PURPLE)
	);
	public static final FlowerLichenBlock BLUE_GIANT_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		BlockBehaviour.Properties.ofFullCopy(ALBA_GLORY_OF_THE_SNOW)
			.mapColor(MapColor.COLOR_BLUE)
	);

	public static final TallFlowerBlock DATURA = new TallFlowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUNFLOWER));

	public static final MilkweedBlock MILKWEED = new MilkweedBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.SUNFLOWER)
			.randomTicks()
	);

	public static final Block CATTAIL = new WaterloggableTallFlowerBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH)
			.sound(SoundType.WET_GRASS)
			.strength(0.0F)
			.noOcclusion()
	);

	public static final WaterlilyBlock FLOWERING_LILY_PAD = new WaterlilyBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD)
	);

	public static final AlgaeBlock ALGAE = new AlgaeBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.FROGSPAWN)
			.mapColor(MapColor.PLANT)
			.sound(WWSoundTypes.ALGAE)
	);

	public static final WilderBushBlock BUSH = new WilderBushBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)
			.mapColor(MapColor.PLANT)
			.noOcclusion()
			.randomTicks()
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);
	public static final Block POTTED_BUSH = Blocks.flowerPot(BUSH);

	public static final TumbleweedPlantBlock TUMBLEWEED_PLANT = new TumbleweedPlantBlock(
		BlockBehaviour.Properties.of()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final Block POTTED_TUMBLEWEED_PLANT = Blocks.flowerPot(TUMBLEWEED_PLANT);
	public static final TumbleweedBlock TUMBLEWEED = new TumbleweedBlock(
		BlockBehaviour.Properties.of()
			.instabreak()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final Block POTTED_TUMBLEWEED = Blocks.flowerPot(TUMBLEWEED);

	public static final Block POTTED_BIG_DRIPLEAF = Blocks.flowerPot(Blocks.BIG_DRIPLEAF);
	public static final Block POTTED_SMALL_DRIPLEAF = Blocks.flowerPot(Blocks.SMALL_DRIPLEAF);

	public static final Block POTTED_SHORT_GRASS = Blocks.flowerPot(Blocks.SHORT_GRASS);

	public static final Block POTTED_PRICKLY_PEAR = Blocks.flowerPot(PRICKLY_PEAR_CACTUS);

	public static final ShelfFungusBlock BROWN_SHELF_FUNGUS = new ShelfFungusBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK)
			.lightLevel(state -> 1)
			.randomTicks()
			.noCollission()
			.noOcclusion()
			.sound(WWSoundTypes.MUSHROOM)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final ShelfFungusBlock RED_SHELF_FUNGUS = new ShelfFungusBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK)
			.randomTicks()
			.noCollission()
			.noOcclusion()
			.sound(WWSoundTypes.MUSHROOM)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final PollenBlock POLLEN = new PollenBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
			.mapColor(MapColor.SAND)
			.sound(WWSoundTypes.POLLEN)
			.offsetType(BlockBehaviour.OffsetType.NONE)
	);

	public static final SpongeBudBlock SPONGE_BUD = new SpongeBudBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.SPONGE)
			.strength(0.1F)
			.noCollission()
			.noOcclusion()
			.sound(SoundType.SPONGE)
	);

	public static final OstrichEggBlock OSTRICH_EGG = new OstrichEggBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);

	public static final Block NULL_BLOCK = new Block(
		BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
			.sound(WWSoundTypes.NULL_BLOCK)
	);

	public static final DisplayLanternBlock DISPLAY_LANTERN = new DisplayLanternBlock(
		BlockBehaviour.Properties.of().mapColor(MapColor.METAL).forceSolidOn().strength(3.5F).sound(SoundType.LANTERN)
			.lightLevel(state -> state.getValue(WWBlockStateProperties.DISPLAY_LIGHT))
	);

	public static final GeyserBlock GEYSER = new GeyserBlock(
		BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN)
			.sound(WWSoundTypes.GEYSER)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.lightLevel(blockState -> 2)
			.strength(0.85F)
			.isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false)
			.hasPostProcess(Blocks::always)
			.emissiveRendering(Blocks::always)
	);

	private static final MapColor BAOBAB_PLANKS_COLOR = MapColor.COLOR_ORANGE;

	// WOOD

	public static final Block BAOBAB_PLANKS = new Block(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final StairBlock BAOBAB_STAIRS = new StairBlock(
		BAOBAB_PLANKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(BAOBAB_PLANKS)
	);
	public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(
		BAOBAB_WOOD_TYPE,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_SLAB = new SlabBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_BUTTON = Blocks.woodenButton(BAOBAB_SET);
	public static final PressurePlateBlock BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(
		BAOBAB_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final DoorBlock BAOBAB_DOOR = new DoorBlock(
		BAOBAB_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final TrapDoorBlock BAOBAB_TRAPDOOR = new TrapDoorBlock(
		BAOBAB_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FenceBlock BAOBAB_FENCE = new FenceBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_BAOBAB_LOG = createStrippedHollowedLogBlock(BAOBAB_PLANKS_COLOR);

	private static final MapColor BAOBAB_BARK_COLOR = MapColor.COLOR_BROWN;
	public static final Block BAOBAB_LOG = Blocks.log(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR);
	public static final FrozenSignBlock BAOBAB_SIGN = new FrozenSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor()),
		BAOBAB_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/baobab_sign"))
	);
	public static final FrozenWallSignBlock BAOBAB_WALL_SIGN = new FrozenWallSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.dropsLike(BAOBAB_SIGN),
		BAOBAB_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/baobab_sign"))
	);

	public static final BlockFamily BAOBAB = BlockFamilies.familyBuilder(BAOBAB_PLANKS)
		.button(BAOBAB_BUTTON)
		.slab(BAOBAB_SLAB)
		.stairs(BAOBAB_STAIRS)
		.fence(BAOBAB_FENCE)
		.fenceGate(BAOBAB_FENCE_GATE)
		.pressurePlate(BAOBAB_PRESSURE_PLATE)
		.sign(BAOBAB_SIGN, BAOBAB_WALL_SIGN)
		.door(BAOBAB_DOOR)
		.trapdoor(BAOBAB_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final FrozenCeilingHangingSignBlock BAOBAB_HANGING_SIGN = new FrozenCeilingHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor()),
		BAOBAB_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/baobab_hanging_sign"))
	);
	public static final FrozenWallHangingSignBlock BAOBAB_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.dropsLike(BAOBAB_HANGING_SIGN),
		BAOBAB_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/baobab_hanging_sign"))
	);
	public static final Block STRIPPED_BAOBAB_LOG = Blocks.log(BAOBAB_PLANKS_COLOR, BAOBAB_PLANKS_COLOR);
	public static final RotatedPillarBlock STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock BAOBAB_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(BAOBAB_BARK_COLOR)
	);

	private static final MapColor CYPRESS_PLANKS_COLOR = MapColor.COLOR_LIGHT_GRAY;
	public static final Block CYPRESS_PLANKS = new Block(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final StairBlock CYPRESS_STAIRS = new StairBlock(
		CYPRESS_PLANKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(CYPRESS_PLANKS)
	);
	public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(
		CYPRESS_WOOD_TYPE,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final SlabBlock CYPRESS_SLAB = new SlabBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_BUTTON = Blocks.woodenButton(CYPRESS_SET);
	public static final PressurePlateBlock CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(
		CYPRESS_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final DoorBlock CYPRESS_DOOR = new DoorBlock(
		CYPRESS_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final TrapDoorBlock CYPRESS_TRAPDOOR = new TrapDoorBlock(
		CYPRESS_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FenceBlock CYPRESS_FENCE = new FenceBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CYPRESS_LOG = createStrippedHollowedLogBlock(CYPRESS_PLANKS_COLOR);

	private static final MapColor CYPRESS_BARK_COLOR = MapColor.STONE;
	public static final Block CYPRESS_LOG = Blocks.log(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR);
	public static final FrozenSignBlock CYPRESS_SIGN = new FrozenSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor()),
		CYPRESS_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/cypress_sign"))
	);
	public static final FrozenWallSignBlock CYPRESS_WALL_SIGN = new FrozenWallSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.dropsLike(CYPRESS_SIGN),
		CYPRESS_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/cypress_sign"))
	);

	public static final BlockFamily CYPRESS = BlockFamilies.familyBuilder(CYPRESS_PLANKS)
		.button(CYPRESS_BUTTON)
		.slab(CYPRESS_SLAB)
		.stairs(CYPRESS_STAIRS)
		.fence(CYPRESS_FENCE)
		.fenceGate(CYPRESS_FENCE_GATE)
		.pressurePlate(CYPRESS_PRESSURE_PLATE)
		.sign(CYPRESS_SIGN, CYPRESS_WALL_SIGN)
		.door(CYPRESS_DOOR)
		.trapdoor(CYPRESS_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final FrozenCeilingHangingSignBlock CYPRESS_HANGING_SIGN = new FrozenCeilingHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor()),
		CYPRESS_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/cypress_hanging_sign"))
	);
	public static final FrozenWallHangingSignBlock CYPRESS_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.dropsLike(CYPRESS_HANGING_SIGN),
		CYPRESS_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/cypress_hanging_sign"))
	);

	public static final Block STRIPPED_CYPRESS_LOG = Blocks.log(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR);
	public static final RotatedPillarBlock STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock CYPRESS_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(CYPRESS_BARK_COLOR)
	);

	private static final MapColor PALM_PLANKS_COLOR = MapColor.TERRACOTTA_WHITE;
	public static final Block PALM_PLANKS = new Block(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final StairBlock PALM_STAIRS = new StairBlock(
		PALM_PLANKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(PALM_PLANKS)
	);
	public static final Block PALM_FENCE_GATE = new FenceGateBlock(
		PALM_WOOD_TYPE,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(PALM_PLANKS.defaultMapColor())
	);
	public static final SlabBlock PALM_SLAB = new SlabBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_BUTTON = Blocks.woodenButton(PALM_SET);
	public static final PressurePlateBlock PALM_PRESSURE_PLATE = new PressurePlateBlock(
		PALM_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final DoorBlock PALM_DOOR = new DoorBlock(
		PALM_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final TrapDoorBlock PALM_TRAPDOOR = new TrapDoorBlock(
		PALM_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final FenceBlock PALM_FENCE = new FenceBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_PALM_LOG = createStrippedHollowedLogBlock(PALM_PLANKS_COLOR);

	private static final MapColor PALM_BARK_COLOR = MapColor.COLOR_LIGHT_GRAY;
	public static final Block PALM_LOG = Blocks.log(PALM_PLANKS_COLOR, PALM_BARK_COLOR);
	public static final FrozenSignBlock PALM_SIGN = new FrozenSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(PALM_LOG.defaultMapColor()),
		PALM_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/palm_sign"))
	);
	public static final FrozenWallSignBlock PALM_WALL_SIGN = new FrozenWallSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.dropsLike(PALM_SIGN),
		PALM_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/palm_sign"))
	);

	public static final BlockFamily PALM = BlockFamilies.familyBuilder(PALM_PLANKS)
		.button(PALM_BUTTON)
		.slab(PALM_SLAB)
		.stairs(PALM_STAIRS)
		.fence(PALM_FENCE)
		.fenceGate(PALM_FENCE_GATE)
		.pressurePlate(PALM_PRESSURE_PLATE)
		.sign(PALM_SIGN, PALM_WALL_SIGN)
		.door(PALM_DOOR)
		.trapdoor(PALM_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final FrozenCeilingHangingSignBlock PALM_HANGING_SIGN = new FrozenCeilingHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor()),
		PALM_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/palm_hanging_sign"))
	);
	public static final FrozenWallHangingSignBlock PALM_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.dropsLike(PALM_HANGING_SIGN),
		PALM_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/palm_hanging_sign"))
	);
	public static final Block STRIPPED_PALM_LOG = Blocks.log(PALM_PLANKS_COLOR, PALM_BARK_COLOR);
	public static final RotatedPillarBlock STRIPPED_PALM_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock PALM_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(PALM_BARK_COLOR)
	);
	public static final HollowedLogBlock HOLLOWED_PALM_LOG = createHollowedLogBlock(PALM_PLANKS_COLOR, PALM_BARK_COLOR);

	// MAPLE

	private static final MapColor MAPLE_PLANKS_COLOR = MapColor.COLOR_LIGHT_GRAY;
	public static final Block MAPLE_PLANKS = new Block(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final StairBlock MAPLE_STAIRS = new StairBlock(
		MAPLE_PLANKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MAPLE_PLANKS)
	);
	public static final Block MAPLE_FENCE_GATE = new FenceGateBlock(
		MAPLE_WOOD_TYPE,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final SlabBlock MAPLE_SLAB = new SlabBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_BUTTON = Blocks.woodenButton(MAPLE_SET);
	public static final PressurePlateBlock MAPLE_PRESSURE_PLATE = new PressurePlateBlock(
		MAPLE_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final DoorBlock MAPLE_DOOR = new DoorBlock(
		MAPLE_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final TrapDoorBlock MAPLE_TRAPDOOR = new TrapDoorBlock(
		MAPLE_SET,
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final FenceBlock MAPLE_FENCE = new FenceBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_MAPLE_LOG = createStrippedHollowedLogBlock(MAPLE_PLANKS_COLOR);

	private static final MapColor MAPLE_BARK_COLOR = MapColor.STONE;
	public static final Block MAPLE_LOG = Blocks.log(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR);
	public static final FrozenSignBlock MAPLE_SIGN = new FrozenSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor()),
		MAPLE_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/maple_sign"))
	);
	public static final FrozenWallSignBlock MAPLE_WALL_SIGN = new FrozenWallSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
			.dropsLike(MAPLE_SIGN),
		MAPLE_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/maple_sign"))
	);

	public static final BlockFamily MAPLE = BlockFamilies.familyBuilder(MAPLE_PLANKS)
		.button(MAPLE_BUTTON)
		.slab(MAPLE_SLAB)
		.stairs(MAPLE_STAIRS)
		.fence(MAPLE_FENCE)
		.fenceGate(MAPLE_FENCE_GATE)
		.pressurePlate(MAPLE_PRESSURE_PLATE)
		.sign(MAPLE_SIGN, MAPLE_WALL_SIGN)
		.door(MAPLE_DOOR)
		.trapdoor(MAPLE_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final FrozenCeilingHangingSignBlock MAPLE_HANGING_SIGN = new FrozenCeilingHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor()),
		MAPLE_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/maple_hanging_sign"))
	);
	public static final FrozenWallHangingSignBlock MAPLE_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
			.dropsLike(MAPLE_HANGING_SIGN),
		MAPLE_WOOD_TYPE,
		ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id("blocks/maple_hanging_sign"))
	);

	public static final Block STRIPPED_MAPLE_LOG = Blocks.log(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR);
	public static final RotatedPillarBlock STRIPPED_MAPLE_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock MAPLE_WOOD = new RotatedPillarBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(MAPLE_BARK_COLOR)
	);

	private WWBlocks() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

	public static void registerDecorativeBlocks() {
		registerBlockAfter(Blocks.MUD_BRICKS, "cracked_mud_bricks", CRACKED_MUD_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.MUD_BRICK_WALL, "chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHISELED_MUD_BRICKS, "mossy_mud_bricks", MOSSY_MUD_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_MUD_BRICKS, "mossy_mud_brick_stairs", MOSSY_MUD_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_MUD_BRICK_STAIRS, "mossy_mud_brick_slab", MOSSY_MUD_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_MUD_BRICK_SLAB, "mossy_mud_brick_wall", MOSSY_MUD_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlock("scorched_sand", SCORCHED_SAND);
		registerBlock("scorched_red_sand", SCORCHED_RED_SAND);
	}

	public static void registerWoods() {
		//BAOBAB IN BUILDING BLOCKS
		registerBlockAfter(Items.MANGROVE_BUTTON, "baobab_log", BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_LOG, "baobab_wood", BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_WOOD, "stripped_baobab_log", STRIPPED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_BAOBAB_LOG, "stripped_baobab_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_BAOBAB_WOOD, "baobab_planks", BAOBAB_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_PLANKS, "baobab_stairs", BAOBAB_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_STAIRS, "baobab_slab", BAOBAB_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_SLAB, "baobab_fence", BAOBAB_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_FENCE, "baobab_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_FENCE_GATE, "baobab_door", BAOBAB_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_DOOR, "baobab_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_TRAPDOOR, "baobab_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_PRESSURE_PLATE, "baobab_button", BAOBAB_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//BAOBAB IN NATURE
		registerBlockAfter(Items.MANGROVE_LOG, "baobab_log", BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.MANGROVE_LEAVES, "baobab_leaves", BAOBAB_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BAOBAB_LEAVES, "baobab_leaf_litter", BAOBAB_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);

		//CYPRESS IN BUILDING BLOCKS
		registerBlockAfter(BAOBAB_BUTTON, "cypress_log", CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_LOG, "cypress_wood", CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_WOOD, "stripped_cypress_log", STRIPPED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_CYPRESS_LOG, "stripped_cypress_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_CYPRESS_WOOD, "cypress_planks", CYPRESS_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_PLANKS, "cypress_stairs", CYPRESS_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_STAIRS, "cypress_slab", CYPRESS_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_SLAB, "cypress_fence", CYPRESS_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_FENCE, "cypress_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_FENCE_GATE, "cypress_door", CYPRESS_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_DOOR, "cypress_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_TRAPDOOR, "cypress_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_PRESSURE_PLATE, "cypress_button", CYPRESS_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//CYPRESS IN NATURE
		registerBlockAfter(BAOBAB_LOG, "cypress_log", CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BAOBAB_LEAF_LITTER, "cypress_leaves", CYPRESS_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(CYPRESS_LEAVES, "cypress_leaf_litter", CYPRESS_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);

		//PALM IN BUILDING BLOCKS
		registerBlockAfter(CYPRESS_BUTTON, "palm_log", PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_LOG, "palm_wood", PALM_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_WOOD, "stripped_palm_log", STRIPPED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_PALM_LOG, "stripped_palm_wood", STRIPPED_PALM_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_PALM_WOOD, "palm_planks", PALM_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_PLANKS, "palm_stairs", PALM_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_STAIRS, "palm_slab", PALM_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_SLAB, "palm_fence", PALM_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_FENCE, "palm_fence_gate", PALM_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_FENCE_GATE, "palm_door", PALM_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_DOOR, "palm_trapdoor", PALM_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_TRAPDOOR, "palm_pressure_plate", PALM_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_PRESSURE_PLATE, "palm_button", PALM_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//PALM IN NATURE
		registerBlockAfter(CYPRESS_LOG, "palm_log", PALM_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(CYPRESS_LEAF_LITTER, "palm_fronds", PALM_FRONDS, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(PALM_FRONDS, "palm_frond_litter", PALM_FROND_LITTER, CreativeModeTabs.NATURAL_BLOCKS);

		//MAPLE IN BUILDING BLOCKS
		registerBlockAfter(Blocks.CHERRY_BUTTON, "maple_log", MAPLE_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_LOG, "maple_wood", MAPLE_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_WOOD, "stripped_maple_log", STRIPPED_MAPLE_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_MAPLE_LOG, "stripped_maple_wood", STRIPPED_MAPLE_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_MAPLE_WOOD, "maple_planks", MAPLE_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_PLANKS, "maple_stairs", MAPLE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_STAIRS, "maple_slab", MAPLE_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_SLAB, "maple_fence", MAPLE_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_FENCE, "maple_fence_gate", MAPLE_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_FENCE_GATE, "maple_door", MAPLE_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_DOOR, "maple_trapdoor", MAPLE_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_TRAPDOOR, "maple_pressure_plate", MAPLE_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_PRESSURE_PLATE, "maple_button", MAPLE_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//MAPLE IN NATURE
		registerBlockAfter(Blocks.CHERRY_LOG, "maple_log", MAPLE_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(CHERRY_LEAF_LITTER, "yellow_maple_leaves", YELLOW_MAPLE_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(YELLOW_MAPLE_LEAVES, "yellow_maple_leaf_litter", YELLOW_MAPLE_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(YELLOW_MAPLE_LEAF_LITTER, "orange_maple_leaves", ORANGE_MAPLE_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(ORANGE_MAPLE_LEAVES, "orange_maple_leaf_litter", ORANGE_MAPLE_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(ORANGE_MAPLE_LEAF_LITTER, "red_maple_leaves", RED_MAPLE_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(RED_MAPLE_LEAVES, "red_maple_leaf_litter", RED_MAPLE_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlock("baobab_nut", BAOBAB_NUT);
		registerBlock("potted_baobab_nut", POTTED_BAOBAB_NUT);

		registerBlockAfter(Items.MANGROVE_PROPAGULE, "cypress_sapling", CYPRESS_SAPLING, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_cypress_sapling", POTTED_CYPRESS_SAPLING);

		registerBlock("coconut", COCONUT);
		registerBlock("potted_coconut", POTTED_COCONUT);

		registerBlockAfter(Items.CHERRY_SAPLING, "maple_sapling", MAPLE_SAPLING, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_maple_sapling", POTTED_MAPLE_SAPLING);

		registerBlock("baobab_sign", BAOBAB_SIGN);
		registerBlock("baobab_wall_sign", BAOBAB_WALL_SIGN);
		registerBlock("baobab_hanging_sign", BAOBAB_HANGING_SIGN);
		registerBlock("baobab_wall_hanging_sign", BAOBAB_WALL_HANGING_SIGN);
		registerBlock("cypress_sign", CYPRESS_SIGN);
		registerBlock("cypress_wall_sign", CYPRESS_WALL_SIGN);
		registerBlock("cypress_hanging_sign", CYPRESS_HANGING_SIGN);
		registerBlock("cypress_wall_hanging_sign", CYPRESS_WALL_HANGING_SIGN);
		registerBlock("palm_sign", PALM_SIGN);
		registerBlock("palm_wall_sign", PALM_WALL_SIGN);
		registerBlock("palm_hanging_sign", PALM_HANGING_SIGN);
		registerBlock("palm_wall_hanging_sign", PALM_WALL_HANGING_SIGN);
		registerBlock("maple_sign", MAPLE_SIGN);
		registerBlock("maple_wall_sign", MAPLE_WALL_SIGN);
		registerBlock("maple_hanging_sign", MAPLE_HANGING_SIGN);
		registerBlock("maple_wall_hanging_sign", MAPLE_WALL_HANGING_SIGN);
	}

	public static void registerHollowedLogs() {
		registerBlockAfter(Items.OAK_LOG, "hollowed_oak_log", HOLLOWED_OAK_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_OAK_LOG, "stripped_hollowed_oak_log", STRIPPED_HOLLOWED_OAK_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.SPRUCE_LOG, "hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_SPRUCE_LOG, "stripped_hollowed_spruce_log", STRIPPED_HOLLOWED_SPRUCE_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.BIRCH_LOG, "hollowed_birch_log", HOLLOWED_BIRCH_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_BIRCH_LOG, "stripped_hollowed_birch_log", STRIPPED_HOLLOWED_BIRCH_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.JUNGLE_LOG, "hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_JUNGLE_LOG, "stripped_hollowed_jungle_log", STRIPPED_HOLLOWED_JUNGLE_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.ACACIA_LOG, "hollowed_acacia_log", HOLLOWED_ACACIA_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_ACACIA_LOG, "stripped_hollowed_acacia_log", STRIPPED_HOLLOWED_ACACIA_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.DARK_OAK_LOG, "hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_DARK_OAK_LOG, "stripped_hollowed_dark_oak_log", STRIPPED_HOLLOWED_DARK_OAK_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.CRIMSON_STEM, "hollowed_crimson_stem", HOLLOWED_CRIMSON_STEM, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_CRIMSON_STEM, "stripped_hollowed_crimson_stem", STRIPPED_HOLLOWED_CRIMSON_STEM, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.WARPED_STEM, "hollowed_warped_stem", HOLLOWED_WARPED_STEM, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_WARPED_STEM, "stripped_hollowed_warped_stem", STRIPPED_HOLLOWED_WARPED_STEM, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.MANGROVE_LOG, "hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_MANGROVE_LOG, "stripped_hollowed_mangrove_log", STRIPPED_HOLLOWED_MANGROVE_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.CHERRY_LOG, "hollowed_cherry_log", HOLLOWED_CHERRY_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_CHERRY_LOG, "stripped_hollowed_cherry_log", STRIPPED_HOLLOWED_CHERRY_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockBefore(BAOBAB_WOOD, "hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_BAOBAB_LOG, "stripped_hollowed_baobab_log", STRIPPED_HOLLOWED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_LOG, "hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(CYPRESS_WOOD, "hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_CYPRESS_LOG, "stripped_hollowed_cypress_log", STRIPPED_HOLLOWED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_LOG, "hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(PALM_WOOD, "hollowed_palm_log", HOLLOWED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_PALM_LOG, "stripped_hollowed_palm_log", STRIPPED_HOLLOWED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_LOG, "hollowed_palm_log", HOLLOWED_PALM_LOG, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(MAPLE_WOOD, "hollowed_maple_log", HOLLOWED_MAPLE_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_MAPLE_LOG, "stripped_hollowed_maple_log", STRIPPED_HOLLOWED_MAPLE_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MAPLE_LOG, "hollowed_maple_log", HOLLOWED_MAPLE_LOG, CreativeModeTabs.NATURAL_BLOCKS);
	}

	public static void registerDeepDark() {
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "sculk_wall", SCULK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "sculk_slab", SCULK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "sculk_stairs", SCULK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.SCULK, "osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.SCULK_SENSOR, "hanging_tendril", HANGING_TENDRIL, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("echo_glass", ECHO_GLASS);
	}

	public static void registerPlants() {
		registerBlock("potted_big_dripleaf", POTTED_BIG_DRIPLEAF);
		registerBlock("potted_small_dripleaf", POTTED_SMALL_DRIPLEAF);
		registerBlock("potted_short_grass", POTTED_SHORT_GRASS);
		registerBlockAfter(Items.DANDELION, "seeding_dandelion", SEEDING_DANDELION, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
		registerBlockAfter(Items.CORNFLOWER, "carnation", CARNATION, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_carnation", POTTED_CARNATION);
		registerBlockAfter(CARNATION, "marigold", MARIGOLD, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_marigold", POTTED_MARIGOLD);
		registerBlockBefore(Items.WITHER_ROSE, "glory_of_the_snow", GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "blue_giant_glory_of_the_snow", BLUE_GIANT_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "pink_giant_glory_of_the_snow", PINK_GIANT_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "violet_beauty_glory_of_the_snow", VIOLET_BEAUTY_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "alba_glory_of_the_snow", ALBA_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.PEONY, "datura", DATURA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(DATURA, "milkweed", MILKWEED, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(MILKWEED, "cattail", CATTAIL, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.CACTUS, "tumbleweed_plant", TUMBLEWEED_PLANT, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_tumbleweed_plant", POTTED_TUMBLEWEED_PLANT);
		registerBlockAfter(TUMBLEWEED_PLANT, "tumbleweed", TUMBLEWEED, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_tumbleweed", POTTED_TUMBLEWEED);
		registerBlockAfter(TUMBLEWEED, "bush", BUSH, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_bush", POTTED_BUSH);
		registerBlock("prickly_pear", PRICKLY_PEAR_CACTUS);
		registerBlock("potted_prickly_pear", POTTED_PRICKLY_PEAR);

		registerBlockAfter(Blocks.OAK_LEAVES, "oak_leaf_litter", OAK_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.SPRUCE_LEAVES, "spruce_leaf_litter", SPRUCE_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.BIRCH_LEAVES, "birch_leaf_litter", BIRCH_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.JUNGLE_LEAVES, "jungle_leaf_litter", JUNGLE_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.ACACIA_LEAVES, "acacia_leaf_litter", ACACIA_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.DARK_OAK_LEAVES, "dark_oak_leaf_litter", DARK_OAK_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.MANGROVE_LEAVES, "mangrove_leaf_litter", MANGROVE_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.CHERRY_LEAVES, "cherry_leaf_litter", CHERRY_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.AZALEA_LEAVES, "azalea_leaf_litter", AZALEA_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Blocks.FLOWERING_AZALEA_LEAVES, "flowering_azalea_leaf_litter", FLOWERING_AZALEA_LEAF_LITTER, CreativeModeTabs.NATURAL_BLOCKS);
	}

	public static void registerNotSoPlants() {
		registerBlock("pollen", POLLEN);
		registerBlockAfter(Items.RED_MUSHROOM, "red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.RED_MUSHROOM, "brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		Registry.register(BuiltInRegistries.BLOCK, WWConstants.id("algae"), ALGAE);
		Registry.register(BuiltInRegistries.BLOCK, WWConstants.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
		registerBlockAfter(Items.WET_SPONGE, "sponge_bud", SPONGE_BUD, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SNIFFER_EGG, "ostrich_egg", OSTRICH_EGG, CreativeModeTabs.NATURAL_BLOCKS);
	}

	public static void registerMisc() {
		registerBlockBefore(Items.BEE_NEST, "termite_mound", TERMITE_MOUND, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.GLASS, "null_block", NULL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.CHEST, "stone_chest", STONE_CHEST, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlock("display_lantern", DISPLAY_LANTERN);

		registerBlockBefore(Items.SPONGE, "blue_pearlescent_mesoglea", BLUE_PEARLESCENT_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BLUE_PEARLESCENT_MESOGLEA, "purple_pearlescent_mesoglea", PURPLE_PEARLESCENT_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(PURPLE_PEARLESCENT_MESOGLEA, "blue_mesoglea", BLUE_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BLUE_MESOGLEA, "pink_mesoglea", PINK_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(PINK_MESOGLEA, "red_mesoglea", RED_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(RED_MESOGLEA, "yellow_mesoglea", YELLOW_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(YELLOW_MESOGLEA, "lime_mesoglea", LIME_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(Items.SPONGE, "blue_pearlescent_nematocyst", BLUE_PEARLESCENT_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BLUE_PEARLESCENT_NEMATOCYST, "purple_pearlescent_nematocyst", PURPLE_PEARLESCENT_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(PURPLE_PEARLESCENT_NEMATOCYST, "blue_nematocyst", BLUE_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BLUE_NEMATOCYST, "pink_nematocyst", PINK_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(PINK_NEMATOCYST, "red_nematocyst", RED_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(RED_NEMATOCYST, "yellow_nematocyst", YELLOW_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(YELLOW_NEMATOCYST, "lime_nematocyst", LIME_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockAfter(Items.MAGMA_BLOCK, "geyser", GEYSER, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockBefore(Items.SCULK_SENSOR, "geyser", GEYSER, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	public static void registerBlocks() {
		WWConstants.logWithModId("Registering Blocks for", WWConstants.UNSTABLE_LOGGING);

		registerDecorativeBlocks();
		registerPlants();
		registerWoods();
		registerHollowedLogs();
		registerDeepDark();
		registerNotSoPlants();
		registerMisc();
	}

	private static void registerBlock(String path, Block block) {
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockBefore(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemBefore(comparedItem, path, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockAfter(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, path, block, tabs);
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockItemBefore(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility tabVisibility, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addBefore(comparedItem, block, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerBlockItemAfter(ItemLike comparedItem, String name, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, name, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerBlockItemAfter(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility visibility, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addAfter(comparedItem, block, visibility, tabs);
	}

	private static void actualRegisterBlock(String path, Block block) {
		if (BuiltInRegistries.BLOCK.getOptional(WWConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.BLOCK, WWConstants.id(path), block);
		}
	}

	private static void actualRegisterBlockItem(String path, Block block) {
		if (BuiltInRegistries.ITEM.getOptional(WWConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, WWConstants.id(path), new BlockItem(block, new Item.Properties()));
		}
	}

	@NotNull
	public static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor, SoundType soundType) {
		var settings = BlockBehaviour.Properties.of()
			.mapColor(state -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(soundType)
			.ignitedByLava();

		return new HollowedLogBlock(settings);
	}

	@NotNull
	public static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
		return createHollowedLogBlock(topMapColor, sideMapColor, WWSoundTypes.HOLLOWED_LOG);
	}

	@NotNull
	public static HollowedLogBlock createHollowedStemBlock(MapColor mapColor) {
		return new HollowedLogBlock(BlockBehaviour.Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(WWSoundTypes.HOLLOWED_STEM)
		);
	}

	@NotNull
	public static HollowedLogBlock createStrippedHollowedLogBlock(MapColor mapColor, SoundType soundType) {
		var settings = BlockBehaviour.Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(soundType)
			.ignitedByLava();

		return new HollowedLogBlock(settings);
	}

	@NotNull
	public static HollowedLogBlock createStrippedHollowedLogBlock(MapColor mapColor) {
		return createStrippedHollowedLogBlock(mapColor, WWSoundTypes.HOLLOWED_LOG);
	}

	@NotNull
	public static HollowedLogBlock createStrippedHollowedStemBlock(MapColor mapColor) {
		return new HollowedLogBlock(BlockBehaviour.Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(WWSoundTypes.HOLLOWED_STEM)
		);
	}

	@NotNull
	public static LeafLitterBlock leafLitter(Block sourceBlock, @NotNull ParticleType<LeafParticleOptions> particleType, float litterChance) {
		LeafLitterBlock leafLitterBlock = createLeafLitter(sourceBlock, particleType);
		FallingLeafUtil.registerFallingLeaf(
			sourceBlock,
			leafLitterBlock,
			litterChance,
			particleType,
			0.0225F,
			0.125F,
			3F
		);
		return leafLitterBlock;
	}

	@NotNull
	public static LeafLitterBlock leafLitter(
		Block sourceBlock,
		@NotNull ParticleType<LeafParticleOptions> particleType,
		float litterChance,
		float particleChance,
		float quadSize,
		float particleGravityScale
	) {
		LeafLitterBlock leafLitterBlock = createLeafLitter(sourceBlock, particleType);
		FallingLeafUtil.registerFallingLeaf(
			sourceBlock,
			leafLitterBlock,
			litterChance,
			particleType,
			particleChance,
			quadSize,
			particleGravityScale
		);
		return leafLitterBlock;
	}

	private static @NotNull LeafLitterBlock createLeafLitter(Block sourceBlock, @NotNull ParticleType<LeafParticleOptions> particleType) {
		BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(sourceBlock)
			.randomTicks()
			.noCollission()
			.instabreak()
			.replaceable()
			.pushReaction(PushReaction.DESTROY);

		LeafLitterBlock leafLitterBlock = new LeafLitterBlock(sourceBlock, properties);
		LeafLitterBlock.LeafLitterParticleRegistry.registerLeafParticle(leafLitterBlock, particleType);
		return leafLitterBlock;
	}

	@NotNull
	public static MesogleaBlock mesoglea(@NotNull MapColor mapColor, @NotNull ParticleOptions particleType, boolean pearlescent) {
		MesogleaBlock mesogleaBlock = new MesogleaBlock(
			pearlescent,
			BlockBehaviour.Properties.of()
				.mapColor(mapColor)
				.noOcclusion()
				.strength(0.2F)
				.friction(0.8F)
				.emissiveRendering(Blocks::always)
				.lightLevel(state -> 7)
				.sound(WWSoundTypes.MESOGLEA)
				.isSuffocating(Blocks::never)
				.isViewBlocking(Blocks::never)
				.dynamicShape()
		);
		MesogleaBlock.MesogleaParticleRegistry.registerDripParticle(mesogleaBlock, particleType);
		return mesogleaBlock;
	}

	@NotNull
	public static NematocystBlock nematocyst(@NotNull MapColor mapColor) {
		return new NematocystBlock(
			BlockBehaviour.Properties.of()
				.mapColor(mapColor)
				.noCollission()
				.noOcclusion()
				.emissiveRendering(Blocks::always)
				.lightLevel(state -> 4)
				.sound(WWSoundTypes.NEMATOCYST)
				.pushReaction(PushReaction.DESTROY)
		);
	}

	public static void registerBlockProperties() {
		registerDispenses();
		TermiteManager.Termite.addDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
		TermiteManager.Termite.addDegradable(STRIPPED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		TermiteManager.Termite.addDegradable(HOLLOWED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		TermiteManager.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

		TermiteManager.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
		TermiteManager.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

		TermiteManager.Termite.addDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
		TermiteManager.Termite.addDegradable(STRIPPED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		TermiteManager.Termite.addDegradable(HOLLOWED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		TermiteManager.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

		TermiteManager.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
		TermiteManager.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

		TermiteManager.Termite.addDegradable(PALM_LOG, STRIPPED_PALM_LOG);
		TermiteManager.Termite.addDegradable(STRIPPED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		TermiteManager.Termite.addDegradable(HOLLOWED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		TermiteManager.Termite.addDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);

		TermiteManager.Termite.addNaturalDegradable(PALM_LOG, STRIPPED_PALM_LOG);
		TermiteManager.Termite.addNaturalDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);

		TermiteManager.Termite.addDegradable(MAPLE_LOG, STRIPPED_MAPLE_LOG);
		TermiteManager.Termite.addDegradable(STRIPPED_MAPLE_LOG, STRIPPED_HOLLOWED_MAPLE_LOG);
		TermiteManager.Termite.addDegradable(HOLLOWED_MAPLE_LOG, STRIPPED_HOLLOWED_MAPLE_LOG);
		TermiteManager.Termite.addDegradable(MAPLE_WOOD, STRIPPED_MAPLE_WOOD);

		TermiteManager.Termite.addNaturalDegradable(MAPLE_LOG, STRIPPED_MAPLE_LOG);
		TermiteManager.Termite.addNaturalDegradable(MAPLE_WOOD, STRIPPED_MAPLE_WOOD);

		TermiteManager.Termite.addDegradable(BUSH, Blocks.DEAD_BUSH);

		var sign = (FabricBlockEntityType) BlockEntityType.SIGN;
		var hangingSign = (FabricBlockEntityType) BlockEntityType.HANGING_SIGN;

		sign.addSupportedBlock(BAOBAB_SIGN);
		sign.addSupportedBlock(BAOBAB_WALL_SIGN);
		sign.addSupportedBlock(CYPRESS_SIGN);
		sign.addSupportedBlock(CYPRESS_WALL_SIGN);
		sign.addSupportedBlock(PALM_SIGN);
		sign.addSupportedBlock(PALM_WALL_SIGN);
		sign.addSupportedBlock(MAPLE_SIGN);
		sign.addSupportedBlock(MAPLE_WALL_SIGN);

		hangingSign.addSupportedBlock(BAOBAB_HANGING_SIGN);
		hangingSign.addSupportedBlock(BAOBAB_WALL_HANGING_SIGN);
		hangingSign.addSupportedBlock(CYPRESS_HANGING_SIGN);
		hangingSign.addSupportedBlock(CYPRESS_WALL_HANGING_SIGN);
		hangingSign.addSupportedBlock(PALM_HANGING_SIGN);
		hangingSign.addSupportedBlock(PALM_WALL_HANGING_SIGN);
		hangingSign.addSupportedBlock(MAPLE_HANGING_SIGN);
		hangingSign.addSupportedBlock(MAPLE_WALL_HANGING_SIGN);

		registerStrippable();
		registerComposting();
		registerFlammability();
		registerFuels();
		registerBonemeal();
		registerAxe();
		registerInventories();
	}

	private static void registerDispenses() {
		DispenserBlock.registerBehavior(TUMBLEWEED, new DefaultDispenseItemBehavior() {
			@Override
			@NotNull
			public ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
				Level level = source.level();
				Direction direction = source.state().getValue(DispenserBlock.FACING);
				Vec3 position = source.center().add(direction.getStepX(), direction.getStepY(), direction.getStepZ());
				Tumbleweed tumbleweed = new Tumbleweed(WWEntities.TUMBLEWEED, level);
				Vec3 vec3 = new Vec3(direction.getStepX(), direction.getStepY() + 0.1D, direction.getStepZ()).normalize().add(level.random.triangle(0D, 0.0172275D * 6D), level.random.triangle(0D, 0.0172275D * 6D), level.random.triangle(0D, 0.0172275D * 6D)).scale(1.1D);
				tumbleweed.setDeltaMovement(vec3);
				tumbleweed.setPos(position);
				level.addFreshEntity(tumbleweed);
				stack.shrink(1);
				return stack;
			}
		});
	}

	private static void registerStrippable() {
		StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
		StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
		StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
		StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
		StrippableBlockRegistry.register(PALM_LOG, STRIPPED_PALM_LOG);
		StrippableBlockRegistry.register(PALM_WOOD, STRIPPED_PALM_WOOD);
		StrippableBlockRegistry.register(MAPLE_LOG, STRIPPED_MAPLE_LOG);
		StrippableBlockRegistry.register(MAPLE_WOOD, STRIPPED_MAPLE_WOOD);

		StrippableBlockRegistry.register(HOLLOWED_ACACIA_LOG, STRIPPED_HOLLOWED_ACACIA_LOG);
		StrippableBlockRegistry.register(HOLLOWED_BIRCH_LOG, STRIPPED_HOLLOWED_BIRCH_LOG);
		StrippableBlockRegistry.register(HOLLOWED_CHERRY_LOG, STRIPPED_HOLLOWED_CHERRY_LOG);
		StrippableBlockRegistry.register(HOLLOWED_DARK_OAK_LOG, STRIPPED_HOLLOWED_DARK_OAK_LOG);
		StrippableBlockRegistry.register(HOLLOWED_OAK_LOG, STRIPPED_HOLLOWED_OAK_LOG);
		StrippableBlockRegistry.register(HOLLOWED_SPRUCE_LOG, STRIPPED_HOLLOWED_SPRUCE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_JUNGLE_LOG, STRIPPED_HOLLOWED_JUNGLE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_MANGROVE_LOG, STRIPPED_HOLLOWED_MANGROVE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_CRIMSON_STEM, STRIPPED_HOLLOWED_CRIMSON_STEM);
		StrippableBlockRegistry.register(HOLLOWED_WARPED_STEM, STRIPPED_HOLLOWED_WARPED_STEM);
		StrippableBlockRegistry.register(HOLLOWED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		StrippableBlockRegistry.register(HOLLOWED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		StrippableBlockRegistry.register(HOLLOWED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		StrippableBlockRegistry.register(HOLLOWED_MAPLE_LOG, STRIPPED_HOLLOWED_MAPLE_LOG);
	}

	private static void registerComposting() {
		CompostingChanceRegistry.INSTANCE.add(CARNATION, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(CATTAIL, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(DATURA, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(MILKWEED, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(MARIGOLD, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(BROWN_SHELF_FUNGUS, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(RED_SHELF_FUNGUS, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(PALM_FRONDS, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(YELLOW_MAPLE_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ORANGE_MAPLE_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(RED_MAPLE_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BAOBAB_NUT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(MAPLE_SAPLING, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(WWItems.COCONUT, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(WWItems.SPLIT_COCONUT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(BLUE_GIANT_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ALBA_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(PINK_GIANT_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(VIOLET_BEAUTY_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BUSH, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(OAK_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(SPRUCE_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(BIRCH_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(JUNGLE_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(ACACIA_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(DARK_OAK_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(MANGROVE_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(CHERRY_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(AZALEA_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(FLOWERING_AZALEA_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(PALM_FROND_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(YELLOW_MAPLE_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(ORANGE_MAPLE_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(RED_MAPLE_LEAF_LITTER, 0.1F);
	}

	private static void registerFlammability() {
		WWConstants.logWithModId("Registering Flammability for", WWConstants.UNSTABLE_LOGGING);
		var flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableBlockRegistry.add(POLLEN, 100, 60);
		flammableBlockRegistry.add(SEEDING_DANDELION, 100, 60);
		flammableBlockRegistry.add(CARNATION, 100, 60);
		flammableBlockRegistry.add(CATTAIL, 100, 60);
		flammableBlockRegistry.add(DATURA, 100, 60);
		flammableBlockRegistry.add(MILKWEED, 100, 60);
		flammableBlockRegistry.add(MARIGOLD, 100, 60);
		flammableBlockRegistry.add(GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(BLUE_GIANT_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(PINK_GIANT_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(VIOLET_BEAUTY_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(VIOLET_BEAUTY_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(TUMBLEWEED, 100, 60);
		flammableBlockRegistry.add(TUMBLEWEED_PLANT, 100, 60);
		flammableBlockRegistry.add(BUSH, 90, 40);

		flammableBlockRegistry.add(HOLLOWED_BIRCH_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_CHERRY_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_ACACIA_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_JUNGLE_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_DARK_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_MANGROVE_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_CHERRY_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_SPRUCE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_BIRCH_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_CHERRY_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_ACACIA_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_JUNGLE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_DARK_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_MANGROVE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_SPRUCE_LOG, 5, 5);

		flammableBlockRegistry.add(HOLLOWED_BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(BAOBAB_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_BAOBAB_WOOD, 5, 5);
		flammableBlockRegistry.add(BAOBAB_PLANKS, 5, 20);
		flammableBlockRegistry.add(BAOBAB_STAIRS, 5, 20);
		flammableBlockRegistry.add(BAOBAB_DOOR, 5, 20);
		flammableBlockRegistry.add(BAOBAB_FENCE, 5, 20);
		flammableBlockRegistry.add(BAOBAB_SLAB, 5, 20);
		flammableBlockRegistry.add(BAOBAB_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(BAOBAB_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(BAOBAB_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(BAOBAB_LEAVES, 100, 60);
		flammableBlockRegistry.add(BAOBAB_BUTTON, 5, 20);
		flammableBlockRegistry.add(BAOBAB_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(HOLLOWED_CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(CYPRESS_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_CYPRESS_WOOD, 5, 5);
		flammableBlockRegistry.add(CYPRESS_PLANKS, 5, 20);
		flammableBlockRegistry.add(CYPRESS_STAIRS, 5, 20);
		flammableBlockRegistry.add(CYPRESS_DOOR, 5, 20);
		flammableBlockRegistry.add(CYPRESS_FENCE, 5, 20);
		flammableBlockRegistry.add(CYPRESS_SLAB, 5, 20);
		flammableBlockRegistry.add(CYPRESS_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(CYPRESS_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(CYPRESS_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(CYPRESS_LEAVES, 100, 60);
		flammableBlockRegistry.add(CYPRESS_BUTTON, 5, 20);
		flammableBlockRegistry.add(CYPRESS_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(HOLLOWED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(PALM_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(PALM_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_PALM_WOOD, 5, 5);
		flammableBlockRegistry.add(PALM_PLANKS, 5, 20);
		flammableBlockRegistry.add(PALM_STAIRS, 5, 20);
		flammableBlockRegistry.add(PALM_DOOR, 5, 20);
		flammableBlockRegistry.add(PALM_FENCE, 5, 20);
		flammableBlockRegistry.add(PALM_SLAB, 5, 20);
		flammableBlockRegistry.add(PALM_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(PALM_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(PALM_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(PALM_FRONDS, 100, 60);
		flammableBlockRegistry.add(PALM_BUTTON, 5, 20);
		flammableBlockRegistry.add(PALM_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(HOLLOWED_MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(MAPLE_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_MAPLE_WOOD, 5, 5);
		flammableBlockRegistry.add(MAPLE_PLANKS, 5, 20);
		flammableBlockRegistry.add(MAPLE_STAIRS, 5, 20);
		flammableBlockRegistry.add(MAPLE_DOOR, 5, 20);
		flammableBlockRegistry.add(MAPLE_FENCE, 5, 20);
		flammableBlockRegistry.add(MAPLE_SLAB, 5, 20);
		flammableBlockRegistry.add(MAPLE_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(MAPLE_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(MAPLE_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAVES, 100, 60);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAVES, 100, 60);
		flammableBlockRegistry.add(RED_MAPLE_LEAVES, 100, 60);
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(RED_MAPLE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(MAPLE_BUTTON, 5, 20);
		flammableBlockRegistry.add(MAPLE_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(OAK_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(SPRUCE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(BIRCH_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(JUNGLE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(ACACIA_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(DARK_OAK_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(MANGROVE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(CHERRY_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(AZALEA_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(FLOWERING_AZALEA_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(BAOBAB_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(CYPRESS_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(PALM_FROND_LITTER, 200, 60);
	}

	private static void registerFuels() {
		WWConstants.logWithModId("Registering Fuels for", WWConstants.UNSTABLE_LOGGING);
		FuelRegistry registry = FuelRegistry.INSTANCE;

		registry.add(WWItems.BAOBAB_BOAT, 1200);
		registry.add(WWItems.BAOBAB_CHEST_BOAT, 1200);
		registry.add(BAOBAB_LOG.asItem(), 300);
		registry.add(STRIPPED_BAOBAB_LOG.asItem(), 300);
		registry.add(BAOBAB_WOOD.asItem(), 300);
		registry.add(STRIPPED_BAOBAB_WOOD.asItem(), 300);
		registry.add(BAOBAB_PLANKS.asItem(), 300);
		registry.add(BAOBAB_SLAB.asItem(), 150);
		registry.add(BAOBAB_STAIRS.asItem(), 300);
		registry.add(BAOBAB_PRESSURE_PLATE.asItem(), 300);
		registry.add(BAOBAB_BUTTON.asItem(), 100);
		registry.add(BAOBAB_TRAPDOOR.asItem(), 300);
		registry.add(BAOBAB_FENCE_GATE.asItem(), 300);
		registry.add(BAOBAB_FENCE.asItem(), 300);
		registry.add(WWItems.BAOBAB_SIGN, 300);
		registry.add(WWItems.BAOBAB_HANGING_SIGN, 800);
		registry.add(WWItems.BAOBAB_NUT, 100);

		registry.add(WWItems.CYPRESS_BOAT, 1200);
		registry.add(WWItems.CYPRESS_CHEST_BOAT, 1200);
		registry.add(CYPRESS_LOG.asItem(), 300);
		registry.add(STRIPPED_CYPRESS_LOG.asItem(), 300);
		registry.add(CYPRESS_WOOD.asItem(), 300);
		registry.add(STRIPPED_CYPRESS_WOOD.asItem(), 300);
		registry.add(CYPRESS_PLANKS.asItem(), 300);
		registry.add(CYPRESS_SLAB.asItem(), 150);
		registry.add(CYPRESS_STAIRS.asItem(), 300);
		registry.add(CYPRESS_PRESSURE_PLATE.asItem(), 300);
		registry.add(CYPRESS_BUTTON.asItem(), 100);
		registry.add(CYPRESS_TRAPDOOR.asItem(), 300);
		registry.add(CYPRESS_FENCE_GATE.asItem(), 300);
		registry.add(CYPRESS_FENCE.asItem(), 300);
		registry.add(WWItems.CYPRESS_SIGN, 300);
		registry.add(WWItems.CYPRESS_HANGING_SIGN, 800);
		registry.add(CYPRESS_SAPLING.asItem(), 100);

		registry.add(WWItems.PALM_BOAT, 1200);
		registry.add(WWItems.PALM_CHEST_BOAT, 1200);
		registry.add(PALM_LOG.asItem(), 300);
		registry.add(STRIPPED_PALM_LOG.asItem(), 300);
		registry.add(PALM_WOOD.asItem(), 300);
		registry.add(STRIPPED_PALM_WOOD.asItem(), 300);
		registry.add(PALM_PLANKS.asItem(), 300);
		registry.add(PALM_SLAB.asItem(), 150);
		registry.add(PALM_STAIRS.asItem(), 300);
		registry.add(PALM_PRESSURE_PLATE.asItem(), 300);
		registry.add(PALM_BUTTON.asItem(), 100);
		registry.add(PALM_TRAPDOOR.asItem(), 300);
		registry.add(PALM_FENCE_GATE.asItem(), 300);
		registry.add(PALM_FENCE.asItem(), 300);
		registry.add(WWItems.PALM_SIGN, 300);
		registry.add(WWItems.PALM_HANGING_SIGN, 800);
		registry.add(WWItems.COCONUT, 150); // COCONUT OIL IS KNOWN TO BE FLAMMABLE :)
		registry.add(WWItems.SPLIT_COCONUT, 75);

		registry.add(WWItems.MAPLE_BOAT, 1200);
		registry.add(WWItems.MAPLE_CHEST_BOAT, 1200);
		registry.add(MAPLE_LOG.asItem(), 300);
		registry.add(STRIPPED_MAPLE_LOG.asItem(), 300);
		registry.add(MAPLE_WOOD.asItem(), 300);
		registry.add(STRIPPED_MAPLE_WOOD.asItem(), 300);
		registry.add(MAPLE_PLANKS.asItem(), 300);
		registry.add(MAPLE_SLAB.asItem(), 150);
		registry.add(MAPLE_STAIRS.asItem(), 300);
		registry.add(MAPLE_PRESSURE_PLATE.asItem(), 300);
		registry.add(MAPLE_BUTTON.asItem(), 100);
		registry.add(MAPLE_TRAPDOOR.asItem(), 300);
		registry.add(MAPLE_FENCE_GATE.asItem(), 300);
		registry.add(MAPLE_FENCE.asItem(), 300);
		registry.add(WWItems.MAPLE_SIGN, 300);
		registry.add(WWItems.MAPLE_HANGING_SIGN, 800);
		registry.add(MAPLE_SAPLING.asItem(), 100);

		registry.add(HOLLOWED_WARPED_STEM.asItem(), 300);
		registry.add(HOLLOWED_CRIMSON_STEM.asItem(), 300);
		registry.add(HOLLOWED_MANGROVE_LOG.asItem(), 300);
		registry.add(HOLLOWED_ACACIA_LOG.asItem(), 300);
		registry.add(HOLLOWED_JUNGLE_LOG.asItem(), 300);
		registry.add(HOLLOWED_DARK_OAK_LOG.asItem(), 300);
		registry.add(HOLLOWED_SPRUCE_LOG.asItem(), 300);
		registry.add(HOLLOWED_CHERRY_LOG.asItem(), 300);
		registry.add(HOLLOWED_BIRCH_LOG.asItem(), 300);
		registry.add(HOLLOWED_BAOBAB_LOG.asItem(), 300);
		registry.add(HOLLOWED_CYPRESS_LOG.asItem(), 300);
		registry.add(HOLLOWED_PALM_LOG.asItem(), 300);
		registry.add(HOLLOWED_MAPLE_LOG.asItem(), 300);

		registry.add(STRIPPED_HOLLOWED_WARPED_STEM.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_CRIMSON_STEM.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_MANGROVE_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_ACACIA_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_JUNGLE_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_SPRUCE_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_CHERRY_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_BIRCH_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_BAOBAB_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_CYPRESS_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_PALM_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_MAPLE_LOG.asItem(), 300);

		registry.add(TUMBLEWEED.asItem(), 150);
		registry.add(TUMBLEWEED_PLANT.asItem(), 150);
	}

	private static void registerBonemeal() {
		BonemealBehaviors.register(
			Blocks.LILY_PAD,
			new BonemealBehaviors.BonemealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return true;
				}

				@Override
				public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, FLOWERING_LILY_PAD.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);

		BonemealBehaviors.register(
			Blocks.DANDELION,
			new BonemealBehaviors.BonemealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return true;
				}

				@Override
				public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, SEEDING_DANDELION.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);
	}

	private static void registerAxe() {
		AxeBehaviors.register(Blocks.OAK_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_OAK_LOG, false));
		AxeBehaviors.register(Blocks.BIRCH_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_BIRCH_LOG, false));
		AxeBehaviors.register(Blocks.CHERRY_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_CHERRY_LOG, false));
		AxeBehaviors.register(Blocks.SPRUCE_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_SPRUCE_LOG, false));
		AxeBehaviors.register(Blocks.DARK_OAK_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_DARK_OAK_LOG, false));
		AxeBehaviors.register(Blocks.JUNGLE_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_JUNGLE_LOG, false));
		AxeBehaviors.register(Blocks.ACACIA_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_ACACIA_LOG, false));
		AxeBehaviors.register(Blocks.MANGROVE_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_MANGROVE_LOG, false));
		AxeBehaviors.register(Blocks.CRIMSON_STEM, HollowedLogBlock.createHollowBehavior(HOLLOWED_CRIMSON_STEM, true));
		AxeBehaviors.register(Blocks.WARPED_STEM, HollowedLogBlock.createHollowBehavior(HOLLOWED_WARPED_STEM, true));
		AxeBehaviors.register(BAOBAB_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_BAOBAB_LOG, false));
		AxeBehaviors.register(CYPRESS_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_CYPRESS_LOG, false));
		AxeBehaviors.register(PALM_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_PALM_LOG, false));
		AxeBehaviors.register(MAPLE_LOG, HollowedLogBlock.createHollowBehavior(HOLLOWED_MAPLE_LOG, false));
		//STRIPPED
		AxeBehaviors.register(Blocks.STRIPPED_OAK_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_OAK_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_BIRCH_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_BIRCH_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_CHERRY_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_CHERRY_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_SPRUCE_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_SPRUCE_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_DARK_OAK_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_DARK_OAK_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_JUNGLE_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_JUNGLE_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_ACACIA_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_ACACIA_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_MANGROVE_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_MANGROVE_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_CRIMSON_STEM, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_CRIMSON_STEM, true));
		AxeBehaviors.register(Blocks.STRIPPED_WARPED_STEM, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_WARPED_STEM, true));
		AxeBehaviors.register(STRIPPED_BAOBAB_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_BAOBAB_LOG, false));
		AxeBehaviors.register(STRIPPED_CYPRESS_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_CYPRESS_LOG, false));
		AxeBehaviors.register(STRIPPED_PALM_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_PALM_LOG, false));
		AxeBehaviors.register(STRIPPED_MAPLE_LOG, HollowedLogBlock.createHollowBehavior(STRIPPED_HOLLOWED_MAPLE_LOG, false));
	}

	private static void registerInventories() {
		ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(), STONE_CHEST);
	}
}