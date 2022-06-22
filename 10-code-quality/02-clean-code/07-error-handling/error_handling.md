#XỬ LÝ LỖI

##1- Sử dụng ngoại lệ thay vì để cho code xử lý
- Không nên dùng cách đặt cờ hay trả lại mã lỗi vì gây lộn xộn, việc code và xử lý lỗi bị trộn lẫn với nhau.
- Trong ví dụ bên dưới có thể thấy rằng việc trả lại mã lỗi khiến cho người sử dụng hàm phải bắt lỗi ngay mà không phải
lúc nào họ cũng biết là hàm này có lỗi hay nhớ phải bắt lỗi.
```java
public class DeviceController { 
    ...
    public void sendShutDown() { 
        DeviceHandle handle = getHandle(DEV1); 
        // Kiểm tra trạng thái của thiết bị
        if (handle != DeviceHandle.INVALID) {
            // Lưu trạng thái thiết bị vào record field
            retrieveDeviceRecord(handle);
            // Nếu không bị treo, hãy tắt
            if (record.getStatus() != DEVICE_SUSPENDED) {
                pauseDevice(handle); 
                clearDeviceWorkQueue(handle); 
                closeDevice(handle);
            } else {
                logger.log("Thiết bị bị treo. Không thể tắt");
            }
        } else {
            logger.log("Xử lý không hợp lệ cho: " + DEV1.toString()); 
        }
    } 
    ...
}
```
- Logic của code và việc sử lý lỗi nên được xử lý riêng biệt.
```java
public class DeviceController { 
    ...
    public void sendShutDown() {
        try {
            tryToShutDown();
        } catch (DeviceShutDownError e) {
            logger.log(e); }
    }

    private void tryToShutDown() throws DeviceShutDownError {
        DeviceHandle handle = getHandle(DEV1);
        DeviceRecord record = retrieveDeviceRecord(handle);
        pauseDevice(handle);
        clearDeviceWorkQueue(handle);
        closeDevice(handle);
    }

    private DeviceHandle getHandle(DeviceID id) {
        ...
        throw new DeviceShutDownError("Xử lý không hợp lệ cho: " + id.toString()); 
        ...
    }
    ...
}
```
 
##2- Viết khối Try-Catch-Finally
- Khi viết TCF hãy kiểm tra, tìm kiếm các ngoại lệ có thể xảy ra rồi mới bắt đầu xử lý các bước logic

##3- Ưu tiên sử dụng Unchecked Exceptions
- Lý do ko dùng các checked exceptions: Các checked exceptions khi sử dụng ở các cấp thấp sẽ dẫn đến thay đổi ở các cấp
cao hơn, vi phạm nguyên tắc mở đóng (thay đổi sẽ không gây nên quá nhiều thay đổi trong các thành phần khác của chương trình).

- Custom lại lớp exception theo nhu cầu của người gọi.

##4- Cung cấp ngữ cảnh có ngoại lệ
- Một ngoại lệ cần cung cấp đầy đủ thông tin để xác định nguyên nhân, vị trí lỗi
- Nếu cần thiết hãy cung cấp thêm thông tin ở phần log.

##5- Xác định ngoại lệ theo nhu cầu 
- Có nhiều cách phân loại lỗi. Chúng ta có thể phân loại chúng theo nguồn của chúng: Chúng đến từ thành phần này hay
thành phần khác? Hoặc loại của chúng: Chúng bị lỗi thiết bị, lỗi mạng, hoặc lỗi lập trình? Tuy nhiên, khi chúng ta xác
định các lớp ngoại lệ trong một ứng dụng, mối quan tâm quan trọng nhất của chúng ta là cách chúng được bắt.
```java
ACMEPort port = new ACMEPort(12);
try { 
    port.open();
} catch (DeviceResponseException e) { 
    reportPortError(e);
    logger.log("Device response exception", e);
} catch (ATM1212UnlockedException e) { 
    reportPortError(e); 
    logger.log("Unlock exception", e);
} catch (GMXError e) { 
    reportPortError(e);
    logger.log("Device response exception");
} finally { 
    ...
}
```
- Code ngoại lệ đểu thuộc 1 loại, giải quyết bằng cách gói các API vào 1 class và đảm bảo trả về 1 ngoại lệ chung.

