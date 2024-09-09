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

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.render.blockentity.DisplayLanternBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.HangingTendrilBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.SculkSensorBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.StoneChestBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.model.AncientHornProjectileModel;
import net.frozenblock.wilderwild.entity.render.model.CrabModel;
import net.frozenblock.wilderwild.entity.render.model.JellyfishModel;
import net.frozenblock.wilderwild.entity.render.model.OstrichInbredModel;
import net.frozenblock.wilderwild.entity.render.model.OstrichModel;
import net.frozenblock.wilderwild.entity.render.model.ScorchedModel;
import net.frozenblock.wilderwild.entity.render.model.TumbleweedModel;
import net.frozenblock.wilderwild.entity.render.renderer.AncientHornProjectileRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.CrabRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.FireflyRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.JellyfishRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.OstrichRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.ScorchedRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.TumbleweedRenderer;
import net.frozenblock.wilderwild.registry.WWBlockEntities;
import net.frozenblock.wilderwild.registry.WWEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;

@Environment(EnvType.CLIENT)
public class WWModelLayers {
	public static final ModelLayerLocation ANCIENT_HORN_PROJECTILE_LAYER = new ModelLayerLocation(WWConstants.id("ancient_horn_projectile"), "main");
	public static final ModelLayerLocation SCULK_SENSOR = new ModelLayerLocation(WWConstants.id("sculk_sensor"), "main");
	public static final ModelLayerLocation HANGING_TENDRIL = new ModelLayerLocation(WWConstants.id("hanging_tendril"), "main");
	public static final ModelLayerLocation DISPLAY_LANTERN = new ModelLayerLocation(WWConstants.id("display_lantern"), "main");
	public static final ModelLayerLocation STONE_CHEST = new ModelLayerLocation(WWConstants.id("stone_chest"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_LEFT = new ModelLayerLocation(WWConstants.id("double_stone_chest_left"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_RIGHT = new ModelLayerLocation(WWConstants.id("double_stone_chest_right"), "main");
	public static final ModelLayerLocation JELLYFISH = new ModelLayerLocation(WWConstants.id("jellyfish"), "main");
	public static final ModelLayerLocation TUMBLEWEED = new ModelLayerLocation(WWConstants.id("tumbleweed"), "main");
	public static final ModelLayerLocation CRAB = new ModelLayerLocation(WWConstants.id("crab"), "main");
	public static final ModelLayerLocation OSTRICH = new ModelLayerLocation(WWConstants.id("ostrich"), "main");
	public static final ModelLayerLocation OSTRICH_INBRED = new ModelLayerLocation(WWConstants.id("ostrich"), "inbred");
	public static final ModelLayerLocation OSTRICH_SADDLE = new ModelLayerLocation(WWConstants.id("ostrich"), "saddle");
	public static final ModelLayerLocation SCORCHED = new ModelLayerLocation(WWConstants.id("scorched"), "main");


	public static void init() {
		EntityRendererRegistry.register(WWEntities.FIREFLY, FireflyRenderer::new);

		EntityRendererRegistry.register(WWEntities.ANCIENT_HORN_VIBRATION, AncientHornProjectileRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ANCIENT_HORN_PROJECTILE_LAYER, AncientHornProjectileModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.JELLYFISH, JellyfishRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(JELLYFISH, JellyfishModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.TUMBLEWEED, TumbleweedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TUMBLEWEED, TumbleweedModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.CRAB, CrabRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CRAB, CrabModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.OSTRICH, OstrichRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH, OstrichModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_INBRED, OstrichInbredModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_SADDLE, OstrichModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.SCORCHED, ScorchedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCORCHED, ScorchedModel::createSpiderBodyLayer);

		EntityRendererRegistry.register(WWEntities.COCONUT, ThrownItemRenderer::new);

		BlockEntityRenderers.register(BlockEntityType.SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		BlockEntityRenderers.register(BlockEntityType.CALIBRATED_SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntities.HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntities.DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntities.STONE_CHEST, StoneChestBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(STONE_CHEST, StoneChestBlockEntityRenderer::createSingleBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestBlockEntityRenderer::createDoubleBodyLeftLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestBlockEntityRenderer::createDoubleBodyRightLayer);
	}
}