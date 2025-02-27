/*
 * Copyright Mazaffaka Project (c) 2012.
 */

package l2p.commons.crypt;

import l2p.commons.util.Base64;
import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

public class CryptUtil
{
    private final static Logger _log = Logger.getLogger(CryptUtil.class);

    private static Cipher _encCipher;
    private static Cipher _decCipher;

    private final static String _pass = "ALNF__etJI34(*&#@$234JjJ&R(#*&?45?[:F{EWKFW&5HKJ343HDFP345MVC73885445VNSKJ";
    private static SecretKey _key = null;
    private final static byte _salt[] = new byte[]{(byte)0x8C, (byte)0x1E, (byte)0xA1, (byte)0x9B, (byte)0x02, (byte)0x70, (byte)0x02, (byte)0x5D };

    private static boolean _initiated = false;

    private static void init()
    {
        if (_initiated)
            return;

        try
        {
            KeySpec keySpec = new PBEKeySpec(_pass.toCharArray(), _salt, 19);
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(_salt, 19);
            _key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            _encCipher = Cipher.getInstance(_key.getAlgorithm());
            _decCipher = Cipher.getInstance(_key.getAlgorithm());

            _encCipher.init(Cipher.ENCRYPT_MODE, _key, paramSpec);
            _decCipher.init(Cipher.DECRYPT_MODE, _key, paramSpec);
        }
        catch (Exception e)
        {
            _log.error("Cannot init crypto engine.", e);
        }

        _initiated = true;
    }

    public static String encrypt(String data)
    {
        init();
        try
        {
            return Base64.encodeBytes(_encCipher.doFinal(data.getBytes("UTF8")));
        }
        catch (Exception e)
        {
            _log.error("Cannot encrypt data.", e);
        }
        return null;
    }

    public static String decrypt(String data)
    {
        init();
        try
        {
            String decoded = new String(_decCipher.doFinal(Base64.decode(data)), "UTF8");

            return decoded;
        }
        catch (Exception e)
        {
            _log.error("Cannot decrypt data.", e);
        }

        return null;
    }

    public static void encrypt(InputStream in, OutputStream out)
    {
        init();
        out = new CipherOutputStream(out, _encCipher);

        try
        {
            int num;
            byte[] buffer = new byte[1024];
            while ((num = in.read(buffer)) >= 0)
                out.write(buffer, 0, num);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            _log.error("Cannot write encrypted file.", e);
        }
    }

    public static InputStream decrypt(InputStream input, InputStream readable)
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        decrypt(input, output);
        return new ByteArrayInputStream(output.toByteArray());
    }

    /**
     * Decrypts file.
     * @param file Input file to decrypt.
     * @return Input stream with decrypted data
     * @throws IOException
     */
    public static InputStream decryptOnDemand(File file) throws IOException
    {
        InputStream input = new FileInputStream(file);
        InputStream output;
        if ((byte)input.read() == 0x00)
        {
            byte[] bytes = new byte[0];
            output = new ByteArrayInputStream(bytes);
            output = decrypt(input, output);
            output.reset();
        }
        else
            output = new FileInputStream(file);

        return output;
    }

    /**
     * Makes decrypting of file if it is needed.
     * @param input Input stream to decrypt.
     * @return Input stream with decrypted data.
     */
    public static InputStream decryptOnDemand(InputStream input) throws IOException
    {
        InputStream output;
        if ((byte)input.read() == 0x00)
        {
            byte[] bytes = new byte[0];
            output = new ByteArrayInputStream(bytes);
            output = decrypt(input, output);
        }
        else
            output = input;

        output.reset();
        return output;
    }

    public static void decrypt(InputStream in, OutputStream out)
    {
        init();
        in = new CipherInputStream(in, _decCipher);

        try
        {
            InputStreamReader reader = new InputStreamReader(in, "UTF8");

            int num = 0;
            byte[] buffer = new byte[1024];
            while ((num = in.read(buffer)) >= 0)
                out.write(buffer, 0, num);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            _log.error("Cannot decrypt file.", e);
        }
    }

    public static String encrypt(InputStream stream) throws IOException
    {
        init();
        StringBuilder buffer = new StringBuilder();
        int chr;
        while ((chr = stream.read()) >= 0)
            buffer.append(String.valueOf(chr));

        return encrypt(buffer.toString());
    }

    public static String decrypt(InputStream stream) throws IOException
    {
        init();
        StringBuilder buffer = new StringBuilder();
        int chr;
        while ((chr = stream.read()) >= 0)
            buffer.append(Character.toChars(chr));

        return decrypt(buffer.toString());
    }

    public static int getKeyHash()
    {
        init();
        return _key.hashCode();
    }
}
