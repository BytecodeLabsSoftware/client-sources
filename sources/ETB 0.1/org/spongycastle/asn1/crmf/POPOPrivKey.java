package org.spongycastle.asn1.crmf;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.EnvelopedData;

public class POPOPrivKey
  extends ASN1Object
  implements ASN1Choice
{
  public static final int thisMessage = 0;
  public static final int subsequentMessage = 1;
  public static final int dhMAC = 2;
  public static final int agreeMAC = 3;
  public static final int encryptedKey = 4;
  private int tagNo;
  private ASN1Encodable obj;
  
  private POPOPrivKey(ASN1TaggedObject obj)
  {
    tagNo = obj.getTagNo();
    
    switch (tagNo)
    {
    case 0: 
      this.obj = DERBitString.getInstance(obj, false);
      break;
    case 1: 
      this.obj = SubsequentMessage.valueOf(ASN1Integer.getInstance(obj, false).getValue().intValue());
      break;
    case 2: 
      this.obj = DERBitString.getInstance(obj, false);
      break;
    case 3: 
      this.obj = PKMACValue.getInstance(obj, false);
      break;
    case 4: 
      this.obj = EnvelopedData.getInstance(obj, false);
      break;
    default: 
      throw new IllegalArgumentException("unknown tag in POPOPrivKey");
    }
  }
  
  public static POPOPrivKey getInstance(Object obj)
  {
    if ((obj instanceof POPOPrivKey))
    {
      return (POPOPrivKey)obj;
    }
    if (obj != null)
    {
      return new POPOPrivKey(ASN1TaggedObject.getInstance(obj));
    }
    
    return null;
  }
  
  public static POPOPrivKey getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1TaggedObject.getInstance(obj, explicit));
  }
  
  public POPOPrivKey(SubsequentMessage msg)
  {
    tagNo = 1;
    obj = msg;
  }
  
  public int getType()
  {
    return tagNo;
  }
  
  public ASN1Encodable getValue()
  {
    return obj;
  }
  













  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, tagNo, obj);
  }
}
