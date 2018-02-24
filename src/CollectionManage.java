import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.toIntExact;

public class CollectionManage {
    private ArrayDeque<Personage> heroes = new ArrayDeque<>();
    private final String fileName = "materials\\Heroes.csv";
    private final String fileNameClosing = "materials\\HeroesClosing.csv";


    public ArrayDeque<Personage> getHeroes() {
        return heroes;
    }

    public String read(){
        String heroesJson = "";
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)
        )
        {
            String line;

            while ((line = br.readLine()) != null) {
                heroesJson += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return heroesJson;
    }

    public void collectionCreater(){
        String heroesJson = read();
        try {
            JSONParser parser = new JSONParser();
            JSONArray json = (JSONArray) parser.parse(heroesJson);
            for(int i = 0; i < json.size(); ++i){
                JSONObject ob = (JSONObject) json.get(i);
                switch ((String)ob.get("type")){
                    case "Читатель": {
                        heroes.add(new Reader((String)ob.get("name")));
                        break;
                    }
                    case "Лунатик": {
                        heroes.add(new Moonlighter((String)ob.get("name"), (double)ob.get("x"), (double)ob.get("y"), toIntExact((long)ob.get("height"))));
                        break;
                    }
                    case "Коротышка": {
                        heroes.add(new Shorties((String)ob.get("name"), (double)ob.get("x"), (double)ob.get("y"), toIntExact((long)ob.get("height"))));
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void collectionSave(){
        try (FileWriter file = new FileWriter(fileNameClosing, false)){
            file.write(toJson());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String toJson(){
        String res = "";
        ArrayList<Map<Object, Object>> a = new ArrayList<>();
        while (!heroes.isEmpty()){
            switch (heroes.getFirst().type){
                case "Читатель": {
                    Map<Object, Object> map = new HashMap<>();
                    map.put("type", heroes.getFirst().type);
                    map.put("name", heroes.getFirst().name);
                    map.put("height", heroes.getFirst().height);
                    map.put("force", heroes.getFirst().force);
                    a.add(map);
                    break;
                }
                case "Коротышка" : {}
                case "Лунатик" : {
                    Map<Object, Object> map = new HashMap<>();
                    map.put("type", heroes.getFirst().type);
                    map.put("name", heroes.getFirst().name);
                    map.put("x", heroes.getFirst().x);
                    map.put("y", heroes.getFirst().y);
                    map.put("height", heroes.getFirst().height);
                    map.put("skillSwear", heroes.getFirst().skillSwear);
                    map.put("force", heroes.getFirst().force);
                    a.add(map);
                    break;
                }
                default: {
                    break;
                }
            }
            heroes.removeFirst();
        }
        return JSONValue.toJSONString(a);
    }
}
