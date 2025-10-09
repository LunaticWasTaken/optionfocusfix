package de.chloedev.optionfocusfix.mixin;

import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ContainerEventHandler.class)

public interface MixinContainerEventHandler {

    @Shadow
    List<? extends GuiEventListener> children();

    @Inject(method = "mouseClicked", at = @At(value = "RETURN", ordinal = 1))
    default void forceUnfocus(MouseButtonEvent mouseButtonEvent, boolean bl, CallbackInfoReturnable<Boolean> cir) {
        this.children().forEach(guiEventListener -> {
            if (!(guiEventListener instanceof EditBox) && !(guiEventListener instanceof AbstractScrollArea)) guiEventListener.setFocused(false);
        });
    }

    @Inject(method = "mouseReleased", at = @At("RETURN"))
    default void unfocusOnRelease(MouseButtonEvent mouseButtonEvent, CallbackInfoReturnable<Boolean> cir) {
        this.children().forEach(guiEventListener -> {
            if (!(guiEventListener instanceof EditBox) && !(guiEventListener instanceof AbstractScrollArea)) guiEventListener.setFocused(false);
        });
    }
}
