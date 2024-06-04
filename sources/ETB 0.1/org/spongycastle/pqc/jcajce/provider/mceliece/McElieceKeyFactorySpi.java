package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.pqc.asn1.McEliecePrivateKey;
import org.spongycastle.pqc.asn1.McEliecePublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;















public class McElieceKeyFactorySpi
  extends KeyFactorySpi
  implements AsymmetricKeyInfoConverter
{
  public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
  
  public McElieceKeyFactorySpi() {}
  
  protected PublicKey engineGeneratePublic(KeySpec keySpec)
    throws InvalidKeySpecException
  {
    if ((keySpec instanceof X509EncodedKeySpec))
    {

      byte[] encKey = ((X509EncodedKeySpec)keySpec).getEncoded();
      


      try
      {
        pki = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(encKey));
      }
      catch (IOException e) {
        SubjectPublicKeyInfo pki;
        throw new InvalidKeySpecException(e.toString());
      }
      try
      {
        SubjectPublicKeyInfo pki;
        if (PQCObjectIdentifiers.mcEliece.equals(pki.getAlgorithm().getAlgorithm()))
        {
          McEliecePublicKey key = McEliecePublicKey.getInstance(pki.parsePublicKey());
          
          return new BCMcEliecePublicKey(new McEliecePublicKeyParameters(key.getN(), key.getT(), key.getG()));
        }
        

        throw new InvalidKeySpecException("Unable to recognise OID in McEliece public key");


      }
      catch (IOException cce)
      {

        throw new InvalidKeySpecException("Unable to decode X509EncodedKeySpec: " + cce.getMessage());
      }
    }
    

    throw new InvalidKeySpecException("Unsupported key specification: " + keySpec.getClass() + ".");
  }
  








  protected PrivateKey engineGeneratePrivate(KeySpec keySpec)
    throws InvalidKeySpecException
  {
    if ((keySpec instanceof PKCS8EncodedKeySpec))
    {

      byte[] encKey = ((PKCS8EncodedKeySpec)keySpec).getEncoded();
      



      try
      {
        pki = PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(encKey));
      }
      catch (IOException e) {
        PrivateKeyInfo pki;
        throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec: " + e);
      }
      try
      {
        PrivateKeyInfo pki;
        if (PQCObjectIdentifiers.mcEliece.equals(pki.getPrivateKeyAlgorithm().getAlgorithm()))
        {
          McEliecePrivateKey key = McEliecePrivateKey.getInstance(pki.parsePrivateKey());
          
          return new BCMcEliecePrivateKey(new McEliecePrivateKeyParameters(key.getN(), key.getK(), key.getField(), key.getGoppaPoly(), key.getP1(), key.getP2(), key.getSInv()));
        }
        

        throw new InvalidKeySpecException("Unable to recognise OID in McEliece private key");

      }
      catch (IOException cce)
      {
        throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec.");
      }
    }
    


    throw new InvalidKeySpecException("Unsupported key specification: " + keySpec.getClass() + ".");
  }
  












  public KeySpec getKeySpec(Key key, Class keySpec)
    throws InvalidKeySpecException
  {
    if ((key instanceof BCMcEliecePrivateKey))
    {
      if (PKCS8EncodedKeySpec.class.isAssignableFrom(keySpec))
      {
        return new PKCS8EncodedKeySpec(key.getEncoded());
      }
    }
    else if ((key instanceof BCMcEliecePublicKey))
    {
      if (X509EncodedKeySpec.class.isAssignableFrom(keySpec))
      {
        return new X509EncodedKeySpec(key.getEncoded());
      }
      

    }
    else {
      throw new InvalidKeySpecException("Unsupported key type: " + key.getClass() + ".");
    }
    
    throw new InvalidKeySpecException("Unknown key specification: " + keySpec + ".");
  }
  










  public Key translateKey(Key key)
    throws InvalidKeyException
  {
    if (((key instanceof BCMcEliecePrivateKey)) || ((key instanceof BCMcEliecePublicKey)))
    {

      return key;
    }
    throw new InvalidKeyException("Unsupported key type.");
  }
  


  public PublicKey generatePublic(SubjectPublicKeyInfo pki)
    throws IOException
  {
    ASN1Primitive innerType = pki.parsePublicKey();
    McEliecePublicKey key = McEliecePublicKey.getInstance(innerType);
    return new BCMcEliecePublicKey(new McEliecePublicKeyParameters(key.getN(), key.getT(), key.getG()));
  }
  

  public PrivateKey generatePrivate(PrivateKeyInfo pki)
    throws IOException
  {
    ASN1Primitive innerType = pki.parsePrivateKey().toASN1Primitive();
    McEliecePrivateKey key = McEliecePrivateKey.getInstance(innerType);
    return new BCMcEliecePrivateKey(new McEliecePrivateKeyParameters(key.getN(), key.getK(), key.getField(), key.getGoppaPoly(), key.getP1(), key.getP2(), key.getSInv()));
  }
  

  protected KeySpec engineGetKeySpec(Key key, Class tClass)
    throws InvalidKeySpecException
  {
    return null;
  }
  

  protected Key engineTranslateKey(Key key)
    throws InvalidKeyException
  {
    return null;
  }
  
  private static Digest getDigest(AlgorithmIdentifier algId)
  {
    return new SHA256Digest();
  }
}
