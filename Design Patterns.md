# 设计模式

对于《Android源码分析设计模式应用与实战》的笔记。

~~适用人群我自己~~

## 一、面向对象的六大原则

### 1.1 单一职责原则

单一职责原则（Single Responsiblity Principle）简称**SRP**，其定义是：**就一个类而言，它应该仅有一个引起它变化的原因**。简单来说，一个类中应该是**一组相关性很高的函数、数据的封装**。

两个完全不一样的功能不应该放在一个类中。当然呢，`util`（帮助）类除外。

### 1.2 开闭原则

开闭原则（Open Close Principle）简称**OCP**，其定义是：**软件中的对象（类、模块、函数等）应该对于扩展是开放的，但对于修改是封闭的**。

若是修改原有代码容易造成意想不到的错误，所以在理想情况下我们希望采用继承原类的方法来升级维护。以一个简单的图片缓存器为例：

![image-20190422164109851](/Users/moanbigking/Library/Application Support/typora-user-images/image-20190422164109851.png)

用户可通过实现ImageCache接口自定义他需要的ImageLoader，通过ImageLoader中暴露的一个setImageLoader方法实现注入。

### 1.3 里氏替换原则

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

### 1.4 依赖倒置原则

依赖倒置原则（Dependence Inversion Principle）简称**DIP**，指代一种特定的解藕形式，使高层次模块不依赖于低层次模块的实现细节。通俗的说，依赖倒置原则有以下几个关键点：

- 高层模块不应该依赖低层模块，两者都应该依赖其抽象。
- 抽象不应该依赖细节。
- 细节应该依赖抽象。

在java语言中，抽象就是指抽象类或者接口类，两者都不能直接被实例化。由此引申出概念：细节就是实现类，即实现接口或继承抽象而产生的类就是细节，其特点就是可以直接被实例化；高层模块就是调用端，低层模块就是具体实现类。依赖倒置原则在java语言中的具体表现为：**模块间的依赖通过抽象发生，实现类之间不发生直接的依赖关系，其依赖关系是通过接口或抽象类产生的**。简而言之，面向抽象（接口或者抽象类）编程。

### 1.5 接口隔离原则

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

### 1.6 迪米特原则

迪米特原则（Law of Demeter）简称**LOD**，也被称为最少知识原则，其定义为：**一个对象应该对其他对象有最少的了解**。这样可以降低耦合度，将当一个类发生改变的时候对其他类的影响降至最小。

错误范例见LOD中以Stupid开头的几个类，这个StupidTenant不仅依赖了StupidMediator类，而且频繁的与StupidRoom类打交道，租户的要求仅仅是租到一间合适的房子，如果像错误范例中所写的那样将检测的工作全部放在租户中进行，中介类的作用就被弱化了，而且租户类和房间类的耦合度高，租户类和中介类的耦合度也很高，这样就导致三者的关系纠缠不清。

正确范例见LOD中无前缀的几个类。

将对租房是否符合租户要求的判定移到了中介中（现实中也是这样），中介根据租户的要求寻找房子并将结果反馈给租户，租户并不需要知道全部租房的信息，租户只需要与中介沟通就好了！

## 二、单例模式

~~这个谁没用过~~

### 2.1 使用场景

确保某个类只有一个对象，避免产生多个对象消耗资源。或者某个类的对象只应该有一个。

### 2.2 要素

- 构造函数一般用private修饰，不对外开放。
- 通过一个静态方法或**枚举**（加粗是因为我之前没见过）返回单例类对象。
- 确保单例中对象有且只有一个~~（废话不然叫什么单例）~~。
- 确保单例在反序列化时不会重新构造对象。

### 2.3 * 枚举单例的使用

首先，我不是很会枚举，所以我要说一下可能大家都知道的废话。

``` java
//基本用法
enum Typr{
  A, B, C, D;
}
//如果不写枚举？
class Type extends Enum{
  public static final Type A;
  public static final Type B;
  //TODO
}
```

我们可以将Type看作一个类，而将A,B,C,D看作类的实例。当然，实例的构造不是我们做的，一个Enum的构造方法是私有的，也就是不允许我们调用。

既然我们将Type看做一个类，而将A,B,C,D看作类的实例，我们就可以在Type中定义变量和方法！

``` java
enum Type{
  A, B, C, D;
  static int value;
  public static int getValue() {
    return value;
  }
  String type;
  public String getType() {
    return type;
  }
}
```

