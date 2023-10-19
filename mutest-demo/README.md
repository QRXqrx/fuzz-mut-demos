## mutest-demo

基于Javaparaser源码变换的简易变异测试框架。

项目结构：
```text
├── compile-mutants.sh # 批量编译变异体
├── pom.xml
├── pool  # Mutant Pool，变异生成的变异体
├── src     
│   ├── main # Demo变异引擎与变异执行实现
│   └── test # 一些Javaparser使用样例
└── testsuite # 独立出来的测试套件类
```

本模块的主要内容集中在main目录下，包括：

- `DemoSrcMutantEngine`: 基于Javaparser实现的Source-level变异引擎，对变异测试中的下列三个环节进行了简单实现：
  1. **变异算子选择**: 确定变异测试所用的变异算子。本模块提供了一种变异算子实现`BinaryMutator`，能够对二元运算符`+,-,*`进行变异。
  2. **变异体构建**: 利用选定的变异算子生成变异体，包括变异点定位和实施变异两个步骤。
- `DemoMutantExecution`: 利用给定的测试套件执行变异体，计算变异得分。
- `compile-mutants.sh`: 将生成的变异体（.java文件）批量编译成.class文件。
- `Calculator`: 一个简单的类，包含`add`，`substract`，`multiply`三个方法。
- `CalculatorTestSuite`: `Calculator`的简单测试套件，通过抛出异常的方式表现测试不通过。

### 使用方法

本Demo的使用分为三个部分、四个步骤： 
0. 准备待测程序和测试套件；
1. 使用`DemoSrcMutantEngine`生成源代码变异体；
2. 使用`compile-mutants.sh`将源代码变异体编译为`.class`文件；
3. 使用`DemoMutantExecution`执行变异体。

可能的命令行执行过程如下：

```shell
# 变异生成（Mutant Creation）
java edu.nju.mutest.DemoSrcMutantEngine <src_file> <pool_dir>
bash compile-mutants.sh <pool_dir>
# 变异执行（Mutation Execution）
java edu.nju.mutest.DemoMutantExecution <testsuite_dir> <pool_dir>
```

### 拓展阅读

- [Pitest](https://github.com/hcoles/pitest): State of the art mutation testing system for the JVM.
- [Evosuite](https://www.evosuite.org/): Automatic test suite generation for Java.
