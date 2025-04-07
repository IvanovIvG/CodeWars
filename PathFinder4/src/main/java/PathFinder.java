import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Ivanov
 **/
public class PathFinder {
    private static final Point position = new Point(0, 0);
    private static final Direction direction = new Direction(Side.Left);

    public static Point iAmHere(String path) {
        List<Command> commands = getCommandsFromPath(path);
        for (Command command : commands) {
            command.execute(position, direction);
        }
        return position;
    }

    private static List<Command> getCommandsFromPath(String path) {
        if(path.isEmpty()) return Collections.emptyList();
        List<Command> commands = new ArrayList<>();
        String[] stringCommands = path.split("(?<=\\D)(?=\\D)|(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
        for (String stringCommand : stringCommands) {
            Command command = getCommandFromString(stringCommand);
            commands.add(command);
        }
        return commands;
    }

    private static Command getCommandFromString(String stringCommand) {
        return switch (stringCommand) {
            case "r" -> new ClockWise();
            case "l" -> new CounterClockwise();
            case "R", "L" -> new Reverse();
            default -> new Move(Integer.parseInt(stringCommand));
        };
    }
}

class Direction {
    public Side getSide() {
        return side;
    }

    private Side side;

    public Direction(Side side) {
        this.side = side;
    }

    public void reverse() {
        switch (side){
            case Left -> side = Side.Right;
            case Right -> side = Side.Left;
            case Down -> side = Side.Up;
            case Up -> side = Side.Down;
        }
    }

    public void counterClockWise() {
        switch (side){
            case Left -> side = Side.Down;
            case Right -> side = Side.Up;
            case Down -> side = Side.Right;
            case Up -> side = Side.Left;
        }
    }

    public void clockWise() {
        switch (side){
            case Left -> side = Side.Up;
            case Right -> side = Side.Down;
            case Down -> side = Side.Left;
            case Up -> side = Side.Right;
        }
    }
}

enum Side{
    Up, Down, Left, Right
}


interface Command {
    void execute(Point position, Direction direction);
}

class Move implements Command {
    private final int moves;

    public Move(int moves) {
        this.moves = moves;
    }

    @Override
    public void execute(Point position, Direction direction) {
        switch (direction.getSide()) {
            case Up -> position.move(position.x, position.y + moves);
            case Down -> position.move(position.x, position.y - moves);
            case Left -> position.move(position.x - moves, position.y);
            case Right -> position.move(position.x + moves, position.y);
        }
    }
}

class ClockWise implements Command {

    @Override
    public void execute(Point position, Direction direction) {
        direction.clockWise();
    }
}

class CounterClockwise implements Command {

    @Override
    public void execute(Point position, Direction direction) {
        direction.counterClockWise();
    }
}

class Reverse implements Command {


    @Override
    public void execute(Point position, Direction direction) {
        direction.reverse();
    }
}

