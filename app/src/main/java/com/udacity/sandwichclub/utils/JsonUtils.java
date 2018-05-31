package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

  static ArrayList<String> alsoKnownAsList = new ArrayList<>();
  static ArrayList<String> ingredientsList = new ArrayList<>();

    public static Sandwich parseSandwichJson(String json) {

        final String KEY_NAME = "name";
        final String KEY_MAIN_NAME = "mainName";
        final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_INGREDIENTS = "ingredients";

        int arrayMark=0;

        Sandwich sandwich=null;
        try {

            //This forms the Root JSONObject of the JSON File
            JSONObject jsonObject = new JSONObject(json);

            //Getting name JSONObject from the Root JSONObject
            JSONObject nameObject = jsonObject.getJSONObject(KEY_NAME);

            String mainName = nameObject.getString(KEY_MAIN_NAME);

            JSONArray alsoKnownAsArray = nameObject.getJSONArray(KEY_ALSO_KNOW_AS);

                //Extracting items from JSONArray and adding it to the alsoKnownAs List
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
                }

            String placeOfOrigin = jsonObject.getString(KEY_PLACE_OF_ORIGIN);
            String description = jsonObject.getString(KEY_DESCRIPTION);
            String imageUrl = jsonObject.getString(KEY_IMAGE);

            JSONArray ingredientsArray = jsonObject.getJSONArray(KEY_INGREDIENTS);

                //Extracting items from JSONArray and adding it to the Ingredients List
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredientsList.add(ingredientsArray.getString(i));
                }
            //Making a new object of Sandwich class and passing the appropriate parameters in the constructor
            sandwich = new Sandwich(mainName,alsoKnownAsList,placeOfOrigin,description,imageUrl,ingredientsList);

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
