package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.observer.BlockVisitObserver;

/**
 * When player wants to see how many times they pass the blocks
 */
public class ViewBlockVisitCountCommand implements Command {
    private final BlockVisitObserver blockVisitObserver;

    /**
     * @param blockVisitObserver the counter for the blocks the player passed
     */
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
