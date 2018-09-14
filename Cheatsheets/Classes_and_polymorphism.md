# Overloading

Super class will always be more generic than sub class. We only create sub classes to fulfill specific needs.  
Method overloading is when there are multiple methods with the same name but different type signatures.  

```java
calculate(String): void
calculate(int): void
```


# Polymorphism
```
Polymorphism allows you to write code targeting superclass objects, use that code on subclass objects, and achieve possibly different results based on the actual class of the object.  
```

Allows you to do this
```java
public class Animal {
    protected String name;
    Animal(String name){
        this.name = name;
    }
    public String speak(){
        return name;
    }
}

public class Cat extends Animal {
    public Cat(String name) {
        super(name); // Must have this.
    }

    @Override
    public String speak() {
        return name + ": Meow";
    }
}


public class PetShelter2 {
    private static Animal[] animals = new Animal[]{
            new Cat("Mittens"),
            new Cat("Snowball"),
            new Dog("Spot")};
    // Cat and Dog inherit from the same class -> thus keeping code simpler

    // Iterate over the animals and print their voices
    public static void main(String[] args) {
        for (Animal a: animals){
            System.out.println(a.speak());
        }
    }
}
```


# Abstract classes 

In Java, an abstract method is declared with the keyword **abstract** and given without an implementation. If a class includes abstract methods, then the class itself must be declared abstract.  

- Abstract classes cannot have methods with body.  
- As one method of the class is **abstract**, the class itself is abstract.

```java
public abstract class Account {

    int number;

    void close(){
        //...
    }
}
```

```java
// Declaration  
Account a; // Acceptable
Account a = new Account(); // Will give compilation error

// Instead, we will need to declare a macro here
Account a = new Account() {
    @Override
    public String something () {
        // Do something
        return "test";
    };
};
```


# Interfaces
- An interface is a behavior specification i.e. a collection of method specifications . If a class implements the interface , it means the class is able to support the behaviors specified by the said interface.  

- An interface is shown similar to a class with an additional keyword ```<< interface >>``` . When a class implements an interface, it is shown similar to class inheritance except a dashed line is used instead of a solid line.

- Interfaces cannot be instantiated—they can only be implemented by classes. When an instantiable class implements an interface, indicated by the keyword implements, it provides a method body for each of the methods declared in the interface.

- An interface can be used as a type

- Java allows multiple inheritance among interfaces. Java interface can inherit multiple other interfaces. A Java class can implement multiple interfaces (and inherit from one class).

- Interfaces can also contain constants and static methods.


Sample: 
```java

public interface AnimalClassAddon {
    int CONSTANT = 10;

    // Method signature
    void movement (String input); // We do not declare functions with a body here either similar to abstract class

    // Static method
    static boolean isSpeedAllowed(int speed){
        return speed <= CONSTANT;
    }

}
```


