## fuzz-targets

一些以`public static void main(String[] args)`为执行入口、要求单个字符串作为输入的模糊目标（Fuzz Target）。同学们在实现的过程中应考虑初始种子的读取和模糊目标的执行。

项目结构：
```text
├── README.md
├── build.sh    # 一次性编译所有模糊目标的脚本
├── edu         # 源代码
└── init-seed   # 一些样例种子
```

一次性编译所有Fuzz Target：`bash ./build.sh`
