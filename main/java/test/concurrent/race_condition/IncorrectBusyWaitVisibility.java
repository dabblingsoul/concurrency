package test.concurrent.race_condition;

/**
 * 3. Incorrect assumption about memory visibility in busy-wait loops:
 *
 * The busy-wait loop may never see the updated value of flag
 * due to lack of proper synchronization. Use volatile for the flag
 * or proper synchronization mechanisms.
 */
public class IncorrectBusyWaitVisibility {
    private boolean flag = false;

    public void waitForFlag() {
        while (!flag) {
            // Busy-wait
        }
    }

    public void setFlag() {
        flag = true;
    }
}
