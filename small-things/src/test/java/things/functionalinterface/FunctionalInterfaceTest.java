package things.functionalinterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

public class FunctionalInterfaceTest {

    @Test
    public void FunctionTest() {
        // 익명 클래스를 이용한 방법으로 적용하려는 메서드를 Override해서 사용한다.
        Function<String, Integer> toInt = new Function<String, Integer>() {
            @Override
            public Integer apply(final String value) {
                return Integer.parseInt(value);
            }
        };

        // 람다식을 이용하고 메서드 레퍼런스를 사용해 가독성 있게 구현할 수 있다.
        Function<String, Integer> toIntValue = Integer::parseInt;

        final Integer number1 = toInt.apply("100");
        final Integer number2 = toIntValue.apply("200");
        System.out.println(number1);
        System.out.println(number2);
    }

    @Test
    public void FunctionIdentityTest() {
        // Function.identity() 메서드는 t -> t로 자기 자신 그대로 가는 것을 말한다
        final Function<Integer, Integer> identity = Function.identity();

        System.out.println(identity.apply(100) == 100);
    }

    @Test
    public void ConsumerTest() {
        final Consumer<String> printValue = System.out::println;
        // 될 것 같지만 안된다. 함수형에는 명확한 Input과 Output이 있어야 하기 때문에
        // final Function<String, Void> printVoid = System.out::println;
        printValue.accept("HelloWorld!");
    }

    @Test
    public void PredicateTest() {
        final Predicate<Integer> isPositive = value -> value > 0;

        System.out.println(isPositive.test(100));
        System.out.println(isPositive.test(-1));
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(filter(list, isPositive));
    }

    // 이런 식으로 Predicate를 활용할 수 있다. 어떤 list에 대해서 filter를 거치는 방법
    private static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<>();
        for (T input : list) {
            if (filter.test(input)) {
                result.add(input);
            }
        }
        return result;
    }

    @Test
    public void SupplierTest() {
        final Supplier<String> helloSupplier = () -> "hello";

        long start = System.currentTimeMillis();

        printValidIndex(0, getVeryHeavyWork());
        printValidIndex(-1, getVeryHeavyWork());
        printValidIndex(-2, getVeryHeavyWork());

        // 메서드의 리턴값을 바로 가지고 하는 것이 아니라서 Supplier를 써주면 인덱싱 계산 후에 get해서 불러올 수 있다.
        printValidIndexUsingSupplier(0, () -> getVeryHeavyWork());
        printValidIndexUsingSupplier(-1, () -> getVeryHeavyWork());
        printValidIndexUsingSupplier(-2, () -> getVeryHeavyWork());

        System.out.println("It took " + ((System.currentTimeMillis() - start) / 1000));
    }

    private static String getVeryHeavyWork() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "bingbong";
    }

    // String을 한 번 더 감싸줌으로써 인덱스를 먼저 검사 후, value를 꺼내올 수 있다. 불필요한 메모리 낭비, Lazy Evaluation을 줄일 수 있다.
    private static void printValidIndexUsingSupplier(int number, Supplier<String> valueSupplier) {
        if (number >= 0) {
            System.out.println("value is : " + valueSupplier.get());
        } else {
            System.out.println("Invalid");
        }
    }

    private static void printValidIndex(int number, String value) {
        if (number >= 0) {
            System.out.println("value is : " + value);
        } else {
            System.out.println("Invalid");
        }
    }

    @Test
    public void testCustomInterface() {
        // 쓰는 순간에 타입이 결정된다. 그래서 람다식을 써도 괜찮다.
        print(1L, "bingbong", "test@email.com",
            (id, name, email) -> "id = " + id + ", name = " + name + ", email = " + email);
    }

    private <T1, T2, T3> void print(T1 t1, T2 t2, T3 t3, Custom<T1, T2, T3, String> function) {
        System.out.println(function.apply(t1, t2, t3));
    }

    // 제네릭 타입 파라미터이기 때문에 추론이 가능하다.
    interface Custom<T1, T2, T3, R> {

        R apply(T1 t1, T2 t2, T3 t3);
    }

    @Test
    public void testInvalidFunctionalInterface() {
        final InvalidFunctionalInterface anonymousClass = new InvalidFunctionalInterface() {
            @Override
            public <T> String mkString(final T value) {
                return value.toString();
            }
        };

        // 람다식으로 하게 되면 추론이 불가능하게 된다. Input 타입과 Output 타입을 정확히 해줄 수가 없다.
        // final InvalidFunctionalInterface invalidFunctionalInterface = value -> value.toString();

        System.out.println(anonymousClass.mkString(123));
        // 여기 안으로 들어가야지 그 타입을 알 수 있다.
        // System.out.println(invalidFunctionalInterface.mkString(123));
    }

    // 함수형 인터페이스를 사용할 때 제약사항. 제네릭을 사용하면 람다식에서는 사용하지 못 한다.
    @FunctionalInterface
    interface InvalidFunctionalInterface {

        <T> String mkString(T value);
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }

    private static <T> BigDecimal total(List<T> list, Function<T, BigDecimal> mapper) {
        BigDecimal total = BigDecimal.ZERO;
        for (final T t : list) {
            total = total.add(mapper.apply(t));
        }
        return total;
    }

    @Test
    public void identitdy() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        System.out.println(
            map(list, i -> i)
        );
        // 위와 아래는 똑같다.

        System.out.println(
            map(list, Function.identity())
        );

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        numbers.stream()
            .filter(number -> number > 3)
            .filter(number -> number < 9)
            .map(number -> number * 2)
            .filter(number -> number > 20)
            .findFirst()
        ;
    }
}
