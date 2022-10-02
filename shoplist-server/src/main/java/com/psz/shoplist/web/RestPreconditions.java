package com.psz.shoplist.web;

import java.util.Optional;

public class RestPreconditions {
    public static <T> T checkFound(Optional<T> resource) {
        if (resource.isPresent()) {
            return resource.get();
        }
        throw new MyResourceNotFoundException();
    }

    public static <T> void checkNotNull(T resource){
        if (resource == null){
            throw new MyResourceEmptyException();
        }
    }

    public static class MyResourceNotFoundException extends RuntimeException{

    }

    public static class MyResourceEmptyException extends RuntimeException{

    }
}
