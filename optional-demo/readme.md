# 关于java8 Optional

> 文档版本：v1.0版本

和C/C++不一样，java从一开始就尝试将指针彻底的包装起来，所有关于指针的操作都由底层的jvm完成，java程序员只需要知道引用对象和`null`就ok。

但是这个`null`确实也没少让程序员头疼，每天不遇到几个`NullPointException`(NPE)都感觉今天是不是不太正常。那如何彻底的从技术和思维上解决这个让人头疼的问题就很值的探讨。

## `null`的问题

java开发的同学都很清楚，实际的开发中很多场景都会遇到NPE：

- 隐晦的自动拆箱NPE
- 数据库查询结果为`null`导致的NPE
- 集合内部元素为null引起NPE
- `session`中获取数据为null导致NPE
- 级联调用NPE

说白了只要尝试调用实际为`null`的对象的属性或者方法都会导致NPE。

如下下面这段代码（示例1）：
```java
public class Demo1 {

    /**
     * 获取人对应的车的保险的名称
     * @param person
     * @return
     */
    public String getName(Person person) {
        return person.getCar().getInsurance().getName();
    }
}
```

其中数据model如下，后面也会复用（示例2）

```java
public class Person {

    private Car car;

    public Car getCar() {
        return car;
    }
}

public class Car {

    private Insurance insurance;

    public Insurance getInsurance() {
        return insurance;
    }
}

public class Insurance {

    private String name;

    public String getName() {
        return name;
    }
}
```

这段代码从业务功能上讨论其实没啥问题。但是实际情况是调用此代码的人不确定会扔什么样的数据进来，只要 `person`， `car`， `insurace` 三个bean中有任何一个为`null`，都会导致NPE。实际开发中，这种NPE场景也是最为常见和多发的。

## 防御式检查

如果避免上述操作导致NPE的问题，常见的做法如下，也叫做防御式检查：

```java
/**
 * 简单的防御式检查
 * @param person
 * @return
 */
public String getName2(Person person) {
    if (person != null) {
        Car car = person.getCar();
        if (car != null) {
            Insurance insurance = car.getInsurance();
            if (insurance != null) {
                return insurance.getName();
            }
        }
    }
    return "Unknown";
}
```

通过一层层的检查，直到完全确认`insurance`不为`null`时，才返回正确的`name`，否则返回`“unknown”`。但是这段代码在实际维护中其实会让人头疼，因为层层嵌套的金字塔式的代码看起来不美观，而且不容易理解。尝试改进如下：

```java
/**
 * 获取name3
 * @param person
 * @return
 */
public String getName3(Person person) {
    if (person == null) {
        return "Unknown";
    }
    Car car = person.getCar();
    if (car == null) {
        return "Unknown";
    }
    Insurance insurance = car.getInsurance();
    if (insurance == null) {
        return "Unknown";
    }
    return insurance.getName();
}
```

这段代码尝试做了一点点改进，将多层嵌套的大括号代码进行拆解，使得所有的逻辑都在一个层面，使用截断式的判断，只要检查到对象为`null`，就返回错误信息。稍微比上面的好一点，但是也会存在问题。多行重复的操作，很实容易写错，而且后期维护的时候，任何一处细小的改动都需要所有地方同步进行更新，否则就会出现预期之外的结果返回，也很麻烦。**最重要的是，也没有彻底解决`null`的问题，甚至为了处理`null`，引入的安全检查代码看起来比业务代码还多，稍微有点啰嗦。**

## 了解`Optional`

铺垫了这么多，其实就想说明一个问题，`null`很头疼，咋办？

java8中引入的新的类：`Optional`，可以帮助我们更好的去处理`null`。

