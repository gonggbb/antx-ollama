# idea

**Ctrl+O**：选择父类的方法进行重写。 - **Ctrl+Q**：查看JavaDoc。 - **Ctrl+Alt+Space**：类名自动完成
## 配置同步
C:\Users\Administrator\AppData\Roaming\JetBrains\IntelliJIdea2024.1



格式化功能（如 IntelliJ IDEA 中的 Ctrl + Alt + L 或 Eclipse 中的 Ctrl + Shift + F）来清理多余的空格并统一格式。

Ctrl + Shift + A

⌘ + Shift + A

alt+7、ctrl+F12 结构

ctrl + n 查找
ctrl + h 查看类继承关系


## 杀掉 IDEA 进程


```
# 查找 IDEA 进程 ID
ps aux | grep idea

# 杀掉进程（替换 <PID> 为实际 PID）
kill -9 <PID>

# 重新启动 IDEA
open -a /Applications/IntelliJ\ IDEA.app

```

## mongo

C:\Program Files\MongoDB\Server\8.0\bin

## 编码器

### 乱码

https://blog.csdn.net/HaHa_Sir/article/details/122851700

BOM”指的是“Byte Order Mark”，即字节顺序标记

### IntelliJ IDEA中的选项解释
- **包含 BOM**：选择此选项时，IDEA会在保存文件时添加BOM。这对于确保某些旧版软件或特定环境下的兼容性可能有帮助。
- **不含 BOM**：选择此选项时，IDEA不会在文件开头添加BOM。这是大多数现代文本处理工具和编程语言所推荐的方式，因为它避免了不必要的额外字符，特别是在Unix/Linux系统中。
- **在 Windows 下使用 BOM，否则不使用 BOM**：这个选项是针对跨平台开发的一种折衷方案。它根据操作系统的不同自动决定是否添加BOM，以适应不同的平台特性。

### 何时使用BOM
- 如果您正在与一些老版本的软件或特定要求BOM存在的系统交互，可能需要选择“包含 BOM”。
- 对于大多数现代应用和跨平台开发，建议选择“不含 BOM”，以保持文件的简洁性和广泛的兼容性。

总之，在IntelliJ IDEA中设置文件编码时考虑是否包含BOM，主要取决于您的具体需求和目标环境的兼容性要求。




# maven

位置：Administrator: 在Windows系统上，这个目录通常位于 C:\Users\Administrator\.m2\repository；
而在Linux或MacOS系统上，则通常位于 /home/<YourUsername>/.m2/repository。这里的波浪号（~）代表当前用户的主目录。

Spring Boot 3.4.5 对应的 Maven 版本可能是 3.8.6
Spring Boot 的简化配置：如果你使用的是 Spring Boot，它自带了一个内嵌的 Maven 插件，可以简化项目的构建和运行过程。Spring Boot 的 spring-boot-maven-plugin 插件可以让你在 pom.xml 中只定义一些基本的依赖，而无需详细配置 Maven。


<dependencies>用于列出项目实际需要的依赖项。
dependencyManagement 元素中，你可以定义依赖项的 groupId、artifactId 和 version，而不需要指定 scope。
这些定义的依赖项版本会在项目的实际依赖解析过程中被使用，除非在子模块中明确指定了不同的版本。

<settings>
    <localRepository>/path/to/local/repo</localRepository>
    <interactiveMode>true</interactiveMode>
    <usePluginRegistry>false</usePluginRegistry>
    <offline>false</offline>
    <pluginGroups>
        <!-- 插件组 -->
    </pluginGroups>
    <servers>
        <!-- 服务器配置 -->
    </servers>
    <mirrors>
        <!-- 镜像配置 -->
    </mirrors>
    <profiles>
        <!-- 配置文件 -->
    </profiles>
    <activeProfiles>
        <!-- 激活的配置文件 -->
    </activeProfiles>
</settings>



# Java项目构建工具的演进总结

## 1. **原始阶段（手动管理）**
- **问题**：早期Java项目依赖手动配置类路径（Classpath），开发、编译、打包流程繁琐，依赖管理混乱。
- **工具**：无统一工具，依赖IDE（如Eclipse、NetBeans、IDEA）的基础功能组织流程。

---

## 2. **Ant的出现**
- **改进**：提供编译和依赖管理的自动化脚本，但仍需手动下载和管理JAR包。
- **痛点**：依赖缺失或版本冲突问题严重（如XML解析包需手动寻找）。

---

