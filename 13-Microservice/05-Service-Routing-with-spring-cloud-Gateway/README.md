## 5.1 Introduction

- Trong một kiến trúc phân tán như một microservice, sẽ đến lúc chúng ta cần đảm bảo rằng các hành vi quan trọng như
  security, logging, and tracking users trên nhiều cuộc gọi service sẽ xảy ra.

    - Thật khó để triển khai các khả năng này trong từng service một cách nhất quán. Các nhà phát triển tập trung vào
      việc delivering functionality và trong cơn lốc của hoạt động hàng ngày, họ có thể dễ dàng quên việc triển khai
      service logging or tracking trừ khi họ làm việc trong một ngành được quản lý khi yêu cầu.
    - Đẩy mạnh trách nhiệm thực hiện các mối quan tâm xuyên suốt như bảo mật và đăng nhập cho các nhóm phát triển cá
      nhân làm tăng đáng kể khả năng ai đó sẽ không thực hiện chúng đúng cách hoặc sẽ quên thực hiện chúng. Mối quan tâm
      xuyên suốt đề cập đến các phần hoặc tính năng của thiết kế chương trình có thể áp dụng trong toàn bộ ứng dụng và
      có thể ảnh hưởng đến các phần khác của ứng dụng.
    - Có thể tạo ra sự phụ thuộc khó khăn trên tất cả các dịch vụ của chúng ta. chúng ta càng xây dựng nhiều khả năng
      thành một khuôn khổ chung được chia sẻ trên tất cả các service của chúng ta, thì càng khó thay đổi hoặc thêm hành
      vi trong mã chung của chúng ta mà không cần phải biên dịch lại và triển khai lại tất cả các dịch vụ của chúng tôi.
      Đột nhiên, việc nâng cấp các khả năng cốt lõi được tích hợp vào thư viện dùng chung sẽ trở thành một quá trình di
      chuyển lâu dài.
- Để giải quyết vấn đề này, chúng ta cần tóm tắt những mối quan tâm xuyên suốt này thành một service có thể hoạt động
  độc lập và hoạt động như một bộ lọc và bộ định tuyến cho tất cả các cuộc gọi microservice trong kiến trúc của chúng
  ta. chúng ta gọi service này là gateway.
- Các service clients của chúng ta không còn gọi trực tiếp đến một microservice nữa. Thay vào đó, tất cả các cuộc gọi
  được định tuyến thông qua service gateway, hoạt động như a single Policy Enforcement Point (PEP), và sau đó được
  chuyển đến a final destination.
    - Đặt tất cả các cuộc gọi service sau a single URL và ánh xạ các cuộc gọi đó bằng cách sử dụng tính năng service
      discovery với các service instances thực tế của chúng.
    - Inject correlation IDs vào mọi cuộc gọi service đi qua service gateway.
    - Inject the correlation ID được trả về từ HTTP response và gửi lại cho the client.

## 5.2 What is a service gateway?

- Cho đến nay, với các microservices mà chúng ta đã xây dựng trong các chương trước đó, chúng ta đã gọi trực tiếp các
  services riêng lẻ thông qua ứng dụng web client hoặc gọi chúng theo cách lập trình thông qua công cụ service discovery
  như Eureka.
  ![Alt text](Image/Figure8.1-Without-a-service-gateway.png?raw=true "Title")
- A service gateway hoạt động như một trung gian giữa service client và một service được gọi. service client chỉ nói
  chuyện với a single URL managed được quản lý bởi service gateway.
- The service gateway tách đường dẫn đến từ the service client call và xác định service mà the service client đang cố
  gắng gọi. Hình 8.2 minh họa cách cổng dịch vụ hướng người dùng đến một microservice mục tiêu và phiên bản tương ứng,
  giống như một cảnh sát giao thông chỉ đạo lưu lượng truy cập.
  ![Alt text](Image/Figure8.2-The-Service-gateway-sits.png?raw=true "Title")
