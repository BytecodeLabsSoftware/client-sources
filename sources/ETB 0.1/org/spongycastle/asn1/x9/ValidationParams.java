package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;











public class ValidationParams
  extends ASN1Object
{
  private DERBitString seed;
  private ASN1Integer pgenCounter;
  
  public static ValidationParams getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  
  public static ValidationParams getInstance(Object obj)
  {
    if ((obj instanceof ValidationParams))
    {
      return (ValidationParams)obj;
    }
    if (obj != null)
    {
      return new ValidationParams(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ValidationParams(byte[] seed, int pgenCounter)
  {
    if (seed == null)
    {
      throw new IllegalArgumentException("'seed' cannot be null");
    }
    
    this.seed = new DERBitString(seed);
    this.pgenCounter = new ASN1Integer(pgenCounter);
  }
  
  public ValidationParams(DERBitString seed, ASN1Integer pgenCounter)
  {
    if (seed == null)
    {
      throw new IllegalArgumentException("'seed' cannot be null");
    }
    if (pgenCounter == null)
    {
      throw new IllegalArgumentException("'pgenCounter' cannot be null");
    }
    
    this.seed = seed;
    this.pgenCounter = pgenCounter;
  }
  
  private ValidationParams(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {
      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    seed = DERBitString.getInstance(seq.getObjectAt(0));
    pgenCounter = ASN1Integer.getInstance(seq.getObjectAt(1));
  }
  
  public byte[] getSeed()
  {
    return seed.getBytes();
  }
  
  public BigInteger getPgenCounter()
  {
    return pgenCounter.getPositiveValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(seed);
    v.add(pgenCounter);
    return new DERSequence(v);
  }
}
