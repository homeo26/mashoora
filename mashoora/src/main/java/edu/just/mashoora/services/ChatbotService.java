package edu.just.mashoora.services;

import edu.just.mashoora.chatbot.ChatbotMessage;
import edu.just.mashoora.payload.request.ChatbotQueryRequest;
import edu.just.mashoora.payload.response.ChatbotQueryResponse;

import java.util.List;

public interface ChatbotService {

    ChatbotQueryResponse sendQuery(Long senderId, ChatbotQueryRequest message);

    List<ChatbotMessage> getQueriesBySenderId(Long senderId);

    void saveChatbotHistory(Long senderId, ChatbotQueryRequest request, ChatbotQueryResponse response);

    void deleteBySenderId(Long senderId);

}
