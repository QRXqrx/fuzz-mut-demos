## Intraprocedural Data Flow Analysis

### 什么是数据流分析？

- 基于控制流图进行的分析。Soot中对应控制流图（CFG）的数据结构为`UnitGraph`；

- 控制流图中的节点是语句，边表示从源节点到目标节点的控制流流向；

- 数据流分析主要关心控制流图节点包含的两个元素：

  1. 输入集合：In-set
  2. 输出集合：Out-set

- 输入集合和输出集合在节点中初始化、传输，直到到达不动点（Fixed Point，可以理解为整张图中的数据集合已经不变。）

- 数据流关注的是控制流图每个节点处的输入和输出；定制数据流分析就是定制这些数据的集合。因此在进行数据流分析之前，我们需要明确分析的目标，定义一个满足预期需求的数据流集合。

  > In the end, all you do is inspect the flow set before and after each statement. By the design of the analysis, your flow sets should tell you exactly the information you need.

### 如何使用Soot实现流分析

使用前向分析类`ForwardFlowAnalysis`进行数据流分析

```java
public abstract class ForwardFlowAnalysis<N, A> extends FlowAnalysis<N, A>
```

1. 两个类型参数：

   - `N`：节点类型。一般是`Unit`；

   - `A`：抽象类型（应该指的是数据分析的目标类型）。程序分析使用抽象类型。需要注意的是：抽象类型的`equals`和`hashCode`方法需要正确实现，保证Soot可以确定何时分析到达了不动点。
2. 完成构造
   - **至少**传入一个有向图`DirectedGraph`;
   - 在构造方法的最后调用`doAnalysis`方法；
   - 构造的中间部分需要构建自己分析的数据结构。
3. 初始化操作
   - `newInitialFlow()`：返回一个抽象类型A的对象，用于初始化有向图中的除入口以外的每一个节点（每一条语句）的In-set和Out-set。如果是Forward分析，则第一个节点是入口（Entry）语句；如果是Backward分析，则第一个节点是出口（Exit）语句；
   - `entryInitialFlow()`：初始化第一个语句；
4. 基本操作
   - `copy(A src, A dest)`：将`src`的值复制给`dest`
   - `merge(out1<A>, out2<A>, in1<A>)`：汇集操作，发生在控制流汇集处。将两个基本块的输出汇集成新的In-set。根据分析目标的不同，merge可能是交或者并
   - `flowThrough(in<A>, N node, out<A>)`：流操作，应该对应程序分析中的Transfer Function。表示在某个节点需要做的分析操作。

