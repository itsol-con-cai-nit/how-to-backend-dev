# Chapter 3: Creating and Manipulating documents

## 1. Inserting new documents, ObjectId

Muốn insert dữ liệu (insert document) vào một collection, chúng ta sử dụng hàm [`insert()`](https://www.mongodb.com/docs/manual/reference/method/db.collection.insert/). 

Giả sử chúng ta cần insert data vào collection `inspections` thuộc db `sample_training`, các document sẽ có dạng như sau.

![](./img/inspections-collection-data.png)

Một điểm đáng chú ý trong MongoDB khác so với SQL, đó chính là việc ta không cần insert đầy đủ, đúng theo cấu trúc của collection (mà thực ra không có cấu trúc được chỉ định sẵn cho collection rằng bắt buộc nó phải có bao nhiêu cột, bao nhiêu trường giống SQL). Ngược lại, với SQL, khi chúng ta tạo bảng, chúng ta cần quy định xem bảng đó có bao nhiêu trường/cột, các cột nào được phép NULL và các cột nào không. Từ đó, khi chạy lệnh insert ta cần insert tất cả các trường mà không cho phép NULL.

Ví dụ, một document của collection `inspections` chứa các trường như `certificate_number`, `result`, `sector`, ... Nhưng nếu ta cố tình insert một document mới không có các trường trên, mà là các trường ta tùy ý thêm vào thì sao?

![](./img/junk-insert.png)

MongoDB vẫn cho phép chúng ta insert như bình thường, và để kiểm chứng, chúng ta thử tìm kiếm.

![](./img/junk-find.png)

Điều này nói lên sự khác biệt to lớn giữa SQL và NoSQL, một bên 







