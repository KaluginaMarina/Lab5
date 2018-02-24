public class Flea implements Insects{
    String name = "Клоп";
    String type = "Насекомое";
    int force = 1;
    double x = 0;
    double y = 0;
    double z = 0;
    int skillSwear = 0;

    Flea(double x, double y, double z) {
        String name = "Клоп";
        String type = "Насекомое";
        int force = 9;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean fallByCollar(Personage p) {
        if (this.x == p.x && this.y == p.y && this.z >= p.height) {
            System.out.println(this.name + " провалился за шиворот.");
            this.z = 6 * p.height / 7;
            return true;
        }
        else {
            System.out.println(this.name + " попытался запрыгнуть " + p.name + " за шиворот, но у него не получилось.");
            this.z = 0;
            return false;
        }
    }

    @Override
    public void biteBack(Personage p) {
        p.force--;
        System.out.println(this.name + " принялся кусать спину.");
    }

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
            Moonlighter tmp = (Moonlighter) s;
            return (tmp.name.equals(this.name)  && this.force == tmp.force && this.type.equals(tmp.type));
        }
    }

    @Override
    public int hashCode(){
        final int power = 239;
        int hash = 0;
        for (int i = 0; i < name.length(); ++i){
            hash = hash * power + (int)name.charAt(i);
        }

        for (int i = 0; i < type.length(); ++i){
            hash = hash * power + (int)type.charAt(i);
        }

        hash *= force * x * y * z;
        return hash;
    }

    @Override
    public String toString(){
        return this.type + " " + this.name;
    }
}
