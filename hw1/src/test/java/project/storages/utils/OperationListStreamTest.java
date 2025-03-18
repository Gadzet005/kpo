package project.storages.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.consts.OperationType;
import project.domains.BankAccount;
import project.domains.Category;
import project.domains.Operation;

@ExtendWith(MockitoExtension.class)
class OperationListStreamTest {
    @Mock
    Operation op1;
    @Mock
    Operation op2;
    @Mock
    Operation op3;
    @Mock
    BankAccount account1;
    @Mock
    BankAccount account2;
    @Mock
    Category cat1;
    @Mock
    Category cat2;

    @BeforeEach
    void setUp() {
        reset(op1, op2, op3);
    }

    Date getDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    @DisplayName("OperationListStream.filterByType")
    void filterByType() {
        when(op1.getType()).thenReturn(OperationType.EXPENSE);
        when(op2.getType()).thenReturn(OperationType.INCOME);
        when(op3.getType()).thenReturn(OperationType.EXPENSE);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.filterByType(OperationType.EXPENSE).toList();

        assertEquals(2, res.size());
        assertTrue(res.contains(op1));
        assertTrue(res.contains(op3));
    }

    @Test
    @DisplayName("OperationListStream.filterByAmountRange")
    void filterByAmountRange() {
        when(op1.getAmount()).thenReturn(100.0);
        when(op2.getAmount()).thenReturn(200.0);
        when(op3.getAmount()).thenReturn(300.0);

        // @formatter:off
        var testCases = new Object[][] { 
            { 100.0, 200.0, List.of(op1, op2) },
            { 200.0, 300.0, List.of(op2, op3) },
            { 350.0, 400.0, List.of() }, 
            { null, 200.0, List.of(op1, op2) },
            { 100.0, null, List.of(op1, op2, op3) },
            { null, null, List.of(op1, op2, op3) }, 
        };
        // @formatter:on

        var operations = List.of(op1, op2, op3);
        BiFunction<Double, Double, OperationListStream> getStream = (Double min,
                Double max) -> new OperationListStream(operations.stream())
                        .filterByAmountRange(min, max);

        for (var testCase : testCases) {
            var min = (Double) testCase[0];
            var max = (Double) testCase[1];
            @SuppressWarnings("unchecked")
            var expected = (List<Operation>) testCase[2];
            var stream = getStream.apply(min, max);
            var res = stream.toList();

            assertEquals(expected.size(), res.size());
            for (var op : expected) {
                assertTrue(res.contains(op));
            }
        }
    }

    @Test
    @DisplayName("OperationListStream.filterByDateRange")
    void filterByDateRange() {
        var date1 = getDate(2025, 1, 15);
        var date2 = getDate(2025, 2, 19);
        var date3 = getDate(2025, 2, 20);

        when(op1.getDate()).thenReturn(date1);
        when(op2.getDate()).thenReturn(date2);
        when(op3.getDate()).thenReturn(date3);

        // @formatter:off
        var testCases = new Object[][] {
            { date1, date3, List.of(op1, op2, op3) },
            { date2, date3, List.of(op2, op3) },
            { getDate(2024, 1, 1), getDate(2024, 1, 30), List.of() },
            { null, null, List.of(op1, op2, op3) },
            { null, date1, List.of(op1) },
            { date2, null, List.of(op2, op3) }, 
        };
        // @formatter:on

        var operations = List.of(op1, op2, op3);
        BiFunction<Date, Date, OperationListStream> getStream = (Date start,
                Date end) -> new OperationListStream(operations.stream())
                        .filterByDateRange(start, end);

        for (var testCase : testCases) {
            var start = (Date) testCase[0];
            var end = (Date) testCase[1];
            @SuppressWarnings("unchecked")
            var expected = (List<Operation>) testCase[2];

            var stream = getStream.apply(start, end);
            var res = stream.toList();

            assertEquals(expected.size(), res.size());
            for (var op : expected) {
                assertTrue(res.contains(op));
            }
        }
    }

