package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;














public abstract class SerpentEngineBase
  implements BlockCipher
{
  protected static final int BLOCK_SIZE = 16;
  static final int ROUNDS = 32;
  static final int PHI = -1640531527;
  protected boolean encrypting;
  protected int[] wKey;
  protected int X0;
  protected int X1;
  protected int X2;
  protected int X3;
  
  SerpentEngineBase() {}
  
  public void init(boolean encrypting, CipherParameters params)
  {
    if ((params instanceof KeyParameter))
    {
      this.encrypting = encrypting;
      wKey = makeWorkingKey(((KeyParameter)params).getKey());
      return;
    }
    
    throw new IllegalArgumentException("invalid parameter passed to " + getAlgorithmName() + " init - " + params.getClass().getName());
  }
  
  public String getAlgorithmName()
  {
    return "Serpent";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  

















  public final int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    if (wKey == null)
    {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    
    if (inOff + 16 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 16 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    if (encrypting)
    {
      encryptBlock(in, inOff, out, outOff);
    }
    else
    {
      decryptBlock(in, inOff, out, outOff);
    }
    
    return 16;
  }
  


  public void reset() {}
  


  protected static int rotateLeft(int x, int bits)
  {
    return x << bits | x >>> -bits;
  }
  


  protected static int rotateRight(int x, int bits)
  {
    return x >>> bits | x << -bits;
  }
  


























  protected final void sb0(int a, int b, int c, int d)
  {
    int t1 = a ^ d;
    int t3 = c ^ t1;
    int t4 = b ^ t3;
    X3 = (a & d ^ t4);
    int t7 = a ^ b & t1;
    X2 = (t4 ^ (c | t7));
    int t12 = X3 & (t3 ^ t7);
    X1 = (t3 ^ 0xFFFFFFFF ^ t12);
    X0 = (t12 ^ t7 ^ 0xFFFFFFFF);
  }
  



  protected final void ib0(int a, int b, int c, int d)
  {
    int t1 = a ^ 0xFFFFFFFF;
    int t2 = a ^ b;
    int t4 = d ^ (t1 | t2);
    int t5 = c ^ t4;
    X2 = (t2 ^ t5);
    int t8 = t1 ^ d & t2;
    X1 = (t4 ^ X2 & t8);
    X3 = (a & t4 ^ (t5 | X1));
    X0 = (X3 ^ t5 ^ t8);
  }
  



  protected final void sb1(int a, int b, int c, int d)
  {
    int t2 = b ^ a ^ 0xFFFFFFFF;
    int t5 = c ^ (a | t2);
    X2 = (d ^ t5);
    int t7 = b ^ (d | t2);
    int t8 = t2 ^ X2;
    X3 = (t8 ^ t5 & t7);
    int t11 = t5 ^ t7;
    X1 = (X3 ^ t11);
    X0 = (t5 ^ t8 & t11);
  }
  



  protected final void ib1(int a, int b, int c, int d)
  {
    int t1 = b ^ d;
    int t3 = a ^ b & t1;
    int t4 = t1 ^ t3;
    X3 = (c ^ t4);
    int t7 = b ^ t1 & t3;
    int t8 = X3 | t7;
    X1 = (t3 ^ t8);
    int t10 = X1 ^ 0xFFFFFFFF;
    int t11 = X3 ^ t7;
    X0 = (t10 ^ t11);
    X2 = (t4 ^ (t10 | t11));
  }
  



  protected final void sb2(int a, int b, int c, int d)
  {
    int t1 = a ^ 0xFFFFFFFF;
    int t2 = b ^ d;
    int t3 = c & t1;
    X0 = (t2 ^ t3);
    int t5 = c ^ t1;
    int t6 = c ^ X0;
    int t7 = b & t6;
    X3 = (t5 ^ t7);
    X2 = (a ^ (d | t7) & (X0 | t5));
    X1 = (t2 ^ X3 ^ X2 ^ (d | t1));
  }
  



  protected final void ib2(int a, int b, int c, int d)
  {
    int t1 = b ^ d;
    int t2 = t1 ^ 0xFFFFFFFF;
    int t3 = a ^ c;
    int t4 = c ^ t1;
    int t5 = b & t4;
    X0 = (t3 ^ t5);
    int t7 = a | t2;
    int t8 = d ^ t7;
    int t9 = t3 | t8;
    X3 = (t1 ^ t9);
    int t11 = t4 ^ 0xFFFFFFFF;
    int t12 = X0 | X3;
    X1 = (t11 ^ t12);
    X2 = (d & t11 ^ t3 ^ t12);
  }
  



  protected final void sb3(int a, int b, int c, int d)
  {
    int t1 = a ^ b;
    int t2 = a & c;
    int t3 = a | d;
    int t4 = c ^ d;
    int t5 = t1 & t3;
    int t6 = t2 | t5;
    X2 = (t4 ^ t6);
    int t8 = b ^ t3;
    int t9 = t6 ^ t8;
    int t10 = t4 & t9;
    X0 = (t1 ^ t10);
    int t12 = X2 & X0;
    X1 = (t9 ^ t12);
    X3 = ((b | d) ^ t4 ^ t12);
  }
  



  protected final void ib3(int a, int b, int c, int d)
  {
    int t1 = a | b;
    int t2 = b ^ c;
    int t3 = b & t2;
    int t4 = a ^ t3;
    int t5 = c ^ t4;
    int t6 = d | t4;
    X0 = (t2 ^ t6);
    int t8 = t2 | t6;
    int t9 = d ^ t8;
    X2 = (t5 ^ t9);
    int t11 = t1 ^ t9;
    int t12 = X0 & t11;
    X3 = (t4 ^ t12);
    X1 = (X3 ^ X0 ^ t11);
  }
  



  protected final void sb4(int a, int b, int c, int d)
  {
    int t1 = a ^ d;
    int t2 = d & t1;
    int t3 = c ^ t2;
    int t4 = b | t3;
    X3 = (t1 ^ t4);
    int t6 = b ^ 0xFFFFFFFF;
    int t7 = t1 | t6;
    X0 = (t3 ^ t7);
    int t9 = a & X0;
    int t10 = t1 ^ t6;
    int t11 = t4 & t10;
    X2 = (t9 ^ t11);
    X1 = (a ^ t3 ^ t10 & X2);
  }
  



  protected final void ib4(int a, int b, int c, int d)
  {
    int t1 = c | d;
    int t2 = a & t1;
    int t3 = b ^ t2;
    int t4 = a & t3;
    int t5 = c ^ t4;
    X1 = (d ^ t5);
    int t7 = a ^ 0xFFFFFFFF;
    int t8 = t5 & X1;
    X3 = (t3 ^ t8);
    int t10 = X1 | t7;
    int t11 = d ^ t10;
    X0 = (X3 ^ t11);
    X2 = (t3 & t11 ^ X1 ^ t7);
  }
  



  protected final void sb5(int a, int b, int c, int d)
  {
    int t1 = a ^ 0xFFFFFFFF;
    int t2 = a ^ b;
    int t3 = a ^ d;
    int t4 = c ^ t1;
    int t5 = t2 | t3;
    X0 = (t4 ^ t5);
    int t7 = d & X0;
    int t8 = t2 ^ X0;
    X1 = (t7 ^ t8);
    int t10 = t1 | X0;
    int t11 = t2 | t7;
    int t12 = t3 ^ t10;
    X2 = (t11 ^ t12);
    X3 = (b ^ t7 ^ X1 & t12);
  }
  



  protected final void ib5(int a, int b, int c, int d)
  {
    int t1 = c ^ 0xFFFFFFFF;
    int t2 = b & t1;
    int t3 = d ^ t2;
    int t4 = a & t3;
    int t5 = b ^ t1;
    X3 = (t4 ^ t5);
    int t7 = b | X3;
    int t8 = a & t7;
    X1 = (t3 ^ t8);
    int t10 = a | d;
    int t11 = t1 ^ t7;
    X0 = (t10 ^ t11);
    X2 = (b & t10 ^ (t4 | a ^ c));
  }
  



  protected final void sb6(int a, int b, int c, int d)
  {
    int t1 = a ^ 0xFFFFFFFF;
    int t2 = a ^ d;
    int t3 = b ^ t2;
    int t4 = t1 | t2;
    int t5 = c ^ t4;
    X1 = (b ^ t5);
    int t7 = t2 | X1;
    int t8 = d ^ t7;
    int t9 = t5 & t8;
    X2 = (t3 ^ t9);
    int t11 = t5 ^ t8;
    X0 = (X2 ^ t11);
    X3 = (t5 ^ 0xFFFFFFFF ^ t3 & t11);
  }
  



  protected final void ib6(int a, int b, int c, int d)
  {
    int t1 = a ^ 0xFFFFFFFF;
    int t2 = a ^ b;
    int t3 = c ^ t2;
    int t4 = c | t1;
    int t5 = d ^ t4;
    X1 = (t3 ^ t5);
    int t7 = t3 & t5;
    int t8 = t2 ^ t7;
    int t9 = b | t8;
    X3 = (t5 ^ t9);
    int t11 = b | X3;
    X0 = (t8 ^ t11);
    X2 = (d & t1 ^ t3 ^ t11);
  }
  



  protected final void sb7(int a, int b, int c, int d)
  {
    int t1 = b ^ c;
    int t2 = c & t1;
    int t3 = d ^ t2;
    int t4 = a ^ t3;
    int t5 = d | t1;
    int t6 = t4 & t5;
    X1 = (b ^ t6);
    int t8 = t3 | X1;
    int t9 = a & t4;
    X3 = (t1 ^ t9);
    int t11 = t4 ^ t8;
    int t12 = X3 & t11;
    X2 = (t3 ^ t12);
    X0 = (t11 ^ 0xFFFFFFFF ^ X3 & X2);
  }
  



  protected final void ib7(int a, int b, int c, int d)
  {
    int t3 = c | a & b;
    int t4 = d & (a | b);
    X3 = (t3 ^ t4);
    int t6 = d ^ 0xFFFFFFFF;
    int t7 = b ^ t4;
    int t9 = t7 | X3 ^ t6;
    X1 = (a ^ t9);
    X0 = (c ^ t7 ^ (d | X1));
    X2 = (t3 ^ X1 ^ X0 ^ a & X3);
  }
  



  protected final void LT()
  {
    int x0 = rotateLeft(X0, 13);
    int x2 = rotateLeft(X2, 3);
    int x1 = X1 ^ x0 ^ x2;
    int x3 = X3 ^ x2 ^ x0 << 3;
    
    X1 = rotateLeft(x1, 1);
    X3 = rotateLeft(x3, 7);
    X0 = rotateLeft(x0 ^ X1 ^ X3, 5);
    X2 = rotateLeft(x2 ^ X3 ^ X1 << 7, 22);
  }
  



  protected final void inverseLT()
  {
    int x2 = rotateRight(X2, 22) ^ X3 ^ X1 << 7;
    int x0 = rotateRight(X0, 5) ^ X1 ^ X3;
    int x3 = rotateRight(X3, 7);
    int x1 = rotateRight(X1, 1);
    X3 = (x3 ^ x2 ^ x0 << 3);
    X1 = (x1 ^ x0 ^ x2);
    X2 = rotateRight(x2, 3);
    X0 = rotateRight(x0, 13);
  }
  
  protected abstract int[] makeWorkingKey(byte[] paramArrayOfByte);
  
  protected abstract void encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2);
  
  protected abstract void decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2);
}
