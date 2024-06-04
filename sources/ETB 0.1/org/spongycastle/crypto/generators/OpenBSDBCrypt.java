package org.spongycastle.crypto.generators;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;








public class OpenBSDBCrypt
{
  private static final byte[] encodingTable = { 46, 47, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };
  















  private static final byte[] decodingTable = new byte[''];
  private static final String defaultVersion = "2y";
  private static final Set<String> allowedVersions = new HashSet();
  

  static
  {
    allowedVersions.add("2a");
    allowedVersions.add("2y");
    allowedVersions.add("2b");
    
    for (int i = 0; i < decodingTable.length; i++)
    {
      decodingTable[i] = -1;
    }
    
    for (int i = 0; i < encodingTable.length; i++)
    {
      decodingTable[encodingTable[i]] = ((byte)i);
    }
  }
  


















  private static String createBcryptString(String version, byte[] password, byte[] salt, int cost)
  {
    if (!allowedVersions.contains(version))
    {
      throw new IllegalArgumentException("Version " + version + " is not accepted by this implementation.");
    }
    
    StringBuffer sb = new StringBuffer(60);
    sb.append('$');
    sb.append(version);
    sb.append('$');
    sb.append(cost < 10 ? "0" + cost : Integer.toString(cost));
    sb.append('$');
    sb.append(encodeData(salt));
    
    byte[] key = BCrypt.generate(password, salt, cost);
    
    sb.append(encodeData(key));
    
    return sb.toString();
  }
  













  public static String generate(char[] password, byte[] salt, int cost)
  {
    return generate("2y", password, salt, cost);
  }
  















  public static String generate(String version, char[] password, byte[] salt, int cost)
  {
    if (!allowedVersions.contains(version))
    {
      throw new IllegalArgumentException("Version " + version + " is not accepted by this implementation.");
    }
    
    if (password == null)
    {
      throw new IllegalArgumentException("Password required.");
    }
    if (salt == null)
    {
      throw new IllegalArgumentException("Salt required.");
    }
    if (salt.length != 16)
    {
      throw new DataLengthException("16 byte salt required: " + salt.length);
    }
    if ((cost < 4) || (cost > 31))
    {
      throw new IllegalArgumentException("Invalid cost factor.");
    }
    
    byte[] psw = Strings.toUTF8ByteArray(password);
    


    byte[] tmp = new byte[psw.length >= 72 ? 72 : psw.length + 1];
    
    if (tmp.length > psw.length)
    {
      System.arraycopy(psw, 0, tmp, 0, psw.length);
    }
    else
    {
      System.arraycopy(psw, 0, tmp, 0, tmp.length);
    }
    
    Arrays.fill(psw, (byte)0);
    
    String rv = createBcryptString(version, tmp, salt, cost);
    
    Arrays.fill(tmp, (byte)0);
    
    return rv;
  }
  













  public static boolean checkPassword(String bcryptString, char[] password)
  {
    if (bcryptString.length() != 60)
    {

      throw new DataLengthException("Bcrypt String length: " + bcryptString.length() + ", 60 required.");
    }
    
    if ((bcryptString.charAt(0) != '$') || 
      (bcryptString.charAt(3) != '$') || 
      (bcryptString.charAt(6) != '$'))
    {
      throw new IllegalArgumentException("Invalid Bcrypt String format.");
    }
    
    String version = bcryptString.substring(1, 3);
    
    if (!allowedVersions.contains(version))
    {
      throw new IllegalArgumentException("Bcrypt version '" + bcryptString.substring(1, 3) + "' is not supported by this implementation");
    }
    
    int cost = 0;
    try
    {
      cost = Integer.parseInt(bcryptString.substring(4, 6));

    }
    catch (NumberFormatException nfe)
    {
      throw new IllegalArgumentException("Invalid cost factor: " + bcryptString.substring(4, 6));
    }
    if ((cost < 4) || (cost > 31))
    {
      throw new IllegalArgumentException("Invalid cost factor: " + cost + ", 4 < cost < 31 expected.");
    }
    

    if (password == null)
    {
      throw new IllegalArgumentException("Missing password.");
    }
    byte[] salt = decodeSaltString(bcryptString
      .substring(bcryptString.lastIndexOf('$') + 1, bcryptString
      .length() - 31));
    
    String newBcryptString = generate(version, password, salt, cost);
    
    return bcryptString.equals(newBcryptString);
  }
  








  private static String encodeData(byte[] data)
  {
    if ((data.length != 24) && (data.length != 16))
    {
      throw new DataLengthException("Invalid length: " + data.length + ", 24 for key or 16 for salt expected");
    }
    boolean salt = false;
    if (data.length == 16)
    {
      salt = true;
      byte[] tmp = new byte[18];
      System.arraycopy(data, 0, tmp, 0, data.length);
      data = tmp;
    }
    else
    {
      data[(data.length - 1)] = 0;
    }
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int len = data.length;
    


    for (int i = 0; i < len; i += 3)
    {
      int a1 = data[i] & 0xFF;
      int a2 = data[(i + 1)] & 0xFF;
      int a3 = data[(i + 2)] & 0xFF;
      
      out.write(encodingTable[(a1 >>> 2 & 0x3F)]);
      out.write(encodingTable[((a1 << 4 | a2 >>> 4) & 0x3F)]);
      out.write(encodingTable[((a2 << 2 | a3 >>> 6) & 0x3F)]);
      out.write(encodingTable[(a3 & 0x3F)]);
    }
    
    String result = Strings.fromByteArray(out.toByteArray());
    if (salt == true)
    {
      return result.substring(0, 22);
    }
    

    return result.substring(0, result.length() - 1);
  }
  













  private static byte[] decodeSaltString(String saltString)
  {
    char[] saltChars = saltString.toCharArray();
    
    ByteArrayOutputStream out = new ByteArrayOutputStream(16);
    

    if (saltChars.length != 22)
    {
      throw new DataLengthException("Invalid base64 salt length: " + saltChars.length + " , 22 required.");
    }
    

    for (int i = 0; i < saltChars.length; i++)
    {
      int value = saltChars[i];
      if ((value > 122) || (value < 46) || ((value > 57) && (value < 65)))
      {
        throw new IllegalArgumentException("Salt string contains invalid character: " + value);
      }
    }
    

    char[] tmp = new char[24];
    System.arraycopy(saltChars, 0, tmp, 0, saltChars.length);
    saltChars = tmp;
    
    int len = saltChars.length;
    
    for (int i = 0; i < len; i += 4)
    {
      byte b1 = decodingTable[saltChars[i]];
      byte b2 = decodingTable[saltChars[(i + 1)]];
      byte b3 = decodingTable[saltChars[(i + 2)]];
      byte b4 = decodingTable[saltChars[(i + 3)]];
      
      out.write(b1 << 2 | b2 >> 4);
      out.write(b2 << 4 | b3 >> 2);
      out.write(b3 << 6 | b4);
    }
    
    byte[] saltBytes = out.toByteArray();
    

    byte[] tmpSalt = new byte[16];
    System.arraycopy(saltBytes, 0, tmpSalt, 0, tmpSalt.length);
    saltBytes = tmpSalt;
    
    return saltBytes;
  }
  
  public OpenBSDBCrypt() {}
}
