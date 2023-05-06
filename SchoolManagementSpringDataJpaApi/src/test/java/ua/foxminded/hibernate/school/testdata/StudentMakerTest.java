package ua.foxminded.hibernate.school.testdata;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StudentMakerTest {

  static int generateStudents_ExpectedTwoHundred() {
    StudentMaker student = new StudentMaker();
    return (int) student.generateStudents(student.generateNames(20), student.generateSurnames(20)).stream()
        .filter(s -> s.trim().contains(" ")).count();
  }

  static Stream<Arguments> expectedAndActualStreamProvider() {
    StudentMaker student = new StudentMaker();
    return Stream.of(arguments(20, student.generateNames(20).size()),
        arguments(20, student.generateSurnames(20).size()), arguments(200, generateStudents_ExpectedTwoHundred()));
  }

  @ParameterizedTest
  @MethodSource("ua.foxminded.hibernate.school.testdata.StudentMakerTest#expectedAndActualStreamProvider()")
  void studentGenerationWithNamesAndSurnames_ShouldBeEquals(int expected, int actual) {
    assertEquals(expected, actual);
  }
}
