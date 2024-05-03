package edu.just.mashoora.repository;

import edu.just.mashoora.chatbot.ChatbotMessage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotRepository extends JpaRepository<ChatbotMessage, Long> {

    // Method to save a ChatbotMessage
    ChatbotMessage save(ChatbotMessage chatbotMessage);

    // Method to delete all chat history of a specific senderId
    @Transactional
    @Modifying
    @Query("DELETE FROM ChatbotMessage c WHERE c.senderId = ?1")
    void deleteBySenderId(Long senderId);

    // Method to get all chat history for a specific senderId
    List<ChatbotMessage> getQueriesBySenderId(Long senderId);
}
