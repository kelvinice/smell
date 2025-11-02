# Analisis Code Smell - UAS Refactoring (Girish Theory)

## 1. ABSTRACTION SMELL - Missing Abstraction

**Nama Smell**: Missing Abstraction  
**Lokasi**: `FleetApplication.java` - Class `StaffData` (baris 110-113)  
**Penjelasan**: Class `StaffData` hanya menyimpan data primitif (`id` dan `name`) tanpa behavior apapun. Class ini merupakan data class yang seharusnya diekstrak menjadi abstraksi yang lebih bermakna. Data primitif yang dikelompokkan bersama (`id` dan `name`) menunjukkan Missing Abstraction karena mereka seharusnya menjadi bagian dari abstraksi yang lebih tinggi level (yaitu menjadi bagian langsung dari class `Staff` tanpa perlu class terpisah). Ini menyebabkan:
- Detail implementasi terekspos dan tersebar
- Tight coupling antara `Staff` dan `StaffData`
- Violation of Single Responsibility Principle

**Solusi**: Integrate `StaffData` langsung ke dalam class `Staff` dengan menggunakan private fields dan enkapsulasi yang tepat. Hilangkan class `StaffData` dan pindahkan data ke dalam `Staff` sebagai private fields dengan getter/setter.

**Hasil Perbaikan**: Class `Staff` sekarang memiliki enkapsulasi yang proper dengan private fields `id` dan `name` beserta getter/setter. Tidak ada lagi class `StaffData` yang tidak perlu.

---

## 2. ENCAPSULATION SMELL - Missing Encapsulation

**Nama Smell**: Missing Encapsulation  
**Lokasi**: `FleetApplication.java` - Class `StaffData` (baris 110-113) dan `Vehicle` (baris 31)  
**Penjelasan**: 
- Class `StaffData` memiliki public fields (`public String id;` dan `public String name;`) yang melanggar prinsip encapsulation. Public fields memungkinkan akses langsung dari luar class tanpa kontrol, yang dapat menyebabkan data corruption dan violation of encapsulation.
- Class `Vehicle` memiliki public field `public ServiceType serviceType;` yang seharusnya dienkapsulasi dengan private field dan accessor methods.

Ini menyebabkan:
- Data dapat diubah dari luar class tanpa validasi
- Tidak ada kontrol terhadap akses data
- Violation of information hiding principle

**Solusi**: 
- Untuk `StaffData`: Ubah public fields menjadi private dan tambahkan getter/setter (atau lebih baik, hilangkan class ini dan integrasikan ke `Staff`)
- Untuk `Vehicle.serviceType`: Ubah menjadi private field dengan getter/setter methods

**Hasil Perbaikan**: 
- `Staff` class sekarang menggunakan private fields dengan getter/setter untuk `id` dan `name`
- `Vehicle.serviceType` sekarang private dengan method `getServiceType()` dan `setServiceType()`
- Semua data dienkapsulasi dengan proper encapsulation

---

## 3. MODULARIZATION SMELL - Hub-like Modularization

**Nama Smell**: Hub-like Modularization  
**Lokasi**: `FleetApplication.java` - Class `FleetSystem` (baris 153-194)  
**Penjelasan**: Class `FleetSystem` bertindak sebagai hub yang menghubungkan banyak modul berbeda:
- Vehicle management
- Technician management  
- Billing service
- Staff management
- Notification service

Class ini memiliki terlalu banyak tanggung jawab dan dependency, membuatnya menjadi central hub yang terlalu kompleks. Setiap perubahan pada salah satu modul mempengaruhi class ini, dan class ini perlu mengetahui detail implementasi dari semua modul yang digunakannya. Ini menyebabkan:
- Tight coupling dengan banyak class
- Violation of Single Responsibility Principle
- Sulit untuk di-test dan dimaintain
- Sulit untuk di-extend dengan fitur baru

**Solusi**: Extract Class untuk memisahkan tanggung jawab:
- `VehicleManager` - menangani operasi terkait vehicle
- `TechnicianManager` - menangani operasi terkait technician
- `StaffManager` - menangani operasi terkait staff
- `FleetSystem` tetap sebagai coordinator yang menggunakan manager-manager tersebut

**Hasil Perbaikan**: `FleetSystem` sekarang lebih ringan dan hanya berperan sebagai coordinator. Setiap manager bertanggung jawab untuk domain masing-masing, mengurangi coupling dan meningkatkan modularity.

---

## 4. HIERARCHY SMELL - Missing Hierarchy

**Nama Smell**: Missing Hierarchy  
**Lokasi**: `FleetApplication.java` - Class `Technician` (baris 78-100) dan `Staff` hierarchy (baris 101-137)  
**Penjelasan**: Class `Technician` dan class-class dalam hierarchy `Staff` (Manager, Cleaner) seharusnya memiliki common hierarchy karena mereka sama-sama merupakan employee/staff yang bekerja dalam fleet system. Mereka memiliki karakteristik yang sama:
- Memiliki `id` dan `name`
- Dapat ditugaskan pekerjaan (assignTask, performDuties)
- Bagian dari sistem yang sama

Namun, `Technician` tidak memiliki hubungan hierarchy dengan `Staff`, padahal secara konseptual mereka adalah jenis employee yang berbeda. Ini menyebabkan:
- Duplikasi code (id, name di Technician dan Staff)
- Tidak dapat diperlakukan secara polymorphic
- Violation of DRY principle
- Sulit untuk menambahkan fitur common untuk semua employee

**Solusi**: Buat abstract class `Employee` sebagai parent class yang menampung common properties dan behavior. `Technician`, `Manager`, dan `Cleaner` semuanya extend dari `Employee`. Atau bisa juga buat interface `Employee` yang diimplement oleh keduanya.

**Hasil Perbaikan**: 
- Abstract class `Employee` dibuat dengan common fields (`id`, `name`) dan common methods
- `Technician`, `Manager`, dan `Cleaner` semuanya extend `Employee`
- Dapat diperlakukan secara polymorphic
- Tidak ada duplikasi code untuk id dan name
- Lebih mudah untuk menambahkan jenis employee baru

---


