package hu.ponte.hr.services;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
public class SignService {

    public String signImage(byte[] bytes) {
        try {
            String encodeImage = encodeImage(bytes);
            return sign(encodeImage);
        } catch (Exception e) {
            throw new RuntimeException("Could not sign image", e);
        }
    }

    private String sign(String encodedImage) {
        try {
            byte[] key = Files.readAllBytes(Paths.get("src/main/resources/config/keys/key.private"));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            PrivateKey finalKey = keyFactory.generatePrivate(keySpec);
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(finalKey);
            byte[] bytes = encodedImage.getBytes(StandardCharsets.UTF_8);
            sig.update(bytes);
            return Arrays.toString(bytes);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String encodeImage(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] decodeImage(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }
}
