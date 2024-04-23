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
