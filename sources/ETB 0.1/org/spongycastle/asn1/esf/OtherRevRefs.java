package org.spongycastle.asn1.esf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;














public class OtherRevRefs
  extends ASN1Object
{
  private ASN1ObjectIdentifier otherRevRefType;
  private ASN1Encodable otherRevRefs;
  
  public static OtherRevRefs getInstance(Object obj)
  {
    if ((obj instanceof OtherRevRefs))
    {
      return (OtherRevRefs)obj;
    }
    if (obj != null)
    {
      return new OtherRevRefs(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  private OtherRevRefs(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    otherRevRefType = new ASN1ObjectIdentifier(((ASN1ObjectIdentifier)seq.getObjectAt(0)).getId());
    try
    {
      otherRevRefs = ASN1Primitive.fromByteArray(seq.getObjectAt(1)
        .toASN1Primitive().getEncoded("DER"));
    }
    catch (IOException e)
    {
      throw new IllegalStateException();
    }
  }
  
  public OtherRevRefs(ASN1ObjectIdentifier otherRevRefType, ASN1Encodable otherRevRefs)
  {
    this.otherRevRefType = otherRevRefType;
    this.otherRevRefs = otherRevRefs;
  }
  
  public ASN1ObjectIdentifier getOtherRevRefType()
  {
    return otherRevRefType;
  }
  
  public ASN1Encodable getOtherRevRefs()
  {
    return otherRevRefs;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(otherRevRefType);
    v.add(otherRevRefs);
    return new DERSequence(v);
  }
}
