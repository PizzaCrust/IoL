package net.pizzacrust.iol.mixin;

import net.pizzacrust.iol.IoL;
import net.pizzacrust.iol.mod.ModLoader;
import net.pizzacrust.mixin.Inject;
import net.pizzacrust.mixin.MethodName;
import net.pizzacrust.mixin.MixinBridge;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Injects to Minecraft's client boot process.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class MixinMinecraft {
    /**
     * Represents the THIS object in the Minecraft lass.
     */
    @MixinBridge
    private Object thisObj;

    /**
     * Constructs this mixin w/ a this object.
     * @param thisObj the this object
     */
    public MixinMinecraft(Object thisObj) { this.thisObj = thisObj; }

    /**
     * Called when the Minecraft reaches the injection.
     */
    @Inject(Inject.Execution.AFTER)
    @MethodName("startGame")
    public void onInject() {
        IoL.LOADER_LOGGER.info("[IOL] Mixin injection has succeeded.");
        IoL.LOADER_LOGGER.info("[IOL] Detecting all mods in mods directory...");
        File[] mods = IoL.MODS_DIRECTORY.listFiles((dir, name) -> {
            return name.endsWith(".jar");
        });
        IoL.LOADER_LOGGER.info("[IOL] Detected" + mods.length + " mods in the mods directory.");
        IoL.LOADER_LOGGER.info("[IOL] Loading detected mods...");
        for (File file : mods) {
            IoL.LOADER_LOGGER.info("[IOL] Loading " + file.getName() + "...");
            ModLoader modLoader = new ModLoader();
            try {
                modLoader.loadMod(file);
            } catch (Exception e) {
                IoL.LOADER_LOGGER.error("[IOL] Failed to load " + file.getName() + "!");
                e.printStackTrace();
            }
        }
        IoL.LOADER_LOGGER.info("[IOL] IoL injection has finished!");
    }
}
