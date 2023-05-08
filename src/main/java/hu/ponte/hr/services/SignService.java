package hu.ponte.hr.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;

@Service
public class SignService {

    public String signImage(byte[] bytes) {
        try {
            return encodeImage(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Could not sign image", e);
        }
    }

    public String encodeImage(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] decodeImage(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }
}