- The service gateway đóng vai trò là người gác cổng cho tất cả lưu lượng gửi đến các cuộc gọi microservice trong ứng
  dụng của chúng ta. Với cổng dịch vụ tại chỗ, các service clients của chúng ta không bao giờ gọi trực tiếp URL của một
  dịch vụ riêng lẻ mà thay vào đó thực hiện tất cả các cuộc gọi đến the service gateway
- Bởi vì a service gateway nằm giữa tất cả các cuộc gọi từ the client đến các the individual services, nó cũng hoạt động
  như một PEP trung tâm cho các cuộc gọi service. Việc sử dụng PEP tập trung có nghĩa là các mối quan tâm service xuyên
  suốt có thể được thực hiện ở một nơi duy nhất mà không cần các nhóm phát triển riêng lẻ phải thực hiện các mối quan
  tâm đó.
    - Static routing: A service gateway đặt tất cả các lệnh gọi service phía sau a single URL and API route. Điều này
      đơn giản hóa việc phát triển vì chúng ta chỉ phải biết về một service endpoint cho tất cả các service của mình.
    - Dynamic routing: A service gateway có thể kiểm tra các service requests đến và dựa trên dữ liệu từ yêu cầu đến,
      thực hiện định tuyến thông minh cho the service caller. Ví dụ: khách hàng tham gia chương trình beta có thể có tất
      cả các cuộc gọi đến một dịch vụ được chuyển đến một nhóm dịch vụ cụ thể đang chạy phiên bản mã khác với những gì
      mọi người khác đang sử dụng.
    - Authentication and authorization: Bởi vì tất cả các service calls route thông qua một service gateway, the service
      gateway là một nơi tự nhiên để kiểm tra xem liệu những the caller of a service đã xác thực chính họ hay chưa.
    - Metric collection and logging: A service gateway có thể được sử dụng để metrics and log information khi một cuộc
      gọi service đi qua cổng đó. Bạn cũng có thể sử dụng the service gateway để xác nhận rằng các phần thông tin quan
      trọng được cung cấp cho các user requests, do đó đảm bảo rằng việc logging là đồng nhất. Điều này không có nghĩa
      là bạn không nên thu thập metrics từ bên trong các services riêng lẻ của mình.
    - A service gateway cho phép bạn tập trung thu thập nhiều metrics cơ bản của mình, như số lần service được gọi và
      thời gian phản hồi của service.

## 5.3  Introducing Spring Cloud Gateway

- Spring Cloud Gateway là triển khai the API gateway được xây dựng trên Spring framework 5, Project Reactor và Spring
  Boot 2.0. This gateway là nonblocking gateway.
- Nonblocking applications được viết theo cách sao cho các luồng chính không bao giờ bị chặn.Thay vào đó, các luồng này
  luôn có sẵn để cung cấp các yêu cầu và xử lý chúng không đồng bộ trong nền để trả lại phản hồi sau khi xử lý xong.
- Spring Cloud Gateway cung cấp một số khả năng, bao gồm:
    - Ánh xạ các tuyến đường cho tất cả các service trong ứng dụng của bạn đến một URL duy nhất.
    - Xây dựng bộ lọc có thể kiểm tra và hành động theo các yêu cầu và phản hồi đến qua gateway. Các bộ lọc này cho phép
      chúng ta đưa các điểm thực thi chính sách vào mã của mình và thực hiện nhiều hành động trên tất cả các lệnh gọi
      service của chúng ta theo một cách nhất quán. Nói cách khác, các bộ lọc này cho phép chúng ta sửa đổi các yêu cầu
      và phản hồi HTTP requests and responses.
    - Xây dựng vị từ, là các đối tượng cho phép chúng ta kiểm tra xem các yêu cầu có đáp ứng một tập hợp các điều kiện
      nhất định hay không trước khi thực hiện hoặc xử lý một yêu cầu.

## 5.3.1 Setting up the Spring Boot gateway project