既然我们将Type看作一个类，而将A,B,C,D看作实例，因为getValue方法是一个静态方法，所以调用时直接Type.getValue()即可，而getType方法是一个实例方法，所以调用时通过**Type.A.getType()**即可。

而且！对于某个实例，它甚至可以实现自己的实例方法！

``` java
enum Type{
  A{
    public String getType() {
      return "now I return A";
    }
  }, B, C, D;
  static int value;
  public static int getValue() {
    return value;
  }
  String type;
  public String getType() {
    return type;
  }
}
```

这里，独属于A的实例方法可以**覆盖**原本的方法。

更深一步，我们可以在枚举中定义抽象方法并强制其所有实例覆写该方法。

``` java
enum Type{
  public abstract String getType();
  A{
    @Override
    public String getType() {
      return "A";
    }
  }, B{
    @Override
    public String getType() {
      return "B";
    }
  }, C{
    @Override
    public String getType() {
      return "C";
    }
  }, D{
    @Override
    public String getType() {
      return "D";
    }
  };
}
```

~~以上所有代码老子都没有跑过，如果有问题请装作莫有看见。~~

好！有了上面的铺垫，下面我们正式进入枚举单例的学习！

``` java
class Resource{}
public enum Sample{
  INSTANCE;
  private Resource instance;
  Sample() {
    instance = new Resource();
  }
  public Resource getInstance() {
    return instance;
  }
}
```

这个Resource就是我们要应用的单例模式的资源，具体表现形式可以为网络连接，数据库连接，线程池等等。

获取资源的方式为：**Sample.INSTANCE.getInstance()**。

下面分析一下单例是如何被保证的：

首先，枚举的构造方法是私有的（就是私有！），在我们第一次访问枚举实例的时候会执行这个构造方法，同时每个实例都是static final的，这就保证了每个实例只能被实例化一次，所以我们的INSTANCE也能保证只被实例化一次，进而instance也能保证只被实例化一次。~~（草，好牛逼）~~

多嘴：

我们再看一哈Enum这个类的声明。

``` java
public abstract class Enum<E extends Enum<E>> implements Comparable<E>, Seriarizable
```

枚举也提供了**序列化机制**。

> 《Effective Java》里面讲，单元素的枚举类型已经成为实现单例模式的最佳选择。
>
> ~~（我觉得也是）~~

### 2.4 懒汉模式与饿汉模式

- 饿汉模式

  ``` java
  //饿汉模式的典型写法
  public class Sample{
    private static final Sample INSTANCE = new Sample();
    private Sample(){}
    public static Sample getInstance() {
      return INSTANCE;
    }
  }
  ```

  可以看到，这个Sample在声明的时候就已经初始化。

- 懒汉模式

  ``` java
  //懒汉模式的经典写法
  public class Sample{
    private static Sample INSTANCE;
    private Sample(){}
    public static synchronized Sample getInstance() {
      if (INSTANCE == null) {
        INSTANCE = new Sample();
      }
      return INSTANCE;
    }
  }
  ```

  需要注意的是，饿汉模式下的getInstance方法添加了**synchronized**关键字，这是**在多线程条件下保证单例对象唯一性的手段**。

  懒汉模式的优点在于单例只有在使用时才会被实例化，在一定程度上节约了资源；缺点是第一次加载时需要及时实例化，反应稍慢，最大的问题在于每次调用getInstance方法时都进行同步，造成不必要的同步开销。所以**不建议使用懒汉模式**！

### 2.5 * Double Check Lock(DCL)实现单例

这种方法既能够在需要的时候才初始化单例，又保证了线程安全，好像一般来说这是最常见的单例写法。

``` java
public class Sample{
  private static Sampel INSTANCE = null;
  private Sample(){}
  public static Sample getInstance() {
    if (INSTANCE == null) {
      synchronized (Sample.class) {
        if (INSTANCE == null) {
          INSTANCE = new Sample();
        }
      }
    }
    return INSTANCE;
  }
}
```

这个方法的精髓在于getInstance方法，可以看到此方法对INSTANCE进行了**两次判空**，第一层避免了不必要的同步，第二层在null的情况下创造了实例。

假设线程执行到`INSTANCE = new Sample();`语句，**这并不是一个原子操作**（所谓原子操作是指不会被线程调度机制打断的操作），转为汇编代码之后大致做了如下三件事：

1. 给Sample的实例分配内存；
2. 调用Sample()的构造函数，初始化成员字段；
3. 将INSTANCE对象指向分配的空间，这时它不再是null。