```java
LocalPort port = new LocalPort(12); 
try {
    port.open();
} catch (PortDeviceFailure e) {
    reportError(e);
    logger.log(e.getMessage(), e);
} finally {
    ...
}
```

```java
public class LocalPort {
    private ACMEPort innerPort;
    public LocalPort(int portNumber) { 
        innerPort = new ACMEPort(portNumber);
    }
    public void open() { 
        try {
            innerPort.open();
        } catch (DeviceResponseException e) {
            throw new PortDeviceFailure(e);
        } catch (ATM1212UnlockedException e) {
            throw new PortDeviceFailure(e);
        } catch (GMXError e) {
            throw new PortDeviceFailure(e);
        } 
    }
    ...
}
```
- Đóng gói API bên thứ 3 giúp giảm bớt sự phụ thuộc vào nó , thông thường 1 lớp ngoại lệ duy nhất là tốt cho 1 vùng code
cụ thể.

##5- Xác định dòng chảy bình thường
- Khi code xử lý lỗi dùng để xử lý 1 trường hợp đặc biệt, hãy tạo ra 1 lớp hoặc cấu hình 1 đối tượng để  xử lý trường  hợp đặc biệt đó 
-  Bên dưới là 1 trường hợp xử lý lỗi đặc biệt, lỗi đã trở thành 1 phần trong logic nghiệp vụ.
```java
try {
    MealExpenses expenses = expenseReportDAO.getMeals(employee.getID()); 
    m_total += expenses.getTotal();
} catch(MealExpensesNotFound e) { 
    m_total += getMealPerDiem();
}
```
- Cách xử lý là tạo ra 1 lớp cấu hình để xử lý trường hợp đặc biệt.
```java
MealExpenses expenses = expenseReportDAO.getMeals(employee.getID()); 
m_total += expenses.getTotal();
```
```java
public class PerDiemMealExpenses implements MealExpenses { 
    public int getTotal() {
        // trả lại công tác phí mặc định 
    }
}
```

6- Đừng trả về null
- Trả về null khiến cho người gọi hàm phải check null liên tục và không phải ai cũng nhớ việc check null.
```java
public void registerItem(Item item) { 
    if (item != null) {
        ItemRegistry registry = peristentStore.getItemRegistry(); 
        if (registry != null) {
            Item existing = registry.getItem(item.getID());
            if (existing.getBillingPeriod().hasRetailOwner()) {
                existing.register(item); 
            }
        } 
    }
}
```
- Hãy ném ra 1 ngoại lệ hoặc trả về 1 đối tượng đặc biệt để thay thế.
```java
List<Employee> employees = getEmployees(); 
if (employees != null) {
    for(Employee e : employees) { 
        totalPay += e.getPay();
    } 
```

```java
List<Employee> employees = getEmployees(); 
for(Employee e : employees) {
    totalPay += e.getPay(); 
}
```

```java
public List<Employee> getEmployees() { 
    if( .. không có employees .. )
        return Collections.emptyList(); 
}
```

7- Đừng truyền vào null
- Truyền null vào 1 hàm sẽ rắt dễ gây ra lỗi
```java
public class MetricsCalculator {
    public double xProjection(Point p1, Point p2) { 
        return (p2.x – p1.x) * 1.5;
    }
    ...
}
```
```java
calculator.xProjection(null, new Point(12, 13));
```
- Không có cách để giải quyết vấn đề này chỉ có thể đừng truyền vào null.

- Nguồn: https://toihocdesignpattern.com/chuong-7-xu-ly-loi.html
