// Solution to Week 13 Homework Exercise 4

// compile: javac -cp json-simple-1.1.1.jar;. OpenRequest.java

package com.josh;

import org.json.simple.*;  // required for JSON encoding and decoding

public class OpenRequest extends Request {
    // class name to be used as tag in JSON representation
    private static final String _class =
        OpenRequest.class.getSimpleName();

    private String name;

    public OpenRequest(String name) {
        this.name = name;
    }

    // Serializes this object into a JSONObject
    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("identity", name);
        return obj;
    }

//    // Tries to deserialize a OpenRequest instance from a JSONObject.
//    // Returns null if deserialization was not successful (e.g. because a
//    // different object was serialized).
//    public static OpenRequest fromJSON(Object val) {
//        try {
//            JSONObject obj = (JSONObject)val;
//            // check for _class field matching class name
//            if (!_class.equals(obj.get("_class")))
//                return null;
//            // construct the object to return (checking for nulls)
//            return new OpenRequest(_class);
//        } catch (ClassCastException | NullPointerException e) {
//            return null;
//        }
//    }
}
