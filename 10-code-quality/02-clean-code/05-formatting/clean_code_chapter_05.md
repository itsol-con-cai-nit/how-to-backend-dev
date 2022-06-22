# Định dạng code

##  Mục đích của việc định dạng
- Giúp code của bạn trở nên rõ ràng.
- Giúp code dễ bảo trì, nâng cấp.

## Định dạng theo chiều dọc
Kích thước một file mã nguồn Java có các kích thước khác nhau (Như hình)
  
![img.png](img.png)

Độ dài tệp trong một số dự án

- Có bảy dự án khác nhau được mô tả. Junit, FitNesse, testNG, Time and Money (tam), JDepend, Ant, và Tomcat.
- Đường kẻ đen xuyên qua hình chữ nhật biểu thị số dòng lớn nhất, nhỏ nhất trong mỗi file nguồn.
- Hình chữ nhật biểu thị cho khoảng 1/3 giá trị phổ biến nhất.
- Khoảng giữa hình chữ nhật là giá trị trung bình.

=> Các file code thường có độ dài dưới 200 dòng, , trong khoảng từ 200 dến 500 thường là các file xây dựng 
cho hệ thống quan trọng như thư viện.

=> Các file nhỏ sẽ dễ hiểu hơn các file lớn.


### Viết code như viết văn
- Tên file nên đơn giản nhưng có khả năng giải thích.
- Những dòng đầu tiên của file mã nguồn sẽ cung cấp các khái niệm và thuật toán.
- Tính trừu tượng sẽ giảm dần theo vị trí từ trên xuống dưới.
- Các hàm trong file nên có độ dài nhỏ và sắp xếp có tổ chức.

### Khoảng cách giữa code và các định nghĩa 
- Viết code để đọc được từ trái qua phải, từ trẻn xuống dưới, mỗi dòng đại diện cho một biểu thức hoặc một mệnh đề,
và mỗi nhóm dòng đại diện cho một mạch logic hoàn chỉnh. hững định nghĩa đó nên được tách biệt với nhau bằng những dòng trống.

```java
package fitnesse.wikitext.widgets;
 
import java.util.regex.*;
 
public class BoldWidget extends ParentWidget {
    public static final String REGEXP = "'''.+?'''";
    private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
        Pattern.MULTILINE + Pattern.DOTALL
    );
 
    public BoldWidget(ParentWidget parent, String text) throws Exception {
        super(parent);
        Matcher match = pattern.matcher(text);
        match.find();
        addChildWidgets(match.group(1));
    }
     
    public String render() throws Exception {
        StringBuffer html = new StringBuffer("<b>");
        html.append(childHtml()).append("</b>");
        return html.toString();
    }
}
```

Khi xóa các dòng trống đó đi:

```java
package fitnesse.wikitext.widgets;
import java.util.regex.*;
public class BoldWidget extends ParentWidget {
    public static final String REGEXP = "'''.+?'''";
    private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
        Pattern.MULTILINE + Pattern.DOTALL);
    public BoldWidget(ParentWidget parent, String text) throws Exception {
        super(parent);
        Matcher match = pattern.matcher(text);
        match.find();
        addChildWidgets(match.group(1));}
    public String render() throws Exception {
        StringBuffer html = new StringBuffer("<b>");
        html.append(childHtml()).append("</b>");
        return html.toString();
    }
}
```

=> Khoảng cách dọc dùng để tách các định nghĩa.

### Mật độ theo chiều dọc
=> Các dòng liên quan đến nhau sẽ xuất hiện liền theo chiều dọc.

- Code bị chia cắt bởi comment vô nghĩa.
```java
public class ReporterConfig {
 
    /**
     * The class name of the reporter listener
     */
    private String m_className;
     
    /**
     * The properties of the reporter listener
     */
    private List<Property> m_properties = new ArrayList<Property>();
 
    public void addProperty(Property property) {
        m_properties.add(property);
    }
```
- Code đảm bảo mật độ.
```java
public class ReporterConfig {
    private String m_className;
    private List<Property> m_properties = new ArrayList<Property>();
 
    public void addProperty(Property property) {
        m_properties.add(property);
    }
```
=> Trông dễ nhìn, biết ngay rằng đây có 1 class với 2 biến và một phương thức

