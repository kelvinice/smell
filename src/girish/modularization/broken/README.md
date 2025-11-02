# Broken Modularization

Broken Modularization terjadi ketika class terpisah menjadi dua class yang tightly coupled, padahal seharusnya menjadi satu class. Satu class menampung behavior/logic, dan class lain menampung data. Separation ini tidak memberikan benefit dan malah menyebabkan unnecessary coupling.

Smell ini mirip dengan Data Class dari Martin Fowler, tetapi fokus pada pemisahan yang tidak tepat antara data dan behavior.

### Penyebab Smell

- **Misguided separation**: Developer memisahkan data dan behavior karena merasa "seharusnya" terpisah, tanpa memahami bahwa data dan behavior yang terkait seharusnya dalam satu class.
- **Copy-paste design**: Developer menyalin pattern dari bahasa pemrograman lain (misalnya C struct) tanpa memahami konteks OOP.
- **Misunderstanding of separation of concerns**: Developer salah memahami bahwa separation of concerns berarti memisahkan data dan behavior, padahal yang benar adalah memisahkan concern yang berbeda.

### Contoh

#### Masalah

Perhatikan package `before`. Terdapat class [Device.java](before/Device.java) yang menggunakan [DeviceData.java](before/DeviceData.java) untuk menyimpan data. Device hanya mengakses data dari DeviceData tanpa logic yang berarti.

```java
// Data class yang tidak diperlukan
public class DeviceData {
    public String deviceID;
    public String devicePath;
    // Hanya data, tidak ada behavior
}

// Behavior class yang tightly coupled dengan DeviceData
public class Device {
    private DeviceData data;
    
    public String getDevicePath() {
        return data.devicePath;  // Hanya passthrough
    }
    
    public String lookupDevice(String device) {
        // Logic method
    }
    
    public boolean isEnabled() {
        // Logic method
    }
}
```

**Masalah yang terjadi:**
1. Unnecessary coupling - Device tightly coupled dengan DeviceData
2. No benefit - pemisahan tidak memberikan value tambahan
3. Extra indirection - harus melalui DeviceData untuk akses data
4. Confusion - developer lain mungkin bingung kenapa data dan behavior terpisah
5. Violation of cohesion - data dan behavior yang terkait seharusnya bersama

**Observasi:**
- DeviceData hanya berisi public fields tanpa behavior
- Device hanya mengakses fields dari DeviceData tanpa transformasi
- Tidak ada alasan yang jelas kenapa harus terpisah

#### Penyelesaian

Pada package `after`, DeviceData dihilangkan dan fields-nya di-move langsung ke Device. Sekarang data dan behavior ada dalam satu class yang cohesive.

```java
public class Device {
    // Data langsung di class, tidak perlu class terpisah
    private String deviceID;
    private String devicePath;
    
    public String getDevicePath() {
        return devicePath;  // Langsung akses, tidak perlu indirection
    }
    
    public String lookupDevice(String device) {
        // Logic method
    }
    
    public boolean isEnabled() {
        // Logic method
    }
}
```

**Keuntungan setelah refactoring:**
1. Better cohesion - data dan behavior yang terkait berada bersama
2. Reduced coupling - tidak ada unnecessary dependency
3. Simpler code - lebih mudah dipahami
4. Easier maintenance - semua terkait Device ada di satu tempat
5. Proper encapsulation - data bisa di-private dengan proper getter/setter

### Kapan Data Class Diperlukan

**Data class diperlukan bila:**
- Data digunakan oleh banyak class yang berbeda (misalnya DTO, Value Object)
- Data adalah immutable value object
- Data memang dirancang untuk transfer antara layer (misalnya API response)

**Data class TIDAK diperlukan bila:**
- Hanya digunakan oleh satu class behavior
- Data dan behavior tightly coupled
- Tidak ada alasan jelas untuk separation

### Refactoring Technique

Teknik refactoring yang digunakan adalah **Inline Class**:
1. Move semua fields dari data class ke behavior class
2. Update semua referensi ke data class
3. Hapus data class yang tidak diperlukan

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Data class memang diperlukan untuk multiple contexts
- Data class adalah DTO untuk transfer data
- Pemisahan memang dirancang untuk specific pattern (misalnya Repository pattern)

