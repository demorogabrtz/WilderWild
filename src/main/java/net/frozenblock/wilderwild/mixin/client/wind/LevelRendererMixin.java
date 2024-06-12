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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.impl.WilderDripSuspendedParticleInterface;
import net.frozenblock.wilderwild.wind.WilderClientWindManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Inject(method = "addParticleInternal(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("RETURN"))
	private void wilderWild$addParticle(
		ParticleOptions options, boolean force, boolean decreased, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
		CallbackInfoReturnable<Particle> info
	) {
		if (info.getReturnValue() instanceof WilderDripSuspendedParticleInterface dripParticle) {
			dripParticle.wilderWild$setUsesWind(true);
		}
	}

	@Unique
	private static boolean wilderWild$useWind() {
		return AmbienceAndMiscConfig.CLOUD_MOVEMENT && ClientWindManager.shouldUseWind();
	}

	@WrapOperation(
		method = "renderLevel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/LevelRenderer;renderClouds(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V"
		)
	)
	public void wilderWild$changeCloudPosition(
		LevelRenderer instance, PoseStack matrices, Matrix4f projectionMatrix, Matrix4f matrix4f, float tickDelta, double cameraX, double cameraY, double cameraZ,
		Operation<Void> operation
	) {
		boolean useWind = wilderWild$useWind();

		cameraX = useWind ? cameraX - WilderClientWindManager.getCloudX(tickDelta) * 12D
			: cameraX;
		cameraY = useWind ? (float) (cameraY + Mth.clamp(WilderClientWindManager.getCloudY(tickDelta) * 12D, -10D, 10D))
			: cameraY;
		cameraZ = useWind ? cameraZ - WilderClientWindManager.getCloudZ(tickDelta) * 12D
			: cameraZ;

		operation.call(instance, matrices, projectionMatrix, matrix4f, tickDelta, cameraX, cameraY, cameraZ);
	}

}
