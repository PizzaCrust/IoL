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
     * The client mappings.
     */
    public static File CLIENT_MAPPINGS = new File(new File(System.getProperty("minecraft.dir"), "srg"), "client.srg");

    /**
     * The Javaagent entry method for IoL.
     * @param agentArguments the arguments given to the agent
     * @param instrumentation the instrumentation object of the class loader
     */
    public static void premain(String agentArguments, Instrumentation instrumentation) {
        LOADER_LOGGER.info("[IOL] Resolving Minecraft directory...");
        if (System.getProperty("os.name").startsWith("Windows")) {
            System.setProperty("minecraft.dir", new File(System.getenv("APPDATA"), ".minecraft").getAbsolutePath());
        }
        if (System.getProperty("os.name").startsWith("Mac")) {
            File applicationSupport = new File(System.getProperty("user.home") + "/Library/Application Support");
            System.setProperty("minecraft.dir", new File(applicationSupport, "minecraft").getAbsolutePath());
        }
        if (System.getProperty("minecraft.dir") == null) {
            System.setProperty("minecraft.dir", new File(System.getProperty("user.home"), ".minecraft").getAbsolutePath());
        }
        if (!(new File(System.getProperty("minecraft.dir")).exists())) {
            LOADER_LOGGER.error("[IOL] Minecraft directory couldn't be detected or doesn't exist.");
            System.exit(0);
            return;
        }
    }
}