    @Test
    @DisplayName("OperationListStream.filterByCategory")
    void filterByCategory() {
        when(cat1.getId()).thenReturn(1);
        when(cat2.getId()).thenReturn(2);
        when(op1.getCategory()).thenReturn(cat1);
        when(op2.getCategory()).thenReturn(cat2);
        when(op3.getCategory()).thenReturn(cat1);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.filterByCategory(cat2.getId()).toList();

        assertEquals(1, res.size());
        assertTrue(res.contains(op2));
    }

    @Test
    @DisplayName("OperationListStream.filterByAccount")
    void filterByAccount() {
        when(account1.getId()).thenReturn(1);
        when(account2.getId()).thenReturn(2);
        when(op1.getAccount()).thenReturn(account1);
        when(op2.getAccount()).thenReturn(account2);
        when(op3.getAccount()).thenReturn(account1);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.filterByAccount(account1.getId()).toList();

        assertEquals(2, res.size());
        assertTrue(res.contains(op1));
        assertTrue(res.contains(op3));
    }

    @Test
    @DisplayName("OperationListStream.sum")
    void sum() {
        when(op1.getAmount()).thenReturn(100.0);
        when(op1.getType()).thenReturn(OperationType.EXPENSE);
        when(op2.getAmount()).thenReturn(200.0);
        when(op2.getType()).thenReturn(OperationType.INCOME);
        when(op3.getAmount()).thenReturn(300.0);
        when(op3.getType()).thenReturn(OperationType.INCOME);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.sum();

        assertEquals(400.0, res);
    }

    @Test
    @DisplayName("OperationListStream.sumAbs")
    void sumAbs() {
        when(op1.getAmount()).thenReturn(100.0);
        when(op2.getAmount()).thenReturn(200.0);
        when(op3.getAmount()).thenReturn(300.0);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.sumAbs();

        assertEquals(600.0, res);
    }

    @Test
    @DisplayName("OperationListStream.average")
    void average() {
        when(op1.getAmount()).thenReturn(100.0);
        when(op2.getAmount()).thenReturn(200.0);
        when(op3.getAmount()).thenReturn(300.0);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.average();

        assertEquals(200.0, res);
    }

    @Test
    @DisplayName("OperationListStream.count")
    void count() {
        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.count();

        assertEquals(3, res);
    }

    @Test
    @DisplayName("OperationListStream.sortByDate")
    void sortByDate() {
        when(op1.getDate()).thenReturn(getDate(2025, 1, 2));
        when(op2.getDate()).thenReturn(getDate(2025, 1, 3));
        when(op3.getDate()).thenReturn(getDate(2025, 1, 1));

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.sortByDate().toList();

        assertEquals(op3, res.get(0));
        assertEquals(op1, res.get(1));
        assertEquals(op2, res.get(2));
    }

    @Test
    @DisplayName("OperationListStream.sortByAmount")
    void sortByAmount() {
        when(op1.getAmount()).thenReturn(200.0);
        when(op2.getAmount()).thenReturn(100.0);
        when(op3.getAmount()).thenReturn(300.0);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.sortByAmount().toList();

        assertEquals(op2, res.get(0));
        assertEquals(op1, res.get(1));
        assertEquals(op3, res.get(2));
    }

    @Test
    @DisplayName("OperationListStream chaining")
    void chaining() {
        var date1 = getDate(2025, 1, 1);
        var date2 = getDate(2025, 1, 2);
        var date3 = getDate(2025, 1, 3);

        when(op1.getDate()).thenReturn(date1);
        when(op2.getDate()).thenReturn(date2);
        when(op3.getDate()).thenReturn(date3);
        when(op2.getAmount()).thenReturn(200.0);
        when(op3.getAmount()).thenReturn(300.0);

        var operations = List.of(op1, op2, op3);
        var stream = new OperationListStream(operations.stream());
        var res = stream.filterByDateRange(date2, date3)
                .filterByAmountRange(150.0, 250.0).count();

        assertEquals(1, res);
    }
}
