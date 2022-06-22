# CHƯƠNG 4: Comment thế nào cho chuẩn ?

                                        “Đừng biến đống code gớm ghiếc của bạn thành comment – hãy viết lại nó”

                                                                                                BRIAN W. KERNIGHAN AND P. J. PLAUGHER
## Các comment tốt nhất nên trở thành sự lựa chọn cuối cùng
- Khi các dòng code của bạn chưa rõ nghĩa thì cần đến các dòng comment
- => Suy nghĩ cách thể biến các dòng code trở nên dễ hiểu và chính xác với những gì ta muốn thể hiện
- Các dòng comment dần trở nên dứt đoạn với độ chính xác giảm dần theo thời gian tái lập và tạo lên chương trình

```java
MockRequest request;
private final String HTTP_DATE_REGEXP =
 "[SMTWF][a-z]{2}\\,\\s[0-9]{2}\\s[JFMASOND][a-z]{2}\\s"+
 "[0-9]{4}\\s[0-9]{2}\\:[0-9]{2}\\:[0-9]{2}\\sGMT";
private Response response;
private FitNesseContext context;
private FileResponder responder;
private Locale saveLocale;
// Example: "Tue, 02 Apr 2003 22:18:49 GMT"
```

=> Giảm thiểu comment code bằng cách code thật tường minh, rành mạch.

## Đừng dùng comment để làm màu cho code
  - Đừng dùng comment để giải thích cho code khó hiểu, hãy làm code dễ hiểu.

## Giải thích ý nghĩa ngay trong code
```java
// Check to see if the employee is eligible for full benefits
if ((employee.flags & HOURLY_FLAG) && (employee.age > 65))
```

```java
if (employee.isEligibleForFullBenefits())
```

- Hãy tạo ra các hàm có tên giống với comment mà bạn muốn viết.

## Comment tốt
### 1- Comment pháp lý
- Dùng comment pháp lý để đáp ứng nhu cầu của doanh nghiệp.

```java
/* Copyright (C) 2003,2004,2005 by Object Mentor, Inc.
 * All rights reserved. 
 * Released under the terms of the GNU General Public License version
 * 2 or later.
 */
```

### 2- Comment cung cấp thông tin
- Ví dụ, hãy xem cách mà comment này giải thích về giá trị trả về của một phương thức trừu tượng:

```java
// Returns an instance of the Responder be ing tested. 
protected abstract Responder responderInstance();
```

=> Ví dụ, trong trường hợp này, chúng ta có thể dọn dẹp comment trên bằng cách đặt lại tên hàm thành _**responderBeingTested**_.

###  3- Comment giải thích mục đích
- Đôi khi comment không chỉ cung cấp thông tin về những dòng code mà còn cung cấp ý định đằng sau nó.

```java
public void testConcurrentAddWidgets() throws Exception {
    WidgetBuilder widgetBuilder = new WidgetBuilder(new Class[]{BoldWidget.class});
    String text = "'''bold text'''";
    ParentWidget parent =
    new BoldWidget(new MockWidgetRoot(), "'''bold text'''");
    AtomicBoolean failFlag = new AtomicBoolean();
    failFlag.set(false);
    //This is our best attempt to get a race condition
    //by creating large number of threads.
        
        // Comment bên trên là comnment giải thích lý do sử dụng solution này của tác giả của đoạn code
    for (int i = 0; i < 25000; i++) {
        WidgetBuilderThread widgetBuilderThread =
        new WidgetBuilderThread(widgetBuilder, text, parent,
                                failFlag);
        Thread thread = new Thread(widgetBuilderThread);
        thread.start();
    }
    assertEquals(false, failFlag.get());
}
```
### 4- Comment làm dễ hiểu
- Khi cần giải thích 1 phần code trong thư viện khác hoặc phần code mà bạn không có quyền tùy chỉnh hãy dùng comment
```java
public void testCompareTo() throws Exception
{
    WikiPagePath a = PathParser.parse("PageA");
    WikiPagePath ab = PathParser.parse("PageA.PageB");
    WikiPagePath b = PathParser.parse("PageB");
    WikiPagePath aa = PathParser.parse("PageA.PageA");
    WikiPagePath bb = PathParser.parse("PageB.PageB");
    WikiPagePath ba = PathParser.parse("PageB.PageA");
 
    assertTrue(a.compareTo(a) == 0); // a == a
    assertTrue(a.compareTo(b) != 0); // a != b
    assertTrue(ab.compareTo(ab) == 0); // ab == ab
    assertTrue(a.compareTo(b) == -1); // a < b
    assertTrue(aa.compareTo(ab) == -1); // aa < ab
    assertTrue(ba.compareTo(bb) == -1); // ba < bb
    assertTrue(b.compareTo(a) == 1); // b > a
    assertTrue(ab.compareTo(aa) == 1); // ab > aa
    assertTrue(bb.compareTo(ba) == 1); // bb > ba
}
```

