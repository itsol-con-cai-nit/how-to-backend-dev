## 4.1 Introduction

- Tất cả các hệ thống, đặc biệt là các hệ thống phân tán, đều gặp lỗi.
- Cách chúng ta xây dựng các ứng dụng của mình để ứng phó với sự thất bại đó là một phần quan trọng trong công việc của
  mọi nhà phát triển phần mềm.
- Tuy nhiên, khi nói đến việc xây dựng các hệ thống có khả năng phục hồi, hầu hết các kỹ sư phần mềm chỉ tính đến sự
  thất bại hoàn toàn của một phần cơ sở hạ tầng hoặc service.
- Họ tập trung vào việc xây dựng dự phòng vào từng lớp ứng dụng của họ bằng cách sử dụng các kỹ thuật như clustering key
  servers, load balancing giữa services và phân tách cơ sở hạ tầng thành multiple locations.
- Trong khi các cách tiếp cận này tính đến sự mất mát hoàn toàn của một thành phần hệ thống, chúng chỉ giải quyết một
  phần nhỏ của việc xây dựng các hệ thống có `resilient systems`. Khi một service crashes, thật dễ dàng phát hiện ra
  rằng service đó không còn ở đó và ứng dụng có thể định tuyến xung quanh service đó.
- Tuy nhiên, khi một service chạy chậm, việc phát hiện ra hiệu suất kém và định tuyến xung quanh nó là điều vô cùng khó
  khăn.
    - Sự xuống cấp của service có thể bắt đầu không liên tục và sau đó tạo đà. Các dấu hiệu đầu tiên của lỗi có thể là
      một nhóm nhỏ người dùng phàn nàn về một vấn đề nào đó cho đến khi đột nhiên, vùng chứa ứng dụng cạn kiệt luồng
      luồng của nó và sập hoàn toàn.
    - Các cuộc gọi đến các remote services thường đồng bộ và không cắt ngắn một cuộc gọi dài. Nhà phát triển ứng dụng
      thường gọi một service để thực hiện một hành động và đợi dịch vụ đó hoạt động trở lại. Người gọi không có khái
      niệm về thời gian chờ để giữ cho cuộc gọi service không bị treo.
    - Các ứng dụng thường được thiết kế để đối phó với sự cố hoàn toàn của các remote resources, không phải là sự suy
      thoái từng phần. Thông thường, miễn là dịch vụ không hoàn toàn bị lỗi, ứng dụng sẽ tiếp tục gọi một service hoạt
      động kém và sẽ không bị lỗi nhanh. Trong trường hợp này, ứng dụng hoặc service đang gọi có thể giảm chất lượng
      hoặc nhiều khả năng bị sập vì cạn kiệt tài nguyên.
    - Tài nguyên cạn kiệt là nơi tài nguyên hạn chế, chẳng hạn như nhóm luồng hoặc kết nối cơ sở dữ liệu, đạt tối đa và
      calling client phải đợi tài nguyên đó khả dụng trở lại.

## 4.2 What are client-side resiliency patterns?

- Client-side resiliency software patterns tập trung vào việc bảo vệ a client của a remote resource (một cuộc gọi
  microservice khác hoặc database lookup) khỏi sự cố khi remote resource fails do lỗi hoặc hiệu suất kém.
  ![Alt text](Image/Figure7.1-TheFourClientResiliencyPatternsAct.png?raw=true "Title")
- These patterns (client-side load balancing, circuit breaker, fallback, and bulkhead) được thực hiện the client (
  microservice) gọi remote resource.

### 4.2.1 Client-side load balancing

- Client-side load balancing liên quan đến việc the client tra cứu service’s individual instances của dịch vụ từ a
  service discovery agent (như Netflix Eureka) và sau đó caching the physical location của các service instance nói
  trên.
- Khi service consumer cần gọi service consumer, the client-side load balancer sẽ trả về location from the pool of
  service locations mà nó duy trì.
- Bởi vì client-side load balancer nằm giữa service client và service consumer, load balancer có thể phát hiện xem
  service instance có lỗi hoặc hoạt động kém hay không. Nếu client-side load balancer phát hiện ra sự cố, nó có thể xóa
  service instance đó khỏi nhóm các service locations có sẵn và ngăn bất kỳ cuộc gọi nào trong tương lai đánh vào
  service instance đó.

