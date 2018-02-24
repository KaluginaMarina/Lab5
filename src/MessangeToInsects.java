import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MessangeToInsects {
    public String tone;
    public String msg;
    public Insects toWhom;
    public Personage fromWhom;
    class AngryInsectException extends Exception{
        AngryInsectException(String msg){
            super(msg);
        }
    }

    /**
     * Метод, который создает сообщение
     */
    MessangeToInsects(String msg, String tone, Insects toWhom, Personage fromWhom){
        this.msg = msg;
        this.toWhom = toWhom;
        this.fromWhom = fromWhom;
        this.tone = tone;
    }
    /**
     * Метод в котором персонаж ругается на насекомого.
     * Причем, если skillSwear у насекомого больше, чем у персонажа, то он отвечает.
     */
    void sayMessange() throws IOException {
        System.out.println("\"" + msg + "\"" + " - " + tone + " " + fromWhom.name + ".");

        if (toWhom.skillSwear >= fromWhom.skillSwear){

            class Answer {
                private ArrayList<String> tone = new ArrayList<>();
                private ArrayList<String> ans = new ArrayList<>();

                Answer() throws IOException {
                    FileReader answer = new FileReader("materials\\answer.txt");
                    FileReader toneFile = new FileReader("materials\\tone.txt");
                    Scanner scanAns = new Scanner(answer);
                    Scanner scanTone = new Scanner(toneFile);
                    while(scanAns.hasNextLine()) {
                        ans.add(scanAns.nextLine());
                    }
                    while (scanTone.hasNextLine()) {
                        tone.add(scanTone.nextLine());
                    }

                    answer.close();
                    toneFile.close();
                }

                private void sayAnswer() throws AngryInsectException {
                    if (ans.size() == 0 || tone.size() == 0){
                        throw new AngryInsectException(toWhom + " топнул ногой, потому что не нашел, что ответить.");
                    }
                    System.out.println("\"" + ans.get((int)(Math.random()*100 % ans.size())) + "\"" + " - " + tone.get((int)(Math.random()*100 % tone.size())) + " " + toWhom + ".");
                }
            }

            Answer answer = new Answer();

            try {
                answer.sayAnswer();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(toWhom + " заплакал и промолчал, потому что обратился к недопустимому индексу массива.");
            } catch (AngryInsectException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Никто ничего не ответил, потому что, на самом деле, никакого клопа не существует. Как и персонажа.");
            } catch (Exception e){
                System.out.println(toWhom + " просто испарился. Никто не знает почему.");
            }
        }
    }
}