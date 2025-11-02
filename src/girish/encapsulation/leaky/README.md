# Leaky Encapsulation

Leaky Encapsulation terjadi ketika method getter mengembalikan reference langsung ke internal collection/object tanpa membuat copy, sehingga caller bisa memodifikasi internal state dari object.

Walaupun field sudah di-encapsulate dengan `private`, tetapi jika getter mengembalikan reference yang sama dengan internal state, maka encapsulation menjadi "bocor" karena caller masih bisa mengubah internal state melalui reference tersebut.

### Penyebab Smell

- **Shallow copy**: Developer tidak menyadari bahwa mengembalikan reference collection/object berarti memberikan akses langsung ke internal state.
- **Performance misconception**: Developer menghindari membuat copy karena khawatir akan performance, padahal untuk collection kecil, overhead-nya minimal.
- **Lack of defensive programming**: Developer tidak memikirkan bahwa caller bisa memanipulasi data melalui reference yang dikembalikan.

### Contoh

#### Masalah

Perhatikan [ToDoList.java](before/ToDoList.java) pada package `before`. Method `getList()` mengembalikan reference langsung ke internal `Vector<ToDo> list`, sehingga caller bisa memodifikasi list internal melalui reference yang dikembalikan.

```java
public class ToDoList {
    private Vector<ToDo> list;
    
    public Vector<ToDo> getList() {
        //shallow copy - MASALAH!
        return list;  // Return reference langsung
    }
}
```

**Masalah yang terjadi:**
```java
ToDoList todoList = new ToDoList();
todoList.add(new ToDo("Task 1"));

Vector<ToDo> list = todoList.getList();
list.clear();  // BISA MENGHAPUS INTERNAL STATE!

// Sekarang todoList kosong, padahal seharusnya masih ada "Task 1"
```

#### Penyelesaian

Pada package `after`, method `getList()` dibuat mengembalikan deep copy dari internal list. Setiap item di-copy menggunakan `clone()`, sehingga caller tidak bisa memodifikasi internal state.

```java
public class ToDoList {
    private Vector<ToDo> list;
    
    public Vector<ToDo> getList() {
        //deep copy - AMAN!
        Vector<ToDo> result = new Vector<>();
        
        for (ToDo toDo : list) {
            try {
                result.add((ToDo) toDo.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        
        return result;  // Return copy, bukan reference
    }
}
```

**Keuntungan setelah refactoring:**
- Internal state terlindungi dari modifikasi eksternal
- Encapsulation yang benar-benar terjamin
- Caller tidak bisa merusak internal consistency
- Lebih mudah untuk debugging

**Alternatif solusi:**
- Menggunakan unmodifiable collection (misalnya `Collections.unmodifiableList()`)
- Menggunakan immutable collections
- Membuat interface yang lebih spesifik yang tidak mengizinkan modifikasi

### When to Ignore

Smell ini tidak perlu di-refactor bila:
- Performance benar-benar menjadi concern kritis dan copy operation sangat mahal
- Class memang sengaja didesain untuk shared mutable state (jarang terjadi)
- Collection yang dikembalikan memang dimaksudkan untuk dimodifikasi oleh caller sebagai bagian dari design

