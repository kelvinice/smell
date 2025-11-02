# Cyclically-dependent Modularization

Cyclically-dependent Modularization terjadi ketika ada circular dependency antara class-class, dimana class A bergantung pada class B, dan class B bergantung pada class A (atau melalui chain dependency yang membentuk cycle).

Circular dependency menyebabkan tight coupling dan membuat code sulit di-maintain, di-test, dan di-understand. Juga bisa menyebabkan masalah saat compilation dan initialization.

### Penyebab Smell

- **Bidirectional relationship**: Developer membuat relationship dua arah tanpa menyadari circular dependency yang terjadi.
- **Lack of design planning**: Developer tidak merencanakan dependency structure dengan baik.
- **Incremental development**: Dependency cycle terbentuk secara bertahap tanpa disadari.
- **Misunderstanding of separation**: Developer tidak memahami bagaimana memisahkan concern dengan benar.

### Contoh

#### Masalah

Perhatikan package `before`. Terdapat circular dependency antara [Order.java](before/Order.java), [Item.java](before/Item.java), dan [TaxCalculator.java](before/TaxCalculator.java).

```java
// Order bergantung pada Item dan TaxCalculator
public class Order {
    private Vector<Item> items;
    
    public double getAmount() {
        TaxCalculator calc = new TaxCalculator();
        return calc.computeAmount(this);  // Pass Order ke TaxCalculator
    }
    
    public Vector<Item> getItems() {
        // ...
    }
}

// TaxCalculator bergantung pada Order
public class TaxCalculator {
    public double computeAmount(Order order) {
        Vector<Item> items = order.getItems();  // Access Order
        double amount = 0;
        for (Item item : items) {
            amount += item.getCost();
        }
        return amount + calculateTax(amount);
    }
}
```

**Masalah yang terjadi:**
1. Circular dependency - Order → TaxCalculator → Order
2. Tight coupling - Order dan TaxCalculator tightly coupled
3. Hard to test - sulit test TaxCalculator tanpa Order
4. Hard to reuse - TaxCalculator tidak bisa digunakan tanpa Order
5. Violation of Dependency Inversion Principle

**Dependency cycle:**
```
Order → TaxCalculator → Order (CIRCULAR!)
```

#### Penyelesaian

Pada package `after`, circular dependency dipecah dengan mengubah TaxCalculator agar tidak bergantung pada Order. TaxCalculator sekarang hanya menerima data yang diperlukan (items atau amount), bukan Order object.

```java
// Order sekarang compute amount sendiri dengan bantuan TaxCalculator
public class Order {
    private Vector<Item> items;
    
    public double getAmount() {
        TaxCalculator calc = new TaxCalculator();
        
        // Compute amount di Order, bukan di TaxCalculator
        Vector<Item> items = getItems();
        double amount = 0;
        for (Item item : items) {
            amount += item.getCost();
        }
        
        // TaxCalculator hanya untuk calculate tax, tidak perlu Order
        return amount + calc.calculateTax(amount);
    }
}

// TaxCalculator sekarang independent, tidak bergantung pada Order
public class TaxCalculator {
    // Hanya method untuk calculate tax, tidak perlu Order
    double calculateTax(double amount) {
        return amount * 0.1;
    }
}
```

**Keuntungan setelah refactoring:**
1. No circular dependency - dependency flow sekarang linear
2. Loose coupling - TaxCalculator tidak bergantung pada Order
3. Easier to test - TaxCalculator bisa di-test secara independent
4. Better reusability - TaxCalculator bisa digunakan oleh class lain
5. Clear responsibility - setiap class punya tanggung jawab jelas

**Dependency setelah refactoring:**
```
Order → TaxCalculator (ONE-WAY, NO CYCLE!)
```

### Alternatif Solusi

**1. Extract Method:**
- Move computation logic ke Order
- TaxCalculator hanya untuk tax calculation

**2. Dependency Inversion:**
- Buat interface untuk calculation
- Inject dependency melalui constructor/method

**3. Separate Concerns:**
- Order untuk order management
- TaxCalculator untuk tax calculation
- Tidak saling bergantung

### Refactoring Technique

Teknik refactoring yang digunakan:
1. **Break dependency cycle** - ubah salah satu class agar tidak bergantung pada yang lain
2. **Extract method** - move logic ke class yang tepat
3. **Introduce parameter** - pass data, bukan object, untuk mengurangi coupling

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Circular dependency memang diperlukan untuk design pattern tertentu (jarang)
- Cycle adalah intentional untuk bidirectional relationship (tetapi sebaiknya gunakan observer pattern atau event-driven)
- Refactoring akan menyebabkan breaking change yang besar (tapi sebaiknya tetap di-refactor)

### Best Practices

**Untuk menghindari circular dependency:**
1. Design dependency dari top-down atau bottom-up, jangan bi-directional
2. Use dependency injection untuk loose coupling
3. Extract shared logic ke utility class
4. Use events/callbacks untuk bidirectional communication