### Khoảng cách theo chiều dọc
- Các định nghĩa có liên quan với nhau nên được giữ gần nhau theo chiều dọc.

**Khai Báo Biến**

- Các biến nên được khai báo càng gần với nơi sử dụng chúng càng tốt
  (Vì hàm của chúng ta rất ngắn, nên các biến cục bộ phải đc khai báo ở dòng đầu tiên của mỗi hàm)
```java
private static void readPreferences(){
    InputStream is = null;
    try{
        is = new FileInputStream(getPreferencesFile());
        setPreferences(new Properties(getPreferences()));
        getPreferences().load(is);
    }catch (IOException e){
        try{
            if (is != null)
                is.close();
        }catch (IOException e1){
        }
    }
}
```

- Các biến điều khiển vòng lặp nên được khai báo trong vòng lặp, như ví dụ:
```java
public int countTestCases() {
    int count= 0;
    for (Test each : tests)
        count += each.countTestCases();
    return count;
}
```

- Trong một vài trường hợp hiếm gặp, một biến có thể được khai báo ở đầu một khối lệnh hoặc ngay trước một vòng lặp trong một hàm-hơi-dài. Bạn có thể thấy một biến như vậy bên dưới, được trích từ một hàm-rất-dài trong TestNG.
```java
for (XmlTest test : m_suite.getTests()) {
    TestRunner tr = m_runnerFactory.newTestRunner(this, test);
    tr.addListener(m_textReporter);
    m_testRunners.add(tr);
 
    invoker = tr.getInvoker();
     
    for (ITestNGMethod m : tr.getBeforeSuiteMethods()) {
        beforeSuiteMethods.put(m.getMethod(), m);
    }
    for (ITestNGMethod m : tr.getAfterSuiteMethods()) {
        afterSuiteMethods.put(m.getMethod(), m);
    }
}
```

**Các Biến Instance**
- Nên được khai báo ở đầu các class, và cũng không nên tạo khoảng cách giữa các biến này vì trong một class được thiết kế tốt, các biến instance được sử dụng bởi nhiều phương thức của class.

- Đừng giấu biến Instance của bạn như ví dụ bên dưới.
```java
public class TestSuite implements Test {
    static public Test createTest(Class<? extends TestCase> theClass, String name) {
        ... 
    }
 
    public static Constructor<? extends TestCase>
    getTestConstructor(Class<? extends TestCase> theClass)
    throws NoSuchMethodException { 
        ... 
    }
 
    public static Test warning(final String message) {
        ...
    }
 
    private static String exceptionToString(Throwable t) {
        ...
    }
 
    private String fName;
    private Vector<Test> fTests= new Vector<Test>(10);
 
    public TestSuite() {
    }
 
    public TestSuite(final Class<? extends TestCase> theClass) {
        ...
    }
 
    public TestSuite(Class<? extends TestCase> theClass, String name) {
        ...
    }
    ... ... ... ... ...
}
```

**Các Hàm Phụ Thuộc Nhau**
- Nếu một hàm gọi một hàm khác, chúng nên được đặt gần nhau. Và nếu có thể, hàm gọi nên ở trên hàm được gọi.

```java
public class WikiPageResponder implements SecureResponder {
    protected WikiPage page;
    protected PageData pageData;
    protected String pageTitle;
    protected Request request;
    protected PageCrawler crawler;
     
    public Response makeResponse(FitNesseContext context, Request request)
    throws Exception {
        String pageName = getPageNameOrDefault(request, "FrontPage");
        loadPage(pageName, context);
        if (page == null)
            return notFoundResponse(context, request);
        else
            return makePageResponse(context);
    }
 
    private String getPageNameOrDefault(Request request, String defaultPageName)
    {
        String pageName = request.getResource();
        if (StringUtil.isBlank(pageName))
            pageName = defaultPageName;
 
        return pageName;
    }
 
    protected void loadPage(String resource, FitNesseContext context)
        throws Exception {
        WikiPagePath path = PathParser.parse(resource);
        crawler = context.root.getPageCrawler();
        crawler.setDeadEndStrategy(new VirtualEnabledPageCrawler());
        page = crawler.getPage(context.root, path);
        if (page != null)
            pageData = page.getData();
    }
 
    private Response notFoundResponse(FitNesseContext context, Request request)
        throws Exception {
        return new NotFoundResponder().makeResponse(context, request);
    }
 
    private SimpleResponse makePageResponse(FitNesseContext context)
        throws Exception {
        pageTitle = PathParser.render(crawler.getFullPath(page));
        String html = makeHtml(context);
 
        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setContent(html);
        return response;
    }
...
```

