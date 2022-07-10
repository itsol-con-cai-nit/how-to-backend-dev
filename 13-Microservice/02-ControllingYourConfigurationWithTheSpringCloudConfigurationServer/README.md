## 2.1 Introduction

- Software developers luôn luôn nghe về tầm quan trọng của việc giữ cấu hình ứng dụng tách biệt với code.
- Điều này có nghĩa là không sử dụng các giá trị được mã hóa cứng trong code.
- Bởi vì mỗi khi thay đổi configuration được thực hiện, ứng dụng phải được recompiled and/or redeployed.
- Nhiều developer chuyển sang các tệp thuộc tính (YAML, JSON hoặc XML) để lưu trữ thông tin `configuration` của họ.
- Nhưng nó chỉ hiệu quả ứng dụng nhỏ. Hãy tưởng tượng chúng ta có hàng trăm microservice và mỗi microservice chứa các
  cấu hình khác nhau cho các môi trường khác nhau.
- Nếu các tệp đó không bên ngoài ứng dụng, mỗi khi có thay đổi, chúng ta phải tìm kiếm tệp trong code repository, thực
  hiện các quy trình và resart application.
- Để tránh việc này, như một phương pháp hay nhất để phát triển microservice dựa vào cloud.

    - Tách biệt hoàn toàn configuration của một ứng dụng khỏi mã thực đang được triển khai.
    - Xây dựng immutable application images không bao giờ thay đổi vì chúng được quảng bá thông qua môi trường của bạn.
    - Inject bất kì application configuration thông qua một trong hai biến môi trường khi khởi động máy chủ thông qua
      một trong hai biến môi trường hoặc một kho lưu trữ tập trung mà các microservices đọc khi khởi động.

## 2.2 On managing configuration (and complexity)

- Quản lý configuration cho microservices chạy trên cloud là việc rất quan trọng.
- Bời vì các instances cần được lauch nhanh và giảm thiểu tối đa sự có mặt của con người.
- Khi một người cần cấu hình thủ công điều này sẽ trở thành cơ hội cho việc cấu hình `drift`, ngừng hoạt động không mong
  muốn và thời gian trễ trong việc đáp ứng các thách thức về khả năng mở rộng trong ứng dụng.
    - Segregate: Chúng ta cần tách biệt hoàn toàn thông tin cấu hình dịch vụ khỏi việc triển khai thực tế của một dịch
      vụ. Thông tin cấu hình phải được chuyển dưới dạng các biến môi trường đến dịch vụ bắt đầu hoặc đọc từ kho lưu trữ
      tập trung khi dịch vụ bắt
    - Abstract: Thay vì viết mã đọc trực tiếp kho dịch vụ, cho dù là dựa trên tệp hay cơ sở dữ liệu JDBC, chúng ta nên
      sử dụng dịch vụ JSON dựa trên REST để truy xuất dữ liệu cấu hình của ứng dụng.
    - Centralize: Giảm thiểu số lượng các kho lưu trữ khác nhau được sử dụng để lưu trữ dữ liệu cấu hình. Tập trung cấu
      hình ứng dụng của bạn thành ít kho nhất có thể.
    - Harden: Điều quan trọng là giải pháp bạn sử dụng và triển khai phải có tính khả dụng và dư thừa cao.

## 2.3 Your configuration management architecture

- Việc tải quản lý cấu hình cho một microservice xảy ra trong giai đoạn khởi động của microservice.
  ![Alt text](Image/Figure2.1-MicroserviceStartUps.png?raw=true "Title")
  ![Alt text](Image/Figure2.2-ConfigurationManagement.png?raw=true "Title")

- Khi một microservice instance xuất hiện, nó sẽ gọi service endpoint để đọc thông tin configuration. Cái cụ thể cho môi
  trường nó hoạt động.
- Cấu hình thực tế thì nằm trong một kho lưu trữ
- Việc quản lý application configuration data thực xảy ra độc lập với cách ứng dụng được deploy. Các thay đổi từ các nhà
  phát triển được đẩy qua đường dẫn xây dựng và triển khai đến kho lưu trữ cấu hình.
- Khi có sự thạy đổi, các service phải được thông báo và refresh bản sao dữ liệu ứng dụng của chúng.

## 2.4 Building our Spring Cloud Configuration Server

![Alt text](Image/Figture5.2-CreatingOurBootrap.png?raw=true "Title")

- Có hai phần quan trọng:
    - Đặt tên cho tất cả service mà chúng ta sẽ tạo trong `architecture for service discovery`
    - Port

### 2.4.1 Setting up the Spring Cloud Config bootstrap class

![Alt text](Image/Figure5.3-SetingUp.png?raw=true "Title")

### 2.4.2 Using the Spring Cloud Config Server with a filesystem

![Alt text](Image/Figure5.4-FileSystem.png?raw=true "Title")

- `filesystem` lưu trữ thông tin, chúng phải gọi Spring Cloud Config Server để chạy với native profile.
    - `native` chỉ là profile được tạo cho Spring Cloud Configuration Server, cho biết rằng các tệp cấu hình sẽ được
      truy xuất hoặc đọc từ classpath hoặc hệ thống tệp.

### 2.4.3 Setting up the configuration files for a service

