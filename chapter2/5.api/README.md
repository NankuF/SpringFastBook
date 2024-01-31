# RESTApi

Простейшее апи реализуется в контроллере, с добавлением аннотации @ResponseBody<br>
@ResponseBody - возвращает тело ответа, вместо поиска и возврата view.<br>
```java
@Controller
public class ExampleController {
    @GetMapping("/example")
    @ResponseBody
    public String example(){
        return "this is simple text";
    }
}
```
Чтобы не писать в методах @ResponseBody, придумали @RestController
```java
@RestController
public class RestExampleController {
    @GetMapping("/restexample")
    public String restExample(){
        return "this is simple rest text";
    }
}
```
В примерах выше результат возврата будет одинаковый - строка.<br>
Если возвращать не строку, а POJO объект -> вернет json.
```java
    @GetMapping("/country")
    @ResponseBody
    public Country country() {
        var country = Country.of("SuperRussia", 250);
        return country;
    }

    @GetMapping("/restcountry")
    public Country restCountry() {
        var country = Country.of("Russia", 150);
        return country;
    }
```
Чтобы модифицировать возвращаемый response, придумали ResponseEntity.
```java
    @GetMapping("/france")
    @ResponseBody
    public ResponseEntity<Country>  getFrance() {
        var country= Country.of("France", 30);
        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .header("continent", "Europe")
            .header("capital", "Paris")
            .header("favorite_food", "cheese and wine")
            .body(country);
    }

    @GetMapping("/restfrance")
    public ResponseEntity<Country>  getRestFrance() {
        var country= Country.of("France", 30);
        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .header("continent", "Europe")
            .header("capital", "Paris")
            .header("favorite_food", "cheese and wine")
            .body(country);
    }
```
Вернет json с добавлением в заголовок трех пар ключ-значение.