// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.client.gui.inventory.GuiInventory;
import events.listeners.EventRender2D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class GuiElements extends Module
{
    public GuiElements() {
        super("GuiElements", Type.Visual, "GuiElements", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("PosX", this, 210.0, 1.0, 1000.0, false));
        Aqua.setmgr.register(new Setting("PosY", this, 300.0, 1.0, 1000.0, false));
        Aqua.setmgr.register(new Setting("Width", this, 200.0, 1.0, 1000.0, false));
        Aqua.setmgr.register(new Setting("Height", this, 320.0, 1.0, 1000.0, false));
        Aqua.setmgr.register(new Setting("BackgroundAlpha", this, 100.0, 51.0, 255.0, false));
        Aqua.setmgr.register(new Setting("Blur", this, false));
        Aqua.setmgr.register(new Setting("CustomPic", this, false));
        Aqua.setmgr.register(new Setting("InvPic", this, true));
        Aqua.setmgr.register(new Setting("ChestPic", this, true));
        Aqua.setmgr.register(new Setting("BackgroundColor", this, true));
        Aqua.setmgr.register(new Setting("GlowColor", this, true));
        Aqua.setmgr.register(new Setting("Mode", this, "Aqua", new String[] { "Placeholder", "Test" }));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (!(e instanceof EventRender2D) || GuiElements.mc.currentScreen instanceof GuiInventory) {}
    }
}
