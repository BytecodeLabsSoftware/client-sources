package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.generators.DHKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;













public class DHAgreement
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private DHPrivateKeyParameters key;
  private DHParameters dhParams;
  private BigInteger privateValue;
  private SecureRandom random;
  
  public DHAgreement() {}
  
  public void init(CipherParameters param)
  {
    AsymmetricKeyParameter kParam;
    AsymmetricKeyParameter kParam;
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      random = rParam.getRandom();
      kParam = (AsymmetricKeyParameter)rParam.getParameters();
    }
    else
    {
      random = new SecureRandom();
      kParam = (AsymmetricKeyParameter)param;
    }
    

    if (!(kParam instanceof DHPrivateKeyParameters))
    {
      throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
    }
    
    key = ((DHPrivateKeyParameters)kParam);
    dhParams = key.getParameters();
  }
  



  public BigInteger calculateMessage()
  {
    DHKeyPairGenerator dhGen = new DHKeyPairGenerator();
    dhGen.init(new DHKeyGenerationParameters(random, dhParams));
    AsymmetricCipherKeyPair dhPair = dhGen.generateKeyPair();
    
    privateValue = ((DHPrivateKeyParameters)dhPair.getPrivate()).getX();
    
    return ((DHPublicKeyParameters)dhPair.getPublic()).getY();
  }
  







  public BigInteger calculateAgreement(DHPublicKeyParameters pub, BigInteger message)
  {
    if (!pub.getParameters().equals(dhParams))
    {
      throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
    }
    
    BigInteger p = dhParams.getP();
    
    BigInteger peerY = pub.getY();
    if ((peerY == null) || (peerY.compareTo(ONE) <= 0) || (peerY.compareTo(p.subtract(ONE)) >= 0))
    {
      throw new IllegalArgumentException("Diffie-Hellman public key is weak");
    }
    
    BigInteger result = peerY.modPow(privateValue, p);
    if (result.equals(ONE))
    {
      throw new IllegalStateException("Shared key can't be 1");
    }
    
    return message.modPow(key.getX(), p).multiply(result).mod(p);
  }
}
