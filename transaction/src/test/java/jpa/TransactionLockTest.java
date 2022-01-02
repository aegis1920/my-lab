package jpa;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import application.domain.Person;
import application.domain.PersonWithVersion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.PessimisticLockException;
import javax.persistence.RollbackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionLockTest {

    // 설정정보를 가져옴. 항상 테이블을 Create하는 속성 있음
    private final EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("jpa-practice");

    @BeforeEach
    void setUp() {
        // 단순히 DB 저장을 위한 작업
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new PersonWithVersion("bingbong", 3));
        entityManager.persist(new Person("bingbong", 3));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @DisplayName("Optimistic Locking - 버전이 서로 다를 때")
    @Test
    void optimisticLock_UpdateSameTime_ThrownException() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager2.getTransaction().begin();

        // 동시에 조회했으므로 이때 버전은 둘다 0
        PersonWithVersion Person = entityManager.find(PersonWithVersion.class, 1);
        PersonWithVersion member2 = entityManager2.find(PersonWithVersion.class, 1);

        /*
        update
            PersonWithVersion
        set
            age=?,
            name=?,
            version=?
        where
            id=?
            and version=?
        */
        Person.setName("hello");

        // 첫 번째 트랜잭션은 정상적으로 진행. member의 버전은 1. 위처럼 update할 때 버전을 증가시켜준다.
        entityManager.getTransaction().commit();
        entityManager.close();

        assertThat(Person.getVersion()).isEqualTo(1);

        member2.setName("world");

        // member2를 commit할 때 같은 버전인 1로 할 수 없으니 OptimisticLockException!
        assertThatThrownBy(() -> entityManager2.getTransaction().commit())
            .isInstanceOf(RollbackException.class)
            .getCause()
            .isInstanceOf(OptimisticLockException.class);

        entityManager2.close();
    }

    @DisplayName("Pessimistic Locking - 락이 걸린 엔티티를 수정할 때")
    @Test
    void pessimisticLock_UpdateSameTime_ThrownException() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager2.getTransaction().begin();

        /*
        select
        member0_.id as id1_0_0_,
            member0_.age as age2_0_0_,
        member0_.name as name3_0_0_
            from
        Person member0_
        where
        member0_.id=? for update
        */

        // 위처럼 for update를 붙여서 가져오기 때문에 Lock이 걸린다.
        entityManager.find(Person.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        Person member2 = entityManager2.find(Person.class, 1);

        // 이렇게 조회하는 건 상관없지만
        assertThat(member2.getName()).isEqualTo("bingbong");

        member2.setName("hello");

        // 수정하고 먼저 DB에 업데이트하려 할 때 PessimisticLockException이 일어난다.
        assertThatThrownBy(() -> entityManager2.getTransaction().commit())
            .isInstanceOf(RollbackException.class)
            .getCause()
            .isInstanceOf(PessimisticLockException.class);

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManager2.close();
    }
}
