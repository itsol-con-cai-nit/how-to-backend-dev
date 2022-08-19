# Chapter 2: Importing, Exporting and Querying data

## 1. How does MongoDB store data?

Khi chúng ta xem, tìm kiếm, cập nhật, thêm mới, xóa bỏ (nói chung là các hành động CRUD) với document và collection trong MongoDB, chúng ta đang thao tác với [JSON (JavaScript Object Notation)](https://www.w3schools.com/js/js_json_intro.asp).

![](./img/json-ex.png)

Chúng ta sẽ không bàn nhiều về JSON ở bài viết này. Ngoài các ưu điểm của JSON ra, ta cần đề cập tới 1 số yếu điểm của nó đó chính là các ký tự là văn bản (con người đọc được, tốn công encode/edcode khi lưu trữ), tiêu tốn khá nhiều khoảng trắng và xuống dòng, có số lượng kiểu dữ liệu hạn chế, ... Vì thế nên MongoDB cho phép người dùng xem dữ liệu dưới dạn JSON nhưng lại lưu trữ dữ liệu dưới dạng BSON.

BSON (Binary JSON) là một kiểu JSON nhưng lưu dưới dạng nhị phân. Nó cắt giảm khoảng trống thừa, tăng tốc độ khi truy cập, hỗ trợ nhiều kiểu dữ liệu và linh hoạt hơn. Điều này giúp cho việc sử dụng BSON để lưu trữ mang lại hiệu năng cao hơn của MongoDB.

![](./img/bson-ex.png)

## 2. Import & export

## 3. Data explorer

## 4. Find command
