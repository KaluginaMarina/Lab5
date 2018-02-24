public class Moonlighter extends Personage {
    String type = "Лунатик";
    int skillSwear = 10;

    class unchException extends RuntimeException{
        unchException(String msg){
            super(msg);
        }
    }

    Moonlighter (String name, double x, double y, int height){
        this.name = name;
        this.type = "Лунатик";
        this.x = x;
        this.y =y;
        this.force = 7;
        this.height = height;
    }

    /**
     *  Поднять голову
     */
    public void headUp() {
        System.out.println(this.name + " поднял голову.");
    }

    static enum ShortiesType{
        NEAR, FAR;
    }

    /**
     * Увидеть коротышку
     */
    public void seeShorties(Shorties k, Shorties... p ){
        //Добавим специально сюда локальный класс, например ОпределитьРасстояниеМеждуОбъектамик который будет специально,
        // чтобы определить расстояние по тексту (вдали они друг для друга или нет)
        /**
         * Локальный класс, который определяет расстояние между коротышками и говорит дадеко ли они от нас находятся
         */
        class Distance{
             private ShortiesType far(Moonlighter k, Shorties p){
                double s = Math.sqrt( (k.x-p.x)*(k.x-p.x) + (k.y-p.y)*(k.y-p.y) );
                if (s > 10 && s <= 50){
                    return ShortiesType.FAR;
                }
                else {
                    return ShortiesType.NEAR;
                }
             }
             private int shortiesCounter(ShortiesType type, Moonlighter k, Shorties... p){
                 int counter = 0;
                 for (int i = 0; i < p.length; ++i){
                     if (this.far(k, p[i]) == type) {
                         counter++;
                     }
                 }
                 return counter;
             }
             private int  shortiesCountFar(Moonlighter k, Shorties... p){
                 return shortiesCounter(ShortiesType.FAR, k, p);
             }
             private int shortiesCountNear(Moonlighter k, Shorties... p){
                 return shortiesCounter(ShortiesType.NEAR, k, p);
             }
        }
        Distance d = new Distance();
        int near = d.shortiesCountFar(this, p), far = d.shortiesCountNear(this, p), longAway = p.length - far - near;

        if (far > 0 || near > 0) {
            System.out.println(this.name + " увидел " + ((far == 0) ? "вблизи " : "вдалеке ") + p.length + " коротышек.");
        } else {
            System.out.println(this.name + " не увидел " + "коротышек.");
        }
        this.feel();
        for (int i = 0; i < p.length; ++i){
            p[i].compare(k);
        }
    }

    public void tease (Flea p){
        if (p == null){
            throw new unchException("error");
        }
        try {
            MessangeToInsects msg = new MessangeToInsects("Ах ты, зверюшка чертова!", "выругался", p, this);
            msg.sayMessange();
            MessangeToInsects msg2 = new MessangeToInsects("Мало тебе двух миллионов? Три дам, чтоб тебе провалиться на месте!", "сказал", p, this);
            msg2.sayMessange();

            if (p.skillSwear < this.skillSwear) {
                battle(p);
            }
        } catch (unchException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(this.name + " немой.");
        }
    }

    /**
     * Битва Лунатика и Клопа
     * @param p - Клоп
     * @return true - Лунатик победил, false - иначе
     */
    public boolean battle(Flea p){


        if (p.force >= this.force){
            this.feel();
            p.fallByCollar(this);
            p.biteBack(this);

            System.out.println(this.name + " не в силах расправиться с ничтожным " + p.name + "ом.");
            if (this.mood != Mood.FURY){
                changeMood(Mood.FURY);
            }
            return true;
        }
        else {
            System.out.println(this.name + " расправился с " + p.name + ".");
            if (this.mood != Mood.HAPPY){
                changeMood(Mood.HAPPY);
            }
            return false;
        }
    }

    /**
     * Метод, который меняет настроение
     * @param newMood - новое настроение персонажа
     */
    public void changeMood(Mood newMood){
        switch (newMood){
            case NORMAL: {
                System.out.println("У " + this.name + " снова пришел в норму.");
                break;
            }
            case SAD: {
                System.out.println(this.name + " расстроился.");
                break;
            }
            case FURY: {
                System.out.println(this.name + " пришел в бешенство.");
                break;
            }
            case HAPPY: {
                System.out.println(this.name + " обрадовался.");
                break;
            }
            default: {
                System.out.println("У " + this.name + " что-то случилось с настроением.");
            }
        }
    }
}
