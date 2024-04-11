# 🛒 쇼핑몰 프로젝트

**NHN Academy**에서 배운 내용을 기반으로 Java와 Servlet/JSP를 활용하여 쇼핑몰 프로젝트를 개발하였습니다.

## 🎨 개발 환경
- **Frontend**: HTML, CSS, Bootstrap
- **Backend**: Java, Servlet/JSP, MySQL(JDBC)
- **Server**: Tomcat

### ER 다이어그램
![ER-Diagram](./submit/erd/ERD.png)

## 🎈 개발 내용

### MVC Pattern
효율적인 개발을 위해 Servlet/JSP를 활용하여 MVC(Model, View, Controller) 패턴을 구현하였습니다.


### Custom Annotation
Java Reflection API를 활용하여 Annotation Custom을 수행하여 스프링에서 제공하는 `@RequestMapping`과 유사한 어노테이션을 구현하였습니다. 이를 통해 모든 Controller를 효율적으로 관리하였습니다.

_@RequestMapping Annotation_
```java

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
  enum Method{
      POST,GET
  }

  String[] value();
  Method method() default Method.GET;
}
```

Reflection API를 이용하여 모든 컨트롤러를 가져오는 코드

```java
try {
  for (Class<?> clazz : c) {
  RequestMapping annotation = clazz.getDeclaredAnnotation(RequestMapping.class);
  if (Objects.nonNull(annotation)) {
    String[] value = annotation.value();
    String method = annotation.method().name();

                    for (String url : value) {
                        String key = getKey(method, url);
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        beanMap.put(key, instance);
                    }
                }
            }
        }
```

### ThreadLocal
효율적인 JDBC Connection 관리 및 thread-safe한 환경을 구성하기 위해 ThreadLocal 변수에 Connection을 할당하는 방식을 채택하였습니다. 이를 통해 트랜잭션 문제를 최소화하고 안정적인 환경을 구축할 수 있었습니다.

```java
public class DbConnectionThreadLocal {
  private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
  private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(() -> false);
  ...
}
```

---

## ⚔ 주요 기능

### **사용자**

1. 회원가입을 통해 아이디를 생성할 수 있습니다.

![회원가입](https://github.com/f1v3-dev/shoppingmall/assets/84575041/0e6535ba-e0c2-466a-9a82-b8c73e1a7e90)
    

2. 로그인이 가능합니다.

![로그인](https://github.com/f1v3-dev/shoppingmall/assets/84575041/ab76ab68-b921-4440-904d-2e7e26d3ca70)

2-1. 로그인 로직

![로그인 로직](https://github.com/f1v3-dev/shoppingmall/assets/84575041/050c603e-3793-4f12-89c3-cb9bceddc05a)


3. 카테고리별로 상품을 조회할 수 있습니다.

![상품 조회](https://github.com/f1v3-dev/shoppingmall/assets/84575041/905a0d13-9ba7-4190-ba94-f6b39ca2a8b7)

4. 최근 본 상품은 쿠키를 통해 관리됩니다.

![최근 본 상품](https://github.com/f1v3-dev/shoppingmall/assets/84575041/2f654970-d635-4312-a164-d4490beb1cb8)

5. 상품의 상세정보를 확인하고 장바구니에 담거나 바로 구매할 수 있습니다.

![상품 상세정보](https://github.com/f1v3-dev/shoppingmall/assets/84575041/23f1752c-8a73-43f4-a888-b8e422f540b9)

    

### **회원**

1. 마이페이지에서 회원 정보를 수정하거나 탈퇴할 수 있습니다.

![마이페이지](https://github.com/f1v3-dev/shoppingmall/assets/84575041/79462887-e95b-4979-83bd-6ac781fcd392)   

2. 상품을 장바구니에 담거나 구매할 수 있습니다.

![장바구니](https://github.com/f1v3-dev/shoppingmall/assets/84575041/3086c177-a6e1-43d1-8a21-6d15ca78923a)

![구매하기](https://github.com/f1v3-dev/shoppingmall/assets/84575041/09b0e493-73d5-47fa-94b1-c76ce63e550c)


3. 주문 내역을 확인할 수 있습니다.

![주문 내역](https://github.com/f1v3-dev/shoppingmall/assets/84575041/24894701-6a59-44d0-8c66-d6b14bc7f983)

  
4. 자신의 주소를 등록하고 관리할 수 있습니다.

![주소 관리](https://github.com/f1v3-dev/shoppingmall/assets/84575041/c3193748-43c0-4dce-b939-72500d17c359)

### **관리자**

1. 카테고리를 추가/수정할 수 있습니다.

![카테고리 추가/수정](https://github.com/f1v3-dev/shoppingmall/assets/84575041/f04b186e-b48c-4a39-9b13-c62a049d2120)

2. 상품을 추가/수정할 수 있습니다.

![상품 추가/수정](https://github.com/f1v3-dev/shoppingmall/assets/84575041/27914d54-81d1-45c4-837a-773719ddac79)

3. 회원 리스트를 관리할 수 있습니다.

![회원 리스트](https://github.com/f1v3-dev/shoppingmall/assets/84575041/7c6fdb11-6cb9-4c75-8e86-ef32a597e366)

