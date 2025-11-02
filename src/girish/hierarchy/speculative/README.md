# Speculative Hierarchy

Speculative Hierarchy terjadi ketika dibuat hierarchy (abstract class atau interface) yang tidak benar-benar diperlukan, biasanya karena developer "mengantisipasi" kebutuhan di masa depan yang belum jelas atau tidak terjadi.

Smell ini berkebalikan dengan Missing Hierarchy. Di satu sisi kita perlu hierarchy untuk abstraction, tetapi di sisi lain kita tidak boleh membuat hierarchy yang tidak diperlukan (YAGNI - You Aren't Gonna Need It).

### Penyebab Smell

- **Over-engineering**: Developer membuat abstraction untuk "jaga-jaga" kebutuhan di masa depan.
- **Premature optimization**: Developer berusaha membuat design yang "flexible" tanpa tahu apakah flexibility tersebut benar-benar diperlukan.
- **Copy-paste design**: Developer menyalin pattern dari tempat lain tanpa memahami konteks kebutuhan.
- **Lack of requirements clarity**: Tidak jelas apa yang benar-benar diperlukan, sehingga developer membuat "general solution".

### Contoh

#### Masalah

Perhatikan package `before`. Terdapat hierarchy [Currency.java](before/Currency.java) dengan subclass [Digital.java](before/Digital.java) dan [Traditional.java](before/Traditional.java), padahal subclass-subclass ini kosong dan tidak memiliki behavior yang berbeda.

```java
public abstract class Currency {
    public abstract String getCode();
    //...
}

// Subclass yang tidak diperlukan
public abstract class Digital extends Currency {
    // KOSONG - tidak ada behavior tambahan
}

public abstract class Traditional extends Currency {
    // KOSONG - tidak ada behavior tambahan
}

// Implementasi langsung
public class IDR extends Currency {
    // ...
}

public class USD extends Currency {
    // ...
}
```

**Masalah yang terjadi:**
1. Hierarchy yang tidak berguna - Digital dan Traditional tidak menambahkan value
2. Complexity yang tidak perlu - lebih banyak class tanpa benefit
3. Maintenance overhead - lebih banyak file untuk di-maintain
4. Confusion - developer lain mungkin bingung kenapa ada hierarchy ini
5. Melanggar YAGNI principle

**Kemungkinan skenario:**
- Developer mungkin mengira akan ada behavior berbeda antara digital dan traditional currency di masa depan
- Tetapi ternyata tidak ada perbedaan yang signifikan
- Atau perbedaan tersebut tidak pernah terjadi

#### Penyelesaian

Pada package `after`, hierarchy yang tidak diperlukan dihapus. Subclass Digital dan Traditional dihilangkan, dan implementasi langsung extends dari Currency.

```java
public abstract class Currency {
    public abstract String getCode();
    //...
}

// Langsung extends Currency tanpa hierarchy intermediate
public class IDR extends Currency {
    @Override
    public String getCode() {
        return "IDR";
    }
}

public class USD extends Currency {
    @Override
    public String getCode() {
        return "USD";
    }
}
```

**Keuntungan setelah refactoring:**
1. Simplifikasi - lebih sedikit class, lebih mudah dipahami
2. Mengikuti YAGNI - tidak membuat yang tidak diperlukan
3. Easier maintenance - lebih sedikit file untuk di-maintain
4. Clearer intent - code langsung menunjukkan apa yang diperlukan
5. Jika di masa depan benar-benar diperlukan hierarchy intermediate, bisa ditambahkan saat itu

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Ada rencana jelas bahwa hierarchy akan diperlukan dalam waktu dekat
- Hierarchy tersebut memang diperlukan untuk design pattern tertentu (misalnya Strategy pattern)
- Refactoring akan menyebabkan breaking change yang besar

### Kapan Menentukan Hierarchical Level

**Gunakan hierarchy intermediate bila:**
- Ada behavior yang berbeda di beberapa subgroup
- Ada property yang hanya dimiliki oleh subgroup tertentu
- Ada constraint yang hanya berlaku untuk subgroup tertentu

**Jangan gunakan hierarchy intermediate bila:**
- Hanya berbeda di implementasi method yang sama
- Tidak ada behavior/property khusus untuk subgroup
- "Hanya untuk jaga-jaga" tanpa kebutuhan jelas

