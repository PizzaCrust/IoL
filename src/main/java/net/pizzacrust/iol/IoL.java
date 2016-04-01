package net.pizzacrust.iol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.instrument.Instrumentation;

/**
 * The entry point of the Javaagent of IoL.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class IoL {
    /**
     * The loader's logger.
     */
    public static final Logger LOADER_LOGGER = LogManager.getLogger("IoL");

    /**
     * The mods directory.
     */
    public static final File MODS_DIRECTORY = new File(System.getProperty("user.dir"), "mods");

    /**
     * The Javaagent entry method for IoL.
     * @param agentArguments the arguments given to the agent
     * @param instrumentation the instrumentation object of the class loader
     */
    public static void premain(String agentArguments, Instrumentation instrumentation) {
        LOADER_LOGGER.info("[IOL] Checking for a mods directory...");
        if (!MODS_DIRECTORY.exists()) {
            LOADER_LOGGER.info("[IOL] Mods directory doesn't exist, creating one!");
            MODS_DIRECTORY.mkdir();
        } else {
            LOADER_LOGGER.info("[IOL] Plugins directory has been detected!");
        }
        LOADER_LOGGER.info("[IOL] IoL agent is finished, launching Minecraft...");
    }
}
