package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.observer.BlockVisitObserver;

public class ViewBlockVisitCountCommand implements Command {
    private final BlockVisitObserver blockVisitObserver;

    public ViewBlockVisitCountCommand(BlockVisitObserver blockVisitObserver) {
        this.blockVisitObserver = blockVisitObserver;
    }

    @Override
    public void execute() {
        blockVisitObserver.view();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
