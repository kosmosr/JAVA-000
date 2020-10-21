
# 学习笔记

## 字节码

字节码：所有平台都统一使用的程序存储格式，是构成**平台无关性**的基石

### 指令

Java虚拟机的指令由**一个字节**长度的**操作码**以及跟随其后的零至多个代表次操作所需的参数（**操作数**）构成

根据指令的性质，主要分为四个大类：

1. 栈操作指令，包括与局部变量交互的指令

   大概包括以下：

   - 将局部变量加载到操作栈：iload_N、lload_N、dload_N
   - 将栈上数值存储到局部变量表：istore_N、lstore_N、dstore_N

   如下图：

   <img src="https://blog-1256602811.file.myqcloud.com/opcode1.png" alt="opcode1"  />

2. 程序流程控制指令

   如条件分支：if_icmpne（当比较结果为不相等时，则进行跳转），ifnull（如果引用为null，则进行跳转）

3. 对象操作指令，包括方法调用指令

   - `invokestatic`：调用类的静态方法
   - `invokespecial`：调用构造函数，也可以调用本类中的**private**方法，以及可见的父类方法
   - `invokevirtual`：调用对象的实例方法
   - `invokeinterface`：调用接口方法

4. 算术运算以及类型转换指令

   运算指令，如iadd加法、iinc自增运算

   类型转换，如i2b、i2c等

### 动态例子

```java
public static void foo() {
    int a = 1;
    int b = 2;
    int c = (a + b) * 5;
}
```

![微信图片_20201020002517](https://blog-1256602811.file.myqcloud.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20201020002517.gif)

## GC
占位
# 作业一
> 1.自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。

相关代码路径：`src/main/java/Hello.java`
## 解答
源码：

````java
public class Hello {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = a + b;
        if (c == 3) {
            System.out.println("SUCCESS");
        }
        for (int i = 0; i < c; i++) {
            System.out.println(i);
        }
    }
}
````

编译后输出：

