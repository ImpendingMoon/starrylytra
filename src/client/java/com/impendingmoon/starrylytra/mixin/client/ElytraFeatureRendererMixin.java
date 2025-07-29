package com.impendingmoon.starrylytra.mixin.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<S extends BipedEntityRenderState, M extends EntityModel<S>> {
    @Shadow
    @Final
    private ElytraEntityModel model;
    @Shadow @Final private ElytraEntityModel babyModel;
    @Shadow @Final private EquipmentRenderer equipmentRenderer;

    /// Replaces the default Elytra material with the End Gateway material when a data component is set
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderElytra(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                                S state, float limbAngle, float limbDistance, CallbackInfo ci) {

        // Use default rendering if it's not equipped or if it doesn't have the "starry" custom data component
        ItemStack stack = state.equippedChestStack;
        EquippableComponent equip = stack.get(DataComponentTypes.EQUIPPABLE);
        NbtComponent customData = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, null);
        if (equip == null || equip.assetId().isEmpty()) { return; }
        if (customData == null || !customData.contains("starry")) { return; }

        ElytraEntityModel modelToUse = state.baby ? babyModel : model;
        modelToUse.setAngles(state);

        matrices.push();
        matrices.translate(0.0F, 0.0F, 0.125F);

        RenderLayer endPortalLayer = RenderLayer.getEndGateway();
        VertexConsumer buffer = vertexConsumers.getBuffer(endPortalLayer);

        modelToUse.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        ci.cancel();
    }
}
