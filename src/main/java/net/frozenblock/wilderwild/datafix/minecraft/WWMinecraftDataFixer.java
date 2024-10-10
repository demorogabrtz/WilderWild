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

package net.frozenblock.wilderwild.datafix.minecraft;

import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.frozenblock.datafixer.api.FabricDataFixerBuilder;
import net.fabricmc.frozenblock.datafixer.api.FabricDataFixes;
import net.fabricmc.frozenblock.datafixer.api.SimpleFixes;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.DisplayLanternComponentizationFix;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.DisplayLanternItemComponentizationFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;

public final class WWMinecraftDataFixer {
	// 3 is 24w09a (components, display lantern fixes)
	// 2 is 1.20.4 (short grass)
	// 1 is 1.20.1 (base version)

	public static final int DATA_VERSION = 3;

	private WWMinecraftDataFixer() {
		throw new UnsupportedOperationException("WWMinecraftDataFixer contains only static declarations.");
	}

	public static void applyDataFixes(final @NotNull ModContainer mod) {
		WWConstants.log("Applying Minecraft-Version-Based DataFixes for Wilder Wild with Data Version " + DATA_VERSION, true);
		var builder = new FabricDataFixerBuilder(DATA_VERSION);
		builder.addSchema(0, FabricDataFixes.getBaseSchema());

		Schema schemaV2 = builder.addSchema(2, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_grass to potted_short_grass", WWConstants.id("potted_grass"), WWConstants.id("potted_short_grass"), schemaV2);

		Schema schemaV3 = builder.addSchema(3, NamespacedSchema::new);
		builder.addFixer(new DisplayLanternComponentizationFix(schemaV3));
		builder.addFixer(new DisplayLanternItemComponentizationFix(schemaV3));

		FabricDataFixes.buildAndRegisterFixer(mod, "minecraft", builder);
		WWConstants.log("Minecraft-Version-Specific DataFixes for Wilder Wild have been applied", true);
	}

}