## 3. **Maven的革命性突破**
- **核心优势**：
   - **标准化生命周期**：定义项目构建阶段（编译、测试、打包等）。
   - **依赖管理**：通过中央仓库（Maven Central）自动解决依赖，无需手动下载。
   - **项目对象模型（POM）**：通过`pom.xml`统一配置项目和插件。
- **影响**：极大提升生产力，成为Java构建工具的标准，并启发了其他语言生态（如Python的pip、Node.js的npm）。
- **缺点**：XML配置冗长复杂，灵活性不足（如文件操作需编写插件）。

---

## 4. **新一代工具：Gradle与sbt**
- **Gradle**：
   - **改进**：基于Groovy脚本，支持声明式语法，配置简洁灵活。
   - **应用**：Spring系列项目、Android开发主流选择。
- **sbt**：
   - **改进**：基于Scala脚本，适合Scala和复杂Java项目。
   - **特点**：增量编译、交互式命令行。
- **共同优势**：
   - 兼容Maven仓库，保留依赖管理优势。
   - 脚本化配置替代XML，简化扩展和自定义任务。


Java构建工具的演进本质是**从手动到自动化、从繁琐到标准化、从XML到脚本化**的优化过程。Maven解决了依赖管理和生命周期标准化的问题，而Gradle/sbt在此基础上通过脚本化提升了灵活性和开发体验。未来，构建工具将进一步与云原生、容器化技术深度集成，持续释放开发者的生产力。



## **sbt（Scala Build Tool）** 确实是 **Play Framework** 项目的默认构建工具。以下是更详细的解释：

---

### **sbt 与 Play Framework 的关系**
1. **sbt 的定位**：
   - sbt 是专为 **Scala 和 Java 项目**设计的构建工具，支持依赖管理、编译、测试、打包等全生命周期操作。
   - 其核心优势是 **基于 Scala 脚本的灵活配置**（`build.sbt` 文件），以及高效的增量编译能力。

2. **Play Framework 的选择**：
   - Play 是一个现代化的 **全栈 Web 框架**，支持 Scala 和 Java。
   - Play 官方选择 sbt 作为默认构建工具，原因包括：
      - **与 Scala 生态深度集成**：Play 的许多特性（如路由、模板编译）需要与 Scala 工具链无缝协作。
      - **高效开发体验**：sbt 的增量编译和热重载（`sbt run`）非常适合 Play 这类需要频繁修改和调试的 Web 项目。
      - **插件生态**：sbt 有丰富的插件（如 `sbt-play`）可直接支持 Play 的特定功能（如路由生成、资产打包）。

3. **sbt 在 Play 项目中的具体作用**：
   - **依赖管理**：通过 `build.sbt` 声明 Play 框架本身及其他库的依赖（从 Maven Central 或私有仓库自动拉取）。
   - **编译与热重载**：运行 `sbt run` 启动开发服务器，修改代码后自动重新编译并刷新页面。
   - **打包部署**：使用 `sbt dist` 生成可部署的 ZIP 包（包含所有依赖和启动脚本）。
   - **自定义任务**：可通过 sbt 脚本扩展构建流程（如生成数据库迁移脚本）。

- **sbt 是 Play Framework 的“官方搭档”**，两者在设计理念和工具链上高度契合。
- 如果你使用 Play 开发 Web 应用，**sbt 是最优选择**，它能最大化框架特性和开发效率。
- 对于纯 Java 项目，Gradle 或 Maven 可能更合适；但在 Scala 或 Play 生态中，sbt 仍是主流。


# Knife4jAutoConfiguration: Did not match:

Spring Boot 自动配置过程中由于某些条件不满足而未启用对应自动配置类的提示信息。下面是对每条日志的解释


#  `@AiService` 注解的工作原理及其应用场景有一个更全面的理解。以下是总结：

### 注解定义与特性

- **定义**: `@AiService` 是一个自定义注解，通过 `@interface` 关键字定义。
- 它使用了 `@Target(ElementType.TYPE)` 限制其只能应用于类或接口声明，
- 并通过 `@Retention(RetentionPolicy.RUNTIME)` 确保该注解在运行时可通过反射访问。

### 应用场景与目的

- **标记服务组件**: 这个注解通常用于标识某些特定的类作为“AI服务”。如果是在Spring框架中，
- 它可能意味着这些被标注的类是服务层的一部分，可以被Spring的IoC容器管理。

### 工作原理

1. **应用注解**: 开发者将 `@AiService` 注解应用于某个类上，如：
   ```java
   @AiService
   public class MyAIService {
       // 类的内容
   }
   ```

