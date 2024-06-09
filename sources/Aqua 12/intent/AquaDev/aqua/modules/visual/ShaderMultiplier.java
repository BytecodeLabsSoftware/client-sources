// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import org.lwjgl.opengl.GL11;
import intent.AquaDev.aqua.utils.shader.ShaderStencilUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.utils.shader.Glow;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import events.listeners.EventPostRender2D;
import events.Event;
import events.listeners.EventGlowESP;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.utils.shader.ShaderProgram;
import net.minecraft.client.shader.Framebuffer;
import intent.AquaDev.aqua.modules.Module;

public class ShaderMultiplier extends Module
{
    public static Framebuffer pass;
    public static Framebuffer output;
    public static Framebuffer input;
    public static ShaderProgram blurProgram;
    Minecraft MC;
    
    public ShaderMultiplier() {
        super("ShaderMultiplier", Type.Visual, "ShaderMultiplier", 0, Category.Visual);
        this.MC = Minecraft.getMinecraft();
        Aqua.setmgr.register(new Setting("Sigma", this, 5.0, 0.0, 50.0, true));
        Aqua.setmgr.register(new Setting("Multiplier", this, 1.0, 0.0, 3.0, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    public static void drawGlowESP(final Runnable runnable, final boolean renderTwice) {
        final EventGlowESP event = new EventGlowESP(runnable);
        Aqua.INSTANCE.onEvent(event);
        if (!event.isCancelled() || renderTwice) {
            runnable.run();
        }
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPostRender2D) {
            drawGlowESP(() -> Gui.drawRect(-2001, -2001, -2000, -2000, new Color(0, 0, 0, 0).getRGB()), false);
            Glow.checkSetup();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            final ScaledResolution sr = new ScaledResolution(this.MC);
            final double screenWidth = sr.getScaledWidth_double();
            final double screenHeight = sr.getScaledHeight_double();
            Glow.blurProgram.init();
            Glow.setupBlurUniforms();
            Glow.doBlurPass(0, Glow.input.framebufferTexture, Glow.pass, (int)screenWidth, (int)screenHeight);
            Glow.doBlurPass(1, Glow.pass.framebufferTexture, Glow.output, (int)screenWidth, (int)screenHeight);
            Glow.blurProgram.uninit();
            ShaderStencilUtil.initStencil();
            ShaderStencilUtil.bindWriteStencilBuffer();
            this.drawTexturedQuad1(Glow.input.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.bindReadStencilBuffer(0);
            this.drawTexturedQuad1(Glow.output.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.uninitStencilBuffer();
            GlStateManager.bindTexture(0);
            GlStateManager.alphaFunc(516, 0.2f);
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            Glow.input.framebufferClear();
            this.MC.getFramebuffer().bindFramebuffer(false);
        }
        else if (event instanceof EventGlowESP) {
            Glow.onGlowEvent((EventGlowESP)event);
        }
    }
    
    private void drawTexturedQuad1(final int texture, final double width, final double height) {
        GlStateManager.enableBlend();
        GL11.glBindTexture(3553, texture);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, height);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(width, height);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d(width, 0.0);
        GL11.glEnd();
    }
    
    static {
        ShaderMultiplier.pass = new Framebuffer(ShaderMultiplier.mc.displayWidth, ShaderMultiplier.mc.displayHeight, false);
        ShaderMultiplier.output = new Framebuffer(ShaderMultiplier.mc.displayWidth, ShaderMultiplier.mc.displayHeight, false);
        ShaderMultiplier.input = new Framebuffer(ShaderMultiplier.mc.displayWidth, ShaderMultiplier.mc.displayHeight, true);
        ShaderMultiplier.blurProgram = new ShaderProgram("vertex.vert", "espGlow.glsl");
    }
}
