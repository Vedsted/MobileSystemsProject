package yanf.app;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadBlackList {

    public static MozillaBlackList loadBlackList(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.disconnect);
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();

        String s;
        try {
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return parseResponse(stringBuffer.toString());
    }

    private static MozillaBlackList parseResponse(String response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(MozillaBlackList.class, new BlackListDeserializer()).create();
        return gson.fromJson(response, MozillaBlackList.class);
    }
}
