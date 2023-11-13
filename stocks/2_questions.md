# Question 1

In the assignment, you had to create a `MessageHandler` interface. Please answer the following two questions:

1. Describe the benefits of using this `MessageHandler` interface. (~50-100 words)
2. Instead of creating an implementation of `MessageHandler` that invokes a command handler, we could also pass the command handler to the client/server directly without the middle man of the `MessageHandler` implementation. What are the implications of this? (~50-100 words)

___

**Answer**:
1. The Benefits of using MessageHandler interface are: Firstly, Message Handler is used to organize the code and follow
design pattern as we extracted the "message handling" function out of the client/server class and put it into its own class. 
Secondly, as the functionality of message handling is taken out of the client/server class, we have a better control on how or what we 
want the handler to handle the message. Lastly, as the message handling functionality decouples from the rest of the code
it makes the programmer easier to test and debug the code.
2. One of the implications of having MessageHandler as a middle man instead of passing command handler directly to 
client/server is that it gives the code a more flexibility in the sense of handling the message. The program may have 
other implementation in the future, using middle men give space for the program to add more option is handling the 
incoming message. On top of that, it simplifies the job of each class which then makes us code easier, for example, 
the MessageHandler only have to deal with message and CommandHandler will deal with the commands.
___

# Question 2

One of your colleagues wrote the following class:

```java
public class RookieImplementation {

    private final Car car;

    public RookieImplementation(Car car) {
        this.car = car;
    }

    public void carEventFired(String carEvent) {
        if("steer.left".equals(carEvent)) {
            car.steerLeft();
        } else if("steer.right".equals(carEvent)) {
            car.steerRight();
        } else if("engine.start".equals(carEvent)) {
            car.startEngine();
        } else if("engine.stop".equals(carEvent)) {
            car.stopEngine();
        } else if("pedal.gas".equals(carEvent)) {
            car.accelerate();
        } else if("pedal.brake".equals(carEvent)) {
            car.brake();
        }
    }
}
```

This code makes you angry. Briefly describe why it makes you angry and provide the improved code below.

___

**Answer**:<br>This reason the mode makes me angry is because the colleague uses if-else to catch the inputs and
call car functions. According to Advanced-OOP lectures, that is not a good practice; what we can improve is to use command
pattern to decouple the command from the Car Class. Moreover, we can add a factory for future implementation, for instance,
in the case that there are more than one type of vehicles that uses different command's combination.

Improved code:

```java
import java.util.HashMap;

public class RookieImplementation {

	private final Car car;
    private final CommandHandler commandHandler;

	public RookieImplementation(Car car ,CommandHandler commandHandler) {
		this.car = car;
        this.commandHandler = commandHandler;
	}
	public void carEventFired(String carEvent) {
        if (carEvent != null) {
            commandHandler.execute(carEvent)
		}
	}
}

public class CommandHandler {
	private Map<String, Command> commandMap;
	public CommandHandler() {
		this.commandMap = new HashMap<>();
	}
    public String execute(String command) {
        if(commandMap.containsKey(command)) {
            commandMap.get(command).execute();
		}
	}
	public addCommand(String command, Command commandClass) {
		this.commandMap.put(command, commmandClass);
	}
}

public class CarCommandHandlerFactory() {
	//add command to the commandHandler
}
public interface Command {
	execute() {}
}
public class CarAbstractCommand implements Command {
	private Car car;
	public EngineCommand (Car car) {this.car = car;}
}
public class SteerRightCommand extends CarAbstractCommand {
	public execute() {super.car.steerRight();}
}
public class SteerLeftCommand extends CarAbstractCommand {
	public execute() {super.car.steerLeft();}
}
public class EngineStartCommand extends CarAbstractCommand {
	public execute() {super.car.startEngine();}
}
public class EngineStopCommand extends CarAbstractCommand {
	public execute() {super.car.stopEngine();}
}
public class PedalGasCommand extends CarAbstractCommand {
	public execute() {super.car.accelerate();}
}
public class PedalBrakeCommand extends CarAbstractCommand {
	public execute() {super.car.brake();}
}
```
___

# Question 3

You have the following exchange with a colleague:

> **Colleague**: "Hey, look at this! It's super handy. Pretty simple to write custom experiments."

```java
class Experiments {
    public static Model runExperimentA(DataTable dt) {
        CommandHandler commandSequence = new CleanDataTableCommand()
            .setNext(new RemoveCorrelatedColumnsCommand())
            .setNext(new TrainSVMCommand())

        Config config = new Options();
        config.set("broadcast", true);
        config.set("svmdatatable", dt);

        commandSequence.handle(config);

        return (Model) config.get("svmmodel");
    }

    public static Model runExperimentB() {
        CommandHandler commandSequence = new CleanDataTableCommand()
            .setNext(new TrainSGDCommand())

        Config config = new Options();
        config.set("broadcast", true);
        config.set("sgddatatable", dt);

        commandSequence.handle(config);

        return (Model) config.get("sgdmodel");
    }
}
```

> **Colleague**: "I could even create this method to train any of the models we have. Do you know how Jane did it?"

```java
class Processor {
    public static Model getModel(String algorithm, DataTable dt) {
        CommandHandler commandSequence = new TrainSVMCommand()
            .setNext(new TrainSDGCommand())
            .setNext(new TrainRFCommand())
            .setNext(new TrainNNCommand())

        Config config = new Options();
        config.set("broadcast", false);
        config.set(algorithm + "datatable", dt);

        commandSequence.handle(config);

        return (Model) config.get(algorithm + "model");
    }
}
```

> **You**: "Sure! She is using the command pattern. Easy indeed."
>
> **Colleague**: "Yeah. But look again. There is more; she uses another pattern on top of it. I wonder how it works."

1. What is this other pattern? What advantage does it provide to the solution? (~50-100 words)

2. You know the code for `CommandHandler` has to be a simple abstract class in this case, probably containing four methods:
- `CommandHandler setNext(CommandHandler next)` (implemented in `CommandHandler`),
- `void handle(Config config)` (implemented in `CommandHandler`),
- `abstract boolean canHandle(Config config)`,
- `abstract void execute(Config config)`.

Please provide a minimum working example of the `CommandHandler` abstract class.

___

**Answer**: 

1. The other pattern that is used on top of the command pattern is the Chain of Responsibility; where the CommandHandler
can set the next CommandHandler by using setNext(). This allows each CommandHandler to just have to handle their part
and pass to the next CommandHandler. Consequently, our program is more flexible as we can assign a particular sequence of
handlers using in different scenarios.

2.
```java
public class CommandHandler {
    private CommandHandler next;
    abstract  boolean canHandle(Config config) {}
	abstract void execute(Config config) {}
    
	public void handle(Config config) {
		if (canHandle(config)) {
			execute(config);
		} else if (next != null) {
			next.handle(config);
		}
	}
    
    public void setNext(CommandHandler next) {
        this.next = next;
	}
}

class CleanDataTableCommand extends CommandHandler {
	@Override
	 boolean canHandle(Config config) {
        if (dt != null) {
            return true;
        }
		return false;
	}

	@Override
	protected void execute(Config config) {
		dt.cleanData();
	}
}

```
___