```bash
>  javap -v -p Hello
Classfile /E:/geekbangJava/JAVA-000/out/production/code/main/java/Hello.class
  Last modified 2020-10-19; size 712 bytes
  MD5 checksum 5cd7986015558d1603a463df3f11a085
  Compiled from "Hello.java"
public class main.java.Hello
// 对应jdk版本
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
// Class常量池
Constant pool:
   #1 = Methodref          #7.#27         // java/lang/Object."<init>":()V
   #2 = Fieldref           #28.#29        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #30            // SUCCESS
   #4 = Methodref          #31.#32        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Methodref          #31.#33        // java/io/PrintStream.println:(I)V
   #6 = Class              #34            // main/java/Hello
   #7 = Class              #35            // java/lang/Object
   #8 = Utf8               <init>
   #9 = Utf8               ()V
  #10 = Utf8               Code
  #11 = Utf8               LineNumberTable
  #12 = Utf8               LocalVariableTable
  #13 = Utf8               this
  #14 = Utf8               Lmain/java/Hello;
  #15 = Utf8               main
  #16 = Utf8               ([Ljava/lang/String;)V
  #17 = Utf8               i
  #18 = Utf8               I
  #19 = Utf8               args
  #20 = Utf8               [Ljava/lang/String;
  #21 = Utf8               a
  #22 = Utf8               b
  #23 = Utf8               c
  #24 = Utf8               StackMapTable
  #25 = Utf8               SourceFile
  #26 = Utf8               Hello.java
  #27 = NameAndType        #8:#9          // "<init>":()V
  #28 = Class              #36            // java/lang/System
  #29 = NameAndType        #37:#38        // out:Ljava/io/PrintStream;
  #30 = Utf8               SUCCESS
  #31 = Class              #39            // java/io/PrintStream
  #32 = NameAndType        #40:#41        // println:(Ljava/lang/String;)V
  #33 = NameAndType        #40:#42        // println:(I)V
  #34 = Utf8               main/java/Hello
  #35 = Utf8               java/lang/Object
  #36 = Utf8               java/lang/System
  #37 = Utf8               out
  #38 = Utf8               Ljava/io/PrintStream;
  #39 = Utf8               java/io/PrintStream
  #40 = Utf8               println
  #41 = Utf8               (Ljava/lang/String;)V
  #42 = Utf8               (I)V
{
  // 空参构造器方法
  public main.java.Hello();                  
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 9: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lmain/java/Hello;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=5, args_size=1
         0: iconst_1   // 把1放入操作数栈顶
         1: istore_1   // 把栈顶int型数值放入slot为1的本地变量表中 相当于源码中的 a = 1
         2: iconst_2   
         3: istore_2  // 同上，相当于源码中的 b = 2
         4: iload_1   // 加载slot为1的本地变量表中的int数值，并放入栈顶
         5: iload_2   // 同上, 但slot为2
         6: iadd    // 退栈两次并把结果相加重新放入栈顶，相当于源码中的 a + b
         7: istore_3  // 把上述的运算结果，存回slot为3的本地变量表中，
         8: iload_3  // 加载slot为3的本地变量表中的int数值，并放入栈顶
         9: iconst_3   // 把3放入栈顶
        10: if_icmpne     21  // 比较栈顶上的前两个数值结果，如果相等则继续下面的逻辑，否则跳到21行
        13: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        16: ldc           #3                  // String SUCCESS
        18: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        // 上面三个字节码命令即调用System.out中的println方法
        21: iconst_0 // 把0推入栈顶
        22: istore        4  // 把栈顶上的数放入slot为4的本地变量表中
        
        24: iload         4  // 加载slot为4的本地变量表中的int数值，并放入栈顶
        26: iload_3  // 同上，slot为3
        27: if_icmpge     44 // 比较栈顶上前两个数值结果，为false时跳转到44行
        30: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        33: iload         4
        35: invokevirtual #5                  // Method java/io/PrintStream.println:(I)V
        // 打印方法
        38: iinc          4, 1 // 对slot为4的本地变量表中的数值进行加1
        41: goto          24  // 回到24行，重复执行逻辑
        44: return
      // 源码与字节码行号的对应关系
      LineNumberTable:
        line 11: 0
        line 12: 2
        line 13: 4
        line 14: 8
        line 15: 13
        line 17: 21
        line 18: 30
        line 17: 38
        line 20: 44
       // 本地变量表
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           24      20     4     i   I
            0      45     0  args   [Ljava/lang/String;
            2      43     1     a   I
            4      41     2     b   I
            8      37     3     c   I
      StackMapTable: number_of_entries = 3
        frame_type = 254 /* append */
          offset_delta = 21
          locals = [ int, int, int ]
        frame_type = 252 /* append */
          offset_delta = 2
          locals = [ int ]
        frame_type = 250 /* chop */
          offset_delta = 19
}
SourceFile: "Hello.java"
```

# 作业二

> 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供

代码路径：`src/main/java/HelloClassLoader.java`

源码：

```java
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        try {
            Class<?> helloClass = helloClassLoader.loadClass("Hello");
            Object helloInstance = helloClass.newInstance();
            Method method = helloClass.getDeclaredMethod("hello");
            method.invoke(helloInstance);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream inputStream = this.getResourceAsStream(name + ".xlass");
        if (inputStream == null) {
            return super.findClass(name);
        }
        try (BufferedInputStream stream = new BufferedInputStream(inputStream)) {
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                bytes[i] = (byte)(255 - b);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
    }
}

```

思路：

1. 自定义**ClassLoader**，重写`findClass`方法
2. 在`findClass`方法中，使用I/O包下的`BufferedInputStream`类进行读取文件字节流，读完后然后使用for循环对每个字节进行相应的解密
3. 随后利用反射调用生成实例，并调用`Hello`方法即可



# 作业三

> 画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系

![jvm](https://blog-1256602811.file.myqcloud.com/jvm.png)