![Alt text](Image/Figure8.4-Our-gateway-server-dependencies.png?raw=true "Title")
![Alt text](Image/Listing8.2-Setting-up-the-Gateway-bootstrap.yml-file.png?raw=true "Title")

## 5.3.2 Configuring the Spring Cloud Gateway to communicate with Eureka.

- Để thêm Gateway service, mới, bước đầu tiên là tạo configuration file cho service này trong Spring Configuration
  Server repository.
  ![Alt text](Image/Listing8.3-Setting-up-the-Eureka-configuration-in-the-Spring-Configuration-Server.png?raw=true "Title")
- Và cuối cùng, chúng ta sẽ thêm @EnableEurekaClient trong ApiGatewayServerApplication class.
  ![Alt text](Image/Listing8.4-Adding-@EnableEurekaClient.png?raw=true "Title")

## 5.4 Configuring routes in Spring Cloud Gateway

- Về cơ bản, Spring Cloud Gateway là một reverse proxy. Một reverse proxy là một máy chủ trung gian nằm giữa máy khách
  đang cố gắng tiếp cận tài nguyên và chính tài nguyên đó.
- The client không biết nó thậm chí đang giao tiếp với server. Reverse proxy sẽ đảm nhận việc nắm bắt the client’s
  request và sau đó client’s behalf gọi remote resource.
    - Trong trường hợp của một kiến trúc microservice, Spring Cloud Gateway nhận một cuộc gọi microservice từ a client
      và chuyển tiếp nó đến the upstream service.
    - The service client nghĩ rằng nó chỉ giao tiếp với gateway. Nhưng nó không thực sự đơn giản như vậy. Để giao tiếp
      với the upstream services, gateway phải biết cách ánh xạ cuộc gọi đến với the upstream route.
    - Spring Cloud Gateway có một số cơ chế để thực hiện việc này, bao gồm:
        - Automated mapping of routes using service discovery.
        - Manual mapping of routes using service discovery.

### 5.4.1 Automated mapping of routes via service discovery.

- All route mappings cho the gateway được thực hiện bằng cách xác định the routes trong gateway-server.yml file.
- Tuy nhiên, Spring Cloud Gateway có thể automatically route requests dựa trên service IDs của chúng bằng cách thêm các
  cấu hình sau vào the gateway-server configuration file.
  ![Alt text](Image/Listing8.5-Setting-up-the-discovery-locator.png?raw=true "Title")
- Spring Cloud Gateway tự động sử dụng Eureka service ID đang được gọi và ánh xạ nó tới a downstream service instance.
- Ví dụ: nếu chúng ta muốn gọi organization service của mình và sử dụng automated routing qua Spring Cloud Gateway,
  chúng ta sẽ yêu cầu khách hàng của chúng ta gọi Gateway service instance bằng cách sử dụng URL sau làm endpoint.

  ![Alt text](Image/Url-as-the-endpoint.png?raw=true "Title")
  ![Alt text](Image/Figure8.5-The-Spring-Cloud-Gateway.png?raw=true "Title")
- Vẻ đẹp của việc sử dụng Spring Cloud Gateway với Eureka là giờ đây chúng ta không chỉ a single endpoint mà qua đó
  chúng ta có thể thực hiện cuộc gọi mà còn có thể thêm và xóa remove instances of a service mà không cần phải sửa đổi
  gateway.
- Ví dụ: chúng ta có thể thêm một new service to Eureka và the gateway automatically routes các cuộc gọi đến nó vì nó
  đang giao tiếp với Eureka về nơi đặt physical service endpoints thực tế.
- Nếu chúng ta muốn xem the routes managed bởi the Gateway server, chúng ta có thể liệt kê các the routes thông qua the
  actuator/gateway/routes endpoint on the Gateway server.
  ![Alt text](Image/Figure8.6-Each-service-that-is-mapped.png?raw=true "Title")

### 5.4.2 Manually mapping routes using service discovery.

