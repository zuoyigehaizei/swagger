package com.example.demoswagger;

import com.google.common.util.concurrent.Callables;
import org.apache.catalina.Executor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoswaggerApplicationTests {

    @Test
    public void lambdaStream() {
        List<Person> javaProgrammers = new ArrayList<Person>() {
            {
                add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
                add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
                add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
                add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
                add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
                add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
                add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
                add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
                add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
                add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
            }
        };
        List<Person> phpProgrammers = new ArrayList<Person>() {
            {
                add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
                add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
                add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
                add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
                add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
                add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
                add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
                add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
                add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
                add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
            }
        };

        //stream流的用法
//        phpProgrammers.stream().filter(p -> p.getSalary() > 1400)
//                .forEach((p) -> System.out.println(p.getFirstName()));

        //做一个可重用的过滤器
        Predicate<Person> ageFilter = (p) -> (p.getAge() > 25);
        Predicate<Person> salaryFilter = (p) -> p.getSalary() > 1400;
        Predicate<Person> genderFilter = (p) -> "female".equals(p.getGender());

        javaProgrammers.stream().filter(ageFilter)
                .filter(salaryFilter)
                .filter(genderFilter)
                .sorted((p,p2) -> (p.getFirstName().compareTo(p2.getFirstName())))
                .limit(3)  //不好使
                .forEach(person -> System.out.println(person.getFirstName()));

        List<Person> collect = javaProgrammers.stream().filter(ageFilter)
                .filter(salaryFilter)
                .filter(genderFilter)
                .sorted((p, p2) -> (p.getFirstName().compareTo(p2.getFirstName())))
                .limit(3)  //不好使
                .collect(Collectors.toList());

        //获取比较的最小值
        Person person = javaProgrammers.stream().filter(ageFilter)
                .filter(salaryFilter)
                .filter(genderFilter)
                .min((p1, p2) -> (p1.getSalary() - p2.getSalary()))
                .get();

        String collect1 = javaProgrammers.stream()
                .map(Person::getFirstName)
                .collect(Collectors.joining(";"));

        javaProgrammers.parallelStream()
                .mapToInt(p -> p.getSalary())
                .sum();

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntSummaryStatistics ssss = numbers.parallelStream()
                .mapToInt((x) -> x)
                .summaryStatistics();
        ssss.getAverage();
        System.out.println("zhixingwanvbi");
    }

    @Test
    public void contextLoads() throws ExecutionException, InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorService executorService = new ThreadPoolExecutor(10, 30, 10000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    private AtomicInteger count = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        String threadName = DemoswaggerApplicationTests.class.getSimpleName() + count.addAndGet(1);
                        System.out.println(threadName);
                        t.setName(threadName);
                        return t;
                    }
                }, new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        // 记录异常
                        // 报警处理等
                        System.out.println("error.............");
                    }
                });
        MyRunnable myRunnable = new MyRunnable();
        Future submit = executorService.submit(myRunnable);
        Future submit1 = executorService.submit(myRunnable);
        Future submit2 = executorService.submit(myRunnable);
        String o = (String)submit.get();
        String a = (String)submit1.get();
        String b = (String)submit2.get();
        System.out.println("callable" + o + a + b);
    }

    @Test
    public void timeDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = localDateTime.toLocalTime();
        LocalDate localDate = localDateTime.toLocalDate();
        System.out.println("lsdkfjlsdfj");
    }

}



//lei
class MyRunnable implements Callable {

    public String run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我是线程" + Thread.currentThread().getName());
        return "w shi xiancheng:" + Thread.currentThread().getName();
    }

    @Override
    public Object call() throws Exception {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我是线程" + Thread.currentThread().getName());
        return "w shi xiancheng:" + Thread.currentThread().getName();
    }
}

