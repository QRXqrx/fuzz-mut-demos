## Soot基础对象

Soot提供了若干类基本对象，例如：`Body`、`Unit`、`Local`、`Value`、`UnitBox`和`ValueBox`。

### 1、`Body`

`Body`对应中间表示（Intermedia Representation，IR）。Soot提供了四种`Body`类，分别对应Soot支持的四种中间表示：`BafBody`、`JimpleBody`、`ShimpleBody`和`GrimpBody`

### 2、`Local`

`Local`对应局部变量。字节码中也有类似的表示：Local Variable Part和Operand Stack part。`Local`保存在`LocalChain`中，每个中间变量的`Local`实现不同。

- 举例

  ```
  java.lang.String[] r0;
  int i0, i1, i2, $i3, $i4;
  java.io.PrintStream $r1, $r2;
  java.lang.Exception $r3, r4;
  ```

### 3、Trap记号

Trap记号对应Java异常处理机制。Java的异常处理语句可以用一个四元组表示：(exception, start, stop, handler)。handler对应catch语句。

- 举例

  ```
  catch java.lang.Exception from label0 to label1 with label2;
  ```

### 4、`Unit`

`Unit`对应Java程序的语句（Statement），每种中间表示都有不同的`Unit`实现。例如：Jimple的`Stmt`、Grimp的`Inst`。

- 举例：Jimple的赋值语句`AssignStmt`

  ```
  x = y + z;
  ```

### 5、`Value`

`Value`表示代码中的数据，因此`Value`应该会在数据流分析中用的到。Soot中提供了以下四种和数据有关的类型：

- `Local`：局部变量（和方法参数）
- `Constant`：常量
- `Expr`：表达式，定义了与数据相关的操作。以一个或多个`Value`为输入，返回另外的`Value`；
- `ParameterRef`、`CaughtExceptionRef`和`ThisRef`：各种引用。

### 6、`Box`

`Box`无处不在，可以将`Box`理解为指针，提供对Soot对象的非直接访问。

**注意**：`Ref`比`Box`更贴近`Box`的语义，但是`Ref`在soot中有别的含义

`Box`有两个常用的子类：`UnitBox`和`ValueBox`，分别表示语句的集合和值的集合。

####  6.1 `UnitBox`

`UnitBox`对应基本块。`getUnitBoxes`可以获取基本块的下一个跳跃点（？）

#### 6.2 `ValueBox`

一个`Unit`包含很多`ValueBox`，表示在`Unit`（语句）中定义或者用到的值



### 7、`Unit`必须提供的几类方法

- 获取`ValueBox`（值）

  ```java
  public List getUseBoxes();
  public List getDefBoxes();
  public List getUseAndDefBoxes();
  ```

  这些方法返回在`Unit`表示的语句中使用或定义的值。例如`getUseBoxes`会返回所有的用值，包括表达式和表达式的组成部分。

- 获取`UnitBox`（基本块）

  - 获取**当前语句指向**的所有基本块

    ```java
    public List getUnitBoxes();
    ```

  - 获取**指向当前语句**的所有基本块

    ```java
    public List getBoxesPointingToThis();
    ```

  - 判定Execution Flow执行流向（控制流流向?）

    ```java
    public boolean fallsThrough(); // 如果能够流向下一条语句，返回True
    public boolean branches(); // 如果流向的语句并非下一条，返回true
    ```

  - 重定向指向

    ```java
    public void redirectJumpsToThisTo(Unit newLocation);
    ```

    这个方法能够将所有指向当前语句的重新定向到一个新的语句（`newLocation`）

