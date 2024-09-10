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

import java.util.Map;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;

public final class WWVillagers {
	private WWVillagers() {
		throw new UnsupportedOperationException("WWVillagers contains only static declarations.");
	}

	public static void register() {
		Map<ResourceKey<Biome>, VillagerType> villagerTypeMap = VillagerType.BY_BIOME;
		villagerTypeMap.put(WWWorldgen.CYPRESS_WETLANDS, VillagerType.SWAMP);
		villagerTypeMap.put(WWWorldgen.OASIS, VillagerType.DESERT);
		villagerTypeMap.put(WWWorldgen.FROZEN_CAVES, VillagerType.SNOW);
		villagerTypeMap.put(WWWorldgen.ARID_FOREST, VillagerType.DESERT);
		villagerTypeMap.put(WWWorldgen.ARID_SAVANNA, VillagerType.SAVANNA);
		villagerTypeMap.put(WWWorldgen.PARCHED_FOREST, VillagerType.SAVANNA);
		villagerTypeMap.put(WWWorldgen.BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWWorldgen.SPARSE_BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWWorldgen.BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWWorldgen.TEMPERATE_RAINFOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWWorldgen.DARK_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWWorldgen.MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWWorldgen.DYING_MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWWorldgen.SNOWY_DYING_MIXED_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWWorldgen.SNOWY_DYING_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA, VillagerType.SNOW);
		villagerTypeMap.put(WWWorldgen.FLOWER_FIELD, VillagerType.PLAINS);

		TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
			factories.add(new VillagerTrades.ItemsForEmeralds(WWItems.BAOBAB_NUT, 5, 1, 8, 1));
			factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.CYPRESS_SAPLING.asItem(), 5, 1, 8, 1));
			factories.add(new VillagerTrades.ItemsForEmeralds(WWItems.COCONUT, 5, 1, 8, 1));
			factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.MAPLE_SAPLING.asItem(), 5, 1, 8, 1));
		});
	}
}