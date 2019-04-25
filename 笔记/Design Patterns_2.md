# 设计模式（二）

## 六、抽象工厂模式

### 6.1 定义

为创建一组相关或者是相互依赖的对象提供一个接口，而不需要指定他们的具体类。

### 6.2 使用场景

一个对象族拥有相同的约束的时候可以使用抽象工厂模式，比如说，在android和ios下都有短信软件和拨号软件，两者都属于softerware软件的范畴，但是，因为他们的操作平台不同其实现逻辑必然不同，这个时候可以用抽象工厂方法来生产相关软件。

可以在`/AbstractFacotryPattern`文件夹中找到代码示范。

### 6.3 抽象工厂模式与工厂模式的区别

#### 工厂模式

工厂模式是说鼠标生产厂是个父类，有生产鼠标这个接口。

戴尔鼠标工厂、惠普鼠标工厂继承它，可以分别生产戴尔鼠标和惠普鼠标。

![工厂模式](/Users/moanbigking/乱七八糟的东西/学习设计模式/思维导图/工厂模式.jpg)

#### 抽象工厂模式

抽象工厂模式是说这个工厂不仅生产鼠标，而且生产键盘。也就是父类有生产鼠标、生产键盘两个接口。

![抽象工厂模式](/Users/moanbigking/乱七八糟的东西/学习设计模式/思维导图/抽象工厂模式.jpg)

### 6.4 源码中的抽象工厂模式

以framework的角度来看Activity和Service都可以看作一个具体的工厂，这样看来算是一个抽象工厂模式的雏形，更确切的例子在Android底层对MediaPlayer的创建。

具体涉及到底层C++的内容，我还不能够掌握（……）。

MediaPlayerFactory是Android底层为创建不同MediaPlayer而定义的一个类，其中调用函数创建不同的MediaPlayer，而每一种具体的MediaPlayer最终都会调用MediaPlayerFactory的registerFactory_1函数来将它注册到MediaPlayerFactory中。

需要注意的是，MediaPlayerFactory只是用来管理MediaPlayer的类，对于每一种具体的MediaPlayer实际上由一个具体的Factory类来创造。比如说，StagefightPlayerFactory创建StagefightPlayer，NuPlayerFactory创建NuPlayer，SonivoxPlayerFactory创建SonivoxPlayer，等等。

### 6.5 总结

- 在实际开发中抽象工厂模式的应用并不多，因为很少出现多个产品种类的情况，大部分情况下我们使用工厂模式就可以解决问题。
- 其优点在于分离接口与实现，客户端不知道具体的实现是谁，这是一个典型的面相接口编程，在切换类型时灵活、容易。
- 其缺点在于会产生非常庞大的代码量，并且不易于拓展，若想增添一个新的功能，那么所有具体类都需要做修改。

## 七、策略模式

## 八、状态模式

## 九、责任链模式

## 十、解释器模式

