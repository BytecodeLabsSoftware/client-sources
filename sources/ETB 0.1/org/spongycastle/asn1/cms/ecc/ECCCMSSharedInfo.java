package org.spongycastle.asn1.cms.ecc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;













public class ECCCMSSharedInfo
  extends ASN1Object
{
  private final AlgorithmIdentifier keyInfo;
  private final byte[] entityUInfo;
  private final byte[] suppPubInfo;
  
  public ECCCMSSharedInfo(AlgorithmIdentifier keyInfo, byte[] entityUInfo, byte[] suppPubInfo)
  {
    this.keyInfo = keyInfo;
    this.entityUInfo = Arrays.clone(entityUInfo);
    this.suppPubInfo = Arrays.clone(suppPubInfo);
  }
  


  public ECCCMSSharedInfo(AlgorithmIdentifier keyInfo, byte[] suppPubInfo)
  {
    this.keyInfo = keyInfo;
    entityUInfo = null;
    this.suppPubInfo = Arrays.clone(suppPubInfo);
  }
  

  private ECCCMSSharedInfo(ASN1Sequence seq)
  {
    keyInfo = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    
    if (seq.size() == 2)
    {
      entityUInfo = null;
      suppPubInfo = ASN1OctetString.getInstance((ASN1TaggedObject)seq.getObjectAt(1), true).getOctets();
    }
    else
    {
      entityUInfo = ASN1OctetString.getInstance((ASN1TaggedObject)seq.getObjectAt(1), true).getOctets();
      suppPubInfo = ASN1OctetString.getInstance((ASN1TaggedObject)seq.getObjectAt(2), true).getOctets();
    }
  }
  











  public static ECCCMSSharedInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static ECCCMSSharedInfo getInstance(Object obj)
  {
    if ((obj instanceof ECCCMSSharedInfo))
    {
      return (ECCCMSSharedInfo)obj;
    }
    if (obj != null)
    {
      return new ECCCMSSharedInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  




  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(keyInfo);
    
    if (entityUInfo != null)
    {
      v.add(new DERTaggedObject(true, 0, new DEROctetString(entityUInfo)));
    }
    
    v.add(new DERTaggedObject(true, 2, new DEROctetString(suppPubInfo)));
    
    return new DERSequence(v);
  }
}
