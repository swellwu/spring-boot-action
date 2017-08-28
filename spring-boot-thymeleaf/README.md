## spring-boot-thymeleaf
spring-boot使用thymeleaf前端框架。

## 时间绑定
前端传送格式化后的时间字符串给后端，后端绑定到Date类型。
### 使用注解绑定
```
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
@DateTimeFormat(pattern = "yyyy-MM-dd")
```
如果用的@DateTimeFormat(pattern = "yyyy-MM-dd")，传输的是yyyy-MM-dd HH:mm:ss格式，则忽略时分秒。
### 使用spring mvc 绑定
@InitBinder绑定类型转换