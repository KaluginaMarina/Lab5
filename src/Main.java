import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException,  IOException {

        System.out.println("\"Незнайка на Луне\"");
        Reader reader = new Reader("Читатель");
        String heroesJson = new String();

        ArrayDeque<Personage> heroes = new ArrayDeque<>();
        try (FileInputStream fis = new FileInputStream("materials\\Heroes.csv");
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

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(heroesJson);
            JSONObject address = (JSONObject) json.get("address");
            System.out.println(address.get("city"));
        } catch (Exception e){
            e.printStackTrace();
        }

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

    }
}
