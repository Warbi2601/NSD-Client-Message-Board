// Solution to Week 13 Homework Exercise 4

// compile: javac -cp json-simple-1.1.1.jar;. UnsubscribeRequest.java

package com.josh;

import org.json.simple.*;  // required for JSON encoding and decoding

public class UnsubscribeRequest extends Request {
    // class name to be used as tag in JSON representation
    private static final String _class =
        UnsubscribeRequest.class.getSimpleName();

    private String identity;
    private String channel;

    public UnsubscribeRequest(String identity, String channel) {
        this.identity = identity;
        this.channel = channel;
    }

    // Serializes this object into a JSONObject
    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("identity", identity);
        obj.put("channel", channel);
        return obj;
    }

    // Tries to deserialize a UnsubscribeRequest instance from a JSONObject.
    // Returns null if deserialization was not successful (e.g. because a
    // different object was serialized).
    public static UnsubscribeRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            // check for _class field matching class name
            if (!_class.equals(obj.get("_class")))
                return null;
            // deserialize posted message
            // construct the object to return (checking for nulls)
            return new UnsubscribeRequest(obj.get("identity").toString(), obj.get("channel").toString());
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
