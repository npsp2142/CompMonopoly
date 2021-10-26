package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.observer.PathObserver;

public class ViewPathCommand implements Command {
    private final PathObserver pathObserver;

    public ViewPathCommand(PathObserver pathObserver) {
        this.pathObserver = pathObserver;
    }


    @Override
    public void execute() {
        pathObserver.view();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
