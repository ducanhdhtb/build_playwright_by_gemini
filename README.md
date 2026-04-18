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
    - **Ví dụ:** `Login.feature`

2.  **Step Definitions (`...Steps.java`):**
    - **Vị trí:** `src/test/java/com/tclife/stepdefinitions`
    - **Mục đích:** Là lớp "keo" (glue) dịch các câu lệnh Gherkin trong file feature thành code Java có thể thực thi.
    - **Cách hoạt động:** Mỗi `Given`, `When`, `Then` trong file feature sẽ tương ứng với một phương thức Java trong lớp này. Các phương thức này sẽ gọi đến các hành động trong Page Objects.

3.  **Page Objects (`...Page.java`):**
    - **Vị trí:** `src/main/java/com/tclife/pages`
    - **Mục đích:** Mô hình hóa các trang của ứng dụng web. Mỗi lớp Page Object chịu trách nhiệm quản lý các phần tử (locators) và các hành động của người dùng trên trang đó.

4.  **Test Runner (`TestRunner.java`):**
    - **Vị trí:** `src/test/java/com/tclife/tests`
    - **Mục đích:** Là điểm khởi đầu để chạy các bài test. Nó sử dụng các annotation của Cucumber để chỉ định nơi tìm file feature, nơi tìm step definitions, và cách tạo báo cáo.

## 4. Hướng dẫn chạy

### 4.1. Chạy trên máy cá nhân (Local)

**Bước 1: Clone dự án**

```sh
git clone https://github.com/ducanhdhtb/build_playwright_by_gemini.git
cd build_playwright_by_gemini
```

**Bước 2: Chạy kiểm thử**

Sử dụng lệnh sau trong terminal:

```sh
mvn clean test
```

Lệnh này sẽ khởi chạy TestNG, TestNG sẽ chạy `TestRunner`, và `TestRunner` sẽ thực thi các kịch bản trong các file `.feature`.

**Bước 3: Xem báo cáo kiểm thử**

Sau khi chạy xong, xem báo cáo Allure chi tiết bằng lệnh:

```sh
mvn allure:serve
```

Báo cáo sẽ được nhóm theo "Tính năng" (Feature) và "Kịch bản" (Scenario), rất trực quan và dễ theo dõi.

## 5. Tích hợp liên tục (CI/CD)

Framework này được cấu hình để chạy trên cả GitHub Actions và Jenkins.

### 5.1. Với GitHub Actions

- **File cấu hình:** `.github/workflows/ci.yml`
- **Luồng hoạt động:**
    1.  Quy trình tự động được kích hoạt mỗi khi có code mới được đẩy lên nhánh `main`.
    2.  GitHub Actions sẽ chạy `mvn clean test`.
    3.  Báo cáo Allure sẽ được tạo và triển khai lên GitHub Pages.
- **Cách xem báo cáo Allure trên GitHub:**
    1.  Truy cập vào repository trên GitHub.
    2.  Đi đến tab **"Settings"** -> **"Pages"**.
    3.  Bạn sẽ thấy một URL có dạng `https://<username>.github.io/<repository-name>/`. Đây chính là link để xem báo cáo Allure mới nhất.

### 5.2. Với Jenkins

- **File cấu hình:** `Jenkinsfile`
- **Luồng hoạt động:**
    1.  Pipeline được cấu hình để tự động chạy mỗi giờ một lần (`cron('H * * * *')`).
    2.  Jenkins sẽ checkout code, chạy `mvn clean test`, và publish báo cáo Allure.
    3.  Sau khi hoàn tất, một email thông báo kết quả sẽ được gửi đến địa chỉ đã được cấu hình.
- **Hướng dẫn thiết lập Job trên Jenkins:**
    1.  **Cài đặt Plugins:** Đảm bảo Jenkins đã được cài các plugin: `Allure Jenkins Plugin`, `Email Extension Plugin`, `JDK Tool`, `Maven Integration`.
    2.  **Cấu hình Tools:** Vào `Manage Jenkins` -> `Tools` để cấu hình đường dẫn cho `jdk11` và `maven3`.
    3.  **Cấu hình Email:** Vào `Manage Jenkins` -> `System` để cấu hình SMTP server.
    4.  **Tạo Job:**
        - Chọn `New Item`, đặt tên và chọn loại `Pipeline`.
        - Trong mục "Pipeline", chọn `Pipeline script from SCM`, điền URL của repo và đảm bảo **Script Path** là `Jenkinsfile`.
    5.  **Chạy Job:** Click `Build Now` để chạy ngay lập tức, hoặc đợi Jenkins tự động chạy theo lịch.