### 4.2.2 Circuit breaker

- The circuit breaker pattern được mô phỏng theo một cầu dao điện. Trong hệ thống điện, thiết bị ngắt mạch sẽ phát hiện
  xem có quá nhiều dòng điện chạy qua dây dẫn hay không. Nếu bộ ngắt mạch phát hiện sự cố, nó sẽ ngắt kết nối với phần
  còn lại của hệ thống điện và giữ cho hệ thống không chiên các thành phần hạ lưu.
- Với a software circuit breaker, khi một remote service được gọi, circuit breaker sẽ giám sát cuộc gọi. Nếu cuộc gọi
  kéo dài quá lâu, circuit breaker sẽ ngắt và ngắt cuộc gọi. The circuit breaker pattern cũng giám sát tất cả các cuộc
  gọi đến một tài nguyên từ xa và nếu đủ số cuộc gọi không thành công, việc triển khai bộ ngắt mạch sẽ “bật”, không
  nhanh và ngăn các cuộc gọi trong tương lai tới tài nguyên từ xa bị lỗi.

### 4.2.3 Fallback processing

- Với the fallback pattern, khi một remote service không thành công, thay vì tạo ra một ngoại lệ, service consumer thực
  hiện một đường dẫn mã thay thế và cố gắng thực hiện hành động thông qua một phương tiện khác. Điều này thường liên
  quan đến việc tìm kiếm dữ liệu từ một nguồn dữ liệu khác hoặc xếp hàng đợi yêu cầu của người dùng để xử lý trong tương
  lai. Cuộc gọi của người dùng không được hiển thị ngoại lệ cho thấy sự cố, nhưng họ có thể được thông báo rằng yêu cầu
  của họ sẽ phải được thử sau.
- Ví dụ: giả sử bạn có một trang web thương mại điện tử theo dõi hành vi của người dùng và cung cấp cho họ các đề xuất
  về các mặt hàng khác mà họ có thể muốn mua. Thông thường, bạn sẽ gọi một microservice để chạy phân tích hành vi trước
  đây của người dùng và trả lại danh sách các đề xuất phù hợp với người dùng cụ thể đó. Tuy nhiên, nếu dịch vụ tùy chọn
  không thành công, dự phòng của bạn có thể là truy xuất danh sách tùy chọn chung hơn dựa trên tất cả các giao dịch mua
  của người dùng, có tính khái quát hơn nhiều. Và, dữ liệu này có thể đến từ một nguồn dữ liệu và dịch vụ hoàn toàn
  khác.

### 4.2.4 Bulkheads

- The bulkhead patternn dựa trên ý tưởng đóng tàu. Một con tàu được chia thành các khoang gọi là vách ngăn, được ngăn
  cách và kín nước hoàn toàn. Ngay cả khi thân tàu bị thủng, một vách ngăn vẫn giữ nước giới hạn trong khu vực của tàu
  nơi xảy ra đâm thủng và ngăn toàn bộ con tàu đổ đầy nước và chìm.
- Khái niệm tương tự có thể được áp dụng cho một dịch vụ phải tương tác với multiple remote resources. Khi sử dụng
  bulkhead pattern, bạn ngắt các cuộc gọi đến remote resources thành các nhóm luồng của riêng chúng và giảm nguy cơ xảy
  ra sự cố với một lệnh gọi remote resources chậm sẽ làm hỏng toàn bộ ứng dụng.

## 4.3 Why client resiliency matters

- Mặc dù chúng ta đã nói về các patterns of client resiliency khác nhau này trong phần tóm tắt, nhưng hãy đi sâu vào một
  ví dụ cụ thể hơn về nơi có thể áp dụng these patterns . Chúng ta sẽ xem xét một tình huống điển hình và xem lý do tại
  sao các mẫu client resiliency patterns lại quan trọng đối với việc triển khai microservice-based architecture chạy
  trên the cloud.
- Applications A and B giao tiếp trực tiếp with the licensing service.Licensing service truy xuất dữ liệu từ cơ sở dữ
  liệu và gọi organization service để thực hiện một số công việc cho nó.
