package claude;

public interface MarineListener {
    void onSynchronizationRequest(SynchronizationRequestEvent evt);
    void onDataTransferComplete(DataTransferEvent evt);
}