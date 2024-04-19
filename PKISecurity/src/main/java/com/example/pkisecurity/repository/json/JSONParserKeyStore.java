package com.example.pkisecurity.repository.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;

public class JSONParserKeyStore {

    private static JSONArray keystoreConfigs;

    private static final String path = "src/main/resources/static/key-store-password.json";

    private static void loadFile() {
        String jsonText = null;
        try {
            jsonText = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        keystoreConfigs = new JSONArray(jsonText);
    }

    public static String getKSFile(String alias) {
        loadFile();
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
        loadFile();
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

    public static String getKSPassByFileName(String fileName) {
        loadFile();
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject ksConfig = keystoreConfigs.getJSONObject(i);
            if (ksConfig.getString("file-name").equals(fileName)) {
                return ksConfig.getString("password");
            }
        }
        return null;
    }

    public static boolean doesFileExistInJSON(String keyStoreName) {
        loadFile();

        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject ksConfig = keystoreConfigs.getJSONObject(i);
            String fileName = ksConfig.getString("file-name");
            if (fileName.equals(keyStoreName)) {
                return true;
            }
        }
        return false;
    }

    public static void saveKeyStorePassword(String alias, String fileName, String password) {
        loadFile();

        if (doesFileExistInJSON(fileName)) {
            JSONObject existing = findKeyByFileName(fileName);
            JSONArray aliasesArray = existing.getJSONArray("aliases");
            aliasesArray.put(alias);
            existing.put("aliases", aliasesArray);
        } else {
            JSONObject newKey = new JSONObject();
            ArrayList<String> aliases = new ArrayList<>();
            aliases.add(alias);
            newKey.put("aliases", aliases);
            newKey.put("file-name", fileName);
            newKey.put("password", password);
            keystoreConfigs.put(newKey);
        }
        try {
            Files.writeString(Paths.get(path), keystoreConfigs.toString(4));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save updated keys to file", e);
        }
    }

    private static JSONObject findKeyByFileName(String fileName) {
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject key = keystoreConfigs.getJSONObject(i);
            if (fileName.equals(key.getString("file-name"))) {
                return key;
            }
        }
        return null;
    }
}
