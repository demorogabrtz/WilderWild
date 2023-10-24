/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.render.animations.CrabAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class CrabModel<T extends Crab> extends HierarchicalModel<T> {
	private static final float PI_180 = Mth.PI / 180F;
	private static final float DOUBLE_PI = Mth.PI * 2F;
	private static final float FIFTY_RADIANS = 50F * PI_180;

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart main_claw;
	private final ModelPart claw_top;
	private final ModelPart claw_bottom;
	private final ModelPart left_claw;
	private final ModelPart legs;
	private final ModelPart back_right_leg;
	private final ModelPart middle_right_leg;
	private final ModelPart front_right_leg;
	private final ModelPart back_left_leg;
	private final ModelPart middle_left_leg;
	private final ModelPart front_left_leg;

	public float xRot;

	public float scale;

	public CrabModel(@NotNull ModelPart root) {
		this.root = root;

		this.body = root.getChild("body");
		this.torso = body.getChild("torso");
		this.main_claw = this.body.getChild("main_claw");
		this.claw_top = this.main_claw.getChild("claw_top");
		this.claw_bottom = this.main_claw.getChild("claw_bottom");

		this.left_claw = this.body.getChild("left_claw");

		this.legs = root.getChild("legs");
		this.back_right_leg = this.legs.getChild("back_right_leg");
		this.middle_right_leg = this.legs.getChild("middle_right_leg");
		this.front_right_leg = this.legs.getChild("front_right_leg");
		this.back_left_leg = this.legs.getChild("back_left_leg");
		this.middle_left_leg = this.legs.getChild("middle_left_leg");
		this.front_left_leg = this.legs.getChild("front_left_leg");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 2).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
			.texOffs(7, 0).addBox(-4.0F, -4.0F, 5.0F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_claw = body.addOrReplaceChild("left_claw", CubeListBuilder.create().texOffs(16, 12).addBox(1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -1.0F, 4.25F, 0.0F, -0.6981F, 0.2618F));

		PartDefinition main_claw = body.addOrReplaceChild("main_claw", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, -1.5F, 4.25F, 0.0F, 0.6981F, -0.5236F));
		PartDefinition claw_top = main_claw.addOrReplaceChild("claw_top", CubeListBuilder.create().texOffs(0, 12).addBox(-6.0F, -1.5F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));
		PartDefinition claw_bottom = main_claw.addOrReplaceChild("claw_bottom", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, 0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));

		//LEGS
		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));
		PartDefinition back_right_leg = legs.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -2.25F, -0.4876F, -0.0741F, -0.7109F));
		PartDefinition back_left_leg = legs.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -2.25F, -0.4876F, 0.0741F, 0.7109F));
		PartDefinition middle_right_leg = legs.addOrReplaceChild("middle_right_leg", CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -0.25F, -0.0949F, -0.0741F, -0.7109F));
		PartDefinition middle_left_leg = legs.addOrReplaceChild("middle_left_leg", CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -0.25F, -0.0949F, 0.0741F, 0.7109F));
		PartDefinition front_right_leg = legs.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 1.75F, 0.3414F, -0.0741F, -0.7109F));
		PartDefinition front_left_leg = legs.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, 1.75F, 0.3414F, 0.0741F, 0.7109F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	private static void bobClaw(@NotNull ModelPart modelPart, float ageInTicks, float multiplier) {
		modelPart.zRot += multiplier * (Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
		modelPart.yRot += multiplier * (Mth.sin(ageInTicks * 0.067F) * 0.05F);
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.xRot = Mth.lerp(partialTick, entity.prevClimbAnimX, entity.climbAnimX) * 85F;
		this.scale = entity.getScale();
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.hidingAnimationState, CrabAnimations.CRAB_EMERGE_WAIT, ageInTicks);
		this.animate(entity.diggingAnimationState, CrabAnimations.CRAB_DIG, ageInTicks);
		this.animate(entity.emergingAnimationState, CrabAnimations.CRAB_EMERGE, ageInTicks);
		bobClaw(this.main_claw, ageInTicks, 2F);
		bobClaw(this.left_claw, ageInTicks, -2F);

		float movementDelta = Math.min(limbSwingAmount * 4F, 1.0F);
		limbSwing *= 4.75F;
		float fastAngle = limbSwing * 0.3331F;
		float limbSwing5 = Math.min(1F, limbSwingAmount * 5) * 0.5F;
		float fastAngleSin = Math.sin(fastAngle);
		float walkA = Mth.lerp(movementDelta, 0F, ((1F - fastAngleSin) * limbSwing5) - 0.5F);
		float walkB = Mth.lerp(movementDelta, 0F, ((1F + fastAngleSin) * limbSwing5) - 0.5F);

		float legRoll = Math.sin(fastAngle) * 0.4F * limbSwingAmount;
		float lerpedWalkA = Mth.lerp(walkA, -legRoll, FIFTY_RADIANS);
		float lerpedWalkB = Mth.lerp(walkB, legRoll, FIFTY_RADIANS);
		this.back_right_leg.zRot += lerpedWalkA;
		this.middle_right_leg.zRot += lerpedWalkB;
		this.front_right_leg.zRot += lerpedWalkA;

		this.back_left_leg.zRot -= lerpedWalkB;
		this.middle_left_leg.zRot -= lerpedWalkA;
		this.front_left_leg.zRot -= lerpedWalkB;

		this.back_right_leg.y -= walkA;
		this.middle_right_leg.y -= walkB;
		this.front_right_leg.y -= walkA;

		this.back_left_leg.y -= walkB;
		this.middle_left_leg.y -= walkA;
		this.front_left_leg.y -= walkB;

		float fastAngleDelayed = (float) ((limbSwing + Math.PI) * 0.3331F);
		float fastAngleDelayedSin = Math.sin(fastAngleDelayed);
		float walkADelayed = Mth.lerp(movementDelta, 0F, (((1F - fastAngleDelayedSin) * limbSwing5) - 0.5F));
		float walkBDelayed = Mth.lerp(movementDelta, 0F, (((1F + fastAngleDelayedSin) * limbSwing5) - 0.5F));
		this.back_right_leg.x += walkBDelayed;
		this.middle_right_leg.x += walkADelayed;
		this.front_right_leg.x += walkBDelayed;

		this.back_left_leg.x -= walkADelayed;
		this.middle_left_leg.x -= walkBDelayed;
		this.front_left_leg.x -= walkADelayed;

		float climbRotRadians = xRot * PI_180;
		this.body.zRot += legRoll + climbRotRadians;
		this.legs.zRot += climbRotRadians;

		//Attack Anim
		this.body.yRot = Mth.sin(Mth.sqrt(this.attackTime) * (DOUBLE_PI)) * -0.2F;
		float attackSin = Mth.sin(this.attackTime * (float) Math.PI);
		this.main_claw.x += attackSin * 1.5F;
		this.main_claw.xRot += attackSin * -80F * PI_180;
		this.main_claw.yRot += attackSin * 100F * PI_180;
		this.main_claw.zRot += attackSin * 20F * PI_180;
		this.claw_top.zRot += attackSin * 45F * PI_180;
		this.claw_bottom.zRot -= this.claw_top.zRot;
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.translate(0, 1.3F, 0);
		poseStack.mulPose(Axis.YP.rotationDegrees(90F));
		poseStack.scale(this.scale, this.scale, this.scale);
		this.root().render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
