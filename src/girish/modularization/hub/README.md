# Hub-like Modularization

Hub-like Modularization terjadi ketika satu class menjadi "hub" atau pusat yang digunakan oleh banyak class lain, tetapi class tersebut tidak memiliki cohesion yang baik - method-method di dalamnya tidak terkait satu sama lain.

Smell ini mirip dengan God Class atau Large Class dari Martin Fowler, tetapi fokus pada class yang menjadi dependency hub dengan banyak tanggung jawab yang tidak terkait.

### Penyebab Smell

- **Utility class abuse**: Developer membuat satu "utility class" besar yang menampung semua helper methods.
- **Lack of categorization**: Developer tidak mengelompokkan methods berdasarkan concern atau responsibility.
- **Convenience over design**: Developer memilih convenience (semua di satu tempat) daripada design yang baik.
- **Incremental growth**: Class berkembang secara bertahap tanpa refactoring, sehingga menjadi terlalu besar dan tidak cohesive.

### Contoh

#### Masalah

Perhatikan package `before`. Class [Utilities.java](before/Utilities.java) menjadi hub yang menampung berbagai macam utility methods yang tidak terkait: validation methods, string manipulation methods, dan kemungkinan lainnya.

```java
public class Utilities {
    // Validation methods
    public static boolean validateEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    // ... other validation methods
    
    // String manipulation methods (TIDAK TERKAIT dengan validation!)
    public static String toTitleCase(String s) {
        String result = "";
        boolean afterWhitespace = true;
        for (char c : s.toCharArray()) {
            if(Character.isWhitespace(c)) {
                afterWhitespace = true;
                result += c;
                continue;
            }
            if(afterWhitespace) {
                result += Character.toUpperCase(c);
                afterWhitespace = false;
            } else {
                result += Character.toLowerCase(c);
            }
        }
        return result;
    }
    
    // ... other string manipulation methods
}
```

**Masalah yang terjadi:**
1. Low cohesion - validation methods dan string manipulation tidak terkait
2. Hard to find - developer sulit menemukan method yang dicari
3. Hard to maintain - perubahan di satu concern bisa mempengaruhi yang lain
4. Violation of Single Responsibility Principle
5. God class - class mencoba melakukan terlalu banyak hal

**Penggunaan:**
```java
// Banyak class menggunakan Utilities, membuatnya menjadi hub
Utilities.validateEmail(email);
Utilities.toTitleCase(name);
Utilities.otherMethod1();
Utilities.otherMethod2();
// ...
```

#### Penyelesaian

Pada package `after`, Utilities dipecah menjadi beberapa class yang lebih cohesive berdasarkan concern mereka. [Validator.java](after/Validator.java) untuk validation, dan [StringManipulation.java](after/StringManipulation.java) untuk string manipulation.

```java
// Class untuk validation concerns
public class Validator {
    public static boolean validateEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    // ... other validation methods
}

// Class untuk string manipulation concerns
public class StringManipulation {
    public static String toTitleCase(String s) {
        // ... implementation
    }
    
    // ... other string manipulation methods
}
```

**Keuntungan setelah refactoring:**
1. Better cohesion - setiap class punya concern yang jelas
2. Easier to find - method lebih mudah ditemukan berdasarkan concern
3. Easier to maintain - perubahan hanya mempengaruhi class yang relevan
4. Single Responsibility - setiap class punya satu tanggung jawab
5. Better organization - code lebih terorganisir

**Penggunaan setelah refactoring:**
```java
// Lebih jelas dan terorganisir
Validator.validateEmail(email);
StringManipulation.toTitleCase(name);
```

### Refactoring Technique

Teknik refactoring yang digunakan adalah **Extract Class**:
1. Identifikasi groups of methods yang terkait (cohesive)
2. Extract setiap group menjadi class terpisah
3. Beri nama class yang jelas berdasarkan concern
4. Update semua references ke class baru

### Organizing Principles

**Group methods berdasarkan:**
- **Domain concern**: validation, formatting, calculation
- **Data type**: string operations, number operations, date operations
- **Functionality**: file operations, network operations, database operations

**Contoh grouping yang baik:**
- `EmailValidator`, `PhoneValidator`, `CreditCardValidator` → `Validator` class
- `StringFormatter`, `NumberFormatter`, `DateFormatter` → `Formatter` class
- `FileReader`, `FileWriter`, `FileUtils` → `FileOperations` class

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Methods memang terkait dan cohesive
- Class hanya digunakan untuk internal utilities dalam satu module
- Refactoring akan menyebabkan over-engineering

### Best Practices

**Untuk menghindari hub-like modularization:**
1. Group methods berdasarkan concern, bukan convenience
2. Extract class ketika methods tidak terkait
3. Use package organization untuk grouping
4. Keep classes focused pada single responsibility
5. Regular refactoring untuk menjaga cohesion

