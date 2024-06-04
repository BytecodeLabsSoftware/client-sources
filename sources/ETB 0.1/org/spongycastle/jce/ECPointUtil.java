package org.spongycastle.jce;

import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECCurve.Fp;
import org.spongycastle.math.ec.ECFieldElement;













public class ECPointUtil
{
  public ECPointUtil() {}
  
  public static java.security.spec.ECPoint decodePoint(EllipticCurve curve, byte[] encoded)
  {
    ECCurve c = null;
    
    if ((curve.getField() instanceof ECFieldFp))
    {

      c = new ECCurve.Fp(((ECFieldFp)curve.getField()).getP(), curve.getA(), curve.getB());
    }
    else
    {
      int[] k = ((ECFieldF2m)curve.getField()).getMidTermsOfReductionPolynomial();
      
      if (k.length == 3)
      {

        c = new ECCurve.F2m(((ECFieldF2m)curve.getField()).getM(), k[2], k[1], k[0], curve.getA(), curve.getB());

      }
      else
      {
        c = new ECCurve.F2m(((ECFieldF2m)curve.getField()).getM(), k[0], curve.getA(), curve.getB());
      }
    }
    
    org.spongycastle.math.ec.ECPoint p = c.decodePoint(encoded);
    
    return new java.security.spec.ECPoint(p.getAffineXCoord().toBigInteger(), p.getAffineYCoord().toBigInteger());
  }
}
