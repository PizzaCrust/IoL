package net.pizzacrust.iol.mixin;

import net.minecraft.server.MinecraftServer;
import net.pizzacrust.iol.IoL;
import net.pizzacrust.mixin.Inject;
import net.pizzacrust.mixin.MethodName;
import net.pizzacrust.mixin.Mixin;
import net.pizzacrust.mixin.MixinBridge;

import java.lang.reflect.Field;

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
    /**
     * Represents the instance of the MinecraftServer.
     */
    public static MinecraftServer INSTANCE;

    /**
     * Indicates that this field is a bridge.
     */
    @MixinBridge
    /**
     * The this object of this mixin.
     */
    public Object thisObj;

    public MixinDedicatedServer(Object thisObj) {
        this.thisObj = thisObj;
    }

    /**
     * Mixin method identification annotations.
     */
    @Inject(value = Inject.Execution.CUSTOM, line = 1041)
    @MethodName("j")
    /**
     * The startServer method.
     */
    public void startServer() {
        /**
         * TODO: Injection process.
         * The injection process shall be done in this Mixin.
         */
        IoL.LOADER_LOGGER.info("[IOL] Mixin injection has succeeded.");
        IoL.LOADER_LOGGER.info("[IOL] Releasing server instance...");
        try {
            thisObj.getClass().getDeclaredField("INSTANCE").set(null, thisObj);
        } catch (Exception e) {
            IoL.LOADER_LOGGER.error("[IOL] Failed to release server instance.");
            e.printStackTrace();
        }
        IoL.LOADER_LOGGER.info("[IOL] Verifying server instances...");
        boolean verified = false;
        for (Field field : thisObj.getClass().getDeclaredFields()) {
            if (field.getName().equals("INSTANCE")) {
                try {
                    if (!(field.get(null) == null)) {
                        verified = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!verified) {
            IoL.LOADER_LOGGER.error("[IOL] Integrity of instance is unknown! ");
            System.exit(0);
            return;
        }
        IoL.LOADER_LOGGER.info("[IOL] Instance has been verified.");
    }
}
