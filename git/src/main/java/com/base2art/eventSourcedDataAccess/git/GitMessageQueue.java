package com.base2art.eventSourcedDataAccess.git;

import lombok.val;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GitMessageQueue {

    private final GitWorker worker;
    private final GitTrigger trigger;
    private final EnumMap<MessageType, Procedure> map;
    private final LinkedList<MessageType> messages = new LinkedList<>();
    private final AtomicBoolean isRunning = new AtomicBoolean();

    public GitMessageQueue(final GitWorker worker, final GitTrigger trigger) {
        this.worker = worker;
        this.trigger = trigger;

        this.map = new EnumMap<>(MessageType.class);
        this.map.put(MessageType.Commit, worker::commit);
        this.map.put(MessageType.Pull, worker::pull);
        this.map.put(MessageType.Push, worker::push);
    }

    public void addMessage(final MessageType messageType) {
        if (messages.size() > 0 && messageType == messages.get(0)) {
            return;
        }

        messages.addFirst(messageType);
        trigger.trigger(messageType);
    }

    public void pushMessageNow(final MessageType messageType) {

        messages.addLast(messageType);
        trigger.trigger(messageType);
    }

    public void run() {
        if (this.isRunning.get()) {
            return;
        }

        this.isRunning.set(true);
        try {
            this.processQueue();
        }
        finally {
            this.isRunning.set(false);
        }
    }

    private void processQueue() {
        if (this.messages.size() == 0) {
            return;
        }

        val last = this.messages.removeLast();

        if (worker.getConfig().isDebug()) {
            System.out.println("Performing a '" + last.name() + "'");
        }
        try {
            this.map.get(last).call();
        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace(System.out);
            System.out.println();
        }

        if (worker.getConfig().isDebug()) {
            System.out.println("Performed a '" + last.name() + "'");
        }
    }
}
