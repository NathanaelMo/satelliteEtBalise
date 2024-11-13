package v3;

public interface MarineListener {
    void onSynchroRequest(SynchronizationRequestEvent evt);
    void echangeFini(DataTransferEvent evt);
}