package kr.re.ec.talk.dto;

import java.util.List;

/*
* POJO object for handling request new messages.
* @author Taehee Kim 2016-09-17
*/
public class RequestNewMessagesResponse extends ResponseBase {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    //TODO: fix NPE about concurrency
    public String toString() {
        String str = "RequestNewMessagesResponse [messages.size()=";
        if(messages != null) {
            str += messages.size();
        } else {
            str += "null";
        }
//        for(Message e: messages) {
//            str += e.toString() + ", ";
//        }
        return str + "]";
    }
}
