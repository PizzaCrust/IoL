package net.pizzacrust.iol.meta;

/**
 * Identifies the mod with information such as name, author, version, etc.
 * This annotation must be annotated on a AbstractMod extension!
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public @interface ModInfo {
    /**
     * The mod name.
     * @return the name
     */
    String name();

    /**
     * The mod version.
     * @return the version
     */
    String version();

    /**
     * The authors of the mod.
     * @return the authors
     */
    String[] authors();
}
