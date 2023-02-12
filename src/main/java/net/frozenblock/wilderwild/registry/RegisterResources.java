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

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public final class RegisterResources {
	private RegisterResources() {
		throw new UnsupportedOperationException("RegisterResources contains only static declarations.");
	}

	public static void register() {
		ResourceManagerHelper.registerBuiltinResourcePack(WilderSharedConstants.id("new_main_menu"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.DEFAULT_ENABLED);
		//ResourceManagerHelper.registerBuiltinResourcePack(WilderSharedConstants.id("old_wilder_wild_panoramas"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.NORMAL);
		//ResourceManagerHelper.registerBuiltinResourcePack(WilderSharedConstants.id("update_1_20_additions"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.NORMAL);
		//WilderFeatureFlags.init();
		//FrozenFeatureFlags.rebuild();
	}
}
