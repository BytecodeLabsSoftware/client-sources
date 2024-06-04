package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.util.Arrays;


public class MacData
  extends ASN1Object
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  
  DigestInfo digInfo;
  
  byte[] salt;
  BigInteger iterationCount;
  
  public static MacData getInstance(Object obj)
  {
    if ((obj instanceof MacData))
    {
      return (MacData)obj;
    }
    if (obj != null)
    {
      return new MacData(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private MacData(ASN1Sequence seq)
  {
    digInfo = DigestInfo.getInstance(seq.getObjectAt(0));
    
    salt = Arrays.clone(((ASN1OctetString)seq.getObjectAt(1)).getOctets());
    
    if (seq.size() == 3)
    {
      iterationCount = ((ASN1Integer)seq.getObjectAt(2)).getValue();
    }
    else
    {
      iterationCount = ONE;
    }
  }
  



  public MacData(DigestInfo digInfo, byte[] salt, int iterationCount)
  {
    this.digInfo = digInfo;
    this.salt = Arrays.clone(salt);
    this.iterationCount = BigInteger.valueOf(iterationCount);
  }
  
  public DigestInfo getMac()
  {
    return digInfo;
  }
  
  public byte[] getSalt()
  {
    return Arrays.clone(salt);
  }
  
  public BigInteger getIterationCount()
  {
    return iterationCount;
  }
  











  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(digInfo);
    v.add(new DEROctetString(salt));
    
    if (!iterationCount.equals(ONE))
    {
      v.add(new ASN1Integer(iterationCount));
    }
    
    return new DERSequence(v);
  }
}
