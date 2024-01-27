# Использование интерфейсов как абстракций

Цель этого примера:
1. Показать как Spring работает с интерфейсами.
2. Показать как выбирать из нескольких бинов, имплементирующих один интерфейс.

### Как Spring работает с интерфейсами
Когда Spring сталкивается с зависимостью в виде интерфейса, он ищет в контексте экземпляр класса(бин), который реализует этот интерфейс и вставляет его в зависимый бин (DI).
Устанавливать @Component на интерфейс бессмысленно, тк из интерфейса нельзя создать экземпляр объекта. Устанавливать надо на класс реализующий интерфейс.

### Если бинов, реализующий интерфейс, несколько
2 варианта решения:<br>
@Primary над бином<br>
В этом случае при наличии несколько бинов реализующих один интерфейс - будет выбран бин с аннотацией @Primary
```java
@Component
@Primary
public class EmailCommentNotificationProxy{...}
```
@Qualifier в бине, и в зависимом классе вызывающий этот бин.<br>
Здесь мы явно даем имя создаваемому бину, и явно вызываем его в зависимом бине. Spring понимает какой из бинов, реализующий интерфейс, и в каком порядке вставлять в конструктор.
```java
//bean 1
@Component
@Qualifier("EMAIL")  // регистрозависимый. EMAIL и email - разные id бинов!
public class EmailCommentNotificationProxy implements CommentNotificationProxy{...}
//bean 2
@Component
@Qualifier("PUSH")
public class PushCommentNotificationProxy implements CommentNotificationProxy{...}

//dependency bean
@Service
public class CommentService {
    private final CommentNotificationProxy emailCommentNotificationProxy;
    private final CommentNotificationProxy pushCommentNotificationProxy;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(
        @Qualifier("EMAIL")CommentNotificationProxy eCommentNotificationProxy,
        @Qualifier("PUSH")CommentNotificationProxy pCommentNotificationProxy,
            CommentRepository commentRepository) {
        this.emailCommentNotificationProxy = eCommentNotificationProxy;
        this.pushCommentNotificationProxy = pCommentNotificationProxy;
        this.commentRepository = commentRepository;
    }
    }
```
### Про аннотации @Component, @Repository, @Service
@Component, @Repository, @Service - выполняют одну и ту же задачу и указывают Spring, что отмеченный класс является бином.<br>
@Repository, @Service - аннотации, подсказывают разработчику, что аннотируемый класс - выполняет задачу  Сервиса или Репозитория.

### Нэйминг
Сервисы — объекты, задачей которых является реализация сценариев использования. В название принято добавлять Service.<br>
Репозитории — объекты, отвечающие за сохранность данных. Объект, который непосредственно взаимодействует с базой данных, обычно называют репозиторием. В название принято добавлять Repository.<br>
Встречается также термин «объект доступа к данным» (data access object, DAO).<br>
Прокси - объекты, коммуницирующие с чем-то, что находится за пределами приложения. В название принято добавлять Proxy.

CommentService - содержит бизнес-логику взаимодействия с комментариями.
CommentRepository - сохраняет комментарии в БД.
CommentNotificationProxy - отправляет уведомления по разным каналам - пуш, емайл...


// Перечитать 5 главу позже, про scope.