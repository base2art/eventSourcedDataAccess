package com.base2art.eventSourcedDataAccess.memory;

import lombok.Getter;

public class Archivable<T> {

    private boolean isArchived;

    @Getter
    private final T data;

    public Archivable(T data) {
        this.data = data;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        if (this.isArchived) {
            return;
        }

        this.isArchived = isArchived;
    }
}
