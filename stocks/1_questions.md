# Question 1

Suppose you are developing a similar (if not identical) project for a company. One teammate poses the following:

> "We do not have to worry about logging. The application is very small and tests should take care of any potential bugs. If we really need it, we can print some important data and just comment it out later."

Do you agree or disagree with the proposition? Please elaborate on your reason to agree or disagree. (~50-100 words)

___

**Answer**: I disagree with the idea of not logging when developing a program, even if the application is small, 
because logging can provide other programmers with crucial information when there are errors or bugs in the application. 
Moreover, logging can keep track of unusual behavior, enhancing the safety and security of the program. On top of that, 
programmers should always anticipate potential errors, which means we should not assume things work perfectly without 
paying an extra attention. Therefore, relying on tests and printing out important data are actions that should be 
strongly discouraged.

___

# Question 2

Suppose you have the following `LinkedList` implementation:

![LinkedList](images/LinkedList.png)

How could you modify the `LinkedList` class so that the value could be any different data type? Preferably, provide the code of the modified class in the answer.
___

**Answer**:

```java
import lombok.Getter;

public class LinkedList {
    private Node head;
    private int size = 0;
    private static final int HEADERVALUE = -9999999;

    public LinkedList() {
        head = Node(HEADERVALUE);
    }

    public void insert(Object value) {
        head.setNextNode(Node(value));
    }

    public void delete(Object value) {
        Node currentNode = this.head();
        //while (currentNode.next != null) {
        // ...
        //}
    }

    public Object getSize() {
        return this.size;
    }
}

@Getter
public class Node {
    Object value;
    Node next;
    public Node(Object value) {
        this.value = value;
    }
    public setNextNode(Node next) {
        this.next = next;
    }
}
```

___

# Question 3

How is Continuous Integration applied to (or enforced on) your assignment? (~30-100 words)

___

**Answer**: Continuous integration can be applied to our assignment as the team consists of more than one programmer and 
each individual works independently on the same project. Therefore, using test-driven development to build the 
project, having an agreement to frequently merge codes into the common repository and assigning work in parallel, 
and most importantly, having clear communication, yields productive continuous integration.

___

# Question 4

One of your colleagues wrote the following class:

```java
import java.util.*;

public class MyMenu {

    private Map<Integer, PlayerAction> actions;

    public MyMenu() {
        actions = new HashMap<>();
        actions.put(0, DoNothingAction());
        actions.put(1, LookAroundAction());
        actions.put(2, FightAction());
    }

    public void printMenuOptions(boolean isInCombat) {
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("What do you want to?");
        menuOptions.add("\t0) Do nothing");
        menuOptions.add("\t1) Look around");
        if(isInCombat) {
            menuOptions.add("\t2) Fight!");
        }
        menuOptions.forEach(System.out::println);
    }

    public void doOption() {
        int option = getNumber();
        if(actions.containsKey(option)) {
            actions.get(option).execute();
        }
    }

    public int getNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
```
List at least 2 things that you would improve, how it relates to test-driven development and why you would improve these things. Provide the improved code below.

___

**Answer**:

- For each action, they have a common field here, which is the description of the action adding to the menuOptions.
  Personally, I would like to create a super class that contains fields like that so that programmers would not have to 
  hard-code the description every time, which may lead to bugs. Likewise, during test-driven development, we may 
  encounter the scenarios where classes, fields, or methods are overlapped. Depending on the situation, such as creating 
  an interface or superclass would enhance the program in terms of coherency and more.

- For doOption(), if the input isn't one of the options, the method will terminate. Personally, I would like the program
  to continue listening for inputs until it's in one of the options. Similarly, during test-driven development, we have to
  think of scenarios in which the program will receive unusual inputs.

Improved code:

```java
import java.util.*;

public class MyMenu {

    private Map<Integer, PlayerAction> actions;

    public MyMenu() {
        actions = new HashMap<>();
        actions.put(0, DoNothingAction());
        actions.put(1, LookAroundAction());
        actions.put(2, FightAction());
    }

    public void printMenuOptions(boolean isInCombat) {
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("What do you want to?");
        menuOptions.add(DoNothingAction.getDescription());
        menuOptions.add(LookAroundAction.getDescription());
        if(isInCombat) {
            menuOptions.add(FightAction.getDescription());
        }
        menuOptions.forEach(System.out::println);
    }

    public void doOption() {
        int option = getNumber();
        if(actions.containsKey(option)) {
            actions.get(option).execute();
        } else {
            System.out.println("Please enter the correct input...");
            doOption();
        }
    }

    public int getNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
```
___