- The organization service truy xuất dữ liệu từ một nền tảng cơ sở dữ liệu hoàn toàn khác và gọi đến một another
  service, inventory service, from a third-party cloud provider, service của họ chủ yếu dựa vào thiết bị Lưu trữ gắn
  trong Mạng nội bộ (NAS) để ghi dữ liệu vào hệ thống tệp được chia sẻ. Ứng dụng C gọi trực tiếp inventory service.
  ![Alt text](Image/Figure7.2-AnApplicationCanBeThought.png?raw=true "Title")
- The developers đã viết organization service không bao giờ lường trước được sự chậm chạp xảy ra với các cuộc gọi đến
  inventory service. Họ đã viết mã của mình để việc ghi vào cơ sở dữ liệu của họ và các lần đọc từ dịch vụ xảy ra trong
  cùng một giao dịch. Khi the inventory service bắt đầu chạy chậm, không chỉ nhóm luồng cho các yêu cầu đến the
  inventory service bắt đầu sao lưu, số lượng kết nối cơ sở dữ liệu trong nhóm kết nối của vùng chứa dịch vụ sẽ cạn
  kiệt. Các kết nối này được giữ mở vì các lệnh gọi đến the inventory service không bao giờ hoàn tất.
- Giờ đây, licensing service bắt đầu cạn kiệt tài nguyên vì nó đang gọi organization service, dịch vụ này đang chạy chậm
  do inventory service. Cuối cùng, cả ba ứng dụng ngừng phản hồi vì chúng hết tài nguyên trong khi chờ yêu cầu hoàn
  thành. Toàn bộ kịch bản này có thể tránh được nếu một circuit-breaker pattern đã được thực hiện tại mỗi điểm mà tài
  nguyên phân tán được gọi (hoặc là lệnh gọi đến cơ sở dữ liệu hoặc lệnh gọi đến service).

- In figure 7.3,licensing service không bao giờ gọi trực tiếp organization service. Thay vào đó, khi cuộc gọi được thực
  hiện, licensing service sẽ ủy quyền lệnh gọi thực tế của service cho circuit breaker,circuit breaker này sẽ nhận cuộc
  gọi và gói nó trong một chuỗi (thường được quản lý trong một nhóm luồng) độc lập với người gọi ban đầu.
- Bằng cách gói cuộc gọi trong một chuỗi, the client không còn trực tiếp chờ cuộc gọi hoàn tất. Thay vào đó, circuit
  breaker giám sát luồng và có thể ngắt cuộc gọi nếu luồng chạy quá dài.
  ![Alt text](Image/Figure7.3-TheCircuitBreakerTrips.png?raw=true "Title")
    - Fail fast: Khi một remote service gặp sự cố xuống cấp, ứng dụng sẽ nhanh chóng bị lỗi và ngăn chặn các vấn đề về
      cạn kiệt tài nguyên thường làm tắt toàn bộ ứng dụng. Trong hầu hết các tình huống ngừng hoạt động, tốt hơn là giảm
      một phần thay vì ngừng hoàn toàn.
    - Fail gracefully: Bằng cách timing out and failing fast, the circuit breaker pattern cho chúng ta khả năng hỏng hóc
      một cách duyên dáng hoặc tìm kiếm các cơ chế thay thế để thực hiện ý định của người dùng. Ví dụ: nếu người dùng
      đang cố gắng truy xuất dữ liệu từ một nguồn dữ liệu và nguồn dữ liệu đó đang bị suy giảm dịch vụ, thì các dịch vụ
      của chúng ta có thể truy xuất dữ liệu đó từ một vị trí khác.
    - Recover seamlessly: Với the circuit breaker pattern hoạt động như một trung gian, circuit breaker có thể kiểm tra
      định kỳ để xem liệu tài nguyên được yêu cầu có trực tuyến trở lại hay không và kích hoạt lại quyền truy cập vào nó
      mà không cần sự can thiệp của con người.

## 4.4  Setting up the licensing service to use Spring Cloud and Resilience4j.

- Để bắt đầu khám phá Resilience4j, chúng ta cần thiết lập dự án pom.xml để nhập các dependencies.
  ![Alt text](Image/Listing7.1-AddingResilience4jDependency.png?raw=true "Title")

## 4.5 Implementing a circuit breaker

