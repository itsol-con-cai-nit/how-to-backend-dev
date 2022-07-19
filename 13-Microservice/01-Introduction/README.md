# 1.1 What is a Microservice

### Introduction

- Trước khi ý tưởng về `microservice` phát triển, hầu hết ứng dụng được xây dựng theo `monolithic architectural style`
- Tất cả UI, business, và database access logic được đóng gói cùng nhau trong a single application duy nhất và đươc
  deploy lên a application server.
- Vấn đề ở đây là kích thước và độ phức tạp của ứng dụng lớn lên. Mỗi lần, một team cần sự thay đổi, toàn bộ ứng dụng
  cần rebuilt, retested and redeployed.
- Khái niệm về một `microservice` ban đầu len lỏi vào ý thức của cộng đồng phát triển phần mềm vào khoảng năm 2014 và là
  một phản hồi trực tiếp đến nhiều thử thách của cố gắng mở rộng quy mô cả về mặt kỹ thuật và tổ chức, các monolithic
  applications.
- Microservices cho phép bạn phân rã một ứng dụng lớn thành các thành phần dễ quản lý với các trách nhiệm được xác định
  hẹp (cụ thể).
  ![Alt text](Image/Figure1.1-MonolithicApplication.png?raw=true "Title")
  ![Alt text](Image/Figure1.2-MicroserviceApplication.png?raw=true "Title")

### A microservice architecture has the following characteristics:

- Application logic được chia thành các thành phần chi tiết nhỏ với ranh giới trách nhiệm được xác định rõ ràng, phối
  hợp để cùng đưa ra giải pháp.
- Mỗi thành phần có `a small domain of responsibility`và được triển khai hoàn toàn độc lập với nhau.
- Microservice giao tiếp dựa trên một vài nguyên tắc cơ bản. (principles, not standards) và sử dụng các giao thức nhẹ
  như HTTP và JSON.
- Việc triển khai kỹ thuật của các service là không liên quan đến nhau vì ứng dụng luôn giao tiếp với một giao thức
  trung lập về công nghệ.
  ![Alt text](Image/Figure1.3-Comparing.png?raw=true "Title")

# 1.2 Why is Spring relevant to microservice ?

- Trong java application, ứng dụng được chia nhỏ thành nhiều classes nơi mỗi class thường có liên kết với nhưng class
  khác trong ứng dụng.
- Những liên kết là lời gọi hàm contructor trực tiếp trong code.
- Một khi code được complied, những điểm liên kết này không thể thay đổi.
- Vấn đề trong một dự án lớn, những liên kết là giòn khi có một sự thay đổi có thể dẫn đến tác động đến mã code khác.
- A dependency injection framework, such as Spring cho phép bạn dễ dàng quản lý project java hơn bằng ngoại hóa(
  externalization provides custom serialization) mối quan hệ giữa các object trong ứng dụng của bạn thông qua quy ước,
  thay vì những object có kết nối cứng nhắc.

`Small, Simple, and Decoupled Services = Scalable, Resilient, and Flexible Applications`

### Using Spring Boot offers the following benefits for our microservices:

- Giảm thời gian phát triển và tăng hiệu quả và năng suất
- Cung cấp HTTP server để chạy web application.
- Cho phép bạn tránh viết nhiều boilerplate code
- Tạo điều kiện tích hợp với Spring Ecosystem (includes Spring Data, Spring Security, Spring Cloud, and more).
- Cung cấp một tập hợp các plugin phát triển khác nhau.

# 1.3 What are we building?

