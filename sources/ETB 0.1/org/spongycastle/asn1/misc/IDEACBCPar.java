package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;


public class IDEACBCPar
  extends ASN1Object
{
  ASN1OctetString iv;
  
  public static IDEACBCPar getInstance(Object o)
  {
    if ((o instanceof IDEACBCPar))
    {
      return (IDEACBCPar)o;
    }
    if (o != null)
    {
      return new IDEACBCPar(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  

  public IDEACBCPar(byte[] iv)
  {
    this.iv = new DEROctetString(iv);
  }
  

  public IDEACBCPar(ASN1Sequence seq)
  {
    if (seq.size() == 1)
    {
      iv = ((ASN1OctetString)seq.getObjectAt(0));
    }
    else
    {
      iv = null;
    }
  }
  
  public byte[] getIV()
  {
    if (iv != null)
    {
      return Arrays.clone(iv.getOctets());
    }
    

    return null;
  }
  









  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (iv != null)
    {
      v.add(iv);
    }
    
    return new DERSequence(v);
  }
}
