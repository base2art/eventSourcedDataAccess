package com.base2art.eventSourcedDataAccess.git;

public interface GitTrigger {
    void trigger(MessageType messageType);
}
