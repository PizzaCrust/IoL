package net.pizzacrust.iol.map;

import com.google.common.collect.BiMap;
import net.pizzacrust.iol.IoL;
import net.pizzacrust.iol.meta.Tested;
import org.spongepowered.asm.obfuscation.SrgContainer;
import org.spongepowered.asm.obfuscation.SrgField;
import org.spongepowered.asm.obfuscation.SrgMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * A registry for obfuscation -> deobfuscated mappings.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class MappingsRegistry {

    /**
     * The SRG container of the SRG file.
     */
    private final SrgContainer container;

    /**
     * Constructs a new {@link MappingsRegistry} from a {@link SrgContainer}.
     * @param srg the SRG container
     */
    public MappingsRegistry(SrgContainer srg){
        this.container = srg;
    }

    /**
     * Initializes the object to read from a SRG file.
     * @param srgFile the srg file
     * @throws IOException if the file couldn't be read
     */
    public void init(File srgFile) throws IOException {
        container.readSrg(srgFile);
    }

    /**
     * Retrieves a class mapping from the {@link SrgContainer}.
     * @param deobfName the deobfuscated class name
     * @return the obfuscated name
     */
    @Tested
    public String getClassMapping(String deobfName) {
        try {
            Class<?> srgContainerClass = SrgContainer.class;
            Field classMappings = srgContainerClass.getDeclaredField("classMap");
            classMappings.setAccessible(true);
            BiMap<String, String> map = (BiMap<String, String>) classMappings.get(this.container);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().equals(deobfName.replace('.', '/'))) {
                    return entry.getKey().replace('/', '.');
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Retrieves a method mapping.
     * @param deobfClassParent the parent of the method
     * @param deobfMethodName the method name
     * @return the mapping
     */
    @Tested
    public SrgMethod getMethodMapping(String deobfClassParent, String deobfMethodName) {
        BiMap<SrgMethod, SrgMethod> methodMap;
        try {
            Class<?> srgContainerClass = SrgContainer.class;
            Field methodMappingField = srgContainerClass.getDeclaredField("methodMap");
            methodMappingField.setAccessible(true);
            methodMap = (BiMap<SrgMethod, SrgMethod>) methodMappingField.get(this.container);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for (Map.Entry<SrgMethod, SrgMethod> entry : methodMap.entrySet()) {
            SrgMethod obf = entry.getKey();
            SrgMethod deobf = entry.getValue();
            if (deobf.getOwner().replace('/', '.').equals(deobfClassParent)) {
                if (deobf.getSimpleName().equals(deobfMethodName)) {
                    return obf;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves a field mapping.
     * @param deobfClassParent the parent of the field
     * @param deobfFieldName the field name
     * @return the mapping
     */
    public SrgField getFieldMapping(String deobfClassParent, String deobfFieldName) {
        BiMap<SrgField, SrgField> fieldMap;
        try {
            Class<?> srgContainerClass = SrgContainer.class;
            Field fieldMappingField = srgContainerClass.getDeclaredField("fieldMap");
            fieldMappingField.setAccessible(true);
            fieldMap = (BiMap<SrgField, SrgField>) fieldMappingField.get(this.container);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for (Map.Entry<SrgField, SrgField> entry : fieldMap.entrySet()) {
            SrgField deobf = entry.getKey();
            SrgField obf = entry.getValue();
            if (deobf.getOwner().replace('/', '.').equals(deobfClassParent)) {
                if (deobf.getName().equals(deobfFieldName)) {
                    return obf;
                }
            }
        }
        return null;
    }

    /**
     * Represents different types of SRG mappings.
     */
    public enum Type {
        /**
         * Bukkit mappings.
         */
        BUKKIT("BUKKIT"),
        /**
         * MCP mappings.
         */
        MCP("MCP");

        private String id;

        Type(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
