package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.valueobject.PaymentStatus;
import com.food.ordering.system.order.service.dataaccess.outbox.payment.entity.PaymentOutboxEntity;
import com.food.ordering.system.order.service.dataaccess.outbox.payment.repository.PaymentOutboxJpaRepository;
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static com.food.ordering.system.saga.order.SagaConstants.ORDER_SAGA_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = OrderServiceApplication.class)
@Sql(value = {"classpath:sql/OrderPaymentSagaTestSetUp.sql"})
@Sql(value = {"classpath:sql/OrderPaymentSagaTestCleanUp.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderPaymentSagaTest {

    @Autowired
    private OrderPaymentSaga orderPaymentSaga;

    @Autowired
    private PaymentOutboxJpaRepository paymentOutboxJpaRepository;

    private final UUID SAGA_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afa");
    private final UUID ORDER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb17");
    private final UUID CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID PAYMENT_ID = UUID.randomUUID();
    private final BigDecimal PRICE = new BigDecimal("100");

    @Test
    @Disabled("The test is Disabled")
    void testDoublePayment() {
        orderPaymentSaga.process(getPaymentResponse());
        orderPaymentSaga.process(getPaymentResponse());
    }

    @Test
    @Disabled("The test is Disabled")
    void testDoublePaymentsWithThreads() throws InterruptedException {
        Thread thread1 = new Thread(() -> orderPaymentSaga.process(getPaymentResponse()));
        Thread thread2 = new Thread(() -> orderPaymentSaga.process(getPaymentResponse()));

        thread1.start();
        thread2.start();

        // join() method will block the calling thread which is the main thread that calls the method until
        // the threads whose join() method is called has completed.
        // So, by calling join() method for both threads, I will be sure that both threads will be executed
        // before the main thread exits this test method.
        thread1.join();
        thread2.join();

        assertPaymentOutbox();
    }

    @Test
    @Disabled("The test is Disabled")
    void testDoublePaymentsWithLatch() throws InterruptedException {

        // CountDownLatch is a synchronization construct that allows one or more threads to wait until
        // the set of operations being performed in other threads completes.
        CountDownLatch latch = new CountDownLatch(2);
        Thread thread1 = new Thread(() -> {
            try {
                orderPaymentSaga.process(getPaymentResponse());
            } catch (OptimisticLockingFailureException ex) {
                log.error("OptimisticLockingFailureException occurred for thread1");
            } finally {
                latch.countDown();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                orderPaymentSaga.process(getPaymentResponse());
            } catch (OptimisticLockingFailureException ex) {
                log.error("OptimisticLockingFailureException occurred for thread2");
            } finally {
                latch.countDown();
            }
        });

        thread1.start();
        thread2.start();

        // it will wait until both threads complete it
        latch.await();
        assertPaymentOutbox();
    }

    private void assertPaymentOutbox() {
        Optional<PaymentOutboxEntity> paymentOutboxEntity =
                paymentOutboxJpaRepository.findByTypeAndSagaIdAndSagaStatusIn(
                        ORDER_SAGA_NAME, SAGA_ID, List.of(SagaStatus.PROCESSING));

        assertTrue(paymentOutboxEntity.isPresent());
    }

    private PaymentResponse getPaymentResponse() {
        return PaymentResponse.builder()
                .id(UUID.randomUUID().toString())
                .sagaId(SAGA_ID.toString())
                .orderId(ORDER_ID.toString())
                .customerId(CUSTOMER_ID.toString())
                .paymentId(PAYMENT_ID.toString())
                .paymentStatus(PaymentStatus.COMPLETED)
                .price(PRICE)
                .createdAt(Instant.now())
                .failureMessages(new ArrayList<>())
                .build();
    }
}