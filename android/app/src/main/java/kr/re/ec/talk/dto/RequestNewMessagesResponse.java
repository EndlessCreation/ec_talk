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
    public String toString() {
        String str = "RequestNewMessagesResponse [messages.size()=" + messages.size();
//        for(Message e: messages) { //TODO: NPE about concurrency
//            str += e.toString() + ", ";
//        }
        return str + "]";
    }
}