- Để hiểu về circuit breakers, chúng ta có thể nghĩ đến hệ thống điện. Điều gì xảy ra khi có quá nhiều dòng điện chạy
  qua dây dẫn trong hệ thống điện? Như bạn sẽ nhớ lại, nếu bộ ngắt mạch phát hiện sự cố, bộ ngắt mạch sẽ ngắt kết nối
  với phần còn lại của hệ thống, tránh làm hỏng thêm các bộ phận khác. Điều tương tự cũng xảy ra trong kiến trúc mã.
- Những gì chúng ta muốn đạt được với circuit breakers trong mã của chúng ta là giám sát các remote calls và tránh chờ
  đợi lâu trên các dịch vụ. Trong các tình huống này, circuit breaker có nhiệm vụ ngắt các kết nối đó và giám sát nếu có
  thêm các cuộc gọi thất bại hoặc hoạt động kém. This pattern sau đó thực hiện một lỗi nhanh chóng và ngăn chặn các yêu
  cầu trong tương lai đến một remote resource bị lỗi.
  ![Alt text](Image/Figure7.4-Resilience4jCircuitBreaker.png?raw=true "Title")
- Ban đầu, e Resilience4j circuit breaker bắt đầu ở closed state và chờ client requests. closed state sử dụng ring bit
  buffer để lưu trữ trạng thái thành công hoặc thất bại của các yêu cầu. Khi một yêu cầu thành công được thực hiện,
  circuit breaker lưu một bit 0 trong ring bit buffer. Nhưng nếu nó không nhận được phản hồi từ dịch vụ được gọi, nó sẽ
  lưu bit 1.
- Để tính toán tỷ lệ thất bại, ring phải đầy. Ví dụ, trong kịch bản trước, ít nhất 12 cuộc gọi phải được đánh giá trước
  khi có thể tính được tỷ lệ thất bại. Nếu chỉ có 11 yêu cầu được đánh giá, circuit breaker sẽ không chuyển sang trạng
  thái mở ngay cả khi tất cả 11 cuộc gọi đều thất bại. Lưu ý rằng circuit breaker chỉ mở khi tỷ lệ hỏng hóc trên ngưỡng
  có thể cấu hình.
  ![Alt text](Image/Figure7.5-Resilience4jCircuitBreakerRingBitBuffer.png?raw=true "Title")
- Khi circuit breaker ở trạng thái mở, tất cả các cuộc gọi bị từ chối trong thời gian có thể định cấu hình và circuit
  breaker ném ra một `CallNotPeriledException`. Khi hết thời gian cấu hình, bộ ngắt mạch chuyển sang trạng thái nửa mở
  và cho phép một số yêu cầu để xem liệu service có còn khả dụng hay không.
- Ở half-open state, the circuit breaker sử dụng ring bit buffer có thể cấu hình khác để đánh giá tỷ lệ hỏng hóc. Nếu tỷ
  lệ hỏng hóc mới này vượt quá ngưỡng đã định cấu hình, the circuit breaker sẽ chuyển trở lại trạng thái mở; nếu nó thấp
  hơn hoặc bằng ngưỡng, nó sẽ chuyển trở lại thành đóng. Điều này có thể hơi khó hiểu, nhưng chỉ cần nhớ, ở trạng thái
  mở, bộ ngắt mạch từ chối và ở trạng thái đóng, bộ ngắt mạch chấp nhận tất cả các yêu cầu.

  ![Alt text](Image/Figure7.6-Resilience4jSitsBetweenEachRemoteResource.png?raw=true "Title")
- Hãy bắt đầu cuộc thảo luận Resilience4j của chúng ta bằng cách trình bày cách kết thúc việc truy xuất licensing
  service data từ cơ sở dữ liệu cấp phép bằng synchronous circuit breaker . Với một cuộc gọi đồng bộ, the licensing
  service truy xuất dữ liệu của nó nhưng đợi câu lệnh SQL hoàn thành hoặc hết thời gian chờ bộ ngắt mạch trước khi tiếp
  tục xử lý.
