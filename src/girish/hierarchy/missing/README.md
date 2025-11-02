# Missing Hierarchy

Missing Hierarchy terjadi ketika terdapat behavior atau property yang sama di beberapa class, tetapi tidak dibuat abstraction (interface atau abstract class) yang menaunginya. Akibatnya, kita harus menggunakan type checking (instanceof) dan type casting di berbagai tempat untuk mengakses behavior yang sama.

Smell ini mirip dengan Switch Statements smell dari Martin Fowler, dimana kita menggunakan conditional logic untuk menentukan behavior berdasarkan type.

### Penyebab Smell

- **Lack of abstraction**: Developer tidak mengenali pola commonality di antara class-class yang berbeda.
- **Incremental development**: Class berkembang secara bertahap, dan developer tidak menyadari bahwa sudah waktunya membuat hierarchy.
- **Type-focused design**: Developer berpikir dalam bentuk concrete class, bukan abstraction.

### Contoh

#### Masalah

Perhatikan [AttackService.java](before/AttackService.java) pada package `before`. Class ini menggunakan instanceof checking untuk menentukan apakah object bisa di-hit atau tidak.

```java
public class AttackService {
    public void hit(GameObject obj, int damage) {
        if(obj instanceof Monster) {
            ((Monster) obj).hit(damage);
        } else if(obj instanceof Player) {
            ((Player) obj).hit(damage);
        }
    }
}
```

**Masalah yang terjadi:**
1. Harus menambah if-else baru untuk setiap type baru yang bisa di-hit
2. Violation of Open/Closed Principle - harus modify existing code untuk menambah type baru
3. Tidak ada contract yang jelas - apa saja yang bisa di-hit?
4. Type casting yang tidak aman
5. Logic tersebar - jika ada behavior lain yang juga perlu mengetahui hittable objects

**Perhatikan juga** bahwa [Player.java](before/Player.java) dan [Monster.java](before/Monster.java) keduanya punya method `hit()`, tetapi tidak ada interface yang menyatukan mereka.

#### Penyelesaian

Pada package `after`, dibuat interface [Hittable.java](after/Hittable.java) yang menaungi behavior `hit()`. Class [Player.java](after/Player.java) dan [Monster.java](after/Monster.java) sekarang implement interface ini.

```java
// Interface sebagai contract
public interface Hittable {
    void hit(int damage);
}

// Player implements Hittable
public class Player extends GameObject implements Hittable {
    @Override
    public void hit(int damage) {
        // player hit logic
    }
}

// Monster implements Hittable
public class Monster extends GameObject implements Hittable {
    @Override
    public void hit(int damage) {
        // monster hit logic
    }
}

// AttackService sekarang lebih clean
public class AttackService {
    public void hit(GameObject obj, int damage) {
        if(obj instanceof Hittable) {
            ((Hittable) obj).hit(damage);
        }
    }
}
```

**Keuntungan setelah refactoring:**
1. Contract jelas - interface Hittable mendefinisikan apa yang bisa di-hit
2. Mudah menambah type baru - cukup implement Hittable (Open/Closed Principle)
3. Polymorphism - bisa menggunakan Hittable sebagai type
4. Type safety yang lebih baik
5. Lebih mudah di-maintain dan di-extend

**Alternatif yang lebih baik lagi:**
```java
// Bisa juga menggunakan method overload atau generic
public class AttackService {
    public void hit(Hittable hittable, int damage) {
        hittable.hit(damage);  // Tidak perlu instanceof
    }
}
```

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Hanya ada 2-3 class yang berbagi behavior dan tidak akan berkembang
- Behavior yang dibagi sangat simple dan tidak akan berubah
- Membuat interface akan menyebabkan over-abstraction

