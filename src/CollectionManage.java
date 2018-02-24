import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;

import static java.lang.Math.toIntExact;

public class CollectionManage {
    private ArrayDeque<Personage> heroes = new ArrayDeque<>();
    private final String fileName = "materials\\Heroes.csv";
    private final String fileNameClosing = "materials\\HeroesClosing.svg";


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

}
