package org.spongycastle.pqc.jcajce.provider.mceliece;

import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.spongycastle.pqc.math.linearalgebra.Vector;




















public final class McElieceCCA2Primitives
{
  private McElieceCCA2Primitives() {}
  
  public static GF2Vector encryptionPrimitive(BCMcElieceCCA2PublicKey pubKey, GF2Vector m, GF2Vector z)
  {
    GF2Matrix matrixG = pubKey.getG();
    Vector mG = matrixG.leftMultiplyLeftCompactForm(m);
    return (GF2Vector)mG.add(z);
  }
  


  public static GF2Vector encryptionPrimitive(McElieceCCA2PublicKeyParameters pubKey, GF2Vector m, GF2Vector z)
  {
    GF2Matrix matrixG = pubKey.getG();
    Vector mG = matrixG.leftMultiplyLeftCompactForm(m);
    return (GF2Vector)mG.add(z);
  }
  










  public static GF2Vector[] decryptionPrimitive(BCMcElieceCCA2PrivateKey privKey, GF2Vector c)
  {
    int k = privKey.getK();
    Permutation p = privKey.getP();
    GF2mField field = privKey.getField();
    PolynomialGF2mSmallM gp = privKey.getGoppaPoly();
    GF2Matrix h = privKey.getH();
    PolynomialGF2mSmallM[] q = privKey.getQInv();
    

    Permutation pInv = p.computeInverse();
    

    GF2Vector cPInv = (GF2Vector)c.multiply(pInv);
    

    GF2Vector syndVec = (GF2Vector)h.rightMultiply(cPInv);
    

    GF2Vector errors = GoppaCode.syndromeDecode(syndVec, field, gp, q);
    GF2Vector mG = (GF2Vector)cPInv.add(errors);
    

    mG = (GF2Vector)mG.multiply(p);
    errors = (GF2Vector)errors.multiply(p);
    

    GF2Vector m = mG.extractRightVector(k);
    

    return new GF2Vector[] { m, errors };
  }
  



  public static GF2Vector[] decryptionPrimitive(McElieceCCA2PrivateKeyParameters privKey, GF2Vector c)
  {
    int k = privKey.getK();
    Permutation p = privKey.getP();
    GF2mField field = privKey.getField();
    PolynomialGF2mSmallM gp = privKey.getGoppaPoly();
    GF2Matrix h = privKey.getH();
    PolynomialGF2mSmallM[] q = privKey.getQInv();
    

    Permutation pInv = p.computeInverse();
    

    GF2Vector cPInv = (GF2Vector)c.multiply(pInv);
    

    GF2Vector syndVec = (GF2Vector)h.rightMultiply(cPInv);
    

    GF2Vector errors = GoppaCode.syndromeDecode(syndVec, field, gp, q);
    GF2Vector mG = (GF2Vector)cPInv.add(errors);
    

    mG = (GF2Vector)mG.multiply(p);
    errors = (GF2Vector)errors.multiply(p);
    

    GF2Vector m = mG.extractRightVector(k);
    

    return new GF2Vector[] { m, errors };
  }
}
