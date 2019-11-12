package yanf.app;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class BlackListDeserializer implements JsonDeserializer<MozillaBlackList> {

    @Override
    public MozillaBlackList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ArrayList<Company> advertising = getBlackList(json, "Advertising");
        ArrayList<Company> content = getBlackList(json, "Content");
        ArrayList<Company> analytics = new ArrayList<>();//getBlackList(json, "Analytics");
        ArrayList<Company> fingerprinting = getBlackList(json, "Fingerprinting");
        ArrayList<Company> social = getBlackList(json, "Social");
        ArrayList<Company> disconnect = getBlackList(json, "Disconnect");

        return new MozillaBlackList(advertising, content,fingerprinting,social,disconnect);
    }

    private ArrayList<Company> getBlackList(JsonElement json, String category) {
        ArrayList<Company> ret = new ArrayList<>();

        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("categories");
        JsonArray advertising = categories.getAsJsonArray(category);

        for (JsonElement company : advertising) {

            HashSet<String> domains = new HashSet<>();
            String name = "";
            String website = "";

            for (String s : company.getAsJsonObject().keySet()) {
                // Company names

                name = s;
                JsonObject companyNameObj = company.getAsJsonObject().get(s).getAsJsonObject();
                for(String mainURL : companyNameObj.keySet()){
                    website = mainURL;

                    companyNameObj.get(mainURL).getAsJsonArray().forEach(jsonElement -> domains.add(jsonElement.getAsString()));
                }
            }

            ret.add(new Company(name, website, domains));
        }

        return ret;
    }
}
