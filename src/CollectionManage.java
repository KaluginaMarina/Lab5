import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;

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
                    System.out.println("Введите координату у: ");
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
