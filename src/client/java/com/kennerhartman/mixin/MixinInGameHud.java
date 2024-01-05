//
// Copyright (c) 2024 by Kenner Hartman. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//

package com.kennerhartman.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
	@Inject(at = @At("TAIL"), method = "render")
	private void init(MatrixStack matrices, float tickDelta, CallbackInfo info) {
	}
}