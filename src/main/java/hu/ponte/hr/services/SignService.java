package hu.ponte.hr.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import javax.imageio.ImageIO;
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

    public byte[] signImage(byte[] bytes) {
        try {
            byte[] signedImage = sign(bytes);
            return encodeImage(signedImage);
        } catch (Exception e) {
            throw new RuntimeException("Could not sign image", e);
        }
    }

    private byte[] sign(byte[] encodedImage) throws SignatureException {
        signature.update(encodedImage);
        return signature.sign();
    }

    public byte[] encodeImage(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public byte[] decodeImage(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }
}
