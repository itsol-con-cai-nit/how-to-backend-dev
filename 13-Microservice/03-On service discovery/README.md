## 3.1 Introduction

- Trong bất kì distributed architecture, chúng ta cần tìm hostname or IP address của nơi machine được đặt.
- Khái niệm này đã xuất hiện từ thời kỳ đầu của distributed computing và được biết đến với tên chính
  thức `service discovery`.
- Service discovery có thể một cái gì đó đơn giản như duy trì property file với địa chỉ của tất cả remote services được
  sử dụng bởi application.
- Service discovery là rất quan trọng với microservice, cloud-based applications bởi 2 lý do:
    - Horizontal scaling or scale out: This pattern thường yêu cầu các điều chỉnh trong kiến trúc ứng dụng, chẳng hạn
      như thêm nhiều instance của dịch vụ bên trong dịch vụ đám mây và more containers.
    - Resiliency: This pattern đề cập đến khả năng hấp thụ tác động của các vấn đề trong một kiến trúc hoặc service mà
      không ảnh hưởng đến business.

## 3.2 Where’s my service?

- Nếu bạn có một ứng dụng gọi tài nguyên trải rộng trên nhiều servers.Nó cần phải tìm physical location của các tài
  nguyên đó.
- Trong non-cloud world, service location resolution thường được giải quyết thông qua sự kết hợp giữa DNS và network
  load balancer.
  ![Alt text](Image/Figure6.1-ATraditionalService.png?raw=true "Title")
- Trong kịch bản truyền thống, load balancer khi nhận yêu cầu từ service consumer, xác định vị trí mục nhập địa chỉ thực
  trong bảng định tuyến dựa trên đường dẫn mà người dùng đang cố gắng truy cập.
- This routing table entry chứa danh sách một hoặc nhiều servers hosting the service.
- Load balancer sau khi đã chọn một trong servers trong danh sách và chuyển tiếp yêu cầu đến server đó.
- Với mô hình này, mỗi instance của service được deployed trong một hoặc nhiều application servers.
- Số lượng của những application servers thường là static và persistent.
- Để đạt được một form of high availability, a secondary idle load balancer pinged the primary load balancer xem liệu nó
  còn sống hay không. Nếu nó không còn sống, the secondary load balancer trở nên active, tiếp quản địa chỉ IP của bộ cân
  bằng tải chính và bắt đầu cung cấp các yêu cầu.
- Mặc dù loại mô hình này hoạt động tốt số lượng nhỏ service chạy trên một nhóm static servers. Nó không hoạt động tốt
  trên cloud-based microservice applications.
    - Mặc dù load banlancer có thể được tạo ra rất khả dụng, nhưng đó là một điểm thất bại duy nhất cho toàn bộ cơ sở hạ
      tầng của bạn. Nếu load balancer đi xuống, mọi ứng dụng dựa vào nó cũng sẽ đi xuống.
    - Việc tập trung các service của bạn thành một cụm công cụ load balancer giới hạn khả năng mở rộng quy mô theo chiều
      ngang của cơ sở load-balancing infrastructure trên multiple servers.
    - Hầu hết load balancers truyền thống được quản lý tĩnh.
    - Bởi vìload balancer hoạt động như một proxy cho services, các yêu cầu của service consumer cần phải có chúng được
      ánh xạ tới các physical services.

## 3.3 Service discovery in the cloud

- Giải pháp cho cloud-based microservice environment để sử dụng service discovery cơ chế đó là:
- Highly available: Service discovery cần có thể hỗ trợ “hot” clustering environment nơi service có thể chia sẻ trên
  multiple nodes trong a service discovery cluster. Nếu một node không khả dụng, các node khác trong cụm sẽ có thể tiếp
  quản.
- Peer-to-peer: Mỗi node trong service discovery cluster chia sẻ trạng thái của một service instance.
- Load balanced: Service discovery cần tự động tải các yêu cầu cân bằng trên all service instances.
- Resilient: Service discovery’s client nên cache service information locally. Local caching cho phép giảm dần tính năng
  service discovery để nếu service discovery vụ không khả dụng, các ứng dụng vẫn có thể hoạt động và định vị service dựa
  trên thông tin được duy trì trong local cache.
