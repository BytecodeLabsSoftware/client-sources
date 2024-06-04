package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;


















public class BERSequence
  extends ASN1Sequence
{
  public BERSequence() {}
  
  public BERSequence(ASN1Encodable obj)
  {
    super(obj);
  }
  




  public BERSequence(ASN1EncodableVector v)
  {
    super(v);
  }
  




  public BERSequence(ASN1Encodable[] array)
  {
    super(array);
  }
  
  int encodedLength()
    throws IOException
  {
    int length = 0;
    for (Enumeration e = getObjects(); e.hasMoreElements();)
    {
      length += ((ASN1Encodable)e.nextElement()).toASN1Primitive().encodedLength();
    }
    
    return 2 + length + 2;
  }
  

  void encode(ASN1OutputStream out)
    throws IOException
  {
    out.write(48);
    out.write(128);
    
    Enumeration e = getObjects();
    while (e.hasMoreElements())
    {
      out.writeObject((ASN1Encodable)e.nextElement());
    }
    
    out.write(0);
    out.write(0);
  }
}
