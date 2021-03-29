package model;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5021489486048970357L;

	private static Integer AUTO_INCREMENT = 0; // valoare a clasei, pentru fiecare produs creat se genereaza un id unic

    private Integer id;
   // private String name;

    public MenuItem() {
        id = AUTO_INCREMENT++; // se seteaza id-ul produsului si se incrementeaza AUTO_INCREMENT
    }

    public static Integer getAutoIncrement() {
        return AUTO_INCREMENT;
    }

    public static void setAutoIncrement(Integer autoIncrement) {
        AUTO_INCREMENT = autoIncrement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract Double getPrice(); // metode care trebuie implementate de subclase
    public abstract String getName();

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                '}';
    }
}
