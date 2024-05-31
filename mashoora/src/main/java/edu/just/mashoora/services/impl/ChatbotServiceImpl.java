package edu.just.mashoora.services.impl;

import edu.just.mashoora.chatbot.AdelRequestBody;
import edu.just.mashoora.chatbot.ChatbotMessage;
import edu.just.mashoora.chatbot.PreviousConversation;
import edu.just.mashoora.constants.Constants;
import edu.just.mashoora.payload.request.ChatbotQueryRequest;
import edu.just.mashoora.payload.response.ChatbotQueryResponse;
import edu.just.mashoora.repository.ChatbotRepository;
import edu.just.mashoora.services.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatbotServiceImpl implements ChatbotService {

    @Autowired
    ChatbotRepository chatbotRepository;

    private final String geminiApiUrl = Constants.ADEL_CHATBOT_API_URL;

    @Override
    public ChatbotQueryResponse sendQuery(Long senderId, ChatbotQueryRequest request) {

        List<ChatbotMessage> previousMessages = getQueriesBySenderId(senderId);

        // Construct the body for the API request
        AdelRequestBody requestBody = new AdelRequestBody();
        requestBody.setQuery(request.getQuery());
        requestBody.setPreviousConversations(getPreviousConversation(previousMessages));

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set the request entity, including headers and body
        HttpEntity<AdelRequestBody> entity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<ChatbotQueryResponse> responseEntity = new RestTemplate().exchange(geminiApiUrl, HttpMethod.POST, entity, ChatbotQueryResponse.class);

        // Extract the response body
        ChatbotQueryResponse response = new ChatbotQueryResponse();
        response.setBotResponse(responseEntity.getBody().getBotResponse());

        // Save the chatbot response
        saveChatbotHistory(senderId, request, response);

        return response;
    }

    private List<PreviousConversation> getPreviousConversation(List<ChatbotMessage> messages) {
        List<PreviousConversation> previousConversetionList = new ArrayList<>();

        for (ChatbotMessage message : messages) {
            PreviousConversation prevConv = new PreviousConversation();
            prevConv.setRole("user");
            prevConv.setParts(List.of(message.getQuery()));
            previousConversetionList.add(prevConv);

            if (message.getBotResponse() != null) {
                PreviousConversation botResponseConv = new PreviousConversation();
                botResponseConv.setRole("model");
                botResponseConv.setParts(List.of(message.getBotResponse()));
                previousConversetionList.add(botResponseConv);
            }
        }

        return previousConversetionList;
    }

    @Override
    public List<ChatbotMessage> getQueriesBySenderId(Long senderId) {
        return chatbotRepository.getQueriesBySenderId(senderId);
    }

    @Override
    public void deleteBySenderId(Long senderId) {
        chatbotRepository.deleteBySenderId(senderId);
    }

    @Override
    public void saveChatbotHistory(Long senderId, ChatbotQueryRequest request, ChatbotQueryResponse response) {


        ChatbotMessage message = new ChatbotMessage();
        message.setSenderId(senderId);
        message.setQuery(request.getQuery());
        message.setBotResponse(response.getBotResponse());

        chatbotRepository.save(message);
    }


}
