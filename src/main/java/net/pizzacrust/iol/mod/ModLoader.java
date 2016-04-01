package net.pizzacrust.iol.mod;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * A extremely plain JAR mod loader. This assumes the System ClassLoader is a URLClassLoader.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class ModLoader {
    public static final ArrayList<AbstractMod> loadedMods = new ArrayList<>();

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

    public void loadMod(File mod) throws Exception {
        JarFile jarFile = new JarFile(mod);
        Attributes attributes = jarFile.getManifest().getMainAttributes();
        String mainClass = attributes.getValue("ModClass");
        addURLs(new URL[] { mod.toURI().toURL() });
        Class classObj = Class.forName(mainClass);
        if (!AbstractMod.class.isAssignableFrom(classObj)) {
            throw new Exception();
        }
        AbstractMod abstractMod = (AbstractMod) classObj.newInstance();
        loadedMods.add(abstractMod);
    }
}
