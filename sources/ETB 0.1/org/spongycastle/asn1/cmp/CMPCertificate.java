package org.spongycastle.asn1.cmp;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x509.Certificate;





public class CMPCertificate
  extends ASN1Object
  implements ASN1Choice
{
  private Certificate x509v3PKCert;
  private int otherTagValue;
  private ASN1Object otherCert;
  
  /**
   * @deprecated
   */
  public CMPCertificate(AttributeCertificate x509v2AttrCert)
  {
    this(1, x509v2AttrCert);
  }
  







  public CMPCertificate(int type, ASN1Object otherCert)
  {
    otherTagValue = type;
    this.otherCert = otherCert;
  }
  
  public CMPCertificate(Certificate x509v3PKCert)
  {
    if (x509v3PKCert.getVersionNumber() != 3)
    {
      throw new IllegalArgumentException("only version 3 certificates allowed");
    }
    
    this.x509v3PKCert = x509v3PKCert;
  }
  
  public static CMPCertificate getInstance(Object o)
  {
    if ((o == null) || ((o instanceof CMPCertificate)))
    {
      return (CMPCertificate)o;
    }
    
    if ((o instanceof byte[]))
    {
      try
      {
        o = ASN1Primitive.fromByteArray((byte[])o);
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("Invalid encoding in CMPCertificate");
      }
    }
    
    if ((o instanceof ASN1Sequence))
    {
      return new CMPCertificate(Certificate.getInstance(o));
    }
    
    if ((o instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject taggedObject = (ASN1TaggedObject)o;
      
      return new CMPCertificate(taggedObject.getTagNo(), taggedObject.getObject());
    }
    
    throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
  }
  
  public boolean isX509v3PKCert()
  {
    return x509v3PKCert != null;
  }
  
  public Certificate getX509v3PKCert()
  {
    return x509v3PKCert;
  }
  



  /**
   * @deprecated
   */
  public AttributeCertificate getX509v2AttrCert()
  {
    return AttributeCertificate.getInstance(otherCert);
  }
  
  public int getOtherCertTag()
  {
    return otherTagValue;
  }
  
  public ASN1Object getOtherCert()
  {
    return otherCert;
  }
  











  public ASN1Primitive toASN1Primitive()
  {
    if (otherCert != null)
    {
      return new DERTaggedObject(true, otherTagValue, otherCert);
    }
    
    return x509v3PKCert.toASN1Primitive();
  }
}
