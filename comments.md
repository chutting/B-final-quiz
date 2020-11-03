### 完成度：
* 四个接口都有了

__Details:__

- \- 获取分组的接口应该返回完整的分组
- \- 分组接口应该返回分好的组列表
- \- 应该在点击分组的时候才生成组

### 测试：


__Details:__

- \- 有Controller层测试，覆盖了Happy Path

### 知识点：
* 使用了三层架构
* 了解下lombok的使用
* 需要加强Restful实践

__Details:__

- \- @CrossOrigin可以放在类上
- \- 违反Restful实践, 创建资源的请求应该使用POST
- \- 没有使用泛型
- \- 了解下Comparator的静态方法
- \- lambda表达式还可以简化

### 工程实践：
* 分包合理
* 注意单一职责，group和student应该有单独的Controller和Service
* 需要加强面向对象的意识

__Details:__

- \- 未使用的import语句
- \- 长方法，建议抽子方法来提高可读性
- \- 应该创建专门的对象来表示Group
- \- 计算id的方式不够健壮，可以使用字段保存最大id
- \- Magic Number
- \- 代码不优雅

### 综合：


__Details:__



