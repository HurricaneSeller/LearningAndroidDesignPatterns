# 设计模式

对于《Android源码分析设计模式应用与实战》的笔记。

~~适用人群我自己~~

## 一、面向对象的六大原则

### 1. 单一职责原则

单一职责原则（Single Responsiblity Principle）简称**SRP**，其定义是：**就一个类而言，它应该仅有一个引起它变化的原因**。简单来说，一个类中应该是**一组相关性很高的函数、数据的封装**。

两个完全不一样的功能不应该放在一个类中。当然呢，`util`（帮助）类除外。

### 2. 开闭原则

开闭原则（Open Close Principle）简称**OCP**，其定义是：**软件中的对象（类、模块、函数等）应该对于扩展是开放的，但对于修改是封闭的**。

若是修改原有代码容易造成意想不到的错误，所以在理想情况下我们希望采用继承原类的方法来升级维护。以一个简单的图片缓存器为例：

![image-20190422164109851](/Users/moanbigking/Library/Application Support/typora-user-images/image-20190422164109851.png)

用户可通过实现ImageCache接口自定义他需要的ImageLoader，通过ImageLoader中暴露的一个setImageLoader方法实现注入。

### 3. 里氏替换原则

里氏替换原则（Liskov Substution Principle）简称**LSP**，拥有两种定义。定义一：**若对每一个O1: S都有O2: T，使得以T定义的所有程序P在所有的对象O1替换为O2时，程序P的行为没有变化，那么S是T的子类型**；定义一或许难以理解，所以给出相对好理解的定义二：**所有引用基类的地方都必须能透明的使用其子类的对象**，通俗的讲，就是能使用父类的地方必须能够使用子类并且不出现任何错误。也就是说，**抽象**。

里氏替换原则是基于继承和多态两大原则提出的。

``` kotlin
abstract class SampleView{
    abstract fun draw()
    fun measure(width: Int, height: Int) {
        //测量的代码放在这里，若是不希望子类更改这里的代码可以写成final修饰
    }
}
class SampleWindow {
    fun draw(child: SampleView) {
        child.draw()
    }
}
class SampleButton : SampleView() {
    override fun draw() {
        //具体的实现逻辑
    }
}
//子类通过覆写SampleView的draw方法来实现其独特的绘制方法。任何继承自SampleView的子类都可以调用show方法。
```

里氏替换原则的优点和缺点都十分明显，优点：

- 代码重用，减少创建类的成本，每个子类都拥有父类的方法和属性。
- 子类与父类基本相似，但又与父类有所区别。
- 提高代码的可扩展性。

缺点：

- 继承是侵入性的，只要继承就必须拥有父类的所有属性和方法。
- 可能造成子类代码冗余、灵活性降低，因为子类必须具有子类的属性和方法。

### 4. 依赖倒置原则

依赖倒置原则（Dependence Inversion Principle）简称**DIP**，指代一种特定的解藕形式，使高层次模块不依赖于低层次模块的实现细节。通俗的说，依赖倒置原则有以下几个关键点：

- 高层模块不应该依赖低层模块，两者都应该依赖其抽象。
- 抽象不应该依赖细节。
- 细节应该依赖抽象。

在java语言中，抽象就是指抽象类或者接口类，两者都不能直接被实例化。由此引申出概念：细节就是实现类，即实现接口或继承抽象而产生的类就是细节，其特点就是可以直接被实例化；高层模块就是调用端，低层模块就是具体实现类。依赖倒置原则在java语言中的具体表现为：**模块间的依赖通过抽象发生，实现类之间不发生直接的依赖关系，其依赖关系是通过接口或抽象类产生的**。简而言之，面向抽象（接口或者抽象类）编程。

### 5. 接口隔离原则

接口隔离原则（InterfaceSegregation Principle）简称**ISP**，其定义为：**客户不应该依赖其不需要的接口（类间的依赖关系应该建立在最小的接口上）**。接口隔离原则将庞大、臃肿的接口分割成更多更小的接口，其目的是使系统解藕，更容易重构、改善和部署。

``` kotlin
class IOSample {
    fun put(url: String, bitmap: Bitmap) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(url + CACHE_DIE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {
        private val CACHE_DIE = "CACHE_DIR"
    }
}
//可以看到，这段代码的可读性非常差，各种try...catch嵌套的都是些简单的代码，并且如此之多的大括号将会轻易的导致错误代码的产生，那么解决方法呢？
```

`FileOutputStream`是一个实现了`Closeable`接口的类，而实现`Closeable`接口的类有一百多个，这就意味着每当用到这样的类的时候你都要写出这样魔幻的代码，这显然是不符合常理的。

``` kotlin
class CloseUtils {
    companion object {
        fun closeQuietly(closeable: Closeable?) {
            if (null != closeable) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
//将IOASample的finally中的代码替换为CloseUtils.closeQuietly(fileOutputStream)就好啦！
//锵锵！解决方法就是这样啦！
```

并且我们可以看到，`CloseUtils`中的`closeQuietlt`方法是基于`Closeable`抽象而不是具体实现，这样同时满足上面的依赖倒置原则！而它之所以满足接口隔离原则，是因为：它只需要知道接口是可关闭的，其他全都不关心。

**到此为止的五个原则又被称为SOLID原则，作为面向对象的五个基本原则**。

### 6. 迪米特原则

迪米特原则（Law of Demeter）简称**LOD**，也被称为最少知识原则，其定义为：**一个对象应该对其他对象有最少的了解**。这样可以降低耦合度，将当一个类发生改变的时候对其他类的影响降至最小。

错误范例见LOD中以Stupid开头的几个类，这个StupidTenant不仅依赖了StupidMediator类，而且频繁的与StupidRoom类打交道，租户的要求仅仅是租到一间合适的房子，如果像错误范例中所写的那样将检测的工作全部放在租户中进行，中介类的作用就被弱化了，而且租户类和房间类的耦合度高，租户类和中介类的耦合度也很高，这样就导致三者的关系纠缠不清。

正确范例见LOD中无前缀的几个类。

## 二、单例模式

## 三、Builder模式

## 四、原型模式

## 五、工厂方法模式

## 六、抽象工厂模式

## 七、策略模式

## 八、状态模式

## 九、责任链模式

## 十、解释器模式

## 十一、命令模式

## 十二、观察者模式

## 十三、备忘录模式

## 十四、迭代器模式

## 十五、模版方法模式

## 十六、访问者模式

## 十七、中介者模式

## 十八、代理模式

## 十九、组合模式

## 二十、适配器模式

## 二十一、装饰模式

## 二十二、享元模式

## 二十三、外观模式

## 二十四、敲击模式

## 二十五、总结

- Android中共有23种设计模式。

