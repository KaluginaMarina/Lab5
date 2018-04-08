package model;

public interface Insects {
    int skillSwear = 0;
    /**
     *    Метод, который кидает клопа за шиворот
     *    @Personage p - противник
     *    @return true - в случае успеха, false - иначе
    */
    public boolean fallByCollar(Personage p);
    /**
     *    Метод, в котором клоп кусает за спину
     *    @Personage p - противник
     */
    public void biteBack(Personage p);
}
