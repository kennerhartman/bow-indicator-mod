//
// Copyright (c) 2024 by Kenner Hartman. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//

package com.kennerhartman.mixin;

import com.kennerhartman.BowIndicatorClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.item.CrossbowItem.getPullTime;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
	@Shadow @Final @Mutable
	private final MinecraftClient client;
	@Shadow @Final @Mutable
	private final ItemRenderer itemRenderer;
	@Shadow
	private ItemStack currentStack;
	@Shadow
	private int heldItemTooltipFade;

	@Shadow public abstract TextRenderer getTextRenderer();

    public MixinInGameHud(MinecraftClient client, ItemRenderer itemRenderer) {
        this.client = client;
        this.itemRenderer = itemRenderer;
    }

    @Inject(at = @At("TAIL"), method = "render")
	private void init(MatrixStack matrices, float tickDelta, CallbackInfo info) {
		ClientPlayerEntity player = this.client.player;

		int useTimeProgress;

		if (currentStack.getItem() instanceof BowItem && player.isUsingItem()) {
			useTimeProgress = (int) (this.getUseProgress(currentStack.getMaxUseTime() - player.getItemUseTimeLeft(), currentStack) * 100);
			this.drawText(matrices, useTimeProgress);
		}

		if (currentStack.getItem() instanceof CrossbowItem && player.isUsingItem()) {
			useTimeProgress = (int) (this.getUseProgress(currentStack.getMaxUseTime() - player.getItemUseTimeLeft(), currentStack) * 100);
			this.drawText(matrices, useTimeProgress);
		}
	}

	@Unique
	private void drawText(MatrixStack matrices, int useTimeProgress) {
		Text text = Text.of(useTimeProgress + "% charged");
		int textWidth = this.getTextRenderer().getWidth(text);

		int yOffset = heldItemTooltipFade > 0 ?  15 : 0;

		int x = (this.client.getWindow().getScaledWidth() - textWidth) / 2;
		int y = this.client.getWindow().getScaledHeight() / 2 + 61 - yOffset;

		int textColor = 0;

		if (useTimeProgress <= 33) {
			textColor = 0xe04141; // red
		} else if (useTimeProgress <= 66) {
			textColor = 0xff8b4d; // orange
		} else if (useTimeProgress <= 99) {
			textColor = 0xfae543; // yellow
		} else if (useTimeProgress == 100) {
			textColor = 0x80f000; // green
		}

		getTextRenderer().drawWithShadow(matrices, useTimeProgress + "% charged", x, y, textColor);
	}

	// pulled from CrossbowItem class
	@Unique
	private float getUseProgress(int useTicks, ItemStack stack) {
		float f = (float)useTicks / (float)getPullTime(stack);
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}
}