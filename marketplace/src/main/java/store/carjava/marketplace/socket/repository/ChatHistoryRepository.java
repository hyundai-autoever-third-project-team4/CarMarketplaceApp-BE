package store.carjava.marketplace.socket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import store.carjava.marketplace.socket.entity.ChatHistory;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    List<ChatHistory> findAllBySenderId(Long id);

    @Query("SELECT DISTINCT ch.topicId FROM ChatHistory ch ORDER BY ch.topicId")
    List<Long> findDistinctTopicIds();
}
