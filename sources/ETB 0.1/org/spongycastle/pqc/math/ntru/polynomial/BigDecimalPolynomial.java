package org.spongycastle.pqc.math.ntru.polynomial;

import java.math.BigDecimal;






public class BigDecimalPolynomial
{
  private static final BigDecimal ZERO = new BigDecimal("0");
  private static final BigDecimal ONE_HALF = new BigDecimal("0.5");
  


  BigDecimal[] coeffs;
  



  BigDecimalPolynomial(int N)
  {
    coeffs = new BigDecimal[N];
    for (int i = 0; i < N; i++)
    {
      coeffs[i] = ZERO;
    }
  }
  





  BigDecimalPolynomial(BigDecimal[] coeffs)
  {
    this.coeffs = coeffs;
  }
  





  public BigDecimalPolynomial(BigIntPolynomial p)
  {
    int N = coeffs.length;
    coeffs = new BigDecimal[N];
    for (int i = 0; i < N; i++)
    {
      coeffs[i] = new BigDecimal(coeffs[i]);
    }
  }
  



  public void halve()
  {
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = coeffs[i].multiply(ONE_HALF);
    }
  }
  







  public BigDecimalPolynomial mult(BigIntPolynomial poly2)
  {
    return mult(new BigDecimalPolynomial(poly2));
  }
  







  public BigDecimalPolynomial mult(BigDecimalPolynomial poly2)
  {
    int N = coeffs.length;
    if (coeffs.length != N)
    {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    
    BigDecimalPolynomial c = multRecursive(poly2);
    
    if (coeffs.length > N)
    {
      for (int k = N; k < coeffs.length; k++)
      {
        coeffs[(k - N)] = coeffs[(k - N)].add(coeffs[k]);
      }
      coeffs = copyOf(coeffs, N);
    }
    return c;
  }
  



  private BigDecimalPolynomial multRecursive(BigDecimalPolynomial poly2)
  {
    BigDecimal[] a = coeffs;
    BigDecimal[] b = coeffs;
    
    int n = coeffs.length;
    if (n <= 1)
    {
      BigDecimal[] c = (BigDecimal[])coeffs.clone();
      for (int i = 0; i < coeffs.length; i++)
      {
        c[i] = c[i].multiply(coeffs[0]);
      }
      return new BigDecimalPolynomial(c);
    }
    

    int n1 = n / 2;
    
    BigDecimalPolynomial a1 = new BigDecimalPolynomial(copyOf(a, n1));
    BigDecimalPolynomial a2 = new BigDecimalPolynomial(copyOfRange(a, n1, n));
    BigDecimalPolynomial b1 = new BigDecimalPolynomial(copyOf(b, n1));
    BigDecimalPolynomial b2 = new BigDecimalPolynomial(copyOfRange(b, n1, n));
    
    BigDecimalPolynomial A = (BigDecimalPolynomial)a1.clone();
    A.add(a2);
    BigDecimalPolynomial B = (BigDecimalPolynomial)b1.clone();
    B.add(b2);
    
    BigDecimalPolynomial c1 = a1.multRecursive(b1);
    BigDecimalPolynomial c2 = a2.multRecursive(b2);
    BigDecimalPolynomial c3 = A.multRecursive(B);
    c3.sub(c1);
    c3.sub(c2);
    
    BigDecimalPolynomial c = new BigDecimalPolynomial(2 * n - 1);
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = coeffs[i];
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[(n1 + i)] = coeffs[(n1 + i)].add(coeffs[i]);
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[(2 * n1 + i)] = coeffs[(2 * n1 + i)].add(coeffs[i]);
    }
    return c;
  }
  






  public void add(BigDecimalPolynomial b)
  {
    if (coeffs.length > coeffs.length)
    {
      int N = coeffs.length;
      coeffs = copyOf(coeffs, coeffs.length);
      for (int i = N; i < coeffs.length; i++)
      {
        coeffs[i] = ZERO;
      }
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = coeffs[i].add(coeffs[i]);
    }
  }
  





  void sub(BigDecimalPolynomial b)
  {
    if (coeffs.length > coeffs.length)
    {
      int N = coeffs.length;
      coeffs = copyOf(coeffs, coeffs.length);
      for (int i = N; i < coeffs.length; i++)
      {
        coeffs[i] = ZERO;
      }
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = coeffs[i].subtract(coeffs[i]);
    }
  }
  





  public BigIntPolynomial round()
  {
    int N = coeffs.length;
    BigIntPolynomial p = new BigIntPolynomial(N);
    for (int i = 0; i < N; i++)
    {
      coeffs[i] = coeffs[i].setScale(0, 6).toBigInteger();
    }
    return p;
  }
  



  public Object clone()
  {
    return new BigDecimalPolynomial((BigDecimal[])coeffs.clone());
  }
  
  private BigDecimal[] copyOf(BigDecimal[] a, int length)
  {
    BigDecimal[] tmp = new BigDecimal[length];
    
    System.arraycopy(a, 0, tmp, 0, a.length < length ? a.length : length);
    
    return tmp;
  }
  
  private BigDecimal[] copyOfRange(BigDecimal[] a, int from, int to)
  {
    int newLength = to - from;
    BigDecimal[] tmp = new BigDecimal[to - from];
    
    System.arraycopy(a, from, tmp, 0, a.length - from < newLength ? a.length - from : newLength);
    
    return tmp;
  }
  
  public BigDecimal[] getCoeffs()
  {
    BigDecimal[] tmp = new BigDecimal[coeffs.length];
    
    System.arraycopy(coeffs, 0, tmp, 0, coeffs.length);
    
    return tmp;
  }
}