> 由于java编译器乱序执行，在JDK1.5及之前的JMM（Java Memory Model，即java内存模型）中Cache、寄存器到主内存回写顺序的规定，上面的2步和3步的顺序是无法保证的，若执行1-3-2，在3执行完毕而2未执行的情况下切换到线程B，因为INSTANCE在A中执行了3所以它不再是非空，而它实际没有意义，所以直接使用会出错，这就是DCL失控的情况。
>
> 不过在JDK1.5之后SUN官方注意到了这个问题，调整了JVM（具体我还不懂），具体化了volatile关键字，只需要将INSTANCE的定义改为private volatile static Sample INSTANCE = null; 就可以保证每次INSTANCE都是从主存中读取，从而避免了上述问题。
>
> 这个暂时看不太懂没关系，只需要记住，加volatile关键字就行。

DCL同样存在初次加载较慢的问题。

### 2.6 * 静态内部类单例模式

一种对DCL的优化。

``` java
public class Singleton{
  private Singleton(){}
  public static Singleton getInstance() {
    return SingletonHolder.sInstance;
  }
  //静态内部类
  private static class SingletonHolder{
    private static final Singleton sInstance = new Singleton();
  }
}
```

可以看到，在第一次加载Singleton类的时候不会初始化sIntance，而是在第一次调用getInstance方法时才会导致虚拟机加载静态内部类SingletonHolder，这种方式不仅保证线程安全，而且保证了单例对象的唯一性，所以这是最推荐的方法。

### 2.7 关于序列化

在枚举单例中我们提到，枚举类提供了序列化机制。

上述除了枚举的所有方法仍然存在重新创造实例的风险：在反序列化时。

通过序列化可以将一个对象写入磁盘再读回来，从而有效的获得一个实例，即使构造函数是私有的，反序列化仍然有特殊的手段去创建类的一个新实例（我还不懂）。反序列化提供了一个很特别的钩子函数，类中有一个私有的、被实例化的方法`readResolve()`，这个方法可以让开发人员控制对象的反序列化。

上述几个示例中若想避免重新创造对象，必须加入以下代码：

``` java
private Object readSolve() throws ObjectStreamException{
  return sInstance;
}
```

就是让这个方法返回sInstance而不是新生成一个对象，而枚举单例模式不存在这个问题。

### 2.8 使用容器实现单例模式

~~什么奇技淫巧~~

``` java
public class SingletonManager{
  private static Map<String, Object> objMap = new HashMap<>();
  
  private SingletonManager(){}
  public static void registrService(String key, Object instance) {
    if (!objMap.containsKey(key)) {
      objMap.put(key, instance);
    }
  }
  public static Object getInstance(String key) {
    return objMap.get(key);
  }
}
```

其优点在于进一步降低了耦合度

~~以我目前的水平应该用不到此方法~~

### 2.9 系统源代码分析

#### LayoutInflater

具体实现之后有空再记

### 2.10 总结

#### 图片缓存

图片缓存（ImageLoader）是单例模式很常见的一种应用场景，用户通过getInstance获得单例。用户可以通过载入ImageLoaderConfig来对ImageLoader进行配置，当调用displayImage方法时，ImageLoader将请求构成一个BitmapRequest，然后将请求添加到请求队列中，图片加载线程（RequestDispatcher）会从请求队列（RequestQueue）中获得请求，然后加载并显示。

#### 优点

- 单例模式在内存中只有一个实例，减少了内存开销（在一个对象频繁的被创造和销毁时，对系统内存的消耗是巨大的，这种情况下单例模式可以有效的优化性能）。
- 由于单例模式只存在一个实例，减少了系统的性能开销，当一个对象的产生需要较多资源（如读取配置、产生其他依赖对象）的时候，可以通过在应用启动时直接产生一个单例对象，永久驻入内存的方式来解决。
- 避免对资源的多重占用，例如一个写文件操作，由于只存在一个实例，可以避免一个资源文件被同时写操作。
- 可以在系统设置全局的访问点，优化和共享资源访问（例如，可以设置一个单例类，负责所有表的映射处理）。

#### 缺点

- 单例模式一般没有接口，扩展很困难，若要扩展，除了修改代码基本没有第二种方法可以实现。
- 单例对象如果持有一个Context，则容易引起内存泄漏，此时注意传给单例对象的Context最好是ApplicationContext。

## 三、Builder模式

### 3.1 定义

Builder模式是将一个复杂对象的构建与他的表示分离，使得同样的构建过程可以创造不同的表示。

