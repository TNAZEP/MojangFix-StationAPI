/*
 * Copyright (C) 2022-2024 js6pak
 *
 * This file is part of MojangFixStationAPI.
 *
 * MojangFixStationAPI is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, version 3.
 *
 * MojangFixStationAPI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with MojangFixStationAPI. If not, see <https://www.gnu.org/licenses/>.
 */

package pl.telvarost.mojangfixstationapi.mixin.client.text.sign;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.telvarost.mojangfixstationapi.ModHelper;
import pl.telvarost.mojangfixstationapi.mixinterface.TextFieldWidgetAccessor;
import pl.telvarost.mojangfixstationapi.mixinterface.SignBlockEntityAccessor;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
    @Unique
    private SignBlockEntity sign;

    @Inject(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;DDDF)V", at = @At("HEAD"))
    private void onRender(SignBlockEntity sign, double x, double y, double z, float var8, CallbackInfo ci) {
        this.sign = sign;
    }

    @Redirect(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;DDDF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/block/entity/SignBlockEntity;texts:[Ljava/lang/String;", args = "array=get"))
    private String getSignText(String[] signText, int i) {
        return ((TextFieldWidgetAccessor) ((SignBlockEntityAccessor) sign).getTextFields()[i]).getDisplayText();
    }
}
