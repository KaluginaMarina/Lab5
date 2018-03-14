import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Math.toIntExact;

public class CollectionManage {
    private ArrayDeque<Personage> heroes = new ArrayDeque<>();
    private final Date createDate;
    private Date changeDate;

    //private final String fileName = "materials/Heroes.csv";
    //private final String fileNameClosing = "materials/HeroesClosing.csv";
    private final String fileName = System.getenv("FILENAME");
    private final String fileNameClosing = System.getenv("FILENAMECLOSE");


    CollectionManage(){
        createDate = new Date();
        changeDate = createDate;
    }

    public ArrayDeque<Personage> getHeroes() {
        return heroes;
    }

    /**
     * Метод для чтения текста из файла fileName
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
            System.out.println("Неправильный путь для FILENAME.");
        }
        return heroesJson;
    }

    /**
     * Метод, создающий коллекцию Personage по данным из файла fileName
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
                        Reader reader = new Reader(sc.next());
                        reader.height = sc.nextInt();
                        reader.force = sc.nextInt();
                        if (!reader.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(reader);
                        break;
                    }
                    case "Лунатик": {
                        Moonlighter moonlighter = new Moonlighter(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                        moonlighter.skillSwear = sc.nextInt();
                        moonlighter.force = sc.nextInt();
                        if (!moonlighter.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(moonlighter);
                        break;
                    }
                    case "Коротышка": {
                        Shorties shorties = new Shorties(sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                        shorties.skillSwear = sc.nextInt();
                        shorties.force = sc.nextInt();
                        if (!shorties.setMood(sc.next())){
                            throw new Exception();
                        }
                        heroes.add(shorties);
                        break;
                    }
                    default: {
                        throw new Exception();
                    }
                }
            }
            changeDate = new Date();
            return true;
        } catch (Exception e){
            System.out.println("Неправильное представление объекта.");
            return false;
        }
    }

    /**
     * Метод, записывающий текущее состояние коллекции в файл fileNameClose
     */
    public void collectionSave(){
        System.out.println(fileNameClosing);
        if (!Files.isWritable(Paths.get(fileNameClosing))){
            System.out.println("Что-то с правами");
            return;
        }

        try (FileWriter file = new FileWriter(fileNameClosing, false)){
            String toCsv = toSCV();
            file.write(toCsv);
        } catch (Exception e){
            System.out.println("Неправильный путь для FILENAMECLOSE." + e.getMessage());
        }
    }

    /**
     * Метод, переаодящий строку в строку, формата scv
     * @return String - строка, формата scv
     */
    private String toSCV(){
        String res = "";
        ArrayDeque<Personage> p = heroes.clone();
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
        heroes = p;
        return res;
    }

    /**
     * remove_last - удаляет последний элемент
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
     * Считывает строку из консоли.
     * Считывание происходит до тех пор, пока не встретится пустая строка
     * @return String - считанная строка
     */
    private String readPers(){
        Scanner input = new Scanner(System.in);
        String res = "";
        String str = input.nextLine();
        if(str.equals("\\s+")){
            str = input.nextLine();
        }
       try{
           while (!(str).equals("")){
               res += str;
               str = input.nextLine();
           }
       } catch (Exception e){
           e.printStackTrace();
           return res;
       };
        return res;
    }

    /**
     * add {element} Метод для добавления элемента в коллекцию в интерактивном режиме
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @param next - строка, которая подается пользователем после команды
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    public boolean add(String next) {
        try {
            String heroesJson = readPers();
            heroesJson = next + heroesJson;
            if (heroesJson == null){
                return false;
            };
            JSONParser parser = new JSONParser();
            JSONObject ob = (JSONObject) parser.parse(heroesJson);
            switch ((String)ob.get("type")) {
                case "Читатель": {
                    Reader reader = new Reader((String) ob.get("name"));
                    reader.height = toIntExact((long) ob.get("height"));
                    reader.force = toIntExact((long) ob.get("force"));
                    if(!reader.setMood((String) ob.get("mood"))){
                        throw new Exception();
                    }
                    heroes.add(reader);
                    //heroes.add(new Reader((String) ob.get("name")));
                    break;
                }
                case "Лунатик": {
                    Moonlighter moonlighter = new Moonlighter((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height")));
                    moonlighter.skillSwear =  toIntExact((long) ob.get("skillSwear"));
                    moonlighter.force =  toIntExact((long) ob.get("force"));
                    if(!moonlighter.setMood((String) ob.get("mood"))){
                        throw new Exception();
                    }
                    heroes.add(moonlighter);
                    //heroes.add(new Moonlighter((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height"))));
                    break;
                }
                case "Коротышка": {
                    Shorties shorties = new Shorties((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height")));
                    shorties.skillSwear =  toIntExact((long) ob.get("skillSwear"));
                    shorties.force =  toIntExact((long) ob.get("force"));
                    if(!shorties.setMood((String) ob.get("mood"))){
                        throw new Exception();
                    }
                    heroes.add(shorties);
                    //heroes.add(new Shorties((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height"))));
                    break;
                }
                default: {
                    throw new Exception();
                }
            };
        } catch (Exception e){
            System.out.println("Объект должен быть формата json или введено некорректное представление объекта.");
            return false;
        }
        changeDate = new Date();
        return true;
    }

    /**
     *  load - перечитать коллекцию из файла
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    public boolean load(){
        while (!heroes.isEmpty()){
            heroes.removeFirst();
        }
        return (collectionCreater());
    }

    /**
     * переписывает ArrayDegue в List
     * @return list - данная очередь в коллекцие list
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
     * @param list - lfyysq List
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
     * remove_greater {element}: удалить из коллекции все элементы, превышающие заданный
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @param next -строка, которая подается пользователем после команды
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    public boolean remove_greater(String next){
        if (!add(next)) {
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
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @param command "add_if_max" или "add_if_min"
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    public boolean addIf(String command, String next){
        if (heroes.isEmpty()){
            System.out.println("Коллекция пуста.");
            return false;
        }
        if (!add(next)){
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

    /**
     * info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, дата изменения, количество элементов)
     */
    public void info(){
        System.out.println("Тип коллекции: " + heroes.getClass());
        System.out.println("Количество элементов в коллекци: " + heroes.size());
        System.out.println("Дата создания: " + createDate);
        System.out.println("Дата изменения: " + changeDate);
    }
}