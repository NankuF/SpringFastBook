# Аспекты и аспектно-ориентированное программирование
Аспект - похож на декоратор. Так же модифицирует обертываемый метод.<br>
Однако в отличие от декоратора, у него больше возможностей.<br>
Может:<br>
1. Не запускать перехваченный метод и вернуть любой ответ, будто метод был запущен.
2. Перехватить и при необходимости подменить аргументы вызываемого метода.
3. Как декоратор может выполнять различную логику, дополняя вызываемый метод.

Имеет 5 аннотаций:<br>
@Around - сработает до и после срабатывания метода.<br>
@Before - сработает перед методом.<br>
@After - сработает в любом случае после метода.<br>
@AfterReturning - сработает после возврата методом рез-та.<br>
@AfterTrowing - сработает если будет ошибка в методе.<br>

Метод, который надо перехватить помечается либо меткой на языке AspectJ (@PointCut(...)), либо специально создается аннотация, и аспекту говорится, чтобы перехватывал методы помеченные этой аннотацией.

Алгоритм работы с аспектом:
1. Создать аспект
   ```java
    @Aspect
    public class SecurityAspect {

    private Logger logger = Logger.getLogger(SecurityAspect.class.getName());

    @Around(value = "@annotation(ToLog)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Security Aspect: Calling the intercepted method");
        //здесь выполняется перехватываемый метод. Если не указать - перехватываемый метод не будет выполняться.
        Object returnedValue = joinPoint.proceed();
        logger.info("Security Aspect: Method executed and returned " + returnedValue);
        return returnedValue;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    }
   ```
2. Зарегистрировать его как бин - @Component либо @Bean.
3. Создать аннотацию и разместить ее над перехватываемым методом, либо указать срез @PointCut над выполняемым методом.
   ```java
   // пример аннотации
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ToLog(){}

    // пример помечаемого метода где-то в классе
    @ToLog
    public String publishComment(Comment comment) {
        logger.info("Publishing comment:" + comment.getText());
        return "SUCCESS";
    }
   ```
4. Если нужен определенный порядок вызова аспектов - повесить на них аннотацию @Order(int), где int - число. Чем меньше, тем раньше вызовется аспект, по отношению к другим аспектам.
    ```java
    @Aspect  // это аспект
    @Order(1) // выполнится первым среди аспектов
    @Component // регистрирую как бин
    public class SecurityAspect {
        private Logger logger = Logger.getLogger(SecurityAspect.class.getName());

    @Around("@annotation(ToLog)") // сработает на метод, отмеченный аннотацией @ToLog
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Start running securityAspect");
        joinPoint.proceed();
        logger.info("End running securityAspect");
    }
    }
    ```