package net.pizzacrust.iol.mixin;

import net.pizzacrust.iol.IoL;
import net.pizzacrust.mixin.Inject;
import net.pizzacrust.mixin.MethodName;
import net.pizzacrust.mixin.Mixin;

/**
 * Injects to Minecraft's booting process.
 * This is the only thing that isn't dynamic. Java doesn't allow dynamic annotations.
 * IoL can be updated to the next Minecraft version by changing the @Mixin and @MethodName annotation.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
@Mixin("la")
public class MixinDedicatedServer {
    @Inject(value = Inject.Execution.CUSTOM, line = 1041)
    @MethodName("j")
    public void onStart() {
        /**
         * TODO: Injection process.
         * The injection process shall be done in this Mixin.
         */
        IoL.LOADER_LOGGER.info("[IOL] Mixin injection has succeeded.");
    }
}
