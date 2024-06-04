package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

























public class SCVPReqRes
  extends ASN1Object
{
  private final ContentInfo request;
  private final ContentInfo response;
  
  public static SCVPReqRes getInstance(Object obj)
  {
    if ((obj instanceof SCVPReqRes))
    {
      return (SCVPReqRes)obj;
    }
    if (obj != null)
    {
      return new SCVPReqRes(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private SCVPReqRes(ASN1Sequence seq)
  {
    if ((seq.getObjectAt(0) instanceof ASN1TaggedObject))
    {
      request = ContentInfo.getInstance(ASN1TaggedObject.getInstance(seq.getObjectAt(0)), true);
      response = ContentInfo.getInstance(seq.getObjectAt(1));
    }
    else
    {
      request = null;
      response = ContentInfo.getInstance(seq.getObjectAt(0));
    }
  }
  
  public SCVPReqRes(ContentInfo response)
  {
    request = null;
    this.response = response;
  }
  
  public SCVPReqRes(ContentInfo request, ContentInfo response)
  {
    this.request = request;
    this.response = response;
  }
  
  public ContentInfo getRequest()
  {
    return request;
  }
  
  public ContentInfo getResponse()
  {
    return response;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (request != null)
    {
      v.add(new DERTaggedObject(true, 0, request));
    }
    
    v.add(response);
    
    return new DERSequence(v);
  }
}
