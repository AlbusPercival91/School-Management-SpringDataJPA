package ua.foxminded.springdatajpa.school.testdata;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GroupMakerTest {

  static boolean generateGroups_ExpectedPattern() {
    GroupMaker group = new GroupMaker();
    boolean assigned = false;
    Pattern pattern = Pattern.compile("[a-z]{2}-[0-9]{2}");
    int matchedPattern = (int) group.generateGroups().stream().map(pattern::matcher).filter(Matcher::find).count();

    if (matchedPattern == 10) {
      assigned = true;
    }
    return assigned;
  };

  static boolean assignGroupId_fromTenToThirtyPerStudentExpected() {
    GroupMaker group = new GroupMaker();
    boolean assigned = false;
    List<Integer> studentsPerGroup = new ArrayList<>();
    studentsPerGroup = group.assignGroupId().stream().filter(Objects::nonNull)
        .map(i -> Collections.frequency(group.assignGroupId(), i)).collect(Collectors.toList());
    int nullQuantity = (int) group.assignGroupId().stream().filter(Objects::isNull).count();

    if (Collections.min(studentsPerGroup) >= 10 && Collections.max(studentsPerGroup) <= 30 && nullQuantity > 0) {
      assigned = true;
    }
    return assigned;
  }

  static Stream<Arguments> expectedAndActualStreamProvider() {
    return Stream.of(arguments(true, generateGroups_ExpectedPattern()),
        arguments(true, assignGroupId_fromTenToThirtyPerStudentExpected()));
  }

  @ParameterizedTest
  @MethodSource("ua.foxminded.springdatajpa.school.testdata.GroupMakerTest#expectedAndActualStreamProvider()")
  void groupsGenerationAndGroupsAssignation_ShouldBeEquals(boolean expected, boolean actual) {
    assertEquals(expected, actual);
  }
}
