package org.spongycastle.asn1.x9;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
















public class OtherInfo
  extends ASN1Object
{
  private KeySpecificInfo keyInfo;
  private ASN1OctetString partyAInfo;
  private ASN1OctetString suppPubInfo;
  
  public OtherInfo(KeySpecificInfo keyInfo, ASN1OctetString partyAInfo, ASN1OctetString suppPubInfo)
  {
    this.keyInfo = keyInfo;
    this.partyAInfo = partyAInfo;
    this.suppPubInfo = suppPubInfo;
  }
  






  public static OtherInfo getInstance(Object obj)
  {
    if ((obj instanceof OtherInfo))
    {
      return (OtherInfo)obj;
    }
    if (obj != null)
    {
      return new OtherInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private OtherInfo(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    keyInfo = KeySpecificInfo.getInstance(e.nextElement());
    
    while (e.hasMoreElements())
    {
      ASN1TaggedObject o = (ASN1TaggedObject)e.nextElement();
      
      if (o.getTagNo() == 0)
      {
        partyAInfo = ((ASN1OctetString)o.getObject());
      }
      else if (o.getTagNo() == 2)
      {
        suppPubInfo = ((ASN1OctetString)o.getObject());
      }
    }
  }
  





  public KeySpecificInfo getKeyInfo()
  {
    return keyInfo;
  }
  





  public ASN1OctetString getPartyAInfo()
  {
    return partyAInfo;
  }
  





  public ASN1OctetString getSuppPubInfo()
  {
    return suppPubInfo;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(keyInfo);
    
    if (partyAInfo != null)
    {
      v.add(new DERTaggedObject(0, partyAInfo));
    }
    
    v.add(new DERTaggedObject(2, suppPubInfo));
    
    return new DERSequence(v);
  }
}
