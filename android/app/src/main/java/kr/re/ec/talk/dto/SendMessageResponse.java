package kr.re.ec.talk.dto;

/**
 * POJO object for handling response about sending message to other.
 * @author Taehee Kim 2016-09-17
 */
public class SendMessageResponse extends ResponseBase {

    @Override
    public String toString() {
        return "SendMessageResponse [getSuccess()=" + getSuccess()
                + ", getMessage()=" + getMessage() + ", toString()="
                + super.toString() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + "]";
    }

}
