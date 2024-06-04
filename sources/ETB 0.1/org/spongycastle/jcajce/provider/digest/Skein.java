package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SkeinDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.macs.SkeinMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;



public class Skein
{
  private Skein() {}
  
  public static class DigestSkein256
    extends BCMessageDigest
    implements Cloneable
  {
    public DigestSkein256(int outputSize)
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      BCMessageDigest d = (BCMessageDigest)super.clone();
      digest = new SkeinDigest((SkeinDigest)digest);
      
      return d;
    }
  }
  
  public static class Digest_256_128
    extends Skein.DigestSkein256
  {
    public Digest_256_128()
    {
      super();
    }
  }
  
  public static class Digest_256_160
    extends Skein.DigestSkein256
  {
    public Digest_256_160()
    {
      super();
    }
  }
  
  public static class Digest_256_224
    extends Skein.DigestSkein256
  {
    public Digest_256_224()
    {
      super();
    }
  }
  
  public static class Digest_256_256
    extends Skein.DigestSkein256
  {
    public Digest_256_256()
    {
      super();
    }
  }
  
  public static class DigestSkein512
    extends BCMessageDigest
    implements Cloneable
  {
    public DigestSkein512(int outputSize)
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      BCMessageDigest d = (BCMessageDigest)super.clone();
      digest = new SkeinDigest((SkeinDigest)digest);
      
      return d;
    }
  }
  
  public static class Digest_512_128
    extends Skein.DigestSkein512
  {
    public Digest_512_128()
    {
      super();
    }
  }
  
  public static class Digest_512_160
    extends Skein.DigestSkein512
  {
    public Digest_512_160()
    {
      super();
    }
  }
  
  public static class Digest_512_224
    extends Skein.DigestSkein512
  {
    public Digest_512_224()
    {
      super();
    }
  }
  
  public static class Digest_512_256
    extends Skein.DigestSkein512
  {
    public Digest_512_256()
    {
      super();
    }
  }
  
  public static class Digest_512_384
    extends Skein.DigestSkein512
  {
    public Digest_512_384()
    {
      super();
    }
  }
  
  public static class Digest_512_512
    extends Skein.DigestSkein512
  {
    public Digest_512_512()
    {
      super();
    }
  }
  
  public static class DigestSkein1024
    extends BCMessageDigest
    implements Cloneable
  {
    public DigestSkein1024(int outputSize)
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      BCMessageDigest d = (BCMessageDigest)super.clone();
      digest = new SkeinDigest((SkeinDigest)digest);
      
      return d;
    }
  }
  
  public static class Digest_1024_384
    extends Skein.DigestSkein1024
  {
    public Digest_1024_384()
    {
      super();
    }
  }
  
  public static class Digest_1024_512
    extends Skein.DigestSkein1024
  {
    public Digest_1024_512()
    {
      super();
    }
  }
  
  public static class Digest_1024_1024
    extends Skein.DigestSkein1024
  {
    public Digest_1024_1024()
    {
      super();
    }
  }
  



  public static class HashMac_256_128
    extends BaseMac
  {
    public HashMac_256_128()
    {
      super();
    }
  }
  
  public static class HashMac_256_160
    extends BaseMac
  {
    public HashMac_256_160()
    {
      super();
    }
  }
  
  public static class HashMac_256_224
    extends BaseMac
  {
    public HashMac_256_224()
    {
      super();
    }
  }
  
  public static class HashMac_256_256
    extends BaseMac
  {
    public HashMac_256_256()
    {
      super();
    }
  }
  
  public static class HashMac_512_128
    extends BaseMac
  {
    public HashMac_512_128()
    {
      super();
    }
  }
  
  public static class HashMac_512_160
    extends BaseMac
  {
    public HashMac_512_160()
    {
      super();
    }
  }
  
  public static class HashMac_512_224
    extends BaseMac
  {
    public HashMac_512_224()
    {
      super();
    }
  }
  
  public static class HashMac_512_256
    extends BaseMac
  {
    public HashMac_512_256()
    {
      super();
    }
  }
  
  public static class HashMac_512_384
    extends BaseMac
  {
    public HashMac_512_384()
    {
      super();
    }
  }
  
  public static class HashMac_512_512
    extends BaseMac
  {
    public HashMac_512_512()
    {
      super();
    }
  }
  
  public static class HashMac_1024_384
    extends BaseMac
  {
    public HashMac_1024_384()
    {
      super();
    }
  }
  
  public static class HashMac_1024_512
    extends BaseMac
  {
    public HashMac_1024_512()
    {
      super();
    }
  }
  
  public static class HashMac_1024_1024
    extends BaseMac
  {
    public HashMac_1024_1024()
    {
      super();
    }
  }
  
  public static class HMacKeyGenerator_256_128
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_256_128()
    {
      super(128, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_256_160
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_256_160()
    {
      super(160, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_256_224
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_256_224()
    {
      super(224, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_256_256
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_256_256()
    {
      super(256, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_512_128
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_512_128()
    {
      super(128, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_512_160
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_512_160()
    {
      super(160, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_512_224
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_512_224()
    {
      super(224, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_512_256
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_512_256()
    {
      super(256, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_512_384
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_512_384()
    {
      super(384, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_512_512
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_512_512()
    {
      super(512, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_1024_384
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_1024_384()
    {
      super(384, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_1024_512
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_1024_512()
    {
      super(512, new CipherKeyGenerator());
    }
  }
  
  public static class HMacKeyGenerator_1024_1024
    extends BaseKeyGenerator
  {
    public HMacKeyGenerator_1024_1024()
    {
      super(1024, new CipherKeyGenerator());
    }
  }
  



  public static class SkeinMac_256_128
    extends BaseMac
  {
    public SkeinMac_256_128()
    {
      super();
    }
  }
  
  public static class SkeinMac_256_160
    extends BaseMac
  {
    public SkeinMac_256_160()
    {
      super();
    }
  }
  
  public static class SkeinMac_256_224
    extends BaseMac
  {
    public SkeinMac_256_224()
    {
      super();
    }
  }
  
  public static class SkeinMac_256_256
    extends BaseMac
  {
    public SkeinMac_256_256()
    {
      super();
    }
  }
  
  public static class SkeinMac_512_128
    extends BaseMac
  {
    public SkeinMac_512_128()
    {
      super();
    }
  }
  
  public static class SkeinMac_512_160
    extends BaseMac
  {
    public SkeinMac_512_160()
    {
      super();
    }
  }
  
  public static class SkeinMac_512_224
    extends BaseMac
  {
    public SkeinMac_512_224()
    {
      super();
    }
  }
  
  public static class SkeinMac_512_256
    extends BaseMac
  {
    public SkeinMac_512_256()
    {
      super();
    }
  }
  
  public static class SkeinMac_512_384
    extends BaseMac
  {
    public SkeinMac_512_384()
    {
      super();
    }
  }
  
  public static class SkeinMac_512_512
    extends BaseMac
  {
    public SkeinMac_512_512()
    {
      super();
    }
  }
  
  public static class SkeinMac_1024_384
    extends BaseMac
  {
    public SkeinMac_1024_384()
    {
      super();
    }
  }
  
  public static class SkeinMac_1024_512
    extends BaseMac
  {
    public SkeinMac_1024_512()
    {
      super();
    }
  }
  
  public static class SkeinMac_1024_1024
    extends BaseMac
  {
    public SkeinMac_1024_1024()
    {
      super();
    }
  }
  
  public static class SkeinMacKeyGenerator_256_128
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_256_128()
    {
      super(128, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_256_160
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_256_160()
    {
      super(160, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_256_224
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_256_224()
    {
      super(224, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_256_256
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_256_256()
    {
      super(256, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_512_128
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_512_128()
    {
      super(128, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_512_160
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_512_160()
    {
      super(160, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_512_224
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_512_224()
    {
      super(224, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_512_256
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_512_256()
    {
      super(256, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_512_384
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_512_384()
    {
      super(384, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_512_512
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_512_512()
    {
      super(512, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_1024_384
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_1024_384()
    {
      super(384, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_1024_512
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_1024_512()
    {
      super(512, new CipherKeyGenerator());
    }
  }
  
  public static class SkeinMacKeyGenerator_1024_1024
    extends BaseKeyGenerator
  {
    public SkeinMacKeyGenerator_1024_1024()
    {
      super(1024, new CipherKeyGenerator());
    }
  }
  
  public static class Mappings
    extends DigestAlgorithmProvider
  {
    private static final String PREFIX = Skein.class.getName();
    


    public Mappings() {}
    


    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("MessageDigest.Skein-256-128", PREFIX + "$Digest_256_128");
      provider.addAlgorithm("MessageDigest.Skein-256-160", PREFIX + "$Digest_256_160");
      provider.addAlgorithm("MessageDigest.Skein-256-224", PREFIX + "$Digest_256_224");
      provider.addAlgorithm("MessageDigest.Skein-256-256", PREFIX + "$Digest_256_256");
      
      provider.addAlgorithm("MessageDigest.Skein-512-128", PREFIX + "$Digest_512_128");
      provider.addAlgorithm("MessageDigest.Skein-512-160", PREFIX + "$Digest_512_160");
      provider.addAlgorithm("MessageDigest.Skein-512-224", PREFIX + "$Digest_512_224");
      provider.addAlgorithm("MessageDigest.Skein-512-256", PREFIX + "$Digest_512_256");
      provider.addAlgorithm("MessageDigest.Skein-512-384", PREFIX + "$Digest_512_384");
      provider.addAlgorithm("MessageDigest.Skein-512-512", PREFIX + "$Digest_512_512");
      
      provider.addAlgorithm("MessageDigest.Skein-1024-384", PREFIX + "$Digest_1024_384");
      provider.addAlgorithm("MessageDigest.Skein-1024-512", PREFIX + "$Digest_1024_512");
      provider.addAlgorithm("MessageDigest.Skein-1024-1024", PREFIX + "$Digest_1024_1024");
      
      addHMACAlgorithm(provider, "Skein-256-128", PREFIX + "$HashMac_256_128", PREFIX + "$HMacKeyGenerator_256_128");
      addHMACAlgorithm(provider, "Skein-256-160", PREFIX + "$HashMac_256_160", PREFIX + "$HMacKeyGenerator_256_160");
      addHMACAlgorithm(provider, "Skein-256-224", PREFIX + "$HashMac_256_224", PREFIX + "$HMacKeyGenerator_256_224");
      addHMACAlgorithm(provider, "Skein-256-256", PREFIX + "$HashMac_256_256", PREFIX + "$HMacKeyGenerator_256_256");
      
      addHMACAlgorithm(provider, "Skein-512-128", PREFIX + "$HashMac_512_128", PREFIX + "$HMacKeyGenerator_512_128");
      addHMACAlgorithm(provider, "Skein-512-160", PREFIX + "$HashMac_512_160", PREFIX + "$HMacKeyGenerator_512_160");
      addHMACAlgorithm(provider, "Skein-512-224", PREFIX + "$HashMac_512_224", PREFIX + "$HMacKeyGenerator_512_224");
      addHMACAlgorithm(provider, "Skein-512-256", PREFIX + "$HashMac_512_256", PREFIX + "$HMacKeyGenerator_512_256");
      addHMACAlgorithm(provider, "Skein-512-384", PREFIX + "$HashMac_512_384", PREFIX + "$HMacKeyGenerator_512_384");
      addHMACAlgorithm(provider, "Skein-512-512", PREFIX + "$HashMac_512_512", PREFIX + "$HMacKeyGenerator_512_512");
      
      addHMACAlgorithm(provider, "Skein-1024-384", PREFIX + "$HashMac_1024_384", PREFIX + "$HMacKeyGenerator_1024_384");
      addHMACAlgorithm(provider, "Skein-1024-512", PREFIX + "$HashMac_1024_512", PREFIX + "$HMacKeyGenerator_1024_512");
      addHMACAlgorithm(provider, "Skein-1024-1024", PREFIX + "$HashMac_1024_1024", PREFIX + "$HMacKeyGenerator_1024_1024");
      
      addSkeinMacAlgorithm(provider, 256, 128);
      addSkeinMacAlgorithm(provider, 256, 160);
      addSkeinMacAlgorithm(provider, 256, 224);
      addSkeinMacAlgorithm(provider, 256, 256);
      
      addSkeinMacAlgorithm(provider, 512, 128);
      addSkeinMacAlgorithm(provider, 512, 160);
      addSkeinMacAlgorithm(provider, 512, 224);
      addSkeinMacAlgorithm(provider, 512, 256);
      addSkeinMacAlgorithm(provider, 512, 384);
      addSkeinMacAlgorithm(provider, 512, 512);
      
      addSkeinMacAlgorithm(provider, 1024, 384);
      addSkeinMacAlgorithm(provider, 1024, 512);
      addSkeinMacAlgorithm(provider, 1024, 1024);
    }
    
    private void addSkeinMacAlgorithm(ConfigurableProvider provider, int blockSize, int outputSize)
    {
      String mainName = "Skein-MAC-" + blockSize + "-" + outputSize;
      String algorithmClassName = PREFIX + "$SkeinMac_" + blockSize + "_" + outputSize;
      String keyGeneratorClassName = PREFIX + "$SkeinMacKeyGenerator_" + blockSize + "_" + outputSize;
      
      provider.addAlgorithm("Mac." + mainName, algorithmClassName);
      provider.addAlgorithm("Alg.Alias.Mac.Skein-MAC" + blockSize + "/" + outputSize, mainName);
      provider.addAlgorithm("KeyGenerator." + mainName, keyGeneratorClassName);
      provider.addAlgorithm("Alg.Alias.KeyGenerator.Skein-MAC" + blockSize + "/" + outputSize, mainName);
    }
  }
}
