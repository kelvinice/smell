# Analisis Code Smell - UTS Refactoring

## 1. BLOATERS - Long Method

**Tipe**: Long Method  
**Nama**: Long Method  
**Lokasi**: `app/LibraryManager.java` - Method `startApp()` (baris 28-140)  
**Penjelasan**: Method `startApp()` memiliki 113 baris kode dan melakukan banyak hal sekaligus: menampilkan menu, menangani input user, membuat user baru, melihat member, menambah buku, dan menghitung denda. Method yang terlalu panjang sulit dipahami, dirawat, dan di-test. Ini melanggar Single Responsibility Principle.

**Solusi**: Extract Method untuk setiap fitur utama menjadi method terpisah:
- `UserController.createUser()` - untuk membuat user baru
- `UserController.viewMembers()` - untuk melihat member
- `BookController.addBook()` - untuk menambah buku
- `PaymentController.calculateFine()` - untuk menghitung denda
- `MenuDisplay.showMenu()` - untuk menampilkan menu

**Hasil Perbaikan**: Method `startApp()` sekarang hanya 30 baris dan menggunakan switch statement untuk delegasi ke controller yang sesuai.

---

## 2. CHANGE PREVENTERS - Divergent Change

**Tipe**: Divergent Change  
**Nama**: Divergent Change  
**Lokasi**: `app/LibraryManager.java` - Class LibraryManager  
**Penjelasan**: Class `LibraryManager` harus diubah untuk berbagai alasan yang berbeda:
- Perubahan logika user registration mempengaruhi class ini
- Perubahan logika book management mempengaruhi class ini
- Perubahan logika payment mempengaruhi class ini
- Perubahan format display mempengaruhi class ini

Ini menunjukkan bahwa class ini memiliki terlalu banyak tanggung jawab dan melanggar Single Responsibility Principle.

**Solusi**: Extract Class untuk memisahkan tanggung jawab:
- `UserController` - menangani semua operasi terkait user
- `BookController` - menangani semua operasi terkait buku
- `PaymentController` - menangani semua operasi terkait pembayaran
- `MenuDisplay` - menangani tampilan menu

**Hasil Perbaikan**: Sekarang setiap perubahan pada satu fitur hanya mempengaruhi controller yang relevan, bukan seluruh `LibraryManager`.

---

## 3. DISPENSABLES - Data Class

**Tipe**: Data Class  
**Nama**: Data Class  
**Lokasi**: `models/Book.java` dan `models/Address.java`  
**Penjelasan**: Class `Book` dan `Address` hanya berisi getter dan setter tanpa behavior apapun. Mereka hanya menyimpan data dan tidak memiliki logika bisnis. Data class menandakan bahwa logika mungkin berada di tempat lain yang tidak seharusnya, membuat kode sulit dimaintain.

**Solusi**: Move behavior ke dalam class tersebut:
- `Address.getFullAddress()` - method untuk format alamat lengkap
- `Book.getDisplayInfo()` - method untuk format informasi buku yang ditampilkan

**Hasil Perbaikan**: Class `Address` dan `Book` sekarang memiliki behavior yang relevan, mengurangi feature envy dan meningkatkan encapsulation.

---

## 4. COUPLERS - Feature Envy

**Tipe**: Feature Envy  
**Nama**: Feature Envy  
**Lokasi**: `app/LibraryManager.java` - Baris 91-95  
**Penjelasan**: Method di `LibraryManager` terlalu banyak mengakses data dari `Address` object melalui chain method calls (`user.getAddress().getStreet()`, `getVillage()`, `getDistrict()`, `getMunicipality()`). Ini menunjukkan bahwa method di `LibraryManager` lebih tertarik dengan internal data dari `Address` daripada data miliknya sendiri. Formatting address seharusnya menjadi tanggung jawab `Address` atau `User`.

**Solusi**: Pindahkan logika formatting address:
- `Address.getFullAddress()` - method untuk format alamat
- `User.getFormattedInfo()` - method untuk format informasi user lengkap (termasuk alamat)

**Hasil Perbaikan**: Sekarang `UserController.viewMembers()` hanya memanggil `user.getFormattedInfo()` tanpa perlu mengakses detail internal `Address`. Ini mengurangi coupling dan meningkatkan encapsulation.

---

## 5. OBJECT-ORIENTATION ABUSERS - Switch Statements

**Tipe**: Switch Statements  
**Nama**: Switch Statements  
**Lokasi**: `models/User.java` - Method `getUserCategory()` (baris 33-40)  
**Penjelasan**: Method `getUserCategory()` menggunakan switch statement berdasarkan integer `userCategory`. Ini melanggar Open/Closed Principle karena jika ada tipe user baru, kita harus memodifikasi switch statement. Selain itu, penggunaan magic number (0, 1) membuat kode kurang readable dan error-prone.

**Solusi**: Replace Type Code with Subclasses:
- `User` menjadi abstract class dengan abstract method `getUserCategory()`
- `Member extends User` - implementasi untuk member
- `Librarian extends User` - implementasi untuk librarian

**Hasil Perbaikan**: 
- Tidak ada lagi switch statement atau magic numbers
- Mengikuti Open/Closed Principle - bisa menambah tipe user baru tanpa memodifikasi class yang sudah ada
- Type safety - tidak bisa membuat User dengan tipe yang tidak valid
- Polymorphism - behavior terspesialisasi di subclass masing-masing

---

