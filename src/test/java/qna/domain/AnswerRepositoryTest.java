package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.QuestionTest.Q1;

@DataJpaTest
class AnswerRepositoryTest {

    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Autowired
    private AnswerRepository answerRepository;

    @DisplayName("Answer 저장")
    @Test
    void save() {
        final Answer actual = answerRepository.save(A1);

        assertThat(actual.getId()).isNotNull();
    }

    @DisplayName("Answer 전체 조회")
    @Test
    void findAll() {
        final Answer answer1 = answerRepository.save(A1);
        final Answer answer2 = answerRepository.save(A2);

        List<Answer> answers = answerRepository.findAll();

        assertThat(answers).hasSize(2);
        assertThat(answers).contains(answer1, answer2);
    }

    @DisplayName("삭제되지 않은 Answer id로 조회")
    @Test
    void findByIdAndDeletedFalse() {
        final Answer expected = answerRepository.save(A1);

        Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(expected.getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
        assertThat(actual.get().isDeleted()).isFalse();
    }

    @DisplayName("Question id로 삭제되지않은 Answer 리스트 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        final Answer answer1 = answerRepository.save(A1);
        final Answer answer2 = answerRepository.save(A2);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(Q1.getId());

        assertThat(answers).hasSize(2);
        assertThat(answers).contains(answer1, answer2);
    }

}