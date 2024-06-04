package org.spongycastle.jcajce.provider.config;

import java.io.OutputStream;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;

/**
 * @deprecated
 */
public class PKCS12StoreParameter
  extends org.spongycastle.jcajce.PKCS12StoreParameter
{
  public PKCS12StoreParameter(OutputStream out, char[] password)
  {
    super(out, password, false);
  }
  
  public PKCS12StoreParameter(OutputStream out, KeyStore.ProtectionParameter protectionParameter)
  {
    super(out, protectionParameter, false);
  }
  
  public PKCS12StoreParameter(OutputStream out, char[] password, boolean forDEREncoding)
  {
    super(out, new KeyStore.PasswordProtection(password), forDEREncoding);
  }
  
  public PKCS12StoreParameter(OutputStream out, KeyStore.ProtectionParameter protectionParameter, boolean forDEREncoding)
  {
    super(out, protectionParameter, forDEREncoding);
  }
}
