package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;










public class ExchangePair
{
  private final AsymmetricKeyParameter publicKey;
  private final byte[] shared;
  
  public ExchangePair(AsymmetricKeyParameter publicKey, byte[] shared)
  {
    this.publicKey = publicKey;
    this.shared = Arrays.clone(shared);
  }
  





  public AsymmetricKeyParameter getPublicKey()
  {
    return publicKey;
  }
  





  public byte[] getSharedValue()
  {
    return Arrays.clone(shared);
  }
}
