package model;

import java.util.Date;

public class Shorties extends Personage {

    public Shorties (String name, double x, double y, int height){
        this.name = name;
        this.type = "Коротышка";
        this.x = x;
        this.y = y;
        this.force = 5;
        this.height = height;
        this.skillSwear = 3;
        this.dateCreate = new Date();
    }

    /**
     * Сравнение коротышек.
     * @param s - второй коротышка
     * @return true - коротышки похожи, false - разные коротышки
     */
    public boolean compare(Shorties s){
        if (equals(s)) {
            System.out.println(this.type + " похож на " + this.name + ".");
            return true;
        }
        else {
            System.out.println(this.type + " не похож на " + this.name + ".");
            return false;
        }
    }

    public interface Cometo{
        public double coordinate(Personage a, Personage b);
    }
    public void come(Personage p){

        Cometo xy = new Cometo(){
            @Override
            public double coordinate(Personage who, Personage where){
                if (who.x - where.x < 0){
                    return where.x + Math.random()*10;
                }
                else if (who.x - where.x > 0){
                    return where.x - Math.random()*10;
                }
                else {
                    return where.x;
                }
            }
        };
        this.x = xy.coordinate(this, p);
        this.y = xy.coordinate(this, p);
        System.out.println("Какой-то " + this.type + " подошел к " + p.name + ".");
    }

    /**
     * Коротышка скрылся за деревом
     */
    public void goForThree(Tree t){
        this.x = t.x;
        this.y = t.y;
        System.out.println(this.type + " скрылся за " + t.name + ".");
    }
}
