package de.chloedev.optionfocusfix.mixin;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerEventHandler.class)
public interface MixinContainerEventHandler {

    @Inject(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/events/ContainerEventHandler;setFocused(Lnet/minecraft/client/gui/components/events/GuiEventListener;)V", shift = At.Shift.AFTER))
    private void fixMouseClicked(MouseButtonEvent click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        ContainerEventHandler self = (ContainerEventHandler) this;
        // Prevent buttons from remaining focused after being clicked
        if (self.getFocused() instanceof AbstractButton) {
            self.setFocused(null);
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"))
    private void fixMouseReleased(MouseButtonEvent click, CallbackInfoReturnable<Boolean> cir) {
        ContainerEventHandler self = (ContainerEventHandler) this;
        // Prevent sliders from remaining focused after being dragged
        if (self.getFocused() instanceof AbstractSliderButton) {
            self.setFocused(null);
        }
    }
}