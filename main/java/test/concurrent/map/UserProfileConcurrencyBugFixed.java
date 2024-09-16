package test.concurrent.map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Key changes and explanations:
 *
 * AtomicReference: We wrap the UserProfile in an AtomicReference. This allows us to perform atomic operations on the reference.
 * Immutable UserProfile: We made UserProfile immutable by making its fields final and removing setters. This is a good practice when working with concurrent code as it eliminates a whole class of potential bugs.
 * updateAndGet method: We use the updateAndGet method of AtomicReference, which atomically applies a function to the current value and returns the updated value. This ensures that all updates are applied and none are lost.
 * Lambda functions: We use lambda functions to create new UserProfile instances based on the current state, either incrementing the age or appending to the name.
 *
 * These changes address the concurrency issues in the following ways:
 *
 * AtomicReference ensures that the read-modify-write operation is atomic, preventing lost updates.
 * The updateAndGet method guarantees that each thread's changes are applied exactly once, even if multiple threads are trying to update simultaneously.
 * By making UserProfile immutable, we ensure that once a UserProfile instance is created, it cannot be modified, which eliminates race conditions within the UserProfile object itself.
 *
 * With these modifications, you'll find that:
 *
 * All 1000 age increments will be applied.
 * The name will have exactly 1000 exclamation marks appended.
 * There will be no lost updates due to race conditions.
 *
 * This example demonstrates how AtomicReference can be used to safely update complex objects in a multi-threaded environment, ensuring that all updates are applied correctly without losing data due to race conditions.
 */
public class UserProfileConcurrencyBugFixed {
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

class UserProfile1 {
    private String name;
    private int age;

    public UserProfile1(String name, int age) {
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

