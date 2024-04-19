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

public class JSONParser {
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

    public static String getKSFile(String alias) {
        setupConfig("key-store-password.json");
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject ksConfig = keystoreConfigs.getJSONObject(i);
            JSONArray aliases = ksConfig.getJSONArray("aliases");
            for (int j = 0; j < aliases.length(); j++) {
                if (aliases.getString(j).equals(alias)) {
                    return ksConfig.getString("file-name");
                }
            }
        }
        return null;
    }

    public static String getKSPass(String alias) {
        setupConfig("key-store-password.json");
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject ksConfig = keystoreConfigs.getJSONObject(i);
            JSONArray aliases = ksConfig.getJSONArray("aliases");
            for (int j = 0; j < aliases.length(); j++) {
                if (aliases.getString(j).equals(alias)) {
                    return ksConfig.getString("password");
                }
            }
        }
        return null;
    }

    public static PrivateKey getPrivateKey(String email) {
        setupConfig("private-keys.json");
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject keyConfig = keystoreConfigs.getJSONObject(i);
            if (keyConfig.getString("email").equals(email)) {
                String privateKeyPEM = keyConfig.getString("private-key");
                return decodePrivateKey(privateKeyPEM);
            }
        }
        return null;
    }

    private static PrivateKey decodePrivateKey(String key) {
        try {
            key = key.replace("\n", "").replace("\r", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
            byte[] decodedKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
}