### 3.2 使用场景

- 相同的方法不同的执行顺序，产生不同的事件结果时；
- 多个部件和零件，都可以装配到一个对象中去，但是产生的结果不同；
- 产品类非常复杂，或者产品类的调用顺序不同产生了不同的作用；
- 初始化一个类的参数非常多，并且很多参数具有默认值。

### 3.3 系统源代码分析

#### AlertDialog.Builder

``` kotlin
//一个使用AlertDialog.Builder的简单示例
class AlertDialogSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog_sample)
    }
    fun showDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setIcon(R.drawable.ic_launcher_foreground)
        builder.setMessage(MESSAGE)
        builder.setPositiveButton("Button1") {dialog, which -> title = "onClickButton1" }
        builder.create().show()
    }
    companion object{
        val MESSAGE = "message_sample"
    }
}
```

可以看见，可以通过builder的set方法更改dialog的图标、标题、信息等。

在源码中我们可以看到，AlertDialog内部持有一个AlertController的实例（mAlert），这个实例在AlertDIalog初始化的时候同时被初始化，调用builder的set方法实际上是调用了`mAlert.set...()`。

``` java
//setTitle的源码
public void setTitle(CharSequence title) {
  super.setTitle(title);
  this.mAlert.setTitle(title);
}
```

而Builder是AlertDialog的内部类：

``` java
public static class Builder {
  private final AlertParams P;//储存AlertDialog的各个参数，如title、message、icon等。
  private final int mTheme;//theme是一个resourceId，详细我还不是很清楚
  
  //省略两个构造器函数
  
  //返回Builder的set方法
  public AlertDialog.Builder setTitle(@StringRes int titleId) {
    this.P.mTitle = this.P.mContext.getText(titleId);
    return this;
  }
  //省略类似方法
  
  public AlertDialog create() {
    //构造AlertDialog对象
    AlertDialog dialog = new AlertDialog(this.P.mContext, this.mTheme);
    //将p中的参数传到mAlert对象中，注意看这个apply方法！
    this.P.apply(dialog.mAlert);
    dialog.setCancelable(this.P.mCancelable);
    if (this.P.mCancelable) {
        dialog.setCanceledOnTouchOutside(true);
    }

    dialog.setOnCancelListener(this.P.mOnCancelListener);
    dialog.setOnDismissListener(this.P.mOnDismissListener);
    if (this.P.mOnKeyListener != null) {
        dialog.setOnKeyListener(this.P.mOnKeyListener);
    }

    return dialog;
  }
}  
```

在apply函数中，只是将AlertParams的参数设置到AlertController中，例如将标题设置到标题视图中，将消息设置到内容视图中，随后通过show函数展示这个Dialog。

``` java
public AlertDialog show() {
  AlertDialog dialog = this.create();
  dialog.show();
  return dialog;
}
//可以看到，这个函数最终调用了dialog.show()。

//下面是dialog.show()的源码（在Dialog类中
    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     */
/*我简单翻译一下：是这个dialog开始工作并显示在屏幕上。window被放置在application层并且不透明，注意当这个dialog正在展示中， 你需要重写这个方法来进行初始化而不是在onStart方法中依赖它。*/
    public void show() {
        if (mShowing) {
            if (mDecor != null) {
                if (mWindow.hasFeature(Window.FEATURE_ACTION_BAR)) {
                    mWindow.invalidatePanelMenu(Window.FEATURE_ACTION_BAR);
                }
                mDecor.setVisibility(View.VISIBLE);
            }
            return;
        }

        mCanceled = false;

      //1. onCreate调用
        if (!mCreated) {
            dispatchOnCreate(null);
        } else {
            // Fill the DecorView in on any configuration changes that
            // may have occured while it was removed from the WindowManager.
            final Configuration config = mContext.getResources().getConfiguration();
            mWindow.getDecorView().dispatchConfigurationChanged(config);
        }
      
      //2. onStart
        onStart();
        mDecor = mWindow.getDecorView();

        if (mActionBar == null && mWindow.hasFeature(Window.FEATURE_ACTION_BAR)) {
            final ApplicationInfo info = mContext.getApplicationInfo();
            mWindow.setDefaultIcon(info.icon);
            mWindow.setDefaultLogo(info.logo);
            mActionBar = new WindowDecorActionBar(this);
        }

      //获取布局参数
        WindowManager.LayoutParams l = mWindow.getAttributes();
        boolean restoreSoftInputMode = false;
        if ((l.softInputMode
                & WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION) == 0) {
            l.softInputMode |=
                    WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION;
            restoreSoftInputMode = true;
        }

        mWindowManager.addView(mDecor, l);
        if (restoreSoftInputMode) {
            l.softInputMode &=
                    ~WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION;
        }

        mShowing = true;

        sendShowMessage();
    }
```



