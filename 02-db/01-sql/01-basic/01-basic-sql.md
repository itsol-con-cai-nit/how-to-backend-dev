# SQL BASIC

## What is SQL ?
- `DBMS-Database Management System`là phần mềm được thiết kế để có thể xác định, tiến hành các thao tác, truy xuất và quản lý dữ liệu trong `database`.
- `Database` là một bộ sưu tập dữ liệu được tổ chức bày bản và thường được truy cập từ hệ thống máy tính hoặc tồn tại dưới dạng tập tin trong `DBMS`.
- `SQL-Structure Query Language` là ngôn ngữ cho phép chúng ta viết các câu lệnh để thao tác với dữ liệu trong `database`.
  ![](img/img.png)

- Trong lập trình khi nhắc đến `SQL` và `NoSQL` chúng ta ngầm hiểu là ám chỉ đến 2 loại `DBMS` khác nhau:
    - `Relational database`: `MySQL`, `MSSQL`, `Postgres`... Đây là những `database` thường sử dụng để lưu trữ dữ liệu có cấu trúc rõ ràng, có tính quan hệ với nhau và quan trọng nhất là sử dụng `SQL` để truy vấn data.
    - `Non-relational database`: `MongoDB`, `Cassandra`, `DynamoDB`... Hiểu một cách đơn giản nó ngược lại so với `SQL`. Đây là những `database` có khả năng lưu trữ dữ liệu phi cấu trúc, không cần thể hiện quan hệ ràng buộc với nhau, không sử dụng `SQL` để truy vấn data.

- Trong nội dung của phần này chúng ta sẽ chỉ tập trung tìm hiểu về `Relational database`, `SQL` và các `DBMS` hỗ trợ `Relational database`.

## Database structure
- Trong `relational database`, `data` được tổ chức dưới dạng `table` bao gồm `column (field)` và `row (record)`
  ![img.png](img.png)
- Ví dụ với `table` Product chứa thông tin của các sản phẩm bao gồm một vài column cơ bản như id, product_name, price, description.

### Column (field)
- Một `Column` sẽ bao gồm tên của `Column` và kiểu dữ liệu -`Data types`

#### Data types
- `Data Types` định nghĩa kiểu của giá trị sẽ được lưu trong `column` của một `table`.
  ![img_1.png](img/img_1.png)
- `Data Types` có thể được chia thành các loại chính sau:
    - `Numeric`: các thể loại số như là int, tinyint, bigint, float, real, etc.
      ![img_2.png](img/img_2.png)
    - `Date, Time`: thời gian và ngày tháng năm như là  Date, Time, Datetime, etc.
      ![img_3.png](img/img_3.png)
    - `Character, String:`: ký tự và chuỗi như là char, varchar, text, etc.
      ![img_4.png](img/img_4.png)  
      *`Lưu ý`: các kiểu dữ liệu trên dành cho các luồng ký tự (character stream) không nên được sử dụng cho kí tự unicode.*
    - `Unicode character`: ký tự thuộc các bảng mã Unicode như là nchar, nvarchar, ntext, etc.
      ![img_5.png](img/img_5.png)  
      *`Lưu ý`: các loại dữ liệu kể trên chưa được hỗ trợ trong MySql.*
    - `Binary`: chuỗi nhị phân như là binary, varbinary, etc.
      ![img_6.png](img/img_6.png)
    - `Miscellaneous`: các kiểu đặc biệt khác như là clob, blob, xml, cursor, table, etc.
      ![img_7.png](img/img_7.png)
- Một số lưu ý quan trọng về `Data Types`:
    - Không phải tất cả các `Data Type` đều được hỗ trợ trong các `DBMS`, ví dụ `Oracle database` không hỗ trợ kiểu DATETIME.
      vì vậy khi thiết kế cơ sở dữ liệu hay viết câu lệnh `SQL` cần nắm rõ kiểu dữ liệu có được hỗ trợ trong `DBMS` đang sử dụng hay không.
    - Bài viết chỉ nêu ra những loại dữ liệu phổ biến nhất trong các `DBMS` một số `DBMS` vẫn có các `Data type` của riêng chúng như
      Microsoft SQL Server có `money`, `smallmoney`.
    - Cần lưu ý về khả năng lưu trữ của `Data Type` để chọn ra loại phù hợp nhất cho yêu cầu của dự án.
