# DevOps
![Alt text](devops.png?raw=true "Title")

## DevOps là gì 

- Là một văn hóa làm việc đề cao sự hợp tác 
- Hướng đến kéo 2 giai đoạn phát triển và vận hành xích lại gần nhau hơn
- Devops ra đời nhằm tối ưu hóa chương trình phát triển phần mềm

## Phát triển phần mềm `DevOps`
![Alt text](ptpm.png?raw=true "Title")

### Phát triển
- developer
- designer
- QA & QC (là người chịu trách nhiệm đảm bảo chất lượng sản phẩm)

### Vận hành
- System Administrator
- Release Engineer
- DBA
- Security Engineer
- System Engineer
- Operation Executive 
- Network Engineer

## Build trong phần phát triển `DevOps`

- Build là một quá trình tự động để chuẩn bị source code deploy lên môi trường bằng cách sử dụng script or tool
- Tuỳ vào ngôn ngữ đc sử dụng mà cần phải compile, transform.
- Thông thường build cũng giống như việc chạy command-line tool để chạy đoạn code đã được viết script hoặc được setting trong file config. 
- Việc build không nên bị phụ thuộc vào IDE cũng như những config của máy tính


## Docker 

- Docker là một nền tảng được sử dụng để cung cấp cho bạn building , deploying
 và running ứng dụng một cách dễ dạng hơn.
- Docker sẽ hoạt động thông qua cách sử dụng những containers ở trên nền tảng ảo hóa
- Docker phát hành 13/03/2013
- Viết bằng: Golang

## Các thành phần chính của docker

### Docker image
- Docker image là một file bất biến - không thay đổi, chứa các source code, libraries, dependencies, tools và các files khác cần thiết cho một ứng dụng để chạy.
- Do tính chất read-only. Chúng đại diện cho một application và virtual environment của nó tại một thời điểm cụ thể. Tính nhất quán này là một trong những tính năng tuyệt vời của Docker. Nó cho phép các developers test và thử nghiệm phần mềm trong điều kiện ổn định, thống nhất.
- Dockerfile là một file dạng text không có extension(mở rộng), và tên bắt buộc phải là Dockerfile
- Dockerfile là một file kịch bản sử dụng để tạo mới một image

### Docker container
- Container nó là một giải pháp để chuyển giao phần mềm 1 cách đáng tin cậy giữa các môi trường máy tính khác nhau
- tạo ra 1 môi trường chứa mọi thứ mà phần mềm có thể chạy đc
- không bị các yếu tố liên quan đến môi trường hệ thống làm ảnh hưởng tới
- cũng như ko làm ảnh hưởng tới các phần còn lại của hệ thống
- VD: java, mysql, python .... Tất cả được bỏ gọn vào 1 hoặc nhiều cái thùng (container)
#### ưu điểm 
- Linh động: Triển khai ở bất kỳ nơi đâu do sự phụ thuộc của ứng dụng vào tầng OS cũng như cơ sở hạ tầng được loại bỏ
- Nhanh: Do chia sẻ host OS nên container có thể được tạo gần như một cách tức thì. Điều này khác với vagrant - tạo môi trường ảo ở level phần cứng, nên khi khởi động mất nhiều thời gian hơn
- Nhẹ: Container cũng sử dụng chung các images nên cũng không tốn nhiều disks
- Đồng nhất :Khi nhiều người cùng phát triển trong cùng một dự án sẽ không bị sự sai khác về mặt môi trường
- Đóng gói: Có thể ẩn môi trường bao gồm cả app vào trong một gói được gọi là container. Có thể test được các container. Việc bỏ hay tạo lại container rất dễ dàng
#### nhược điểm
- tính an toàn thấp

### Docker volume
- Volume trong Docker như một ổ đĩa ảo được dùng để chứa và chia sẻ dữ liệu giữa các container hoặc giữa container với host
- Có hai loại volume chính: Volume giữa host và container, Volume giữa các container

### Docker network
- Docker network sẽ đảm nhiệm nhiệm vụ kết nối mạng giữa các container với nhau, kết nối giữa container với bên ngoài
- Docker network có thể cung cấp hầu hết các chức năng mà một hệ thống mạng bình thường cần có
- Hệ thống network Docker là dạng plugable, sử dụng drivers. Hầu hết các driver được cung cấp mặc định, với các chức năng cốt lõi của các chức năng mạng thông thường

#### Docker network (BRIDGE)
- Đây là driver mạng default của Docker. Nếu không chỉ định driver thì bridge sẽ là driver mạng mặc định khi khởi tạo.

#### Docker Network (HOST)
- Dùng khi container cần giao tiếp với host và sử dụng luôn mạng ở host, vì sử dụng mạng của máy chủ đang chạy nên không còn lớp mạng nào giữa container với Docker Host phù hợp khi cần connect từ container ra thẳng ngoài host

#### Docker Network (OVERLAY)
- Mạng lớp phủ - Overlay network tạo một mạng phân tán giữa nhiều máy chủ Docker. Kết nối nhiều Docker daemons với nhau và cho phép các cụm services giao tiếp với nhau. Chúng ta có thể sử dụng overlay network để giao tiếp dễ dàng giữa cụm các services với một container độc lập, hay giữa 2 container với nhau ở khác máy chủ Docker daemons.

#### Docker network (MACVLAN)
- Mạng Macvlan cho phép chúng ta gán địa chỉ MAC cho container, điều này làm cho mỗi container như là một thiết bị vật lý trong mạng. Docker daemon định tuyến truy cập tới container bởi địa chỉ MAC. Sử dụng driver macvlan là lựa chon tốt khi các ứng dụng khác cần phải connect đến theo địa chỉ vật lý hơn là thông qua các lớp mạng của máy chủ

#### Docker network (NONE)
- Với container không cần networking hoặc cần disable đi tất cả mọi networking, chúng ta sẽ chọn driver này. Thường được dùng với mạng tùy chỉnh. Driver này không thể dùng trong cụm swarm














