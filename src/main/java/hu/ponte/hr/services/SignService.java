package hu.ponte.hr.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SignService {

    public String signImage(byte[] bytes) {
        return Arrays.toString(bytes);
    }
}