- Resilience4j và Spring Cloud sử dụng @CircuitBreaker để đánh dấu Java class methods được quản lý bởi Resilience4j
  circuit breaker. Khi Spring framework nhìn thấy annotation này, nó sẽ tự động tạo ra một proxy bao bọc method và
  manages tất cả các lệnh gọi đến phương thức đó thông qua một nhóm luồng được đặt riêng để xử lý các cuộc gọi từ xa.
  Hãy thêm @CircuitBreaker vào phương thức getLicensesByOrganization().
  ![Alt text](Image/Listing7.2-WrappingARemoteResource.png?raw=true "Title")
- Đây không giống như nhiều mã và không phải vậy, nhưng có rất nhiều chức năng bên trong một annotation này. Với việc sử
  dụng chú thích @CircuitBreaker, bất cứ khi nào phương thức getLicensesByOrganization () được gọi, cuộc gọi được bao
  bọc bởi một Resilience4j circuit breaker. Bộ circuit breaker làm gián đoạn mọi nỗ lực không thành công để gọi phương
  thức getLicensesByOrganization ().
  ![Alt text](Image/Listing7.3-PurposelyTimingOut.png?raw=true "Title")
- Nếu bạn nhập _http://localhost:8080/v1/organization/e6a625cc-718b-48c2-ac76-1dfdff9a531e/license/endpoint_
  đủ lần trong Postman, bạn sẽ thấy thông báo lỗi sau được trả về từ licensing service:
  ![Alt text](Image/Message-LicensingService.png?raw=true "Title")
  -Nếu chúng ta tiếp tục thực hiện dịch vụ không thành công,the ring bit buffer sẽ lấp đầy và chúng ta sẽ nhận được
  lỗi.
  ![Alt text](Image/Figure7.7-ACircuitBreakerError.png?raw=true "Title")

### 4.5.1 Customizing the circuit breaker

- Làm cách nào customize the Resilience4j circuit breaker.
- Điều này có thể dễ dàng thực hiện bằng cách thêm một số parameters vào tệp application.yml, boostrap.yml hoặc service
  configuration file nằn trong Spring Config Server repository.
  ![Alt text](Image/Listing7.4-CustomizingTheCircuitBreaker.png?raw=true "Title")
- Resilience4j cho phép chúng ta tùy chỉnh hoạt động của circuit breakers thông qua các thuộc tính của ứng dụng. Chúng
  ta có thể định configure bao nhiêu instances tùy thích và each instance có thể có một different configuration.
    - ringBufferSizeInClosedState: Đặt kích thước của the ring bit buffer khi circuit breaker ở trạng thái đóng. Giá trị
      mặc định là 100.
    - ringBufferSizeInHalfOpenState: Đặt kích thước của the ring bit buffer khi bộ ngắt mạch ở trạng thái nửa mở. Giá
      trị mặc định là 10.
    - waitDurationInOpenState: Đặt thời gian chờ the circuit breaker trước khi chuyển trạng thái từ mở sang nửa mở. Giá
      trị mặc định là 60.000 ms.
    - failureRateThreshold: Configures tỷ lệ phần trăm của ngưỡng tỷ lệ thất bại. Hãy nhớ rằng, khi tỷ lệ hỏng hóc lớn
      hơn hoặc bằng ngưỡng này, the circuit breaker sẽ chuyển sang trạng thái mở và bắt đầu các cuộc gọi ngắn mạch. Giá
      trị mặc định là 50.
    - recordExceptions: Liệt kê các trường hợp ngoại lệ sẽ được coi là thất bại. Theo mặc định, tất cả các trường hợp
      ngoại lệ được ghi lại là lỗi.

## 4.6 Fallback processing

- Một phần vẻ đẹp của the circuit breaker pattern là do “middleman” nằm giữa consumer of a remote resource và resource
  itself, chúng ta có cơ hội ngăn chặn lỗi service và chọn một hướng hành động thay thế để thực hiện.
- Trong Resilience4j, đây được gọi là fallback strategy và dễ dàng thực hiện. Hãy xem cách xây dựng a simple fallback
  strategy cho licensing service của chúng ta để trả về licensing object cho biết hiện không có thông tinlicensing.
  ![Alt text](Image/Listing7.5-ImplementingAFallbackInResilience4j.png?raw=true "Title")
  ![Alt text](Image/Listing7.5.1-ImplementingAFallbackInResilience4j.png?raw=true "Title")
