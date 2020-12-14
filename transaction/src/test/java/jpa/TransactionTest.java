package jpa;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.MemberWithVersion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    // 설정정보를 가져옴. 항상 테이블을 Create하는 속성 있음
    private final EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("jpa-practice");

    @BeforeEach
    void setUp() {
        // 단순히 DB 저장을 위한 작업
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new MemberWithVersion("bingbong", 3L));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @DisplayName("Optimistic Locking - 동시에 조회 및 수정했을 때")
    @Test
    void optimisticLock_UpdateSameTime_ThrownException() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager2.getTransaction().begin();

        // 동시에 조회했으므로 이때 버전은 둘다 0
        MemberWithVersion member = entityManager.find(MemberWithVersion.class, 1L);
        MemberWithVersion member2 = entityManager2.find(MemberWithVersion.class, 1L);

        member.setName("hello");

        // 첫 번째 트랜잭션은 정상적으로 진행. member의 버전은 1
        entityManager.getTransaction().commit();
        entityManager.close();

        assertThat(member.getVersion()).isEqualTo(1);

        member2.setName("world");

        // member2를 commit할 때 같은 버전인 1로 할 수 없으니 OptimisticLockException!
        assertThatThrownBy(() -> entityManager2.getTransaction().commit())
            .isInstanceOf(RollbackException.class)
            .getCause()
            .isInstanceOf(OptimisticLockException.class);

        entityManager2.close();
    }
}