**Các Định Nghĩa Có Liên Quan**
- Các định nghĩa có liên quan, có mỗi liên hệ nhất định, mối quan hệ càng mạnh thì khoảng cách giữa chúng càng ít.

```java
public class Assert {
    static public void assertTrue(String message, boolean condition) {
        if (!condition)
            fail(message);
    }
 
    static public void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }
 
    static public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }
     
    static public void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }
    ...
```

-> Các hàm này có mối liên hệ mạnh vì chúng có cùng một định dạng tên và thực hiện các biến thể của cùng một tác vụ. Việc chúng gọi nhau là hiển nhiên, ngay cả khi không, chúng vẫn nên ở gần nhau.

### Trật tự theo chiều dọc
-Các lời gọi hàm phụ thuộc nhau theo hướng đi xuống, nghĩa là lời gọi hàm nên gọi tới hàm bên dưới (Hàm thực hiện gọi hàm đó) -> giúp tạo ra luồn dữ liệu trong module mã nguồn từ cấp cao đến cấp thấp

##  Định dạng theo chiều ngang
- Độ rộng của một dòng code nên dài bao nhiều ?

Hình cho thấy sự phân phối kích thước của các dòng trong 7 dự án

![img_11.png](img_11.png)

- Một dòng xoay quanh mức 45 ký tự.
- Có khoảng 1% số dòng có độ rộng từ 20-60 ký tự, chiếm 40% mã nguồn.
- Có khoảng 30% số dòng dưới 10 ký tự.
-> Giữ dòng code ngắn.
-> Ngắn hơn vạch kẻ dọc trên inteleji, giữ ở dưới 80 ký tự, max là 120 ký tự.

### Khoảng cách và mật độ


- Bao quanh các toán tử gán bằng khoảng trắng để làm nổi bật chúng. Câu lệnh gán có hai yếu tố rõ ràng: vế trái và vế phải.
Các khoảng trắng làm sự tách biệt này thêm rõ ràng.
- Không đặt khoảng trắng giữa tên hàm và dấu ngoặc đơn mở.
```java
private void measureLine(String line) {
    lineCount++;
    int lineSize = line.length();
    totalChars += lineSize;
    lineWidthHistogram.addLine(lineSize, lineCount);
    recordWidestLine(lineSize);
}
```

- Tách các đối số trong lời gọi hàm để làm nổi bật chúng.
- Dùng khoảng trắng để thể hiện sự ưu tiên.
```java
public class Quadratic {
    public static double root1(double a, double b, double c) {
        double determinant = determinant(a, b, c);
        return (-b + Math.sqrt(determinant)) / (2*a);
    }
 
    public static double root2(int a, int b, int c) {
        double determinant = determinant(a, b, c);
        return (-b - Math.sqrt(determinant)) / (2*a);
    }
     
    private static double determinant(double a, double b, double c) {
        return b*b - 4*a*c;
    }
}
```

### Căn chỉnh theo chiều ngang
- Căn theo format của inteleji.

### Việc lùi đầu dòng
- Lùi các dòng code tỉ lệ thuận với vị trí của chúng trong hệ thống phân cấp.
- Lùi dòng giúp mắt dễ nhìn và phân biệt các phần của hệ thống.
```java
public class FitNesseServer implements SocketServer { private FitNesseContext
context; public FitNesseServer(FitNesseContext context) { this.context =
context; } public void serve(Socket s) { serve(s, 10000); } public void
serve(Socket s, long requestTimeout) { try { FitNesseExpediter sender = new
FitNesseExpediter(s, context);
sender.setRequestParsingTimeLimit(requestTimeout); sender.start(); }
catch(Exception e) { e.printStackTrace(); } } }
 
-----
 
public class FitNesseServer implements SocketServer {
    private FitNesseContext context;
 
    public FitNesseServer(FitNesseContext context) {
        this.context = context;
    }
 
    public void serve(Socket s) {
        serve(s, 10000);
    }
     
    public void serve(Socket s, long requestTimeout) {
        try {
            FitNesseExpediter sender = new FitNesseExpediter(s, context);
            sender.setRequestParsingTimeLimit(requestTimeout);
            sender.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
- Không viết các lệnh 1 dòng như bên dưới.
```java
public class CommentWidget extends TextWidget
{
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";
 