2. **利用反射读取注解**: 在程序运行时，可以通过Java的反射API检查某个类是否被标记了 `@AiService` 注解，并根据此信息采取行动。例如：
   ```java
   Class<?> clazz = MyAIService.class;
   if (clazz.isAnnotationPresent(AiService.class)) {
       AiService aiServiceAnnotation = clazz.getAnnotation(AiService.class);
       // 根据注解进行相应的处理
   }
   ```

3. **框架集成**: 在支持依赖注入和面向切面编程（如Spring）的框架中，`@AiService` 可能被用来自动检测并注册服务。
4. 框架会扫描项目中的组件，自动发现带有 `@AiService` 的类，并将其注册为Spring上下文中的bean，以便其他组件可以依赖注入使用。

### 总结

`@AiService` 注解本身并不直接影响程序的行为，它的价值在于作为一种元数据，允许开发者和框架基于这些信息执行特定操作。
通过Java的反射机制，可以在运行时动态地获取注解信息，从而实现诸如自动化配置、依赖注入等高级功能。
这使得代码更加简洁且易于维护，尤其是在大型项目或微服务架构中。


# 注入

```
qwen:
  chat:
    memory:
      max-messages: 10
```

```

1. @Value
   适用于注入单个配置项。

@Component
public class MyService {

    @Value("${qwen.chat.memory.max-messages}")
    private int maxMessages;

    public void print() {
        System.out.println("Max Messages: " + maxMessages);
    }
}
2. @Component + @ConfigurationProperties
   @Component
   @ConfigurationProperties(prefix = "qwen.chat.memory")
   public class QwenChatMemoryConfig {

   private int maxMessages;

   // Getter and Setter
   public int getMaxMessages() {
   return maxMessages;
   }

   public void setMaxMessages(int maxMessages) {
   this.maxMessages = maxMessages;
   }
   }

@Autowired
private QwenChatMemoryConfig qwenChatMemoryConfig;


3. 构造函数注入 + @Value
   @Component
   public class MyService {

   private final int maxMessages;

   public MyService(@Value("${qwen.chat.memory.max-messages}") int maxMessages) {
   this.maxMessages = maxMessages;
   }

   public void print() {
   System.out.println("Max Messages: " + maxMessages);
   }
   }



4. @ConfigurationProperties + @EnableConfigurationProperties
@ConfigurationProperties(prefix = "qwen.chat.memory")
public class QwenChatMemoryConfig {

    private int maxMessages;

    public int getMaxMessages() {
        return maxMessages;
    }

    public void setMaxMessages(int maxMessages) {
        this.maxMessages = maxMessages;
    }
}


@SpringBootApplication
@EnableConfigurationProperties(QwenChatMemoryConfig.class)
public class Application {
public static void main(String[] args) {
SpringApplication.run(Application.class, args);
}
}


@Autowired
private QwenChatMemoryConfig qwenChatMemoryConfig;
```

```sql
CREATE DATABASE `guiguxiaozhi`;
USE `guiguxiaozhi`;
CREATE TABLE `appointment` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`username` VARCHAR(50) NOT NULL,
`id_card` VARCHAR(18) NOT NULL,
`department` VARCHAR(50) NOT NULL,
`date` VARCHAR(10) NOT NULL,
`time` VARCHAR(10) NOT NULL,
`doctor_name` VARCHAR(50) DEFAULT NULL,
PRIMARY KEY (`id`)
)
```


| 字段名         | 类型           | 是否为空 | 描述               |
|----------------|----------------|----------|--------------------|
| `id`           | BIGINT         | NOT NULL | 主键，自增         |
| `username`     | VARCHAR(50)    | NOT NULL | 用户姓名           |
| `id_card`      | VARCHAR(18)    | NOT NULL | 身份证号           |
| `department`   | VARCHAR(50)    | NOT NULL | 预约科室           |
| `date`         | VARCHAR(10)    | NOT NULL | 预约日期（格式如：YYYY-MM-DD） |
| `time`         | VARCHAR(10)    | NOT NULL | 预约时间（格式如：HH:mm 或时间段） |
| `doctor_name`  | VARCHAR(50)    | 可为 NULL | 指定医生姓名       |

NOT NULL：字段不允许为空。
AUTO_INCREMENT：自动递增，通常用于主键。
PRIMARY KEY：主键约束，确保该字段值唯一且非空。
DEFAULT NULL：如果未提供值，则默认为 NULL。


RAG 过程分为 2 个不同的阶段：索引和检索