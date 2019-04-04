// Solution to Week 13 Homework Exercise 4

// compile: javac -cp json-simple-1.1.1.jar;. GetRequest.java

package com.josh;

import org.json.simple.*;  // required for JSON encoding and decoding

public class GetRequest extends Request {
    private static final String _class =
        GetRequest.class.getSimpleName();

    private String identity;
    private int after;

    public GetRequest(String identity, int after) {
        this.identity = identity;
        this.after = after;
    }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("identity", identity);
        obj.put("after", after);
        return obj;
    }

    public static GetRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class")))
                return null;
            return new GetRequest(obj.get("identity").toString(), (int)obj.get("after"));
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