###  5- Comment cảnh báo về hậu quả

```java
 // Don't run unless you 
// have some time to kill – Đừng chạy hàm này, trừ khi mày quá rảnh 
public void _testWithReallyBigFile() {
        writeLinesToFile(10000000);
        response.setBody(testFile);
        response.readyToSend(this);
        String responseString = output.toString();
        assertSubString("Content-Length: 1000000000", responseString);
        assertTrue(bytesSent > 1000000000);
        }
 ```
- Có thể sử dụng @Ignore (“It takes too long to run”) để thay thế.

### 6- Comment todo
- Những comment dạng TODO là những công việc mà lập trình viên cho rằng nên được thực hiện, nhưng vì lý do nào đó mà họ 
không thể thực hiện nó ngay lúc này. Nó có thể là một lời nhắc để xóa một hàm không dùng nữa, hoặc yêu cầu người khác xem 
xét một số vấn đề: đặt lại một cái tên khác tốt hơn, lời nhắc thay đổi code của hàm khi kế hoạch của dự án thay đổi,..

```java
// TODO-MdM these are not needed – Hàm này không cần thiết
// We expect this to go away when we do the checkout model
// Nó sẽ bị xóa khi chúng tôi thực hiện mô hình thanh toán
protected VersionInfo makeVersion() throws Exception
{
    return null;
}
```


### 7- Comment khuếch đại
- Comment để khuếch đại tầm quan trọng của 1 cái gì đó trông có vẻ không quan trọng
```java
String listItemContent = match.group(3).trim();
// the trim is real important. It removes the starting
// spaces that could cause the item to be recognized
// as another list.
new ListItemWidget(this, listItemContent, this.level + 1);
return buildList(text.substring(match.end()));
```

### 8- Javadocs trogn public APIs
- Đây là các comment trong thuw viện để mô tả tính năng.

## Comment tồi

### 1- Comment độc thoại
- Comment không diễn giải đầy đủ chỉ có tác giả hiểu ý nghĩa của comment.

```java
public void loadProperties()
{
    try
    {
        String propertiesPath = propertiesLocation + "/" + PROPERTIES_FILE;
        FileInputStream propertiesStream = new FileInputStream(propertiesPath);
        loadedProperties.load(propertiesStream);
    }
    catch(IOException e)
    {
        // No properties files means all defaults are loaded
    }
}
```

### 2- Comment thừa
   - Phần code của hàm này đã cung cấp đầy đủ thông tin để người đọc có thể hiểu được không cần phải thêm comment.
```java
// Utility method that returns when this.closed is true. Throws an exception
// if the timeout is reached.
// Phương thức return khi this.closed là true, phát sinh ngoại lệ nếu hết thời gian chờ
public synchronized void waitForClose(final long timeoutMillis)
 throws Exception
{
    if(!closed)
    {
        wait(timeoutMillis);
        if(!closed)
            throw new Exception("MockResponseSender could not be closed");
    }
}
```

