package com.javaniu.servlet;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Lzf
 * 理解原子引用
 */
@Getter
@ToString
@AllArgsConstructor
class User{
    String userName;
    int age;
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("z3",22);
        User li4 = new User("li4",25);

        AtomicReference<User> user = new AtomicReference<>();
        user.set(z3); //主内存的值

        System.out.println(user.compareAndSet(z3,li4) +"\t"+user.get().toString());
        System.out.println(user.compareAndSet(z3,li4) +"\t"+user.get().toString());
    }
}
