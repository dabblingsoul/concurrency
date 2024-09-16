package test.concurrent.map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * In this example, we have a UserProfile class and two threads trying to update a
 * shared UserProfile instance concurrently. One thread increments the age,
 * while the other appends an exclamation mark to the name.
 * The concurrency bug here is that updates from one thread can be lost due to race conditions.
 * For example, if both threads read the current profile at the same time, create updated versions,
 * and then write back, one of the updates will be lost.
 *
 * AtomicReference is used when you need to ensure that read and write operations on an object reference are
 * atomic (indivisible) and visible across threads. It's particularly useful when you want to update an entire
 * object atomically, rather than just primitive values.
 *
 * Key uses of AtomicReference include:
 *
 * Ensuring atomic updates to object references
 * Implementing lock-free algorithms
 * Providing happens-before guarantees for visibility across threads
 * Enabling compare-and-set operations on object references
 */
public class UserProfileConcurrencyBug {
    private static UserProfile sharedProfile = new UserProfile("Alice", 30);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                UserProfile current = sharedProfile;
                UserProfile updated = new UserProfile(current.getName(), current.getAge() + 1);
                sharedProfile = updated;
            }
        });

        executor.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                UserProfile current = sharedProfile;
                UserProfile updated = new UserProfile(current.getName() + "!", current.getAge());
                sharedProfile = updated;
            }
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Final shared profile: " + sharedProfile);
    }
}

class UserProfile {
    private String name;

    private int age;

    public UserProfile(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "UserProfile{name='" + name + "', age=" + age + "}";
    }

}