需要注意在最后调用了`builder.create().show()`，`show`方法主要做了以下三件事：

1. 通过`dispatchOnCreate`函数来调用`AlertDialog`的`onCreate`函数；
2. 调用`AlertDialog`的`onstart`；
3. 将`Dialog`的`DecorView`添加到`WindowManager`中。

我不想记了，再说（。

#### WindowManager

### 3.4 总结

#### 图片缓存

``` java
//基本结构
public final class ImageLoader{
  private ImageLoaderConfig mConfig;
  
  //通过init方法初始化mConfig
  public void init(ImageLoaderConfig config) {
    this.mConfig = config;
  }
}
public class ImageLoaderConfig{
  //私有构造器方法，不允许除内部类之外的类直接构造，即只能通过Builder.build();
  private ImageLoaderConfig(){}
  
  public static class Builder{
    BitmapCache mCache = new MemoryCache();
    //提供各种参数的默认值
    
    void applyConfig(ImageLoaderConfig config) {
      this.mCahche = config.cache;
      //自定义属性值
    }
    
    //采用默认参数的方法
    public ImageLoaderConfig create() {
      ImageLoaderConfig config = new ImageLoaderConfig();
      applyConfig(config);
      return config;
    }
  }
}

```

而用户的使用方法类似下方：

``` java
private void initImageLoader() {
  ImageLoaderConfig config = new ImageLoaderConfig.Builder()
    .setParam1(param1)
    .setParam2(param2)
    .setParam3(param3)
    .create();
  ImageLoader.getInstance().init(config);
}
```

#### 优点

- 良好的封装性，使用建造者模式可以让客户端不必知道产品内部组成的细节；
- 建造者独立，容易扩展。

#### 缺点

产生多余的Builder对象，消耗内存。

## 四、原型模式

### 4.1 定义

用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。

### 4.2 使用场景

- 类初始化需要消耗巨大资源，使用原型模式可以避免这些消耗；
- 通过new产生一个对象需要非常繁琐的数据准备或者访问权限，原型模式就没有这个问题；
- 一个对象需要提供给其他对象访问，而任何一个调用者都可能修改其值，可以考虑用原型模式拷贝多个对象供调用，即保护性拷贝。
- 需要注意的是，通过实现了Cloneable接口的原型模式在调用clone函数构造实例时不一定比直接new的速度快（只有在new的对象较为耗时或成本较高的时候才能取得效率上的提升），当然使用原型模式不意味着必须实现Cloneable接口。

### 4.3 简单实现

设想一个场景，有一份文档摆在你的面前，你需要对它进行修改，但不确定修改后的版本是否会被采纳，那么最稳妥的做法就是拷贝一份文档，对拷贝的副本进行修改。

``` kotlin
class WordDocument(): Cloneable{
  private var mText: String ?= null
  private var mImages = ArrayList<String>()
  init{
    System.out.println("init fun")
  }
  override fun clone() {
    try{
      WordDocument doc = super.clone as WordDocument
      doc.mText = this.mText
      doc.mImage = this.mImage
      return doc
    } catch (e: Exception) {
      return null
    }
  }
}
//clone()是Object中的方法，但是如果一个没有实现Cloneable接口的实例试图调用clone方法，将会导致一个异常。

//使用示例
fun Sample() {
  val document1 = WordDocument()
  val document2 = document1.clone()
  //同时可以修改document2的属性
  document2.mText = "after_change"
}
```

### 4.4 深拷贝和浅拷贝

#### 浅拷贝

4.3中的简单实现只是实现了浅拷贝，或者叫影子拷贝，这份拷贝实际上不是将原始字段重新构造了一份而是副本持有原始字段的引用，也就是说~~这根本没有卵用~~修改2的同时也修改了1、修改1的同时也修改了2，

#### 深拷贝

解决上述问题的方法是在clone时进行这样的操作：

``` kotlin
//深拷贝
override fun clone() {
    try{
      WordDocument doc = super.clone as WordDocument
      doc.mText = this.mText
      doc.mImage = this.mImage.clone
      return doc
    } catch (e: Exception) {
      return null
    }
  }
}
```

### 4.5 系统源代码分析



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

