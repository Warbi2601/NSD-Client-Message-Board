// Solution to Week 13 Homework Exercise 4

// compile: javac -cp json-simple-1.1.1.jar;. GetRequest.java

package com.josh;

import org.json.simple.*;  // required for JSON encoding and decoding

public class GetRequest extends Request {
    // class name to be used as tag in JSON representation
    private static final String _class =
        GetRequest.class.getSimpleName();

    private String identity;
    private int after;

    public GetRequest(String identity, int after) {
        this.identity = identity;
        this.after = after;
    }

    // Serializes this object into a JSONObject
    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("identity", identity);
        obj.put("after", after);
        return obj;
    }

    // Tries to deserialize a GetRequest instance from a JSONObject.
    // Returns null if deserialization was not successful (e.g. because a
    // different object was serialized).
    public static GetRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            // check for _class field matching class name
            if (!_class.equals(obj.get("_class")))
                return null;
            // construct the new object to return
            return new GetRequest(obj.get("identity").toString(), (int)obj.get("after"));
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
