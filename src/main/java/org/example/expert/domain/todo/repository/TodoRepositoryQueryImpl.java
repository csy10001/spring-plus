package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    // Qtodo, Quser를 자동 생성할려면... cmd에 gradlew 재빌딩해야하더군요 ㄷㄷ;; 그것도 모르고 고치는데 한세월 걸렸습니다 하핳...
    // findByIdWithUser을 jpa쿼리문에서 QueryDSL로 바꿨습니다.
    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        var result = jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
