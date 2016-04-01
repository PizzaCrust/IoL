package net.pizzacrust.iol.security;

import java.security.Permission;

/**
 * Mods are limited to functions that are safe and won't harm the user's system.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class SandboxSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission permission) {
        switch (permission.getName()) {
            case "setSecurityManager":
                throw new SecurityException("SandboxSecurityManager cannot be replaced after started.");
        }
    }

    @Override
    public void checkPermission(Permission permission, Object context) {
        this.checkPermission(permission);
    }

    @Override
    public void checkExec(String cmd) {
        throw new RuntimeException("SandboxSecurityManager does not allow executing system commands to ensure user safety.");
    }
}
