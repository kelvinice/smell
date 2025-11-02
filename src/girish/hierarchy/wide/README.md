# Wide Hierarchy

Wide Hierarchy terjadi ketika hierarchy terlalu "lebar" - terlalu banyak direct subclass dari satu parent class. Parent class mencoba menaungi terlalu banyak concern yang berbeda, sehingga hierarchy menjadi sulit dipahami dan di-maintain.

Smell ini berkebalikan dengan Deep Hierarchy (terlalu banyak level). Wide hierarchy fokus pada terlalu banyak sibling di level yang sama.

### Penyebab Smell

- **God class parent**: Parent class mencoba menjadi "master class" yang menaungi segala sesuatu.
- **Lack of categorization**: Developer tidak membuat grouping yang tepat untuk subclass-subclass.
- **Incremental growth**: Hierarchy berkembang tanpa perencanaan, dan setiap class baru langsung extends dari root parent.
- **Misunderstanding of abstraction**: Developer tidak memahami bahwa abstraction bisa dibuat bertingkat (intermediate abstraction).

### Contoh

#### Masalah

Perhatikan package `before`. Class [GameObject.java](before/GameObject.java) memiliki terlalu banyak direct subclass yang berbeda-beda: [Player.java](before/Player.java), [Monster.java](before/Monster.java), [NPC.java](before/NPC.java), [Stone.java](before/Stone.java), [Tree.java](before/Tree.java).

```java
public abstract class GameObject {
    // ...
}

// Terlalu banyak direct subclass
public class Player extends GameObject implements Hittable { ... }
public class Monster extends GameObject implements Hittable { ... }
public class NPC extends GameObject { ... }
public class Stone extends GameObject { ... }
public class Tree extends GameObject { ... }
```

**Masalah yang terjadi:**
1. Parent class terlalu general - mencoba menaungi terlalu banyak tipe object
2. Sulit memahami - developer harus melihat banyak subclass untuk memahami structure
3. Violation of Single Responsibility - parent class tidak jelas tanggung jawabnya
4. Sulit di-maintain - perubahan di parent bisa mempengaruhi banyak subclass yang berbeda
5. Tidak ada grouping - semua subclass sejajar padahal ada yang lebih mirip

**Observasi:**
- Player dan Monster sama-sama bisa di-hit (Hittable), punya health
- Stone dan Tree adalah environment objects, tidak bisa di-hit
- NPC mungkin punya behavior yang berbeda lagi

#### Penyelesaian

Pada package `after`, dibuat intermediate abstraction untuk mengelompokkan subclass yang mirip. [GameUnit.java](after/GameUnit.java) dibuat untuk game objects yang punya health dan bisa di-hit, dan [Environment.java](after/Environment.java) untuk environment objects.

```java
// Root abstraction tetap general
public abstract class GameObject {
    // Common behavior untuk semua game objects
}

// Intermediate abstraction untuk objects yang bisa di-hit dan punya health
public abstract class GameUnit extends GameObject implements Hittable {
    private int health;
    
    protected void reduceHealth(int damage) {
        health -= damage;
        if(health <= 0) {
            die();
        }
    }
}

// Intermediate abstraction untuk environment objects
public abstract class Environment extends GameObject {
    // Common behavior untuk environment
}

// Subclass sekarang lebih terorganisir
public class Player extends GameUnit { ... }
public class Monster extends GameUnit { ... }
public class NPC extends GameUnit { ... }  // atau Environment, tergantung behavior

public class Stone extends Environment { ... }
public class Tree extends Environment { ... }
```

**Keuntungan setelah refactoring:**
1. Better organization - subclass dikelompokkan berdasarkan similarity
2. Clearer responsibility - setiap intermediate abstraction punya tanggung jawab jelas
3. Easier to understand - hierarchy lebih mudah dipahami
4. Easier maintenance - perubahan hanya mempengaruhi subgroup yang relevan
5. Better abstraction - level abstraction yang tepat untuk setiap concern

**Structure setelah refactoring:**
```
GameObject (root)
├── GameUnit (intermediate - untuk objects dengan health)
│   ├── Player
│   ├── Monster
│   └── NPC
└── Environment (intermediate - untuk environment objects)
    ├── Stone
    └── Tree
```

### Refactoring Technique

Teknik yang digunakan adalah **Extract Superclass** atau membuat **intermediate abstraction**:
1. Identifikasi kelompok subclass yang mirip
2. Buat abstract class/interface untuk intermediate abstraction
3. Group subclass ke intermediate abstraction yang sesuai
4. Extract common behavior ke intermediate abstraction

### Kapan Membuat Intermediate Abstraction

**Buat intermediate abstraction bila:**
- Ada 3+ subclass yang berbagi behavior/property yang sama
- Subclass bisa dikelompokkan berdasarkan concern tertentu
- Parent class terlalu general dan tidak jelas tanggung jawabnya

**Jangan buat intermediate abstraction bila:**
- Hanya ada 2-3 subclass total
- Subclass sangat berbeda dan tidak bisa dikelompokkan
- Akan menyebabkan over-engineering

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Hierarchy memang dirancang untuk menaungi banyak diverse types
- Semua subclass memang perlu direct access ke parent
- Refactoring akan menyebabkan over-abstraction

