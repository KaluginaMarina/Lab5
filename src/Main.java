import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        CollectionManage cm = new CollectionManage();
        Runtime.getRuntime().addShutdownHook(new Thread(()->cm.collectionSave()));
        cm.collectionCreater();

        String man = cm.read("materials/man.txt");
        System.out.println("help / ?: открыть справку");

        while(true){
            System.out.println("Введите команду: ");
            try{Scanner input = new Scanner(System.in);
            String command = input.nextLine();
            command = command.replaceAll("\\s+", "");
            if (command.equals("remove_last")){
                cm.removeLast();
            } else if (command.equals("load")) {
                if(cm.load()){
                    System.out.println("Выполнено.");
                }
            } else if (command.equals("info")){
                cm.info();
            } else if (command.length() > 13 && command.substring(0, 14).equals("remove_greater")){
                if(cm.remove_greater(command.substring(14))){
                    System.out.println("Выполнено.");
                };
            } else if ((command.length() > 9 && command.substring(0, 10).equals("add_if_max")) || (command.length() > 9 && command.substring(0, 9).equals("add_if_min"))){
                if(cm.addIf(command.substring(0, 10), command.substring(10))){
                    System.out.println("Выполнено.");
                }
            } else if (command.length() > 2 && command.substring(0,3).equals("add")){
                if(cm.add(command.substring(3, command.length()))){
                    System.out.println(cm.getHeroes().getLast() + " добавлен в коллекцию.");
                };
            } else if (command.equals("print")) {
                System.out.println(cm.getHeroes());
            } else if (command.equals("sort")) {
                cm.sort();
                System.out.println("Выполнено.");
            } else if (command.equals("?") || command.equals("help")){
                System.out.println(man);
            } else if (command.equals("exit")){
                break;
            } else {
                System.out.println("Команда не найдена.\nhelp / ?: открыть справку");
            } }
            catch (NoSuchElementException e){break;};

        }

        System.out.println("\"Незнайка на Луне\"");
        Reader reader = new Reader("Читатель");

        Forest thisForest = new Forest();                                                    // Создаем лес
        Tree tree = new Tree("дерево", 20, 30, thisForest);
        Tree tree2 = new Tree ("сосна", 25, 30, thisForest);

        Forest.ForestElf elf = thisForest.new ForestElf();                                 // Создаем в лесу тишину
        elf.makeSilence();

        Moonlighter skuperfild = new Moonlighter("Скуперфильд", 100, 100, 200);
        Flea flea = new Flea(100, 100, 300);

        skuperfild.tease(flea);                                                            // Скуперфильд не в силах расправиться с ничтожным клопом. Скуперфильд пришел в бешенство.

        Shorties migo = new Shorties("Миго", 0, 0, 100);
        Shorties readerMigo = migo;
        Shorties julio = new Shorties("Жулио", 10, 10, 110);
        Shorties readerJulio = julio;
        Shorties krabs = new Shorties("Крабс", 20, 10, 115);
        Shorties readerKrabs = krabs;
        Shorties sckuperfildKrabs = krabs;

        thisForest.stepsShorties();                                                     // Послышались шаги коротышек
        skuperfild.headUp();                                                            // Скуперфильд поднял голову
        elf.makeSilence();
        skuperfild.seeShorties(krabs, krabs, migo, julio);                              // Скуперфильд увидел 3 коротышек
                                                                                        // Скуперфильд почувствовал, что
                                                                                        // Коротышка похож на Крабса
        krabs.goForThree(tree);                                                         // Коротышка скрылся за деревом
        migo.come(skuperfild);
        julio.come(skuperfild);                                                        // 2 коротышки подошли к Скуперфильду
        reader.feel();                                                                 // Читатель почувствовал, что
        julio.compare(readerJulio);                                                    // Коротышка похож на Жулио
        migo.compare(readerMigo);                                                      // Коротышка похож на Миго

        cm.collectionSave();
    }
}
