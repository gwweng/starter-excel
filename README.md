# starter-excel

### 介绍
本模块主要基于Alibaba的高性能Excel框架-EasyExcel进行二次封装，实现更高效率的开发，目前支持功能如下

##### 导入
- 支持controller方法参数使用@ImportXlsx注解修饰实现数据解析
- 补充日期转换器（LocalDateConverter，LocalDateTimeConverter）解析填充日期字段，excel设置成文本类型
- 增加导入错误处理提示

##### 导出
- 支持controller方法使用@ExportXlsx注解修饰实现数据导出，模板下载
- 补充日期转换器（LocalDateConverter，LocalDateTimeConverter）格式化导出日期字段
- 增加导出列筛选功能，接口参数继承ExportColumn

### 其他
@ExcelIgnore  忽略导出项