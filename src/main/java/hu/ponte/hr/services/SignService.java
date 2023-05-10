package hu.ponte.hr.services;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class SignService {

    static Signature signature;

    /**
     * Parsing the private key and loading it with the given algorithm into the signature variable.
     */
    static {
        try {
            byte[] key = Files.readAllBytes(Paths.get("src/main/resources/config/keys/key.private"));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            PrivateKey finalKey = keyFactory.generatePrivate(keySpec);
            signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(finalKey);
        } catch (IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Signs the image and encodes it with Base64
     * @param bytes of the image
     * @return the signed&encoded byte array.
     */
    public byte[] signAndEncodeImage(byte[] bytes) {
        try {
            byte[] signedImage = sign(bytes);
            return encodeImage(signedImage);
        } catch (Exception e) {
            throw new RuntimeException("Could not sign image", e);
        }
    }

    public byte[] decodeImage(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    /**
     * @param bytes of image
     * @return signed byte array
     * @throws SignatureException if there's an issue with the signature.
     */
    private byte[] sign(byte[] bytes) throws SignatureException {
        signature.update(bytes);
        return signature.sign();
    }

    private byte[] encodeImage(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }
}
