# Một số lệnh khác
- **`git log`** hoặc **`git diff`**: xem lịch sử commit
- **`git status`**: xem trạng thái của git repo xem đang ở nhánh nào, có thay đổi gì không.
- **`git reset`**: để xóa sự thay đổi về trạng thái ban đầu chưa sửa hoặc về bất cứ commit nào trong quá khứ.

    - `git reset --hard HEAD`       (về commit HEAD)

    - `git reset --hard HEAD^`      (về commit trước HEAD)

    - `git reset --hard HEAD~1`     (về trước 1 commit so với "^")

    - `git reset --hard HEAD~2`     (về commit trước 2 commit với HEAD)
- `git restore`: phục hồi các file
- **`git stash`** trở về trạng thái ban đầu: là lệnh dùng để thiết lập trạng thái ban đầu cho tất cả các file nằm trong thư mục làm việc (working directory), điều kiện là dữ liệu đó đã được đưa vào trạng thái Staged hoặc đã từng được committed. Trạng thái ban đầu ở đây chính là nội dung dữ liệu ban đầu của file (so với commit cuối cùng hoặc pull từ remote repository).
    - `git stash save` --lưu change hiện tại vào stash
    - `git stash list` --hiển thị danh sách stash
    - `git stash drop <stash>`: xóa 1 stash ra khỏi lịch sử, stash truyền vào dạng *stash@{0,1,2....}*
    - `git stash apply <stash_name>` --đẩy changes từ 1 stash vào một nhánh
    - `git stash show`: xem chi tiết 1 stash , so sánh nó với dữ liệu ban đầu
    - `git stash clear`: xóa tất cả stash
- `Git --amend`: thay đổi commit cuối cùng

    Trong một số trường hợp bạn commit nhưng bị quên add một số file nào đó và bạn không muốn tạo ra một commit mới thì có thể sử dụng lệnh `commit` kết hợp tham số `--amend` để gộp các file đó và bổ sung vào commit cuối cùng, vì vậy không tạo ra commit mới.  
    Cú pháp lệnh:

    - `git add file_cần_thêm`
    - `git commit --amend` 