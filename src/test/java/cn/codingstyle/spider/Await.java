package cn.codingstyle.spider;

import org.assertj.core.api.Assertions;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class Await {
    private int actual;
    private int timeout;
    private TimeUnit unit;
    private Supplier<Integer> supplier;

    static public Await await() {
        return new Await();
    }

    public Await atMost(int timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
        return this;
    }

    public Await until(Supplier<Integer> supplier) {
        this.supplier = supplier;
        return this;
    }

    public void isEqualTo(int expected) {
        int timeWaited = 0;
        final long kTimeoutMillis = unit.toMillis(timeout);
        int tickTime = 1000;

        while (true) {
            Integer actual = supplier.get();
            if (actual == expected) {
                System.out.println("Assertion passed. timeWaited = " + timeWaited);
                return;
            }

            if (timeWaited >= kTimeoutMillis) {
                assertThat(actual).isEqualTo(expected);
            }

            try {
                Thread.sleep(tickTime);
                timeWaited += tickTime;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
