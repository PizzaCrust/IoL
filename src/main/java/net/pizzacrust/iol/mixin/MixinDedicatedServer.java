package net.pizzacrust.iol.mixin;

import net.minecraft.server.MinecraftServer;
import net.pizzacrust.iol.IoL;
import net.pizzacrust.iol.map.MappingsRegistry;
import net.pizzacrust.iol.plugin.PluginLoader;
import net.pizzacrust.mixin.Inject;
import net.pizzacrust.mixin.MethodName;
import net.pizzacrust.mixin.Mixin;
import net.pizzacrust.mixin.MixinBridge;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        MinecraftServer instance = null;
        for (Field field : thisObj.getClass().getDeclaredFields()) {
            if (field.getName().equals("INSTANCE")) {
                try {
                    if (!(field.get(null) == null)) {
                        verified = true;
                        instance = (MinecraftServer) field.get(null);
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
        IoL.LOADER_LOGGER.info("[IOL] Detecting Minecraft Version...");
        if (IoL.MAPPING_TYPE != MappingsRegistry.Type.MCP) {
            try {
                String getMinecraftVersionMapping = IoL.REGISTRY.getMethodMapping("net.minecraft.server.MinecraftServer", "getMinecraftVersion").getSimpleName();
                Class minecraftServerClass = MinecraftServer.class;
                for (Method method : minecraftServerClass.getDeclaredMethods()) {
                    if (method.getName().equals(getMinecraftVersionMapping) && method.getReturnType() == String.class) {
                        System.setProperty("minecraft.ver", (String) method.invoke(instance, new Object[0]));
                    }
                }
            } catch (Exception e) {
                IoL.LOADER_LOGGER.error("[IOL] Failed to retrieve Minecraft Version!");
                System.exit(0);
                return;
            }
            IoL.LOADER_LOGGER.info("[IOL] Detected Minecraft Version: " + System.getProperty("minecraft.ver"));
        } else {
            IoL.LOADER_LOGGER.info("[IOL] Bukkit mappings do not have getMinecraftVersion!");
        }
        IoL.LOADER_LOGGER.info("[IOL] Detecting plugins in plugins directory...");
        File[] plugins = IoL.PLUGINS_DIRECTORY.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".iol");
            }
        });
        IoL.LOADER_LOGGER.info("[IOL] Detected " + plugins.length + " plugins in the plugins directory.");
        for (File file : plugins) {
            try {
                IoL.LOADER_LOGGER.info("[IOL] Loading file " + file.getName() + "...");
                PluginLoader pluginLoader = new PluginLoader();
                pluginLoader.loadPlugin(file);
                IoL.LOADER_LOGGER.info("[IOL] File " + file.getName() + " has been loaded.");
            } catch (Exception e) {
                IoL.LOADER_LOGGER.error("[IOL] File " + file.getName() + " failed to load.");
                e.printStackTrace();
            }
        }
        IoL.LOADER_LOGGER.info("[IOL] IOL has successfully booted up.");
    }
}
