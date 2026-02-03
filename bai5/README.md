# Bai 5 - Test case cho màn hình Add Job Title

## 1) Mô tả màn hình
Màn hình **Add Job Title** gồm các trường:
- **Job Code** (bắt buộc)
- **Job Title** (bắt buộc)
- **Job Description** (không bắt buộc)
- **Job Specification** (upload file)
- **Note** (không bắt buộc)
- Nút **Cancel** và **Save**

Yêu cầu: Form có kết nối cơ sở dữ liệu, kịch bản kiểm thử rõ ràng, test case bao phủ tất cả các trường hợp, bảng báo cáo và bảng tóm tắt lỗi rõ ràng, dễ tái lập.

---

## 2) Giả định hệ thống
- DB: SQL Server, database `QuanLyNhanSu`.
- Bảng: `job_title` (job_code unique).
- Ràng buộc:
  - `job_code`: bắt buộc, độ dài 1–20, không khoảng trắng đầu/cuối.
  - `job_title`: bắt buộc, độ dài 1–100.
  - `job_description`: tối đa 500 ký tự.
  - `job_specification`: chỉ cho phép PDF/DOC/DOCX, tối đa 5MB.
  - `note`: tối đa 500 ký tự.
- Lưu thành công: ghi vào DB và hiển thị thông báo thành công.

---

## 3) Kịch bản kiểm thử (Test Scenarios)
1. Tạo mới hợp lệ.
2. Bắt buộc nhập (Job Code, Job Title).
3. Độ dài tối thiểu/tối đa.
4. Trùng Job Code.
5. Dữ liệu Unicode/tiếng Việt.
6. Ký tự đặc biệt và ký tự không hợp lệ.
7. Trim khoảng trắng đầu/cuối.
8. Upload file hợp lệ/không hợp lệ.
9. Hủy thao tác (Cancel).
10. Hành vi nút Save (double click, spam).
11. Kết nối DB (offline/timeout/constraint).
12. Hiển thị lỗi rõ ràng và dễ tái lập.

---

## 4) Test Cases (bao phủ đầy đủ)

