import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Math.toIntExact;

public class CollectionManage {
    private ArrayDeque<Personage> heroes = new ArrayDeque<>();
    private final Date createDate;
    private Date changeDate;

    private final String fileName = "materials\\Heroes.csv";
    private final String fileNameClosing = "materials\\HeroesClosing.csv";

    public CollectionManage(){
        createDate = new Date();
        changeDate = createDate;
    }

    public ArrayDeque<Personage> getHeroes() {
        return heroes;
    }

    /**
     * Метод для чтения текста из файла
     * @return String heroesJson - строка-содержимое файла
     */
    public String read(String fileName){
        String heroesJson = "";
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader br = new BufferedReader(isr)
        )
        {
            String line;

            while ((line = br.readLine()) != null) {
                heroesJson += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return heroesJson;
    }

    /**
     * Метод, создающий коллекцию Personage по данным из файла
     * @return true - успех, false - инче
     */
    public boolean collectionCreater(){
        String heroesJson = read(fileName);
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
            changeDate = new Date();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод, записывающий текущее состояние коллекции в файл
     */
    public void collectionSave(){
        try (FileWriter file = new FileWriter(fileNameClosing, false)){
            file.write(toJson());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Метод, преобразующий текущую строку в строку, формата JSON
     * @return String
     */
    public String toJson(){
        ArrayList<Map<Object, Object>> a = new ArrayList<>();
        while (!heroes.isEmpty()){
            switch (heroes.getFirst().type){
                case "Читатель": {
                    Map<Object, Object> map = new HashMap<>();
                    map.put("type", heroes.getFirst().type);
                    map.put("name", heroes.getFirst().name);
                    map.put("height", heroes.getFirst().height);
                    map.put("force", heroes.getFirst().force);
                    map.put("mood", heroes.getFirst().mood.toString());
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
                    map.put("mood", heroes.getFirst().mood.toString());
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


    /**
     * Метод, для команды remove_last
     * удаляет последний элемент
     */
    public void removeLast(){
        if (heroes.isEmpty()){
            System.out.println("Коллекция пуста.");
            return;
        }
        System.out.println("Элемент " + heroes.removeLast() + " удален.");
        changeDate = new Date();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * Метод для добавления элемента в коллекцию в интерактивном режиме
     */
    public boolean add() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите тип персонажа: ");
        String type = input.next();
        try {
            switch (type) {
                case "Читатель": {
                    System.out.println("Введите имя персонажа: ");
                    String name = input.next();
                    heroes.add(new Reader(name));
                    changeDate = new Date();
                    return true;
                }
                case "Коротышка": {
                }
                case "Лунатик": {
                    System.out.println("Введите имя персонажа: ");
                    String name = input.next();
                    System.out.println("Введите координату x: ");
                    double x = input.nextDouble();
                    System.out.printf("Введите координату у: ");
                    double y = input.nextDouble();
                    System.out.println("Укажите рост персонажа: ");
                    int h = input.nextInt();
                    if (type.equals("Лунатик")) {
                        heroes.add(new Moonlighter(name, x, y, h));
                    } else {
                        heroes.add(new Shorties(name, x, y, h));
                    }
                    changeDate = new Date();
                    return true;
                }
                default: {
                    System.out.println("Тип персонажа может быть одним из: \"Читатель\", \"Коротышка\", \"Лунатик\".");
                    return false;
                }
            }
        } catch (Exception e){
            System.out.println("Ошибка ввода.");
            return false;
        }
    }

    /**
     * перечитать коллекцию из файла
     * @return true - успех, false - иначе
     */
    public boolean load(){
        while (!heroes.isEmpty()){
            heroes.removeFirst();
        }
        if (collectionCreater()) {
            System.out.println("Выполнено.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * переписывает ArrayDegue в List
     * @return list
     */
     private List<Personage> toList(){
        List<Personage> list = new ArrayList<>();
        while (!heroes.isEmpty()){
            list.add(heroes.removeFirst());
        }
        toArrayDedue(list);
        return list;
    }

    /**
     * пеерписывает List в ArrayDeque
     * @param list
     */
    private void toArrayDedue(List<Personage> list){
        while (!heroes.isEmpty()){
            heroes.removeFirst();
        }
        for (int i = 0; i < list.size(); ++i){
            heroes.add(list.get(i));
        }
    }
    /**
     * Метод, сортирующий коллекцию по возрастанию
     */
    public void sort(){
        List<Personage> list = toList();
        Collections.sort(list, new Comparator<Personage>() {
            public int compare(Personage p1, Personage p2) {
                return p1.compare(p2);
            }
        });
        toArrayDedue(list);
        changeDate = new Date();
    }

    public boolean remove_greater(){
        if (!add()) {
            return false;
        };
        List<Personage> list = toList();
        for (int i = 0; i < list.size() - 1; ++i){
            if (list.get(i).compare(list.get(list.size() - 1)) > 0){
                list.remove(i);
            }
        }
        list.remove(list.get(list.size() - 1));
        toArrayDedue(list);
        changeDate = new Date();
        return true;
    }

    /**
     * add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
     * add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     * @param command "add_if_max" или "add_if_min"
     */
    public boolean addIf(String command){
        if (!add()){
            return false;
        }
        Personage p = heroes.removeLast();
        List<Personage> list = toList();
        Collections.sort(list, new Comparator<Personage>() {
            public int compare(Personage p1, Personage p2) {
                return p1.compare(p2);
            }
        });
        if(command.equals("add_if_max")? (p.compare(list.get(list.size()-1)) <= 0) : (p.compare(list.get(0)) >= 0)){
            System.out.println("Элемент не добавлен.");
            return true;
        }
        heroes.add(p);
        System.out.println("Элемент добавлен");
        changeDate = new Date();
        return true;
    }
}
