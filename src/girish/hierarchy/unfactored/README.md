# Unfactored Hierarchy

Unfactored Hierarchy terjadi ketika ada duplicate code atau common behavior yang ada di beberapa subclass, tetapi tidak di-extract ke parent class atau abstract class. Akibatnya, code yang sama tersebar di beberapa class, melanggar DRY (Don't Repeat Yourself) principle.

Smell ini mirip dengan Pull Up Method/Field refactoring dari Martin Fowler, dimana common code seharusnya di-move ke parent class.

### Penyebab Smell

- **Incremental development**: Class berkembang secara bertahap, dan developer lupa untuk melakukan refactoring ketika melihat duplicate code.
- **Copy-paste programming**: Developer menyalin code dari satu subclass ke subclass lain tanpa menyadari bahwa seharusnya code tersebut ada di parent.
- **Lack of refactoring awareness**: Developer tidak menyadari bahwa duplicate code di subclass perlu di-factored ke parent.
- **Fear of breaking change**: Developer takut memodifikasi parent class karena khawatir akan merusak subclass yang lain.

### Contoh

#### Masalah

Perhatikan package `before`. Class [Player.java](before/Player.java) dan [Monster.java](before/Monster.java) keduanya memiliki logic yang sama untuk mengelola health dan method `hit()`, tetapi logic tersebut tidak di-extract ke parent class.

```java
public class Player extends GameObject implements Hittable {
    private int armor;
    private int health;
    
    @Override
    public void hit(int damage) {
        double damageMultiplier = (1-((0.052*armor)/(0.9+0.048*Math.abs(armor))));
        if(damageMultiplier < 0) damageMultiplier = 0;
        
        health -= (damage * damageMultiplier);  // Common logic dengan Monster
        if(health <= 0) {
            die();
        }
    }
    
    @Override
    public void die() {
        //...
    }
}

public class Monster extends GameObject implements Hittable {
    private int health;
    
    @Override
    public void hit(int damage) {
        health -= damage;  // Logic serupa dengan Player
        if(health <= 0) {
            die();  // Duplicate logic
        }
    }
    
    @Override
    public void die() {
        //...
    }
}
```

**Masalah yang terjadi:**
1. Duplicate code - logic health management ada di Player dan Monster
2. Maintenance burden - jika logic berubah, harus diubah di beberapa tempat
3. Inconsistency risk - mudah terjadi perbedaan implementasi
4. Violation of DRY principle
5. Tidak ada tempat central untuk health management logic

#### Penyelesaian

Pada package `after`, dibuat abstract class [GameUnit.java](after/GameUnit.java) yang menaungi common behavior untuk health management. Class Player dan Monster sekarang extends GameUnit.

```java
// Abstract class untuk common behavior
public abstract class GameUnit extends GameObject implements Hittable {
    private int health;
    
    protected void reduceHealth(int damage) {
        health -= damage;
        if(health <= 0) {
            die();
        }
    }
    
    // die() tetap abstract karena implementasi bisa berbeda
}

// Player menggunakan common logic dengan customization
public class Player extends GameUnit {
    private int armor;
    
    @Override
    public void hit(int damage) {
        double damageMultiplier = (1-((0.052*armor)/(0.9+0.048*Math.abs(armor))));
        if(damageMultiplier < 0) damageMultiplier = 0;
        
        // Menggunakan method dari parent
        this.reduceHealth((int)(damage * damageMultiplier));
    }
    
    @Override
    public void die() {
        // Player-specific die logic
    }
}

// Monster lebih simple karena langsung menggunakan parent logic
public class Monster extends GameUnit {
    @Override
    public void hit(int damage) {
        // Langsung menggunakan parent method
        this.reduceHealth(damage);
    }
    
    @Override
    public void die() {
        // Monster-specific die logic
    }
}
```

**Keuntungan setelah refactoring:**
1. DRY principle - common logic ada di satu tempat
2. Easier maintenance - perubahan health logic hanya di satu tempat
3. Consistency - semua GameUnit menggunakan logic yang sama
4. Clear hierarchy - jelas mana common behavior dan mana yang specific
5. Extensibility - mudah menambah GameUnit baru yang menggunakan logic yang sama

### Refactoring Technique

Teknik refactoring yang digunakan adalah **Pull Up Method** dan **Pull Up Field**:
- Extract common methods ke parent class
- Extract common fields ke parent class
- Keep specific implementations di subclass

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Duplicate code sangat kecil dan tidak akan berubah
- Class yang duplicate tidak akan berkembang lagi
- Refactoring akan menyebabkan coupling yang tidak diinginkan

