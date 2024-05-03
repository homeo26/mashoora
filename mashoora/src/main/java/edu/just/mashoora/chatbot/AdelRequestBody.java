package edu.just.mashoora.chatbot;

import lombok.Data;
import java.util.List;

@Data
public class AdelRequestBody {

    private String query;
    private List<PreviousConversation> previousConversations;

}