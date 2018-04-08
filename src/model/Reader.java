package model;


import java.util.Date;

public class Reader extends Personage {
   public Reader(String name) {
       this.type = "Читатель";
       this.name = name;
       this.force = 0;
       this.height = 158;
       this.dateCreate = new Date();
    }


}
