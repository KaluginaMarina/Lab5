import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException,  IOException {

        CollectionManage cm = new CollectionManage();
        cm.collectionCreater();

        String man = cm.read("materials\\man.txt");
        System.out.println("help / ?: открыть справку");

        while(true){
            System.out.println("Введите команду: ");
            Scanner input = new Scanner(System.in);
            String command = input.next();

            if (command.equals("remove_last")){
                cm.removeLast();
            } else if (command.equals("load")) {
                if(cm.collectionCreater()) {
                    cm.load();
                }
            } else if (command.equals("info")){
                System.out.println("Тип коллекции: " + cm.getHeroes().getClass());
                System.out.println("Количество элементов в коллекци: " + cm.getHeroes().size());
                System.out.println("Дата создания: " + cm.getCreateDate());
                System.out.println("Дата изменения: " + cm.getChangeDate());
            } else if (command.equals("remove_greater")){
                if(cm.remove_greater()){
                    System.out.println("Выполнено.");
                };
            } else if (command.equals("add_if_max") || command.equals("add_if_min")){
                if(cm.addIf(command)){
                    System.out.println("Выполнено.");
                }
            } else if (command.equals("add")){
                if(cm.add()){
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
            }

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
