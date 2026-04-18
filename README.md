# Framework Kiểm Thử Tự Động với Playwright, Java và TestNG

## 1. Giới thiệu

Đây là một framework kiểm thử tự động được xây dựng để kiểm thử các ứng dụng web hiện đại. Framework được thiết kế theo các mẫu kiến trúc phổ biến như Page Object Model (POM), giúp cho việc bảo trì và mở rộng trở nên dễ dàng.

## 2. Công nghệ sử dụng

- **Ngôn ngữ lập trình:** Java 11
- **Nền tảng tự động hóa:** Playwright for Java
- **Quản lý dự án và thư viện:** Apache Maven
- **Nền tảng chạy test:** TestNG
- **Tạo báo cáo:** Allure Framework
- **Quản lý dữ liệu test:** Apache POI (đọc file Excel)
- **Tích hợp liên tục (CI/CD):** GitHub Actions

## 3. Cài đặt môi trường

Để có thể chạy được framework này trên máy cá nhân, bạn cần cài đặt:

1.  **JDK 11 (Java Development Kit):** Đảm bảo bạn đã cài đặt Java 11 và cấu hình biến môi trường `JAVA_HOME`.
2.  **Apache Maven:** Cài đặt Maven và cấu hình biến môi trường để có thể sử dụng lệnh `mvn` từ terminal.
3.  **Git:** Để clone (sao chép) mã nguồn từ repository.

## 4. Hướng dẫn cài đặt và chạy

### Bước 1: Clone dự án

Mở terminal và chạy lệnh sau để tải mã nguồn về máy:

```sh
git clone <URL-CỦA-REPOSITORY-CỦA-BẠN>
cd tclife
```

### Bước 2: Cài đặt các thư viện

Maven sẽ tự động thực hiện việc này. Lần đầu tiên chạy, quá trình này có thể mất một vài phút.

### Bước 3: Chạy kiểm thử

Sử dụng lệnh sau trong terminal tại thư mục gốc của dự án:

```sh
mvn clean test
```

Lệnh này sẽ thực hiện các công việc sau:
- `clean`: Xóa thư mục `target` để dọn dẹp các kết quả từ lần build trước.
- `test`: Biên dịch mã nguồn và thực thi các bộ kiểm thử được định nghĩa trong file `src/test/resources/testng.xml`.

### Bước 4: Xem báo cáo kiểm thử

Sau khi quá trình chạy test hoàn tất, bạn có thể xem báo cáo Allure chi tiết bằng lệnh:

```sh
mvn allure:serve
```

Lệnh này sẽ tạo một web server tạm thời và tự động mở báo cáo trên trình duyệt của bạn. Báo cáo sẽ bao gồm các bước thực thi, ảnh chụp màn hình tại từng bước, và file trace để debug.

## 5. Quản lý dữ liệu Test

Framework được thiết kế để tách biệt dữ liệu ra khỏi mã nguồn, giúp việc quản lý các trường hợp kiểm thử trở nên linh hoạt.

- **Vị trí file dữ liệu:** `src/test/resources/testdata/SauceDemoTestData.xlsx`
- **Cách hoạt động:**
    1.  Lớp `ExcelUtil.java` sẽ đọc dữ liệu từ file Excel.
    2.  Phương thức `@DataProvider` trong lớp `LoginPageTest` sẽ lấy dữ liệu này.
    3.  TestNG sẽ tự động chạy kịch bản `loginTest` nhiều lần, tương ứng với mỗi dòng dữ liệu trong file Excel.
- **Để thêm kịch bản mới:** Bạn chỉ cần mở file Excel và thêm một dòng mới với dữ liệu tương ứng mà không cần sửa code.

## 6. Tích hợp liên tục (CI/CD) với GitHub Actions

Framework này đã được tích hợp sẵn một quy trình CI/CD sử dụng GitHub Actions.

- **File cấu hình:** `.github/workflows/ci.yml`
- **Luồng hoạt động:**
    1.  Quy trình sẽ tự động được kích hoạt mỗi khi có code mới được đẩy lên nhánh `main`.
    2.  GitHub Actions sẽ tự động cài đặt môi trường và chạy lệnh `mvn clean test`.
    3.  Sau khi test chạy xong, báo cáo Allure sẽ được tạo và triển khai lên GitHub Pages.
    4.  Bạn có thể truy cập vào URL của GitHub Pages để xem báo cáo mới nhất mà không cần chạy lại test trên máy cá nhân. (URL có thể được tìm thấy trong `Settings` -> `Pages` của repository).
