package application.service;

import application.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 테스트는 설명 방식으로 진행되며 각각 Propagation을 다르게 준다.
 * 롤백을 판단하기 위해 로직은 간단하게 저장하는 것으로 진행한다.
 * <p>
 * Outer Transaction
 * A
 * Inner Transaction
 * B
 * C
 * D
 * E
 * (commit)
 */
@SpringBootTest
class TransactionPropagationTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private OuterService outerService;

    @AfterEach
    void afterEach() {
        personRepository.deleteAll();
    }

    @Test
    void 예외가_없다면_ABCDE가_저장된다() {
        outerService.saveABCDE();

        assertThat(personRepository.findAll()).hasSize(5);
    }

    @Test
    void B에_예외가_있다면_기본_Propagation이_REQUIRED로_Outer에도_예외가_전달되어_ABCDE_전부_롤백된다() {
        assertThatThrownBy(() -> outerService.saveABCDEWithBException())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Runtime 예외");

        assertThat(personRepository.findAll()).isEmpty();
    }

    @Test
    void E에_예외가_있다면_기본_Propagation이_REQUIRED로_Inner에도_예외가_전달되어_ABCDE_전부_롤백된다() {
        assertThatThrownBy(() -> outerService.saveABCDEWithEException())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Runtime 예외");

        assertThat(personRepository.findAll()).isEmpty();
    }

    @Test
    void E에_예외가_있으나_propagation이_REQUIRES_NEW로_따로_Commit된다_BC는_COMMIT_ADE는_롤백된다() {
        assertThatThrownBy(() -> outerService.saveABCDEWithEExceptionWithRequiresNew())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Runtime 예외");

        assertThat(personRepository.findAll()).hasSize(2);
    }

    /**
     * 중요!
     * Transaction이 걸려있는 InnerService에서 예외를 Throw했고 OuterService에서 try/catch를 통해 예외를 Catch한 상황
     * 일반적인 생각으로는 OuterService에서 예외를 Catch했으니 그대로 통과할거라 생각했으나 그렇지 않다.
     * ‘참여중인 트랜잭션이 실패시 전역롤백한다’라는 속성(globalRollbackOnParticipationFailure)이 있는데 이게 기본값이 true다.
     * 그래서 중간에 REQUIRED로 참여했던 트랜잭션에서 실패하면 전역 롤백이 된다.
     */
    @Test
    void Inner에서_예외가_발생한_걸_Outer에서_catch로_잡으면_Inner에서_잡지_않았기_때문에_글로벌롤백이_일어난다() {
                assertThatThrownBy(() -> outerService.saveABCDEWithTryCatchAndCException())
                .isInstanceOf(UnexpectedRollbackException.class)
                .hasMessageContaining("Transaction silently rolled back because it has been marked as rollback-only");
    }

    @Test
    void Inner에서_예외가_발생했으나_RequiresNew이기_때문에_B만_롤백되고_ADE는_commit된다() {
        outerService.saveABCDEWithTryCatchAndCExceptionWithRequiresNew();

        assertThat(personRepository.findAll()).hasSize(3);
    }
}
