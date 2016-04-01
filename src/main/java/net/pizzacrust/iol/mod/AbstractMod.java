package net.pizzacrust.iol.mod;

import net.minecraft.client.Minecraft;
import net.pizzacrust.iol.meta.ModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a abstract base mod.
 * Must have {@link ModInfo} annotated and has a empty parameter constructor!
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public abstract class AbstractMod {

    /**
     * Called when the mod is loading.
     * @param minecraft the minecraft
     */
    public abstract void onLoad(Minecraft minecraft);

    /**
     * Called when minecraft or the mod is disabled/shutting down.
     * @param shutdownType indicates whether the client or the mod is disabled/shutting down.
     */
    public abstract void onShutdown(int shutdownType);

    /**
     * Retrieves the mod's logger.
     * @return the logger
     */
    public Logger getLogger() {
        return LogManager.getLogger(getModificationInformation().name());
    }

    /**
     * Retrieves the mod's information.
     * @return the information
     */
    public ModInfo getModificationInformation() {
        ModInfo modInfo = (ModInfo) this.getClass().getAnnotation(ModInfo.class);
        return modInfo;
    }
}
