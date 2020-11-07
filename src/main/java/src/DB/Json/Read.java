package src.DB.Json;


import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Read {
    public static Object ReturnJson(String filename) throws Exception{
        FileReader r = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(r);
    }
    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject(ReturnJson("db.json"));
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("db"));
    }
}
