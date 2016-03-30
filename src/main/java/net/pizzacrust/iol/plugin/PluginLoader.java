package net.pizzacrust.iol.plugin;

import net.pizzacrust.iol.IoL;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * A extremely plain JAR plugin loader. This assumes the System ClassLoader is a URLClassLoader.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class PluginLoader {
    private void addURLs(URL[] urls) throws Exception {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        if ( cl instanceof URLClassLoader ) {
            URLClassLoader ul = (URLClassLoader)cl;
            Class<?>[] paraTypes = new Class[1];
            paraTypes[0] = URL.class;
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", paraTypes);
            method.setAccessible(true);
            Object[] args = new Object[1];
            for(int i=0; i<urls.length; i++) {
                args[0] = urls[i];
                method.invoke(ul, args);
            }
        }
    }

    public void loadPlugin(File plugin) throws Exception {
        JarFile jarFile = new JarFile(plugin);
        Attributes attributes = jarFile.getManifest().getMainAttributes();
        String mainClass = attributes.getValue("PluginClass");
        addURLs(new URL[] { plugin.toURI().toURL() });
        Class classObj = Class.forName(mainClass);
        classObj.getDeclaredMethod("startPlugin", new Class[0]).invoke(null, null); // public static void startPlugin()
    }
}