- Spring Cloud Gateway cho phép mã của chúng ta chi tiết hơn bằng cách cho phép chúng ta xác định rõ ràng các route
  mappings thay vì chỉ dựa vào các automated routes được tạo bằng the Eureka service ID.
  ![Alt text](Image/Listing8.6-Mapping-routes-manually.png?raw=true "Title")
- Một service entry là ánh xạ mà chúng ta đã xác định trong tệp gatewayserver.yml, là organization/**:
  organization-service. Mục nhập dịch vụ khác là automatic mapping được tạo bởi cổng dựa trên the Eureka ID cho
  organization service, đó là /organization-service/ **: organization-service.
    - NOTE: Khi automated route mapping trong đó gateway hiển thị service chỉ dựa trên Eureka service ID, nếu không có
      service instances nào đang chạy, the gateway sẽ không hiển thị the route cho the service.Tuy nhiên, nếu chúng ta
      manually map a route tới a service discovery ID theo cách thủ công và không có trường hợp nào được đăng ký với
      Eureka, gateway sẽ vẫn hiển thị the route. Nếu chúng ta cố gắng gọi route cho the nonexistent service, nó sẽ trả
      về lỗi HTTP 500
- Nếu chúng ta muốn loại trừ the automated mapping của the Eureka service ID route và chỉ có organization service route
  mà chúng ta đã xác định, chúng ta có thể xóa các mục nhập spring.cloud.gateway.discovery.locator mà chúng ta đã thêm
  trong gateway-server.yml tập tin.
  ![Alt text](Image/Listing8.6-Mapping-routes-manually.png?raw=true "Title")
  ![Alt text](Image/Listing8.7-Removing-the-discovery-locator-entries.png?raw=true "Title")
  ![Alt text](Image/Listing8.7.1-Removing-the-discovery-locator-entries.png?raw=true "Title")
  ![Alt text](Image/Figure8.8-The-result-of-the-gateway.png?raw=true "Title")

### 5.4.3 Dynamically reloading route configuration

- Điều tiếp theo chúng ta sẽ xem xét khi định cấu hình các tuyến trong Spring Cloud Gateway là cách dynamically refresh
  routes. The ability to dynamically reload routes rất hữu ích vì nó cho phép chúng ta thay đổi the mapping of routes mà
  không cần phải khởi động lại (các) Gateway server. Các tuyến hiện có có thể được sửa đổi nhanh chóng và new routes sẽ
  phải thực hiện hành động recycling each Gateway server trong môi trường của chúng ta.
- Nếu chúng ta nhập the actuator/gateway/routes endpoint, chúng ta sẽ thấy organization service của chúng ta hiện được
  hiển thị trong gateway.
- Bây giờ, nếu chúng ta muốn thêm new route mappings , tất cả những gì chúng ta phải làm là thực hiện các thay đổi đối
  với the configuration file và chuyển những thay đổi đó trở lại Git repository nơi Spring Cloud Config lấy
  configuration data của nó. Sau đó, chúng ta có thể commit các thay đổi đối với GitHub.
- Spring Actuator hiển thị một tuyến a POST-based endpoint route, actuator/gateway/ refresh, điều này sẽ khiến nó tải
  lại route configuration của nó.
- Sau khi đạt đến actuator/ gateway/refresh này, nếu sau đó bạn nhập the /routes endpoint. Bạn sẽ thấy điều đó hai tuyến
  đường mới được tiếp xúc. Phản hồi của bộ truyền động / cổng / làm mới trả về mã trạng thái HTTP 200 mà không có nội
  dung phản hồi. The response of the actuator/gateway/refresh trả về mã trạng thái HTTP 200 mà không có a response body.

## 5.5 The real power of Spring Cloud Gateway: Predicate and Filter Factories

- Bởi vì chúng ta có thể ủy quyền tất cả các yêu cầu thông qua the gateway, nó cho phép chúng ta đơn giản hóa các
  service invocations của mình. Nhưng sức mạnh thực sự của Spring Gateway phát huy tác dụng khi chúng ta muốn viết logic
  tùy chỉnh sẽ được áp dụng chống lại tất cả các lệnh gọi service đi qua gateway.
- Thông thường, chúng ta sẽ sử dụng custom này để thực thi một bộ chính sách ứng dụng nhất quán như security, logging,
  and tracking giữa tất cả services.
- Các application policies này được coi là cross-cutting concerns vì chúng ta muốn strategies này được áp dụng cho tất
  cả services trong ứng dụng của chúng ta mà không cần phải sửa đổi từng service để triển khai chúng.
- Theo cách này, Spring Cloud Gateway Predicate và Filter Factories có thể được sử dụng tương tự như các Spring aspect
  classes.
- Chúng có thể khớp hoặc chặn một loạt các hành vi và trang trí hoặc thay đổi hành vi của cuộc gọi mà người lập trình
  ban đầu không nhận thức được sự thay đổi. Trong khi a servlet filter hoặc Spring aspect được a specific service, việc
  sử dụng Gateway và các Predicate and Filter Factories của nó cho phép chúng ta thực hiện các mối quan tâm xuyên suốt
  trên tất cả các service đang được định tuyến thông qua gateway.
- Hãy nhớ rằng, predicates cho phép chúng ta kiểm tra xem các yêu cầu có đáp ứng một tập hợp các điều kiện hay không
  trước khi xử lý yêu cầu.
  ![Alt text](Image/The-real-power-of-Spring-Cloud-Gateway.png?raw=true "Title")
- Đầu tiên, the gateway client ((browsers, apps, v.v.) gửi yêu cầu đến Spring Cloud Gateway. Sau khi nhận được yêu cầu
  đó, nó sẽ chuyển trực tiếp đến B Gateway Handler phụ trách việc xác minh rằng đường dẫn được yêu cầu khớp với cấu hình
  của đường cụ thể mà nó đang cố gắng truy cập.
- Nếu mọi thứ khớp, nó sẽ đi vào Gateway Web Handler chịu trách nhiệm đọc các filter và gửi yêu cầu đến các filter đó để
  xử lý thêm. Khi yêu cầu vượt qua tất cả các filter, nó sẽ được chuyển tiếp đến routing configuration: một
  microservice.

### 5.5.1 Built-in Predicate Factories.

- Built-in predicates là các đối tượng cho phép chúng ta kiểm tra xem các yêu cầu có đáp ứng một tập hợp các điều kiện
  hay không trước khi thực hiện hoặc xử lý các yêu cầu. Đối với mỗi tuyến đường, chúng ta có thể đặt multiple Predicate
  Factories, được sử dụng và kết hợp thông qua logic AND.
  ![Alt text](Image/Table8.1-Built-in-predicates-in-Spring-Cloud-Gateway.png?raw=true "Title")
  ![Alt text](Image/Table8.1.1-Built-in-predicates-in-Spring-Cloud-Gateway.png?raw=true "Title")

### 5.5.2 Built-in Filter Factories

- Hệ thống built-in Filter Factories cho phép chúng ta đưa inject policy enforcement points vào mã của mình và thực hiện
  nhiều hành động trên tất cả các lệnh gọi dịch vụ theo một cách nhất quán. Nói cách khác, các bộ lọc này cho phép chúng
  ta sửa đổi các yêu cầu và phản hồi HTTP requests and responses.
  ![Alt text](Image/Table8.2-Built-in-filters-in-Spring-Cloud-Gateway.png?raw=true "Title")

### 5.5.3  Custom filters

- Khả năng ủy quyền tất cả các yêu cầu thông qua the gateway cho phép chúng ta đơn giản hóa các lệnh gọi service của
  mình. Nhưng sức mạnh thực sự của Spring Cloud Gateway phát huy tác dụng khi chúng ta muốn viết custom logic có thể
  được áp dụng chống lại tất cả các lệnh gọi dịch vụ đi qua gateway.
- Thông thường, this custom logic được sử dụng để thực thi một bộ application policies nhất quán như security, logging,
  and tracking giữa tất cả các service.
- Spring Cloud Gateway cho phép chúng ta xây dựng custom logic bằng cách sử dụng a filter trong gateway. Hãy nhớ rằng, a
  filter cho phép chúng ta triển khai a chain of business logic mà each service request sẽ đi qua khi được triển khai.
  Spring Cloud Gateway hỗ trợ hai loại filters sau.
    - Pre-filters: A pre-filter được gọi trước khi yêu cầu thực tế được gửi đến the target destination . A pre-filter
      thường thực hiện nhiệm vụ đảm bảo rằng service có định dạng thông báo nhất quán.
    - Post-filters: A post-filter được gọi sau khi the target service và phản hồi được gửi lại cho the client. Thông
      thường, chúng ta triển khai a post-filter để ghi lại phản hồi từ target service, xử lý lỗi hoặc kiểm tra phản hồi
      đối với sensitive information.
      ![Alt text](Image/Figure8.10-The-pre-filters-target-route-and-post-filters.png?raw=true "Title")
    - Bất kỳ pre-filters được xác định trong gateway đều được gọi khi một yêu cầu đi vào the gateway. The pre-filters sẽ
      kiểm tra và sửa đổi một an HTTP request trước khi nó đến với actual service. Tuy nhiên, A pre-filter không thể
      chuyển hướng người dùng đến a different endpoint or service.
    - Sau khi the pre-filters được thực thi đối với yêu cầu đến bởi gateway, the gateway sẽ xác định đích (nơi dịch vụ
      đang hướng tới).
    - Sau khi the target service, the gateway post-filters sẽ được gọi. The post-filters kiểm tra và sửa đổi phản hồi từ
      service được gọi.
- Cách tốt nhất để hiểu cách triển khai the gateway filters là xem chúng hoạt động. Để đạt được điều này, trong một số
  phần tiếp theo, chúng ta sẽ xây dựng các pre- and post-filters, sau đó chạy các client requests thông qua chúng.
  ![Alt text](Image/Figure8.11-Gateway-filters-provide-centralized-tracking.png?raw=true "Title")
    - Tracking filter: The tracking filter là một pre-filter trước đảm bảo rằng mọi yêu cầu đến từ cổng kết nối đều có
      một correlation ID được liên kết với nó. correlation ID là một ID duy nhất được thực hiện trên tất cả các
      microservices được thực thi khi thực hiện yêu cầu của client. correlation ID cho phép chúng ta theo dõi chuỗi sự
      kiện xảy ra khi một cuộc gọi đi qua một chuỗi các cuộc gọi microservice.
    - Target service: The target service có thể là một organization or the licensing service. Cả hai service đều nhận
      được correlation ID trong HTTP request header.
    - Response filter: The response filter là một post-filter sau đưa correlation ID được liên kết với lệnh gọi service
      vào HTTP response header được gửi đến the client. Bằng cách này, the client sẽ có quyền truy cập vào the
      correlation ID được liên kết với request.

## 5.6 Building the pre-filter

- Việc xây dựng filters trong Spring Cloud Gateway rất đơn giản. Để bắt đầu, chúng ta sẽ xây dựng một pre-filterc, được
  gọi là TrackingFilter, sẽ kiểm tra tất cả các yêu cầu đến gatewayh và xác định xem có HTTP header được gọi là
  tmx-tương quan-id trong request hay không.
- The tmx-correlation-id header sẽ chứa a unique GUID (Globally Universal ID) có thể được sử dụng để theo dõi user’s
  request trên multiple microservices.
  ![Alt text](Image/Listing8.8-Pre-filter-for-generating-correlation-IDs.png?raw=true "Title")
- Để tạo ba global filterc trong Spring Cloud Gateway, chúng ta cần implement GlobalFilter và sau đó ghi đè phương thức
  filter().
- chúng ta đã implemented a class called FilterUtils, class này bao gồm chức năng phổ biến được tất cả các filter sử
  dụng.

![Alt text](Image/Listing8.9-Retrieving-the-tmx-correlation-id.png?raw=true "Title")

- Trước tiên, chúng ta kiểm tra xem tmx-correlationID đã được đặt trên the HTTP headers cho yêu cầu đến chưa. Nếu nó
  không có ở đó, mã của chúng ta sẽ trả về null để tạo một mã sau này. Bạn có thể nhớ rằng trước đó, trong phương thức
  filter () trong lớp TrackingFilter của chúng ta, chúng ta đã thực hiện chính xác điều này với đoạn mã sau:
  ![Alt text](Image/Listing8.9.1-Retrieving-the-tmx-correlation-id.png?raw=true "Title")
- Để đặt tmx-correlation-id, bạn sẽ sử dụng phương thức FilterUtils setCorrelationId () như được hiển thị trong danh
  sách sau:
  ![Alt text](Image/Listing8.10-Setting-the-tmx-correlation-id.png?raw=true "Title")
- Với phương thức FilterUtils setCorrelationId (), khi chúng ta muốn thêm một giá trị vào các HTTP request headers,
  chúng ta có thể sử dụng phương thức ServerWebExchange.Builder mutate (). Phương thức này trả về a builder để thay đổi
  các thuộc tính của đối tượng trao đổi bằng cách gói nó với ServerWebExchangeDecorator và trả về các giá trị đã thay
  đổi hoặc ủy quyền lại cho instance này.

## 5.7 Using the correlation ID in the services

- Bây giờ chúng ta đã đảm bảo rằng a correlation ID đã được thêm vào mọi lệnh gọi microservice qua gateway, chúng
  tôi muốn đảm bảo rằng
    - The correlation ID có thể dễ dàng truy cập được đối với microservice được gọi.
    - Bất kỳ downstream service calls nào mà microservice thực hiện cũng có thể correlation ID cho các the downstream
      calls.
- Để triển khai điều này, chúng ta sẽ xây dựng một bộ ba lớp cho mỗi microservices của chúng ta: UserContextFilter,
  UserContext và UserContextInterceptor. Các lớp này sẽ làm việc cùng nhau để the correlation ID của HTTP request đến,
  ánh xạ nó tới một lớp có thể dễ dàng truy cập và sử dụng được theo the business logic trong ứng dụng và sau đó đảm bảo
  rằng the correlation ID được truyền tới bất kỳ downstream service calls.
  ![Alt text](Image/Figure8.12-Using-a-set-of-common-classes.png?raw=true "Title")
    - Khi một cuộc gọi được thực hiện đến licensing service thông qua gateway, TrackingFilter sẽ inject một a
      correlation ID vào HTTP header đến cho bất kỳ cuộc gọi nào đến gateway.
    - Lớp UserContextFilter, a custom HTTP ServletFilter,, ánh xạ một correlation ID với lớp UserContext. Lớp
      UserContext lưu trữ các giá trị trong một chuỗi để sử dụng sau này trong cuộc gọi.
    - The licensing service business logic thực thi một cuộc gọi đến the organization service.
    - RestTemplate gọi the organization service. RestTemplate sử dụng custom Spring interceptor class,
      UserContextInterceptor, để inject correlation ID vào the outbound call as an HTTP header.

### 5.7.1 UserContextFilter: Intercepting the incoming HTTP request.

- Lớp đầu tiên chúng ta sẽ xây dựng là lớp UserContextFilter. Lớp này là an HTTP servlet filter sẽ chặn tất cả các
  HTTP requests đến vào service và ánh xạ the correlation ID (và một vài giá trị khác) từ HTTP request tới lớp
  UserContext.
  ![Alt text](Image/Listing8.12-Mapping-the-correlation-ID.png?raw=true "Title")
  ![Alt text](Image/Listing8.12.1-Mapping-the-correlation-ID.png?raw=true "Title")
- Cuối cùng, UserContextFilter sẽ ánh xạ các giá trị HTTP header mà bạn quan tâm đến một Java UserContext class.

### 5.7.2 UserContext: Making the HTTP headers easily accessible to the service.

- Lớp UserContext giữ các giá trị the HTTP header cho một service client request riêng lẻ được xử lý bởi microservice
  của chúng ta. Nó bao gồm các phương thức getter / setter lấy và lưu trữ các giá trị từ java.lang.ThreadLocal.
  ![Alt text](Image/Listing8.13-Storing-the-HTTP-header.png?raw=true "Title")
- Ở đây, lớp UserContext không hơn gì một POJO giữ các giá trị được thu thập từ HTTP request đến.
- Tiếp theo, chúng ta sẽ sử dụng lớp UserContextHolder để lưu trữ UserContext trong một biến ThreadLocal có thể truy cập
  bằng bất kỳ phương thức nào được gọi bởi luồng xử lý user’s request.
  ![Alt text](Image/Listing8.14-The-UserContextHolder-stores.png?raw=true "Title")

### 5.7.3 Custom RestTemplate and UserContextInterceptor: Ensuring that the correlation ID gets propagated

- Đoạn mã cuối cùng mà chúng ta sẽ xem xét là lớp UserContextInterceptor. Lớp này đưa correlation ID vào bất kỳ
  HTTP-based service request nào dựa trên HTTP được thực thi từ một RestTemplate instance.
- Điều này được thực hiện để đảm bảo rằng chúng ta có thể thiết lập một liên kết giữa các cuộc gọi service. Để làm điều
  này, chúng ta sẽ sử dụng một Spring interceptor được inject vào lớp RestTemplate.
  ![Alt text](Image/Listing8.15-Injecting-the-correlation-ID.png?raw=true "Title")
- Để sử dụng UserContextInterceptor, chúng ta cần xác định một bean RestTemplate và sau đó thêm UserContextInterceptor
  vào nó. Để làm điều này, chúng ta sẽ xác định bean RestTemplate của riêng mình trong lớp LicenseServiceApplication.
  ![Alt text](Image/Listing8.16-Adding-the-UserContextInterceptor.png?raw=true "Title")

## 5.8 Building a post-filter receiving correlation ID.

- Hãy nhớ rằng Spring Gateway thực hiện lệnh gọi HTTP thực thay mặt cho service client và kiểm tra the response từ the
  target service call.
- Sau đó, nó thay đổi the response or decorates nó với thông tin bổ sung. Khi được kết hợp với việc thu thập dữ liệu
  bằng the pre-filter, a gateway post-filter là vị trí lý tưởng để thu thập metrics and complete any logging liên quan
  đến user’s transaction.
- chúng ta sẽ muốn tận dụng điều này bằng cách injecting the correlation ID mà chúng ta đã chuyển qua microservices của
  mình trở lại client. Bằng cách này, chúng ta có thể chuyển lại the correlation ID cho người gọi mà không cần phải chạm
  vào the message body.
  ![Alt text](Image/Listing8.17-Injecting-the-correlation-ID-into-the-HTTP-response.png?raw=true "Title")
- Khi chúng ta đã triển khai ResponseFilter, chúng ta có thể kích hoạt service của mình và gọi licensing or
  organization service với nó. Sau khi dịch vụ hoàn tất, bạn sẽ thấy tmxcorrelation-id trên the HTTP response header từ
  cuộc gọi.
  ![Alt text](Image/Figure8.13-The-tmx-correlation-id.png?raw=true "Title")
- Cho đến thời điểm này, tất cả các ví dụ filter của chúng ta đã xử lý việc thao tác các cuộc gọi the service client
  trước và sau khi chúng được chuyển đến a target destination.
  ![Alt text](Image/Figure8.14-Logger-output-that-shows-the-pre-filter-data.png?raw=true "Title")
