package test.concurrent.race_condition;

import java.nio.channels.SocketChannel;
import java.io.IOException;

/**
 * 9. Incorrect handling of interrupts in NIO operations
 *
 * This code clears the interrupt status without rethrowing an InterruptedException,
 * potentially causing interrupts to be lost. Preserve the interrupt status or throw an
 * InterruptedException.
 */
public class IncorrectNIOInterruptHandling {
    public static void readFromChannel(SocketChannel channel) throws IOException {
        try {
            // Read from channel
        } catch (Exception e) {
            if (Thread.interrupted()) {
                // Clear interrupt status and ignore
            } else {
                throw e;
            }
        }
    }
}