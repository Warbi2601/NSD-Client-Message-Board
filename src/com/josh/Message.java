package com.josh;

import org.json.simple.*;  // required for JSON encoding and decoding

import java.util.Base64;

public class Message {
    private static final String _class = Message.class.getSimpleName();
    private final String body;
    private final String from;
    private final long when;
    private final String pic;

    public Message(String body, String from, long when) {
        if (body == null || from == null) throw new NullPointerException();
        this.body = body;
        this.from = from;
        this.when = when;
        this.pic = "";
    }

    public Message(String body, String from, long when, String pic) {
        if (body == null || from == null) throw new NullPointerException();
        this.body = body;
        this.from = from;
        this.when = when;
        this.pic = pic;
    }

    public String getBody() { return body; }
    public String getFrom() { return from; }
    public long   getWhen() { return when; }
    public String getPic() { return pic; }

    public byte[] decodeImage() {
        byte[] imageByteArray = Base64.getDecoder().decode(pic);
        return imageByteArray;
    }

    public String toString() {
        return from + ": " + body + " (" + when + ")";
    }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("body", body);
        obj.put("from", from);
        obj.put("when", when);
        obj.put("pic", pic);
        return obj;
    }

    public static Message fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class"))) return null;
            String body      = (String)obj.get("body");
            String from    = (String)obj.get("from");
            long   when = (long)obj.get("when");
            String pic = (String)obj.get("pic");
            return new Message(body, from, when, pic);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
