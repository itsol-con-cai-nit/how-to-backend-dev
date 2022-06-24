# Spring Boot JWT

![](https://img.shields.io/badge/build-success-brightgreen.svg)

***

# Stack

![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)

***

# File structure

```
spring-boot-jwt/
 │
 ├── src/main/java/
 │   └── quankm
 │       ├── configuration
 │       │   └── SwaggerConfig.java
 │       │
 │       ├── controller
 │       │   └── UserController.java
 │       │
 │       ├── dto
 │       │   ├── UserDataDTO.java
 │       │   └── UserResponseDTO.java
 │       │
 │       ├── exception
 │       │   ├── CustomException.java
 │       │   └── GlobalExceptionController.java
 │       │
 │       ├── model
 │       │   ├── AppUserRole.java
 │       │   └── AppUser.java
 │       │
 │       ├── repository
 │       │   └── UserRepository.java
 │       │
 │       ├── security
 │       │   ├── JwtTokenFilter.java
 │       │   ├── JwtTokenFilterConfigurer.java
 │       │   ├── JwtTokenProvider.java
 │       │   ├── MyUserDetails.java
 │       │   └── WebSecurityConfig.java
 │       │
 │       ├── service
 │       │   └── UserService.java
 │       │
 │       └── JwtAuthServiceApp.java
 │
 ├── src/main/resources/
 │   └── application.yml
 │
 ├── .gitignore
 ├── LICENSE
 ├── mvnw/mvnw.cmd
 ├── README.md
 └── pom.xml
```

***

# Giới thiệu

Bài viết dưới đây giới thiệu qua về JWT (Json web token) bạn có thể tìm hiểu chi tiết tại [**jwt.io**](https://jwt.io)!

## JSON Web Token là gì?

Mã thông báo web JSON (JWT) là một tiêu chuẩn mở ([RFC 7519](https://www.rfc-editor.org/info/rfc7519)) định nghĩa một cách nhỏ gọn và khép kín để truyền thông tin an toàn giữa các bên như một đối tượng JSON. Thông tin này có thể được xác minh và đáng tin cậy vì nó được ký điện tử. JWT có thể được ký bằng cách sử dụng bí mật (với thuật toán [HMAC](https://filegi.com/tech-term/hashed-message-authentication-code-hmac-10128/#:~:text=M%E1%BB%99t%20m%C3%A3%20x%C3%A1c%20th%E1%BB%B1c%20th%C3%B4ng,th%E1%BB%A9c%20ph%C3%A2n%20t%C3%ADch%20m%E1%BA%ADt%20m%C3%A3.)) hoặc cặp khóa public/private bằng [RSA](https://manhhomienbienthuy.github.io/2017/02/20/he-ma-hoa-rsa-va-chu-ky-so.html).

Giải thích thêm một số khái niệm của định nghĩa này.

**Compact**: Do kích thước nhỏ hơn, JWT có thể được gửi qua URL, tham số POST hoặc bên trong tiêu đề HTTP. Ngoài ra, kích thước nhỏ hơn có nghĩa là đường truyền nhanh.

**Self-contained**: Payload contains chứa tất cả thông tin cần thiết về người dùng, tránh việc phải truy vấn cơ sở dữ liệu nhiều lần.

## Khi nào nên sử dụng JSON Web Tokens?

Dưới đây là một số tình huống mà JWT hữu ích:

**Authentication**: Đây là tình huống phổ biến nhất để sử dụng JWT. Khi người dùng đã đăng nhập, mỗi yêu cầu tiếp theo sẽ bao gồm JWT, cho phép người dùng truy cập các tuyến đường, dịch vụ và tài nguyên được phép với mã thông báo đó. Đăng nhập một lần là một tính năng sử dụng rộng rãi JWT ngày nay, vì chi phí nhỏ và khả năng dễ dàng sử dụng trên các miền khác nhau.

**Information Exchange**: Mã thông báo web JSON là một cách tốt để truyền thông tin giữa các bên một cách an toàn. Bởi vì JWT có thể được ký — ví dụ: sử dụng public/private — bạn có thể chắc chắn rằng người gửi là những người họ nói. Ngoài ra, vì chữ ký được tính bằng cách sử dụng tiêu đề và trọng tải, bạn cũng có thể xác minh rằng nội dung không bị giả mạo.

## Cấu trúc của JSON Web Token?

JSON Web Token bao gồm ba phần được phân tách bằng dấu chấm (.) Là:

1. Header
2. Payload
3. Signature

Do đó, một JWT thường trông giống như sau.

`xxxxx`.`yyyyy`.`zzzzz`

Hãy chia nhỏ các phần khác nhau.

**Header**

Header thường bao gồm hai phần: Loại mã thông báo, là JWT và thuật toán băm đang được sử dụng, chẳng hạn như HMAC SHA256 hoặc RSA.

Ví dụ:

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

Sau đó, JSON này được mã hóa Base64Url để tạo thành phần đầu tiên của JWT.

**Payload**

Phần thứ hai của JWT là Payload, chứa các xác nhận quyền sở hữu. Xác nhận quyền sở hữu là tuyên bố về một thực thể (thường là người dùng) và siêu dữ liệu bổ sung. Có ba loại xác nhận quyền sở hữu: xác nhận quyền sở hữu dành riêng (reserved), công khai (public) và riêng tư (private).

- **Reserved claims**: Đây là một tập hợp các xác nhận quyền sở hữu được xác định trước, không bắt buộc nhưng được khuyến nghị, để cung cấp một tập hợp các xác nhận quyền sở hữu hữu ích, có thể tương tác. Một số trong số đó là: Iss (issuer - nhà phát hành), exp (expiration time - thời gian hết hạn), sub (subject - chủ đề), aud (audience - khán giả) và những thứ khác.

> Lưu ý rằng tên xác nhận quyền sở hữu chỉ dài ba ký tự miễn là JWT có nghĩa là ngắn gọn.

- **Public claims**: Chúng có thể được xác định theo ý muốn bởi những người sử dụng JWT. Nhưng để tránh va chạm, chúng nên được xác định trong Cơ quan đăng ký mã thông báo web IANA JSON hoặc được định nghĩa là một URI có chứa không gian tên chống va chạm.

- **Private claims**: Đây là những yêu cầu tùy chỉnh được tạo ra để chia sẻ thông tin giữa các bên đồng ý sử dụng chúng.

Ví dụ về tải trọng có thể là:

```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

Payload sau đó được mã hóa Base64Url để tạo thành phần thứ hai của JSON web token.

**Signature**

Để tạo phần signature, bạn phải lấy header được mã hóa, payload được mã hóa, bí mật, thuật toán được chỉ định trong header và ký tên đó.

Ví dụ: nếu bạn muốn sử dụng thuật toán HMAC SHA256, chữ ký sẽ được tạo theo cách sau:

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```

Chữ ký được sử dụng để xác minh rằng người gửi JWT là người mà nó nói và để đảm bảo rằng thư không bị thay đổi trong quá trình này.
Kết hợp tất cả lại với nhau.

Đầu ra là ba chuỗi Base64 được phân tách bằng dấu chấm có thể dễ dàng chuyển trong môi trường HTML và HTTP, đồng thời nhỏ gọn hơn khi so sánh với các tiêu chuẩn dựa trên XML như SAML.

Phần sau cho thấy một JWT có mã hóa header và payload trước đó, và nó được ký bằng bí mật. JWT được mã hóa.

![](https://camo.githubusercontent.com/a56953523c443d6a97204adc5e39b4b8c195b453/68747470733a2f2f63646e2e61757468302e636f6d2f636f6e74656e742f6a77742f656e636f6465642d6a7774332e706e67)

## Cách hoạt động của JSON Web Tokens?
Trong xác thực, khi người dùng đăng nhập thành công bằng thông tin đăng nhập của họ, JWT sẽ được trả lại và phải được lưu cục bộ (thường trong bộ nhớ cục bộ, nhưng cũng có thể sử dụng cookie), thay vì cách tiếp cận truyền thống là tạo phiên trong máy chủ và trả về một cookie.

Bất cứ khi nào người dùng muốn truy cập vào một tuyến đường hoặc tài nguyên được bảo vệ, tác nhân người dùng phải gửi JWT, thường là trong tiêu đề ủy quyền bằng cách sử dụng lược đồ Bearer. Nội dung của tiêu đề sẽ giống như sau:

`Authorization: Bearer <token>`

Đây là cơ chế xác thực không trạng thái vì trạng thái người dùng không bao giờ được lưu trong bộ nhớ máy chủ. Các tuyến được bảo vệ của máy chủ sẽ kiểm tra JWT hợp lệ trong tiêu đề Ủy quyền và nếu có, người dùng sẽ được phép truy cập các tài nguyên được bảo vệ. Vì JWT là độc lập, tất cả thông tin cần thiết đều có ở đó, giảm nhu cầu truy vấn cơ sở dữ liệu nhiều lần.

Điều này cho phép bạn hoàn toàn dựa vào các API dữ liệu không có trạng thái và thậm chí đưa ra yêu cầu đối với các dịch vụ hạ lưu. Không quan trọng miền nào đang phục vụ các API của bạn, vì vậy chia sẻ tài nguyên nhiều nguồn gốc (CORS) sẽ không thành vấn đề vì nó không sử dụng cookie.

Sơ đồ sau đây cho thấy quá trình này:

![](https://camo.githubusercontent.com/5871e9f0234542cd89bab9b9c100b20c9eb5b789/68747470733a2f2f63646e2e61757468302e636f6d2f636f6e74656e742f6a77742f6a77742d6469616772616d2e706e67)

# Tóm tắt xác thực JWT

Lược đồ xác thực dựa trên mã thông báo đã trở nên vô cùng phổ biến trong thời gian gần đây, vì chúng cung cấp các lợi ích quan trọng khi so sánh với các sessions / cookie:

- [CORS](https://topdev.vn/blog/cors-la-gi/)
- Không cần bảo vệ [CSRF](https://viblo.asia/p/tan-cong-csrf-va-cach-phong-chong-m68Z0mnAlkG)
- Tích hợp tốt hơn với điện thoại di động
- Giảm tải trên máy chủ ủy quyền
- Không cần cửa hàng phiên phân phối

**Một số đánh đổi khi thực hiện với cách này:**

- Dễ bị tấn công [XSS](https://viblo.asia/p/ky-thuat-tan-cong-xss-va-cach-ngan-chan-YWOZr0Py5Q0) hơn
- Mã thông báo truy cập có thể chứa các xác nhận quyền sở hữu đã lỗi thời (ví dụ: Khi một số đặc quyền của người dùng bị thu hồi)
- Mã thông báo truy cập có thể tăng kích thước trong trường hợp số lượng yêu cầu tăng lên
- API tải xuống tệp có thể khó triển khai
- Tình trạng vô quốc tịch và thu hồi thực sự loại trừ lẫn nhau

**Quy trình xác thực JWT rất đơn giản**

1. Người dùng nhận được Refresh và Access tokens bằng cách cung cấp thông tin đăng nhập cho máy chủ ủy quyền.
2. Người dùng gửi Access tokens với mỗi yêu cầu truy cập tài nguyên API được bảo vệ.
3. Access token được ký và chứa danh tính người dùng (ví dụ: user id) và các yêu cầu ủy quyền.

Điều quan trọng cần lưu ý là các yêu cầu ủy quyền sẽ được bao gồm với Access tokens.

**Tại sao nó quan trọng?**

Vâng, giả sử rằng các xác nhận quyền sở hữu (ví dụ: đặc quyền của người dùng trong cơ sở dữ liệu) được thay đổi trong thời gian tồn tại của Access tokens. Những thay đổi đó sẽ không có hiệu lực cho đến khi Access tokens mới được phát hành. Trong hầu hết các trường hợp, đây không phải là vấn đề lớn, vì Access tokens có thời gian tồn tại ngắn. Nếu không, hãy chọn mẫu mã thông báo mờ đục (opaque token pattern).

# Chi tiết triển khai

Hãy xem cách chúng ta có thể triển khai xác thực dựa trên JWT token bằng cách sử dụng Java và Spring, trong khi cố gắng sử dụng lại hành vi Spring security default nếu chúng ta có thể. Spring Security framework đi kèm với các lớp bổ trợ đã xử lý các cơ chế ủy quyền như: session cookies, HTTP Basic và HTTP Digest. Tuy nhiên, nó thiếu hỗ trợ gốc dành cho JWT, và chúng ta cần phải cố gắng hết sức để làm cho nó hoạt động.

## H2 DB
Bản demo này hiện đang sử dụng cơ sở dữ liệu H2 có tên ** test_db ** nên ta có thể chạy nó nhanh chóng và out-of-the-box mà không cần cấu hình nhiều. Nếu bạn muốn kết nối với một cơ sở dữ liệu khác, bạn phải chỉ định kết nối trong tệp `application.yml` bên trong thư mục tài nguyên. Lưu ý rằng `hibernate.hbm2ddl.auto = create-drop` sẽ drop và create một cơ sở dữ liệu sạch mỗi khi triển khai (bạn có thể thay đổi nó nếu bạn đang sử dụng nó trong một dự án thực). Dưới đây là ví dụ từ dự án, hãy xem bạn có thể hoán đổi nhận xét trên thuộc tính `url` và` dialect ` để sử dụng cơ sở dữ liệu MySQL của riêng bạn dễ dàng như thế nào:

```yml
spring:
  datasource:
    url: jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    # url: jdbc:mysql://localhost:3306/user_db
    username: root
    password: root
  tomcat:
    max-wait: 20000
    max-active: 50
    max-idle: 20
    min-idle: 15
  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect
        # dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
        id:
          new_generator_mappings: false
```

## Core Code

1. `JwtTokenFilter`
2. `JwtTokenFilterConfigurer`
3. `JwtTokenProvider`
4. `MyUserDetails`
5. `WebSecurityConfig`

**JwtTokenFilter**

`JwtTokenFilter` filter được áp dụng cho từng API (` / ** `) ngoại trừ signin token endpoint (` / users / signin`) và singup endpoint (`/ users / signup`).

Bộ lọc này có các trách nhiệm sau:

1. Kiểm tra mã thông báo truy cập trong Authorization header. Nếu Access token được tìm thấy trong header, hãy ủy quyền xác thực cho `JwtTokenProvider` nếu không sẽ ném ngoại lệ xác thực.
2. Đưa ra các chiến lược thành công hoặc thất bại dựa trên kết quả của quá trình xác thực được thực hiện bởi JwtTokenProvider

Hãy đảm bảo rằng `chain.doFilter (request, response)` được gọi khi xác thực thành công. Bạn muốn xử lý yêu cầu để chuyển sang bộ lọc tiếp theo, bởi vì một bộ lọc cuối cùng * FilterSecurityInterceptor # doFilter * chịu trách nhiệm thực sự gọi phương thức trong bộ điều khiển của bạn đang xử lý tài nguyên API được yêu cầu.

```java
String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenProvider.validateToken(token)) {
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
```

**JwtTokenFilterConfigurer**

Thêm `JwtTokenFilter` vào `DefaultSecurityFilterChain` của spring boot security.

```java
JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
```

**JwtTokenProvider**

`JwtTokenProvider` có trách nhiệm sau:

1. Xác minh chữ ký của mã thông báo truy cập.
2. Trích xuất xác nhận quyền sở hữu và ủy quyền từ Access token và sử dụng chúng để tạo UserContext.
3. Nếu Access token không đúng định dạng, hết hạn hoặc đơn giản là nếu mã thông báo không được ký bằng khóa ký thích hợp thì ngoại lệ xác thực sẽ bị ném.

**MyUserDetails**

Triển khai `UserDetailsService` để xác định hàm * loadUserbyUsername * tùy chỉnh của riêng chúng ta. Giao diện `UserDetailsService` được sử dụng để truy xuất dữ liệu liên quan đến người dùng. Nó có một phương thức tên là * loadUserByUsername *, phương thức này tìm một thực thể người dùng dựa trên tên người dùng và có thể được ghi đè để tùy chỉnh quá trình tìm kiếm người dùng.

Nó được sử dụng bởi `DaoAuthenticationProvider` để tải thông tin chi tiết về người dùng trong quá trình xác thực.

**WebSecurityConfig**

Lớp `WebSecurityConfig` extends ` WebSecurityConfigurerAdapter` để cung cấp cấu hình bảo mật tùy chỉnh.

Các bean sau được cấu hình và khởi tạo trong lớp này:

1. `JwtTokenFilter`
3. `PasswordEncoder`

Ngoài ra, bên trong phương thức `WebSecurityConfig # configure (HttpSecurity http)`, chúng ta sẽ định cấu hình các mẫu để xác định các điểm cuối API được bảo vệ / không được bảo vệ (protected/unprotected). Xin lưu ý rằng chúng tôi đã tắt tính năng bảo vệ CSRF vì chúng tôi không sử dụng Cookie.

```java
// Disable CSRF (cross site request forgery)
http.csrf().disable();

// No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

// Entry points
        http.authorizeRequests()//
        .antMatchers("/users/signin").permitAll()//
        .antMatchers("/users/signup").permitAll()//
        // Disallow everything else..
        .anyRequest().authenticated();

// If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");

// Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

// Optional, if you want to test the API from a browser
// http.httpBasic();
```

# Cách sử dụng code này?
1. Đảm bảo rằng bạn đã cài đặt [Java 8] (https://www.java.com/download/) và [Maven] (https://maven.apache.org)

2. Fork repository này và clone nó

```
$ git clone https://github.com/<your-user>/spring-boot-jwt
```

3. Điều hướng vào thư mục

```
$ cd spring-boot-jwt
```

4. Cài đặt các phụ thuộc

```
$ mvn install
```

5. Chạy dự án

```
$ mvn spring-boot:run
```

6. Điều hướng đến `http: // localhost: 9090 / swagger-ui.html` trong trình duyệt của bạn để kiểm tra mọi thứ đang hoạt động chính xác. Bạn có thể thay đổi cổng mặc định trong tệp `application.yml`

```yml
server:
  port: 9090
```

7. Thực hiện yêu cầu GET tới `/ users / me` để kiểm tra xem bạn chưa được xác thực. Bạn sẽ nhận được phản hồi với `403` kèm theo thông báo` Truy cập bị từ chối` vì bạn chưa đặt mã thông báo JWT hợp lệ của mình

```
$ curl -X GET http://localhost:9090/users/me
```

8. Thực hiện yêu cầu POST tới `/ users / signin` với người dùng quản trị mặc định mà chúng tôi đã lập trình tạo để nhận JWT token hợp lệ

```
$ curl -X POST 'http://localhost:9090/users/signin?username=admin&password=admin'
```

9. Thêm JWT token làm tham số Header và thực hiện lại yêu cầu GET ban đầu cho `/ users / me`

```
$ curl -X GET http://localhost:9090/users/me -H 'Authorization: Bearer <JWT_TOKEN>'
```

10. Và thế là xong, chúc mừng! Bạn sẽ nhận được phản hồi tương tự cho câu trả lời này, nghĩa là bạn hiện đã được xác thực

```javascript
{
    "id": 1,
        "username": "admin",
        "email": "admin@email.com",
        "roles": [
        "ROLE_ADMIN"
    ]
}
```

# Tham khảo tại

- Github [https://github.com/murraco/spring-boot-jwt](https://github.com/murraco/spring-boot-jwt)