# simpleOJ
A simple Online Judger with JavaFX in Java.

## Showcase



## Quick Start

1. 首先执行`query.sql`建立数据库
2. 在`src/database/AccessDB`中修改数据库用户名和密码
3. 运行

## 说明

判题机只定义了6种返回状态，可自定义：

```java
case 0:
	return "ACCEPTED";
case 1:
	return "Compile Error";
case 2:
	return "Runtime Error";
case 3:
	return "Time Limit Exceeded";
case 4:
	return "Memory Limit Exceeded";
case 5:
	return "Wrong Answer";
```

