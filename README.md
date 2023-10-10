## Demos for fuzzing and mutation testing

一些自实现的模糊测试（Fuzzing，Fuzz Testing）和变异测试（Mutation Testing）的Demo，用作NJU软件学院软件测试课的参考。

项目结构：
```shell
.
├── LICENSE       
├── README.md
├── docs            
├── fuzz-targets
├── fuzzer-demo 
├── mut-cases 
└── mutest-demo 
```

- docs: 一些资料（ASM+SOOT）
- fuzz-targets: 一些以Java主类为入口的Fuzz Target/Fuzz Driver
- fuzzer-demo: Fuzzer demo + ASM实战小例子
- mut-cases: 变异测试Demo的输入
- mutest-demo: Source-level变异测试Demo

选课的同学们需根据选题自行搭建模糊器（Fuzzer）或变异测试工具。在本仓库中，fuzz-targets和mut-cases为模糊测试或变异测试的输入，供同学们调试代码使用；fuzzer-demo和mutest-demo分别为模糊测试和变异测试的简单实现。

### 拓展阅读

模糊测试和变异测试通常会涉及一系列程序变换（Program Transformation）和插装（Instrumentation）的分析操作。针对Java程序的分析可以在源代码（.java）层面和字节码层面（.class）进行（即Source-level和Bytecode-level）。这里给出一些Java源代码分析和字节码操作（Bytecode Manipulation）的相关材料。

- Javaparser: Analyse, transform and generate your Java codebase
  - 官网: https://javaparser.org/
- Spoon: Source Code Analysis and Transformation for Java
  - 官网: https://spoon.gforge.inria.fr/
  - Github Repo: https://github.com/INRIA/spoon
  - Examples: https://github.com/SpoonLabs/spoon-examples
- ASM: All-purpose Java bytecode manipulation and analysis framework
  - 官网: https://asm.ow2.io/
- Soot: A framework for analyzing and transforming Java and Android applications
  - 官网: https://soot-oss.github.io/soot/
  - Github Repo: https://github.com/soot-oss/soot 
  - [SootUp](https://soot-oss.github.io/SootUp/announce/): Enhanced version of Soot 
