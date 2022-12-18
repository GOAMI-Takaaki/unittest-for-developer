package com.example;

import java.util.Calendar;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class CalendarUtilsTest {

    @Nested
    @DisplayName("isHolidayは休日であるかを判定する。") // <-- 関数の説明はここに記載する
    @TestInstance(Lifecycle.PER_CLASS)
    class IsHolidayTest { // <-- グルーピングを追加

        @ParameterizedTest
        @MethodSource("sourceWhenTargetDateIsNotNull")
        @DisplayName("対象日付がnullではない場合") // <-- 記述を簡素にできる
        void testWhenTargetDateIsNotNull(String description, Calendar targetDate, boolean containSaturday,
                boolean expected)
                throws Exception {
            // Given by parameter
            // Calendar targetDate = Calendar.getInstance();
            // targetDate.set(2022, Calendar.AUGUST, 14);

            // When
            boolean actual = CalendarUtils.isHoliday(targetDate, containSaturday);

            // Then
            assertThat(actual, is(expected));
        }

        Stream<Arguments> sourceWhenTargetDateIsNotNull() {
            // targetDate
            Calendar weekday = Calendar.getInstance(); // 平日
            weekday.set(2022, Calendar.AUGUST, 1);
            Calendar saturday = Calendar.getInstance(); // 土曜
            saturday.set(2022, Calendar.AUGUST, 6);
            Calendar sunday = Calendar.getInstance(); // 日曜
            sunday.set(2022, Calendar.AUGUST, 7);

            // containSaturday <-- 追加した引数のパターンを追加
            boolean contain = true; // 含む
            boolean notContain = false; // 含まない

            return Stream.of(// <-- targetDate × containSaturday のパターンに変更
                    Arguments.of("対象日が平日で、土曜を含む場合、falseを返す。", weekday, contain, false),
                    Arguments.of("対象日が平日で、土曜を含まない場合、falseを返す。", weekday, notContain, false),
                    Arguments.of("対象日が土曜で、土曜を含む場合、trueを返す。", saturday, contain, true),
                    Arguments.of("対象日が土曜で、土曜を含まない場合、falseを返す。", saturday, notContain, false),
                    Arguments.of("対象日が日曜で、土曜を含む場合、trueを返す。", sunday, contain, true),
                    Arguments.of("対象日が日曜で、土曜を含まない場合、trueを返す。", sunday, notContain, true));
        }

        @Test
        @DisplayName("対象日がnullである場合、例外が発生する。")
        void testWhenTargetDateIsNull() {
            // Given
            Calendar targetDate = null;

            // When
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> CalendarUtils.isHoliday(targetDate, true));

            // Then
            assertThat(e.getMessage(), is("TargetDate is null.")); // <-- メッセージの検証もできる
        }
    }
}
