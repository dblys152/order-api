package com.ys.order.adapter.out.persistence;

import com.ys.order.adapter.config.DataJpaConfig;
import com.ys.order.adapter.out.persistence.fixture.SupportOrderEntityFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DataJpaConfig.class)
class OrderEntityRepositoryTest extends SupportOrderEntityFixture {

    @Autowired
    private OrderEntityRepository repository;

    @Test
    void save() {
        OrderEntity orderEntity = ORDER_ENTITY;

        OrderEntity actual = repository.save(orderEntity);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isEqualTo(orderEntity.getId()),
                () -> assertThat(actual.getOrderLineCollectionList())
                        .isEqualTo(orderEntity.getOrderLineCollectionList()),
                () -> assertThat(actual.getOrderPaymentInfoCollectionList())
                        .isEqualTo(orderEntity.getOrderPaymentInfoCollectionList())
        );
    }
}