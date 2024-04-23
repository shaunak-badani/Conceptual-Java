# Notes

### Using Guice:


Let's say Object B depends on instantiating a class A.
If I say:

```
Guice.createInjector(new AppModule()).getInstance(B.class)
```

I need to put an inject annotation on the constuctor of class B, telling guice to inject the objects that you need to instantiate these objects yourself.

```
@Inject
B(A objectA)
{...}
```

This will inject object A into constructor of class B.

### How does Guice build objects?

When, say `TypeA` is requested, it does two things:

- Consults the binding to resolve the concrete type.
- Build object of that type.

If `TypeA` is an interface, then it must be bound to a concrete implementation in the module:

```
bind(TypeA.class).to(SomeConcreteImplOfTypeA.class)
```

If `TypeA` is a concrete impl, then,
    Case 1:
        No bindings
    Case 2:
        `bind(TypeA.class).to(SubClassOfTypeA.class)`

    - In Case 2, an object using the constructor bound in the module (`SubClassOfTypeA.class`) is invoked, and an object of that class is created.


### How to add constant dependencies?

We can bind String and Integer values as well.

Say the constructor is as so:

```
@Inject
public DrawSquare(String color, Integer edge)
{
    super();
    this.color = color;
    this.edge = edge;
}
```

In our module, we can bind these values:

```
bind(String.class).toInstance("Red");
bind(Integer.class).toInstance(40);
```

So when an object of `DrawSquare` is instantiated, 
```
color = "Red"
edge = 40
```

However, this implies that Guice will inject these values IN ALL CONSTRUCTORS, not just in `DrawSquare`. So if some other class has a String value, that would also get bound to "Red".

But we don't want that.

### How do we solve the above problem?

We annotate the parameters, as so:

```
@Inject
public DrawSquare(@ColorValue String color, @EdgeValue Integer edge)
{...}
```

`ColorValue` and `EdgeValue` are annotations, defined as shown with these set of changes.

Now, we bind `ColorValue` in our module to a value of `Red`, and `EdgeValue` to 40 in our module:

```
bind(String.class).annotatedWith(ColorValue.class).toInstance("Red");
bind(Integer.class).annotatedWith(EdgeValue.class).toInstance(40);
```