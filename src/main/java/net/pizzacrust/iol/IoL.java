package net.pizzacrust.iol;

import net.pizzacrust.iol.map.MappingsRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.obfuscation.SrgContainer;

import java.io.File;
import java.io.IOException;
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
     * The server mappings.
     */
    public static File SERVER_MAPPINGS = new File(System.getProperty("user.dir"), "server.srg");

    /**
     * The mappings registry.
     */
    public static final MappingsRegistry REGISTRY = new MappingsRegistry(new SrgContainer());

    /**
     * The Javaagent entry method for IoL.
     * @param agentArguments the arguments given to the agent
     * @param instrumentation the instrumentation object of the class loader
     */
    public static void premain(String agentArguments, Instrumentation instrumentation) {
        LOADER_LOGGER.info("[IOL] Checking for client mappings...");
        if (!SERVER_MAPPINGS.exists()) {
            LOADER_LOGGER.error("[IOL] Server mappings haven't been found! IoL cannot load without mappings!");
            System.exit(0);
            return;
        }
        LOADER_LOGGER.info("[IOL] Parsing and loading mappings...");
        try {
            REGISTRY.init(SERVER_MAPPINGS);
        } catch (IOException e) {
            LOADER_LOGGER.error("[IOL] Failed to parse and load mappings!");
            e.printStackTrace();
            System.exit(0);
            return;
        }
        LOADER_LOGGER.info("[IOL] Mappings loaded into runtime!");
        LOADER_LOGGER.info("[IOL] IoL agent is finished, launching Minecraft...");
    }
}
