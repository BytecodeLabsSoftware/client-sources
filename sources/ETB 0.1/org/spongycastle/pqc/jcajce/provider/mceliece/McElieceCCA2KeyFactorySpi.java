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
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.spongycastle.pqc.asn1.McElieceCCA2PublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;

















public class McElieceCCA2KeyFactorySpi
  extends KeyFactorySpi
  implements AsymmetricKeyInfoConverter
{
  public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2";
  
  public McElieceCCA2KeyFactorySpi() {}
  
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
        if (PQCObjectIdentifiers.mcElieceCca2.equals(pki.getAlgorithm().getAlgorithm()))
        {
          McElieceCCA2PublicKey key = McElieceCCA2PublicKey.getInstance(pki.parsePublicKey());
          
          return new BCMcElieceCCA2PublicKey(new McElieceCCA2PublicKeyParameters(key.getN(), key.getT(), key.getG(), Utils.getDigest(key.getDigest()).getAlgorithmName()));
        }
        

        throw new InvalidKeySpecException("Unable to recognise OID in McEliece private key");


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
        if (PQCObjectIdentifiers.mcElieceCca2.equals(pki.getPrivateKeyAlgorithm().getAlgorithm()))
        {
          McElieceCCA2PrivateKey key = McElieceCCA2PrivateKey.getInstance(pki.parsePrivateKey());
          
          return new BCMcElieceCCA2PrivateKey(new McElieceCCA2PrivateKeyParameters(key.getN(), key.getK(), key.getField(), key.getGoppaPoly(), key.getP(), Utils.getDigest(key.getDigest()).getAlgorithmName()));
        }
        

        throw new InvalidKeySpecException("Unable to recognise OID in McEliece public key");

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
    if ((key instanceof BCMcElieceCCA2PrivateKey))
    {
      if (PKCS8EncodedKeySpec.class.isAssignableFrom(keySpec))
      {
        return new PKCS8EncodedKeySpec(key.getEncoded());
      }
    }
    else if ((key instanceof BCMcElieceCCA2PublicKey))
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
    if (((key instanceof BCMcElieceCCA2PrivateKey)) || ((key instanceof BCMcElieceCCA2PublicKey)))
    {

      return key;
    }
    throw new InvalidKeyException("Unsupported key type.");
  }
  


  public PublicKey generatePublic(SubjectPublicKeyInfo pki)
    throws IOException
  {
    ASN1Primitive innerType = pki.parsePublicKey();
    McElieceCCA2PublicKey key = McElieceCCA2PublicKey.getInstance(innerType);
    return new BCMcElieceCCA2PublicKey(new McElieceCCA2PublicKeyParameters(key.getN(), key.getT(), key.getG(), Utils.getDigest(key.getDigest()).getAlgorithmName()));
  }
  

  public PrivateKey generatePrivate(PrivateKeyInfo pki)
    throws IOException
  {
    ASN1Primitive innerType = pki.parsePrivateKey().toASN1Primitive();
    McElieceCCA2PrivateKey key = McElieceCCA2PrivateKey.getInstance(innerType);
    return new BCMcElieceCCA2PrivateKey(new McElieceCCA2PrivateKeyParameters(key.getN(), key.getK(), key.getField(), key.getGoppaPoly(), key.getP(), null));
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
}