    public CommentWidget(ParentWidget parent, String text){super(parent, text);}
    public String render() throws Exception {return ""; }
}
```
- Hãy format lại thành: 
```java
public class CommentWidget extends TextWidget
{
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";
 
    public CommentWidget(ParentWidget parent, String text){
        super(parent, text);
    }
 
    public String render() throws Exception {
        return ""; 
    }
}
```

![img_18.png](img_18.png)

**- NHỮNG KHOẢNG TRỐNG GIẢ**
- Hãy dùng 1 dấu phẩy xuống dòng để phần biệt các vòng lặp while hoặc vòng lặp for có một thân giả.
```java
while (dis.read(buf, 0, readBufferSize) != -1)
    ;
```

## Các quy tắc trong nhóm
- Có quy tắc format riêng của bản thân, khi hoạt động trong nhóm thì tuân theo quy tắc đặt ra dùng chung của nhóm. 
- 1 nhóm hoặc dự án đều cần các quy tắc format code riêng để đảm bảo tính nhất quán trong code.

## Các quy tắc định dạng của Uncle Bob**
```java
public class CodeAnalyzer implements JavaFileAnalysis {
    private int lineCount;
    private int maxLineWidth;
    private int widestLineNumber;
    private LineWidthHistogram lineWidthHistogram;
    private int totalChars;
 
    public CodeAnalyzer() {
        lineWidthHistogram = new LineWidthHistogram();
    }
 
    public static List<File> findJavaFiles(File parentDirectory) {
        List<File> files = new ArrayList<File>();
        findJavaFiles(parentDirectory, files);
        return files;
    }
 
    private static void findJavaFiles(File parentDirectory, List<File> files) {
        for (File file : parentDirectory.listFiles()) {
            if (file.getName().endsWith(".java"))
                files.add(file);
            else if (file.isDirectory())
                findJavaFiles(file, files);
        }
    }
 
    public void analyzeFile(File javaFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(javaFile));
        String line;
        while ((line = br.readLine()) != null)
            measureLine(line);
    }
 
    private void measureLine(String line) {
        lineCount++;
        int lineSize = line.length();
        totalChars += lineSize;
        lineWidthHistogram.addLine(lineSize, lineCount);
        recordWidestLine(lineSize);
    }
 
    private void recordWidestLine(int lineSize) {
        if (lineSize > maxLineWidth) {
            maxLineWidth = lineSize;
            widestLineNumber = lineCount;
        }
    }
 
    public int getLineCount() {
        return lineCount;
    }
 
    public int getMaxLineWidth() {
        return maxLineWidth;
    }
 
    public int getWidestLineNumber() {
        return widestLineNumber;
    }
 
    public LineWidthHistogram getLineWidthHistogram() {
        return lineWidthHistogram;
    }
 
    public double getMeanLineWidth() {
        return (double)totalChars/lineCount;
    }
 
    public int getMedianLineWidth() {
        Integer[] sortedWidths = getSortedWidths();
        int cumulativeLineCount = 0;
        for (int width : sortedWidths) {
            cumulativeLineCount += lineCountForWidth(width);
            if (cumulativeLineCount > lineCount/2)
                return width;
        }
        throw new Error("Cannot get here");
    }
 
    private int lineCountForWidth(int width) {
        return lineWidthHistogram.getLinesforWidth(width).size();
    }
 
    private Integer[] getSortedWidths() {
        Set<Integer> widths = lineWidthHistogram.getWidths();
        Integer[] sortedWidths = (widths.toArray(new Integer[0]));
        Arrays.sort(sortedWidths);
        return sortedWidths;
    }
}
```
- Nguồn:
https://toihocdesignpattern.com/chuong-5-dinh-dang-code.html












