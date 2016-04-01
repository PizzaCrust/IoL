package net.pizzacrust.iol.debug;

import net.minecraft.client.main.Main;

import java.util.Arrays;

/**
 * Starts a offline debug mode for Minecraft.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class StartDebugMinecraft {
    public static void main(String[] args)
    {
        Main.main(concat(new String[] {"--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second)
    {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