- Chúng ta sẽ thiết lập dữ liệu cấu hình ứng dụng cho ba môi trường: default run at local, a dev environment, và a
  production environment.
- Với Spring Cloud Config, mọi thứ hoạt động theo thứ bậc. Your application configuration được đại diện bằng name of the
  application và sau đó property file cho môi trường bạn muốn configure.
  ![Alt text](Image/Figure5.5-Spring Cloud Config exposes environment-specific properties.png?raw=true "Title")

### 2.4.4 Integrating Spring Cloud Config with a Spring Boot client

![Alt text](Image/Figure5.8-RetrievingConfiguration.png?raw=true "Title")

- Khi licensing service khởi chạy, nó sẽ liên hệ Spring Cloud Config service thông qua endpoint để build Spring profile.
- The Spring Cloud Config service sau đó sẽ sử dụng configured backend repository (filesystem, Git, or Vault) để lấy
  thông tin
- Spring profile value được chuyển qua URI. Các thuộc tính thích hợp sau đó sẽ chuyển về licensing service.
- Spring boot framework sẽ inject những giá trị này vào phần thích hợp của application.

### 2.4.5 Configuring the licensing service to use Spring Cloud Config

![Alt text](Image/Figure5.6-Configuring the licensing service’s.png?raw=true "Title")

- The spring.application.name is tên application phải map trực tiếp tên config trong Spring Cloud Configuration Server.
- Spring.profiles.active tells Spring Boot cái profile sẽ chạy.
- Spring.cloud.config.uri là vị trí nơi licensing service sẽ tìm thấy Config Server endpoint.

- NOTE: Nếu bạn muốn override nhưng default values và trỏ đến environment khác. Chuyển tất cả lênh thông qua JVM
  arguments:
  ![Alt text](Image/CodeJVMArguments.png?raw=true "Title")

#### Refreshing your properties using Spring Cloud Config Server

- Môt trong nhưng câu hỏi đầu tiên xuất hiện từ các nhóm phát triển àm cách nào họ có thể tự động làm mới ứng dụng của
  mình khi một thuộc tính thay đổi.
- Spring Boot Actuator đưa ra @RefreshScope annotation cho phép nhóm phát triển truy cập vào endpoint `/refresh` buộc
  ứng dụng Spring Boot đọc lại cấu hình ứng dụng của nó.
  ![Alt text](Image/Figure5.12-The RefreshScope annotation.png?raw=true "Title")

### 2.4.5 Using Spring Cloud Configuration Server with Git

- Như đã đề cập ở trên, việc sử dụng filesystem as the backend repository for the Spring Cloud Configuration Server có
  thể không thực tế với cloud-based application.
- Một cách tiếp cận là sử dụng Git source control repository. Bằng cách sử dụng Git, bạn nhận tất tất cả lợi ích của
  việc đặt configuration management properties.
- Cung cấp một cơ chế dễ dàng để tích hợp việc triển khai property configuration files trong việc build và deployment
  pipeline.
  ![Alt text](Image/Figure5.13-Adding Git support to the Spring Cloud.png?raw=true "Title")

- Spring.profiles.active: thuộc tính đặt tất cả active profiles for the Spring Config service.
- Spring.cloud.config.server.git: thuộc tính thông báo cho Spring Cloud Config Server để sử dụng non-filesystem-based
  backend repository.
- spring.cloud.config.server.git.uri: thuộc tính cung cấp URL của kho lưu trữ mà bạn đang kết nối.
- spring.cloud.config.server.git.searchPaths: thuộc tính cho Máy chủ cấu hình biết đường dẫn tương đối trên kho lưu trữ
  Git sẽ được tìm kiếm khi Cloud Config Server boots up.

## 2.5 Protecting sensitive configuration information

- By default, the Spring Cloud Configuration Server lưu properties dưới dạng text trong application’s configuration
  files.
- Điều này bao gồm thông tin nhạy cảm như thông tin xác thực database...
- Spring Cloud Config cung cấp cho bạn khả năng mã hóa các thuộc tính nhạy cảm của bạn một cách dễ dàng.
- Spring Cloud Config hỗ trợ cả symmetric và (shared secret) and asymmetric encryption (public/private) keys.

### 2.5.1 Setting up a symmetric encryption key

- The symmetric encryption key không gì khác hơn a shared secret được sử dụng bởi encrypter để encrypt và decrypter để
  decrypt.
- Với Spring Cloud Configuration Server, the symmetric encryption key là string of characters có thể đặt trong tệp
  bootstrap.ym hoặc được chuyển tới service via an OS environment variable.
  ![Alt text](Image/Figure5.15-Setting a symmetric key in the boostrap.yml file.png?raw=true "Title")

### 2.5.2 Encrypting and decrypting a property

![Alt text](Image/Figure5.13-Encrypting.png?raw=true "Title")

- Spring Cloud Config phát hiện ENCRYPT_KEY environment variable hoặc bootstrap file property được thiết lập và tự động
  thêm 2 endpoints `/encrypt` and `/decrypt`
  ![Alt text](Image/Figure5.16-Adding an encrypted value.png?raw=true "Title")
- The Spring Cloud Configuration Server yêu cầu tất encrypted properties được thêm vào trước giá trị `{cipher}`. Giá trị
  này cho biết rằng Config Server đang được mã hóa.


