package systemTest;

import com.company.Main;
import com.company.model.CompMonopolyApplication;
import com.company.model.command.Command;
import com.company.model.component.Player;
import com.company.model.component.block.JailBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * System tests for CompMonopoly commands
 */
class CommandTest {
    CompMonopolyApplication compMonopolyApplication;

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
    void unknownCommand() {
        Command command = compMonopolyApplication.makeCommand("someCommand");
        assertNull(command);
    }

    @Test
    void startCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        executeCommands(commands);
    }

    @Test
    void startCommand2() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start 2 Amy Ben");
        executeCommands(commands);
    }


    @Test
    void loadCommand() {
        ArrayList<String> commands = new ArrayList<>();

        commands.add("start");
        commands.add("yes");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("save");

        commands.add("load");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("save saveSlot.txt");
        commands.add("load saveSlot.txt");
        executeCommands(commands);
    }

    @Test
    void quitCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("quit");
        executeCommands(commands);
    }

    @Test
    void quitCommand2() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("quit");
        executeCommands(commands);
    }

    @Test
    void saveCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("yes");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("save saveSlotTwo.txt");
        executeCommands(commands);
    }

    @Test
    void pathCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("path");
        executeCommands(commands);
    }

    @Test
    void rollCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("roll");
        executeCommands(commands);
    }

    @Test
    void yesCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("y");
        executeCommands(commands);
    }

    @Test
    void noCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("n");
        executeCommands(commands);
    }

    @Test
    void helpCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("help");
        executeCommands(commands);
    }

    @Test
    void viewBlockViewCountCommand() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("bvc");
        executeCommands(commands);
    }


    @Test
    void destinyCommand() {
        ArrayList<String> commands = new ArrayList<>();
        Player.NEED_PROMPT = false;

        commands.add("start");
        commands.add("yes");
        commands.add("roll");
        commands.add("yes");
        commands.add("roll");
        commands.add("yes");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");

        commands.add("destiny");
        executeCommands(commands);
    }

    @Test
    void viewMoneyCommand() {
        ArrayList<String> commands = new ArrayList<>();
        Player.NEED_PROMPT = false;
        commands.add("start");
        commands.add("yes");
        commands.add("roll");
        commands.add("yes");
        commands.add("roll");
        commands.add("yes");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");

        commands.add("money");
        commands.add("m");
        executeCommands(commands);
    }

    @Test
    void viewBoardCommand() {
        ArrayList<String> commands = new ArrayList<>();
        Player.NEED_PROMPT = false;
        commands.add("start");
        commands.add("roll");
        commands.add("yes");
        commands.add("roll");
        commands.add("yes");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");

        commands.add("board");
        commands.add("b");
        executeCommands(commands);
    }

    @Test
    void viewPlayerProfileCommand() {
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<Player> players = compMonopolyApplication.getCommandFactory().getGameSystem().getPlayers();
        for (Player player : players) {
            player.setResponse(Player.Response.YES);
        }
        commands.add("start");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");
        commands.add("roll");

        commands.add("player");
        commands.add("self");
        commands.add("profile");
        executeCommands(commands);
    }

    @Test
    void cheatLoseMoneyCommand() {
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<Player> players = compMonopolyApplication.getCommandFactory().getGameSystem().getPlayers();
        for (Player player : players) {
            player.setResponse(Player.Response.YES);
        }
        commands.add("start");
        commands.add("cheat -lm");
        commands.add("cheat -lm 15000");
        executeCommands(commands);
    }

    @Test
    void cheatTeleportCommand() {
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<Player> players = compMonopolyApplication.getCommandFactory().getGameSystem().getPlayers();
        for (Player player : players) {
            player.setResponse(Player.Response.YES);
        }
        commands.add("start");
        commands.add("cheat -t");
        commands.add("cheat -t Some Block");
        commands.add("cheat -t Wan Chai");
        executeCommands(commands);
    }
}