- Fault tolerant: Service discovery cần phát hiện khi a service instance không lành mạnh và xóa phiên bản đó khỏi danh
  sách các dịch vụ có sẵn có thể nhận yêu cầu của khách hàng. Nó sẽ phát hiện những lỗi này với các services và thực
  hiện hành động mà không cần sự can thiệp của con người.

### 3.3.1 The architecture of service discovery

- Để bắt đầu cuộc thảo luận xung quanh việc service discovery, chúng ta cần hiểu bốn khái niệm. Các khái niệm chung này
  thường được chia sẻ trên tất cả các triển khai service discovery
- Service registration: Cách a service registers với service discovery agent.
- Client lookup of service address: Cách service client tra cứu thông tin service.
- Information sharing: Cách node chia sẻ service information.
- Health monitoring: Cách các service thông báo lại tình trạng của họ cho service discovery agent.
  ![Alt text](Image/Figure6.2 AsServiceInstancesAreAddedOrRemoved.png?raw=true "Title")
- As service instances start, họ sẽ đăng ký physical location, path, and port mà một hoặc service discovery instances có
  thể sử dụng để truy cập instances.
- Mặc dù mỗi instance of a service có một địa chỉ IP và port duy nhất, each service instance xuất hiện sẽ đăng ký theo
  cùng một service ID.
- service ID không gì khác hơn là một khóa xác định duy nhất một nhóm các trường hợp service instances giống nhau.
- Một service thường chỉ đăng ký với một service discovery service instance. Hầu hết các triển khai service discovery sử
  dụng `peer-to-peer model of data propagation`.
- Nơi dữ liệu xung quanh each service instance được giao tiếp với tất cả other nodes in the cluster.
- Tùy thuộc vào việc service discovery implementation, cơ chế lan truyền có thể sử dụng danh sách hardcoded list of
  services để truyền tới hoặc sử dụng giao thức đa hướng như giao thức kiểu truyền tin hoặc lây nhiễm để cho phép các
  other nodes "“discover”" các thay đổi trong cluster.
- Cuối cùng, each service instance đẩy đến hoặc kéo từ trạng thái của nó bởi service discovery service. Bất kỳ services
  nào không trả lại kết quả kiểm tra sức khỏe tốt sẽ bị xóa khỏi nhóm các trường hợp available service instances.
- Sau khi một dservice được đăng ký với một dịch vụ a service discovery service, nó sẽ sẵn sàng được sử dụng bởi một ứng
  dụng hoặc service cần tận dụng các khả năng của nó. Các mô hình khác nhau tồn tại để khách hàng discover a service.
  ![Alt text](Image/Figure6.3-Client-sideLoadBalancingCaches.png?raw=true "Title")
- Nó liên hệ với discovery service về tất cả các trường hợp mà service consumer (khách hàng) đang yêu cầu và sau đó
  caches data locally trên máy của service consumer’s machine.
