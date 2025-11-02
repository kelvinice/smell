# Insufficient Modularization

Insufficient Modularization terjadi ketika method atau class terlalu besar dan kompleks, tetapi tidak dipecah menjadi method atau class yang lebih kecil. Code yang seharusnya di-modularize tetap berada dalam satu method atau class yang besar.

Smell ini mirip dengan Long Method dan Large Class dari Martin Fowler, tetapi fokus pada kurangnya modularization - code yang seharusnya terpisah tetap digabung.

### Penyebab Smell

- **Lack of refactoring**: Developer tidak melakukan refactoring secara teratur, sehingga method/class tumbuh tanpa kontrol.
- **Copy-paste programming**: Developer menyalin dan menempelkan code tanpa memikirkan struktur yang lebih baik.
- **Rapid development**: Developer fokus pada menyelesaikan fitur dengan cepat tanpa memikirkan struktur code.
- **Lack of awareness**: Developer tidak menyadari bahwa method/class sudah terlalu besar dan kompleks.

### Contoh

#### Masalah

Perhatikan [Foo.java](Foo.java). Method `bar()` melakukan beberapa hal yang berbeda: print message, count capital letters, dan print result. Semua logic ini berada dalam satu method.

```java
public class Foo {
    //McCabe Cyclomatic Complexity = 3
    public void bar() {
        String s = "Halo Dunia";
        System.out.println(s);
        
        int capitalCount = 0;
        
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c >= 'A' && c <= 'Z') capitalCount++;
        }
        
        System.out.println(capitalCount);
    }
}
```

**Masalah yang terjadi:**
1. Multiple responsibilities - method melakukan beberapa hal berbeda
2. Hard to test - sulit test setiap bagian secara terpisah
3. Hard to reuse - logic counting capital letters tidak bisa digunakan lagi
4. Low cohesion - print dan counting logic dicampur
5. Violation of Single Responsibility Principle

**Cyclomatic Complexity = 3** menunjukkan bahwa method ini memiliki beberapa paths yang berbeda, yang biasanya mengindikasikan perlu dipecah.

#### Penyelesaian

Method `bar()` seharusnya dipecah menjadi beberapa method yang lebih kecil dan focused:

```java
public class Foo {
    public void bar() {
        String s = "Halo Dunia";
        printMessage(s);
        
        int capitalCount = countCapitalLetters(s);
        printCount(capitalCount);
    }
    
    private void printMessage(String message) {
        System.out.println(message);
    }
    
    private int countCapitalLetters(String text) {
        int count = 0;
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if(c >= 'A' && c <= 'Z') {
                count++;
            }
        }
        return count;
    }
    
    private void printCount(int count) {
        System.out.println(count);
    }
}
```

**Keuntungan setelah refactoring:**
1. Single responsibility - setiap method punya satu tugas
2. Easier to test - bisa test setiap method secara terpisah
3. Better reusability - `countCapitalLetters()` bisa digunakan di tempat lain
4. Higher cohesion - logic terkait dikelompokkan
5. Easier to understand - method names menjelaskan apa yang dilakukan

**Alternatif dengan utility class:**
```java
public class Foo {
    public void bar() {
        String s = "Halo Dunia";
        printMessage(s);
        
        int capitalCount = StringUtils.countCapitalLetters(s);
        printCount(capitalCount);
    }
}

// Utility class untuk string operations
public class StringUtils {
    public static int countCapitalLetters(String text) {
        // ... implementation
    }
}
```

### Refactoring Techniques

**1. Extract Method:**
- Identifikasi bagian code yang bisa di-extract
- Buat method baru dengan nama yang jelas
- Replace code dengan method call

**2. Extract Class:**
- Jika method terlalu besar, extract menjadi class terpisah
- Move related methods bersama

**3. Extract Variable:**
- Extract complex expression menjadi variable dengan nama jelas
- Improve readability

### Metrics untuk Mendeteksi

**Cyclomatic Complexity:**
- Complexity > 10: perlu dipecah
- Complexity 5-10: consider refactoring
- Complexity < 5: biasanya OK

**Lines of Code:**
- Method > 50 lines: consider refactoring
- Class > 500 lines: consider refactoring

**Number of Responsibilities:**
- Jika method melakukan > 3 hal berbeda: perlu dipecah

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Method memang harus melakukan beberapa hal yang tightly coupled
- Complexity masih manageable dan mudah dipahami
- Refactoring akan membuat code menjadi over-engineered

### Best Practices

**Untuk menghindari insufficient modularization:**
1. Keep methods small and focused
2. Extract method ketika melihat duplicate logic
3. Extract method ketika method melakukan multiple things
4. Use meaningful names untuk extracted methods
5. Regular code review untuk identify large methods/classes

