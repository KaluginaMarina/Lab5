package manage;

import model.Moonlighter;
import model.Personage;
import model.Reader;
import model.Shorties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

public class Command extends CollectionManage{

    public Command(){
        createDate = new Date();
        changeDate = createDate;
    }

    /**
     * add {element} Метод для добавления элемента в коллекцию в интерактивном режиме
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @param next - строка, которая подается пользователем после команды
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    @Override
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
                    heroes = Stream.concat(heroes.stream(), Stream.of(reader)).sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
                    break;
                }
                case "Лунатик": {
                    Moonlighter moonlighter = new Moonlighter((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height")));
                    moonlighter.skillSwear =  toIntExact((long) ob.get("skillSwear"));
                    moonlighter.force =  toIntExact((long) ob.get("force"));
                    if(!moonlighter.setMood((String) ob.get("mood"))){
                        throw new Exception();
                    }
                    heroes = Stream.concat(heroes.stream(), Stream.of(moonlighter)).sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
                    break;
                }
                case "Коротышка": {
                    Shorties shorties = new Shorties((String) ob.get("name"), (double) ob.get("x"), (double) ob.get("y"), toIntExact((long) ob.get("height")));
                    shorties.skillSwear =  toIntExact((long) ob.get("skillSwear"));
                    shorties.force =  toIntExact((long) ob.get("force"));
                    if(!shorties.setMood((String) ob.get("mood"))){
                        throw new Exception();
                    }
                    heroes = Stream.concat(heroes.stream(), Stream.of(shorties)).sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
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
        heroes = heroes.stream().sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
        changeDate = new Date();
        return true;
    }


    /**
     *  load - перечитать коллекцию из файла
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    @Override
    public boolean load(){
        heroes = new ConcurrentLinkedDeque<>();
        return (collectionCreater());
    }

    /**
     * remove_greater {element}: удалить из коллекции все элементы, превышающие заданный
     * Формат задания элемента {element}- json
     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение
     * @param next -строка, которая подается пользователем после команды
     * @return true - успешное выполнение команды, false - при возникновении ошибки
     */
    @Override
    public boolean remove_greater(String next){
        Personage pers = newPers(next);
        if (pers == null) {
            return false;
        };
        heroes = heroes.stream().filter(x -> x.compareTo(pers) < 0).sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
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
    @Override
    public boolean addIf(String command, String next){
        if (heroes.isEmpty()){
            Personage pers = newPers(next);
            heroes = Stream.concat(heroes.stream(), Stream.of(pers)).sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
            System.out.println("Элемент " + pers.toString() + " добавлен");
            changeDate = new Date();
            return true;
        }
        Personage pers = newPers(next);
        if (pers == null){
            return false;
        }
        if(command.equals("add_if_max")? (pers.compareTo(heroes.getLast()) <= 0) : (pers.compareTo(heroes.getLast()) >= 0)){
            System.out.println("Элемент " + pers.toString() + " не добавлен. Элемент не самый " + (command.equals("add_if_max")? "большой." : "маленький"));
            return true;
        }
        heroes = Stream.concat(heroes.stream(), Stream.of(pers)).sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
        System.out.println("Элемент " + pers.toString() + " добавлен");
        changeDate = new Date();
        return true;
    }

    /**
     * info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, дата изменения, количество элементов)
     */
    @Override
    public void info(){
        System.out.println("Тип коллекции: " + heroes.getClass());
        System.out.println("Количество элементов в коллекци: " + heroes.size());
        System.out.println("Дата создания: " + createDate);
        System.out.println("Дата изменения: " + changeDate);
    }


    /**
     * remove_last - удаляет последний элемент
     */
    @Override
    public void removeLast(){
        if (heroes.isEmpty()){
            System.out.println("Коллекция пуста.");
            return;
        }
        System.out.println("Элемент " + heroes.getLast() + " удален.");
        heroes = heroes.stream().limit(heroes.size() - 1).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
        changeDate = new Date();
    }
}
