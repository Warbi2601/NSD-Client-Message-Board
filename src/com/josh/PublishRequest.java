
package com.josh;

import javafx.scene.media.MediaPlayer;
import org.json.simple.*;  // required for JSON encoding and decoding

public class PublishRequest extends Request {
    // class name to be used as tag in JSON representation
    private static final String _class =
            PublishRequest.class.getSimpleName();

    private String identity;
    private Message message;

    // Constructor; throws NullPointerException if message is null.
    public PublishRequest(String identity, Message message) {
        // check for null
        if (identity == null)
            throw new NullPointerException();
        this.identity = identity;

        // check for null
        if (message == null)
            throw new NullPointerException();
        this.message = message;
    }

    Message getMessage() { return message; }

    // Serializes this object into a JSONObject
    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("identity", identity);
        obj.put("message", message.toJSON());
        return obj;
    }

//    // Tries to deserialize a PostRequest instance from a JSONObject.
//    // Returns null if deserialization was not successful (e.g. because a
//    // different object was serialized).
//    public static PublishRequest fromJSON(Object val) {
//        try {
//            JSONObject obj = (JSONObject)val;
//            // check for _class field matching class name
//            if (!_class.equals(obj.get("_class")))
//                return null;
//            // deserialize posted message
//            String message = (String)obj.get("message");
//            // construct the object to return (checking for nulls)
//            return new PublishRequest(message);
//        } catch (ClassCastException | NullPointerException e) {
//            return null;
//        }
//    }
}
