# Missing Encapsulation

Missing Encapsulation terjadi ketika class mengekspos internal implementation details melalui method-method yang seharusnya di-hide. Method-method tersebut mengungkapkan detail tentang bagaimana class melakukan sesuatu (bukan apa yang dilakukan), sehingga coupling menjadi tinggi dan abstraction menjadi leaky.

Smell ini berbeda dengan Deficient Encapsulation yang fokus pada field. Missing Encapsulation fokus pada method yang mengekspos internal implementation.

### Penyebab Smell

- **Implementation-focused design**: Developer mendesain interface class berdasarkan implementasi, bukan berdasarkan behavior yang diperlukan.
- **Lack of abstraction**: Developer tidak memikirkan level abstraction yang tepat untuk class.
- **Inadequate refactoring**: Class berkembang dari waktu ke waktu tanpa memikirkan apakah interface-nya sudah tepat.

### Contoh

#### Masalah

Perhatikan [Encryption.java](before/Encryption.java) pada package `before`. Class ini mengekspos method-method spesifik yang mengungkapkan detail implementasi encryption algorithm yang digunakan.

```java
public class Encryption {
    public void encryptUsingDES() {
        //...
    }
    
    public void encryptUsingAES() {
        //...
    }
}
```

**Masalah yang terjadi:**
1. Caller harus tahu detail implementasi (DES vs AES)
2. Jika ada algorithm baru, perlu menambah method baru di class
3. Tight coupling - caller bergantung pada implementasi spesifik
4. Sulit untuk di-test dan di-maintain
5. Melanggar prinsip abstraction - mengungkapkan detail yang tidak perlu

#### Penyelesaian

Pada package `after`, dibuat abstraction yang lebih baik dengan menggunakan polymorphism. Class `Encryption` menjadi abstract class, dan setiap algorithm dijadikan subclass terpisah.

```java
// Abstraction yang proper
public abstract class Encryption {
    public abstract void encrypt();
}

// Implementasi spesifik di-hide di subclass
public class DES extends Encryption {
    @Override
    public void encrypt() {
        // DES implementation
    }
}

public class AES extends Encryption {
    @Override
    public void encrypt() {
        // AES implementation
    }
}
```

**Keuntungan setelah refactoring:**
- Caller tidak perlu tahu detail implementasi
- Mudah menambah algorithm baru tanpa mengubah existing code
- Loose coupling - caller hanya bergantung pada abstraction
- Polimorfisme memungkinkan runtime selection
- Mengikuti Open/Closed Principle (open for extension, closed for modification)

**Penggunaan:**
```java
Encryption encryption = new AES();  // atau new DES()
encryption.encrypt();  // Caller tidak perlu tahu implementasinya
```

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Class memang dimaksudkan sebagai low-level utility yang memang harus mengekspos detail implementasi
- Abstraction yang lebih tinggi akan membuat code menjadi over-engineered
- Detail implementasi memang diperlukan oleh caller untuk alasan khusus

