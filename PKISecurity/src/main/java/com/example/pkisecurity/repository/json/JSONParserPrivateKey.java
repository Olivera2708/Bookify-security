package com.example.pkisecurity.repository.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class JSONParserPrivateKey {
    private static JSONArray keystoreConfigs;

    private static void setupConfig(String filename){
        String jsonText = null;
        try {
            jsonText = new String(Files.readAllBytes(Paths.get("src/main/resources/static/"+filename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        keystoreConfigs = new JSONArray(jsonText);
    }

    public static PrivateKey getPrivateKey(String serialNumber) {
        setupConfig("private-keys.json");
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject keyConfig = keystoreConfigs.getJSONObject(i);
            if (keyConfig.getString("serial-number").equals(serialNumber)) {
                String privateKeyPEM = keyConfig.getString("private-key");
                return decodePrivateKey(privateKeyPEM);
            }
        }
        return null;
    }

    public static PrivateKey decodePrivateKey(String keyPEM) {
        String privateKeyPEM = keyPEM.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");  // Remove all whitespace including newlines

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveSubjectPrivateKey(String serialNumber, PrivateKey privateKeyPEM) {
        setupConfig("private-keys.json");

        JSONObject newKey = new JSONObject();
        newKey.put("serial-number", serialNumber);

        byte[] encoded = privateKeyPEM.getEncoded();
        String base64Encoded = Base64.getEncoder().encodeToString(encoded);
        newKey.put("private-key", base64Encoded);

        keystoreConfigs.put(newKey);

        try {
            Files.writeString(Paths.get("src/main/resources/static/private-keys.json"), keystoreConfigs.toString(4));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save updated keys to file", e);
        }
    }
}
