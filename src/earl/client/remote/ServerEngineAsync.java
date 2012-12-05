package earl.client.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;

import earl.client.data.GameInfo;
import earl.client.op.Operation;

public interface ServerEngineAsync {
	void getState(String tableId, AsyncCallback<GameInfo> callback);
	void process(Operation op, AsyncCallback<String> abstractCallback);
}