- Để thực hiện fallback strategy với Resilience4j, chúng ta cần thực hiện hai điều. Đầu tiên, chúng ta cần thêm thuộc
  tính fallbackMethod vào @CircuitBreaker hoặc bất kỳ annotation nào khác (chúng ta sẽ giải thích điều này sau). Thuộc
  tính này phải chứa tên của phương thức sẽ được gọi khi Resilience4j ngắt cuộc gọi do lỗi.
- Điều thứ hai chúng ta cần làm là xác định một fallback method. Phương thức này phải nằm trong cùng lớp với phương thức
  gốc được bảo vệ bởi @CircuitBreaker.
- Để tạo phương thức dự phòng trong Resilience4j, chúng ta cần tạo a method that contains cùng chữ ký với hàm gốc cộng
  với one extra parameter, đó là target exception parameter. Với cùng một chữ ký, chúng ta có thể chuyển tất cả các
  parameters từ original method sang fallback method.
- Bây giờ chúng ta đã có fallback method, hãy tiếp tục và gọi lại endpoint của chúng ta. Lần này, khi chúng ta chọn nó
  trong Postman và gặp lỗi hết thời gian chờ (hãy nhớ rằng chúng ta có cơ hội một trong ba), chúng ta sẽ không nhận
  được ngoại lệ từ service call.
  ![Alt text](Image/Figure7.8-Resilience4jFallback.png?raw=true "Title")

## 4.7 Implementing the bulkhead pattern

- Trong một microservice-based application, chúng ta thường cần gọi nhiều microservice để hoàn thành một tác vụ cụ thể.
  Không sử dụng bulkhead pattern, hành vi mặc định cho các lệnh gọi này là chúng được thực thi bằng cách sử dụng cùng
  một chủ đề được dành riêng để xử lý các yêu cầu cho entire Java container.
- Với khối lượng lớn, các vấn đề về hiệu suất với một trong số nhiều service có thể dẫn đến việc tất cả các luồng cho
  Java container bị hoạt động tối đa và đang chờ xử lý công việc, trong khi các yêu cầu công việc mới được sao lưu. Vùng
  chứa Java cuối cùng sẽ bị sập.
- The bulkhead pattern segregates remote resource calls trong nhóm luồng của riêng chúng để có thể chứa a single
  misbehaving service và không làm hỏng contained. Resilience4j cung cấp hai cách triển khai khác nhau của the bulkhead
  pattern.
    - Semaphore bulkhead: Sử dụng cách tiếp cận cách ly semaphore, giới hạn số lượng yêu cầu đồng thời đến service. Khi
      đạt đến giới hạn, nó bắt đầu từ chối các yêu cầu.
    - Thread pool bulkhead: sử dụng một hàng đợi có giới hạn và một nhóm luồng cố định. Cách tiếp cận này chỉ từ chối yêu
      cầu khi nhóm và hàng đợi đã đầy.
- Mô hình này hoạt động tốt nếu chúng ta có một số lượng nhỏ remote resources được truy cập trong một ứng dụng và khối
  lượng cuộc gọi cho các dịch vụ riêng lẻ được phân phối đồng đều (tương đối).
- Vấn đề là nếu chúng ta có các service có khối lượng lớn hơn nhiều hoặc thời gian hoàn thành lâu hơn các service khác,
  chúng ta có thể kết thúc việc đưa luồng cạn kiệt vào nhóm luồng của mình vì một service sẽ thống trị tất cả các luồng
  trong nhóm luồng mặc định.
  ![Alt text](Image/Figure7.9-TheDefaultResilience4jBulkhead.png?raw=true "Title")
- May mắn thay, Resilience4j cung cấp một cơ chế dễ sử dụng để tạo bulkheads giữa các remote resource calls khác nhau.
  ![Alt text](Image/Figure7.10-AResilience4jCommandTied.png?raw=true "Title")
  ![Alt text](Image/Listing7.6-ConfiguringTheBulkhead.png?raw=true "Title")
    - maxWaitDuration: Đặt lượng thời gian tối đa để chặn luồng khi nhập một bulkhead. Giá trị mặc định là 0.
    - maxConcurrentCalls: Đặt số lượng cuộc gọi đồng thời tối đa mà bulkhead cho phép. Giá trị mặc định là 25.
    - maxThreadPoolSize: Đặt kích thước nhóm chủ đề tối đa. Giá trị mặc định là Runtime.getRuntime ().
      AvailableProcessors ().
    - queueCapacity: Đặt thời gian tối đa mà các luồng nhàn rỗi sẽ đợi các tác vụ mới trước khi kết thúc. Điều này xảy
      ra khi số luồng nhiều hơn số luồng lõi. Giá trị mặc định là 20 ms.
