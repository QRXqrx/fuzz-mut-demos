#  ASM简介

*一个注重运行效率的程序分析工具*

## 1 程序分析基础知识

### 1.1 程序分析相关的应用

- Program Analysis
- Program Generation
- Program Transformation

### 1.2 程序分析的目标对象（Java）

- 源代码
- **字节码**

### 1.3 以字节码为操作目标的好处

- 不需要源码
- 程序分析的应用（像Program Generation）可以在运行时进行
  - 在runtime执行程序分析可以提高效率
- 可以分析闭源的Java项目

## 2 ASM梗概

### 2.1 程序分析工具ASM的定位
- 以字节码为操作对象的、具有高分析效率的java程序分析工具
> The ASM library was therefore designed to work on compiled Java classes. It was also designed to be as fast and as small as possible.

-  以**字节数组**（byte arrays）为底层研究对象
> The goal of the ASM library is to generate, transform and analyze compiled Java classes, represented as byte arrays (as they are stored on disk and loaded in the Java Virtual Machine).  

- 主要操作包括关于字节数组的读、写、转换以及对类的分析，不涉及**类的加载过程**（Class Loading）
> Note that the scope of the ASM library is strictly limited to reading, writing, transforming and analyzing classes. In particular the class loading process is out of scope     

### 2.2 ASM提供的两类API

#### Core API

- *Event based representation of classes*
  - 将类的元素（header, field, method declaration）视作event
  - 记录类元素出现的顺序
  - 提供类解析器（Class Parser）将元素解析成event
  - 提供类写入器（Class Writer）将event序列生成编译好的类
- 更快，且所需内存更少
  - 与Object Based API相比，不需要分配内存给树形对象

#### Tree API

- *Object based representation of classes*
  - 将类表示成树形对象
  - 能够将类转化成event序列，反之亦然
  - 建立在Core API的基础之上
- 在做Generation时更方便
  - 与Event Based API相比，表示的元素更丰富

**两种API都只会同时处理一个Class，不会保留Class Hierarchy信息**

### 2.3 ASM的整体结构

#### Event Based API

- 包含producer (the class parser), consumer (class writer), event filters。
- 用户可以为一个producer添加多个consumer和filter
- 有些类似pipeline的处理方式
- 使用步骤：
  - 定义自己的producer、filter和consumer。
  - 将定义好的组件组装成预期的结构。
  - 启动整个流程，完成generation流程

#### Object Based API

- 由class generator和class transformer组成
- generator和transformer的链接顺序代表了transformation的执行顺序

**在整个class转换链中，EBA和OBA可以交替使用**