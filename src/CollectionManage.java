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

    CollectionManage(){
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
        Scanner sc = new Scanner(heroesJson);
        sc.useDelimiter("[,\n]");
        sc.useLocale(Locale.ENGLISH);
        try {
            while(sc.hasNext()){
                String type = sc.next();
                switch (type){
                    case "Читатель": {
                        heroes.add(new Reader(sc.next()));
                        break;
                    }
                    case "Лунатик": {
                        heroes.add(new Moonlighter(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt()));
                        break;
                    }
                    case "Коротышка": {
                        heroes.add(new Shorties(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt()));
                        break;
                    }
                    default: {
                        break;
                    }
                }
                for(int i = 0; i < 3; ++i){ sc.next();}
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
            file.write(toSCV());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String toSCV(){
        String res = "";
        while (!heroes.isEmpty()){
            switch (heroes.getFirst().type){
                case "Читатель" : {
                    res += heroes.getFirst().type + "," + heroes.getFirst().name + "," + heroes.getFirst().height  + "," + heroes.getFirst().force + "," + heroes.getFirst().mood + "\n";
                    break;
                }
                case "Коротышка": { }
                case "Лунатик": {

                    res += heroes.getFirst().type + "," + heroes.getFirst().name + "," + heroes.getFirst().x + "," + heroes.getFirst().y + "," + heroes.getFirst().height + "," + heroes.getFirst().skillSwear + "," + heroes.getFirst().force + "," + heroes.getFirst().mood + "\n";
                    break;
                }
                default:{
                    break;
                }
            }
            heroes.removeFirst();
        }
        return res;
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

    private String readPers(){
        Scanner input = new Scanner(System.in);
        String res = "";
        String str = input.nextLine();
       try{
           while (!(str).equals("")){
               res += str;
               str = input.nextLine();
           }
       } catch (Exception e){
           e.printStackTrace();
           return null;
       };
        return res;
    }

    /**
     * Метод для добавления элемента в коллекцию в интерактивном режиме
     */
    public boolean add() {
        try {
            String heroesJson = readPers();
            if (heroesJson == null){
                return false;
            };
            JSONParser parser = new JSONParser();
            JSONObject ob = (JSONObject) parser.parse(heroesJson);
            switch ((String)ob.get("type")) {
                case "Читатель": {
                    heroes.add(new Reader((String) ob.get("name")));
                    break;
                }
                case "Лунатик": {
                    heroes.add(new Moonlighter((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height"))));
                    break;
                }
                case "Коротышка": {
                    heroes.add(new Shorties((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height"))));
                    break;
                }
                default: {
                    break;
                }
            }
            System.out.println(heroes);
        } catch (Exception e){
            System.out.println("Объект должен быть формата json.");
            return false;
        }
        changeDate = new Date();
        return true;
    }

    /**
     * перечитать коллекцию из файла
     * @return true - успех, false - иначе
     */
    public boolean load(){
        while (!heroes.isEmpty()){
            heroes.removeFirst();
        }
        return (collectionCreater());
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

    /**
     * удалить из коллекции все элементы, превышающие заданный
     */
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
        if (heroes.isEmpty()){
            System.out.println("Коллекция пуста.");
            return false;
        }
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