package org.spongycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.util.Arrays;
















public class IESParameterSpec
  implements AlgorithmParameterSpec
{
  private byte[] derivation;
  private byte[] encoding;
  private int macKeySize;
  private int cipherKeySize;
  private byte[] nonce;
  private boolean usePointCompression;
  
  public IESParameterSpec(byte[] derivation, byte[] encoding, int macKeySize)
  {
    this(derivation, encoding, macKeySize, -1, null, false);
  }
  














  public IESParameterSpec(byte[] derivation, byte[] encoding, int macKeySize, int cipherKeySize, byte[] nonce)
  {
    this(derivation, encoding, macKeySize, cipherKeySize, nonce, false);
  }
  
















  public IESParameterSpec(byte[] derivation, byte[] encoding, int macKeySize, int cipherKeySize, byte[] nonce, boolean usePointCompression)
  {
    if (derivation != null)
    {
      this.derivation = new byte[derivation.length];
      System.arraycopy(derivation, 0, this.derivation, 0, derivation.length);
    }
    else
    {
      this.derivation = null;
    }
    
    if (encoding != null)
    {
      this.encoding = new byte[encoding.length];
      System.arraycopy(encoding, 0, this.encoding, 0, encoding.length);
    }
    else
    {
      this.encoding = null;
    }
    
    this.macKeySize = macKeySize;
    this.cipherKeySize = cipherKeySize;
    this.nonce = Arrays.clone(nonce);
    this.usePointCompression = usePointCompression;
  }
  



  public byte[] getDerivationV()
  {
    return Arrays.clone(derivation);
  }
  



  public byte[] getEncodingV()
  {
    return Arrays.clone(encoding);
  }
  



  public int getMacKeySize()
  {
    return macKeySize;
  }
  



  public int getCipherKeySize()
  {
    return cipherKeySize;
  }
  





  public byte[] getNonce()
  {
    return Arrays.clone(nonce);
  }
  



  public void setPointCompression(boolean usePointCompression)
  {
    this.usePointCompression = usePointCompression;
  }
  





  public boolean getPointCompression()
  {
    return usePointCompression;
  }
}
