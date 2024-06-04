package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;











public class GMSSPublicKey
  extends ASN1Object
{
  private ASN1Integer version;
  private byte[] publicKey;
  
  private GMSSPublicKey(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {
      throw new IllegalArgumentException("size of seq = " + seq.size());
    }
    
    version = ASN1Integer.getInstance(seq.getObjectAt(0));
    publicKey = ASN1OctetString.getInstance(seq.getObjectAt(1)).getOctets();
  }
  
  public GMSSPublicKey(byte[] publicKeyBytes)
  {
    version = new ASN1Integer(0L);
    publicKey = publicKeyBytes;
  }
  
  public static GMSSPublicKey getInstance(Object o)
  {
    if ((o instanceof GMSSPublicKey))
    {
      return (GMSSPublicKey)o;
    }
    if (o != null)
    {
      return new GMSSPublicKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public byte[] getPublicKey()
  {
    return Arrays.clone(publicKey);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(new DEROctetString(publicKey));
    
    return new DERSequence(v);
  }
}
