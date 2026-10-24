# Notes

## Running the code

The project lives in `juice-tut/` and builds with Gradle (via the included wrapper, so no local Gradle install is needed).

Run the Guice example:

```
cd juice-tut && ./gradlew run
```

Run the plain (no-Guice) example:

```
cd juice-tut && ./gradlew runNoGuice
```

Run the tests:

```
cd juice-tut && ./gradlew test
```

## Project structure

### Package vs Project vs Module

| Construct | What it is | Declared via | Own build file / deps / output? | Example here |
|---|---|---|---|---|
| **Package** | Java language namespace (also governs package-private visibility) | `package x.y;` | No — no independent dependencies, no independent compiled output | `skbadani.audit`, `skbadani.usingguice` |
| **Project** (Gradle) / **Module** (Maven) | Build-tool unit, one level up from a package | `build.gradle` + `settings.gradle`'s `include` (Gradle), or `pom.xml`'s `<modules>` (Maven) | Yes — own deps, own compiled jar | `audit/` — its own `build.gradle`, its own `implementation 'aopalliance:aopalliance:1.0'`, its own `build/libs/audit.jar` |
| **Module** (JPMS, Java 9+) | A *third*, unrelated meaning — a JVM/language-level encapsulation boundary, not a build-tool concept | `module-info.java` (`requires`/`exports`) | N/A — different axis entirely | Not used in this project |

One project/module typically contains many packages, and Gradle calls the whole root + subprojects together a single "build" (Maven calls it a "multi-module build" — same idea, different vocabulary). Smallest to largest: **package** (a namespace within one project) → **project/module** (own build file, own deps, own jar) → **build** (the whole multi-project build, run via one `./gradlew` invocation).

### Folder names vs package names

Folder names need not match package names — matching them is just a *convention*, not something the compiler enforces when compiling a whole source set at once (which is what Gradle/Maven do).

Example: in the `audit` module, `Audited.java`, `AuditGuard.java`, and `AuditInterceptor.java` physically live under `src/main/java/sbadani/audit/`, but each still declares `package skbadani.audit;` — the folder was renamed from `skbadani` to `sbadani`, but the `package` statement inside the files was not touched. The compiler uses the `package` statement, not the folder, to decide where the `.class` files go, so they still end up at `build/classes/java/main/skbadani/audit/`, matching the *package name*, not the source folder.

Because all three files declare the same package (`skbadani.audit`), none of them need to import each other. That's the real rule: whether an import is required depends only on whether two files declare the same package string — it has nothing to do with folder structure, and nothing to do with which Gradle project/module they're in. If the folder were renamed back to `skbadani` to match convention, the package declarations wouldn't change at all, so the files would *still* not need imports — fixing a folder-to-package mismatch is purely cosmetic and doesn't affect import requirements either way.

Contrast with `AppModule.java` (package `skbadani.usingguice`), which *does* import `skbadani.audit.Audited` and `skbadani.audit.AuditInterceptor` — not because of any folder naming, but simply because it's declared in a genuinely different package.

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

### If you call `createInjector` twice:

It will create two separate instances of the objects you requested from Guice.
What if you want to tell Guice: `can you create only one object`?


### Introducing `Scopes`:

When you bind the class to the impl with a scope of singleton, as shown in this change,
the injector won't create the object a second time. It will just fetch the created object.

### Binding multiple impls with `MapBinder`:

`MapBinder` lets you bind several implementations of the same interface, keyed by a value (e.g. a `String`):

```
MapBinder<String, DrawShape> shapeBinder = MapBinder.newMapBinder(binder(), String.class, DrawShape.class);
shapeBinder.addBinding("square").to(DrawSquare.class);
```

This binding isn't local to the module that creates it — once the module is installed, `Map<String, DrawShape>` becomes an injectable just like any other binding, so any class can get it with plain `@Inject`:

```
@Inject
Map<String, DrawShape> shapes;
```

No `TypeLiteral` needed at the injection point — Guice resolves the generic type via reflection. (`TypeLiteral`/`Key.get(...)` is only needed when fetching it manually via `injector.getInstance(...)`.)

### `TypeLiteral`:

1. In `Map rawShapeMap = injector.getInstance(Key.get(new TypeLiteral<Map<String, DrawShape>>(){}));` — generics are erased at runtime, so a raw `Map.class` lookup can't distinguish `Map<String, DrawShape>` from any other `Map`. `TypeLiteral` captures the full generic signature (via an anonymous subclass Guice can inspect reflectively), and `Key.get(...)` turns that into a `Key` that `getInstance` accepts.

2. In `MapBinder.newMapBinder(...)` — only needed when the key or value type is itself generic, e.g. `Map<String, List<DrawShape>>`. `List.class` alone can't express the `<DrawShape>` part, so you'd pass `new TypeLiteral<List<DrawShape>>(){}` instead of `List.class`. Not needed here, since both `String` and `DrawShape` are plain, non-generic types.

All of these boil down to the same rule: reach for TypeLiteral whenever you need to name a parameterized type as a value (for a Key, a binding target, or a matcher), since a bare Class object can’t carry the type arguments.

### `Provider<T>`:

Instead of injecting `T` directly, you inject a `Provider<T>` and call `.get()` yourself when you actually need an instance. Three benefits:

1. **Lazy construction** — defer building an expensive object until it's actually needed.
2. **Multiple fresh instances** — call `.get()` more than once to get a new (unscoped) instance each time, instead of one `T` fixed at injection time.
3. **Breaking circular dependencies** — `DrawSquare` → `SquareRequest` → `Map<String, DrawShape>` → `DrawSquare` is a cycle Guice can't proxy through (`SquareRequest` is a concrete class, not an interface). Injecting `Provider<SquareRequest>` into `DrawSquare` instead of `SquareRequest` breaks the construction-order cycle, since the `Provider` can exist before a `SquareRequest` does.

### AOP (method interceptors):

Guice can wrap method calls with an AOP Alliance `MethodInterceptor`, bound via `bindInterceptor(classMatcher, methodMatcher, interceptor)`. The interceptor's `invoke(MethodInvocation)` runs around the real call — before/after logic, or even skipping `invocation.proceed()` entirely — without touching the method body or its callers. It works by proxying the class, so it only applies to methods Guice itself constructs (non-final class, non-private/final method).

Demo: `DrawSquare.draw()` is annotated `@Audited` (a marker annotation, `@Retention(RUNTIME)` + `@Target(METHOD)` so it's reflectively visible on just methods) and throws unless `AuditGuard`'s thread-local flag is `true`. Nothing sets that flag — until `AuditInterceptor` is bound in `AppModule`:

```
bindInterceptor(Matchers.any(), Matchers.annotatedWith(Audited.class), new AuditInterceptor());
```

`AuditInterceptor.invoke()` sets the flag, calls `proceed()`, resets it in a `finally`. `DrawSquare` and its callers never change — the behavior is composed in entirely from the module.