- Mỗi khi khách hàng muốn gọi service, the service consumer sẽ tra cứu thông tin vị trí của service from the cache.
  Thông thường, client-side caching sẽ sử dụng một thuật toán load-balancing algorithm giống
  như [round-robin load-balancing algorithm](https://avinetworks.com/glossary/round-robin-load-balancing/#:~:text=Round%20robin%20load%20balancing%20is,the%20list%20and%20repeats%20again)
  để đảm bảo rằng các cuộc gọi service được trải rộng trên nhiều multiple service instances.
- Khách hàng sau khi liên hệ định kỳ với discovery service và làm mới cache of service instances.The client cache cuối
  cùng cũng nhất quán, nhưng luôn có rủi ro là khi máy khách liên hệ với service discovery instance để làm mới và các
  cuộc gọi được thực hiện, các cuộc gọi có thể được chuyển hướng đến service instance that isn’t healthy.
- Nếu trong quá trình gọi service, cuộc gọi service không thành công, the local service discovery cache bị vô hiệu hóa
  và service discovery client sẽ cố gắng làm mới các mục nhập của nó từ service discovery agent.

### 3.3.2 Service discovery in action using Spring and Netflix Eureka.

![Alt text](Image/Figure6.4-ByImplementingClient-sideCachingAndEureka.png?raw=true "Title")

- Services được bootstrapped, các licensing and organization services register với Eureka service. Quá trình đăng ký này
  cho Eureka biết physical location and port number của each service instance, cùng với một service ID cho service đang
  được khởi động.
- Khi licensing service gọi đến organization service, nó sẽ sử dụng Spring Cloud Load Balancer để cung cấp client-side
  load balancing. Load Balancer liên hệ với Eureka service để truy xuất service location information và sau đó caches it
  locally.
- Định kỳ, Spring Cloud Load Balancer sẽ ping Eureka service và làm mới local cache of service locations.

## 3.4 Building our Spring Eureka service

![Alt text](Image/Figure6.5-EurekaServerDependenciesInSpringInitializr.png?raw=true "Title")
![Alt text](Image/Listing6.2-SettingUpTheEureka.png?raw=true "Title")

- Sau khi thêm thông tin Spring Configuration Server trong bootstrap file trên Eureka Server và vô hiệu hóa Ribbon như
  default client-side load balancer.
  ![Alt text](Image/Listing6.3-SettingUpTheEurekaConfiguration.png?raw=true "Title")
  ![Alt text](Image/Listing6.3.1-SettingUpTheEurekaConfiguration.png?raw=true "Title")
- server.port: Đặt port mặc định.
- eureka.instance.hostname: Đặt Eureka instance hostname cho Eureka service.
- eureka.client.registerWithEureka: Thông báo cho Config Server không đăng ký với Eureka khi Spring Boot Eureka
  application starts.
- eureka.client.fetchRegistry: Khi được đặt thành false, hãy cho Eureka service biết rằng khi khởi động, nó không cần
  phải cache its registry information locally. Khi chạy Eureka client, bạn sẽ muốn thay đổi giá trị này cho các Spring
  Boot services sẽ đăng ký với Eureka.
- eureka.client.serviceUrl.defaultZone: Cung cấp service URL cho bất kỳ client nào. Nó là sự kết hợp của các thuộc tính
  eureka.instance.hostname và server.port.
- eureka.server.waitTimeInMsWhenSyncEmpty: Đặt thời gian chờ trước khi server nhận yêu cầu.
- Phần cuối cùng của công việc thiết lập bạn cần làm cho Eureka service của mình là thêm annotation vào lớp bootstrap
  ứng dụng mà bạn sử dụng để khởi động Eureka service của mình.
  ![Alt text](Image/Listing6.4-AnnotatingTheBootstrapClassToEnablTheEurekaServer.png?raw=true "Title")

## 3.5  Registering services with Spring Eureka

![Alt text](Image/Listing6.5-AddingTheSpringEurekaDependency.png?raw=true "Title")

- `spring-cloud-starter-netflix-eureka-client` giữ các tệp JAR mà Spring Cloud sử dụng để tương tác với Eureka service
  của bạn.
  ![Alt text](Image/Listing 6.6-AddingTheSpring.application.name.png?raw=true "Title")
  ![Alt text](Image/Listing6.8-ModifyingTheServiceApplication.properties.png?raw=true "Title")
- eureka.instance.preferIpAddress: thuộc tính cho Eureka biết rằng bạn muốn đăng ký service’s IP address với Eureka thay
  vì hostname của nó.

### 3.5.1 Eureka’s REST API.

- Để xem tất cả các phiên bản của một dịch vụ trong API REST, hãy chọn GET endpoint sau:
- `http://<eureka service>:8070/eureka/apps/<APPID>`
  ![Alt text](Image/Figure6.7-TheEurekaRESTAPI.png?raw=true "Title")

### 3.5.2 Eureka dashboard.

- Khi Eureka service hoạt động, chúng tôi có thể trỏ trình duyệt của mình tới http://localhost:8070 để xem Eureka
  dashboard.
  ![Alt text](Image/Figure6.8-TheEurekaDashboard.png?raw=true "Title")

## 3.6 Using service discovery to look up a service.

- Chúng ta sẽ xem xét 3 thư viện Spring/Netflix client trong đó service consumer có thể tương tác với Spring Cloud Load
  Balancer.
    - Spring Discovery Client.
    - Spring Discovery Client–enabled REST template.
    - Netflix Feign client.
      ![Alt text](Image/Listing6.9-CallingTheLicensingService.png?raw=true "Title")
    - clientType tham số được truyền trên tuyến thúc đẩy type of client.
        - Discovery: Sử dụng Discovery Client và lstandard Spring RestTemplate class để gọi organization service.
        - Rest: Sử dụng Spring RestTemplate nâng cao để gọi Load Balancer service.
        - Feign: Sử dụng Netflix’s Feign client library để gọi một service via the Load Balancer.

### 3.6.1 Looking up service instances with Spring Discovery Client.

- Spring Discovery Client cung cấp mức truy cập thấp nhất vào Load Balancer và các service được đăng ký trong đó.
- Sử dụng Discovery Client, bạn có thể truy vấn tất cả services registered với Spring Cloud Load Balancer client và các
  URL tương ứng của chúng.
  ![Alt text](Image/Listing6.11-SettingUpTheBootstrapClass.png?raw=true "Title")
- The @EnableDiscoveryClient là trigger cho Spring Cloud để cho phép application sử dụng Discovery Client và Spring
  Cloud Load Balancer libraries.
  ![Alt text](Image/Listing6.12-UsingTheDiscoveryClient.png?raw=true "Title")
  ![Alt text](Image/Listing6.12.1-UsingTheDiscoveryClient.png?raw=true "Title")
- Mục đầu tiên quan tâm trong mã là DiscoveryClient class. Bạn sử dụng lớp này để tương tác với Spring Cloud Load
  Balancer.
- Sau đó, để truy xuất all instances of the organization services đã đăng ký với Eureka, bạn sử dụng phương thức
  getInstances (), chuyển service key mà bạn đang tìm kiếm để truy xuất danh sách các đối tượng ServiceInstance.
- Lớp ServiceInstance giữ thông tin về a specific instance of a service, bao gồm hostname, port, and URI.

### 3.6.2 Invoking services with a Load Balancer–aware Spring REST template.

- Tiếp theo, chúng ta sẽ thấy một ví dụ về cách sử dụng REST template mà Load Balancer-aware.
- Đây là một trong những cơ chế phổ biến hơn để tương tác với Load Balancer qua Spring. Để sử dụng lớp RestTemplate nhận
  biết Load Balancer, chúng ta cần xác định bean RestTemplate với cSpring Cloud @LoadBalanced annotation.
  ![Alt text](Image/Listing6.13-AnnotatingAndDefining.png?raw=true "Title")
  ![Alt text](Image/Listing6.14-UsingALoadBalancer–backedRestTemplate.png?raw=true "Title")
  ![Alt text](Image/Listing6.14.1-UsingALoadBalancer–backedRestTemplate.png?raw=true "Title")
- The Load Balancer–enabled RestTemplate class phân tích cú pháp URL được truyền vào nó và sử dụng bất kỳ thứ gì được
  chuyển vào dưới dạng server name làm khóa để truy vấn Load Balancer cho instance of a service.

### 3.6.3 Invoking services with Netflix Feign client.

- Một giải pháp thay thế cho Spring Load Balancer–enabled RestTemplate class là Netflix’s Feign client library
- The Feign library có một cách tiếp cận khác để gọi một REST service.
- Với cách tiếp cận này, trước tiên nhà phát triển xác định Java interface và sau đó thêm Spring Cloud annotations để
  ánh xạ Eureka-based service mà Spring Cloud Load Balancer sẽ gọi.
- The Spring Cloud framework sẽ tự động tạo ra a proxy class để gọi REST service được nhắm mục tiêu. Không có mã nào
  được viết để gọi service other than an interface definition.
- Để cho phép Feign client sử dụng trong licensing service, chúng ta cần thêm annotation, @EnableFeignClients.
  ![Alt text](Image/Listing6.15-EnablingTheSpringCloud-NetflixFeign.png?raw=true "Title")
  ![Alt text](Image/Listing6.16-DefiningAFeignInterfaceForCalling.png?raw=true "Title")
- chúng ta đã sử dụng @FeignClient annotation, chuyển cho nó application ID of the service mà chúng tôi muốn interface
  to represent. Sau đó, chúng tôi đã xác định một phương thức, getOrganization(), trong interface của chúng ta, phương
  thức này có thể được khách hàng gọi để gọi organization service.




