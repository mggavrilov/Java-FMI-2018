# Обектно-ориентирано програмиране с Java (част II)

## 1. СУСИ, версия 1.1

Ще рефакторираме [предната версия на СУСИ](https://github.com/fmi/java-course/blob/master/02-oop-in-java-i/lab/README.md), използвайки новопридобитите ни знания за `java.lang.Object`, static, Enums и Exceptions.

Ще добавим и някои полезни нови функции на системата.

### Промени в интерфейса `Susi`

За да не смесваме информацията за неуспешно изпълнение на даден метод с връщаната от него стойност при успешно изпълнение, променяме прототипите на `registerStudent` и `setGrade`, така че да хвърлят изключения при неуспешно изпълнение. Изключение ще хвърлят и методите `setGrade` и `getTotalCredits`, в случай че бъдат извикани за нерегистриран в системата студент.

Ще добавим и нов метод, който ще връща сумата от кредитите от курсове от даден тип, събрани от даден студент.

```java
public interface Susi {
  
  /**
  * Registers a student in the system.
  * @throws StudentAlreadyRegisteredException, if the student is already registered
  * @throws CapacityExceededException, if the university capacity is exceeded
  */
  void registerStudent(Student student) throws StudentAlreadyRegisteredException, CapacityExceededException;

  /**
   * Returns the number of registered students
   */
  int getStudentsCount();
  
  /**
  * Sets a grade for the student for the specified course and adds the credits of the
  * course to the total credits of the student.
  * @throws StudentNotFoundException, if the student is not found
  * @throws CapacityExceededException, if the student already took the maximum number of courses
  */
  void setGrade(Student student, Course course, double grade) throws StudentNotFoundException,
                                                             CapacityExceededException;
  
  /**
  * Returns the total sum of credits for this student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getTotalCredits(Student student) throws StudentNotFoundException;
  
  /**
  * Returns the grade point average for the given student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getGPA(Student student) throws StudentNotFoundException;
  
  /**
  * Returns the sum of credits per course type accumulated by the specified student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getCreditsPerType(Student student, CourseType type) throws StudentNotFoundException;

}
```

### Въвеждане на няколко типа курсове

В университета се водят три типа курсове: задължителни, изборни и практики:

| Тип          | Код | Име      |
|:------------ |:--- |:-------- |
| задължителен | 'R' | REQUIRED |
| изборен      | 'E' | ELECTIVE |
| практика     | 'P' | PRACTICE |

Моделирайте типовете курсове с Enum `CourseType`, добавете в интерфейса `Subject` метод 

```java
CourseType getType();
```

и го реализирайте в имплементиращия клас.

### Промени в класовете `Student` и `Course`

Всеки студент се определя еднозначно от факултетния си номер, а всеки курс: от уникалния си идентификатор. С други думи, две инстанции на `Student` са еднакви тогава и само тогава, когато факултетните им номера съвпадат, а две инстанции на `Course` са еднакви тогава и само тогава, когато уникалните им идентификатори съвпадат.

Отразете този факт в имплементацията на двата класа. Уверете се, че навсякъде в останалия код сравнявате коректно инстанциите им за равенство.

В класа `Course` добавете статичен метод `countByType`, който по даден масив от курсове и тип курс връща броя на онези курсове от входния масив, които са от съответния тип.

В класа `Student` добавете метод `sortCourses`, който връща масив от курсовете, по които студентът има оценка, сортирани по оценка в низходящ ред.

### Подсказки

За сортиране използвайте някой от `Arrays.sort()` методите. За да сработи той, трябва класът, обектите от който ще сортирате, да имплементира интерфейса `java.lang.Comparable`. 

Например:

```java
public class MyClass implements Comparable<MyClass> {

    public int compareTo(MyClass other) {
        // Implementation
    }

}
```

Конструкцията `Comparable<MyClass>` ще бъде обяснена на следващата лекция. За момента я използвайте наготово. Със спецификацията на метода `compareTo` се запознайте от неговата документация.