- Để bắt đầu yêu cầu client đầu tiền cần authenticate
  với [`Keycloak`](https://viblo.asia/p/keycloak-spring-boot-bat-dau-voi-keycloak-va-spring-boot-bat-dau-lam-viec-voi-user-trong-realm-3Q75wAP3ZWb)
  để lấy access token. Mỗi khi token được nhận, Client gửi 1 request đến Spring Cloud API Gateway. API Gateway service
  là cổng vào cho toàn bộ architecture.Nó giao tiếp với Eureka service discovery để lấy locations, licensing services và
  sau đó gọi đến các microservice cụ thể.
  ![Alt text](Image/Figure1.4-High-level.png?raw=true "Title")

- Một khi request đến `organization service`, nó validate access token, sau khi validated organization service cập nhật
  và lấy những thông tin và gửi về client as một HTTP response. As an alternative path, một khi the organization
  information được cập nhật, the organization service thêm một message đến Kafka topic .Licensing service nhận thức được
  sự thay đổi.
- Khi message đến licensing service. [Redis](https://viblo.asia/p/redis-la-gi-LzD5dN2OZjY) lưu thông
  trong `Redis’s in-memory database`. Thông qua quá trình này Architecture sử dụng distributed tracing từ Zipkin,
  Elasticsearch, và Logstash để quản lý logs và sử dụng Spring Boot Actuator, Prometheus, and Grafana để hiển thị the
  application metrics.

# 1.4 What exactly is cloud computing?

- Cloud computing là nơi cung cấp các dịch vụ computing và virtualized IT services—databases, networking, software,
  servers, analytics ... Thông qua internet để cung cấp môi trường linh hoạt, bảo mật và dễ sử dụng.
- The cloud computing để người dùng chọn mức độ quản lí thông tin và service những cái mà họ cung
  cấp (` anything as a service.`)
    - Infrastructure as a Service (IaaS) : Nhà cung cấp cung cấp cơ sở hạ tầng cho phép bạn truy cập s computing
      resources such as servers, storage, and networks.( AWS (EC2), Azure Virtual Machines, Google Compute Engine, and
      Kubernetes )
    - Container as a Service (CaaS) : Ảo hóa dựa theo vùng chứa ( such as docker )
    - Platform as a Service (PaaS) : Một môi trường tập trung vào development, execution, and maintenance of the
      application (Google App Engine, Cloud Foundry, Heroku, and AWS Elastic Beanstalk)
    - Function as a Service (FaaS) : Chạy và quản lý các chức năng của ứng dụng mà không phức tạp trong việc xây dựng và
      duy trì cơ sở hạ tầng thường liên quan đến việc phát triển và khởi chạy ứng dụng.
    - Software as a Service (SaaS): Còn được gọi là phần mềm theo yêu cầu. Cho phép người dùng sử dụng một ứng dụng cụ
      thể mà không cần phải triển khai hoặc duy trì nó.
      ![Alt text](Image/Figure1.9-The-different-cloud-computing-models.png?raw=true "Title")

# 1.5 Why the cloud and microservices ?

- Một trong những khái niệm cốt lõi của `a microservice architecture` là mỗi service được đóng gói và deployed độc lập.
  Service instances nên được đưa lên nhanh chóng và chúng không thể phân biệt với nhau.
- Khi viết một microservice, sớm hay muộn bạn sẽ phải quyết định liệu service của bạn được deployed đến 1 trong những
  cái sau đây:

    - Physical server
    - Virtual machine images
    - Virtual container
- Lợi thế của dịch vụ vi mô dựa trên đám mây xoay quanh khái niệm `elasticity`. Các nhà cung cấp dịch vụ đám mây cho
  phép bạn nhanh chóng tạo ra VMs và container chỉ trong vài phút. Nếu các như cầu về công suất bạn giảm xuống bạn có
  thể giảm container để tránh thêm chi phí.
- Sử dụng các nhà cung cấp đám mây để phát triển mang lại cho bạn khả năng mở rộng theo chiều ngang đáng kể.

# 1.6  Microservices are more than writing the code ?

![Alt text](Image/Figure1.5-MicroserviceAreMoreThanBusinessLogic.png?raw=true "Title")

- Right-sized : Cách bạn đảm bảo rằng các dịch vụ nhỏ của bạn có kích thước phù hợp để bạn không phải gánh vác quá nhiều
  trách nhiệm.
- Location transparent : Các bạn quản lý physical details of service invocation. Khi trong một microservice application,
  nhiều service instances có thể nhanh trong khởi động và tắt.
- Resilient : Cách bảo vệ microservice consumers của bạn và toàn vẹn tổng thể ứng dụng của bạn.
- Repeatable : Cách bạn đảm bảo mọi new instance của bạn cung cấp ược đảm bảo có cấu hình và cơ sở mã giống như tất cả
  các instance khác trong quá trình sản xuất.
- Scalable : Cách bạn giảm thiểu sự phụ thuộc trực tiếp giữa các service và đảm bảo bạn có thể mở rộng quy mô.

# 1.7 Core microservice development pattern.

![Alt text](Image/Figure1.11-WhenDesigning.png?raw=true "Title")

- Service granularity : Làm cách nào để bạn tiếp cận việc phân tách miền doanh nghiệp thành microservices để mỗi
  microservice có mức trách nhiệm phù hợp.
- Communication protocols : Các nhà phát triển sẽ giao tiếp với dịch vụ của bạn như thế nào (XML, JSON, AMQP).
- Interface design : Cách bạn cấu trúc dịch vụ của mình.
- Configuration management of service : Làm cách nào để bạn quản lý cấu hình của microservice của mình để nó di chuyển
  giữadifferent environments in the cloud.
- Event processing between services : Bạn làm cách nào để tách microservice của mình bằng các sự kiện để giảm thiểu sự
  phụ thuộc được hardcode giữa các dịch vụ và tăng khả năng `resiliency` ứng dụng của bạn.

# 1.8 Microservice routing patterns.

- The Microservice routing patterns thỏa thuận với cách một client application để cung cấp a microservice discovers the
  location of the service và được chuyển đến nó.
- Trong a cloud-based application, có rất nhiều microservice instances chạy. Để đảm bảo bảo mật, nó được yêu cầu để
  abstract the physical IP address của service đó và có a single point of entry cho service gọi.
    - Service discovery : Với service discovery client application có thể tìm thấy chúng mà không cần mã hóa cứng trong
      những application
    - Service routing : Với an API Gateway, bạn có thể cung cấp a single entry point cho tất service của bạn. Security
      policies and routing rules được áp dụng đồng nhất đến nhiều service và service instances.

  ![Alt text](Image/Figure1.12-ServiceDiscoveryAndServiceRouting.png?raw=true "Title")

# 1.9 Microservice client resiliency

- Bởi vì `microservice architectures are highly distributed`, nên phải cực kì nhạy cảm trong việc ngăn chặn sự cố trong
  a single service(or service instance).
  ![Alt text](Image/Figure1.13-ProtectService.png?raw=true "Title")

- Client-side load balancing : Cách bạn lưu vào bộ nhớ cache vị trí cùa service instance. Để các lệnh gọi đến multiple
  instances của microservice được cân bằng tải.
- Circuit breaker pattern : Các ngăn client tiếp tực gọi vào service đang bị lỗi về hiệu suất.
- Fallback pattern: Khi một service call thất bại, cho phép service client cố gắng thực hiện công việc của mình thông
  qua các phương tiện thay thế.
- Bulkhead pattern : Các microservice sử dụng nhiều tài nguyên phân tán để thực hiện công việc của chúng. Nghĩa là khi
  có 1 hành vi sai của call service thì không ảnh đến phần còn lại của ứng dụng.

# 1.10 Microservice security patterns

![Alt text](Image/Figure1.14-UsingATokenBased.png?raw=true "Title")

- Authentication : Cách bạn xác định client call service.
- Authorization : Cách bạn xác định xem client service gọi một microservice có được phép thực hiện hành động mà họ đang
  cố gắng thực hiện hay không.
- Credential management and propagation : Cách bạn ngăn client service liên tục phải xuất trình thông tin đăng nhập của
  họ cho các cuộc gọi dịch vụ liên quan đến một giao dịch.(OAuth2 and JWT)

# 1.11 Microservice logging and tracing patterns

- Nhược điểm của Microservice architecture là khó để debug, trace and monitor những vấn đề bởi vì một hành động đơn giản
  có thể liên quan đến nhiều microservice.
  ![Alt text](Image/Figure1.15-LoggingAndTracing.png?raw=true "Title")
    - Log correlation : Cách bạn liên kết tất cả logs được tạo ra giữa các service cho single user transaction.
    - Log aggregation : Cách tập hợp tất cả logs được tạo bởi microservices của bạn vào một cơ sở dữ liệu có thể truy
      vấn.
    - Microservice tracing : Cách các luồng transaction trên tất cả service.

# 1.12 Application metrics pattern

- The application metrics pattern: monitor metrics and warn các nguyên nhân có thể gây ra lỗi

    - Metrics : Thông tin về health và số liệu.
    - Metrics service : Store và query the application metrics.
    - Metrics visualization suite : Trực quan dữ liệu.

      ![Alt text](Image/Figure1.16-Metrics.png?raw=true "Title")

