## fuzzer-demo: 模糊测试简单实现

模糊测试中的执行入口一般为称为模糊目标（Fuzz Target）或模糊驱动（Fuzz Driver）。在本项目中，模糊目标是以`public static void main(String[] args)`为执行入口、需要一个字符串作为输入的Java主类。本模块提供一些**黑盒**模糊器的简单实现。

```text
├── README.md
├── pom.xml
├── product # 一些输出
│   ├── Comparable.class  # 完全由ASM ClassWriter编写的接口
│   ├── asmifier          # ASMifier生成的ASM字节码操作代码
│   ├── bytecode          # javap导出的Bytecode示例
│   └── edu               # ASM简易插装结果（结果有些小问题）
└── src
    ├── main              # fuzzer目录下有Fuzzer的Demo实现
    └── test              # 一些ASM bytecode manipulation试验
```

src目录下包含main和test两个目录，接下来对这两个目录进行详细说明。

### 1 main

main目录下包含Fuzzer Demo的源码一些用于测试ASM字节码运算（Bytecode Manipulation）的样例文件，主要关注Fuzzer Demo。`edu.nju.isefuzz.fuzzer`提供两个Fuzzer Demo
  - `DemoGenerationBlackBoxFuzzer`: 黑盒纯随机模糊器，用字符\[a-z\]生成长度为5的字符串执行模糊目标。执行器通过`ProcessBuilder`和`Process`实现。
  - `DemoMutationBlackBoxFuzzer`: 黑盒基于变异的模糊器，指定唯一初始种子，通过不断变异种子产生新的测试用例执行模糊目标。该Demo简单实现了模糊测试的一些主要组件，包括：
    1. 种子选取: 发生在模糊循环前。选择模糊测试的初始种子，即将指定的初始种子添加到种子队列中。
    2. 种子调度: 发生在模糊循环中。包括下一轮种子输入的确定、能量调度（Power Scheduling）、低效种子的清除机制（Seed Retirement）等。
    3. 测试生成: 发生在模糊循环中。实现了一个简单变异操作，能够将种子的某个英文字符翻转成下一个英文字符。
    4. 测试执行: 发生在模糊循环中。通过`ProcessBuilder`和`Process`包装`javac`指令实现。
    5. 输出分析: 发生在模糊循环中。该模糊器阅读模糊目标的标准输出，将产生新输出的测试输入标记为“favored”。
    6. 测试持久化: 发生在模糊循环后。将模糊循环中保存的种子输出存储到硬盘上。

#### 1.1 使用方法

以`DemoMutationBlackBoxFuzzer`为例，运行`DemoMutationBlackBoxFuzzer`需要三个参数：类路径`cp`，模糊目标完全限定类名`tn`，以及模糊结果的保存路径`outDir`。
假设从maven项目的构件目录`./target/classes`下用`java`指令执行，可能的执行流程如下：

```shell
cd ./target/classes
java edu.nju.isefuzz.fuzzer.DemoMutationBlackBoxFuzzer <path_to_target_out> edu.nju.isefuzz.trgt.Target1 ./out
```


#### 1.2 可能的拓展

- **基于覆盖的模糊测试**: 本仓库目前没有提供**基于覆盖**的模糊测试的实现，选择该题目的同学可以自行实现。一般情况下，基于覆盖的模糊器要求在模糊循环开始前对模糊目标进行覆盖率插装。实现插装有几种方式：
  - **改装已有插装工具**: Java插装工具有很多，常见的有[Jacoco](https://www.eclemma.org/jacoco/)和[Emma](https://emma.sourceforge.net/)。可以改装这些工具并将其接入到模糊测试流程中。
  - **自行实现插装工具**: 可以选择采用源代码插装和字节码插装两种。源代码插装可采用Javaparser，用法可参考mutest-demo；字节码插装推荐采用ASM，使用方法可参考本模块的test目录。
- **种子选取/生成**: 可以考虑在模糊循环开始前通过对模糊驱动进行简单分析合成一些有效的初始种子
- **更有效的能量调度**: Demo只提供了简单的能量调度算法。同学们在实现中可以考虑结合一些启发式算法，如遗传算法、登山法、模拟退火算法等。
- **种子排序**: Demo按照加入队列的顺序为每一轮输入生成确定种子输入。可以考虑加入一些种子排序方法，例如：根据触发的状态数量排序、覆盖率排序等。

### 2 test

test目录下以JUnit4单元测试方法的形式提供了一些ASM实战代码，包括：

- `InstTest#testClassWriter()`: 如何使用ClassWriter从头生成一个Java类所需的所有字节码指令（Bytecode Instruction）。
- `InstTest#testAddInst()`: 如何利用ASM进行简单的插装，目的是报告不同的分支已经被。这个例子目前有一些小Bug：
  1. 插装生成的.class文件无法通过JVM的Verifier检查，只能通过`java -noverify`执行。该Bug可能与字节码中要求的Frame计算有关系。
  2. Else分支中的插装结果不生效。通过比对字节码调试发现插装后的字节码是符合预期的，但插装在`goto`字节码指令后方的指令一直不生效，原因不明。


### 拓展阅读

- [AFL++](https://github.com/AFLplusplus/AFLplusplus): A community-driven fuzzer based one AFL.
- [JQF + Zest](https://github.com/rohanpadhye/JQF): Coverage-guided semantic fuzzing for Java.
- [Javafuzz](https://gitlab.com/gitlab-org/security-products/analyzers/fuzzers/javafuzz): Coverage guided fuzzer for java.
- [Jazzer](https://github.com/CodeIntelligenceTesting/jazzer): Coverage-guided, in-process fuzzing for the JVM.

