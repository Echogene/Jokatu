package jokatu.stomp;

import ophelia.util.MapUtils;
import org.springframework.messaging.support.ErrorMessage;

public class SendErrorMessage extends ErrorMessage {
	public SendErrorMessage(Throwable e, String receipt) {
		super(e, MapUtils.createMap("send-receipt", receipt));
	}
}