- chúng ta thường không biết các đặc điểm hiệu suất của một service cho đến khi nó hoạt động dưới mức tải.
- A key indicator cho thấy các thuộc tính nhóm luồng cần được điều chỉnh là khi một lệnh gọi dịch vụ đang trong quá
  trình hết thời gian chờ, ngay cả khi remote resource được nhắm mục tiêu là lành mạnh.
  ![Alt text](Image/Listing7.7-CreatingABulkheadAround.png?raw=true "Title")
- @Bulkhead: annotation này cho biết rằng chúng ta đang thiết lập bulkhead pattern. Nếu chúng ta không đặt thêm giá
  trị nào trong các thuộc tính ứng dụng, thì Resilience4j sẽ sử dụng các giá trị mặc định được đề cập trước đó cho
  semaphore bulkhead type.

## 4.8 Implementing the retry pattern

- The retry pattern chịu trách nhiệm thử lại các nỗ lực giao tiếp với một service khi service đó ban đầu không thành
  công.
- Đối với this pattern, chúng ta phải chỉ định số lần thử lại cho một service instance nhất định và khoảng thời gian
  chúng ta muốn vượt qua giữa mỗi lần thử lại.
  ![Alt text](Image/Listing7.8-ConfiguringTheRetryPattern.png?raw=true "Title")
- maxRetryAttempts: cho phép chúng ta xác định số lần thử lại tối đa cho service( default = 3).
- waitDuration: cho phép chúng ta xác định thời gian chờ giữa các lần thử lại. Giá trị mặc định cho tham số này là 500
  ms.
- retryexceptions: thiết lập danh sách các lớp lỗi sẽ được thử lại. Giá trị mặc định empty.
  ![Alt text](Image/Listing7.9-CreatingABulkheadAround.png?raw=true "Title")
  ![Alt text](Image/Listing7.9.1-CreatingABulkheadAround.png?raw=true "Title")

## 4.9 Implementing the rate limiter pattern

- This retry pattern sẽ ngừng quá tải service với nhiều cuộc gọi hơn mức nó có thể sử dụng trong một khung thời gian
  nhất định. Đây là một kỹ thuật bắt buộc để chuẩn bị cho API của chúng ta có tính khả dụng và độ tin cậy cao.
- Resilience4j cung cấp hai cách triển khai cho rate limiter pattern: AtomicRateLimiter và SemaphoreBasedRateLimiter.
  Việc triển khai mặc định cho RateLimiter là AtomicRateLimiter.
    - SemaphoreBasedRateLimiter là đơn giản nhất. Việc triển khai này dựa trên việc có một
      java.util.concurrent.Semaphore lưu trữ các quyền hiện tại.Trong trường hợp này, tất cả các luồng người dùng sẽ gọi
      phương thức semaphore.tryAcquire để kích hoạt một cuộc gọi đến một luồng nội bộ bổ sung bằng cách thực thi
      semaphore.release khi một giới hạn mới bắt đầu.
    - Không giống như SemaphoreBasedRate, AtomicRateLimiter không cần quản lý luồng vì các luồng người dùng tự thực thi
      tất cả logic quyền. AtomicRateLimiter chia tất cả các nanoseconds từ lúc bắt đầu thành các chu kỳ và mỗi khoảng
      thời gian chu kỳ là khoảng thời gian làm mới (tính bằng nanoseconds). Sau đó, vào đầu mỗi chu kỳ, chúng ta nên đặt
      các quyền hoạt động để giới hạn khoảng thời gian.
        - ActiveCycle: Số chu kỳ được cuộc gọi gần đây nhất sử dụng.
        - ActivePermissions: Số quyền có sẵn sau cuộc gọi cuối cùng.
        - NanosToWait: Số nanoseconds để đợi quyền cho cuộc gọi cuối cùng.
