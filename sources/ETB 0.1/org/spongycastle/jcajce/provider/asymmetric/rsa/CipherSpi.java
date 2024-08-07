package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.ISO9796d1Encoding;
import org.spongycastle.crypto.encodings.OAEPEncoding;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import org.spongycastle.jcajce.provider.util.BadBlockException;
import org.spongycastle.jcajce.provider.util.DigestFactory;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.util.Strings;


public class CipherSpi
  extends BaseCipherSpi
{
  private final JcaJceHelper helper = new BCJcaJceHelper();
  
  private AsymmetricBlockCipher cipher;
  private AlgorithmParameterSpec paramSpec;
  private AlgorithmParameters engineParams;
  private boolean publicKeyOnly = false;
  private boolean privateKeyOnly = false;
  private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
  

  public CipherSpi(AsymmetricBlockCipher engine)
  {
    cipher = engine;
  }
  

  public CipherSpi(OAEPParameterSpec pSpec)
  {
    try
    {
      initFromSpec(pSpec);
    }
    catch (NoSuchPaddingException e)
    {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
  



  public CipherSpi(boolean publicKeyOnly, boolean privateKeyOnly, AsymmetricBlockCipher engine)
  {
    this.publicKeyOnly = publicKeyOnly;
    this.privateKeyOnly = privateKeyOnly;
    cipher = engine;
  }
  

  private void initFromSpec(OAEPParameterSpec pSpec)
    throws NoSuchPaddingException
  {
    MGF1ParameterSpec mgfParams = (MGF1ParameterSpec)pSpec.getMGFParameters();
    Digest digest = DigestFactory.getDigest(mgfParams.getDigestAlgorithm());
    
    if (digest == null)
    {
      throw new NoSuchPaddingException("no match on OAEP constructor for digest algorithm: " + mgfParams.getDigestAlgorithm());
    }
    
    cipher = new OAEPEncoding(new RSABlindedEngine(), digest, ((PSource.PSpecified)pSpec.getPSource()).getValue());
    paramSpec = pSpec;
  }
  
  protected int engineGetBlockSize()
  {
    try
    {
      return cipher.getInputBlockSize();
    }
    catch (NullPointerException e)
    {
      throw new IllegalStateException("RSA Cipher not initialised");
    }
  }
  

  protected int engineGetKeySize(Key key)
  {
    if ((key instanceof RSAPrivateKey))
    {
      RSAPrivateKey k = (RSAPrivateKey)key;
      
      return k.getModulus().bitLength();
    }
    if ((key instanceof RSAPublicKey))
    {
      RSAPublicKey k = (RSAPublicKey)key;
      
      return k.getModulus().bitLength();
    }
    
    throw new IllegalArgumentException("not an RSA key!");
  }
  

  protected int engineGetOutputSize(int inputLen)
  {
    try
    {
      return cipher.getOutputBlockSize();
    }
    catch (NullPointerException e)
    {
      throw new IllegalStateException("RSA Cipher not initialised");
    }
  }
  
  protected AlgorithmParameters engineGetParameters()
  {
    if (engineParams == null)
    {
      if (paramSpec != null)
      {
        try
        {
          engineParams = helper.createAlgorithmParameters("OAEP");
          engineParams.init(paramSpec);
        }
        catch (Exception e)
        {
          throw new RuntimeException(e.toString());
        }
      }
    }
    
    return engineParams;
  }
  

  protected void engineSetMode(String mode)
    throws NoSuchAlgorithmException
  {
    String md = Strings.toUpperCase(mode);
    
    if ((md.equals("NONE")) || (md.equals("ECB")))
    {
      return;
    }
    
    if (md.equals("1"))
    {
      privateKeyOnly = true;
      publicKeyOnly = false;
      return;
    }
    if (md.equals("2"))
    {
      privateKeyOnly = false;
      publicKeyOnly = true;
      return;
    }
    
    throw new NoSuchAlgorithmException("can't support mode " + mode);
  }
  

  protected void engineSetPadding(String padding)
    throws NoSuchPaddingException
  {
    String pad = Strings.toUpperCase(padding);
    
    if (pad.equals("NOPADDING"))
    {
      cipher = new RSABlindedEngine();
    }
    else if (pad.equals("PKCS1PADDING"))
    {
      cipher = new PKCS1Encoding(new RSABlindedEngine());
    }
    else if (pad.equals("ISO9796-1PADDING"))
    {
      cipher = new ISO9796d1Encoding(new RSABlindedEngine());
    }
    else if (pad.equals("OAEPWITHMD5ANDMGF1PADDING"))
    {
      initFromSpec(new OAEPParameterSpec("MD5", "MGF1", new MGF1ParameterSpec("MD5"), PSource.PSpecified.DEFAULT));
    }
    else if (pad.equals("OAEPPADDING"))
    {
      initFromSpec(OAEPParameterSpec.DEFAULT);
    }
    else if ((pad.equals("OAEPWITHSHA1ANDMGF1PADDING")) || (pad.equals("OAEPWITHSHA-1ANDMGF1PADDING")))
    {
      initFromSpec(OAEPParameterSpec.DEFAULT);
    }
    else if ((pad.equals("OAEPWITHSHA224ANDMGF1PADDING")) || (pad.equals("OAEPWITHSHA-224ANDMGF1PADDING")))
    {
      initFromSpec(new OAEPParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), PSource.PSpecified.DEFAULT));
    }
    else if ((pad.equals("OAEPWITHSHA256ANDMGF1PADDING")) || (pad.equals("OAEPWITHSHA-256ANDMGF1PADDING")))
    {
      initFromSpec(new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
    }
    else if ((pad.equals("OAEPWITHSHA384ANDMGF1PADDING")) || (pad.equals("OAEPWITHSHA-384ANDMGF1PADDING")))
    {
      initFromSpec(new OAEPParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, PSource.PSpecified.DEFAULT));
    }
    else if ((pad.equals("OAEPWITHSHA512ANDMGF1PADDING")) || (pad.equals("OAEPWITHSHA-512ANDMGF1PADDING")))
    {
      initFromSpec(new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSource.PSpecified.DEFAULT));
    }
    else if (pad.equals("OAEPWITHSHA3-224ANDMGF1PADDING"))
    {
      initFromSpec(new OAEPParameterSpec("SHA3-224", "MGF1", new MGF1ParameterSpec("SHA3-224"), PSource.PSpecified.DEFAULT));
    }
    else if (pad.equals("OAEPWITHSHA3-256ANDMGF1PADDING"))
    {
      initFromSpec(new OAEPParameterSpec("SHA3-256", "MGF1", new MGF1ParameterSpec("SHA3-256"), PSource.PSpecified.DEFAULT));
    }
    else if (pad.equals("OAEPWITHSHA3-384ANDMGF1PADDING"))
    {
      initFromSpec(new OAEPParameterSpec("SHA3-384", "MGF1", new MGF1ParameterSpec("SHA3-384"), PSource.PSpecified.DEFAULT));
    }
    else if (pad.equals("OAEPWITHSHA3-512ANDMGF1PADDING"))
    {
      initFromSpec(new OAEPParameterSpec("SHA3-512", "MGF1", new MGF1ParameterSpec("SHA3-512"), PSource.PSpecified.DEFAULT));
    }
    else
    {
      throw new NoSuchPaddingException(padding + " unavailable with RSA.");
    }
  }
  






  protected void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if ((params == null) || ((params instanceof OAEPParameterSpec))) {
      CipherParameters param;
      if ((key instanceof RSAPublicKey))
      {
        if ((privateKeyOnly) && (opmode == 1))
        {
          throw new InvalidKeyException("mode 1 requires RSAPrivateKey");
        }
        

        param = RSAUtil.generatePublicKeyParameter((RSAPublicKey)key);
      } else { CipherParameters param;
        if ((key instanceof RSAPrivateKey))
        {
          if ((publicKeyOnly) && (opmode == 1))
          {
            throw new InvalidKeyException("mode 2 requires RSAPublicKey");
          }
          

          param = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)key);
        }
        else
        {
          throw new InvalidKeyException("unknown key type passed to RSA");
        } }
      CipherParameters param;
      if (params != null)
      {
        OAEPParameterSpec spec = (OAEPParameterSpec)params;
        
        paramSpec = params;
        
        if ((!spec.getMGFAlgorithm().equalsIgnoreCase("MGF1")) && (!spec.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId())))
        {
          throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
        }
        
        if (!(spec.getMGFParameters() instanceof MGF1ParameterSpec))
        {
          throw new InvalidAlgorithmParameterException("unkown MGF parameters");
        }
        
        Digest digest = DigestFactory.getDigest(spec.getDigestAlgorithm());
        
        if (digest == null)
        {
          throw new InvalidAlgorithmParameterException("no match on digest algorithm: " + spec.getDigestAlgorithm());
        }
        
        MGF1ParameterSpec mgfParams = (MGF1ParameterSpec)spec.getMGFParameters();
        Digest mgfDigest = DigestFactory.getDigest(mgfParams.getDigestAlgorithm());
        
        if (mgfDigest == null)
        {
          throw new InvalidAlgorithmParameterException("no match on MGF digest algorithm: " + mgfParams.getDigestAlgorithm());
        }
        
        cipher = new OAEPEncoding(new RSABlindedEngine(), digest, mgfDigest, ((PSource.PSpecified)spec.getPSource()).getValue());
      }
    }
    else
    {
      throw new InvalidAlgorithmParameterException("unknown parameter type: " + params.getClass().getName());
    }
    CipherParameters param;
    if (!(cipher instanceof RSABlindedEngine))
    {
      if (random != null)
      {
        param = new ParametersWithRandom(param, random);
      }
      else
      {
        param = new ParametersWithRandom(param, new SecureRandom());
      }
    }
    
    bOut.reset();
    
    switch (opmode)
    {
    case 1: 
    case 3: 
      cipher.init(true, param);
      break;
    case 2: 
    case 4: 
      cipher.init(false, param);
      break;
    default: 
      throw new InvalidParameterException("unknown opmode " + opmode + " passed to RSA");
    }
    
  }
  



  protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    AlgorithmParameterSpec paramSpec = null;
    
    if (params != null)
    {
      try
      {
        paramSpec = params.getParameterSpec(OAEPParameterSpec.class);
      }
      catch (InvalidParameterSpecException e)
      {
        throw new InvalidAlgorithmParameterException("cannot recognise parameters: " + e.toString(), e);
      }
    }
    
    engineParams = params;
    engineInit(opmode, key, paramSpec, random);
  }
  



  protected void engineInit(int opmode, Key key, SecureRandom random)
    throws InvalidKeyException
  {
    try
    {
      engineInit(opmode, key, (AlgorithmParameterSpec)null, random);

    }
    catch (InvalidAlgorithmParameterException e)
    {
      throw new InvalidKeyException("Eeeek! " + e.toString(), e);
    }
  }
  



  protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen)
  {
    bOut.write(input, inputOffset, inputLen);
    
    if ((cipher instanceof RSABlindedEngine))
    {
      if (bOut.size() > cipher.getInputBlockSize() + 1)
      {
        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }
      

    }
    else if (bOut.size() > cipher.getInputBlockSize())
    {
      throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
    }
    

    return null;
  }
  





  protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
  {
    bOut.write(input, inputOffset, inputLen);
    
    if ((cipher instanceof RSABlindedEngine))
    {
      if (bOut.size() > cipher.getInputBlockSize() + 1)
      {
        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }
      

    }
    else if (bOut.size() > cipher.getInputBlockSize())
    {
      throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
    }
    

    return 0;
  }
  



  protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
    throws IllegalBlockSizeException, BadPaddingException
  {
    if (input != null)
    {
      bOut.write(input, inputOffset, inputLen);
    }
    
    if ((cipher instanceof RSABlindedEngine))
    {
      if (bOut.size() > cipher.getInputBlockSize() + 1)
      {
        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }
      

    }
    else if (bOut.size() > cipher.getInputBlockSize())
    {
      throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
    }
    

    return getOutput();
  }
  





  protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
    throws IllegalBlockSizeException, BadPaddingException
  {
    if (input != null)
    {
      bOut.write(input, inputOffset, inputLen);
    }
    
    if ((cipher instanceof RSABlindedEngine))
    {
      if (bOut.size() > cipher.getInputBlockSize() + 1)
      {
        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }
      

    }
    else if (bOut.size() > cipher.getInputBlockSize())
    {
      throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
    }
    

    byte[] out = getOutput();
    
    for (int i = 0; i != out.length; i++)
    {
      output[(outputOffset + i)] = out[i];
    }
    
    return out.length;
  }
  
  private byte[] getOutput()
    throws BadPaddingException
  {
    try
    {
      byte[] bytes = bOut.toByteArray();
      
      return cipher.processBlock(bytes, 0, bytes.length);
    }
    catch (InvalidCipherTextException e)
    {
      throw new BadBlockException("unable to decrypt block", e);
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new BadBlockException("unable to decrypt block", e);
    }
    finally
    {
      bOut.reset();
    }
  }
  




  public static class NoPadding
    extends CipherSpi
  {
    public NoPadding()
    {
      super();
    }
  }
  
  public static class PKCS1v1_5Padding
    extends CipherSpi
  {
    public PKCS1v1_5Padding()
    {
      super();
    }
  }
  
  public static class PKCS1v1_5Padding_PrivateOnly
    extends CipherSpi
  {
    public PKCS1v1_5Padding_PrivateOnly()
    {
      super(true, new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class PKCS1v1_5Padding_PublicOnly
    extends CipherSpi
  {
    public PKCS1v1_5Padding_PublicOnly()
    {
      super(false, new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class OAEPPadding
    extends CipherSpi
  {
    public OAEPPadding()
    {
      super();
    }
  }
  
  public static class ISO9796d1Padding
    extends CipherSpi
  {
    public ISO9796d1Padding()
    {
      super();
    }
  }
}
