package webboards.client.ops.generic;

import webboards.client.ops.Operation;

public class OpponentConnected extends Operation {
	private static final long serialVersionUID = 1L;
	private String user;

	public OpponentConnected(String user) {
		this.user = user;
	}

	public OpponentConnected() {
	}

	@Override
	public String toString() {
		return user + " connected.";
	}

}
