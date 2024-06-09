package org.newdawn.slick.opengl;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;





































public abstract class SlickCallable
{
  private static Texture lastUsed;
  private static boolean inSafe = false;
  


  public SlickCallable() {}
  

  public static void enterSafeBlock()
  {
    if (inSafe) {
      return;
    }
    
    Renderer.get().flush();
    lastUsed = TextureImpl.getLastBind();
    TextureImpl.bindNone();
    GL11.glPushAttrib(1048575);
    GL11.glPushClientAttrib(-1);
    GL11.glMatrixMode(5888);
    GL11.glPushMatrix();
    GL11.glMatrixMode(5889);
    GL11.glPushMatrix();
    GL11.glMatrixMode(5888);
    
    inSafe = true;
  }
  




  public static void leaveSafeBlock()
  {
    if (!inSafe) {
      return;
    }
    
    GL11.glMatrixMode(5889);
    GL11.glPopMatrix();
    GL11.glMatrixMode(5888);
    GL11.glPopMatrix();
    GL11.glPopClientAttrib();
    GL11.glPopAttrib();
    
    if (lastUsed != null) {
      lastUsed.bind();
    } else {
      TextureImpl.bindNone();
    }
    
    inSafe = false;
  }
  





  public final void call()
    throws SlickException
  {
    enterSafeBlock();
    
    performGLOperations();
    
    leaveSafeBlock();
  }
  
  protected abstract void performGLOperations()
    throws SlickException;
}
