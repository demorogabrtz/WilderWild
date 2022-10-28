package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public final class RegisterResources {

	private RegisterResources() {
		throw new UnsupportedOperationException("RegisterResources contains only static declarations.");
	}

	public static void register() {
		ResourceManagerHelper.registerBuiltinResourcePack(WilderWild.id("new_main_menu"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.DEFAULT_ENABLED);
	}
}