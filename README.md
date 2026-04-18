# Framework Kiểm Thử Tự Động với Playwright và Cucumber

## 1. Giới thiệu

Đây là một framework kiểm thử tự động được xây dựng để kiểm thử các ứng dụng web hiện đại. Framework sử dụng kiến trúc **BDD (Behavior-Driven Development)** với Cucumber, kết hợp với **Page Object Model (POM)** để tạo ra một hệ thống kiểm thử mạnh mẽ, dễ đọc và dễ bảo trì.

Các kịch bản kiểm thử được viết bằng ngôn ngữ tự nhiên (Gherkin), giúp cho tất cả mọi người trong đội dự án, từ Business Analyst đến Lập trình viên, đều có thể đọc, hiểu và đóng góp.

## 2. Công nghệ sử dụng

- **Ngôn ngữ lập trình:** Java 11
- **Nền tảng tự động hóa:** Playwright for Java
- **Kiến trúc kiểm thử:** BDD với Cucumber
- **Nền tảng chạy test:** TestNG
- **Quản lý dự án và thư viện:** Apache Maven
- **Tạo báo cáo:** Allure Framework
- **Tích hợp liên tục (CI/CD):** GitHub Actions, Jenkins

## 3. Kiến trúc và Luồng hoạt động

Framework được thiết kế theo nhiều lớp để tách biệt các mối quan tâm:

```
[Features] -> [Step Definitions] -> [Page Objects] -> [Playwright]
```

1.  **Features (`.feature` files):**
    - **Vị trí:** `src/test/resources/features`
    - **Mục đích:** Chứa các kịch bản kiểm thử được viết bằng ngôn ngữ tự nhiên (Gherkin). Đây là "Tài liệu sống", mô tả hành vi của ứng dụng.

2.  **Step Definitions (`...Steps.java`):**
    - **Vị trí:** `src/test/java/com/tclife/stepdefinitions`
    - **Mục đích:** Là lớp "keo" (glue) dịch các câu lệnh Gherkin thành code Java có thể thực thi.

3.  **Page Objects (`...Page.java`):**
    - **Vị trí:** `src/main/java/com/tclife/pages`
    - **Mục đích:** Mô hình hóa các trang của ứng dụng web, quản lý các phần tử (locators) và các hành động của người dùng trên trang đó.

4.  **Test Runner (`TestRunner.java`):**
    - **Vị trí:** `src/test/java/com/tclife/tests`
    - **Mục đích:** Là điểm khởi đầu để chạy các bài test, kết nối TestNG với Cucumber.

## 4. Hướng dẫn chạy

### 4.1. Chạy trên máy cá nhân (Local)

**Bước 1: Clone dự án**

```sh
git clone https://github.com/ducanhdhtb/build_playwright_by_gemini.git
cd build_playwright_by_gemini
```

**Bước 2: Chạy kiểm thử**

```sh
mvn clean test
```

**Bước 3: Xem báo cáo kiểm thử**

```sh
mvn allure:serve
```

## 5. Tích hợp liên tục (CI/CD)

Framework này được cấu hình để chạy trên cả GitHub Actions và Jenkins.

### 5.1. Với GitHub Actions

- **File cấu hình:** `.github/workflows/ci.yml`
- **Luồng hoạt động:** Tự động chạy test và triển khai báo cáo Allure lên GitHub Pages mỗi khi có code mới được đẩy lên.
- **Cách xem báo cáo:** Truy cập tab **"Settings"** -> **"Pages"** của repository để lấy URL của báo cáo.

### 5.2. Với Jenkins

- **File cấu hình:** `Jenkinsfile`
- **Luồng hoạt động:** Tự động chạy test mỗi giờ, publish báo cáo Allure và gửi email thông báo kết quả.
- **Hướng dẫn thiết lập:** Xem chi tiết trong file `architecture.md`.

## 6. Tổng kết - Các tính năng nổi bật

Framework này đã được xây dựng hoàn chỉnh với các tính năng chuyên nghiệp:

-   **Kiến trúc BDD:** Các kịch bản test được viết bằng ngôn ngữ tự nhiên, dễ hiểu cho mọi thành viên trong đội.
-   **Báo cáo trực quan:** Tích hợp Allure để tạo báo cáo chi tiết với ảnh chụp màn hình từng bước và khả năng debug sâu với Playwright Trace.
-   **Tự động hóa CI/CD kép:** Sẵn sàng để chạy trên cả GitHub Actions và Jenkins, cho phép tự động chạy theo lịch, tự động publish báo cáo và gửi thông báo.
-   **Thiết kế hướng bảo trì:** Áp dụng Page Object Model giúp việc cập nhật và bảo trì code trở nên đơn giản khi giao diện web thay đổi.
-   **Thông minh với môi trường:** Tự động phát hiện môi trường CI/CD để chuyển sang chế độ không giao diện (headless), giúp chạy test ổn định.
-   **Tài liệu hóa đầy đủ:** Có file `README.md` và `architecture.md` chi tiết, giúp người mới dễ dàng tiếp cận và sử dụng.

## 7. Công việc tiếp theo

Framework đã hoàn thiện về mặt nền tảng. Công việc tiếp theo là vận hành và mở rộng nó:

1.  **Viết kịch bản test mới:** Tạo các file `.feature` mới cho các chức năng khác của ứng dụng.
2.  **Thêm Step Definitions và Page Objects:** Viết code Java tương ứng cho các kịch bản mới.
3.  **Bảo trì:** Cập nhật các locators trong Page Objects khi giao diện web thay đổi.
4.  **Phân tích kết quả:** Theo dõi các báo cáo Allure được tạo ra tự động để tìm kiếm lỗi và đảm bảo chất lượng cho sản phẩm.