### 3- Comment sai sự thật
- Tránh viết các comment để bổ sung thông tin nhưng lại là những thông tin sai sự thật.


### 4- Comment bắt buộc
- Không viết các javadocs bừa.
```java
/**
 *
 * @param title The title of the CD
 * @param author The author of the CD
 * @param tracks The number of tracks on the CD
 * @param durationInMinutes The duration of the CD in minutes
 */
public void addCD(String title, String author,
 int tracks, int durationInMinutes) {
    CD cd = new CD();
    cd.title = title;
    cd.author = author;
    cd.tracks = tracks;
    cd.duration = duration;
    cdList.add(cd);
}
```

### 5-  Comment nhật ký
- Không viết các comment nhật ký

```java
/*
 * Changes (from 11-Oct-2001)
 * --------------------------
 * 11-Oct-2001 : Re-organised the class and moved it to new package
 * com.jrefinery.date (DG);
 * 05-Nov-2001 : Added a getDescription() method, and eliminated NotableDate
 * class (DG);
 * 12-Nov-2001 : IBD requires setDescription() method, now that NotableDate
 * class is gone (DG); Changed getPreviousDayOfWeek(),
 * getFollowingDayOfWeek() and getNearestDayOfWeek() to correct
 * bugs (DG);
 * 05-Dec-2001 : Fixed bug in SpreadsheetDate class (DG);
 * 29-May-2002 : Moved the month constants into a separate interface
 * (MonthConstants) (DG);
 * 27-Aug-2002 : Fixed bug in addMonths() method, thanks to N???levka Petr (DG);
 * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 29-May-2003 : Fixed bug in addMonths method (DG);
 * 04-Sep-2003 : Implemented Comparable. Updated the isInRange javadocs (DG);
 * 05-Jan-2005 : Fixed bug in addYears() method (1096282) (DG);
 */
```

### 6- Comment gây nhiễu
- Không viết các comment làm phức tạp các điều hiển nhiên ai cũng biết.

```java
/**
 * Default constructor.
 */
protected AnnualDateRule() {
}

/** The day of the month. */
private int dayOfMonth;

/**
 * Returns the day of the month.
 *
 * @return the day of the month.
 */
public int getDayOfMonth() {
        return dayOfMonth;
        }
```

### 7- Comment có thể thay thế bằng hàm hoặc biến 
 Đoạn code sau:
```java
// does the module from the global list <mod> depend on the
// subsystem we are part of?
if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))
```
Có thể được viết lại mà không cần comment:
```java
ArrayList moduleDependees = smodule.getDependSubsystems();
String ourSubSystem = subSysMod.getSubSystem();
if (moduleDependees.contains(ourSubSystem))
```
### 8- Đánh dấu lãnh thổ
- Hạn chế dùng các comment nổi bặt để đánh dấu 1 khu vực code.

`
// Actions //////////////////////////////////
`

### 9- Comment kết thúc
- Không dùng các comment để đánh dấu điểm kết thúc của các khối lệnh dài thay vào đó cố chỉa nhỏ khối lệnh đó ra thành nhiều hàm, biến nhỏ.

```java
public class wc {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int lineCount = 0;
        int charCount = 0;
        int wordCount = 0;
        try {
            while ((line = in.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                String words[] = line.split("\\W");
                wordCount += words.length;
            } //while
            System.out.println("wordCount = " + wordCount);
            System.out.println("lineCount = " + lineCount);
            System.out.println("charCount = " + charCount);
        } // try
        catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
        } //catch
    } //main
}
```

### 10- Comment đánh dấu thuộc tính và dòng tắc giả
- Không dùng comment để đánh dấu bản quyển, các IDE đã làm hộ việc này rồi.
`/* Added by Rick */`


### 11- Comment hóa code
- Đừng chuyển code thành comment, nếu không dùng hãy xóa đi, để lại dễ gây hiểu lầm cho người maintain.
```java
InputStreamResponse response = new InputStreamResponse();
response.setBody(formatter.getResultStream(), formatter.getByteCount());
// InputStream resultsStream = formatter.getResultStream();
// StreamReader reader = new StreamReader(resultsStream);
// response.setContent(reader.read(formatter.getByteCount()));
```

