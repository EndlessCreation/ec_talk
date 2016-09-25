package kr.re.ec.talk.dto;

/**
 * for GCM. not using ResponseBase. 
 * refer to https://developers.google.com/cloud-messaging/downstream.
 * e.g. 
 * {
  "multicast_id": 6006061581990293276,
  "success": 1,
  "failure": 0,
  "canonical_ids": 0,
  "results": [
    {
      "message_id": "0:1474731698008404%5ba54a285ba54a28"
    }
  ]
}
 * @author Taehee Kim
 */
public final class GcmResponse {
	private String multicast_id;
	private int success;
	private int failure;
	private int canonical_ids;
	private Results results;
	class Results {
		private String message_id;

		public String getMessage_id() {
			return message_id;
		}

		public void setMessage_id(String message_id) {
			this.message_id = message_id;
		}

		@Override
		public String toString() {
			return "results [message_id=" + message_id + "]";
		}
		
		
	}
	
	public String getMulticast_id() {
		return multicast_id;
	}

	public void setMulticast_id(String multicast_id) {
		this.multicast_id = multicast_id;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public int getCanonical_ids() {
		return canonical_ids;
	}

	public void setCanonical_ids(int canonical_ids) {
		this.canonical_ids = canonical_ids;
	}

	@Override
	public String toString() {
		return "GcmResponse [multicast_id=" + multicast_id + ", success="
				+ success + ", failure=" + failure + ", canonical_ids="
				+ canonical_ids + ", results=" + results + "]";
	}

	
	
}
