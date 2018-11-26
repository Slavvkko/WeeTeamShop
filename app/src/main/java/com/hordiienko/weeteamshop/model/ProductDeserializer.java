package com.hordiienko.weeteamshop.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hordiienko.weeteamshop.utils.Utils;

import java.lang.reflect.Type;

public class ProductDeserializer implements JsonDeserializer<Product> {
    private static final int LANGUAGE_ID = 1; // Russian

    @Override
    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        int id = getAsIntNullCheck(object.get("id"));
        int defaultImageId = getAsIntNullCheck(object.get("id_default_image"));
        String reference = getAsStringNullCheck(object.get("reference"));
        float price = getAsFloatNullCheck(object.get("price"));
        String name = Utils.removeHtmlTags(getLanguageString(object.get("name"), LANGUAGE_ID));
        String description = Utils.removeHtmlTags(getLanguageString(object.get("description"), LANGUAGE_ID));

        return new Product(id, defaultImageId, reference, price, name, description);
    }

    private String getAsStringNullCheck(JsonElement element) {
        return element.isJsonNull() ? "" : element.getAsString();
    }

    private int getAsIntNullCheck(JsonElement element) {
        return element.isJsonNull() ? -1 : element.getAsInt();
    }

    private float getAsFloatNullCheck(JsonElement element) {
        return element.isJsonNull() ? -1.0f : element.getAsFloat();
    }

    private String getLanguageString(JsonElement element, int languageId) {
        try {
            JsonArray strings = element.getAsJsonArray();

            for (JsonElement elem : strings) {
                JsonObject json = elem.getAsJsonObject();

                if (json.get("id").getAsInt() == languageId) {
                    return json.get("value").getAsString();
                }
            }

            if (strings.size() > 0) {
                return strings.get(0).getAsJsonObject().get("value").getAsString();
            }
        } catch (IllegalStateException ignored) {}

        return "";
    }
}
