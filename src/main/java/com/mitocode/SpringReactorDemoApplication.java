package com.mitocode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

@SpringBootApplication
public class SpringReactorDemoApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringReactorDemoApplication.class);
    private static List<String> dishes = new ArrayList<>();

    private void createMono(){
        Mono<String> m1 = Mono.just("Hello World!");
        m1.subscribe(log::info);
    }

    private void createFlux(){
        Flux<String> fx = Flux.fromIterable(dishes);
        fx.collectList().subscribe(list -> log.info(list.toString()));
        //fx.subscribe(log::info);
    }

    private void m1doOnNext(){
        Flux<String> fx = Flux.fromIterable(dishes);
                fx
                .doOnNext(x -> log.info("Element: " + x))
                .subscribe();
    }

    private void m2map(){
        Flux<String> fx1 = Flux.fromIterable(dishes);
        Flux<String> fx2 = fx1.map(String::toUpperCase);
        fx2.subscribe(log::info);
    }

    private void m3flatMap(){
        Mono.just("jaime").map(x -> 34).subscribe(e -> log.info("Data: " + e));
        Mono.just("jaime").map(x -> Mono.just(34)).subscribe(e -> log.info("Data: " + e));
        Mono.just("jaime").flatMap(x -> Mono.just(34)).subscribe(e -> log.info("Data: " + e));
    }

    public void m4range(){
        Flux<Integer> fx = Flux.range(0, 10);
        fx.map(x -> x * 2)
                .subscribe(s -> log.info("Data: " + s));
    }

    public void m5delayElements() throws InterruptedException {
        Flux.range(0, 20)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(x -> log.info("Data: " + x))
                .subscribe();

        Thread.sleep(22000);
    }

    public void m6zipWith(){
        List<String> clients = new ArrayList<>();
        clients.add("Client 1");
        clients.add("Client 2");
        //clients.add("Client 3");

        Flux<String> fx1 = Flux.fromIterable(dishes);
        Flux<String> fx2 = Flux.fromIterable(clients);

        fx1.zipWith(fx2, (d, c) -> c + " - " + d).subscribe(log::info);
    }

    public void m7merge(){
        List<String> clients = new ArrayList<>();
        clients.add("Client 1");
        clients.add("Client 2");

        Flux<String> fx1 = Flux.fromIterable(dishes);
        Flux<String> fx2 = Flux.fromIterable(clients);
        Mono<String> m1 = Mono.just("MitoCode");

        Flux.merge(fx1, fx2, m1).subscribe(log::info);
    }

    private void m8filter(){
        Predicate<String> predicate = e -> e.startsWith("Pan");
        Flux.fromIterable(dishes)
                .filter(predicate)
                .subscribe(log::info);
    }

    public void m9take(){
        Flux<String> fx1 = Flux.fromIterable(dishes);
        fx1.take(10).subscribe(log::info);
    }

    public void m10TakeLast() {
        Flux<String> fx1 = Flux.fromIterable(dishes);
        fx1.takeLast(2).subscribe(log::info);
    }

    public void m11DefaultIfEmpty(){
        dishes = new ArrayList<>();
        Flux<String> fx1 = Flux.fromIterable(dishes); //Flux.empty(); /Mono.empty();
        fx1.map(String::toUpperCase)
                .filter(e -> e.startsWith("PAN"))
                .defaultIfEmpty("EMPTY FLUX")
                .subscribe(log::info);
    }

    public void m12Error(){
        Flux<String> fx1 = Flux.fromIterable(dishes);
        fx1.doOnNext(e -> {
            throw new ArithmeticException("Arithmetic Exception");
        })
                .onErrorMap(e -> new Exception("Error: " + e.getMessage()))
                //.onErrorReturn("ERROR!!!")
                .subscribe(log::info);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringReactorDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dishes.add("Pan el con chicharron");
        dishes.add("Tacos al pastor");
        dishes.add("Bandeja Paisa");
        m12Error();
    }
}
