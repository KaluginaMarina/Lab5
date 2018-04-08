import manage.*;
import model.*;

import java.util.*;

public class Main {
    public static void main(String[] args){

        Command cm = new Command();
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
            } else if ((command.length() > 9 && command.substring(0, 10).equals("add_if_max")) || (command.length() > 9 && command.substring(0, 10).equals("add_if_min"))){
                if(cm.addIf(command.substring(0, 10), command.substring(10))){
                    System.out.println("Выполнено.");
                }
            } else if (command.length() > 2 && command.substring(0,3).equals("add")){
                if(cm.add(command.substring(3, command.length()))){
                    System.out.println(cm.getHeroes().getLast() + " добавлен в коллекцию.");
                };
            } else if (command.equals("print")) {
                System.out.println(cm.getHeroes());
            } else if (command.equals("?") || command.equals("help")){
                System.out.println(man);
            } else if (command.equals("exit")){
                break;
            } else {
                System.out.println("Команда не найдена.\nhelp / ?: открыть справку");
            } }
            catch (NoSuchElementException e){break;};

        }
        //cm.collectionSave();
    }
}