package net.pizzacrust.iol.mixin;

import net.minecraft.client.gui.FontRenderer;
import net.pizzacrust.mixin.Inject;
import net.pizzacrust.mixin.MethodName;
import net.pizzacrust.mixin.Mixin;
import net.pizzacrust.mixin.MixinBridge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Modifies the main menu screen of Minecraft.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
@Mixin("net.minecraft.client.gui.GuiMainMenu")
public class MixinGuiMainMenu {
    @MixinBridge
    protected FontRenderer fontRendererObj;

    @MixinBridge
    public int height;

    @MixinBridge
    public int width;

    @MixinBridge
    private Method drawString;

    @MixinBridge
    private Object thisObj;

    public MixinGuiMainMenu(Object thisObj) {
        try {
            Class<?> guiScreen = Class.forName("net.minecraft.client.gui.GuiScreen");
            this.fontRendererObj = (FontRenderer) guiScreen.getDeclaredField("fontRendererObj").get(thisObj);
            this.height = (int) guiScreen.getDeclaredField("height").get(thisObj);
            this.width = (int) guiScreen.getDeclaredField("width").get(thisObj);
            Class<?> gui = Class.forName("net.minecraft.client.gui.Gui");
            drawString = gui.getDeclaredMethod("drawString", new Class[] { FontRenderer.class, String.class, int.class, int.class, int.class });
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.thisObj = thisObj;
    }

    @Inject(value = Inject.Execution.CUSTOM, line = 559)
    @MethodName("drawScreen")
    public void onScreenDrew(int a, int b, int c) {
        String poweredByIoL = "Powered by IoL v1";
        try {
            this.drawString.invoke(thisObj, new Object[] { this.fontRendererObj, poweredByIoL, this.width - fontRendererObj.getStringWidth(poweredByIoL) -2, this.height - 20, -1 });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
