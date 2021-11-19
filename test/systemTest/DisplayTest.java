package systemTest;

import com.company.Main;
import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.command.Command;
import com.company.model.component.Player;
import com.company.model.component.block.JailBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * System tests for CompMonopoly commands
 */
class DisplayTest {
    private CompMonopolyApplication compMonopolyApplication;

    @BeforeEach
    void setUp() {
        compMonopolyApplication = Main.createGameApplication(System.in, System.out);
        Player.NEED_PROMPT = false;
        JailBlock.IS_RANDOM = true;
    }

    public void executeCommands(List<String> commands) {
        for (String commandString : commands) {
            Command command = compMonopolyApplication.makeCommand(commandString);
            if (command.isValid()) {
                command.execute();
            }
        }
    }

    @Test
    void printCommandPrompt() {
        ArrayList<String> commands = new ArrayList<>();
        GameDisplay.printCommandPrompt();
        commands.add("start");
        executeCommands(commands);
        GameDisplay.printCommandPrompt();
    }

    @Test
    void printWarnMessage() {
        GameDisplay.warnMessage("Warning Test");
    }

}