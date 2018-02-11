package com.kimersoft.pointofsaleterminal.volleyHelpers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gadour on 05/12/2017.
 */

public class ObjectToJsonParser {

    public JSONObject userSignInJson(String email, String password){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
