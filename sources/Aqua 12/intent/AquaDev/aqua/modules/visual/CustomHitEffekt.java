// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import events.listeners.EventUpdate;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class CustomHitEffekt extends Module
{
    public CustomHitEffekt() {
        super("CustomHurtEffekt", Type.Visual, "CustomHurtEffekt", 0, Category.Visual);
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
        if (e instanceof EventUpdate) {}
    }
}
