package net.pizzacrust.iol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * The Javaagent entry method for IoL.
     * @param agentArguments the arguments given to the agent
     * @param instrumentation the instrumentation object of the class loader
     */
    public static void premain(String agentArguments, Instrumentation instrumentation) {

    }
}
