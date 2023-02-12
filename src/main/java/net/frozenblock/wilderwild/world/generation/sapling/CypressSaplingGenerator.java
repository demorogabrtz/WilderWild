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

package net.frozenblock.wilderwild.world.generation.sapling;

import net.frozenblock.wilderwild.world.additions.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CypressSaplingGenerator extends AbstractTreeGrower {

    public CypressSaplingGenerator() {
    }

    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
        if (random.nextFloat() > 0.4F) {
            return random.nextFloat() > 0.7F ? WilderTreeConfigured.CYPRESS : WilderTreeConfigured.FUNGUS_CYPRESS;
        }
        return WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING;
    }

}