![Optional 示例（引用自java8 实战）](https://upload-images.jianshu.io/upload_images/1156415-ba3fd76bed5bbb86.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

`Optional`的示例如图，简单来说就是当变量存在时，`Optional`类只是包装了一层，而当变量为`null`时，就建立了一个“空”的`Optional`对象，当然此时如果调用`optional.get() `方法，`Optional`还是会扔出NPE，为空时通常应该调用`Optional.empty() `。

![Optional.empty()](https://upload-images.jianshu.io/upload_images/1156415-112385f74ae27318.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

`empty() `其实是一个静态的工厂方法，返回一个“空”的`Optional`对象。看起来和调用空对象没什么区别，但是实际运行中却有着质的却别，`null`对象的调用会引起NPE，导致程序奔溃，而`empty()`就完全没问题。

Optional类结构如图：
![Optional类 结构](https://upload-images.jianshu.io/upload_images/1156415-5bb4e28c60e0f4ae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

简单说明一下基础方法，后面会对每一个方法和对应的思路详细进行说明和举例：

- `empty()` 返回“空”`Optional`对象，静态方法
- `of(T value)` 和 `constructor` 一样，创建`Optional`对象
- `ofNullable(T value)` 传入的参数为`null`时返回“空”`Optional`，否则返回包装好`value`的`Optional`对象 
- `isPresent()` 判断`Optional`包装的对象是否为空
- `get() `获取`Optional`包装的对象，包装的对象为`null`时产生NPE
- `orElse()` 为空时返回指定的参数，否则返回内部包装的对象
- `map()` 执行指定的“转换”方法，返回`null`时，可以包装为“空”的`Optional`对象
- `filter()` 基于`Optional`，对对象的值进行安全的检查和过滤
- `flatMap(T, Optional<U>)`  先简单来说吧，有点流模式的包装转换器，后面这个是重点需要关注的对象

首先划重点：如下的使用`Optional`本质上其实和文中刚开始提到的防御式检查没有任何区别，而且也是完全不推荐这么写：

```java
/**
 * 获取car
 * @param person
 * @return
 */
public Car getCar(Optional<Person> person) {
    
    if (person.isPresent()) {
        return person.get().getCar();
    }
    return null;
}
```
从业务层面去理解`Optional`应该是这样的：被`Optional`包装的对象在业务上允许为`null`，因此我们将其包装为`Optional`对象。因为在实际调用中如果`Optional`对象包装的对象为`null`，进行相关的调用就不会报NPE，更不需要做一系列的防御式检查（后文会举例），同时也可以精准的传达给调用者一个明确的信息，此对象允许为`null`。但是并不是所有的业务场景都适用，如果业务要求某个对象必须不为空且有值，此时就不应该使用`Optional`进行包装，如果产生NPE说明业务代码有问题或者数据有异常，应该在开发阶段就将其fix，而不是使用`Optional`掩盖为`null`的事实。

## 创建Optional对象

创建Optional对象的三种方式

```java
// 创建一个空的Optional
Optional<Car> optCar = Optional.empty();
// 依据非空的对象，创建一个Optional对象
Optional<Car> optCar1 = Optional.of(new Car());
// 依据允许为空的对象，创建一个Optional对象
Optional<Car> optCar2 = Optional.ofNullable(null);
```

## map()：从Optional对象中提取和转换值

`Optional`既然是一个包装对象，那从包装对象中提取对象或者对象的值就是一个常规操作。从前面的基础方法介绍中可知，`get()`方法是从`Optional`对象中提取包装对象的基础方法，但是`get`方法不安全，因为如果包装的对象为`null`，则会报空指针异常。这个时候`map`就显得很有用。

![map方法源码](https://upload-images.jianshu.io/upload_images/1156415-60bd248d41296384.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从源码可以看出，当`Optional`对象为“空”时，`map`什么也不做，返回一个空的`Optional`对象，否则执行传入的`Function`，得到结果并包装为`Optional`对象返回。

思考：至此，我们可以看出，`Optional`的`map()`操作其实和流中的`map`从模式上来说是一致的，甚至可以把`Optioanl`看做一个特殊的Stream（最多只能包含一个元素的流），`map`方法遍历流中的每一个元素，进行某种转换操作（转换操作即输入一个元素A，进行某些操作后返回B），基于`Optional`这个解释也是ok的。

简单的例子，从`insurance`中获取名称：
```java
public String getName2(Insurance insurance) {
    if (Objects.nonNull(insurance)) {
        return insurance.getName();
    }
    return null;
}
```

可以改写为：

```java
public Optional<String> getName(Insurance insurance) {
    Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
    return optInsurance.map(Insurance::getName);
}
```
关于`map`就讲这么多。在文章开始的时候，我们举了一个链式调用例子：
![获取保险的名称 demo](https://upload-images.jianshu.io/upload_images/1156415-39b818e7d069d578.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

现在我们学习了`map`，理所当然我们可以改写成如下方式：
![链式调用改造 demo](https://upload-images.jianshu.io/upload_images/1156415-5183d1679e06f5b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

上述代码在model为示例2的情况下是可以正常运行的，而且实现的很标准。但是这个地方有一个限制存在，如果model如下，这个段代码是没有办法通过编译了：

```java
public class Person {

    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }
}

public class Car {

    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}

public class Insurance {

    private Optional<String> name;

    public Insurance(Optional<String> name) {
        this.name = name;
    }

    public Insurance() {}

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }
}
```
因为第一次调用`getCar`之后，实际获取到的是`Optional<Car>`，再通过`map`包装后就演变成了`Optional<Optional<Car>>`, 显然这个对象是没有办法引用`getInsurance`方法的，因此就会报错。总结一下就是说，`map`在某些场景下可能会存在过度包装的情况。  当然了，这个问题也是可以解决的，这个时候就需要`flatMap()`方法了

![map 会存在过度包装的情况](https://upload-images.jianshu.io/upload_images/1156415-acd0ecd33f1cc40f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## flatMap()

上面我们讨论了，`map`在`Optional`嵌套的时候就略显乏力的，这个时候就需要`flatMap`来对嵌套的`Optional`来归一，使其转换为只有一层`Optional`的包装对象。

首先对上面遇到的问题进行改动，再对实现来进行说明：
```java
public Optional<String> getName2(Person person) {
    Optional<Person> optPerson = Optional.ofNullable(person);

    return optPerson.flatMap(Person::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getName);
}
```

`map`我们已经说明了，提取`Optional`包装的对象或者对象的属性，但是当转换方法直接返回`Optional`包装对象时就会因为过度包装而变得难以处理。`flatMap` 和`map`的不同之处在于，`flatMap`判断内部包装的对象不为空时，会将内部包装的对象传入`Function`中当做参数执行，且期望内部包装的对象为`Optional`包装对象。

![flatMap源码](https://upload-images.jianshu.io/upload_images/1156415-c7a4ecac8a79ae01.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

上述的解释有点直白，其实想表达的意思就是，flatMap会将多层的Optional对象合并为一个。当然，这里也可以类比流中的`flatMap`来理解。流中的`map`是一对一的映射，而`flatMap`就相当于一对多的映射。

流中`flatMap` 把 `input Stream` 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终` output` 的新Stream 里面已经没有 `List `了，都是直接的数字。比如：
```java
Stream<List<Integer>> inputStream = Stream.of(
 Arrays.asList(1),
 Arrays.asList(2, 3),
 Arrays.asList(4, 5, 6)
 );
Stream<Integer> outputStream = inputStream.
flatMap((childList) -> childList.stream());
```
同理，将`Optional`看做是一个元素数量最多为1的集合，这里多对一就体现在`Optional`的双层嵌套了。

> 注意，这里我将描述改为了双层嵌套，因为`flatMap`只支持双层，当超过双层的时候，flatMap也没办法了。请不要怀疑为什么没有可以处理超过两层嵌套的这样的方法提供，这种情况发生的时候，应该怀疑一下自己哪里是不是写冗余了，从而导致产生了畸形包装数据，而不是去尝试解构三层解构。
![一个错误的示范](https://upload-images.jianshu.io/upload_images/1156415-f9a597e379ca4a04.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Optional的默认行为

实际开发中，我们总是有这样一个需求，当`Optional`包装对象在一些连续调用和操作后如果有值则返回内部的值，否则返回指定的参数或者执行指定的方法。相当于一个`else`逻辑，但是写`if else`不是我们期望的，java8的宗旨就是简化代码逻辑，使其清晰可见。说句装逼的话就是：没有java8一句话实现不了的逻辑，哈哈哈哈哈哈，一个点不够就再多几个点上。

比如刚刚刚刚`flatMap`的例子我们想要获取的是`String`而不是`Optional<String>`的时候, 就可以指定一个默认的行为。

![image.png](https://upload-images.jianshu.io/upload_images/1156415-cbdf23caeafe5f91.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- `orElse(T value)`：  当`Optional`为空的时候，返回`value`,否则返回内部的包装对象
- `orElseGet(Supplier<? extends T> other)` ：相当于`orElse`的延迟调用版，当默认指定返回的对象是一个费时费力操作的时候，就应该考虑lasy初始化，以此来提高程序的性能。
- `orElseThrow(Supplier<? extends X> exceptionSupplier) throws X `当然如果有复杂的操作或者可能会产生异常的时候，调用`orElseThrow`则更合适。

## filter

读到这里你可能发现了，抛开知识层面的东西不谈，`Optional`一个很有特色的特点就是我上文说道的，没有一句话解决不了的逻辑，如果不行，就多点（`.XXXMethod()`）几次。这个也是java8的特色，链式调用。

当一个链式操作很长的时候，我们在很多场景下就不可避免的需要中间某处添加一些判断逻辑，来提前结束不符合业务条件的参数的相关操作，避免后续的业务操作发生错误或者进行无用的运算。这里`filter`就可以提供支持，来让我们在链式调用中添加一些谓词判断逻辑，满足我们的业务需求。

举个例子，我们之前一直在获取保险的名称。这个时候业务的同学告诉你实际业务的需求变为：之前的逻辑不要了，现在需要对名称为[**人寿保险**]的保险进行一些业务操作。在这种一改再改的情况下，虽然你心里妈卖批，但是你的代码还是得优雅的实现。

```java
public void doSomethingToRenshou(Person person) {
    Optional<Person> optPerson = Optional.ofNullable(person);

    optPerson.flatMap(Person::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getName)
            .filter(insuranceName -> "人寿保险".equals(insuranceName))
            .ifPresent(insurance -> {
                    //如果满足上面的谓词逻辑，则进行一些操作
                System.out.println("ok， i`m fine!");
            });

}
```

## 实际应用

上面都是针对某一个特定的场景来对`Optional`的方法或者写法来进行说明，但是实际应用中可能需要更加复杂的操作才能满足业务需求。这里的复杂指的并不是实现逻辑，而是说我们需要使用`Optional`中上述说的一种或者两种技巧来尽量优雅的实现更加的复杂的逻辑。

### 1. 合并两个Optional对象

**`Optional<U> do(Optional<T> a, Optional<K> b);`**

如果方法申明如上所示，即：方法入参上接收两个`Optional`对象，最后返回一个`Optional`包装对象，这个时候大多数人都是这么写的：

```java
/**
 * 错误的示范
 * @param person
 * @param car
 * @return
 */
public Optional<Insurance> doSomething(Optional<Person> person, Optional<Car> car) {
    if (person.isPresent() && car.isPresent()) {
        // 我也不知道拿到一个人和车能干啥
        // 假设这里进行了一了一些操作
        return this.findSomething(person.get() , car.get());
    }
    return Optional.empty();
}
```

经过我看书发现，正确的实现姿势应该是这样：

```java
/**
 * 正确的示范
 * @param person
 * @param car
 * @return
 */
public Optional<Insurance> doSomething2(Optional<Person> person, Optional<Car> car) {
    return person.flatMap(realPerson -> car.flatMap(realCar-> this.findSomething(realPerson, realCar)));
}
```

这里的实现逻辑第一眼看可能有点绕，但是却十分巧妙。大概做一下解释。

首先思考我们的业务逻辑，人和车 都存在的时候才需要进行一些操作，否则返回空的`Optional`对象。这里首先第一个是`person`对象调用`flatMap`方法，如果此时`person`是一个空的`Optional`对象，就什么都不做，直接返回空的Optional对象结束方法，否则将`person`作为参数，传入后续的操作中。这第一步逻辑很容易理解，也是`flatMap`的标准操作。然后方法接口中是一个`lambda`表达式，表达式主体是`car`调用`flatMap`方法，此时如果`car`也存在，则`person`和`car`都会作为参数传递给`findSomething`进行业务操作，否则这个`lamdba`表达式执行到这里也就结束了，即返回一个空的`Optional`对象。

### 2. 用Optional包装已经存在的代码

很多时候，我们需要接收别人代码。而这些代码可能不太符合我们现在提倡的用`Optional`包装可能为`null`的对象这一标准，甚至那些代码是基于java7或者更早的版本来实现的，这个时候就需要我们使用装饰模式来对这些老的代码进行包装，使之符合我们正在使用的实现规范，从而顺利的接入新的代码。

首先，明确一点尽量别写`if else`！！！

```java
public Optional<Object> wrap(Map<String, Object> map) {
        
    Optional<Object> newObject = Optional.ofNullable(map.get("key"));
    return newObject;
}
```

这里的例子比较简单，但是想表达观点很明确，如果你的代码需要兼容返回`null`的方法，那就使用兼容`null`的相关`Optional`方法来进行操作。如果业务上不允许产生`null`，那就使用强制非空的方法来进行包装。

### 3. 关于异常

`Optional`的使命是来负责解决`null`和NPE的，当实际生成中遇到了其他的异常逻辑的时候，我们可能需要抽象出`Util`方法来对常用的操作来进行包装，当产生其他业务异常时或者数据操作异常时，抓住异常，返回空的`Optional`，从而兼容异常情况，避免代码在实际运行中直接boom。

## 思考

贯穿全文我想表达几点：

1. 尽量的避免多重代码块的嵌套，简化代码操作上的逻辑。这一点在实际开发中是十分必要的。
2. Optional的出现是让我们可以顺畅的完成业务逻辑且可以优雅的避免掉大量的防御式检查。一开始可能难以接受这种操作，但是我觉得首先需要从思维上接受这种操作其实才是重要的，这样就可以将更多的注意力转移到实现业务逻辑上，而不是书写大量的防御式代码。
3. Optional 从设计上和Stream十分类似，很多时候我们可以类比着Stream来进行理解和学习，可以帮助我们更好的去理解Optional为什么这么设计。

好了，Optional的东西大概就这么多。如果有错误或者不足的地方希望大家可以指出。以上所有的代码都托管在github上：https://github.com/xiaopihai7256/OptionalDemo

## 参考
- [《java8 实战》](https://book.douban.com/subject/26772632/)
- [IBM文档：Java 8 中的 Streams API 详解](https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/)
