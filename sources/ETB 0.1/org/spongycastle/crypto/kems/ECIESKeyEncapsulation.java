package org.spongycastle.crypto.kems;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.KeyEncapsulation;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;




public class ECIESKeyEncapsulation
  implements KeyEncapsulation
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  

  private DerivationFunction kdf;
  

  private SecureRandom rnd;
  
  private ECKeyParameters key;
  
  private boolean CofactorMode;
  
  private boolean OldCofactorMode;
  
  private boolean SingleHashMode;
  

  public ECIESKeyEncapsulation(DerivationFunction kdf, SecureRandom rnd)
  {
    this.kdf = kdf;
    this.rnd = rnd;
    CofactorMode = false;
    OldCofactorMode = false;
    SingleHashMode = false;
  }
  














  public ECIESKeyEncapsulation(DerivationFunction kdf, SecureRandom rnd, boolean cofactorMode, boolean oldCofactorMode, boolean singleHashMode)
  {
    this.kdf = kdf;
    this.rnd = rnd;
    


    CofactorMode = cofactorMode;
    OldCofactorMode = oldCofactorMode;
    SingleHashMode = singleHashMode;
  }
  





  public void init(CipherParameters key)
    throws IllegalArgumentException
  {
    if (!(key instanceof ECKeyParameters))
    {
      throw new IllegalArgumentException("EC key required");
    }
    

    this.key = ((ECKeyParameters)key);
  }
  









  public CipherParameters encrypt(byte[] out, int outOff, int keyLen)
    throws IllegalArgumentException
  {
    if (!(key instanceof ECPublicKeyParameters))
    {
      throw new IllegalArgumentException("Public key required for encryption");
    }
    
    ECPublicKeyParameters ecPubKey = (ECPublicKeyParameters)key;
    ECDomainParameters ecParams = ecPubKey.getParameters();
    ECCurve curve = ecParams.getCurve();
    BigInteger n = ecParams.getN();
    BigInteger h = ecParams.getH();
    

    BigInteger r = BigIntegers.createRandomInRange(ONE, n, rnd);
    

    BigInteger rPrime = CofactorMode ? r.multiply(h).mod(n) : r;
    
    ECMultiplier basePointMultiplier = createBasePointMultiplier();
    


    ECPoint[] ghTilde = {basePointMultiplier.multiply(ecParams.getG(), r), ecPubKey.getQ().multiply(rPrime) };
    


    curve.normalizeAll(ghTilde);
    
    ECPoint gTilde = ghTilde[0];ECPoint hTilde = ghTilde[1];
    

    byte[] C = gTilde.getEncoded(false);
    System.arraycopy(C, 0, out, outOff, C.length);
    

    byte[] PEH = hTilde.getAffineXCoord().getEncoded();
    
    return deriveKey(keyLen, C, PEH);
  }
  







  public CipherParameters encrypt(byte[] out, int keyLen)
  {
    return encrypt(out, 0, keyLen);
  }
  









  public CipherParameters decrypt(byte[] in, int inOff, int inLen, int keyLen)
    throws IllegalArgumentException
  {
    if (!(key instanceof ECPrivateKeyParameters))
    {
      throw new IllegalArgumentException("Private key required for encryption");
    }
    
    ECPrivateKeyParameters ecPrivKey = (ECPrivateKeyParameters)key;
    ECDomainParameters ecParams = ecPrivKey.getParameters();
    ECCurve curve = ecParams.getCurve();
    BigInteger n = ecParams.getN();
    BigInteger h = ecParams.getH();
    

    byte[] C = new byte[inLen];
    System.arraycopy(in, inOff, C, 0, inLen);
    

    ECPoint gTilde = curve.decodePoint(C);
    

    ECPoint gHat = gTilde;
    if ((CofactorMode) || (OldCofactorMode))
    {
      gHat = gHat.multiply(h);
    }
    
    BigInteger xHat = ecPrivKey.getD();
    if (CofactorMode)
    {
      xHat = xHat.multiply(h.modInverse(n)).mod(n);
    }
    
    ECPoint hTilde = gHat.multiply(xHat).normalize();
    

    byte[] PEH = hTilde.getAffineXCoord().getEncoded();
    
    return deriveKey(keyLen, C, PEH);
  }
  







  public CipherParameters decrypt(byte[] in, int keyLen)
  {
    return decrypt(in, 0, in.length, keyLen);
  }
  
  protected ECMultiplier createBasePointMultiplier()
  {
    return new FixedPointCombMultiplier();
  }
  
  protected KeyParameter deriveKey(int keyLen, byte[] C, byte[] PEH)
  {
    byte[] kdfInput = PEH;
    if (!SingleHashMode)
    {
      kdfInput = Arrays.concatenate(C, PEH);
      Arrays.fill(PEH, (byte)0);
    }
    

    try
    {
      kdf.init(new KDFParameters(kdfInput, null));
      

      byte[] K = new byte[keyLen];
      kdf.generateBytes(K, 0, K.length);
      

      return new KeyParameter(K);
    }
    finally
    {
      Arrays.fill(kdfInput, (byte)0);
    }
  }
}
