package daripher.totems.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import daripher.totems.TotemsMod;
import daripher.totems.block.entity.TotemBlockEntity;
import daripher.totems.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class TotemEffectRenderer implements BlockEntityRenderer<TotemBlockEntity> {
	private static final ResourceLocation MYSTERY_EFFECT_ICON = new ResourceLocation(TotemsMod.MOD_ID, "textures/mob_effect/mystery_effect.png");

	public TotemEffectRenderer(BlockEntityRendererProvider.Context ctx) {
	}

	@Override
	public void render(TotemBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int p_112311_, int p_112312_) {
		if (entity.getEffect() == null) {
			return;
		}

		if (entity.getCooldown() > 0) {
			return;
		}

		var minecraft = Minecraft.getInstance();
		var mobEffectTextureManager = minecraft.getMobEffectTextures();
		var effect = entity.getEffect();
		var effectTexture = mobEffectTextureManager.get(effect.getEffect());
		ResourceLocation effectTextureLocation;
		var effectHidden = Config.COMMON.mysteryIcon.get() && entity.isEffectHidden();

		if (effectHidden) {
			effectTextureLocation = MYSTERY_EFFECT_ICON;
		} else {
			effectTextureLocation = effectTexture.atlas().location();
		}

		RenderSystem.setShaderTexture(0, effectTextureLocation);
		var renderType = RenderType.entityCutout(effectTextureLocation);
		var iconSize = 0.5F;
		var y1 = 1 - iconSize / 2;
		var y2 = y1 + iconSize;
		var x1 = -iconSize / 2;
		var x2 = x1 + iconSize;
		var z1 = 0.75F;
		var z2 = z1;
		var u1 = effectHidden ? 1 : effectTexture.getU0();
		var u2 = effectHidden ? 0 : effectTexture.getU1();
		var v1 = effectHidden ? 0 : effectTexture.getV0();
		var v2 = effectHidden ? 1 : effectTexture.getV1();
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.0F, 0.5F);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.getEffectAnimation(partialTicks)));
		renderQuad(poseStack.last(), bufferSource.getBuffer(renderType), x1, x2, y1, y2, z1, z2, u1, u2, v1, v2);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		renderQuad(poseStack.last(), bufferSource.getBuffer(renderType), x1, x2, y1, y2, z1, z2, u1, u2, v1, v2);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		renderQuad(poseStack.last(), bufferSource.getBuffer(renderType), x1, x2, y1, y2, z1, z2, u1, u2, v1, v2);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		renderQuad(poseStack.last(), bufferSource.getBuffer(renderType), x1, x2, y1, y2, z1, z2, u1, u2, v1, v2);
		poseStack.popPose();
	}

	@Override
	public boolean shouldRenderOffScreen(TotemBlockEntity entity) {
		return true;
	}

	private static void renderQuad(PoseStack.Pose pose, VertexConsumer buffer, float x1, float x2, float y1, float y2, float z1, float z2, float u1, float u2, float v1,
			float v2) {
		addVertex(pose.pose(), pose.normal(), buffer, x1, y2, z2, u2, v1);
		addVertex(pose.pose(), pose.normal(), buffer, x1, y1, z2, u2, v2);
		addVertex(pose.pose(), pose.normal(), buffer, x2, y1, z1, u1, v2);
		addVertex(pose.pose(), pose.normal(), buffer, x2, y2, z1, u1, v1);
		addVertex(pose.pose(), pose.normal(), buffer, x2, y2, z1, u1, v1);
		addVertex(pose.pose(), pose.normal(), buffer, x2, y1, z1, u1, v2);
		addVertex(pose.pose(), pose.normal(), buffer, x1, y1, z2, u2, v2);
		addVertex(pose.pose(), pose.normal(), buffer, x1, y2, z2, u2, v1);
	}

	private static void addVertex(Matrix4f pose, Matrix3f normal, VertexConsumer buffer, float x, float y, float z, float u, float v) {
		buffer.vertex(pose, x, y, z).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F)
				.endVertex();
	}
}
