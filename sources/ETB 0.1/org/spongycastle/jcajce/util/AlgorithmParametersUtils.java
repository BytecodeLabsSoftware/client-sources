package org.spongycastle.jcajce.util;

import java.io.IOException;
import java.security.AlgorithmParameters;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Primitive;


















public class AlgorithmParametersUtils
{
  private AlgorithmParametersUtils() {}
  
  public static ASN1Encodable extractParameters(AlgorithmParameters params)
    throws IOException
  {
    ASN1Encodable asn1Params;
    try
    {
      asn1Params = ASN1Primitive.fromByteArray(params.getEncoded("ASN.1"));
    }
    catch (Exception ex) {
      ASN1Encodable asn1Params;
      asn1Params = ASN1Primitive.fromByteArray(params.getEncoded());
    }
    
    return asn1Params;
  }
  








  public static void loadParameters(AlgorithmParameters params, ASN1Encodable sParams)
    throws IOException
  {
    try
    {
      params.init(sParams.toASN1Primitive().getEncoded(), "ASN.1");
    }
    catch (Exception ex)
    {
      params.init(sParams.toASN1Primitive().getEncoded());
    }
  }
}