| TC ID | Mục tiêu | Tiền điều kiện | Bước thực hiện | Dữ liệu | Kỳ vọng | Mức độ | Loại |
|---|---|---|---|---|---|---|---|
| TC-01 | Tạo mới hợp lệ | DB online | Nhập đủ → Save | job_code=JT001, title=Developer, desc=Dev, file=spec.pdf | Lưu thành công, DB có bản ghi | High | Positive |
| TC-02 | Job Code bắt buộc | DB online | Để trống Job Code → Save | job_code=, title=Dev | Báo lỗi bắt buộc | High | Negative |
| TC-03 | Job Title bắt buộc | DB online | Để trống Job Title → Save | job_code=JT002, title= | Báo lỗi bắt buộc | High | Negative |
| TC-04 | Job Code trùng | DB có JT001 | Nhập JT001 → Save | job_code=JT001 | Báo lỗi trùng | High | Negative |
| TC-05 | Job Code min length | DB online | job_code=1 | job_code=1, title=Dev | Lưu thành công | Medium | Positive |
| TC-06 | Job Code max length | DB online | job_code=20 ký tự | job_code=20 chars | Lưu thành công | Medium | Positive |
| TC-07 | Job Code quá dài | DB online | job_code=21 ký tự | job_code=21 chars | Báo lỗi độ dài | Medium | Negative |
| TC-08 | Job Title max length | DB online | title=100 ký tự | title=100 chars | Lưu thành công | Medium | Positive |
| TC-09 | Job Title quá dài | DB online | title=101 ký tự | title=101 chars | Báo lỗi độ dài | Medium | Negative |
| TC-10 | Description max length | DB online | desc=500 ký tự | desc=500 chars | Lưu thành công | Low | Positive |
| TC-11 | Description quá dài | DB online | desc=501 ký tự | desc=501 chars | Báo lỗi độ dài | Low | Negative |
| TC-12 | Note max length | DB online | note=500 ký tự | note=500 chars | Lưu thành công | Low | Positive |
| TC-13 | Note quá dài | DB online | note=501 ký tự | note=501 chars | Báo lỗi độ dài | Low | Negative |
| TC-14 | Job Code có khoảng trắng | DB online | job_code="  JT003 " → Save | job_code="  JT003 " | Trim và lưu JT003 | Medium | Negative |
| TC-15 | Job Title chỉ khoảng trắng | DB online | title="   " → Save | job_code=JT004 | Báo lỗi bắt buộc | Medium | Negative |
| TC-16 | Unicode tiếng Việt | DB online | title="Trưởng phòng" | job_code=JT005 | Lưu thành công | Medium | Positive |
| TC-17 | Ký tự đặc biệt hợp lệ | DB online | title="R&D - Team" | job_code=JT006 | Lưu thành công | Low | Positive |
| TC-18 | Ký tự không hợp lệ | DB online | job_code="JT#01" | job_code=JT#01 | Báo lỗi định dạng | Medium | Negative |
| TC-19 | Upload file hợp lệ | DB online | Upload PDF/DOC/DOCX | file=spec.pdf | Lưu thành công | Medium | Positive |
| TC-20 | Upload file sai định dạng | DB online | Upload .exe | file=spec.exe | Báo lỗi định dạng | High | Negative |
| TC-21 | Upload file quá lớn | DB online | Upload >5MB | file=6MB.pdf | Báo lỗi dung lượng | High | Negative |
| TC-22 | Không upload file | DB online | Bỏ trống file → Save | job_code=JT007 | Lưu thành công (nếu file không bắt buộc) | Low | Positive |
| TC-23 | Save nhiều lần | DB online | Click Save 2 lần nhanh | job_code=JT008 | Chỉ tạo 1 bản ghi | Medium | Negative |
| TC-24 | Cancel | DB online | Nhập dữ liệu → Cancel | job_code=JT009 | Không lưu, form reset | Medium | Negative |
| TC-25 | DB mất kết nối | DB offline | Save hợp lệ | job_code=JT010 | Báo lỗi kết nối, không crash | High | Negative |
| TC-26 | DB timeout | DB chậm | Save | job_code=JT011 | Thông báo timeout | High | Negative |
| TC-27 | Lỗi constraint DB | DB constraint | Save thiếu title | job_code=JT012 | Báo lỗi từ server | High | Negative |
| TC-28 | Trim job title | DB online | title="  Dev  " | job_code=JT013 | Trim và lưu "Dev" | Low | Negative |
| TC-29 | Upload file, rồi Cancel | DB online | Chọn file → Cancel | file=spec.pdf | Không lưu file | Medium | Negative |
| TC-30 | Thông báo thành công | DB online | Save hợp lệ | job_code=JT014 | Thông báo “Saved successfully” | Low | Positive |

---

## 5) Bảng báo cáo kết quả test (Test Report)

| Ngày | Người test | Build | TC chạy | Passed | Failed | Blocked | Ghi chú |
|---|---|---|---|---|---|---|---|
| 2026-02-03 | Tester | v1.0 | 30 | 22 | 8 | 0 | Ví dụ minh họa |

---

## 6) Bảng tóm tắt lỗi (Bug Summary)

| Bug ID | TC liên quan | Mô tả lỗi | Mức độ | Trạng thái | Ghi chú |
|---|---|---|---|---|---|
| BUG-01 | TC-04 | Không báo lỗi khi Job Code trùng | High | Open | Dễ tái lập |
| BUG-02 | TC-23 | Nhấn Save 2 lần tạo 2 bản ghi | Medium | Open | Cần khóa nút |
| BUG-03 | TC-25 | Mất kết nối DB không báo rõ | High | Open | Cần xử lý thông báo |

---

## 7) Mẫu báo cáo lỗi chi tiết

- **Bug ID**:
- **Tiêu đề**:
- **Môi trường**:
- **Tiền điều kiện**:
- **Bước tái lập**:
- **Kết quả thực tế**:
- **Kết quả mong đợi**:
- **Tần suất**:
- **Ảnh/Video**:

---

## 8) Chuỗi kết nối SQL Server (tham khảo)

```java
String url = "jdbc:sqlserver://Admin-PC:1433;"
        + "databaseName=QuanLyNhanSu;"
        + "encrypt=true;"
        + "trustServerCertificate=true;"
        + "loginTimeout=30;";

String user = "sa";
String pass = "123";
```

---

## 9) Cách tái lập nhanh (Quick Repro)
1. Mở màn hình Add Job Title.
2. Thực hiện theo TC tương ứng trong bảng Test Cases.
3. Quan sát thông báo và kiểm tra DB để xác nhận.
