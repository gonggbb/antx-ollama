# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.5/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.5/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

# doc
http://localhost:8080/doc.html

# langchain4j

LangChain4j 提供与许多LLM 提供商、 嵌入/矢量存储等的集成。每个集成都有自己的 maven 依赖项。
https://docs.langchain4j.dev/get-started

https://github.com/langchain4j/langchain4j/tree/main


#  springboot 

LangChain4j Spring Boot 集成需要 Java 17 和 Spring Boot 3.2。

https://docs.langchain4j.dev/tutorials/spring-boot-integration

# 模型排行

https://superclueai.com/


## 模型 
https://docs.langchain4j.dev/integrations/language-models/



# deepseek api

## DEEP_SEEK_API_KEY 环境变量

https://www.deepseek.com/

https://api-docs.deepseek.com/zh-cn/

# ollama

https://ollama.com/

https://github.com/ollama/ollama


```
| Llama 3.3          | 70B        | 43GB  | `ollama run llama3.3`            |
| Llama 3.2          | 3B         | 2.0GB | `ollama run llama3.2`            |

ollama run Llama3.2 
```

https://ollama.com/search


## 使用

https://docs.langchain4j.dev/integrations/language-models/ollama#get-started


# 阿里百炼平台

模型列表：
https://help.aliyun.com/zh/model-studio/models

模型广场：
https://bailian.console.aliyun.com/?productCode=p_efm#/model-market

API Key：
https://bailian.console.aliyun.com/?apiKey=1&productCode=p_efm#/api-key

LangChain4j参考文档： https://docs.langchain4j.dev/integrations/language-models/dashscope#plain-java



# mongodb

服务器： mongodb-windows-x86_64-8.0.6-signed.msi https://www.mongodb.com/try/download/co
mmunity
命令行客户端 ： mongosh-2.5.0-win32-x64.zip https://www.mongodb.com/try/download/shell
图形客户端： mongodb-compass-1.39.3-win32-x64.exe https://www.mongodb.com/try/download/c
ompass


# token使用

https://api-docs.deepseek.com/zh-cn/quick_start/token_usage

https://bailian.console.aliyun.com/?tab=model#/efm/model_experience_center/text



# 文本向量

https://help.aliyun.com/zh/model-studio/text-embedding-synchronous-api?spm=a2c4g.11186623.help-menu-2400256.d_2_5_0.592672a3yMJDRq&scm=20140722.H_2712515._.OR_help-T_cn~zh-V_1

存储

https://www.pinecone.io/

