## Fuzz-mut-demos

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
- fuzz-targets: 一些以Java-main为入口的Fuzz Target/Driver
- fuzzer-demo: Fuzzer demo + ASM实战小例子
- mut-cases: 变异测试Demo的输入
- mutest-demo: Source-level变异测试Demo