- To better understand it:
    - Chu kỳ là những phần thời gian bằng nhau.
    - Nếu các quyền hiện có là không đủ, chúng ta có thể thực hiện bảo lưu quyền bằng cách giảm các quyền hiện tại và
      tính thời gian chúng ta cần để chờ quyền xuất hiện.
    - Resilience4j cho phép điều này bằng cách xác định số lượng cuộc gọi được phép trong một khoảng thời gian (
      limitForPeriod); tần suất các quyền được làm mới (limitRefreshPeriod); và một chuỗi có thể đợi bao lâu để có được
      quyền (timeoutDuration).
      ![Alt text](Image/Listing7.10-ConfiguringTheRetryPattern.png?raw=true "Title")
    - timeoutDuration: cho phép chúng ta xác định thời gian một luồng chờ cấp phép; giá trị mặc định cho tham số này là
      5 s (giây).
    - limitRefreshPeriod: cho phép chúng ta đặt khoảng thời gian giới hạn việc làm mới. Sau mỗi khoảng thời gian, bộ
      giới hạn tốc độ đặt lại số lượng quyền trở lại giá trị limitForPeriod. Giá trị mặc định cho limitRefreshPeriod là
      500 ns (nanoseconds).
    - limitForPeriod: cho phép chúng ta đặt số lượng quyền có sẵn trong một khoảng thời gian làm mới. Giá trị mặc định
      cho limitForPeriod là 50.
      ![Alt text](Image/Listing7.11-CreatingABulkheadAround.png?raw=true "Title")
- Sự khác biệt chính giữa the bulkhead và limiter pattern là bulkhead pattern chịu trách nhiệm giới hạn số lượng cuộc
  gọi đồng thời. With the rate limiter, chúng ta có thể giới hạnn tổng số cuộc gọi trong một khung thời gian nhất định.

## 4.10 ThreadLocal and Resilience4j

- Chúng ta sẽ xác định một số giá trị trong ThreadLocal để xem liệu chúng có được truyền qua các phương thức bằng cách
  sử dụng annotations Resilience4J hay không.
- Hãy xem một ví dụ cụ thể. Thông thường, trong a REST-based environment, chúng ta muốn chuyển thông tin theo ngữ cảnh
  đến một cuộc gọi service sẽ giúp chúng ta quản lý cách hoạt động một service.
- Ví dụ: chúng ta có thể chuyển một correlation ID hoặc authentication token trong HTTP header của lệnh gọi REST mà sau
  đó có thể được truyền cho bất kỳ lệnh gọi service nào. Correlation ID cho phép chúng ta có một số nhận dạng duy nhất
  có thể được truy tìm qua nhiều cuộc gọi service trong một giao dịch duy nhất.
- Để cung cấp giá trị này ở bất kỳ đâu trong lệnh gọi service của chúng ta, chúng ta có thể sử dụng Spring Filter class
  để chặn mọi cuộc gọi trong REST service của chúng ta.
- Sau đó, nó có thể lấy thông tin này từ HTTP request đến và lưu trữ thông tin ngữ cảnh này trong một đối tượng
  UserContext tùy chỉnh.
- Sau đó, bất cứ lúc nào mã của chúng ta cần truy cập giá trị này trong lệnh gọi REST service của chúng ta, mã của
  chúng ta có thể truy xuất UserContext từ biến lưu trữ ThreadLocal và đọc giá trị.
  ![Alt text](Image/Listing7.13-AllUserContextData.png?raw=true "Title")
- UserContextHolder class lưu trữ UserContext trong một ThreadLocal class. Sau khi nó được lưu trữ trong ThreadLocal,
  bất kỳ mã nào được thực thi cho một yêu cầu sẽ sử dụng đối tượng UserContext được lưu trữ trong UserContextHolder.
- Để thực thi ví dụ của chúng ta, chúng ta sẽ gọi service của mình, chuyển vào một correlation ID bằng cách sử dụng
  HTTP header tmx-correlation-id và một giá trị của TEST-CORRELATION-ID.
  ![Alt text](Image/Figure7.11-AddingACorrelationID.png?raw=true "Title")



