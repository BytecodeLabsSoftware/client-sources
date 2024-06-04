package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.Xof;







public class SHAKEDigest
  extends KeccakDigest
  implements Xof
{
  private static int checkBitLength(int bitLength)
  {
    switch (bitLength)
    {
    case 128: 
    case 256: 
      return bitLength;
    }
    throw new IllegalArgumentException("'bitLength' " + bitLength + " not supported for SHAKE");
  }
  

  public SHAKEDigest()
  {
    this(128);
  }
  
  public SHAKEDigest(int bitLength)
  {
    super(checkBitLength(bitLength));
  }
  
  public SHAKEDigest(SHAKEDigest source) {
    super(source);
  }
  
  public String getAlgorithmName()
  {
    return "SHAKE" + fixedOutputLength;
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    return doFinal(out, outOff, getDigestSize());
  }
  
  public int doFinal(byte[] out, int outOff, int outLen)
  {
    int length = doOutput(out, outOff, outLen);
    
    reset();
    
    return length;
  }
  
  public int doOutput(byte[] out, int outOff, int outLen)
  {
    if (!squeezing)
    {
      absorbBits(15, 4);
    }
    
    squeeze(out, outOff, outLen * 8L);
    
    return outLen;
  }
  



  protected int doFinal(byte[] out, int outOff, byte partialByte, int partialBits)
  {
    return doFinal(out, outOff, getDigestSize(), partialByte, partialBits);
  }
  



  protected int doFinal(byte[] out, int outOff, int outLen, byte partialByte, int partialBits)
  {
    if ((partialBits < 0) || (partialBits > 7))
    {
      throw new IllegalArgumentException("'partialBits' must be in the range [0,7]");
    }
    
    int finalInput = partialByte & (1 << partialBits) - 1 | 15 << partialBits;
    int finalBits = partialBits + 4;
    
    if (finalBits >= 8)
    {
      absorb(new byte[] { (byte)finalInput }, 0, 1);
      finalBits -= 8;
      finalInput >>>= 8;
    }
    
    if (finalBits > 0)
    {
      absorbBits(finalInput, finalBits);
    }
    
    squeeze(out, outOff, outLen * 8L);
    
    reset();
    
    return outLen;
  }
}
