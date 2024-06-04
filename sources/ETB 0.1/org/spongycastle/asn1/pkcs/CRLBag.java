package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;





public class CRLBag
  extends ASN1Object
{
  private ASN1ObjectIdentifier crlId;
  private ASN1Encodable crlValue;
  
  private CRLBag(ASN1Sequence seq)
  {
    crlId = ((ASN1ObjectIdentifier)seq.getObjectAt(0));
    crlValue = ((ASN1TaggedObject)seq.getObjectAt(1)).getObject();
  }
  
  public static CRLBag getInstance(Object o)
  {
    if ((o instanceof CRLBag))
    {
      return (CRLBag)o;
    }
    if (o != null)
    {
      return new CRLBag(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  


  public CRLBag(ASN1ObjectIdentifier crlId, ASN1Encodable crlValue)
  {
    this.crlId = crlId;
    this.crlValue = crlValue;
  }
  
  public ASN1ObjectIdentifier getCrlId()
  {
    return crlId;
  }
  
  public ASN1Encodable getCrlValue()
  {
    return crlValue;
  }
  
















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(crlId);
    v.add(new DERTaggedObject(0, crlValue));
    
    return new DERSequence(v);
  }
}
