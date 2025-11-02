# Deficient Encapsulation

Deficient Encapsulation terjadi ketika field di dalam class di-declare sebagai `public`, sehingga bisa diakses dan dimodifikasi secara langsung dari luar class tanpa melalui getter/setter.

Smell ini melanggar prinsip encapsulation dalam Object-Oriented Programming, dimana internal state dari object seharusnya tidak bisa diakses langsung dari luar. Dengan memberikan akses langsung ke field, kita kehilangan kontrol atas validasi dan konsistensi data.

### Penyebab Smell

- **Lack of awareness**: Developer tidak memahami pentingnya encapsulation atau belum terbiasa menggunakan getter/setter.
- **Laziness**: Developer memilih cara cepat dengan membuat field public untuk menghindari menulis getter/setter.
- **Inadequate design**: Tidak ada perencanaan yang matang dalam desain class, sehingga field langsung di-expose sebagai public.

### Contoh

#### Masalah

Perhatikan [SizeRequirement.java](before/SizeRequirement.java) pada package `before`. Field `alignment` dideklarasikan sebagai `public float alignment`, sehingga bisa diakses langsung dari luar class.

Masalah yang bisa terjadi:
1. Tidak ada validasi ketika nilai alignment di-set
2. Nilai bisa di-set di luar rentang yang valid (harus antara 0.0 dan 1.0)
3. Tidak ada kontrol atas perubahan nilai
4. Melanggar prinsip encapsulation

```java
public class SizeRequirement {
    public float alignment;  // BISA LANGSUNG DIUBAH DARI LUAR!
    //...
}
```

#### Penyelesaian

Pada package `after`, field `alignment` diubah menjadi `private` dan ditambahkan method `getAlignment()` dan `setAlignment()` dengan validasi. Dengan cara ini, kita memastikan bahwa nilai alignment selalu valid.

```java
public class SizeRequirement {
    private float alignment;  // TERLINDUNG!
    
    public void setAlignment(float alignment) {
        if(alignment < 0 || alignment > 1) {
            throw new IllegalArgumentException("alignment must be between 0.0 and 1.0");
        }
        this.alignment = alignment;
    }
    
    public float getAlignment() {
        return alignment;
    }
}
```

**Keuntungan setelah refactoring:**
- Validasi otomatis saat set nilai
- Konsistensi data terjaga
- Encapsulation yang proper
- Memudahkan maintenance dan debugging

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Field tersebut memang sengaja dibuat public untuk keperluan khusus (misalnya konstanta yang final)
- Class tersebut adalah data transfer object (DTO) yang memang tidak memerlukan validasi
- Field tersebut adalah konstanta yang tidak akan berubah (misalnya `public static final`)