### 12- HTML Comment
- Đừng đưa HTML vào comment 
```html
/**
 * Task to run fit tests.
 * This task runs fitnesse tests and publishes the results.
 * <p/>
 * <pre>
 * Usage:
 * &lt;taskdef name=&quot;execute-fitnesse-tests&quot;
 * classname=&quot;fitnesse.ant.ExecuteFitnesseTestsTask&quot;
 * classpathref=&quot;classpath&quot; /&gt;
 * OR
 * &lt;taskdef classpathref=&quot;classpath&quot;
 * resource=&quot;tasks.properties&quot; /&gt;
 * <p/>
 * &lt;execute-fitnesse-tests
 * suitepage=&quot;FitNesse.SuiteAcceptanceTests&quot;
 * fitnesseport=&quot;8082&quot;
 * resultsdir=&quot;${results.dir}&quot;
 * resultshtmlpage=&quot;fit-results.html&quot;
 * classpathref=&quot;classpath&quot; /&gt;
 * </pre>
 */
```

- Nó làm comment khó đọc trực tiếp nhưng lại dễ đọc hơn trên trình soạn thảo hoặc IDE khác

### 13- Comment cung cấp thông tin phi tập trung 
- Nếu bắt buộc phải viết một comment, hãy đảm bảo rằng nó giải thích cho phần code gần nó nhất. Đừng cung cấp thông 
tin của toàn bộ hệ thống trong một comment cục bộ.

```java
/**
 * Port on which fitnesse would run. Defaults to <b>8082</b>.
 *
 * @param fitnessePort
 */
public void setFitnessePort(int fitnessePort)
{
    this.fitnessePort = fitnessePort;
}
```

### 14- Comment cung cấp quá nhiều thông tin lan man
- Đừng đưa các cuộc thảo luận hoặc mô tả không liên quan vào comment của bạn.
```java
/*
 RFC 2045 - Multipurpose Internet Mail Extensions (MIME)
 Part One: Format of Internet Message Bodies
 section 6.8. Base64 Content-Transfer-Encoding
 The encoding process represents 24-bit groups of input bits as output
 strings of 4 encoded characters. Proceeding from left to right, a
 24-bit input group is formed by concatenating 3 8-bit input groups.
 These 24 bits are then treated as 4 concatenated 6-bit groups, each
 of which is translated into a single digit in the base64 alphabet.
 When encoding a bit stream via the base64 encoding, the bit stream
 must be presumed to be ordered with the most-significant-bit first.
 That is, the first bit in the stream will be the high-order bit in
 the first 8-bit byte, and the eighth bit will be the low-order bit in
 the first 8-bit byte, and so on.
 */
```

### 15- Comment thiếu sự kết nối với đoạn code mà nó mô tả

- Comment phải mô tả cho những đoạn code khó hiểu chứ không phải viết 1 cách khó hiểu.
```java
/*
 * start with an array that is big enough to hold all the pixels
 * (plus filter bytes), and an extra 200 bytes for header info
 * bắt đầu với một mảng đủ lớn để chứa tất cả các pixel
 * (cộng với một số byte của bộ lọc), và thêm 200 byte cho tiêu đề
 */
 this.pngBytes = new byte[((this.width + 1) * this.height * 3) + 200];
```

### 16- Comment làm tiêu đề cho hàm
- Hãy đặt 1 cái tên hàm dài để thay cho 1 tên hàm ngắn mà còn phải bổ sung comment.


