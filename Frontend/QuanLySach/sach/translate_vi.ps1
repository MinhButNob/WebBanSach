Set-Location "c:\Desktop\Spring Project\WebQuanLySach\Frontend\QuanLySach\sach"

$files = Get-ChildItem -Recurse src -Include *.vue,*.js | Where-Object { $_.FullName -notmatch "node_modules" }

$map = [ordered]@{
  "Library System" = "He thong thu vien"
  "Dashboard" = "Bang dieu khien"
  "Books" = "Sach"
  "Manage Books" = "Quan ly sach"
  "Loans" = "Muon sach"
  "Process Loans" = "Xu ly muon sach"
  "Profile" = "Ho so"
  "Fines" = "Tien phat"
  "Users" = "Nguoi dung"
  "Reports" = "Bao cao"
  "Returns" = "Tra sach"
  "Logout" = "Dang xuat"

  "All categories" = "Tat ca the loai"
  "Search" = "Tim kiem"
  "Category" = "The loai"
  "Sort by" = "Sap xep theo"
  "Title" = "Tieu de"
  "Popularity" = "Pho bien"
  "Newest" = "Moi nhat"

  "books found" = "sach tim thay"
  "Search and browse the library collection." = "Tim kiem va duyet bo suu tap sach."
  "Search by keyword, title, or author" = "Tim theo tu khoa, tieu de hoac tac gia"
  "No books match the current filters." = "Khong co sach phu hop bo loc hien tai."

  "Current loans" = "Khoan dang muon"
  "Loan history" = "Lich su muon"
  "Book title" = "Ten sach"
  "Loan date" = "Ngay muon"
  "Due date" = "Han tra"
  "Return date" = "Ngay tra"
  "Status" = "Trang thai"
  "Action" = "Thao tac"
  "Actions" = "Thao tac"
  "Renew" = "Gia han"
  "Return" = "Tra"

  "Available" = "Con sach"
  "Unavailable" = "Het sach"
  "Publisher" = "Nha xuat ban"
  "Total copies" = "Tong ban sao"
  "Available copies" = "Ban sao con"
  "Categories" = "The loai"
  "Description" = "Mo ta"
  "Borrow" = "Muon"
  "Reserve" = "Dat truoc"
  "Back to books" = "Quay lai danh sach sach"

  "My Loans" = "Khoan muon cua toi"
  "Library Login" = "Dang nhap thu vien"
  "Password" = "Mat khau"

  "Admin Users" = "Nguoi dung quan tri"
  "Add user" = "Them nguoi dung"
  "Edit" = "Sua"
  "Delete" = "Xoa"
  "Admin Reports" = "Bao cao quan tri"
  "Generate report" = "Tao bao cao"
  "Start date" = "Tu ngay"
  "End date" = "Den ngay"
  "Report type" = "Loai bao cao"
  "Results" = "Ket qua"
  "Item" = "Hang muc"

  "Previous" = "Truoc"
  "Next" = "Sau"

  "Programming" = "Lap trinh"
  "Software Design" = "Thiet ke phan mem"
  "Databases" = "Co so du lieu"
  "Reference" = "Tham khao"

  "Books borrowed" = "Sach da muon"
  "Overdue books" = "Sach qua han"
  "Total fines" = "Tong tien phat"
  "Active reservations" = "Dat truoc dang hoat dong"

  "Borrowed" = "Dang muon"
  "Overdue" = "Qua han"
  "Returned" = "Da tra"
  "Unpaid" = "Chua thanh toan"
  "Paid" = "Da thanh toan"
  "Suspended" = "Tam khoa"
  "Active" = "Hoat dong"

  "Most borrowed books" = "Sach duoc muon nhieu nhat"
  "Most active users" = "Nguoi dung hoat dong nhieu nhat"
  "Overdue statistics" = "Thong ke qua han"
  "Revenue from fines" = "Doanh thu tu tien phat"
}

foreach ($file in $files) {
  $content = Get-Content $file.FullName -Raw
  foreach ($k in $map.Keys) {
    $content = $content.Replace($k, $map[$k])
  }
  Set-Content -Path $file.FullName -Value $content -Encoding utf8
}

Write-Output "translation-done"
