# Unnecessary Hierarchy

Unnecessary Hierarchy terjadi ketika dibuat hierarchy (inheritance) yang tidak memberikan value tambahan. Subclass-subclass hanya mewarisi dari parent tanpa menambahkan behavior atau property baru, atau behavior yang berbeda sangat minimal sehingga tidak perlu hierarchy.

Smell ini mirip dengan Speculative Hierarchy, tetapi fokusnya pada hierarchy yang sudah ada dan digunakan, bukan yang "untuk jaga-jaga".

### Penyebab Smell

- **Over-abstraction**: Developer membuat hierarchy karena merasa "harus" menggunakan inheritance, bukan karena memang diperlukan.
- **Misunderstanding of inheritance**: Developer salah memahami kapan harus menggunakan inheritance vs composition.
- **Copy-paste inheritance**: Developer menyalin pattern inheritance dari tempat lain tanpa memahami konteks.
- **Premature generalization**: Developer membuat general solution padahal yang diperlukan adalah simple class.

### Contoh

#### Masalah

Perhatikan package `before`. Terdapat class [TrafficLight.java](before/TrafficLight.java) dengan subclass [Red.java](before/Red.java), [Green.java](before/Green.java), dan [Yellow.java](before/Yellow.java). Subclass-subclass ini kosong dan tidak menambahkan behavior atau property apapun.

```java
public class TrafficLight {
    // Empty or minimal
}

// Subclass yang tidak diperlukan
public class Red extends TrafficLight {
    // KOSONG - tidak ada behavior tambahan
}

public class Green extends TrafficLight {
    // KOSONG - tidak ada behavior tambahan  
}

public class Yellow extends TrafficLight {
    // KOSONG - tidak ada behavior tambahan
}
```

**Masalah yang terjadi:**
1. Hierarchy yang tidak berguna - subclass tidak menambahkan value
2. Over-engineering - lebih kompleks dari yang diperlukan
3. Maintenance overhead - lebih banyak class tanpa benefit
4. Confusion - developer lain mungkin bingung kenapa perlu hierarchy
5. Violation of KISS (Keep It Simple, Stupid) principle

**Kemungkinan skenario:**
- Developer mungkin berpikir setiap warna traffic light adalah "type" yang berbeda
- Tetapi pada kenyataannya, warna hanya merupakan state/value, bukan type yang berbeda
- Inheritance untuk representasi state adalah anti-pattern

#### Penyelesaian

Pada package `after`, hierarchy dihilangkan. TrafficLight menjadi simple class dengan field `color` untuk menyimpan state.

```java
public class TrafficLight {
    String color;  // Simple state, bukan hierarchy
}
```

**Alternatif yang lebih baik (dengan enum):**
```java
public class TrafficLight {
    public enum Color { RED, YELLOW, GREEN }
    
    private Color color;
    
    public TrafficLight(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void changeColor() {
        // State transition logic
        switch(color) {
            case RED: color = Color.GREEN; break;
            case GREEN: color = Color.YELLOW; break;
            case YELLOW: color = Color.RED; break;
        }
    }
}
```

**Keuntungan setelah refactoring:**
1. Simplicity - lebih sederhana dan mudah dipahami
2. State-based design - warna sebagai state, bukan type
3. Easier maintenance - hanya satu class untuk di-maintain
4. Clearer intent - jelas bahwa ini adalah state management, bukan type hierarchy
5. Better design - menggunakan state pattern atau enum lebih tepat daripada inheritance

### Inheritance vs Composition vs State

**Gunakan Inheritance bila:**
- Subclass benar-benar menambahkan behavior yang berbeda
- Ada "is-a" relationship yang jelas
- Subclass akan mengganti/extend behavior parent secara signifikan

**Gunakan Composition/State bila:**
- Perbedaan hanya pada value/state
- Behavior sama, hanya berbeda data
- Tidak ada polymorphism yang diperlukan

**Untuk Traffic Light:**
- Warna adalah **state**, bukan **type**
- Semua traffic light punya behavior yang sama (change color)
- Perbedaannya hanya pada state saat ini
- Cocok menggunakan State Pattern atau simple enum/field

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Hierarchy memang diperlukan untuk design pattern tertentu
- Refactoring akan menyebabkan breaking change yang besar
- Ada rencana untuk menambah behavior berbeda di subclass

