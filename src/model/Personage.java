package model;

import java.util.Date;

public abstract class Personage implements Comparable<Personage>{
    public String name = "NoName";
    public String type = "Персонаж";
    public double x = 0;
    public double y = 0;
    public int skillSwear;
    public int force;
    public int height;
    public Mood mood = Mood.NORMAL;
    public Date dateCreate;

    @Override
    public boolean equals(Object s) {
        if (s == null) {
            return false;
        }
        if (this == s) {
            return true;
        }
        if (!(getClass() == s.getClass())){
            return false;
        }
        else {
            Personage tmp = (Personage) s;
            return (tmp.name.equals(name) && force == tmp.force && mood == tmp.mood && tmp.type.equals(type));
        }
    }

    @Override
    public int hashCode(){
        final int power = 239;
        int hash = 0;
        for (int i = 0; i < name.length(); ++i) {
            hash = hash * power + (int)name.charAt(i);
        }

        for (int i = 0; i < type.length(); ++i) {
            hash = hash * power + (int)type.charAt(i);
        }


        if (mood == Mood.NORMAL) {
            hash *= 2;
        } else if (mood == Mood.FURY) {
            hash *= 3;
        } else if (mood == Mood.HAPPY) {
            hash *= 4;
        } else if (mood == Mood.SAD) {
            hash *= 5;
        }

        hash *= force * x * y * height;
        return hash;
    }

    @Override
    public String toString(){
        return type + " " + name ;
    }

    /**
     * Кто-то что-то почувствовал
     */
    public void feel(){
        System.out.print(name + " почувствовал, что ");
    }

    /**
     * Метод, сравнивает 2-х персонажей
     * @param p - персонаж с которым сравниваем
     * @return 0 - объекты равны, > 0 => (this > p), < 0 => (this < p)
     */
    @Override
    public int compareTo (Personage p){
        if (this.toString().compareTo(p.toString()) == 0){
            return this.height - p.height;
        } else {
            return this.toString().compareTo(p.toString());
        }
    }

    public boolean setMood(String s) {
        switch (s){
            case "Mood.NORMAL" : {}
            case "NORMAL" : {
                this.mood = Mood.NORMAL;
                break;
            }
            case "Mood.FURY" : {}
            case "FURY" : {
                this.mood = Mood.FURY;
                break;
            }
            case "Mood.HAPPY" : {}
            case "HAPPY" : {
                this.mood = Mood.HAPPY;
                break;
            }
            case "Mood.SAD" : {}
            case "SAD" : {
                this.mood = Mood.SAD;
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }
}
