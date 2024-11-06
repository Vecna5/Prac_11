import java.io.*;

class Character implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int energyLevel;
    private int hungerLevel;
    private transient String status;

    public Character(String name, int energyLevel, int hungerLevel) {
        this.name = name;
        this.energyLevel = energyLevel;
        this.hungerLevel = hungerLevel;
        updateStatus();
    }

    public void eat(String food) {
        switch (food.toLowerCase()) {
            case "1000000 beer":
                energyLevel = Math.min(100, energyLevel + 10);
                hungerLevel = Math.max(0, hungerLevel - 10);
                break;
            case "Sandwich":
                energyLevel = Math.min(100, energyLevel + 25);
                hungerLevel = Math.max(0, hungerLevel - 25);
                break;
            default:
                System.out.println("Невідома їжа!");
                break;
        }
        updateStatus();
    }

    public void train(String exercise) {
        switch (exercise.toLowerCase()) {
            case "прес качат":
                energyLevel = Math.max(0, energyLevel - 30);
                hungerLevel = Math.min(100, hungerLevel + 20);
                break;
            case "анджуманя":
                energyLevel = Math.max(0, energyLevel - 15);
                hungerLevel = Math.min(100, hungerLevel + 10);
                break;
            default:
                System.out.println("Невідоме тренування!");
                break;
        }
        updateStatus();
    }

    public void rest(int hours) {
        energyLevel = Math.min(100, energyLevel + hours * 10);
        updateStatus();
    }

    private void updateStatus() {
        if (energyLevel > 70) {
            status = "енергійний";
        } else if (energyLevel < 30) {
            status = "втомлений";
        } else if (hungerLevel > 70) {
            status = "голодний";
        } else {
            status = "brainrot";
        }
    }

    @Override
    public String toString() {
        return "Ім'я: " + name + ", Рівень енергії: " + energyLevel + ", Рівень голоду: " + hungerLevel + ", Статус: " + status;
    }
}

public class Main {
    public static void main(String[] args) {
        Character character;

        File file = new File("character.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                character = (Character) in.readObject();
                System.out.println("Завантажений персонаж:");
                System.out.println(character);

                character.rest(2);
                character.eat("sandwich");
                character.train("прес качат");

                System.out.println("Після дій:");
                System.out.println(character);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Помилка під час завантаження персонажа: " + e.getMessage());
                return;
            }
        } else {
            character = new Character("Goku", 50, 50);
            System.out.println("Створений новий персонаж:");
            System.out.println(character);

            character.eat("sandwich");
            character.train("прес качат");
            character.eat("1000000 beer");
            character.rest(3);
            character.train("анджуманя");

            System.out.println("Після дій:");
            System.out.println(character);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(character);
            System.out.println("Персонаж збережений у файл.");
        } catch (IOException e) {
            System.out.println("Помилка під час збереження персонажа: " + e.getMessage());
        }
    }
}