## Tìm vấn đề trong đoạn code sau 
### Code thúi
```java
/**
 * This class Generates prime numbers up to a user specified
 * maximum. The algorithm used is the Sieve of Eratosthenes.
 * <p>
 * Eratosthenes of Cyrene, b. c. 276 BC, Cyrene, Libya --
 * d. c. 194, Alexandria. The first man to calculate the
 * circumference of the Earth. Also known for working on
 * calendars with leap years and ran the library at Alexandria.
 * <p>
 * The algorithm is quite simple. Given an array of integers
 * starting at 2. Cross out all multiples of 2. Find the next
 * uncrossed integer, and cross out all of its multiples.
 * Repeat untilyou have passed the square root of the maximum
 * value.
 *
 * @author Alphonse
 * @version 13 Feb 2002 atp
 */
import java.util.*;
public class GeneratePrimes
{
    /**
    * @param maxValue is the generation limit.
    */
    public static int[] generatePrimes(int maxValue)
    {
        if (maxValue >= 2) // the only valid case
        {
            // declarations
            int s = maxValue + 1; // size of array
            boolean[] f = new boolean[s];
            int i;
            // initialize array to true.
            for (i = 0; i < s; i++)
            f[i] = true;
            // get rid of known non-primes
            f[0] = f[1] = false;
            // sieve
            int j;
            for (i = 2; i < Math.sqrt(s) + 1; i++)
            {
                if (f[i]) // if i is uncrossed, cross its multiples.
                {
                    for (j = 2 * i; j < s; j += i)
                    f[j] = false; // multiple is not prime
                }
            }
            // how many primes are there?
            int count = 0;
            for (i = 0; i < s; i++)
            {
                if (f[i])
                count++; // bump count.
            }
            int[] primes = new int[count];
            // move the primes into the result
            for (i = 0, j = 0; i < s; i++)
            {
                if (f[i]) // if prime
                primes[j++] = i;
            }
            return primes; // return the primes
        }
        else // maxValue < 2
        return new int[0]; // return null array if bad input.
    }
}

```
### Code sạch
```java
/**
 * This class Generates prime numbers up to a user specified
 * maximum. The algorithm used is the Sieve of Eratosthenes.
 * Given an array of integers starting at 2:
 * Find the first uncrossed integer, and cross out all its
 * multiples. Repeat until there are no more multiples
 * in the array.
 */
public class PrimeGenerator
{
    private static boolean[] crossedOut;
    private static int[] result;
    public static int[] generatePrimes(int maxValue)
    {
        if (maxValue < 2)
            return new int[0];
        else
        {
            uncrossIntegersUpTo(maxValue);
            crossOutMultiples();
            putUncrossedIntegersIntoResult();
            return result;
        }
    }
    private static void uncrossIntegersUpTo(int maxValue)
    {
        crossedOut = new boolean[maxValue + 1];
        for (int i = 2; i < crossedOut.length; i++)
            crossedOut[i] = false;
    }
    private static void crossOutMultiples()
    {
        int limit = determineIterationLimit();
        for (int i = 2; i <= limit; i++)
            if (notCrossed(i))
                crossOutMultiplesOf(i);
    }
    private static int determineIterationLimit()
    {
        // Every multiple in the array has a prime factor that
        // is less than or equal to the root of the array size,
        // so we don't have to cross out multiples of numbers
        // larger than that root.
        double iterationLimit = Math.sqrt(crossedOut.length);
        return (int) iterationLimit;
    }
    private static void crossOutMultiplesOf(int i)
    {
        for (int multiple = 2*i;
                multiple < crossedOut.length;
                multiple += i)
            crossedOut[multiple] = true;
    }
    private static boolean notCrossed(int i)
    {
        return crossedOut[i] == false;
    }
    private static void putUncrossedIntegersIntoResult()
    {
        result = new int[numberOfUncrossedIntegers()];
        for (int j = 0, i = 2; i < crossedOut.length; i++)
            if (notCrossed(i))
                result[j++] = i;
    }
    private static int numberOfUncrossedIntegers()
    {
        int count = 0;
        for (int i = 2; i < crossedOut.length; i++)
            if (notCrossed(i))
                count++;
        return count;
    }
}
```
Nguồn: https://toihocdesignpattern.com/chuong-4-comment-the-nao-cho-chuan.html










