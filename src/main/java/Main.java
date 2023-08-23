import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static BlockingQueue<String> stringsA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> stringsB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> stringsC = new ArrayBlockingQueue<>(100);
    public static int maxA = 0;
    public static int maxB = 0;
    public static int maxC = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    stringsA.put(generateText("abc", 100000));
                    stringsB.put(generateText("abc", 100000));
                    stringsC.put(generateText("abc", 100000));

                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        Thread threadA = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    String text = stringsA.take();
                    int countA = 0;
                    for (String str : text.split("")) {
                        if (str.equals("a")) {
                            countA += 1;
                        }
                    }
                    if (countA > maxA) {
                        maxA = countA;
                    }
                    System.out.println("Колличество А - " + countA);
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    String text = stringsB.take();
                    int countB = 0;
                    for (String str : text.split("")) {
                        if (str.equals("b")) {
                            countB += 1;
                        }
                    }
                    if (countB > maxB) {
                        maxB = countB;
                    }
                    System.out.println("Колличество B - " + countB);
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        Thread threadC = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    String text = stringsC.take();
                    int countC = 0;
                    for (String str : text.split("")) {
                        if (str.equals("c")) {
                            countC += 1;
                        }
                    }
                    if (countC > maxC) {
                        maxC = countC;
                    }
                    System.out.println("Колличество C - " + countC);
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        System.out.println("Максимальное колличество букв a: " + maxA);
        System.out.println("Максимальное колличество букв b: " + maxB);
        System.out.println("Максимальное колличество букв c: " + maxC);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
