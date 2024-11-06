
import java.io.*;

class Person implements Serializable {
    private String name;
    private int age;
    private String city;

    public Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public void savePerson(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(this);
            System.out.println("Об'єкт успішно серіалізовано у файл " + filePath);
        } catch (IOException e) {
            System.out.println("Помилка серіалізації: " + e.getMessage());
        }
    }

    public static Person loadPerson(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Person) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка десеріалізації: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Ім'я: " + name + ", Вік: " + age + ", Місто: " + city;
    }

    public static void main(String[] args) {
        Person person = new Person("Глєк", 45, "Самара");

        person.savePerson("person.ser");

        Person loadedPerson = Person.loadPerson("person.ser");

        if (loadedPerson != null) {
            System.out.println("Десеріалізований об'єкт: " + loadedPerson);
        }
    }
}
