public abstract class Personage {
    String name = "NoName";
    String type = "Персонаж";
    double x = 0;
    double y = 0;
    int skillSwear = 10;
    int force;
    int height;
    Mood mood = Mood.NORMAL;

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
}
