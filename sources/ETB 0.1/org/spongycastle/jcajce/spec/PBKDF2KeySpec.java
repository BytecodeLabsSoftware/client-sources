package org.spongycastle.jcajce.spec;

import javax.crypto.spec.PBEKeySpec;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;





public class PBKDF2KeySpec
  extends PBEKeySpec
{
  private static final AlgorithmIdentifier defaultPRF = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA1, DERNull.INSTANCE);
  




  private AlgorithmIdentifier prf;
  





  public PBKDF2KeySpec(char[] password, byte[] salt, int iterationCount, int keySize, AlgorithmIdentifier prf)
  {
    super(password, salt, iterationCount, keySize);
    
    this.prf = prf;
  }
  





  public boolean isDefaultPrf()
  {
    return defaultPRF.equals(prf);
  }
  





  public AlgorithmIdentifier getPrf()
  {
    return prf;
  }
}
